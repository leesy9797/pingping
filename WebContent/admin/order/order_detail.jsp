<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<% if (request.getParameter("path").equals("direct")) { %>
<%@include file ="../../admin_header.jsp" %>
<% } %>
<%
/*
if (!isLogin) {
	out.println("<script>");
	out.println("alert('로그인 후에 이용하실 수 있습니다.');");
	out.println("location.href='../login_form.jsp';");
	out.println("</script>");
	out.close();
}
*/
System.out.println("아아, 여기는 order_detail.jsp");

request.setCharacterEncoding("utf-8");

ArrayList<CartInfo> orderInfo = (ArrayList<CartInfo>)request.getAttribute("orderInfo");
//OrderPageInfo pageInfo = (OrderPageInfo)request.getAttribute("pageInfo");

System.out.println(orderInfo.size());
System.out.println(orderInfo.get(0).getOi_memo());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
a:link { color:#fff; text-decoration:none; }
/* a:visited { color:#fff; text-decoration:none; }*/
a:hover { color:darkblue; text-decoration:underline; font-weight:bold; }
a:active { color:darkblue; text-decoration:none; }
a:focus { color:darkblue; text-decoration:none; }
.body { margin:0 auto; width:1250px; align:center; }
.memo { width:1250px; padding:5px 15px; margin:15px 0; background-color:yellow; }
.shipInfo th { text-align:left; padding:5px; }
.wrapper { margin:0 auto; width:1250px; padding-top:10px; }
tr, td { padding:10px 0; }
</style>
<div class="wrapper">
<div class="body">
<h2>주문상세정보</h2><hr /><br />
<div class="memo">
<form name="frm" action="order_detail_update.orda" method="get">
<input type="hidden" name="oiid" value="<%=orderInfo.get(0).getOi_id() %>" />
<input type="hidden" name="odid" value="<%=orderInfo.get(0).getOd_id() %>" />
<input type="hidden" name="email" value="<%=orderInfo.get(0).getMi_email() %>" />
<h3>관리자 메모<span style="float:right; padding-right:10px;"><input type="submit" value="저장" /></span></h3>
<textarea rows="10" cols="130%" name="memo"><%=orderInfo.get(0).getOi_memo() %></textarea>
</div>
</form>
<div>
<strong>주문번호 : <%=orderInfo.get(0).getOi_id() %> | 주문일 : <%=orderInfo.get(0).getOi_date() %></strong> 
</div>
<hr />
<div>
<table width="1250" class="orderDetail">
<th width="15%">상품이미지</th>
<th width="*">브랜드명/상품명 옵션/수량 가격</th>
<th width="20%">배송추적/리뷰작성</th>
</tr>
<%
for (int i = 0 ; i < orderInfo.size() ; i++) {
	CartInfo oi = orderInfo.get(i);
	String lnk = "<a href=\"pdt_view.pdt?id=" + oi.getPi_id() + "\">";
%>
<tr>
<td align="center"><%=lnk %><img src="product/pdt_img/<%=oi.getPi_img1() %>" width="90" height="90" /></a></td>
<td><%=lnk %>[<%=oi.getBr_name() %>]<br /><%=oi.getPi_name() %><br />
	<%=oi.getOd_option() %> | <%=oi.getOd_cnt() %>개<br />
	<%=oi.getOd_pdtprice() %>원<br />
	<strong style="font-size:14px;"><span id="buy" style="display:block; color=darkblue;">구매확정(날짜)</span></strong><br />
	</a>
</td>
<td align="center">
	<input type="button" style="display:block;" value="배송추적" class="btn" onclick="alert('송장내역 보여주기');" /><br />
	<input type="button" style="display:block;" value="리뷰작성" class="btn" onclick="location.href='';" />
</td>
</tr>
<% } %>
</table>
</div>
<hr />
<h3>결제정보</h3>
<div>
<table width="1250"  name="payInfo">
<tr>
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
<table width="1250" class="shipInfo" summary="받으시는 분,전화번호,배송지 주소,배송시 요청사항">
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
<br />
<br />
<br />
<br />
<br />
</body>
</html>