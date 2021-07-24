<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%@include file ="../../admin_header.jsp" %>
<%
ArrayList<ProductInfo> pdtList = (ArrayList<ProductInfo>)request.getAttribute("pdtList");
ArrayList<CataBigInfo> cataBigList = 
	(ArrayList<CataBigInfo>)request.getAttribute("cataBigList");	// 대분류 목록
ArrayList<CataSmallInfo> cataSmallList = 
	(ArrayList<CataSmallInfo>)request.getAttribute("cataSmallList");// 소분류 목록
//ArrayList<BrandInfo> brandList = 
//	(ArrayList<BrandInfo>)request.getAttribute("brandList");		// 브랜드 목록
PdtPageInfo pageInfo = (PdtPageInfo)request.getAttribute("pageInfo");

String args = "", schargs = "";
// 검색관련 쿼리스트링 제작
if (pageInfo.getIsview() == null)	schargs += "&isview=";
else								schargs += "&isview=" + pageInfo.getIsview();	// null이 아니거나 빈문자열이면
if (pageInfo.getSchtype() == null)	schargs += "&schtype=";
else								schargs += "&schtype=" + pageInfo.getSchtype();
if (pageInfo.getKeyword() == null)	schargs += "&keyword=";
else								schargs += "&keyword=" + pageInfo.getKeyword();
if (pageInfo.getBcata() == null)	schargs += "&bcata=";
else								schargs += "&bcata=" + pageInfo.getBcata();
if (pageInfo.getScata() == null)	schargs += "&scata=";
else								schargs += "&scata=" + pageInfo.getScata();
//if (pageInfo.getBrand() == null)	schargs += "&brand=";
//else								schargs += "&brand=" + pageInfo.getBrand();
if (pageInfo.getSprice() == null)	schargs += "&sprice=";
else								schargs += "&sprice=" + pageInfo.getSprice();
if (pageInfo.getEprice() == null)	schargs += "&eprice=";
else								schargs += "&eprice=" + pageInfo.getEprice();
if (pageInfo.getSdate() == null)	schargs += "&sdate=";
else								schargs += "&sdate=" + pageInfo.getSdate();
if (pageInfo.getEdate() == null)	schargs += "&edate=";
else								schargs += "&edate=" + pageInfo.getEdate();
if (pageInfo.getStock() == null)	schargs += "&stock=";
else								schargs += "&stock=" + pageInfo.getStock();

args = "?cpage=" + pageInfo.getCpage() + schargs;
%>
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
		monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
		monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
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

