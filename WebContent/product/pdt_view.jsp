<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%@include file ="../header.jsp" %>
<%
ProductInfo pdtInfo = (ProductInfo)request.getAttribute("pdtInfo");

if (pdtInfo == null) {
   out.println("<script>");
   out.println("alert('잘못된 경로로 들어오셨습니다.');");
   out.println("../index.jsp");
   out.println("</script>");
   out.close();
}

String cbname = pdtInfo.getCb_name();      // 대분류
String csname = pdtInfo.getCs_name();      // 소분류
String brand = pdtInfo.getBr_name();      // 브랜드
String name = pdtInfo.getPi_name();         // 상품명
int price = pdtInfo.getPi_price();         // 가격
int stock = pdtInfo.getPi_stock();         // 수량
String option = pdtInfo.getPi_option();      // 옵션
//String option2 = pdtInfo.getPi_option2();   // 옵션1
int dcrate = pdtInfo.getPi_dcrate();      // 할인율
double star = pdtInfo.getPi_staravg();      // 별점평균
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
  	a:link { color:#4f4f4f; text-decoration:none; }
	a:visited { color:#4f4f4f; text-decoration:none; }
	a:hover { color:#2788ff; text-decoration:none; }
	a:focus { color:#2788ff; text-decoration:none; }
	a:active { color:#2788ff; text-decoration:none; }
   
	#btnsch { width:25px; height:25px; }
   tr, td { padding:5px 0; }
   
   .outer { margin-left:300px; }
   
   .categoryBar {
            float : left; position : fixed;
            display : block; padding-top:300px;
            width:80px;
            z-index:1;
   }
   
   .eSlide { float : left; padding-left:230px; padding-bottom:70px;
            max-width:900px;}
            
   .eBanner { float : left; padding-left:10px; padding-bottom:70px;
            max-width:900px;}
            
   .b-example-divider { float : left;
   color: rgba(255, 255, 255, 1.0);}
   
   .details { padding-left:50px; width:1250px; }
   
   .pop { position:relative;
            padding-left : 230px;
            max-width:1300px; margin-top:100px;
   }
   
   .list { display:table; width:1500px; }
   
   .card {
   border:1px #4f4f4f solid; width:250px; height:200px; text-align:center;
   padding:5px; margin:15px 10px; float:left; display:table-cell;
   }

   .page { margin:0 auto; margin-left:130px; float:left; }

   .header {
   z-index : 2;
   }
   
   .frmSch {
      width:1060px;
   }
   
   .cmb {
      width:150px;
   }
   
   .btns {
      width:200px;
   }
   
   #selectopt, #selectcnt { font-weight:bold; color:#2788ff; font-size:20px; }
</style>
<script>
function showOpt() {
// 선택한 옵션을 보여주는 함수
   var obj = document.getElementById("selected");
   var obj1 = document.getElementById("selectopt");
   var obj2 = document.getElementById("selectcnt");
   
   var occnt = document.getElementById("occnt").innerHTML + "개";
   var ocoption = document.getElementById("ocoption").value;
   
   if (ocoption != "") {
      obj.innerHTML = "선택한 옵션";
      obj1.innerHTML = ocoption;
      obj2.innerHTML = occnt;
   } else {
	  obj.innerHTML = "";
      obj1.innerHTML = "";
      obj2.innerHTML = "";
   }
}

function chImg(num) {
   var obj = document.getElementById("bigImg");
   if (num == 1) {
      obj.src = "product/pdt_img/" + <%=pdtInfo.getPi_id() %> + ".jpg";
   } else {
      obj.src = "product/pdt_img/" + <%=pdtInfo.getPi_id() %> + "_" + num + ".jpg";
   }
}

var cnt = 1;
var total = <%=price %>;
function changeCnt(op) {
// 수량에 따른 총 구매가격을 변경시키는 함수
   var obj1 = document.getElementById("occnt");
   var obj2 = document.getElementById("total");
   
   if (op == "+") {
      cnt += 1;
   } else {
      if (cnt > 1) {
         cnt -= 1;   
      }
   }
   obj1.innerHTML = cnt;   // 수량변경
   document.frmPdt.occnt.value = cnt;
   obj2.innerHTML = cnt * total;   // 총 구매가 변경
}

function cartBuy(chk) {
// 장바구니나 즉시구매로 이동시키는 함수로 비로그인시 로그인 폼으로 이동시켜야 함
// 장바구니 : cart_in_proc.ord / 즉시구매 : order_form.ord
   var lnk = "";
   var ocoption = document.getElementById("ocoption");
   if (ocoption.value == "") {
      alert("옵션을 선택하세요.");
      return false;
   }
   
   if (chk == "c")   lnk = "cart_in_proc.ord";
   else         lnk = "order_form.ord";
<% if (!isLogin) { %>
   alert("로그인 후에 이용할 수 있습니다.");
   location.href = "login_form.jsp?url=" + lnk;
<% } else { %>
   var frm = document.frmPdt;
   frm.action = lnk;
   frm.submit();
<% } %>
}
</script>
<div class="outer">
<table cellpadding="0" cellspacing="0" class="details">
<!-- 대분류, 소분류 링크 -->
<tr>
<th>
   <a href="pdt_list.pdt?&bcata=<%=pdtInfo.getCb_id() %>"><%=cbname %></a>
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   <a href="pdt_list.pdt?&bcata=<%=pdtInfo.getCb_id() %>&scata=<%=pdtInfo.getCs_id() %>"><%=csname %></a>
</th>
</tr>
<tr height="20"></tr>
<tr><td>
   <!-- 상품 이미지 및 상품 정보 보기 영역 시작 -->
   <table width="100%" cellpadding="5">
   <tr height="20"></tr>
   <tr align="center">
   <!-- 상품 이미지 영역 시작 -->
   <td width="40%">
      <table width="100%" cellpadding="5">
      <tr><td align="center" valign="middle">
         <img src="product/pdt_img/<%=pdtInfo.getPi_img1() %>" width="300" height="300" id="bigImg"/>
      </td></tr>
      <tr height="10"><td></td></tr>
      <tr><td align="center" valign="middle">
         <img src="product/pdt_img/<%=pdtInfo.getPi_img1()%>" width="50" height="50" onclick="chImg('1');" />
<% if (pdtInfo.getPi_img2() != null && !pdtInfo.getPi_img2().equals("")) { %>
         &nbsp;&nbsp;&nbsp;&nbsp;
         <img src="product/pdt_img/<%=pdtInfo.getPi_img2()%>" width="50" height="50" onclick="chImg('2');" />
<% } %>
<% if (pdtInfo.getPi_img3() != null && !pdtInfo.getPi_img3().equals("")) { %>
         &nbsp;&nbsp;&nbsp;&nbsp;
         <img src="product/pdt_img/<%=pdtInfo.getPi_img3()%>" width="50" height="50" onclick="chImg('3');" />
<% } %>
      </td></tr>
      <tr height="20"></tr>
      </table>
   </td>
   <!-- 상품 이미지 영역 종료 -->
   <!-- 상품 정보 영역 시작 -->
   <td align="left" width="*" valign="top">
      <table width="100%" cellpadding="3">
      <tr height="20" style="border-top:1px solid black; margin:5px 0;"></tr>
      <tr><td colspan="2" style="font-weight:bold;">[<%=brand %>]&nbsp;&nbsp;<%=name %></td></tr>
      <tr><td colspan="2"></td></tr>
      <tr><td width="30%">판매가</th><td width="*"><%=price %></td></tr>
      <tr><td>할인율</th><td><%=dcrate %>%</td></tr>
      <tr><td>별점평균</th><td><%=(star == 0.0) ? "별점 없음" : star + "점" %></td></tr>
      <form name="frmPdt" action="" method="post">
      <input type="hidden" name="kind" value="direct" />
      <input type="hidden" name="piid" value="<%=pdtInfo.getPi_id() %>" />
      <input type="hidden" name="occnt" value="1" />
      <tr><td>수량</th>
      <td>
         <input type="button" id="btnsch" value="-" onclick="changeCnt(this.value); showOpt();" />
         <span id="occnt">1</span>
         <input type="button" id="btnsch" value="+" onclick="changeCnt(this.value); showOpt();"/>
         <!--
         <select name="count" onchange="setTotalPrice(this.value, this.form.total);">
<%
if (stock > 20)   stock = 20;
for (int i = 0 ; i <= stock ; i++) {
%>
            <option><%=i %></option>
<% } %>   
            </select>
            -->
      </td>
      </tr>      
<%
if (option != null && !option.equals("")) {
   String[] arrOpt = option.split(";");   
%>
      <tr>
      <td>옵션</th>
      <td>
         <select id="ocoption" name="ocoption" onchange="showOpt();">
         <option value="">옵션 선택</option>
<%
for (int i = 0 ; i < arrOpt.length ; i++) {
%>
         <option value="<%=arrOpt[i] %>"><%=arrOpt[i] %></option>
<% } %>   
      </select>
      </td>
      </tr>
<% } %>
      <tr height="20"></tr>
      <tr>
      <td style="border-top:1px solid black; margin-top:5px;"><span id="selected"></span></td>
      <td style="border-top:1px solid black; margin-top:5px;"><span id="selectopt"></span>&nbsp;<span id="selectcnt"></span></td>
      </tr>
      </form>
      <tr><td colspan="2" align="right">
         총 구매가격 : <span id="total"><%=price %></span> 원
      </td></tr>
      <tr><td colspan="2" align="center">
         <input type="button" value="장바구니 담기" onclick="cartBuy('c');" />
         &nbsp;&nbsp;&nbsp;&nbsp;
         <input type="button" value="즉시 구매하기" onclick="cartBuy('b');" />
      </td></tr>
      </table>
      </td>
      <!-- 상품 정보 영역 종료 -->
   </tr>
   </table>
   <!-- 상품 이미지 및 상품 정보 보기 영역 종료 -->
</td></tr>
</table>
<table cellpadding="5" cellspacing="0" class="details"">
<!-- 상품 상세 / 상품 문의 / 상품 리뷰 / 배송교환환불안내 시작         !!! 교환안하면 '배송/환불 안내'로 바꾸기 !!! -->
<tr height="20" style="border-top:1px solid black;"></tr>
<tr>
<th width="25%" align="center" class="">상품상세</th>
<th width="25%" align="center" class="">상품문의</th>
<th width="25%" align="center" class="">상품리뷰</th>
<th width="*" align="center" class="">배송/교환/환불 안내</th>
</tr>
<tr height="20"></tr>
<!-- 상품상세 시작 -->
<tr><td colspan="4" align="center" valign="middle"><% System.out.println(pdtInfo.getPi_desc()); %>
   <img src="product/pdt_img/<%=pdtInfo.getPi_desc() %>" width="90%" height="800" id="bigImg" />
</td></tr>
<!-- 상품상세 종료 -->
<tr height="20"></tr>
<!-- 상품문의 시작 -->
<tr>
<th width="20%" style="padding-left:30px;">상품문의</th>
<td colspan="3" width="*" align="right" style="padding-right:70px;"><input type="button" value="문의하기" onclick="" />
</tr>
<tr>
<td colspan="4" style="padding:0 50px;">
 > 구매한 상품의 취소/반품은 '나의쇼핑 > 주문배송내역 조회 > 상세보기'에서 신청 가능합니다.<br /><br />
 > 상품문의 및 후기게시판을 통해 취소나 환불, 반품 등은 처리되지 않습니다.<br /><br />
 > 가격, 판매자, 교환/환불 및 배송 등 해당 상품 자체와 관련 없는 문의는 고객센터 내 1:1문의 게시판을 이용해 주세요.<br /><br />
 > "해당 상품 자체"와 관계없는 글, 양도, 광고성, 욕설, 비방, 도배 등의 글은 예고 없이 이동, 노출 제한, 삭제 등의 조치가 취해질 수 있습니다.<br /><br />
 > 공개 게시판이므로 전화번호, 메일 주소 등 고객님의 소중한 개인정보는 절대 남기지 말아 주세요.<br /><br />
</td>
</tr>
<tr>
<!-- 상문문의 내역 보기 시작      !!! 문의 테이블 값 넣고 만들기 !!! -->
<!-- 
<table width="90%" cellpadding="5">
if (pdtList != null && pdtList.size() > 0) {
// 상품문의 내역이 있으면

} else {
   out.println("<tr><th>상품문의 내역이 없습니다.</th></tr>");
}
%>
</table>
 -->
<!-- 상문문의 내역 보기 종료 -->
</tr>
<!-- 상품문의 종료 -->
<!-- 상품리뷰 시작 -->
<!-- 상품리뷰 종료 -->
<tr height="20"></tr>
<!-- 배송/교환/환불 안내 시작 -->
<tr><th style="padding-left:30px;">배송정보</th><td colspan="3"></td></tr>
<tr>
<td colspan="4" style="padding:0 50px;">
   <table width="100%" border="1" cellpadding="5" cellspacing="5" style="font-size:12px;">
   <tr>
      <th width="20%">배송방법</th>
      <td width="20%">순차배송</td>
      <th rowspan="2" width="20%">배송비</th>
      <td rowspan="2" width="*">3,000원<br />
      - 70,000원 이상 구매 시 무료배송<br />
      - 도서산간 지역 배송불가<br />
      · 도서산간 추가 배송비<br />
      - 제주 지역: 4,000원<br />
      - 도서산간 지역: 0원
      </td>
   </tr>
   <tr><th>배송사</th><td>롯데택배</td></tr>
   <tr><th>묶음배송 여부</th><td colspan="3">가능</td></tr>
   <tr><th>배송기간</th>
      <td colspan="3">
      · 도서산간 지역 등은 배송에 3-5일이 더 소요될 수 있습니다.<br />
       - 천재지변, 물량 수급 변동 등 예외적인 사유 발생 시, 다소 지연될 수 있는 점 양해 부탁드립니다.
       </td>
   </tr>
   </table>   
</td>
</tr>
<!-- 배송/교환/환불 안내 종료 -->
</table>
</div>
<%@ include file="../footer.jsp" %>