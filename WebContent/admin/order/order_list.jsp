<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<% if (request.getParameter("path").equals("direct")) { %>
<%@include file ="../../admin_header.jsp" %>
<% } %>
<%
request.setCharacterEncoding("utf-8");
//request.getParameter("path");
System.out.println(request.getParameter("path"));


ArrayList<CartInfo> cartList = (ArrayList<CartInfo>)request.getAttribute("cartList");
OrderPageInfo pageInfo = (OrderPageInfo)request.getAttribute("pageInfo");

System.out.println("아아, 여기는 admin order_list.jsp");

String args = "", schargs = "";
// 검색 관련 쿼리스트링 제작
if (pageInfo.getOiid() == null)		schargs += "&oiid=";
else								schargs += "&oiid=" + pageInfo.getOiid();

if (pageInfo.getMiemail() == null)	schargs += "&miemail=";
else								schargs += "&miemail=" + pageInfo.getMiemail();

if (pageInfo.getMiname() == null)	schargs += "&miname=";
else								schargs += "&miname=" + pageInfo.getMiname();

if (pageInfo.getMiphone() == null)	schargs += "&miphone=";
else								schargs += "&miphone=" + pageInfo.getMiphone();

if (pageInfo.getSdate() == null)	schargs += "&sdate=";
else								schargs += "&sdate=" + pageInfo.getSdate();

if (pageInfo.getEdate() == null)	schargs += "&edate=";
else 								schargs += "&edate=" + pageInfo.getEdate();

String arrStatus[] = null;
if (pageInfo.getOistatus() == null)	schargs += "&oistatus=";
else {
	arrStatus = pageInfo.getOistatus().split("/");
	for (int i = 0 ; i < arrStatus.length ; i++) {
		schargs += "&oistatus=" + arrStatus[i];
		//System.out.println(arrStatus[0]);
	}
}
/*
if (pageInfo.getOistatuses() == null)	schargs += "&oistatus=";
else {
	for (int i = 0 ; i < pageInfo.getOistatuses().length ; i++) {
	schargs += "&oistatus=" + oistatuses[i];
	System.out.println("schargs" + schargs);
}
*/
//System.out.println(pageInfo.getOistatus());
//System.out.println("oistatuses" + pageInfo.getOistatuses());
//System.out.println("schargs" + schargs);
//System.out.println(getOistatuses().length);

args = "?cpage=" + pageInfo.getCpage() + schargs;
// cpage에 이미 값이 들어있으므로 페이징에서 사용못함
// 페이징에서는 schargs 사용하기

Calendar today = Calendar.getInstance();
int year = today.get(Calendar.YEAR);
int month = today.get(Calendar.MONTH) + 1;
int day = today.get(Calendar.DATE);
today.set(year, month, day);
String todaydate = year + "-" + ((month < 10) ? "0" + month : month) + "-" + ((day < 10) ? "0" + day : day);
//System.out.println(pageInfo.getOistatuses());
%>
<% if (!request.getParameter("path").equals("direct")) { %>
</html>
<head>
<% } %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
.wrapper { margin:0 auto; width:1250px; padding-top:10px; }
#cnt { font-size:20px; font-weight:bold; }
#tMem th, #tMem td  { text-align:center; height:35px; }
.list th { padding-left:20px; }
.btn { width:90px; height:40px; border:2px solid lightblue; }
#btnsch { width:100px; height:35px; }
.ipt { width:140px; height:25px; font-size:18px; font-weight:bold; }
.cmb { width:140px; height:25px; }
tr, td { padding:10px 0; }
</style>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script> 
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script> 
<script>
$(function() {
	$.datepicker.regional['ko'] = {
		closeText: '닫기',
		prevText: '이전달',
		nextText: '다음달',
		currentText: '오늘',
		monthNames: ['1월','2월','3월','4월','5월','6월',
		'7월','8월','9월','10월','11월','12월'],
		monthNamesShort: ['1월','2월','3월','4월','5월','6월',
		'7월','8월','9월','10월','11월','12월'],
		dayNames: ['일','월','화','수','목','금','토'],
		dayNamesShort: ['일','월','화','수','목','금','토'],
		dayNamesMin: ['일','월','화','수','목','금','토'],
		//buttonImage: "/images/kr/create/btn_calendar.gif",
		buttonImageOnly: true,
		// showOn :"both",
		weekHeader: 'Wk',
		dateFormat: 'yy-mm-dd',
		firstDay: 0,
		isRTL: false,
		duration:200,
		showAnim:'show',
		showMonthAfterYear: false
		// yearSuffix: '년'
	};
	$.datepicker.setDefaults($.datepicker.regional['ko']);

	$( "#sdate" ).datepicker({
		//defaultDate: "+1w",
		changeMonth: true,
		//numberOfMonths: 3,
		dateFormat:"yy-mm-dd",
		onClose: function( selectedDate ) {
			//$( "#eday" ).datepicker( "option", "minDate", selectedDate );
		}
	});
	$( "#edate" ).datepicker({
		//defaultDate: "+1w",
		changeMonth: true,
		//numberOfMonths: 3,
		dateFormat:"yy-mm-dd",
		onClose: function( selectedDate ) {
			//$( "#sday" ).datepicker( "option", "maxDate", selectedDate );
		}
	});
});

