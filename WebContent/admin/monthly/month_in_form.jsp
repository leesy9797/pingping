<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%@include file="../../admin_header.jsp" %>
<%
String[] arrInfo1 = {"오토캠핑", "글램핑", "카라반", "방갈로", "펜션", "기타"};
String[] arrInfo2 = {"화장실", "샤워실", "개수대", "매점"};
String[] arrInfo3 = {"무선인터넷", "전기사용", "화로대사용", "장비대여", "장작판매", "온수사용", "애완동물"};
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
.ipt, .cmb { width:300px; }
a:link { color:#4f4f4f; text-decoration:none; }
a:visited { color:#4f4f4f; text-decoration:none; }
a:hover { color:#2788ff; text-decoration:none; }
a:focus { color:#2788ff; text-decoration:none; }
a:active { color:#2788ff; text-decoration:none; }

tr, td { padding:10px 0; }
td { text-align:center; }
.left { text-align:left; padding-left:50px; }
#btnsch { width:100px; height:35px; }
.wrapper { margin:0 auto; width:1250px; padding-top:10px; }
</style>

<div class="wrapper">
	<h2>이달의추천 게시글 등록</h2><hr /><br />
	<form name="frmCamp" action="month_proc.montha" method="post" enctype="multipart/form-data">
	<input type="hidden" name="wtype" value="in" />
	<table width="1250" cellpadding="10" cellspacing="0">
	<tr>
	<th width="15%" class="left">게시글 제목</th>
	<td width="*"><input type="text" name="title" class="ipt" value="여름에는 가평 계곡으로" /></td>
	<th width="15%">캠핑장 이름</th>
	<td width="35%"><input type="text" name="name" class="ipt" value="가평 캠핑장" /></td>
	</tr>
	<tr>
	<th class="left">캠핑장 설명</th>
	<td><textarea rows="10" cols="38" name="content">계곡이 바로 옆에 있는 캠핑장</textarea></td>
	<th>키워드</th>
	<td>
		<textarea rows="10" cols="38" name="mkeyword" placeholder="#을 구분자로 띄어쓰기 없이 입력해주세요.&#13;&#10;&#13;&#10;예) 텐트#방수포#랜턴#버너">텐트#방수포#랜턴#버너</textarea><br />
	</td>
	</tr>
	<tr>
	<th class="left">캠핑장 위치</th>
	<td><input type="text" name="location" class="ipt" placeholder="경기도 가평군" value="경기도 가평군" /></td>
	<th>캠핑장 사이트 url</th>
	<td><input type="text" name="url" class="ipt" placeholder="https://camping.com/" value="https://camping.com/" /></td>
	</tr>
	<tr>
	<!-- 이미지 받아서 하나의 컬럼으로 구분자 "/" -->
	<th class="left">이미지1</th>
	<td><input type="file" name="img1" /></td>
	<th>이미지6</th>
	<td><input type="file" name="img6" /></td>
	</tr>
	<tr>
	<th class="left">이미지2</th>
	<td><input type="file" name="img2" /></td>
	<th>이미지7</th>
	<td><input type="file" name="img7" /></td>
	</tr>
	<tr>
	<th class="left">이미지3</th>
	<td><input type="file" name="img3" /></td>
	<th>이미지8</th>
	<td><input type="file" name="img8" /></td>
	</tr>
	<tr>
	<th class="left">이미지4</th>
	<td><input type="file" name="img4" /></td>
	<th>이미지9</th>
	<td><input type="file" name="img9" /></td>
	</tr>
	<tr>
	<th class="left">이미지5</th>
	<td><input type="file" name="img5" /></td>
	<th>이미지10</th>
	<td><input type="file" name="img10" /></td>
	</tr>
	<tr>
	<th class="left">캠핑장 정보</th>
	<td colspan="3" class="left" style="padding-left:70px;">
	<strong>캠핑형태</strong>
	<% for (int i = 0 ; i < arrInfo1.length ; i++) { %>
		<label for="info1<%=i+1%>"><input type="checkbox" name="info" id="info1<%=i+1%>" value="<%=arrInfo1[i]%>"> <%=arrInfo1[i]%></label>
	<% } %>
	<br /><strong>기본시설</strong>
	<% for (int i = 0 ; i < arrInfo2.length ; i++) { %>
		<label for="info2<%=i+1%>"><input type="checkbox" name="info" id="info2<%=i+1%>" value="<%=arrInfo2[i]%>"> <%=arrInfo2[i]%></label>
	<% } %>
	<br /><strong>이용조건</strong>
	<% for (int i = 0 ; i < arrInfo3.length ; i++) { %>
		<label for="info3<%=i+1%>"><input type="checkbox" name="info" id="info3<%=i+1%>" value="<%=arrInfo3[i]%>"> <%=arrInfo3[i]%></label>
	<% } %>
	</td>
	</tr>
	<tr>
	<th class="left">추천 월</th>
	<td colspan="3" class="left" style="padding-left:70px;">
	<% 
	for (int i = 1 ; i <= 12 ; i++) { 
		String j = i < 10 ? "0" + i : "" + i;
	%>
		<label for="month<%=j%>"><input type="checkbox" name="month" id="month<%=j%>" value="<%=j%>" /> <%=i%>월</label>
	<% } %>
	</td>
	</tr>
	<tr>
	<th class="left">게시여부</th>
	<td colspan="3" class="left" style="padding-left:70px;">
		<input type="radio" name="misview" value="y" /> 게시&nbsp;&nbsp;
		<input type="radio" name="misview" value="n" checked="checked" /> 미게시
	</td>
	</tr>
	<tr>
	<th class="left">상품등록</th>
	<td class="left" style="padding-left:70px;">
		<iframe id="iframePdt" height="400" src="../camping/search_product.camp" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
	</td>
	<td colspan="2" style="padding-left:70px;" valign="top">
		<div class="pdtList">
	      <input type="hidden" name="pdtid" value="" />
	      <span id="pdtArea" style="display:none;"></span>
	      <span style="font-weight:bold; font-size:20px;">태그된 상품들</span><hr />
	      <span id="imgArea" style="text-align:left;"></span>
   		</div>
	</td>
	</tr>
	<tr><td colspan="4" align="center">
		<br /><br />
		<input type="submit" value="게시글 등록" id="btnsch" />
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="reset" value="다시 입력" id="btnsch" />
	</td></tr>
	</table>
	</form>
</div>
</body>
</html>
