<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%@include file ="../header.jsp" %>
<%
// 로그인 후 로그인 화면으로 넘어오기 전 화면으로 돌아가기
if (!isLogin) {
	out.println("<script>");
	out.println("alert('로그인 후  이용하실 수 있습니다.');");
	out.println("location.href='../login_form.jsp';");
	out.println("</script>");
	out.close();
}
%>

<style>
.fontRed { color:red; font-weight:bold; font-size:80%; }
a:link { color:#4f4f4f; text-decoration:none; }
a:visited { color:#4f4f4f; text-decoration:none; }
a:hover { color:#2788ff; text-decoration:none; }
a:focus { color:#2788ff; text-decoration:none; }
a:active { color:#2788ff; text-decoration:none; }

#wrapper { margin:0 auto; width:1250px; background-color:#fff; }
</style>
<script>
// 이미지1 등록 취소했을 경우 이미지2,3 등록 버튼 안보이게 어떻게 하는지
function chkImg1(img) {
	var img2 = document.getElementById("img2");	
	var img3 = document.getElementById("img3");	
	img2.style.visibility = "visible";
	if (img == null || img.equals("")) {
		img2.style.visibility = "hidden";
		img3.style.visibility = "hidden";
	}
}

function chkImg2(img) {
	var img3 = document.getElementById("img3");	
	img3.style.visibility = "visible";
	if (img == null || img.equals("")) {
		img3.style.visibility = "hidden";
	}
}

// 이미지 파일인지 검사

// 내용 입력했는지 검사
function chkValue(frm) {
	var title = frm.title.value;
	var msg = "";
	if (title.length < 7) {
		msg = "<span class='fontRed'>제목은 7자 이상 입력해주세요.</span>";
		frm.title.focus();
		document.getElementById("titleMsg").innerHTML = msg;
		return false;
	}
	
	var content = frm.content.value;
	if (content.length < 1) {
		msg = "<span class='fontRed'>질문 내용을 입력해주세요.</span>";
		frm.content.focus();
		document.getElementById("contentMsg").innerHTML = msg;
		return false;
	}
}

function chkTitle(title) {
	var msg = "";
	if (title.length >= 0) {
		if (title.length < 7) {
			msg = "<span class='fontRed'>제목은 7자 이상 입력해주세요.</span>";
		} else {
			msg = "";
		}
		document.getElementById("titleMsg").innerHTML = msg;
	}
}

function chkContent(content) {
	var msg = "";
	if (content.length < 1) {
		msg = "<span class='fontRed'>질문 내용을 입력해주세요.</span>";
	} else {
		msg = "";
	}
	document.getElementById("contentMsg").innerHTML = msg;
}
</script>

<div id="wrapper">
	<h2>질문하기</h2><hr /><br />
	<form name="frmFree" action="free_proc.free" method="post" enctype="multipart/form-data" onsubmit="return chkValue(this); chkImg();">
	<input type="hidden" name="wtype" value="in" />
	<table width="1250" cellpadding="5">
	<tr height="50" valign="top" align="center">
	<td>
		<input type="text" name="title" size="120" maxlength="100" onkeyup="chkTitle(this.value);" placeholder="제목을 입력해주세요." />
		<br /><span id="titleMsg"></span>
	</td>
	</tr>
	<tr height="200" valign="top" align="center">
	<td>
		<textarea cols="122" rows="10" name="content" onkeyup="chkContent(this.value);" placeholder="질문 내용을 입력해주세요.&#13;&#10;참고가 되는 사진을 공유해주시면 더 좋은 답변을 얻을 수 있습니다.&#13;&#10;※답변자에게는 포인트를 지급하며, 답변이 달린 질문글은 삭제할 수 없습니다.&#13;&#10;※캠핑과 관련 없는 질문은 숨김 및 삭제될 수 있습니다."></textarea>
		<br /><span id="contentMsg"></span>
	</td>
	</tr>
	<tr>
	<td align="right" style="padding-right:50px; padding-top:30px;">
		<input type="file" name="img1" id="img1" accept="image/*" onchange="chkImg1(this.value)" /><br />
		<input type="file" name="img2" id="img2" accept="image/*" onchange="chkImg2(this.value)" style="visibility:hidden;" /><br />
		<input type="file" name="img3" id="img3" accept="image/*" style="visibility:hidden;" /><br />
	</td>
	</tr>
	<tr><td colspan="4" align="center">
		<input type="submit" value="질문 등록" />
	</td></tr>
	</table>
	</form>
</div>
<div style="height:300px;"></div>
<%@include file ="../footer.jsp" %>
