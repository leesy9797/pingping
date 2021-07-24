<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%@include file ="../header.jsp" %>
<%
if (!isLogin) {
	out.println("<script>");
	out.println("alert('로그인 후에 이용하실 수 있습니다.');");
	out.println("location.href='../login_form.jsp';");
	out.println("</script>");
	out.close();
}

request.setCharacterEncoding("utf-8");

ArrayList<CartInfo> orderList = (ArrayList<CartInfo>)request.getAttribute("orderList");
OrderPageInfo pageInfo = (OrderPageInfo)request.getAttribute("pageInfo");

String args = "", schargs = "";
// 검색 관련 쿼리스트링 제작
if (pageInfo.getSdate() == null)	schargs += "&sdate=";
else								schargs += "&sdate=" + pageInfo.getSdate();

if (pageInfo.getEdate() == null)	schargs += "&edate=";
else								schargs += "&edate=" + pageInfo.getEdate();

if (pageInfo.getOistatus() == null)	schargs += "&status=";
else								schargs += "&status=" + pageInfo.getOistatus();

if (pageInfo.getSchdate() == null)	schargs += "&schdate=";
else								schargs += "&schdate=" + pageInfo.getSchdate();

args = "?cpage=" + pageInfo.getCpage() + schargs;
// cpage에 이미 값이 들어있으므로 페이징에서 사용못함
// 페이징에서는 schargs 사용하기
System.out.println(pageInfo.getEpage());
Calendar today = Calendar.getInstance();
int year = today.get(Calendar.YEAR);
int month = today.get(Calendar.MONTH) + 1;
int day = today.get(Calendar.DATE);
today.set(year, month, day);
String todaydate = year + "-" + ((month < 10) ? "0" + month : month) + "-" + ((day < 10) ? "0" + day : day);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
.wrapper {
	width:1250px; margin:0 auto; height:1800px; padding-top:20px;
}
k { color:#fff; text-decoration:none; }
/* a:visited { color:#fff; text-decoration:none; }*/
a:link { color:#4f4f4f; text-decoration:none; }
a:visited { color:#4f4f4f; text-decoration:none; }
a:hover { color:#2788ff; text-decoration:none; font-weight:bold; }
a:focus { color:#2788ff; text-decoration:none; }
a:active { color:#2788ff; text-decoration:none; }

.status div { list-style-type:none; display:inline; padding:5px 15px; margin:5 15px; background:lightblue; float:left; text-align:center; }
.orderTable th { border-bottom:3px black double; }
.orderTable td { border-bottom:1px black solid; }
.orderTable td { height:130px; }
.btn { width:90px; height:40px; border:2px solid lightblue; }
#btnsch { width:40px; height:25px; }
.ipt { width:150px; font-size:18px; font-weight:bold; }
.cmb { width:120px; height:25px; }
.schBox { padding-top:30px; padding-left:30px; }
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
	var frm = document.orderlist;
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

function showList(val) {
	var frm = document.orderlist;
	var status = frm.status.value;
	status = val;
	//alert(status);
	frm.submit();
}
</script>
<div class="wrapper">
<h2>주문배송조회</h2>
<form name="orderlist" action="order_list.ord" method="get">
<div class="schBox">
<strong>SEARCH</strong>&nbsp;&nbsp;
<input type="text" align="absmiddle" name="sdate" id="sdate" size="10" value="<%=pageInfo.getSdate()%>" class="ipt" />&nbsp;&nbsp;~&nbsp;
<input type="text" name="edate" id="edate" size="10" value="<%=pageInfo.getEdate()%>" class="ipt" />&nbsp;&nbsp;
<input type="button" value="검색" id="btnsch" onclick="showList('');" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type="button" name="ago1M" id="ago1M" value="1개월 전" onclick="setSdate('1');" class="btn" />&nbsp;&nbsp;
<input type="button" name="ago3M" id="ago3M" value="3개월 전" onclick="setSdate('3');" class="btn" />&nbsp;&nbsp;
<input type="button" name="ago6M" id="ago6M" value="6개월 전" onclick="setSdate('6');" class="btn" />&nbsp;&nbsp;
<input type="button" name="ago1Y" id="ago1Y" value="1년 전" onclick="setSdate('12');" class="btn" />&nbsp;&nbsp;
<input type="button" name="ago3Y" id="ago3Y" value="2년 전" onclick="setSdate('24');" class="btn" />&nbsp;&nbsp;
<input type="button" name="ago6Y" id="ago6Y" value="3년 전" onclick="setSdate('36');" class="btn" /><br />
</div>
<!-- 
<div class="status">
<input type="button" name="status" value="전체" onclick="showList('');" class="btn" />&nbsp;&nbsp;
<input type="button" name="status" value="입금확인중" onclick="showList('a');" class="btn" />&nbsp;&nbsp;
<input type="button" name="status" value="배송준비" onclick="showList('b');" class="btn" />&nbsp;&nbsp;
<input type="button" name="status" value="배송중" onclick="showList('c');" class="btn" />&nbsp;&nbsp;
<input type="button" name="status" value="배송완료" onclick="showList('d');" class="btn" />&nbsp;&nbsp;
<input type="button" name="status" value="구매확정" onclick="showList('e');" class="btn" />&nbsp;&nbsp;
</div>
 -->
<!-- 	
	<div>
		<a href="javascript:showList('');">전체</a>
	</div>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<div>
		<a href="javascript:showList('a');">입금확인중</a>
	</div>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<div>
		<a href="javascript:showList('b');">배송준비</a>
	</div>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<div>
		<a href="javascript:showList('c');">배송중</a>
	</div>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<div>
		<a href="javascript:showList('d');">배송완료</a>
	</div>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<div>
		<a href="javascript:showList('e');">구매확정</a>
	</div>
 -->
<br />
<hr />
<select name="schdate" onchange="setSdate(this.value);" class="cmb">
	<option value="">기간</option>
	<option value="1" <% if (pageInfo.getSchdate() != null && pageInfo.getSchdate().equals("1")) { %> selected="selected" <% } %>>1개월 전</option>
	<option value="3" <% if (pageInfo.getSchdate() != null && pageInfo.getSchdate().equals("3")) { %> selected="selected" <% } %>>3개월 전</option>
	<option value="6" <% if (pageInfo.getSchdate() != null && pageInfo.getSchdate().equals("6")) { %> selected="selected" <% } %>>6개월 전</option>
	<option value="12" <% if (pageInfo.getSchdate() != null && pageInfo.getSchdate().equals("12")) { %> selected="selected" <% } %>>1년 전</option>
	<option value="24" <% if (pageInfo.getSchdate() != null && pageInfo.getSchdate().equals("24")) { %> selected="selected" <% } %>>2년 전</option>
	<option value="36" <% if (pageInfo.getSchdate() != null && pageInfo.getSchdate().equals("36")) { %> selected="selected" <% } %>>3년 전</option>
</select>&nbsp;&nbsp;
<select name="status" onchange="showList(this.value);" class="cmb">
	<option value="">주문상태</option>
	<option value="a" <% if (pageInfo.getOistatus() != null && pageInfo.getOistatus().equals("a")) { %> selected="selected" <% } %>>입금확인중</option>
	<option value="b" <% if (pageInfo.getOistatus() != null && pageInfo.getOistatus().equals("b")) { %> selected="selected" <% } %>>배송준비</option>
	<option value="c" <% if (pageInfo.getOistatus() != null && pageInfo.getOistatus().equals("c")) { %> selected="selected" <% } %>>배송중</option>
	<option value="d" <% if (pageInfo.getOistatus() != null && pageInfo.getOistatus().equals("d")) { %> selected="selected" <% } %>>배송완료</option>
	<option value="e" <% if (pageInfo.getOistatus() != null && pageInfo.getOistatus().equals("e")) { %> selected="selected" <% } %>>구매확정</option>
</select>&nbsp;&nbsp;
<input type="reset" value="전체 검색" onclick="location.href='order_list.ord?path=direct';" valign="center" />
<br /><br />
<span style="display:none;">여기 선택한 필터 나오는 영역</span>
</form>
<h3 class="listBox">
<% 
String date1 = "";
if(pageInfo.getSdate() != null && !pageInfo.getSdate().equals("")) { 
	date1 = pageInfo.getSdate() + "~"; 
} 

String date2 = "";
if(pageInfo.getEdate() != null && !pageInfo.getEdate().equals("")) { 
	date2 = pageInfo.getEdate() + " "; 
} else {
	date2 = todaydate;
}
%>
	<i><%=date1 %><%=date2 %></i> 까지의 주문내역 총 <i class="c_pink"><%=pageInfo.getCnt() %></i> 건
</h3>
<table class="orderTable" width="1250" cellpadding="5">
<h3 align="center"><strong>주문/배송조회 목록</strong></h3>
<tr align="center">
<th width="20%">주문번호</th>
<th colspan="2" width="*">상품이미지/브랜드명/상품명</th>
<th width="15%">옵션/수량/주문금액</th>
<th width="20%">배송추적/리뷰작성</th>
</tr>
<% 
if (orderList != null && orderList.size() > 0) {
// 주문목록이 있으면
	for (int i = 0 ; i < orderList.size() ; i++) {
		CartInfo oi = orderList.get(i);
		String lnk = "<a href=\"pdt_view.pdt" + args + "&id=" + oi.getPi_id() +
					 "&psize=" + pageInfo.getPsize() + "\">";
		
%>
<tr>
<td align="center"><a href="order_detail.ord<%=args %>&oiid=<%=oi.getOi_id() %>"><strong><%=oi.getOi_id() %></strong><br /><span style="font-size:14px;">[<%=oi.getOi_date().substring(0, 10) %>]</span></a></td>

<td align="center"><%=lnk %><img src="product/pdt_img/<%=oi.getPi_img1() %>" width="90" height="90" /></a></td>
<td><%=lnk %>[<%=oi.getBr_name() %>]<br /><%=oi.getPi_name() %></a></td>
<td>
	옵션 : <%=oi.getOd_option() %> | 수량 : <%=oi.getOd_cnt() %>개<br />
	가격 : <%=oi.getOd_pdtprice() %>원<br /><br />
	<strong style="font-size:14px;">총 구매가격 : <%=oi.getOd_pdtprice()*oi.getOd_cnt() %>원</strong><br />
	<span id="buy" style="font-weight:bold; margin-top:10px; color:red;"><% if (oi.getOi_status().equals("e")) { %>구매확정<% } else { %> <% } %></span>
</td>
<td align="center">
	<% 
	String stylebtn1 = "visibility:hidden;";
	String stylebtn2 = "visibility:hidden;";
	if (oi.getOi_status().equals("c") || oi.getOi_status().equals("d") || oi.getOi_status().equals("e")) {
		stylebtn1 = "visibility:visible;";
		if (oi.getOi_status().equals("e")) {
			stylebtn2 = "visibility:visible;";
		}
	}
	System.out.println(oi.getOi_status());
	System.out.println(stylebtn1);
	System.out.println(stylebtn2);
	%>
	<input type="button" style=<%=stylebtn1 %> value="배송추적" class="btn" onclick="alert('송장내역 보여주기!');" /><br /><br />
	<input type="button" style=<%=stylebtn2 %> value="리뷰작성" class="btn" onclick="location.href='review/review_list.review';" />
</td>
</tr>
<%
	}
} else {
	out.println("<tr><th colspan='5'>검색 결과가 없습니다.</th></tr>");
}
%>
</table>
<br />
<% // 페이징 영역
if (orderList != null && orderList.size() > 0) {
	args = "?psize=" + pageInfo.getPsize() + schargs;
	out.println("<p style=\"width:1250px\" align=\"center\">");

	if (pageInfo.getCpage() == 1) {	// 현재 페이지 번호가 1이면
		out.println("[&lt;&lt;]&nbsp;&nbsp;[&lt;]&nbsp;&nbsp;");
	} else {
		out.print("<a href='order_list.ord" + args + "&cpage=1" + "'>");
		out.println("[&lt;&lt;]</a>&nbsp;&nbsp;");
		out.print("<a href='order_list.ord" + args +
			"&cpage=" + (pageInfo.getCpage() - 1) + "'>");
		out.println("[&lt;]</a>&nbsp;&nbsp;");
	}	// 첫 페이지와 이전 페이지 링크
	
	for (int i = 1, k = pageInfo.getSpage() ; i <= pageInfo.getBsize() && k <= pageInfo.getEpage() ; i++, k++) {
		if (pageInfo.getCpage() == k) {	// 현재 페이지 번호일 경우 링크없이 강조만 함
			out.print("&nbsp;<strong>" + k + "</strong>&nbsp;");
		} else {
			out.print("&nbsp;<a href='order_list.ord" + args + "&cpage=" + k + "'>");
			out.print(k + "</a>&nbsp;");
		}
	}
	
	if (pageInfo.getCpage() == pageInfo.getPcnt()) {	// 현재 페이지 번호가 마지막 페이지 번호이면
		out.println("&nbsp;&nbsp;[&gt;]&nbsp;&nbsp;[&gt;&gt;]");
	} else {
		out.println("&nbsp;&nbsp;<a href='order_list.ord" + args +
			"&cpage=" + (pageInfo.getCpage() + 1) + "'>[&gt;]</a>");
		out.print("&nbsp;&nbsp;<a href='order_list.ord" + args +
			"&cpage=" + pageInfo.getPcnt() + "'>");
		out.println("[&gt;&gt;]</a>");	
	}

out.println("</p>");
}
%>
</div>
<%@ include file="../footer.jsp" %>