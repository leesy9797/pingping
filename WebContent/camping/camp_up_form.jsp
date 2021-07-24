<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%@ include file="../_inc/incHead.jsp" %>
<%
if (!isLogin) {
	out.println("<script>");
	out.println("alert('로그인 후  이용하실 수 있습니다.');");
	out.println("location.href='../login_form.jsp?url='camp_proc.camp';");
	out.println("</script>");
	out.close();
}

System.out.println("여기는 camp_up_form.jsp");

CampingInfo campingInfo = (CampingInfo)request.getAttribute("campingInfo");
CampPageInfo pageInfo = (CampPageInfo)request.getAttribute("pageInfo");

String imgs = campingInfo.getCr_imgs();
System.out.println(imgs);
String[] arrImg = imgs.split("/");
System.out.println(arrImg[0]);
System.out.println(arrImg[1]);
System.out.println(arrImg[2]);

int imgcnt = arrImg.length;
System.out.println(imgcnt);



%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
<style>
body { margin:0 auto; }
.outerBox {
	margin:0 auto; border:1px black solid; width:1250px; height:1000px;
	align:center;
}
.btnBox {
	border:1px black solid; width:1250px; height:50px;
	padding:5px 5px; align:right;
}
.btn {
	align:right; width:130px; height:40px; font-weight:bold;
}
.left {
	border:1px black solid; float:left; width:200px; height:1000px;
	padding:5px 5px;
}
.center {
	border:1px black solid; float:left; width:700px; height:1000px;
	padding:5px 5px; align:center;
}
.right {
	border:1px black solid; float:left; width:348; height:1000px;
	padding:5px 5px;
}
#preview {
	width:200px; height:1000px;
}
#addimg {
	border:1px black solid; width:95%; height:40px;
	margin:5px 5px;
}
#preimgBox1 {
	border:1px black solid; width:150px; height:150px; 
	margin:5px 5px;
}
.imginfo {
	border:1px black solid; width:95%; height:150px; 
	margin:5px 5px;
}
</style>
<script>
function changeImg(img, num) {	// 파일 컨트롤에  이미지를 넣었을 때 이미지 미리보기 
	var nextNum = (num + 1);
	var cnt = document.getElementById("cnt").innerHTML;
	alert(cnt);
	var tmp = "preimg"+num;
	alert(tmp);
	if (tmp == "preimg31") {
		alert('사진 재업로드 횟수(30회)를 초과했습니다. 처음부터 다시 시도해주세요.');

		return;
	}
	
	if (img.files && img.files[0]) {	// i번째 파일 컨트롤에 이미지가 들어있으면
		var reader = new FileReader();
		if (document.getElementById('preimg' + num).style.display == "block") {
			reader.onload = function(e) {
			  document.getElementById('preimg' + num).style.display = "block";	// i번째 이미지 미리보기 
			  document.getElementById('preimg' + num).src = e.target.result;
			  //document.getElementById('img1src').innerHTML = document.getElementById('preimg' + num).src;
			  //document.getElementById('img' + nextNum).style.display = "block";
			};
			
		} else {
			if (num == 1) {
				document.getElementById('preimg' + num).style.display = "block";
			}
			reader.onload = function(e) {
				document.getElementById('preimg' + num).style.display = "block";	// i번째 이미지 미리보기 
				document.getElementById('preimg' + num).src = e.target.result;
				//document.getElementById('img1src').innerHTML = document.getElementById('preimg' + num).src;
				document.getElementById('cnt').innerHTML = cnt / 1 + 1;
				//document.getElementById('img' + nextNum).style.display = "block";
			};
		}
		reader.readAsDataURL(img.files[0]);
		
	} else {	// i번째 파일 컨트롤에 이미지가 삭제되면
		document.getElementById('preimg' + num).src = "";
		document.getElementById('preimg' + num).style.display = "none";
		// 미리보기 지우고, 파일 컨트롤 1개만 보여주기
		
		 <% for (int j = 5 ; j > 0 ; j--) { 	// 5 4 3 2 1 %>
			if (document.getElementById('preimg' + <%=j %>).style.display == "none") {
				document.getElementById('img' + <%=j %>).style.display = "none";

				document.getElementById('cnt').innerHTML = cnt / 1 - 1;
			}		
		<% } %>
	}
}
</script>
<script>
function showAddImg() {	// 이미지 추가하기 버튼 클릭시
	var cnt = document.getElementById("cnt").innerHTML;
	//alert(cnt);
	
	if (cnt == 10) {
		alert('사진은 최대 10장까지만 업로드가 가능합니다.');
	} else if (cnt < 10) {	
		for (var i = 29 ; i > 0 ; i--) {
			var tmp = "preimg" + i;
			var tmp2 = "img" + (i+1);
			if (document.getElementById(tmp).style.display == "block") {	// i번째 사진 미리보기가 있으면
				document.getElementById(tmp2).style.display = "block";		// i+1번째 이미지 추가 컨트롤 활성화
				document.getElementById(tmp2).click();
				return;
		
			}
			if (i == 1) {
				document.getElementById("img1").style.display = "block";
				document.getElementById("img1").click();
				return;
			}
		}
		
	}
}
</script>
<script>
function delImg(num) {
	var cnt = document.getElementById("cnt").innerHTML;
	if(confirm("사진을 삭제하시겠습니까?")) {
		var delimg = "del" + num;
		alert(delimg);
		document.getElementById(delimg).style.display = "none";
		document.getElementById(delimg).src = "";
		
		var preimg = "preimg" + num;
		alert(preimg);
		document.getElementById(preimg).style.display = "none";
		document.getElementById(preimg).src = "";
		
		var img = "img" + num;
		document.getElementById(img).style.display = "none";
		document.getElementById(img).src = "";
		
		document.getElementById('cnt').innerHTML = cnt / 1 - 1;
	}	
}
</script>
<script>
function showDelImg(num) {
	//alert(num);
	var preimg = "preimg" + num;
	//alert(preimg); // preimg1
	document.getElementById(preimg).style.display = "none";	// 미리보기 숨기고

	var delimg = "del" + num;
	//alert(delimg); //del1
	document.getElementById(delimg).style.display = "block";	// x 사진 띄우기
}
</script>
<script>
function showOriImg(num) {
	var delimg = "del" + num;
	//alert(delimg); //del1
	document.getElementById(delimg).style.display = "none";	// x 사진 띄우기
	
	var preimg = "preimg" + num;
	//alert(preimg); // preimg1
	document.getElementById(preimg).style.display = "block";	// 미리보기 숨기고
}
</script>
</head>
<body>
<h2 align="center">캠핑후기 등록</h2>
<form name="frmCamp" action="camp_proc.camp" method="post" enctype="multipart/form-data">
<input type="hidden" name="wtype" value="up" />
<input type="hidden" name="cpage" value="<%=pageInfo.getCpage() %>" />
<input type="hidden" name="psize" value="<%=pageInfo.getPsize() %>" />
<input type="hidden" name="ord" value="<%=pageInfo.getOrd() %>" />
<input type="hidden" name="oldImg1" value="<%=arrImg[0] %>" />
<input type="hidden" name="oldImg2" value="<%=arrImg[1] %>" />
<input type="hidden" name="oldImg3" value="<%=arrImg[2] %>" />
<input type="hidden" name="oldImg4" value="<%=arrImg[3] %>" />
<div class="outerBox">
<div class="btnBox" align="right">
	<input class="btn" type="submit" value="캠핑후기 등록" />
	&nbsp;&nbsp;&nbsp;&nbsp;
	<input class="btn" type="reset" value="다시 입력" />
