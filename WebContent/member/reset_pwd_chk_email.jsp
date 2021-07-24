<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>
function chkValue(frm) {
	var email = frm.email.value;
	var n1 = email.indexOf('@');
	var n2 = email.indexOf('.');
	
	if (email == "") {
		alert("이메일을 입력하세요.");	
		frm.email.focus();
		return false;
		
	} else if (n1 == -1 || n2 == -1 || n1 > n2) {
		alert("올바른 이메일 형식이 아닙니다.");
		frm.email.select();
		return false;
	}
	
	return true;
}
</script>
</head>
<body>
<form name="chkEmail" action="chk_email.mem" method="post" onsubmit="return chkValue(this); ">
<div>
	<div class="cata">
		<input type="button" value="이메일 찾기" onclick="location.href='search_id.jsp';" /> 
		<input type="button" value="비밀번호 재설정" onclick="location.href='reset_pwd_chk_email.jsp';" />
	</div>
	<div>
	비밀번호를 찾으려는 이메일을 입력해주세요.<br />
	이메일 : <input type="text" name="email" size="20" placeholder="이메일을 입력해주세요." value="test1@gmail.com" /><br />
	<input type="submit" value="확인" /> 
	</div>
</div>
</form>
</body>
</html>