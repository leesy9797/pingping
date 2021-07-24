<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%@include file ="../header.jsp" %>
<%
if (!isLogin) {
	out.println("<script>");
	out.println("alert('로그인 후 사용하실 수 있습니다.');");
	out.println("location.href='../login_form.jsp';");
	out.println("</script>");
	out.close();
}

request.setCharacterEncoding("utf-8");
ArrayList<FollowInfo> followingList = (ArrayList<FollowInfo>)request.getAttribute("followingList");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
a:link { color:#4f4f4f; text-decoration:none; }
a:visited { color:#4f4f4f; text-decoration:none; }
a:hover { color:#2788ff; text-decoration:none; }
a:focus { color:#2788ff; text-decoration:none; }
a:active { color:#2788ff; text-decoration:none; }

#wrapper { margin:0 auto; width:1250px; padding-top:70px; }
#following { color:#2788ff; font-weight:bold; }
</style>

<div id="wrapper">
	<h2>팔로잉 목록</h2>
	<table width="1250" cellpadding="5" align="center">
	<tr>
	<td width="20%">
	<% if (memberInfo.getMi_img() == null || memberInfo.getMi_img().equals("")) { %>
		<img src="../img/user.png" width="110" height="110" />
	<% } else {
		memberInfo.getMi_img();
	} %>
	</td>
	<td width="*">
		<%=memberInfo.getMi_nick() %><br /><br />
		팔로워 <%=memberInfo.getMi_follower() %> ㅣ 팔로잉 <%=memberInfo.getMi_following() %><br /><br />
		<input type="button" value="설정" style="width:50px;" onclick="location.href='mypage.mem'"/> (회원정보 수정으로 이동)
	</td>
	</tr>
	<tr><td colspan="3" height="30"><hr /></td></tr>
	<tr>
	<td colspan="2" align="center">
		<a href="follower_list.mem">팔로워</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="following_list.mem" id="following">팔로잉</a>
	</td>
	</tr>
	<tr><td colspan="3" height="30"><hr /></td></tr>
	<tr><td colspan="3" height="30"></td></tr>
	<% 
	if (followingList != null && followingList.size() > 0) {
	// 팔로잉 검색 결과가 있으면
		for (int i = 0 ; i < followingList.size() ; i++) {
			System.out.println(followingList.size());
			FollowInfo fi = followingList.get(i);
			// 해당 회원의 스크랩북 페이지로 이동
			String lnk = "<a href=\"\">";
	%>
	<tr>
	<td align="right">
	<% if (fi.getMi_img() == null || fi.getMi_img().equals("")) { %>
		<img src="../img/user.png" width="25" height="25" />
	<% } else {
		fi.getMi_img();
	} %>
	</td>
	<td>
		&nbsp;<%=fi.getMi_nick() %>
	</td>
	</tr>
	<tr><td colspan="3" height="50"><hr width="1250" /></td></tr>
	<%	
		}
	} else {
		out.println("<tr align='center'><th colspan='3'>팔로잉 중인 회원이 없습니다.</th></tr>");
	}
	%>
	</table>
</div>
<br /><br /><br /><br /><br /><br />
<%@ include file="../footer.jsp" %>
