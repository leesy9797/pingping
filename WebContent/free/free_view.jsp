<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%@include file ="../header.jsp" %>
<%
FreeInfo freeInfo = (FreeInfo)request.getAttribute("freeInfo");
FreePageInfo pageInfo = (FreePageInfo)request.getAttribute("pageInfo");

String args = "", schargs = "";
if (pageInfo.getKeyword() == null)	schargs += "&keyword=";
else								schargs += "&keyword=" + pageInfo.getKeyword();

args = "?cpage=" + pageInfo.getCpage() + schargs;

%>
<style>
#wrapper { margin:0 auto; width:1250px; background-color:#fff; padding-top:50px; }
#free-inner { margin:0 auto; }
#top { padding-left:50px;  }
.font-blue { font-family: Georgia, "Malgun Gothic", serif; color:#2788ff; font-size:15px; font-weight:bold; }
.free-text { padding:0 140px; }
.free-title { height:17px; font-size:17px; font-weight:bold; }
.free-content { height:20px; vertical-align:top; }

a:link { color:#000; text-decoration:none; }
a:visited { color:#000; text-decoration:none; }
a:hover { color:#2788ff; text-decoration:none; }
a:focus { color:#2788ff; text-decoration:none; }
a:active { color:#2788ff; text-decoration:none; }

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
function chkDel() {
	if (confirm("정말 삭제하시겠습까?"))	return true;
	else	return false;
}

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
	<div id="free-inner">
	<div id="top">
	<h2 class="font-blue">질문과 답변</h2>
		<table width="1250" cellpadding="0" cellspacing="0" border="0">
		<tr width="100%">
		<td width="55%">
		<strong><%=freeInfo.getBf_title() %></strong><br />
		</td></tr>
		<% if (isLogin && freeInfo.getMi_email().equals(memberInfo.getMi_email())) { // 작성자가 로그인한 회원정보와 일치할 때만 수정/삭제 버튼 보임 %>
		<tr><td><br />
		<form action="free_proc.free?wtype=del&idx=<%=freeInfo.getBf_idx() %>" method="post" enctype="multipart/form-data" onsubmit="return chkDel();">
		<input type="button" value="수정" onclick="location.href='free_up_form.free<%=args %>&idx=<%=freeInfo.getBf_idx() %>&ord=<%=pageInfo.getOrd() %>'" />&nbsp;
		<input type="submit" value="삭제" /><br /><br />
		</form>
		</td></tr>
		<% } %></div>
		<tr><td>
		<%=freeInfo.getBf_content().replace("\r\n", "<br />") %><br /><br />
		</td></tr>
		<% if (freeInfo.getBf_img1() != null && !freeInfo.getBf_img1().equals("")) { %>
		<tr>
		<td align="center"><img src="free_img/<%=freeInfo.getBf_img1() %>" width="500" height="500" /></td>
		</tr>
		<% } %>
		<% if (freeInfo.getBf_img2() != null && !freeInfo.getBf_img2().equals("")) { %>
		<tr>
		<td align="center"><img src="free_img/<%=freeInfo.getBf_img2() %>" width="500" height="500" /></td>
		</tr>
		<% } %>
		<% if (freeInfo.getBf_img3() != null && !freeInfo.getBf_img3().equals("")) { %>
		<tr>
		<td align="center"><img src="free_img/<%=freeInfo.getBf_img3() %>" width="500" height="500" /></td>
		</tr>
		<% } %>
		<tr><td><br />
		<%=freeInfo.getBf_date().substring(0, 10) %> · 조회 <%=freeInfo.getBf_read() %> · 좋아요 <%=freeInfo.getBf_good() %>
		</td></tr>
		</table>
		<div>
		<%=freeInfo.getBf_nick() %><br />
		<input type="button" value="질문하러가기" onclick="chkLogin();" />
		</div>
	</div>
</div>
<%@include file ="../footer.jsp" %>
