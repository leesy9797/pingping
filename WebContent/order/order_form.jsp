<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%@include file ="../header.jsp" %>
<%
if (!isLogin) {
	out.println("<script>");
	out.println("alert('잘못된 경로로 들어오셨습니다.');");
	out.println("location.href='../login_form.jsp");
	out.println("</script>");
	out.close();
}

String kind = request.getParameter("kind");	// 장바구니와 바로구매의 구분자
int total = 0, pdtprice = 0;	// 총 구매가격을 누적할 변수
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
.wrapper { width:1250px; height:1500px; margin:0 auto; }

.left { width:800px; float:left; border:1px black solid; margin:5px; padding:5px; }

.right { 
	width:450px; float:right; border:1px black solid; margin:5px;  padding:5px;
	position:fixed; display:block; z-index:1; margin-left:850px; margin-top:100px;
}

#cartTable th, #cartTable td { border-bottom:1px black double; }
.membertable th { text-align:left; }
.pay { padding:5px 15px; }
.memberInfo, .addrInfo, .payInfo, .point { padding:5px 15px; width:750px; }
.title { padding-left:10px; font-size:20px; font-weight:bold; }
#chgAddrBtn { padding-right:10px; float:right; }

a:link { color:#4f4f4f; text-decoration:none; }
a:visited { color:#4f4f4f; text-decoration:none; }
a:hover { color:#2788ff; text-decoration:none; font-weight:bold; }
a:focus { color:#2788ff; text-decoration:none; }
a:active { color:#2788ff; text-decoration:none; }

.btn { width:90px; height:40px; border:2px solid lightblue; }
#btnsch { width:90px; height:35px; }
.ipt { width:150px; font-size:18px; font-weight:bold; }
.cmb { width:120px; height:25px; }
.schBox { padding-top:30px; padding-left:30px; }

tr, td { padding:5px 0; }   
</style>
<script>
function selectCmt(val) {
	//alert(val);
	if (val == "e") {
		document.getElementById("cmte").style.display = "block";
	} else {
		document.getElementById("cmte").style.display = "none";
	}
	
}

function chkValue(frm) {
	var agree = frm.agree;
	var payment = frm.payment.value;
	//alert(payment);
	
	if (!agree.checked) {
		alert("결제진행에 관련된 필수 내용에 동의해야 구매가 가능합니다.");
		//document.getElementsByName("agree").focus();
		document.getElementById("disagree").style.color = "red";
		frm.agree.focus();
		return false;
	}
	
	if (payment == "") {
		alert("결제수단을 선택해주세요.");
		document.getElementsByName("payment")[0].focus();
		//frm.agree.focus();
		return false;
	}
	
	return true;
	
}

function useAllPoint() {
	var userpoint = <%=memberInfo.getMi_point() %>;
	var pdtprice = document.getElementById("pdtprice").innerHTML;
	var delipay = document.getElementById("delipay").innerHTML;
	var total = (pdtprice-0) + (delipay-0) - userpoint;
	//alert(total);

	if (userpoint < 3000) {
		alert("보유 포인트가 3000P 이상일 경우 사용이 가능합니다.");
		return false;
	} else {
		document.getElementById("point").value = <%=memberInfo.getMi_point() %>;
		document.getElementById("paypoint").innerHTML = <%=memberInfo.getMi_point() %>;
		document.getElementById("tp").innerHTML = total;
	}
}

function usePoint(val, point) {
	if (val > point) {
		alert(val + "P는 보유 포인트보다 많습니다.");
		document.getElementById("point").value = 0;
		return false;
	} 
	
	var pdtprice = document.getElementById("pdtprice").innerHTML;
	var delipay = document.getElementById("delipay").innerHTML;
	var total = (pdtprice-0) + (delipay-0) - val;
	//alert(total);
	document.getElementById("paypoint").innerHTML = val;
	document.getElementById("tp").innerHTML = total;
}
</script>
<div class="wrapper">
<div class="left">
<h2>주문/결제</h2>
<form name="frmOrder" action="order_proc.ord" method="post" onsubmit="return chkValue(this);">
<input type="hidden" name="kind" value="<%=kind %>" />
<!-- 
<input type="hidden" name="oiaddr1" value="<%=memberInfo.getMa_addr1() %>" />
<input type="hidden" name="oiaddr2" value="<%=memberInfo.getMa_addr2() %>" />
<input type="hidden" name="kind" value="<%=kind %>"/>
<input type="hidden" name="kind" value="<%=kind %>"/>
<input type="hidden" name="kind" value="<%=kind %>"/> -->
<div class="memberInfo">
<strong class="title">구매할 상품 정보</strong>
<hr />
<table width="100%" cellpadding="5" cellspacing="0" id="cartTable">
<tr>
<th width="15%">상품이미지</th>
<th width="*">상품정보</th>
<th width="15%">옵션</th>
<th width="15%">수량</th>
<th width="15%">가격</th>
</tr>
<% 
if (kind.equals("cart")) {	// 장바구니를 통한 구매시
	ArrayList<CartInfo> cartList = (ArrayList<CartInfo>)session.getAttribute("cartList");
	if (cartList != null && cartList.size() > 0) {	// 장바구니에 상품이 들어 있으면
		for (int i = 0 ; i < cartList.size() ; i++) {
			CartInfo cart = cartList.get(i);
			String lnk = "<a href=\"pdt_view.pdt?id=" + cart.getPi_id() + "\">";
	%>
	<tr align="center">
	<td width="*">
		<%=lnk %><img src="product/pdt_img/<%=cart.getPi_img1() %>" width="50" height="60" /></a>
	</td>
	<td>
		<%=lnk %>[<%=cart.getBr_name() %>] <%=cart.getPi_name() %></a>
	</td>
	<td width="15%"><%=cart.getOc_option() %></td>
	<td width="15%"><%=cart.getOc_cnt()%></td>
	<td width="15%"><%=cart.getPi_price() * cart.getOc_cnt() %></td>
	</tr>
<%
		pdtprice += cart.getPi_price() * cart.getOc_cnt();
		// 장바구니 내의 상품 총 가격
		}
	} 
} else {	// 상품 상세화면에서 '바로구매' 클릭시
	ProductInfo pdtInfo = (ProductInfo)request.getAttribute("pdtInfo");
	String occnt = (String)request.getParameter("occnt");
	String ocoption = (String)request.getParameter("ocoption");
	String lnk = "<a href=\"pdt_view.pdt?id=" + pdtInfo.getPi_id() + "\">";
	System.out.println(Integer.parseInt(occnt));
%>
	<input type="hidden" name="piid" value="<%=pdtInfo.getPi_id() %>" />
	<input type="hidden" name="odpdtprice" value="<%=pdtInfo.getPi_price() %>" />
	<input type="hidden" name="odcnt" value="<%=occnt %>" />
	<input type="hidden" name="odoption" value="<%=ocoption %>" />
	<tr align="center">
	<td width="*">
		<%=lnk %><img src="product/pdt_img/<%=pdtInfo.getPi_img1() %>" width="50" height="60" /></a>
	</td>
	<td>
		<%=lnk %>[<%=pdtInfo.getBr_name() %>] <%=pdtInfo.getPi_name() %></a>
	</td>
	<td width="15%"><%=ocoption %></td>
	<td width="15%"><%=Integer.parseInt(occnt) %></td>
	<td width="15%"><%=pdtInfo.getPi_price() * Integer.parseInt(occnt) %></td>
	</tr>
<%
		pdtprice += pdtInfo.getPi_price() * Integer.parseInt(occnt);
		// 장바구니 내의 상품 총 가격
}
%>
<tr><td colspan="5" align="right">총 구매가격 : <%=pdtprice %>원&nbsp;&nbsp;</td></tr>
</table>
</div>
<br />
<div class="memberInfo">
<strong class="title">주문자 정보</strong>
<hr />
<!-- 주문자 이름, 이메일, 전화번호 -->
<table class="membertable" width="100%" cellpadding="5" cellspacing="0">
<tr>
<th width="20%">이름</th>
<td width="*"><input type="text" name="miname" value="<%=memberInfo.getMi_name() %>" /></td>
</tr>
<tr>
<tr>
<th>이메일</th>
<td width="*"><input type="text" name="miemail" value="<%=memberInfo.getMi_email() %>" /></td>
</tr>
<tr>
<th>전화번호</th>
<td>
	<select name="p1">
		<option value="010">010</option>
		<option value="011">011</option>
		<option value="016">016</option>
		<option value="019">019</option>
	</select> - 
	<% String[] arrPhone = memberInfo.getMi_phone().split("-"); // 회원정보 중 전화번호는 필수요소이므로 검사없이 배열로 담음 %>
	<input type="text" name="p2" size="4" maxlength="4" value="<%=arrPhone[1] %>" /> - 
	<input type="text" name="p3" size="4" maxlength="4" value="<%=arrPhone[2] %>" />
</td>
</tr>
</table>
</div>
<br />
<div class="addrInfo">
<strong class="title">배송지 정보</strong><span id="chgAddrBtn"><input type="button" name="changeAddr" value="변경" onclick="" /></span>
<hr />
<!-- 배송지명, 수취인 이름-전화번호, [우편번호] 주소 -->
<table width="100%" cellpadding="5" cellspacing="0">
<tr>
<td>
	<strong style="font-size:20px;" name="ma_name"><%=memberInfo.getMa_name() %></strong>
	<% if (memberInfo.getMa_basic().equals("y")) { %>
	[기본배송지] 사진으로 대체하기!!!!
	<% } %>
</td>
</tr>
<tr>
<td>[<%=memberInfo.getMa_zip() %>] <%=memberInfo.getMa_addr1() %>&nbsp;<%=memberInfo.getMa_addr2() %></td>
</tr>
<tr>
<td><%=memberInfo.getMa_receiver() %>  <%=memberInfo.getMa_phone() %></td>
</tr>
<tr>
<td>
	<select name="oicmt1" onchange="selectCmt(this.value);">
		<option value="">배송시 요청사항을 선택해주세요.</option>
		<option value="a">부재시 문앞에 놓아주세요.</option>
		<option value="b">배송전에 미리 연락주세요.</option>
		<option value="c">부재시 경비실에 맡겨주세요.</option>
		<option value="d">부재시 전화주시거나 문자 남겨 주세요.</option>
		<option value="e">직접입력</option>
	</select>
	<textarea rows="3" cols="110" name="oicmt2" id="cmte" style="display:none;">oi_cmt에 저장할 내용 적기</textarea>
</td>
</tr>
</table>
</div>
<br />
<div class="point">
<strong class="title">포인트</strong>
<hr />
<input type="text" name="oipoint" onblur="usePoint(this.value, <%=memberInfo.getMi_point() %>);" id="point" value="0" <% if (memberInfo.getMi_point() < 3000) { %> disabled="disabled" <% } %> />&nbsp;<input type="button" value="전액사용" onclick="useAllPoint();"/><br /><br />
<strong>사용가능 포인트 : <span><%=memberInfo.getMi_point() %></span>P</strong><br />
<% if (memberInfo.getMi_point() < 3000) { %>
<font color="red">
<% } %>(보유 포인트가 3000P 이상일 경우 사용이 가능합니다.)<br />
<% if (memberInfo.getMi_point() < 3000) { %> </font> <% } %>
</div>
<br />
<div class="payInfo">
<strong class="title">결제 수단</strong>
<hr />
<p style="width:800px;">
	<input type="radio" name="payment" value="a" />카드결제
	<input type="radio" name="payment" value="b" />네이버페이
	<input type="radio" name="payment" value="c" />카카오페이
	<input type="radio" name="payment" value="d" />핸드폰결제
</p>
<p style="width:800px; text-align:center;">
	<input type="submit" value="상품구매" id="btnsch" />&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="button" value="구매취소" id="btnsch" onclick="history.back();" />
</p>
</div>
</div>
<div class="right">
<div class="pay">
<strong class="title">결제금액</strong><br /><br />
<table class="paytable" width="400">
<tr>
<td>총 상품 금액</td><td align="right"><span id="pdtprice"><%=pdtprice %></span>원</td>
</tr>
<tr>
<% 
int delipay = 3000;
if (pdtprice > 70000) {
	delipay = 0;
}
%>
<td>배송비</td><td align="right"><span id="delipay"><%=delipay %></span>원</td>
</tr>
<tr>
<td>포인트 사용</td><td align="right"><span id="paypoint">0</span>원</td>
</tr>
</table>
<hr />
<table width="400">
<tr>
<td><strong class="title">최종 결제 금액</strong></td><td align="right"><strong class="title"><span id="tp"><%=pdtprice + delipay %></span>원</strong></td>
<tr><td colspan="2" align="right"><%=pdtprice / 100 %>P 적립 예정</td></tr>
</tr>
</table>
<hr />
<input type="checkbox" id="I_agree" name="agree" checked="checked" />
<label for="I_agree" id="disagree">아래 내용에 모두 동의합니다.</label>
<br />
<div id="agreement" style="width:100%; height:100px; overflow:auto; border:1px solid #a2a2a2; padding:2px;">
약관 내용<br />약관 내용<br />약관 내용<br />약관 내용<br />약관 내용<br />약관 내용<br />약관 내용<br />
약관 내용<br />약관 내용<br />약관 내용<br />약관 내용<br />약관 내용<br />약관 내용<br />약관 내용<br />
약관 내용<br />약관 내용<br />약관 내용<br />약관 내용<br />약관 내용<br />약관 내용<br />약관 내용<br />
</div>
본인은 만 14세 이상이고, 위 내용을 확인하였습니다.
</div>
<input type="hidden" name="total" value="<%=pdtprice+delipay %>" />
<input type="hidden" name="total" value="<%=pdtprice+delipay %>" />
<input type="hidden" name="delipay" value="<%=delipay %>" />
<input type="hidden" name="pdtprice" value="<%=pdtprice %>" />
</form>
</div>
</div>
<%@ include file="../footer.jsp" %>