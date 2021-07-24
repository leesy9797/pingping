<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%
MemberInfo memberInfo = (MemberInfo)request.getAttribute("memberInfo");
System.out.println("set_pwd : " + memberInfo.getMi_email());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>
function chkPwd() {
	var frm = document.setPwd
	var pwd = frm.pwd.value;
	var pwd2 = frm.pwd2.value;
	var msg = document.getElementById("msg");
	var msg2 = document.getElementById("msg2");
	
	if (pwd == "") {
		msg.innerHTML = "비밀번호를 입력하세요.";
		pwd.focus();
		return false;
		
	} else if (pwd.length < 4) {
		msg.innerHTML = "비밀번호는 4~20자 이내로 입력하세요.";
		pwd.select();
		return false;
		
	} else if (pwd.length >= 8 && pwd != pwd2) {
		msg.innerHTML = "사용가능한 비밀번호입니다.";
		msg2.innerHTML = "비밀번호와 비밀번호 확인이 서로 다릅니다.";
		return true;
		
	}
	
}

function chkPwd2() {
	var frm = document.setPwd
	var pwd = frm.pwd.value;
	var pwd2 = frm.pwd2.value;
	var msg2 = document.getElementById("msg2");
	
	if (pwd2 == "") {
		msg2.innerHTML = "비밀번호를 입력하세요.";
		pwd2.focus();
		return false;
		
	} else if (pwd2.length < 4) {
		msg2.innerHTML = "비밀번호는 4~20자 이내로 입력하세요.";
		pwd2.select();
		return false;
		
	} else if (pwd != pwd2) {
		msg2.innerHTML = "비밀번호와 비밀번호 확인이 서로 다릅니다.";
		pwd2.value = "";
		pwd2.focus();
		return false;
		
	} else if (pwd == pwd2) {
		msg2.innerHTML = "비밀번호와 비밀번호 확인이 같습니다.";
		return true;
		
	}
}
</script>
</head>
<body>
<h2>비밀번호 재설정</h2>
<form name="setPwd" action="set_pwd.mem" method="post">
<input type="hidden" name="email" value="<%=memberInfo.getMi_email() %>" />
새 비밀번호<br />
<input type="password" name="pwd" maxlength="20" onkeyup="chkPwd();" /><br />
<span id="msg">* 비밀번호는 8자 이상 입력해야 합니다.</span><br /><br />
새 비밀번호 확인<br />
<input type="password" name="pwd2" maxlength="20" onkeyup="chkPwd2();" /><br />
<span id="msg2">* 비밀번호는 8자 이상 입력해야 합니다.</span><br /><br />
<input type="submit" value="비밀번호 재설정" />
</form>
</body>
</html>