<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%@include file ="../header.jsp" %>
<%
ArrayList<NoticeInfo> articleList = (ArrayList<NoticeInfo>)request.getAttribute("articleList");
// 화면에서 보여줄 게시글 목록을 ArrayList로 받아옴
PageInfo pageInfo = (PageInfo)request.getAttribute("pageInfo");
// 페이징에 필요한 각종 데이터들을 PageInfo형 인스턴스에 담음
// attribute에 들어있는 데이터들은 Object로 되어있기 때문에 사용하려면 해당 자료형으로 형변환을 해야 함 

int cpage = pageInfo.getCpage();	// 현재 페이지 번호
int pcnt = pageInfo.getPcnt();		// 전체 페이지 개수
int rcnt = pageInfo.getRcnt();		// 전체 게시글 개수
int spage = pageInfo.getSpage();	// 블록 시작 페이지 번호
int epage = pageInfo.getEpage();	// 블록 종료 페이지 번호
int psize = pageInfo.getPsize();	// 페이지 크기
int bsize = pageInfo.getBsize();	// 블록 크기

String schargs = "", args = "";
// 검색조건, 검색어, 검색쿼리스트링, 전체쿼리스트링
String schtype = pageInfo.getSchtype();	// 검색 조건
String keyword = pageInfo.getKeyword();	// 검색어
if (schtype == null || keyword == null) {	// 검색을 하지 않은 경우
	schtype = "";	keyword = "";
} else if (!keyword.equals("")) {	// 검색어가 있으면
	schargs = "&schtype=" + schtype + "&keyword=" + keyword;
	// 검색어가 있을 경우 검색관련 데이터를 쿼리스트링으로 지정
}
args = "&cpage=" + cpage + schargs;

%>
<style>
#brd th { border-bottom:3px black double; text-align:center;  font-size:18px;}
#brd td { border-bottom:1px black solid;  font-size:18px; }

.wrapper {
	width:1250px; margin:0 auto; height:800px; padding-top:50px;
}
k { color:#fff; text-decoration:none; }

a:link { color:#4f4f4f; text-decoration:none; }
a:visited { color:#4f4f4f; text-decoration:none; }
a:hover { color:#2788ff; text-decoration:none; }
a:focus { color:#2788ff; text-decoration:none; }
a:active { color:#2788ff; text-decoration:none; }


tr, td { padding:10px 0; }
</style>
<div class="wrapper">
<h2>공지사항 목록 화면</h2>
<table width="1250" cellpadding="5" cellspacing="0" id="brd">
<tr>
<th width="10%">번호</th><th width="*">제목</th>
<th width="10%">조회수</th><th width="15%">작성일</th>
</tr>
<%
if (articleList.size() > 0 && rcnt >0) {	// 보여줄 목록이 있으면
	int num = rcnt - (psize * (cpage - 1));
	String title = "", lnk = "";
	for (int i = 0 ; i <articleList.size() ; i++) {
		title = articleList.get(i).getBn_title();
		lnk = "<a href='brd_view.ntc?idx=" + articleList.get(i).getBn_idx() + args +"'title='" + title+ "'>";
		if(title.length() > 28) {
			title = title.substring(0, 26) + "...";
		}
%>
<tr align="center">
<td><%=num %></td><td align="left"><%=lnk + title + "</a>" %></td>
<td><%=articleList.get(i).getBn_read() %></td>
<td><%=articleList.get(i).getBn_date().substring(2, 11).replace('-', '.') %></td>
</tr>
<%
	num--;
	}
}
%>

</table>
<br />
<table width="1250" cellpadding="5" cellspacing="0">
<tr><td align="center">
<%
if (rcnt > 0) {	// 게시글이 있을 경우
	if (cpage == 1) {	// 현재 페이지 번호가 1이면
		out.println("[&lt;&lt;]&nbsp;&nbsp;[&lt;]&nbsp;&nbsp;");
	} else {
		out.println("<a href='brd_list.ntc?cpage=1" + schargs + "'>");
		out.println("[&lt;&lt;]</a>&nbsp;&nbsp;");
		out.println("<a href='brd_list.ntc?cpage="+ (cpage - 1) + schargs + "'>");
		out.println("[&lt;]</a>&nbsp;&nbsp;");
	}	// 첫 페이지와 이전 페이지 링크
	
	for (int i = 1, j = spage ; i<= bsize && j <= epage ; i++, j++) {
	// i : 루프돌릴 횟수를 검사하는 용도의 변수, j : 페이지 번호 출력용 변수
	// 조건 : psize만큼 루프를 도는데 페이지가 마지막 페이지일 경우 psize보다 작아도 멈춤
		if (cpage == j) {
			out.print("&nbsp;<strong>" + j + "</strong>&nbsp;");
		} else { 
			out.print("&nbsp;<a href='brd_list.ntc?cpage=" + j + schargs + "'>");
			out.print(j + "</a>&nbsp;");
		}
	}
	
	if (cpage == pcnt) {	// 현재 페이지 번호가 마지막 페이지 번호이면
		out.println("&nbsp;&nbsp;[&gt;]&nbsp;&nbsp;[&gt;&gt;]");
	} else {
		out.println("&nbsp;&nbsp;<a href='brd_list.ntc?cpage=" + 
			(cpage + 1) + schargs + "'>[&gt]</a>");
		out.println("<a href='brd_list.ntc?cpage="+ pcnt + schargs + "'>");
		out.println("[&gt;&gt;]</a>");
	}
	
} else {	// 게시글이 없을 경우 : 페이징 생략
	out.println("검색결과가 없습니다.");
}
%>
</td></tr>
<form name="frmSch" method="get">
<tr align="right"><td>
	<select name="schtype">
		<option value="title" <%if (schtype.equals("title")) { %>selected<%} %> >제목</option>
		<option value="content" <%if (schtype.equals("content")) { %>selected<%} %> >내용</option>
		<option value="tc" <%if (schtype.equals("tc")) { %>selected<%} %>>제목+내용</option>
	</select>
	<input type="text" name="keyword" />
	<input type="submit" value="검 색" )/>
</td></tr>
</form>
</table>
</div>
<%@ include file="../footer.jsp" %>