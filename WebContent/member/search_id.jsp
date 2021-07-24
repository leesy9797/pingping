<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
#msg1, #msg2, #code { display:none; }
</style>
<script>
function chkValue() {
	var frm = document.schId;
	var uname = frm.uname.value;
	var p2 = frm.p2.value;
	var p3 = frm.p3.value;
	
	if (uname == "") {
		alert("이름을 입력하세요.");	
		frm.uname.focus();
		return false;
	} 
	
	if (p2 == "") {
		alert("전화번호를 입력하세요.");
		frm.p2.focus();
		return false;
	} else if (p3 == "") {
		alert("전화번호를 입력하세요.");
		frm.p3.focus();
		return false;
	} 
	
	var msg1 = document.getElementById("msg1");	
	var msg2 = document.getElementById("msg2");	
	var code = document.getElementById("code");	
	alert('인증번호가 전송되었습니다.');
	msg1.style.display = "block";
	code.style.display = "block";
	msg2.style.display = "block";
	
	return true;
}

function showmsg() {
	var msg1 = document.getElementById("msg1");	
	var msg2 = document.getElementById("msg2");	
	var code = document.getElementById("code");	
	alert('인증번호가 전송되었습니다.');
	msg1.style.display = "block";
	code.style.display = "block";
	msg2.style.display = "block";
	
}
</script>
</head>
<body>
<div>
	<div class="cata">
		<input type="button" value="이메일 찾기" onclick="location.reload()" /> 
		<input type="button" value="비밀번호 재설정" onclick="location.href='reset_pwd_chk_email.jsp';" />
	</div>
	<div class="content">
		<form name="schId" action="search_id.mem" method="post" >
		<table width="500">
		<tr>
		<th>이름</th>
		<td><input type="text" name="uname" size="20" placeholder="이름을 입력하세요." value="홍길동" /></td>
		</tr>
		<tr>
		<th>전화번호</th>
		<td>
			<select name="p1">
				<option value="010">010</option>
				<option value="011">011</option>
				<option value="016">016</option>
				<option value="019">019</option>
			</select> - 
			<input type="text" name="p2" size="4" maxlength="4" value="1234" /> - 
			<input type="text" name="p3" size="4" maxlength="4" value="1234" />
			<input type="button" value="인증번호 전송" onclick="return chkValue();" />
		</td>
		</tr>
		<tr>
		<th colspan="3"><span id="msg1">입력하신 번호로 인증번호가 발송되었습니다.</span></th>
		</tr>
		<th></th>
		<td id="code">
			<input type="text" name="code" maxlength="6" disabled="disabled" />
			<input type="submit" value="확인" />
		</td>
		</table>
		</form>
		<span id="msg2">* 인증번호를 받지 못하셨다면, <br />
		 입력하신 이름과 전화번호가 회원정보와 일치하는지 확인해주세요.</span>
	</div>
</div>
</body>
</html>