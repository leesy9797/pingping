<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%@include file ="../admin_header.jsp" %>
<script>
function chkVal(frm) {
	var uid = frm.uid.value;
	var pwd = frm.pwd.value;
	
	if (uid == "") {
		alert("아이디를 입력하세요.");
		frm.uid.focus();
		return false;
	
	}
	if (pwd == "") {
		alert("비밀번호를 입력하세요.");   
		frm.pwd.focus();   
		return false;
	
	} else if (pwd.length < 4) {
		alert("비밀번호는 4자 이상 입력해야 합니다.");  
		frm.pwd.select(); 
		return false;
	
	}
	
	return true;
}

</script> 
<style>
  .bd-placeholder-img {
    font-size: 1.125rem;
    text-anchor: middle;
    -webkit-user-select: none;
    -moz-user-select: none;
    user-select: none;
  }

  @media (min-width: 768px) {
    .bd-placeholder-img-lg {
      font-size: 3.5rem;
    }
  }
      .footer {

    position: absolute;

    left: 0;

    bottom: 0;

    width: 100%;

	padding: 15px 0;
	}
</style>
	

        <!-- Custom styles for this template --> 
   <br /><br /><br />
<main class="form-signin text-center">
  <form form name="frmLogin" action="../adminLogin" method="post" onsubmit="return chkValue(this); ">
    <img class="mb-4" src="../img/pingping_blue.png" alt="" width="220" height="120">
    <div class="form-floating">
		<input type="text" class="form-control" id="floatingInput email" name="uid" id="uid" maxlength="20" value="admin1" />
		<label for="floatingInput uid">이메일</label>
    </div>
    <div class="form-floating">
      <input type="password" class="form-control" id="floatingPassword pwd" name="pwd" maxlength="20" value="1111" />
      <label for="floatingPassword password">비밀번호</label>
    </div>

    <button class="w-100 btn btn-lg btn-primary" type="submit">로그인</button><br /><br />
  </form>
</main>
<br /><br /><br /><br />
</body>
</html>
