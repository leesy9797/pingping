<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%@include file ="./header.jsp" %>
<%
if (memberInfo != null) {
%>
<script>
	alert("잘못된 경로로 들어오셨습니다.");
	location.href = "history.back();";
</script>
<%
	out.close();	// 파일 실행을 강제로 종료시킴
}

request.setCharacterEncoding("utf-8");
String url = request.getParameter("url");
// 로그인 후 이동할 페이지 주소(없는 경우도 있음)

if (url == null) {
	url = "";
} // url 값이 없으면 빈 문자열로 변환하여 사용


//이메일 저장
String tmpEmail = "", cook = request.getHeader("Cookie");
if (cook != null) {
	Cookie[] cookies = request.getCookies();
	for (int i = 0 ; i < cookies.length ; i++) {
		if (cookies[i].getName().equals("saveEmail")) {
			tmpEmail = cookies[i].getValue();
		}
	}
}
%>	
<script>
function chkValue(frm) {
	var email = frm.email.value;
	var pwd = frm.pwd.value;

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
	
	if (pwd == "") {
		alert("비밀번호를 입력하세요.");
		frm.pwd.focus();
		return false;
	} else if (pwd.length < 4) {
		alert("비밀번호는 4~20자 이내로 입력하세요.");
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
  <form form name="frmLogin" action="login" method="post" onsubmit="return chkValue(this); ">
    <input type="hidden" name="url" value="<%=url %>" />
    <img class="mb-4" src="./img/pingping_blue.png" alt="" width="220" height="120">
    

    <div class="form-floating">
      <input type="email" class="form-control" id="floatingInput email" name="email" maxlength="20" value="test1@gmail.com" >
      <label for="floatingInput email">이메일</label>
    </div>
    <div class="form-floating">
      <input type="password" class="form-control" id="floatingPassword pwd" name="pwd" maxlength="20" value="1111" />
      <label for="floatingPassword password">비밀번호</label>
    </div>
	<div id="save-email">
		<input type="checkbox" name="isSave" value="y" id="semail" <% if (!tmpEmail.equals("")) { %>checked="checked"<% } %> /><label for="semail">이메일 저장</label>
	</div><br />
    <button class="w-100 btn btn-lg btn-primary" type="submit">로그인</button><br /><br />
    <button type="button" class="btn btn-primary" onclick="location.href='member/search_id.jsp';">이메일 찾기</button>
    <button type="button" class="btn btn-primary" onclick="location.href='member/reset_pwd_chk_email.jsp';" >비밀번호 재설정</button>
    <p class="mt-5 mb-3 text-muted">© 2017–2021</p>
  </form>
</main>
<br /><br /><br /><br />
<%@ include file="./footer.jsp" %>