function setSdate(num) {
	var frm = document.frmSch;
	var sdate = document.getElementById("sdate");
	var edate = document.getElementById("edate");
	var year = <%=year %>
	var month = <%=month %>
	var day = <%=day %>
	
	edate.value = year + "-" + ((month < 10) ? "0" + month : month) + "-" + ((day < 10) ? "0" + day : day);
	
	var tmp = month - num;
	if (tmp < 1 && tmp > -11) {
		year -= 1;
		month = 12 + tmp;
	} else if (tmp < 1 && tmp > -23) { 
		year -= 2;
		month = 24 + tmp;
	} else if (tmp < 1 && tmp > -35) { 
		year -= 3;
		month = 36 + tmp;
	} else {
		month = tmp;
	}
	
	if (month < 10)	month = "0" + month;
	if (day < 10)	day = "0" + day;
	
	
	//alert(year);
	//alert(month);
	//alert(day);
	sdate.value = year + "-" + month + "-" + day;
	//alert(sdate.value);
	frm.submit();
	
}

function getSelectedChk() {	// 선택한 상품의 체크박스들의 value값을 리턴하는 함수
	var idx = "";	// 선택한 상품의 체크박스들의 value값을 저장하는 변수
	var arrChk = document.frmSch.oistatuses;	// 배열
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
	var arrChk = document.frmSch.oistatuses;
	for (var i = 0 ; i < arrChk.length ; i++) {
		arrChk[i].checked = all.checked;
	}
}

function choose(chk) {
	if (!chk.checked) {	// 현재 체크박스를 선택 해제 했을 경우
		//var all = document.frmCart.all;
		//all.checked = false;
		document.frmSch.all.checked = false;
	} else {
		var arrChk = document.frmSch.oistatuses;
		for (var i = 0 ; i < arrChk.length ; i++) {
			if (!arrChk[i].checked)	return false;
		}
		document.frmSch.all.checked = true;
	}	
}
</script>
<% if (request.getParameter("path").equals("direct")) { %>
</head>
<body>
<% } %>
<div class="wrapper">
<h2>주문 목록</h2><hr /><br />
<form name="frmSch" action="" method="get">
<table width="1250" cellpadding="15" class="list">
<tr>
<th width="21%">주문기간</th>
<td width="*">
	<input type="text" name="sdate" id="sdate" size="10" value="<%=pageInfo.getSdate()%>" class="ipt" /> ~
	<input type="text" name="edate" id="edate" size="10" value="<%=pageInfo.getEdate()%>" class="ipt" />&nbsp;&nbsp;
	<input type="button" name="ago1M" id="ago1M" value="1개월 전" onclick="setSdate('1');" class="btn" />&nbsp;&nbsp;
	<input type="button" name="ago3M" id="ago3M" value="3개월 전" onclick="setSdate('3');" class="btn" />&nbsp;&nbsp;
	<input type="button" name="ago6M" id="ago6M" value="6개월 전" onclick="setSdate('6');" class="btn" />&nbsp;&nbsp;
	<input type="button" name="ago1Y" id="ago1Y" value="1년 전" onclick="setSdate('12');" class="btn" />&nbsp;&nbsp;
	<input type="button" name="ago3Y" id="ago3Y" value="2년 전" onclick="setSdate('24');" class="btn" />&nbsp;&nbsp;
	<input type="button" name="ago6Y" id="ago6Y" value="3년 전" onclick="setSdate('36');" class="btn" />
</td>
</tr>
<tr>
<th>주문번호</th>
<td>
	<input type="text" name="oiid" size="20" value="<%=pageInfo.getOiid()%>" class="ipt" />
</td>
</tr>
<tr>
<th>주문자이메일</th>
<td>
	<input type="text" name="miemail" size="20" value="<%=pageInfo.getMiemail()%>" class="ipt" />
</td>
</tr>
<th>주문자명</th>
<td>
	<input type="text" name="miname" size="20" value="<%=pageInfo.getMiname()%>" class="ipt" />
</td>
</tr>
<th>주문자전화번호</th>
<td>
	<input type="text" name="miphone" size="20" value="<%=pageInfo.getMiphone()%>" class="ipt" />
