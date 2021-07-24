<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%@include file ="../header.jsp" %>
<%
ArrayList<FreeInfo> freeList = (ArrayList<FreeInfo>)request.getAttribute("freeList");
FreePageInfo pageInfo = (FreePageInfo)request.getAttribute("pageInfo");

String args = "", schargs = "";
// 검색 관련 쿼리스트링 제작
if (pageInfo.getKeyword() == null)	schargs += "&keyword=";
else								schargs += "&keyword=" + pageInfo.getKeyword();

args = "?cpage=" + pageInfo.getCpage() + schargs;
%>
<style>
a:link { color:#000; text-decoration:none; }
a:visited { color:#000; text-decoration:none; }
a:hover { color:#2788ff; text-decoration:none; }
a:focus { color:#2788ff; text-decoration:none; }
a:active { color:#2788ff; text-decoration:none; }

#wrapper { margin:0 auto; width:1250px; background-color:#fff; padding-top:70px; }
#free-header { padding-left:520px; margin-bottom:50px; font-family: Georgia, "Malgun Gothic", serif; font-weight:bold; }
#schbox { padding-top:70px; }
.font-blue { color:#2788ff; font-weight:bold; }
.free-text { padding:0 140px; }
.free-notice { padding:70px 0; font-size:17px; font-weight:bold; line-height:35px;}
.free-title { height:17px; font-size:17px; font-weight:bold; }
.free-content { height:20px; vertical-align:top; }
#free-page { padding:40px 0; }
</style>
<script>
function chkLogin() {
// 질문 등록으로 이동시키는 함수로 비로그인 시 로그인 폼으로 이동시켜야 함
	var lnk = "free/free_in_form.free";
<% if (!isLogin) { %>
	location.href = "../login_form.jsp?url=" + lnk;
<% } else { %>
	location.href = "free_in_form.free";
<% } %>
}
</script>

<div id="wrapper">
<h2 id="free-header">질문과 답변</h2>
	<form name="frmSch" method="get">
	<input type="hidden" name="ord" value="<%=pageInfo.getOrd() %>" />
	<table width="1250" cellpadding="5">
	<tr id="schbox">
	<td colspan="4" align="center">
		<input type="text" name="keyword" value="<%=pageInfo.getKeyword() %>" size="50" placeholder="궁금한 것을 검색해보세요." />
		<input type="submit" value="검색"></td>
	</tr>
	</table>
	</form>
	<div class="free-text">
		<table width="960" cellpadding="5">
		<tr>
		<td>
			<div class="free-notice">
				<span class="font-blue">▶ </span>포인트 관련 안내 공지사항<br />
				<span class="font-blue">▶ </span>질문과 답변 게시판 활동 주의사항
			</div>
		</td>
		<td align="right"><input type="button" value="질문하기" onclick="chkLogin();" /></td>
		</tr>
		</table>
		<p style="width:1250px;" align="left">
		<!-- 정렬조건(등록 - 내림차순(기본값), 인기순 - 내림차순) -->
			<a href="free_list.free?cpage=1<%=schargs %>&ord=lastdated" <% if (pageInfo.getOrd().equals("lastdated")) { %>style="font-weight:bold;color:#2788ff;"<% } %>>최신순</a>&nbsp;
			<a href="free_list.free?cpage=1<%=schargs %>&ord=goodd" <% if (pageInfo.getOrd().equals("goodd")) { %>style="font-weight:bold;color:#2788ff;"<% } %>>인기순</a>&nbsp;
		</p>
		<table width="960" cellpadding="5">
		<tr><td colspan="2" height="40" valign="top"><hr /></td></tr>
		<% 
		if (freeList != null && freeList.size() > 0) {
		// 게시글 검색 결과가 있으면
			for (int i = 0 ; i < freeList.size() ; i++) {
				//System.out.println(freeList.size());
				FreeInfo fi = freeList.get(i);
				String lnk = "<a href=\"free_view.free" + args + "&idx=" + fi.getBf_idx() + "&ord=" + pageInfo.getOrd() + "\">";
		%>
		<tr>
		<td width="*" height="80" style="display:block; overflow:hidden; margin-top:30px;">
			<%=lnk %><strong><%=fi.getBf_title() %></strong><br />
			<%=fi.getBf_content() %></a><br />
		</td>
		<td width="20%" align="right" rowspan="2">
		<% if (fi.getBf_img1() != null && !fi.getBf_img1().equals("")) {%>
			<%=lnk %><img src="free_img/<%=fi.getBf_img1() %>" width="150" height="150" /></a>
		<% } %>
		</td>
		</tr>
		<tr>
		<td>
			<%=fi.getBf_nick() %>&nbsp;&nbsp;&nbsp;<%=fi.getBf_date().substring(0, 10).replace("-", ".") %>&nbsp;&nbsp;
			조회&nbsp;<%=fi.getBf_read() %>&nbsp;&nbsp;좋아요&nbsp;<%=fi.getBf_good() %>&nbsp;&nbsp;댓글&nbsp;<%=fi.getBf_reply() %>
		</td>
		</tr>
		<tr><td colspan="2" height="60"><hr /></td></tr>
		<%	
			}
		} else {
			out.println("<tr><th>검색 결과가 없습니다.</th></tr>");
		}
		%>
		</table>
	</div>
	<div id="free-page">
		<% // 정렬기준이 최신순일 때만 페이징 영역 보임
		if (freeList != null && freeList.size() > 0 && !pageInfo.getOrd().equals("goodd")) {
		// 게시글 검색 결과가 있으면
			args = "?ord=" + pageInfo.getOrd() + schargs;
			out.println("<p style=\"width:1250px\" align=\"center\">");
		
			if (pageInfo.getCpage() == 1) {	// 현재 페이지 번호가 1이면
				out.println("[&lt;&lt;]&nbsp;&nbsp;[&lt;]&nbsp;&nbsp;");
			} else {
				out.print("<a href='free_list.free" + args + "&cpage=1" + "'>");
				out.println("[&lt;&lt;]</a>&nbsp;&nbsp;");
				out.print("<a href='free_list.free" + args + "&cpage=" + (pageInfo.getCpage() - 1) + "'>");
				out.println("[&lt;]</a>&nbsp;&nbsp;");
			}	// 첫 페이지와 이전 페이지 링크
			
			for (int i = 1, k = pageInfo.getSpage() ; i <= pageInfo.getBsize() && k <= pageInfo.getEpage() ; i++, k++) {
				if (pageInfo.getCpage() == k) {	// 현재 페이지 번호일 경우 링크없이 강조만 함
					out.print("&nbsp;<strong>" + k + "</strong>&nbsp;");
				} else {
					out.print("&nbsp;<a href='free_list.free" + args + "&cpage=" + k + "'>");
					out.print(k + "</a>&nbsp;");
				}
			}
			
			if (pageInfo.getCpage() == pageInfo.getPcnt()) {	// 현재 페이지 번호가 마지막 페이지 번호이면
				out.println("&nbsp;&nbsp;[&gt;]&nbsp;&nbsp;[&gt;&gt;]");
			} else {
				out.println("&nbsp;&nbsp;<a href='free_list.free" + args + "&cpage=" + (pageInfo.getCpage() + 1) + "'>[&gt;]</a>");
				out.print("&nbsp;&nbsp;<a href='free_list.free" + args + "&cpage=" + pageInfo.getPcnt() + "'>");
				out.println("[&gt;&gt;]</a>");	
			}
		
		out.println("</p>");
		}
		%>
	</div>
</div>
<%@include file ="../footer.jsp" %>
