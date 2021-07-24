<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
MemberInfo memberInfo = (MemberInfo)request.getAttribute("memberInfo");
//System.out.println(memberInfo.getMi_email());

if (memberInfo == null) {
	out.println("<script>");
	out.println("alert('고객님이 입력하신 정보와 일치하는 이메일이 존재하지 않습니다.\n다시 한 번 확인해주세요.');");
	out.println("location.href='search_id.jsp';");
	out.println("</script>");
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>아이디 찾기 성공</h2>
<div>
	<div class="cata">
		<input type="button" value="이메일 찾기" onclick="location.href='search_id.jsp';" /> 
		<input type="button" value="비밀번호 재설정" onclick="location.href='reset_pwd_chk_email.jsp';" />
	</div>
	<div class="content">
		고객님의 정보와 일치하는 이메일입니다.<br />
		<%=memberInfo.getMi_email() %><br />
	</div>
	<input type=button" value="로그인하기" onclick="location.href='../login_form.jsp';" />
</div>
</body>
</html>