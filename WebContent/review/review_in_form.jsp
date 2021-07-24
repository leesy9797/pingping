<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%@include file ="../header.jsp" %>
<%
//로그인 후 로그인 화면으로 넘어오기 전 화면으로 돌아가기
if (!isLogin) {
   out.println("<script>");
   out.println("alert('로그인 후  이용하실 수 있습니다.');");
   out.println("location.href='../login_form.jsp';");
   out.println("</script>");
   out.close();
}

ReviewInfo orderInfo = (ReviewInfo)request.getAttribute("orderInfo");   // 리뷰를 등록할 상품 정보

String[] arrStar = {"★☆☆☆☆ 별로에요 ", "★★☆☆☆ 그냥 그래요 ", "★★★☆☆ 보통이에요 ", "★★★★☆ 맘에 들어요 ", "★★★★★ 아주 좋아요 "};
%>

<style>
.fontRed { color:red; font-weight:bold; font-size:80%; }
a:link { color:#4f4f4f; text-decoration:none; }
a:visited { color:#4f4f4f; text-decoration:none; }
a:hover { color:#2788ff; text-decoration:none; }
a:focus { color:#2788ff; text-decoration:none; }
a:active { color:#2788ff; text-decoration:none; }

#wrapper { margin:0 auto; width:1250px; }
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
   var msg = "";
   var content = frm.content.value;
   if (content.length < 1) {
      msg = "<span class='fontRed'>리뷰 내용을 입력해주세요.</span>";
      frm.content.focus();
      document.getElementById("contentMsg").innerHTML = msg;
      return false;
   }
   
   return true;
}

function chkContent(content) {
   var msg = "";
   if (content.length < 1) {
      msg = "<span class='fontRed'>리뷰 내용을 입력해주세요.</span>";
   } else {
      msg = "";
   }
   document.getElementById("contentMsg").innerHTML = msg;
}
</script>

<div id="wrapper">
   <h2>리뷰쓰기</h2><hr /><br />
   <form name="frmReview" action="review_proc.review" method="post" enctype="multipart/form-data" onsubmit="return chkValue(this);">
   <input type="hidden" name="wtype" value="in" />
   <input type="hidden" name="odid" value="<%=orderInfo.getOd_id() %>" />
   <input type="hidden" name="piid" value="<%=orderInfo.getPi_id() %>" />
   <input type="hidden" name="bropt" value="<%=orderInfo.getOd_option() %>" />
   <table width="1250" cellpadding="5">
   <tr>
   <td width="13%" style="padding-left:140px; padding-bottom:20px;">
      <img src="../product/pdt_img/<%=orderInfo.getPi_img1() %>" width="100" height="100" />
   </td>
   <td width="*" colspan="2" valign="top" style="padding-left:40px;">
      <strong>[<%=orderInfo.getBr_name() %>]&nbsp;<%=orderInfo.getPi_name() %></strong><br />
      <%=orderInfo.getOd_option() %><br />
      <%=orderInfo.getOd_pdtprice() %>원 ㅣ <%=orderInfo.getOd_cnt() %>개
   </td>
   </tr> 
   <tr>
   <td style="padding-left:140px; padding-bottom:20px;"><strong>별점평가</strong></td>
   <td colspan="2" style="padding-left:40px; padding-bottom:20px;">
   <select name="star">
   <% for (int j = arrStar.length ; j > 0 ; j--) { %>
      <option value="<%=j%>"><%=arrStar[j-1] %></option>
   <% } %>
   </select>
   </td>
   </tr>
   <tr height="200" valign="top" align="center">
   <td colspan="3">
      <textarea cols="122" rows="10" name="content" maxlength="500" onkeyup="chkContent(this.value);" placeholder="리뷰는 다른 고객에게 큰 도움이 됩니다."></textarea>
      <br /><span id="contentMsg"></span>
   </td>
   </tr>
   <tr>
   <td colspan="2">
      <!-- 이미지 미리보기? -->
   </td>
   <td align="right" style="padding-right:50px; padding-top:30px;">
      <input type="file" name="img1" id="img1" accept="image/*" onchange="chkImg1(this.value)" /><br />
      <input type="file" name="img2" id="img2" accept="image/*" onchange="chkImg2(this.value)" style="visibility:hidden;" /><br />
      <input type="file" name="img3" id="img3" accept="image/*" style="visibility:hidden;" /><br />
   </td>
   </tr>
   <tr>
   <td colspan="3" style="padding-left:140px;">
      일반리뷰 작성 시 <strong>100P</strong>, 포토리뷰 작성 시 <strong>500P</strong>가 적립됩니다.<br />
      <input type="checkbox" name="agree" value="y" />리뷰 정책에 동의하십니까?
   </td>
   </tr>
   <tr><td colspan="3" align="center" style="padding:40px;">
      <input type="submit" value="리뷰쓰기" />
   </td></tr>
   </table>
   </form>
</div>
<div style="height:150px;"></div>
<%@include file ="../footer.jsp" %>