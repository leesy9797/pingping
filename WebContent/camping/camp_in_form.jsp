<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%@include file ="../header.jsp" %>
<%
if (!isLogin) {
   out.println("<script>");
   out.println("alert('로그인 후  이용하실 수 있습니다.');");
   out.println("location.href='../login_form.jsp?url='camp_proc.camp';");
   out.println("</script>");
   out.close();
}

String pdtid = "";
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
<style>
body { margin:0 auto; }
.wrapper {
   width:1350px; margin:0 auto; height:1500px;
}
  
.outerBox {
   margin:0 auto; border:0px black solid; width:1250px; height:1000px;
   align:center;
}
.btnBox {
   border:0px black solid; width:1250px; height:50px;
   padding:5px 5px; align:right; margin-right:10px;
}
.btn {
   align:right; width:130px; height:40px; font-weight:bold; border:2px solid lightblue;
}
.left {
   border:0px black solid; float:left; width:190px; height:1000px;
   padding:5px 5px;
}
.center {
   border:0px black solid; float:left; width:650px; height:1000px;
   padding:5px 5px; align:center; margin:0 auto;
}
.right {
   border:0px black solid; float:left; width:400; height:1000px;
   padding:5px 5px; 
}
#preview {
   width:200px; height:1000px;
}
#addimg {
   border:0px black solid; width:95%; height:40px;
   margin:5px auto;
}
#preimgBox1 {
   border:1px black solid; width:150px; height:150px; 
   margin:5px 5px;
}
.imginfo {
   width:95%; height:150px; 
   margin:5px auto;
}

