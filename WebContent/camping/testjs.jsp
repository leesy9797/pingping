<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%@ include file="../_inc/incHead.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
body { margin:0; font-size:13px; }
.frmForm { width:1250px; margin:0 auto; }
.submit { width:1000px; text-align:right; }
.upload {
	border:1px blue solid; width:400px; height:100px;
	vertical-align:middle;
}
.UploadedImg {
	text-align:center;
}
.uploadInfo { width:400px; height:100px; }
#crcontent {
	width:400px; height:200px; overflow:auto; vertical-align:top;
}

#crkeyword {
	width:400px; height:50px; overflow:auto; vertical-align:top;
}
#img2, #img3, #img4, #img5 { display:none; }
.imgbtn { display:none; }
.addimg { display:none; }
/* #imgbtn2, #imgbtn3, #imgbtn4, #imgbtn5 { display:none; } */
</style>
<script type="text/javascript" src="jquery-3.6.0.js" charset="euc-kr"></script>
<script>
function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        
        reader.onload = function (e) {
            $('#UploadedImg').attr('src', e.target.result);
        }

        reader.readAsDataURL(input.files[0]);
    }
}

function nextImg(num) {
	var arrImg = document.frmForm.img;			// 이미지 파일 첨부
	var arrImgbtn = document.frmForm.imgbtn;	// 이미지 변경/삭제/순서변경 버튼
	var addimg = document.frmForm.addimg;		// 이미지 추가하기 버튼
	for (var i = 0; i < 4; i++) {
		if (arrImg[i].value != null && arrImg[i].value != "") {
			addimg.style.display = "block";
			addImgbtn[i].style.display = "block";
		} 
	}
		
}

function showAddImg() {
	var arrImg = document.frmForm.img;
	for (var i = 0; i < 4; i++) {
		if (arrImg[i].value != null && arrImg[i].value != "") {
			arrImg[i+1].style.display = "block";
		} 
	}
}

function showAddImg() {
	var arrImg = document.frmForm.img;
	for (var i = 0; i < 4; i++) {
		if (arrImg[i].value != null && arrImg[i].value != "") {
			arrImg[i+1].style.visibility = "visible";
		} 
	}
}

function delImg(val) {
	var arrImg = document.frmForm.img;
	var x = "img" + val;
	//alert(val);
	//alert(x);
	for (var i = val; i < 4; i++) {
		if (arrImg[val].value == null || arrImg[val].value == "") {
			var img = document.getElementById(x);
			img.style.visibility = "visible";
			alert(img);
			
			//arrImg[val].value = arrImg[i+1].value;
			//arrImg[i].style.visibility = "hidden";
		}
		
	}
		
}
</script>
</head>
<body>
<h2 align="center">캠핑후기 등록</h2>
<form name="frmForm" class="frmForm" action="camp_proc.camp" method="post" enctype="multipart/form-data">
<input type="hidden" name="wtype" value="in" />
<p class="submit" style="width:1250px; text-align:right;">
	<input type="submit" value="게시글  등록 " />
	&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="reset" value="다시 입력" />
</p>
<table width="1250" cellpadding="5" id="brd" cellspacing="0">
<tr>
<td width="200">
이미지 미리보기 영역
<div id="imagePreview">
	<img id="img" />
</div>
</td>
<td width="500">
<!-- 사진영역(width=750px) 시작 -->
<table width="100%" cellpadding="5">
<tr>
<img id="UploadedImg" src="../img/photo-camera-vintage.png" width="200" height="200" /><br />
* 캠핑후기 사진은 최대 10장까지 업로드가 가능합니다.<hr />

<input type="button" name="addimg" id="addimg" value="이미지 추가하기" onclick="showAddImg();" /><br />
<form name="frm" action="" method="get" >
<input type="file" id="img1" name="img" value="사진올리기1" onchange="delImg('1');" />
<input type="file" id="img2" name="img" style="visibility:hidden;" value="사진올리기2" onchange="delImg('2');" />
<input type="file" id="img3" name="img" style="visibility:hidden;" value="사진올리기3" onchange="delImg('3');" />
<input type="file" id="img4" name="img" style="visibility:hidden;" value="사진올리기4" onchange="delImg('4');" />
<input type="file" id="img5" name="img" style="visibility:hidden;" value="사진올리기5" onchange="delImg('5');" />
<br />
</form>
<div class="uploadInfo">
	<p>* 이미지 파일은 각각 최대 1MB, 총 10개까지 업로드 가능합니다.</p>
	<p>* 이미지 파일명이 한글, 숫자, 영문이 아닌 다른 언어일 경우 파일이 업로드되지 않거나 깨질 수 있습니다.</p>
	<p style="color:red;">* 저작권 등 다른 사람의 권리를 침해하거나 명예를 훼손하는 파일은 운영 원칙 및 관계 법률에 의해 제재를 받을 수 있습니다.</p>
</div>


</tr>
</table>
<!-- 사진영역(width:750px) 종료 -->
</td>
<td>
<!-- 후기작성영역(width:450px) 시작 -->
<table width="*" cellpadding="5" >
<tr>
<input type="text" name="crcontent" placeholder="사진에 대해 설명해주세요." id="crcontent" />
</tr>
<tr>
<input type="text" name="crkeyword" maxlegnth="200" placeholder="키워드를 입력해주세요.&#13;&#10;키워드는 띄어쓰기없이 입력해주세요.&#13;&#10;예) #텐트#캠핑장" id="crkeyword" />
</tr>
<tr>
<input type="text" name="crpdtname" size="45" maxlegnth="200" placeholder="상품명으로 검색하세요." id="crpdtname" />
&nbsp;
<input type="button" value="검색" onclick="" />
</tr>
</table>
<!-- 사진영역(width=750px) 종료 -->
</td>
</tr>
</table>
</form>
</body>
</html>