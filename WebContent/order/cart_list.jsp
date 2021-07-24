<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%@include file ="../header.jsp" %>
<%
/*
if (!isLogin) {
	out.println("<script>");
	out.println("alert('로그인 후에 이용하실 수 있습니다.');");
	out.println("history.back();");
	out.println("</script>");
	out.close();
}
*/
ArrayList<CartInfo> cartList = (ArrayList<CartInfo>)request.getAttribute("cartList");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
body { margin:0; }
.outer { margin:0 auto; width:1250px; }
#cartTable th { border-bottom:3px black double; }
#cartTable td { border-bottom:1px black solid; }

a:link { color:#4f4f4f; text-decoration:none; }
a:visited { color:#4f4f4f; text-decoration:none; }
a:hover { color:#2788ff; text-decoration:none; }
a:focus { color:#2788ff; text-decoration:none; }
a:active { color:#2788ff; text-decoration:none; }

tr, td { padding:5px 0; }

.btn { width:90px; height:40px; border:2px solid lightblue; }
#btnsch2 { width:130px; height:35px; }
#btnsch { width:25px; height:25px; }
.ipt { width:150px; font-size:18px; font-weight:bold; }
.cmb { width:120px; height:25px; }
</style>
<script src="jquery-3.6.0.js"></script>
<script>
function changeOpt(idx, opt, piid) {
	$.ajax({
		type : "POST",			
		url : "/pingping/cart_up_proc.ord",	
		data : {"kind":"opt", "op":opt, "idx":idx, "piid":piid},	
		success : function(chkRst) {
			if (chkRst == 0) {	// 옵션 변경에 실패했을 경우
				alert("상품 옵션 변경에 실패했습니다.\n다시 시도해 주세요.")	
			} else {	
				location.reload();
			} 	
		}
	});	
}

function changeCnt(op, idx, cnt, max) {	// 장바구니의 수량을 변경시키는 함수
	if (op == "+" && (cnt + 1) > max) {
		alert("구매가능한 수량(" + max + "개)을 초과했습니다.");
		return;
	} else if (op == "-" && (cnt - 1) < 1) {
		alert("최소 1개는 주문해야 합니다.");
		return;
	}
	
	$.ajax({
		type : "POST",			
		url : "/pingping/cart_up_proc.ord",	
		data : {"kind":"cnt", "op":op, "idx":idx, "piid":""},	
		success : function(chkRst) {
			if (chkRst == 0) {	// 수량 변경에 실패했을 경우
				alert("상품 수량 변경에 실패했습니다.\n다시 시도해 주세요.")	
			} else {	
				location.reload();
			} 	
		}
	});	
}

function clearCart(kind) {	// kind : 삭제할 상품들
	var msg = "지정한 상품을 ";
	var idx = kind;
	
	if (kind == -1) {
		msg = "선택한 상품들을 모두 ";
		idx = getSelectedChk();	// 선택된 체크박스의 value들을 받아옴
		if (idx == "") { 
			alert("삭제할 상품을 선택해야 합니다.");
			return;
		}
	} else if (kind == 0) {
		msg = "장바구니 안의 모든 상품을 ";
	}
	
	if (confirm(msg + "삭제하시겠습니까?")) {
		$.ajax({
			type : "POST",			
			url : "/pingping/cart_del_proc.ord",	
			data : {"kind":kind, "idx":idx},	
			success : function(chkRst) {
				if (chkRst == 0) {	// 삭제에 실패했을 경우
					alert("상품 삭제에 실패했습니다.\n다시 시도해 주세요.")	
				} else {	
					location.reload();
				} 	
			}
		});	
	}
}

function getSelectedChk() {	// 선택한 상품의 체크박스들의 value값을 리턴하는 함수
	var idx = "";	// 선택한 상품의 체크박스들의 value값을 저장하는 변수
	var arrChk = document.frmCart.chk;	// 배열
	for (var i = 0 ; i < arrChk.length ; i++) {
		if (arrChk[i].checked) {
			//if (idx == "")	idx += arrChk[i];
			//else			idx += "," + arrChk[i];
			idx += "," + arrChk[i].value;
		}
	}
	if (idx != "")	idx = idx.substring(1);
	
	return idx;
}

function chkAll(all) {
	var totalprice = document.getElementById("total").value;
	var arrChk = document.frmCart.chk;
	for (var i = 0 ; i < arrChk.length ; i++) {
		arrChk[i].checked = all.checked;
	}
	if (all.checked) {
		document.getElementById("totalprice").innerHTML = totalprice;
	} else {
		document.getElementById("totalprice").innerHTML = "0";		
	}
}

function choose(chk, price) {
	var nowprice = document.getElementById("totalprice").innerHTML;
	
	if (!chk.checked) {	// 현재 체크박스를 선택 해제 했을 경우
		//var all = document.frmCart.all;
		//all.checked = false;
		nowprice -= price;
		document.getElementById("totalprice").innerHTML = nowprice;
		
		document.frmCart.all.checked = false;
	} else {
		nowprice = nowprice * 1 + price;
		document.getElementById("totalprice").innerHTML = nowprice;
		
		var arrChk = document.frmCart.chk;
		for (var i = 0 ; i < arrChk.length ; i++) {
			if (!arrChk[i].checked)	return false;
		}
		document.frmCart.all.checked = true;
	}	
}

function goOrder(kind) {
	if (kind == "a") {	// 전체 상품 구매하기일 경우
		var arrChk = document.frmCart.chk;
		for (var i = 0 ; i < arrChk.length ; i++) {
			arrChk[i].checked = true;
		}	// 모든 체크박스를 체크된 상태로 변경
	}
	
	document.frmCart.submit();
}
</script>
<div class="outer">
<h2>장바구니</h2>
<form name="frmCart" action="order_form.ord" method="post">
<input type="hidden" name="kind" value="cart" />
<table width="1250" cellpadding="5" cellspacing="0" id="cartTable">
<tr>
<th width="5%"><input type="checkbox" name="all" checked="checked" onclick="chkAll(this);" /></th>
<th width="*">상품</th>
<th width="10%">옵션</th>
<th width="15%">수량</th>
<th width="10%">단가</th>
<th width="10%">가격</th>
<th width="10%">삭제</th>
</tr>
<% 
if (cartList != null && cartList.size() > 0) {	// 장바구니에 상품이 들어 있으면
	int total = 0;	// 총 구매가격을 누적할 변수
	for (int i = 0 ; i < cartList.size() ; i++) {
		CartInfo cart = cartList.get(i);
		int idx = cart.getOc_idx();	// 카트 인덱스 번호
		int price = cart.getPi_price() * cart.getOc_cnt();
		System.out.println(price);
		String lnk = "<a href=\"pdt_view.pdt?id=" + cart.getPi_id() + "\">";
		int max = cart.getPi_stock();
		if (max == -1)	max = 1000;
	%>
	<tr align="center">
	<td><input type="checkbox" name="chk" value="<%=idx %>" checked="checked" onclick="choose(this, <%=price %>);" /></td>
	<td align="left">
		<%=lnk %><img src="product/pdt_img/<%=cart.getPi_img1() %>" width="50" height="60" /></a>
		[<%=cart.getBr_name() %>] <%=lnk + cart.getPi_name() %></a>
	</td>
	<td>
		<select name="opt" class="cmb"  onchange="changeOpt(<%=idx %>, this.value, '<%=cart.getPi_id() %>');">
<%
//if (option != null && !option.equals("")) {
	String[] arrOpt = cart.getPi_option().split(";");	
	for (int j = 0 ; j < arrOpt.length ; j++) {
		String slt = "";
		if (cart.getOc_option().equals(arrOpt[j]))	slt = " selected='selected'";
		out.println("<option value='" + arrOpt[j] + "'" + slt + ">" + arrOpt[j] + "</option>");
	}
//} 
%>
		</select>
	</td>
	<td>
		<input type="button" id="btnsch" value="-" onclick="changeCnt(this.value, <%=idx %>, <%=cart.getOc_cnt()%>, 0);" />
		<span id="cnt<%=idx%>"><%=cart.getOc_cnt()%></span>
		<input type="button" id="btnsch" value="+" onclick="changeCnt(this.value, <%=idx %>, <%=cart.getOc_cnt()%>, <%=max %>);" />
	</td>
	<td><%=cart.getPi_price() %></td>
	<td><%=cart.getPi_price() * cart.getOc_cnt() %></td>
	<td><input type="button" style="width:50px;" value="삭 제" onclick="clearCart(<%=idx %>);" /></td>
	</tr>
<%
		total += cart.getPi_price() * cart.getOc_cnt();
		// 장바구니 내의 상품 총 가격
	}
%>
</table>
<p style="width:1250px; text-align:right; padding-top:15px; font-weight:bold; color:#2788ff; font-size:20px;">총 결제 금액 : <span id="totalprice"><%=total %></span>&nbsp;&nbsp;</p>
<p style="width:1250px; text-align:right;">
	<input type="button" value="장바구니 비우기" id="btnsch2" onclick="clearCart(0);" />&nbsp;&nbsp;
	<input type="button" value="선택 상품 삭제" id="btnsch2" onclick="clearCart(-1);" />&nbsp;&nbsp;
	<input type="button" value="선택 상품 구매" id="btnsch2" onclick="goOrder('s');" />&nbsp;&nbsp;
	<input type="button" value="전체 상품 구매" id="btnsch2" onclick="goOrder('a');" />&nbsp;&nbsp;
</p>
<input type="hidden" id="total" value="<%=total %>" />
<% 
} else {	// 장바구니가 비었으면
%>
<p style="width:1250px; text-align:center;">장바구니가 비었습니다.</p>
<%
}
%>
<p style="width:1250px; text-align:center;">
	<input type="button" value="계속 쇼핑하기" id="btnsch2" onclick="location.href='pdt_list.pdt';" />
</p>
</form>
</div>
<%@ include file="../footer.jsp" %>