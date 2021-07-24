<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%@include file ="../header.jsp" %>
<%
System.out.println("아아, 여기는 order_detail.jsp");

request.setCharacterEncoding("utf-8");

ArrayList<CartInfo> orderInfo = (ArrayList<CartInfo>)request.getAttribute("orderInfo");
OrderPageInfo pageInfo = (OrderPageInfo)request.getAttribute("pageInfo");
System.out.println(orderInfo.size());
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

.wrapper { width:1250px; margin:0 auto; height:auto; margin-top:50px; }

tr, td { padding:5px 0; }

.btn { width:90px; height:40px; border:2px solid lightblue; }
#btnsch2 { width:130px; height:35px; }
#btnsch { width:25px; height:25px; }
.ipt { width:150px; font-size:18px; font-weight:bold; }
.cmb { width:120px; height:25px; }

.body { margin:0 auto; width:1250px; align:center; }
.point { width:1250px; padding:5px 15px; margin:15px 0; background-color:yellow; }
.shipInfo th { text-align:left; padding:5px; }
.shipInfo td { text-align:left; padding-left:20px; }
.payInfo th { text-align:center; padding:5px; }
</style>
<script>
function iscomfirmed(oiid) {
	if(confirm("구매를 확정하시겠습니까?\n※ 구매확정시 해당 주문건에 포함된 모든 상품의 취소/교환/환불이 불가합니다.")) {
		return true;
	}
	
	return false;
}
</script>
<div class="wrapper">
<div class="body">
<h2>주문상세정보</h2>
<div class="point">
구매확정시 구매액의 3% 포인트 적립<br />
리뷰작성시 각 상품당 포토리뷰 500Point 일반리뷰(사진x) 100Point 적립
</div>
<div>
<strong>주문번호 : <%=orderInfo.get(0).getOi_id() %> | 주문일 : <%=orderInfo.get(0).getOi_date() %></strong> 
</div>
<hr />
<div>
<table width="1250" class="orderDetail">
<!--  -->
<tr align="center">
<th width="20%">주문상세번호</th>
<th colspan="2" width="*">상품이미지/브랜드명/상품명</th>
<th width="25%">옵션/수량/가격</th>
<th width="20%">배송추적/리뷰작성</th>
</tr>
<%
for (int i = 0 ; i < orderInfo.size() ; i++) {
	CartInfo oi = orderInfo.get(i);
	String lnk = "<a href=\"pdt_view.pdt?id=" + oi.getPi_id() + "\">";
%>
<tr>
<td align="center"><strong><%=oi.getOd_id() %></strong><br /><span style="font-size:14px;">[<%=oi.getOi_date().substring(0, 10) %>]</span></td>

<td align="center"><%=lnk %><img src="product/pdt_img/<%=oi.getPi_img1() %>" width="90" height="90" /></a></td>
<td>
	<%=lnk %>[<%=oi.getBr_name() %>]<br /><%=oi.getPi_name() %></a><br />
	<% 
	String stylebtn1 = "display:none;";
	String stylebtn2 = "display:none;";
	if (oi.getOi_status().equals("c") || oi.getOi_status().equals("d")) {
		stylebtn1 = "margin-top:5px; display:inline;";
		if (oi.getOi_status().equals("d")) {
			stylebtn2 = "margin-top:5px; display:inline;";
		}
	}
	System.out.println(oi.getOi_status());
	System.out.println(stylebtn1);
	System.out.println(stylebtn1);
	%>
	<form name="frm" action="purchase_comfirmed.ord" method="post">
	<input type="hidden" name="cpage" value="<%=pageInfo.getCpage() %>" />
	<input type="hidden" name="sdate" value="<%=pageInfo.getSdate() %>" />
	<input type="hidden" name="edate" value="<%=pageInfo.getEdate() %>" />
	<input type="hidden" name="schdate" value="<%=pageInfo.getSchdate() %>" />
	<input type="hidden" name="oiid" value="<%=oi.getOi_id() %>" />
	<input type="hidden" name="price" value="<%=oi.getOi_pdtprice() %>" />
	<input type="button" style=<%=stylebtn1 %> value="반품신청" class="btn" onclick="confirm('반품을 신청하시겠습니까?');" />&nbsp;
	<input type="submit" style=<%=stylebtn2 %> value="구매확정" class="btn" onclick="return iscomfirmed();" />
	</form>
</td>
<td>
	옵션 : <%=oi.getOd_option() %> | 수량 : <%=oi.getOd_cnt() %>개<br />
	가격 : <%=oi.getOd_pdtprice() %>원<br /><br />
	<strong style="font-size:14px;">
	<span id="buy" style="font-weight:bold; margin-top:10px; color:red;"><% if (oi.getOi_status().equals("e")) { %>구매확정<br />(<%=oi.getOi_last() %>)<% } else { %> <% } %></span>
	</strong><br />
</td>
<td align="center">
	<% 
	stylebtn1 = "visibility:hidden";
	stylebtn2 = "visibility:hidden";
	if (oi.getOi_status().equals("c") || oi.getOi_status().equals("d") || oi.getOi_status().equals("e")) {
		stylebtn1 = "visibility:visible";
		if (oi.getOi_status().equals("e")) {
			stylebtn2 = "visibility:visible";
		}
	}
	System.out.println(oi.getOi_status());
	System.out.println(stylebtn1);
	System.out.println(stylebtn2);
	%>
	<input type="button" style=<%=stylebtn1 %> value="배송추적" class="btn" onclick="alert('송장내역 보여주기!');" /><br /><br />
	<input type="button" style=<%=stylebtn2 %> value="리뷰작성" class="btn" onclick="" />
</td>
</tr>
<!--  -->
<% } %>
</table>
</div>
<hr />
<h3>결제정보</h3>
<div>
<table width="1250" class="payInfo" name="payInfo">
<tr align="center">
<th>결제금액</th>
<th>상품판매가</th>
<th>할인금액</th>
<th>배송비</th>
<th>사용포인트</th>
<th>결제수단</th>
</tr>
<tr align="center">
<td><%=orderInfo.get(0).getOi_pay() %>원</td>
<td><%=orderInfo.get(0).getOi_pdtprice() %>원</td>
<td>(-) 원</td>
<td><%=orderInfo.get(0).getOi_delipay() %>원</td>
<td>(-) <%=orderInfo.get(0).getOi_usepoint() %>원</td>
<%
String payment = "";
if (orderInfo.get(0).getOi_payment().equals("a"))	{ payment = "카드결제"; }
if (orderInfo.get(0).getOi_payment().equals("b"))	{ payment = "무통장입금"; }
if (orderInfo.get(0).getOi_payment().equals("c"))	{ payment = "카카오페이"; }
if (orderInfo.get(0).getOi_payment().equals("d"))	{ payment = "네이버페이"; }
if (orderInfo.get(0).getOi_payment().equals("e"))	{ payment = "핸드폰결제"; }
%>
<td><%=payment %></td>
</tr>
</table>
</div>
<hr />
<h3>배송지 정보</h3>
<div>
<table class="shipInfo" summary="받으시는 분,전화번호,배송지 주소,배송시 요청사항">
	<colgroup>
		<col style="width:15%" />
		<col style="width:*" />
	</colgroup>
	<tbody>
		<tr>
			<th scope="row">받으시는 분</th>
			<td><%=orderInfo.get(0).getOi_name() %></td>
		</tr>
		<tr>
			<th scope="row">전화번호</th>
			<td><%=orderInfo.get(0).getOi_phone() %></td>
		</tr>
		<tr>
			<th scope="row">배송지 주소</th>
			<td>[<%=orderInfo.get(0).getOi_zip() %>]&nbsp;<%=orderInfo.get(0).getOi_addr1() %>&nbsp;<%=orderInfo.get(0).getOi_addr2() %></td>
		</tr>
		<tr>
			<th scope="row">배송시 요청사항</th>
			<td>
				<textarea class="cmt" rows="4" cols="10" style="width:99%;" title="배송시 요청사항" readonly="readonly"><%=orderInfo.get(0).getOi_cmt() %></textarea>
			</td>
		</tr>
		</tbody>
</table>
</div>
<hr />
<h3>주문자 정보</h3>
<div>
<table width="1250" class="shipInfo" summary="주문자명,전화번호,회원이메일,주문일자">
	<colgroup>
		<col style="width:15%" />
		<col style="width:35%" />
		<col style="width:15%" />
		<col style="width:35%" />
	</colgroup>
	<tbody>
		<tr>
			<th scope="row">주문자명</th>
			<td><%=orderInfo.get(0).getOi_name() %></td>
			<th scope="row">전화번호</th>
			<td><%=orderInfo.get(0).getOi_phone() %></td>
		</tr>
		<tr>
			<th scope="row">회원이메일</th>
			<td><%=orderInfo.get(0).getMi_email() %></td>
			<th scope="row">주문일자</th>
			<td><%=orderInfo.get(0).getOi_date() %></td>
		</tr>
		</tbody>
</table>
</div>
</div>
<br /><br /><br /><br /><br />
</div>
<%@ include file="../footer.jsp" %>