.img { margin:5px }
</style>
<script>
function changeImg(img, num) {   // 파일 컨트롤에  이미지를 넣었을 때 이미지 미리보기 
   var nextNum = (num + 1);
   var cnt = document.getElementById("cnt").innerHTML;
   //alert(cnt);
   var tmp = "preimg"+num;
   //alert(tmp);
   if (tmp == "preimg31") {
      alert('사진 재업로드 횟수(30회)를 초과했습니다. 처음부터 다시 시도해주세요.');

      return;
   }
   
   if (img.files && img.files[0]) {   // i번째 파일 컨트롤에 이미지가 들어있으면
      var reader = new FileReader();
      if (document.getElementById('preimg' + num).style.display == "block") {
         reader.onload = function(e) {
           document.getElementById('preimg' + num).style.display = "block";   // i번째 이미지 미리보기 
           document.getElementById('preimg' + num).src = e.target.result;
           //document.getElementById('img1src').innerHTML = document.getElementById('preimg' + num).src;
           //document.getElementById('img' + nextNum).style.display = "block";
         };
         
      } else {
         if (num == 1) {
            document.getElementById('preimg' + num).style.display = "block";
         }
         reader.onload = function(e) {
            document.getElementById('preimg' + num).style.display = "block";   // i번째 이미지 미리보기 
            document.getElementById('preimg' + num).src = e.target.result;
            //document.getElementById('img1src').innerHTML = document.getElementById('preimg' + num).src;
            document.getElementById('cnt').innerHTML = cnt / 1 + 1;
            //document.getElementById('img' + nextNum).style.display = "block";
         };
      }
      reader.readAsDataURL(img.files[0]);
      
   } else {   // i번째 파일 컨트롤에 이미지가 삭제되면
      document.getElementById('preimg' + num).src = "";
      document.getElementById('preimg' + num).style.display = "none";
      // 미리보기 지우고, 파일 컨트롤 1개만 보여주기
      
       <% for (int j = 5 ; j > 0 ; j--) {    // 5 4 3 2 1 %>
         if (document.getElementById('preimg' + <%=j %>).style.display == "none") {
            document.getElementById('img' + <%=j %>).style.display = "none";

            document.getElementById('cnt').innerHTML = cnt / 1 - 1;
         }      
      <% } %>
   }
}
</script>
<script>
function showAddImg() {   // 이미지 추가하기 버튼 클릭시
   var cnt = document.getElementById("cnt").innerHTML;
   //alert(cnt);
   
   if (cnt == 10) {
      alert('사진은 최대 10장까지만 업로드가 가능합니다.');
   } else if (cnt < 10) {   
      for (var i = 29 ; i > 0 ; i--) {
         var tmp = "preimg" + i;
         var tmp2 = "img" + (i+1);
         if (document.getElementById(tmp).style.display == "block") {   // i번째 사진 미리보기가 있으면
            //document.getElementById(tmp2).style.display = "block";      // i+1번째 이미지 추가 컨트롤 활성화
            document.getElementById(tmp2).click();
            return;
      
         }
         if (i == 1) {
            //document.getElementById("img1").style.display = "block";
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
      //alert(delimg);
      document.getElementById(delimg).style.display = "none";
      document.getElementById(delimg).src = "";
      
      var preimg = "preimg" + num;
      //alert(preimg);
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
   document.getElementById(preimg).style.display = "none";   // 미리보기 숨기고

   var delimg = "del" + num;
   //alert(delimg); //del1
   document.getElementById(delimg).style.display = "block";   // x 사진 띄우기
}
</script>
<script>
function showOriImg(num) {
   var delimg = "del" + num;
   //alert(delimg); //del1
   document.getElementById(delimg).style.display = "none";   // x 사진 띄우기
   
   var preimg = "preimg" + num;
   //alert(preimg); // preimg1
   document.getElementById(preimg).style.display = "block";   // 미리보기 숨기고
}
</script>
<script>
function chkValue(frm) {
   var cnt = document.getElementById("cnt").innerHTML;
   //alert(cnt);
   if (cnt == 0) {
      alert("최소 1개 이상의 캠핑후기 사진을 등록해야 합니다.");
      document.getElementById("addimg").focus();
      return false;
   }
   return true;
}
</script>
<div class="wrapper">
<h2 align="center">캠핑후기 등록</h2>
<form name="frmCamp" action="camp_proc.camp" method="post" enctype="multipart/form-data" onsubmit="return chkValue(this);">
<input type="hidden" name="wtype" value="in" />
<div class="outerBox">
<div class="btnBox" align="right">
   <input class="btn" type="submit" value="캠핑후기 등록" />
   &nbsp;&nbsp;&nbsp;&nbsp;
   <input class="btn" type="reset" value="다시 입력" />
</div>
<div class="left">
   <div class="preview">
      <img id="preimg1" name="preimg1" style="display:none;" width="180" height="180" onmouseover="showDelImg('1');" onmouseout="showOriImg('1');" />
      <span id="del1" style="display:none;"><img src="camp_img/cancel-button.png" name="preimg" width="180" height="180" onclick="delImg('1');" /></span>
      
      <% for (int i = 2 ; i < 32; i ++) { %>
      <img id="preimg<%=i %>" name="preimg<%=i %>" style="display:none;" width="180" height="180" onmouseover="showDelImg('<%=i %>');" onmouseout="showOriImg('<%=i %>');" />
      <span id="del<%=i %>" style="display:none;"><img src="camp_img/cancel-button.png" name="preimg" width="180" height="180" onclick="delImg('<%=i %>');" /></span>
      <% } %>
   </div>
</div>
<div class="center">
   <div class="">
      <span id="cnt" style="display:none;">0</span>
      <input type="file" style="display:none;" id="img1" name="img1" accept="image/*" value="1" onchange="changeImg(this, 1);" />
      
      <% for (int i = 2 ; i < 32; i ++) { 
         //if (i == 30) {            out.println("<script></script>");         }
      %>
      <input type="file" id="img<%=i %>" name="img<%=i %>" accept="image/*" style="display:none;" value="<%=i %>" onchange="changeImg(this, <%=i %>);" />
      <% } %>
      <span style="margin-left:90px;"><img src="../img/upload.png" width="500" height="500" onclick="showAddImg();" /></span>
      <button type="button" style="margin-left:10px;" class="btn btn-primary" id="addimg" onclick="showAddImg();">이미지 추가하기</button>
   </div>
   <div class="imginfo">
   * 이미지 파일은 각 1MB 이내, 최대 10장까지 업로드가 가능합니다.<br />
   * 이미지 파일명이 한글, 숫자, 영문이 아닌 다른 언어일 경우 파일이 업로드되지 않거나 깨질 수 있습니다.<br />
   <font color="red">* 저작권 등 다른 사람의 권리를 침해하거나 명예를 훼손하는 파일은 운영 원칙 및 관계 법률에 의해 제재를 받을 수 있습니다.</font><br />
   </div> 
   <div class="pdtList">
      <input type="hidden" name="pdtid" value="" />
      <span id="pdtArea" style="display:none;"></span><br />
      <span style="font-weight:bold; font-size:20px; padding-left:10px;">태그된 상품들</span><hr />
      <span id="imgArea"></span>
      
      <%  %>
   </div>
</div>
<div class="right">
<textarea style="margin-left:40px;" cols="40" rows="10" name="content" placeholder="사진에 대해 설명해주세요.">가평 캠핑장 다녀왔는데 진짜 여기가 최고인듯 ㅜㅜㅜㅜ

요즘 사람도 별로 안많아서 다녀오심 좋을듯해요~!!~!
</textarea>
<br />
<textarea style="margin-left:40px;" cols="40" rows="5" name="keyword" placeholder="키워드를 입력해주세요.&#13;&#10;키워드는 띄어쓰기없이 입력해주세요.&#13;&#10;예)#감성캠핑#가평캠핑">#감성캠핑#가평캠핑#가평감성캠핑</textarea>
<br /><br />
<iframe id="iframePdt" width="120%" height="400" src="../camping/search_product.camp" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
</div>
</div>
</form>
</div>
<%@include file ="../footer.jsp" %>