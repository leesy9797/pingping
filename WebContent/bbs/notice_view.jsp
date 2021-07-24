<!— notice_view,jsp —>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%@include file ="../header.jsp" %>
<%
NoticeInfo article = (NoticeInfo)request.getAttribute("article");
if (article == null) {
	out.println("<script>");
	out.println("alert('잘못된 경로로 들어오셨습니다.');");
	out.println("history.back();");
	out.println("</script>");
}

request.setCharacterEncoding("utf-8");
int idx = Integer.parseInt(request.getParameter("idx"));
int cpage = Integer.parseInt(request.getParameter("cpage"));
String schtype = request.getParameter("schtype");
String keyword = request.getParameter("keyword");
String args = "?cpage=" + cpage;
if (schtype != null && keyword != null && !keyword.equals("")) {
	args += "&schtype=" + schtype + "&keyword=" + keyword;
}	// 검색어가 있을 경우에만 쿼리스트링에 추가
%>
<style>
.wrapper {
	width:1250px; margin:0 auto; height:1800px; padding-top:50px;
}

#brd th { border-bottom:1px black solid; text-align:center;  font-size:18px;}
#brd td { border-bottom:1px black solid;  font-size:18px; }

k { color:#fff; text-decoration:none; }

a:link { color:#4f4f4f; text-decoration:none; }
a:visited { color:#4f4f4f; text-decoration:none; }
a:hover { color:#2788ff; text-decoration:none; }
a:focus { color:#2788ff; text-decoration:none; }
a:active { color:#2788ff; text-decoration:none; }


tr, td { padding:10px 0; }
</style>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="wrapper">
<h2>공지사항 보기 화면</h2>
<table width="1250" cellpadding="5" cellspacing="0" id="brd">
<tr>
	<th width="15%">제 목</th><td width="*"><%=article.getBn_title() %></td>
	<th width="15%">작성일</th><td width="10%" align="center"><%=article.getBn_date().substring(0, 11) %></td>
	<th width="15%">조회수</th><td width="10%" align="center"><%=article.getBn_read() %>

</tr>
<tr><th>내 용</th><td colspan="5"><%=article.getBn_content().replace("\r\n", "<br />") %></td></tr>
<tr></td>
</tr>
</table>
<table width="1200" cellpadding="5" cellspacing="0">
<tr><td height="20"></td></tr>
<tr><td align="center">
	<input type="button" value="목록으로" onclick="location.href='brd_list.ntc<%=args %>'" />
</td></tr>
</table>
</body>
</div>
<%@ include file="../footer.jsp" %>