<%	// 스크립트 배열 만드는 법
String cbid = "", arrName = "";   // 대분류ID, 대분류/소분류
int j = 0;
for (int i = 0 ; i < cataSmallList.size() ; i++) {   // cataSmallList.size() = 6
	if (!cbid.equals(cataSmallList.get(i).getCb_id())) {
		cbid = cataSmallList.get(i).getCb_id();
		arrName = "arr" + cbid;
		out.println("var " + arrName + " = new Array();");
		out.println(arrName + "[0] = new Option(\"\", \"소분류 전체\");");
		j = 1;
	}
	out.println(arrName + "[" + j + "] = new Option(\"" + 
	cataSmallList.get(i).getCs_id() + "\", \"" + cataSmallList.get(i).getCs_name() + "\");");
	j++;
}
%>
function setCategory(x, target) {
// x : 선택한 대분류 ID, target : 선택한 대분류에 속한 소분류를 보여줄 컨트롤 객체
	// 1. 원래 소분류 콤보박스에 있던 데이터(option 태그)들을 모두 삭제
	for (var i = target.options.length - 1 ; i > 0 ; i--) {
	// 삭제는 반대방향으로 해야 효율적임(속도가 빠름) : 0번은 지우지 않고 남겨둠(콤보박스의 모양유지를 위해서)
	// 앞에서부터 지우면 한 칸씩 당겨지는데 굳이 그럴 필요가 없음
		target.options[i] = null;	// null을 넣으면 삭제가 됨
	}
	
	// 2. 선택한 대분류에 속하는 소분류 데이터를 소분류 콤보박스에 넣음
	if (x != "") {   // 대분류를 선택했으면
		var arr = eval("arr" + x);
		// 대분류에서 선택한 값에 따라 사용할 배열을 지정 - 소분류 콤보박스에서 보여줄 option 요소들이 있는 배열
		// if문으로 x==1 -> arr1, x==2 -> arr2f로 해도 됨
		
		for (var i = 0 ; i < arr.length ; i++) {
			target.options[i] = new Option(arr[i].value, arr[i].text);
			// 지정한 arr 배열만큼 target에 option 요소 지정
		}
		target.options[0].selected = true;
		// target의 0번 인덱스에 해당하는 option 태그를 선택한 상태로 만듬
	}
}
</script>
<style>
.ipt, .cmb { width:150px; }
a:link { color:#4f4f4f; text-decoration:none; }
a:visited { color:#4f4f4f; text-decoration:none; }
a:hover { color:#2788ff; text-decoration:none; }
a:focus { color:#2788ff; text-decoration:none; }
a:active { color:#2788ff; text-decoration:none; }
.btn { width:90px; height:40px; border:1px solid lightblue; }
#btnsch { width:100px; height:35px; }
.ipt { width:140px; height:25px; font-size:18px; font-weight:bold; }
.cmb { width:140px; height:25px; }
tr, td { padding:10px 0; }
th { padding-left:20px; }
.wrapper { margin:0 auto; width:1250px; padding-top:10px; }
.pdt-page { margin:0 auto; width:800px; }
</style>

<div class="wrapper">
	<h2>상품 관리</h2><hr /><br />
	<form name="frmSch" action="" method="get">
	<input type="hidden" name="ord" value="<%=pageInfo.getOrd() %>" />
	<table width="1250" cellpadding="5">
	<tr>
	<th width="10%">분류</th>
	<td width="40%">
		<select name="bcata" onchange="setCategory(this.value, this.form.scata);" class="cmb">
			<option value="">대분류 전체</option>
	<%
	for (CataBigInfo cata : cataBigList) {
		String slt = "";
		if (pageInfo.getBcata() != null && pageInfo.getBcata().equals(cata.getCb_id()))
			slt = " selected='selected'";
	%>
			<option value="<%=cata.getCb_id() %>"<%=slt %>><%=cata.getCb_name() %></option>
	<% } %>
		</select>&nbsp;&nbsp;&nbsp;
		<select name="scata" class="cmb">
			<option value="">소분류 전체</option>
	<%
	if (pageInfo.getBcata() != null && !pageInfo.getBcata().equals("")) {
	// 대분류 검색조건이 있을 경우
		for (CataSmallInfo cata : cataSmallList) {
			if (pageInfo.getBcata().equals(cata.getCb_id())) {
			// 검색조건의 대분류와 동일한 대분류를 가진 소분류일 경우
				String slt = "";
				if (pageInfo.getScata() != null && pageInfo.getScata().equals(cata.getCs_id()))
					slt = " selected='selected'";
	%>
			<option value="<%=cata.getCs_id() %>"<%=slt %>><%=cata.getCs_name() %></option>
	<%
			}
		}
	}
	%>
		</select>
	</td>
	<!-- 
	<th width="10%">브랜드</th>
	<td width="40%">
		<select name="brand">
			<option value="">브랜드 전체</option>
	<%
	//for (BrandInfo br : brandList) {
		//String slt = "";
		//if (pageInfo.getBrand() != null && pageInfo.getBrand().equals(br.getB_name()))
			//slt = " selected='selected'";
	%>
			<option value=""></option>
	<%// } %>
		</select>
	</td>
	-->
	</tr>
	<tr>
	<th>검색어</th>
	<td>
		<select name="schtype" class="cmb">
			<option value="name"<% if (pageInfo.getSchtype().equals("name")) { %> selected="selected"<% } %>>상품명</option>
			<option value="id"<% if (pageInfo.getSchtype().equals("id")) { %> selected="selected"<% } %>>상품코드</option>
		</select>&nbsp;&nbsp;&nbsp;
		<input type="text" name="keyword" value="<%=pageInfo.getKeyword() %>" class="ipt" />
	</td>
	<th>가격대</th>
	<td>
		<input type="text" name="sprice" size="10" value="<%=pageInfo.getSprice()%>" />원 ~
		<input type="text" name="eprice" size="10" value="<%=pageInfo.getEprice()%>" />원
	</td>
	</tr>
	<tr>
	<th>재고량</th>
	<td><input type="text" name="stock" size="10" value="<%=pageInfo.getStock() %>" class="ipt" /> 개 이상</td>
	<th>등록기간</th>
	<td>
		<input type="text" name="sdate" id="sdate" value="<%=pageInfo.getSdate() %>" class="ipt" /> ~
		<input type="text" name="edate" id="edate" value="<%=pageInfo.getEdate() %>" class="ipt" />
	</td>
	</tr>
	<tr>
	<th>게시여부</th>
	<td colspan="3">
		<input type="radio" name="isview" value="" <% if (pageInfo.getIsview().equals("")) { %>checked="checked"<% } %> /> 전체 검색&nbsp;&nbsp;
		<input type="radio" name="isview" value="y" <% if (pageInfo.getIsview().equals("y")) { %>checked="checked"<% } %> /> 게시중&nbsp;&nbsp;
		<input type="radio" name="isview" value="n" <% if (pageInfo.getIsview().equals("n")) { %>checked="checked"<% } %> /> 미게시
	</td>
	</tr>
	<tr><td colspan="4" align="center">
		<input type="submit" value="상품 검색" id="btnsch" />
		&nbsp;&nbsp;
		<input type="reset" value="다시 입력" id="btnsch" />
		&nbsp;&nbsp;
		<input type="button" value="전체 검색" id="btnsch" onclick="location.href='pdt_list.pdta';" />
		&nbsp;&nbsp;
		<input type="button" value="상품 등록" id="btnsch" onclick="location.href='pdt_in_form.pdta?cpage=1';" />
	</td></tr>
	</table>
	</form>
	<hr />
	<p style="width:1250px;" align="right">
		<a href="pdt_list.pdta<%=args%>&ord=lastdated" <% if (pageInfo.getOrd().equals("lastdated")) { %>style="font-weight:bold;color:#2788ff;"<% } %>>신상품순</a>&nbsp;
		<a href="pdt_list.pdta<%=args%>&ord=salecntd" <% if (pageInfo.getOrd().equals("salecntd")) { %>style="font-weight:bold;color:#2788ff;"<% } %>>인기순</a>&nbsp;
		<a href="pdt_list.pdta<%=args%>&ord=namea" <% if (pageInfo.getOrd().equals("namea")) { %>style="font-weight:bold;color:#2788ff;"<% } %>>상품명</a>&nbsp;
		<a href="pdt_list.pdta<%=args%>&ord=pricea" <% if (pageInfo.getOrd().equals("pricea")) { %>style="font-weight:bold;color:#2788ff;"<% } %>>낮은 가격순</a>&nbsp;
		<a href="pdt_list.pdta<%=args%>&ord=priced" <% if (pageInfo.getOrd().equals("priced")) { %>style="font-weight:bold;color:#2788ff;"<% } %>>높은 가격순</a>&nbsp;
		<a href="pdt_list.pdta<%=args%>&ord=reviewcntd" <% if (pageInfo.getOrd().equals("reviewcntd")) { %>style="font-weight:bold;color:#2788ff;"<% } %>>리뷰순</a>&nbsp;&nbsp;
	</p>
	<table width="1250" cellpadding="5">
	<% 
	if (pdtList != null && pdtList.size() > 0) {	// 상품 검색결과가 있으면
		for (int i = 0 ; i < pdtList.size() ; i++) {	// 상품이미지, 상품명, 가격 
			ProductInfo pi = pdtList.get(i);
			String lnk = "<a href=\"pdt_up_form.pdta" + args + "&id=" + pi.getPi_id() + "&ord=" + pageInfo.getOrd() + "\">";
			String st = pi.getPi_stock() + "";	// 문자열로 변경
			if (pi.getPi_stock() == -1)		st = "무제한";
			String view = "게시중";
			if (pi.getPi_isview().equals("n"))	view = "미게시";
	%>
	<tr>
	<td width="130" align="center"><%=lnk %><img src="../product/pdt_img/<%=pi.getPi_img1() %>" width="100" height="100" /></a></td>
	<td width="*">
		<%=lnk + pi.getPi_name() %></a>
		<%=pi.getCb_name() %> -> <%=pi.getCs_name() %><br />
		<%=pi.getBr_name() %> / 재고량 : <%=st %> / <%=view %>
	</td>
	<td width="100"><strong><%=pi.getPi_price() %></strong> 원</td>
	</tr>
	<%
		}
	} else {
		out.println("<tr><th>검색 결과가 없습니다.</th></tr>");
	}
	%>
	</table>
	<div class="pdt-page"><br /><br />
		<%
		if (pdtList != null && pdtList.size() > 0) {
			args = "?ord=" + pageInfo.getOrd() + "&psize=" + pageInfo.getPsize() + schargs;	
			
			out.println("<p style=\"width:800px;\" align=\"center\">");
			
			if (pageInfo.getCpage() == 1) {	// 현재 페이지 번호가 1이면
				out.println("[&lt;&lt;]&nbsp;&nbsp;[&lt;]&nbsp;&nbsp;");
			} else {
				out.print("<a href='pdt_list.pdta" + args + "&cpage=1" + "'>");
				out.println("[&lt;&lt;]</a>&nbsp;&nbsp;");
				out.print("<a href='pdt_list.pdta" + args + "&cpage=" + (pageInfo.getCpage() - 1) + "'>");
				out.println("[&lt;]</a>&nbsp;&nbsp;");
			} // 첫 페이지와 이전 페이지 링크
			
			for (int i = 1, k = pageInfo.getSpage() ; i <= pageInfo.getBsize() && k <= pageInfo.getEpage() ; i++, k++) {
				if (pageInfo.getCpage() == k) {
					out.print("&nbsp;<strong>" + k + "</strong>&nbsp;");
				} else {
					out.print("&nbsp;<a href='pdt_list.pdta" + args + "&cpage=" + k + "'>");
					out.print(k + "</a>&nbsp;");
				}
			}
		
			if (pageInfo.getCpage() == pageInfo.getPcnt()) {	// 현재 페이지 번호가 마지막 페이지 번호이면
				out.println("&nbsp;&nbsp;[&gt;]&nbsp;&nbsp;[&gt;&gt;]");
			} else {
				out.println("&nbsp;&nbsp;<a href='pdt_list.pdta" + args + "&cpage=" + (pageInfo.getCpage() + 1) + "'>[&gt;]</a>");
				out.print("&nbsp;&nbsp;<a href='pdt_list.pdta" + args + "&cpage=" + pageInfo.getPcnt() + "'>");
				out.println("[&gt;&gt;]</a>");
			} // 마지막 페이지와 이전 페이지 링크
		
			out.println("</p>");
		}
		%>
	</div>
	<br /><br /><br /><br /><br />
</div>
</body>
</html>