</div>
<div class="left">
	<div class="preview">
		<% for (int i = 1 ; i <= imgcnt ; i++) { %>
		<img id="preimg<%=i %>" name="preimg<%=i %>" src="camp_img/<%=arrImg[i-1] %>" style="display:block;" width="180" height="180" onmouseover="showDelImg('<%=i %>');" onmouseout="showOriImg('<%=i %>');" />
		<span id="del<%=i %>" style="display:none;"><img src="camp_img/cancel-button.png" name="preimg" width="180" height="180" onclick="delImg('<%=i %>');" /></span>
		<% } %>
		<% for (int i = imgcnt + 1 ; i < 32; i ++) { %>
		<img id="preimg<%=i %>" name="preimg<%=i %>" style="display:none;" width="180" height="180" onmouseover="showDelImg('<%=i %>');" onmouseout="showOriImg('<%=i %>');" />
		<span id="del<%=i %>" style="display:none;"><img src="camp_img/cancel-button.png" name="preimg" width="180" height="180" onclick="delImg('<%=i %>');" /></span>
		<% } %>
	</div>
</div>
<div class="center">
	<div class="">
		<span id="cnt"><%=imgcnt %></span>
		<% for (int i = 1 ; i <= imgcnt ; i++) { %>
		<input type="file" id="img<%=i %>" name="img<%=i %>" src="camp_img/<%=arrImg[i-1] %>" accept="image/*" style="display:block;" value="<%=i %>" onchange="changeImg(this, <%=i %>);" />
		<% } %>
		<% for (int i = imgcnt ; i < 32; i ++) { // 4부터  %>		
		<input type="file" id="img<%=i %>" name="img<%=i %>" accept="image/*" style="display:none;" value="<%=i %>" onchange="changeImg(this, <%=i %>);" />
		<% } %>
		<button type="button" class="btn btn-primary" id="addimg" onclick="showAddImg();">이미지 추가하기</button>
	</div>
	<div class="imginfo">
	* 이미지 파일은 각 1MB 이내, 최대 10장까지 업로드가 가능합니다.<br />
	* 이미지 파일명이 한글, 숫자, 영문이 아닌 다른 언어일 경우 파일이 업로드되지 않거나 깨질 수 있습니다.<br />
	<font color="red">* 저작권 등 다른 사람의 권리를 침해하거나 명예를 훼손하는 파일은 운영 원칙 및 관계 법률에 의해 제재를 받을 수 있습니다.</font><br />
	</div>
</div>

<div class="right">
<textarea cols="40" rows="10" name="content" placeholder="사진에 대해 설명해주세요."><%=campingInfo.getCr_content() %>
</textarea>
<br />
<textarea cols="40" rows="5" name="keyword" placeholder="키워드를 입력해주세요.&#13;&#10;키워드는 띄어쓰기없이 입력해주세요.&#13;&#10;예)#감성캠핑#가평캠핑"><%=campingInfo.getCr_keyword() %></textarea>
<br /><br />
<iframe>아이프레임 영역</iframe>
</div>

</div>
</form>
</body>
</html>