</td>
</tr>
<tr>
<th>주문상태</th>
<td>
	<!-- <input type="checkbox" name="all" id="all" value="" checked="checked" onclick="chkAll(this);" />
	<label for="all">전체</label> -->
	<input type="checkbox" name="oistatuses" id="before" value="a" <% if (Arrays.asList(arrStatus).contains("a")) { %>checked="checked"<% } %> onclick="choose(this);" />
	<label for="Delivery ">입금확인중</label>
	<input type="checkbox" name="oistatuses" id="ready" value="b" <% if (Arrays.asList(arrStatus).contains("b")) { %>checked="checked"<% } %> onclick="choose(this);" />
	<label for="ready">배송준비</label>
	<input type="checkbox" name="oistatuses" id="ship" value="c" <% if (Arrays.asList(arrStatus).contains("c")) { %>checked="checked"<% } %> onclick="choose(this);" />
	<label for="ship">배송중</label>
	<input type="checkbox" name="oistatuses" id="delivery" value="d" <% if (Arrays.asList(arrStatus).contains("d")) { %>checked="checked"<% } %> onclick="choose(this);" />
	<label for="delivery">배송완료</label>
	<input type="checkbox" name="oistatuses" id="ok" value="e" <% if (Arrays.asList(arrStatus).contains("e")) { %>checked="checked"<% } %> onclick="choose(this);" />
	<label for="ok">구매확정</label><br />
	<input type="checkbox" name="oistatuses" id="cancela" value="f" <% if (Arrays.asList(arrStatus).contains("f")) { %>checked="checked"<% } %> onclick="choose(this);" />
	<label for="cancela">취소처리중</label>
	<input type="checkbox" name="oistatuses" id="cancelb" value="g" <% if (Arrays.asList(arrStatus).contains("g")) { %>checked="checked"<% } %> onclick="choose(this);" />
	<label for="cancelb">취소완료</label><br />
	<input type="checkbox" name="oistatuses" id="refunda" value="h" <% if (Arrays.asList(arrStatus).contains("h")) { %>checked="checked"<% } %> onclick="choose(this);" />
	<label for="refunda">환불처리중</label>
	<input type="checkbox" name="oistatuses" id="refundb" value="i" <% if (Arrays.asList(arrStatus).contains("i")) { %>checked="checked"<% } %> onclick="choose(this);" />
	<label for="refundb">환불완료</label><br />
	<input type="checkbox" name="oistatuses" id="changea" value="j" <% if (Arrays.asList(arrStatus).contains("j")) { %>checked="checked"<% } %> onclick="choose(this);" />
	<label for="changea">교환처리중</label>
	<input type="checkbox" name="oistatuses" id="changeb" value="k" <% if (Arrays.asList(arrStatus).contains("k")) { %>checked="checked"<% } %> onclick="choose(this);" />
	<label for="changeb">교환완료</label>
</td>
</tr>
<tr><td height="20"></td></tr>
<tr><td colspan="4" align="center">
	<input type="submit" value="검색" id="btnsch" />
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="reset" value="초기화" id="btnsch" />
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="button" value="전체 검색" id="btnsch" onclick="location.href='order_list.orda';" />
</td></tr>
</table>
</form>
<hr />
<span id="cnt">검색된 회원수 : <%=pageInfo.getRcnt() %></span><br />
<table style="border:1px solid black;" width="1250" cellpadding="5" cellspacing="0" id="tMem">
<tr>
<th width="12%">주문번호</th>
<th width="12%">회원이름</th>
<th width="12%">회원이메일</th>
<th width="*">전화번호</th>
<th width="12%">결제방법</th>
<th width="12%">결제일자</th>
<th width="12%">주문상태</th>
<th width="12%">상세보기</th>
</tr>
<%
if (cartList != null && cartList.size() > 0) {
	for (int i = 0 ; i < cartList.size() ; i++) {	
		//System.out.println(pdtList.size());
		CartInfo ci = cartList.get(i);
%>
	<tr>
		<td align="center"><%=ci.getOi_id() %></td>
		<td align="center"><%=ci.getMi_name() %></td>
		<td align="center"><%=ci.getMi_email() %></td>
		<td align="center"><%=ci.getMi_phone() %></td>
		<%
		String x = ci.getOi_payment();
		String payment = "";
		if (x.equals("a"))	payment = "카드결제";
		if (x.equals("b"))	payment = "무통장입금";
		if (x.equals("c"))	payment = "카카오페이";
		if (x.equals("d"))	payment = "네이버페이";
		if (x.equals("e"))	payment = "핸드폰결제";
		%>
		<td align="center"><%=payment %></td>
		<td align="center"><%=ci.getOi_date().substring(0, 10) %></td>
		<%
		x = ci.getOi_status();
		String status = "";
		if (x.equals("a"))	status = "결제대기";
		if (x.equals("b"))	status = "배송준비";
		if (x.equals("c"))	status = "배송중";
		if (x.equals("d"))	status = "배송완료";
		if (x.equals("e"))	status = "구매확정";
		if (x.equals("f"))	status = "취소처리중";
		if (x.equals("g"))	status = "취소완료";
		if (x.equals("h"))	status = "환불처리중";
		if (x.equals("i"))	status = "환불완료";
		if (x.equals("j"))	status = "교환처리중";
		if (x.equals("k"))	status = "교환완료";		
		%>
		<td align="center"><%=status %></td>
		<td align="center">
			<a href="order_detail.orda?miemail=<%=ci.getMi_email() %>&oiid=<%=ci.getOi_id() %>&path=direct">
			상세보기</a>
		</td>
	</tr>
<%
	}
} else {
	out.println("<tr><th colspan='10'>검색 결과가 없습니다.</th></tr>");
}
%>
</table>
</div>
<br />
<br />
<br />
<br />
<br />
</body>
</html>