<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%@include file="../../admin_header.jsp" %>
<%
ArrayList<MonthlyInfo> monthList = (ArrayList<MonthlyInfo>)request.getAttribute("monthList");
MonthlyPageInfo pageInfo = (MonthlyPageInfo)request.getAttribute("pageInfo");

String args = "", schargs = "";
// 검색관련 쿼리스트링 제작
if (pageInfo.getIsview() == null)	schargs += "&isview=";
else								schargs += "&isview=" + pageInfo.getIsview();	// null이 아니거나 빈문자열이면
if (pageInfo.getSchtype() == null)	schargs += "&schtype=";
else								schargs += "&schtype=" + pageInfo.getSchtype();
if (pageInfo.getKeyword() == null)	schargs += "&keyword=";
else								schargs += "&keyword=" + pageInfo.getKeyword();
if (pageInfo.getSdate() == null)	schargs += "&sdate=";
else								schargs += "&sdate=" + pageInfo.getSdate();
if (pageInfo.getEdate() == null)	schargs += "&edate=";
else								schargs += "&edate=" + pageInfo.getEdate();

String[] arrMonth = {""};
if (pageInfo.getMonth() == null) {
	schargs += "&month=";
} else {
	arrMonth = pageInfo.getMonth().split("/");
	for (int i = 0 ; i < arrMonth.length ; i++) {
		schargs += "&month=" + arrMonth[i];
	}
}
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
</script>
<style>
a:link { color:#4f4f4f; text-decoration:none; }
a:visited { color:#4f4f4f; text-decoration:none; }
a:hover { color:#2788ff; text-decoration:none; }
a:focus { color:#2788ff; text-decoration:none; }
a:active { color:#2788ff; text-decoration:none; }

tr, td { padding:10px 0; }
.wrapper { margin:0 auto; width:1250px; padding-top:10px; }
.frmSch { margin:0 auto; width:1250px; padding:30px 0; }
.ipt, .cmb { width:130px; }
.month-btn { margin:5px 0; width:150px; height:35px; }
.month-page { margin:0 auto; width:1250px; }
.btn { width:90px; height:40px; border:2px solid lightblue; }
.ipt { width:140px; height:25px; font-size:18px; font-weight:bold; }
.cmb { width:140px; height:25px; }
#btnsch { width:100px; height:35px; }
tr, td { padding:10px 0; }
th { padding-left:20px; }
</style>

<div class="wrapper">
<h2>이달의추천 게시글 관리</h2><hr /><br />
	<div class="frmSch">
		<form name="frmSch" action="" method="get">
		<input type="hidden" name="ord" value="<%=pageInfo.getOrd() %>" />
		<table width="1250" cellpadding="5" class="table1">
		<tr>
		<th>검색어</th>
		<td>
			<select name="schtype" class="cmb">
				<option value="title"<% if (pageInfo.getSchtype().equals("title")) { %> selected="selected"<% } %>>게시글 제목</option>
				<option value="name"<% if (pageInfo.getSchtype().equals("name")) { %> selected="selected"<% } %>>캠핑장 이름</option>
			</select>
			<input type="text" name="keyword" class="cmb" value="<%=pageInfo.getKeyword() %>" />
		</td>
		<th>등록기간</th>
		<td>
			<input type="text" name="sdate" id="sdate" value="<%=pageInfo.getSdate() %>" size="10" class="ipt" /> ~
			<input type="text" name="edate" id="edate" value="<%=pageInfo.getEdate() %>" size="10" class="ipt" />
		</td>
		</tr>
		<tr>
		<th>추천 월</th>
		<td colspan="3">
		<% 
		for (int i = 1 ; i <= 12 ; i++) { 
			String j = i < 10 ? "0" + i : "" + i;
		%>
			<label for="month<%=j%>"><input type="checkbox" name="month" id="month<%=j%>" value="<%=j%>" <% if(Arrays.asList(arrMonth).contains(j)) { %>checked="checked"<% } %>> <%=i%>월</label>
		<% } %>
		</td>
		</tr>
		<tr>
		<th>게시여부</th>
		<td colspan="3">
			<input type="radio" name="isview" value="" <% if (pageInfo.getIsview().equals("")) { %>checked="checked"<% } %> /> 전체 검색&nbsp;&nbsp;
			<input type="radio" name="isview" value="y" <% if (pageInfo.getIsview().equals("y")) { %>checked="checked"<% } %> /> 게시중&nbsp;&nbsp;
			<input type="radio" name="isview" value="n" <% if (pageInfo.getIsview().equals("n")) { %>checked="checked"<% } %> /> 미게시&nbsp;&nbsp;
		</td>
		</tr>
		<tr><td colspan="4" align="center">
			<input type="submit" value="게시글 검색" id="btnsch" />
			&nbsp;&nbsp;
			<input type="reset" value="다시 입력" id="btnsch" />
			&nbsp;&nbsp;
			<input type="button" value="전체 검색" id="btnsch" onclick="location.href='month_list.montha';" />
			&nbsp;&nbsp;
			<input type="button" value="게시글 등록" id="btnsch" onclick="location.href='month_in_form.montha?cpage=1';" />
		</td></tr>
		</table>
		</form>
	</div>
	<hr />
	<p style="width:1250px;" align="right">
		<a href="month_list.montha?cpage=1<%=schargs%>&ord=lastdated" <% if (pageInfo.getOrd().equals("lastdated")) { %>style="font-weight:bold;color:#2788ff;"<% } %>>최신등록순</a>&nbsp;
		<a href="month_list.montha?cpage=1<%=schargs%>&ord=goodd" <% if (pageInfo.getOrd().equals("goodd")) { %>style="font-weight:bold;color:#2788ff;"<% } %>>인기순</a>&nbsp;
		<a href="month_list.montha?cpage=1<%=schargs%>&ord=replyd" <% if (pageInfo.getOrd().equals("replyd")) { %>style="font-weight:bold;color:#2788ff;"<% } %>>댓글순</a>&nbsp;&nbsp;
	</p>
	<table width="1250" cellpadding="5" class="table2">
	<% 
	if (monthList != null && monthList.size() > 0) {	// 게시글 검색결과가 있으면
		for (int i = 0 ; i < monthList.size() ; i++) {
			MonthlyInfo mi = monthList.get(i);
			String lnk = "<a href=\"month_up_form.montha" + args + "&idx=" + mi.getMr_idx() + "&ord=" + pageInfo.getOrd() + "\">";
			String view = "게시중";
			if (mi.getMr_isview().equals("n"))	view = "미게시";
	%>
	<tr>
	<td width="150" align="center"><%=lnk %><img src="../monthly/month_img/<%=mi.getMr_imgs().substring(0, mi.getMr_imgs().indexOf("/")) %>" width="140" height="85" /></a></td>
	<td width="*">
		<%=lnk + mi.getMr_title() %></a><br />
		<%=mi.getMr_name() %> / <%=mi.getMr_location() %> / <%=view %><br />
		<%=mi.getMr_content().replace("\r\n", "<br />") %>
	</td>
	<td width="100">
		<input type="button" value="미리보기" id="btnsch" class="month-btn" onclick="location.href='../monthly/month_view.month?cpage=1&idx=<%=mi.getMr_idx() %>'"/><br />
		<input type="button" value="수정하기" id="btnsch" class="month-btn" onclick="location.href='month_up_form.montha<%=args %>&idx=<%=mi.getMr_idx() %>&ord=<%=pageInfo.getOrd() %>'" />
	</td>
	</tr>
	<%
		}
	} else {
		out.println("<tr align='center'><th>검색 결과가 없습니다.</th></tr>");
	}
	%>
	</table>
	<div class="month-page"><br /><br />
		<%
		if (monthList != null && monthList.size() > 0) {
			args = "?ord=" + pageInfo.getOrd() + "&psize=" + pageInfo.getPsize() + schargs;	
			
			out.println("<p style=\"width:800px;\" align=\"center\">");
			
			if (pageInfo.getCpage() == 1) {	// 현재 페이지 번호가 1이면
				out.println("[&lt;&lt;]&nbsp;&nbsp;[&lt;]&nbsp;&nbsp;");
			} else {
				out.print("<a href='month_list.montha" + args + "&cpage=1" + "'>");
				out.println("[&lt;&lt;]</a>&nbsp;&nbsp;");
				out.print("<a href='month_list.montha" + args + "&cpage=" + (pageInfo.getCpage() - 1) + "'>");
				out.println("[&lt;]</a>&nbsp;&nbsp;");
			} // 첫 페이지와 이전 페이지 링크
			
			for (int i = 1, k = pageInfo.getSpage() ; i <= pageInfo.getBsize() && k <= pageInfo.getEpage() ; i++, k++) {
				if (pageInfo.getCpage() == k) {
					out.print("&nbsp;<strong>" + k + "</strong>&nbsp;");
				} else {
					out.print("&nbsp;<a href='month_list.montha" + args + "&cpage=" + k + "'>");
					out.print(k + "</a>&nbsp;");
				}
			}
		
			if (pageInfo.getCpage() == pageInfo.getPcnt()) {	// 현재 페이지 번호가 마지막 페이지 번호이면
				out.println("&nbsp;&nbsp;[&gt;]&nbsp;&nbsp;[&gt;&gt;]");
			} else {
				out.println("&nbsp;&nbsp;<a href='month_list.montha" + args + "&cpage=" + (pageInfo.getCpage() + 1) + "'>[&gt;]</a>");
				out.print("&nbsp;&nbsp;<a href='month_list.montha" + args + "&cpage=" + pageInfo.getPcnt() + "'>");
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
