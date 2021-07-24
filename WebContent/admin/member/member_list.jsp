<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%@include file ="../../admin_header.jsp" %>
<%
ArrayList<MemberInfo> memberList = (ArrayList<MemberInfo>)request.getAttribute("memberList");

MemberPageInfo pageInfo = (MemberPageInfo)request.getAttribute("pageInfo");

String args = "", schargs = "";

//검색 관련 쿼리스트링 제작
if (pageInfo.getEmail() == null)	schargs += "&email=";
else								schargs += "&email=" + pageInfo.getEmail();

if (pageInfo.getNick() == null)		schargs += "&nick=";
else								schargs += "&nick=" + pageInfo.getNick();

if (pageInfo.getPhone() == null)	schargs += "&phone=";
else								schargs += "&phone=" + pageInfo.getPhone();

if (pageInfo.getSage() == null) 	schargs += "&sage=";
else								schargs += "&sage=" + pageInfo.getSage();

if (pageInfo.getEage() == null) 	schargs += "&eage=";
else								schargs += "&eage=" + pageInfo.getEage();

if (pageInfo.getIsactive() == null)	schargs += "&isactive=";
else								schargs += "&isactive=" + pageInfo.getIsactive();

if (pageInfo.getGender() == null) 		schargs += "&gender=";
else									schargs += "&gender=" + pageInfo.getGender();

if (pageInfo.getJoinsdate() == null) 	schargs += "&joinsdate=";
else									schargs += "&joinsdate=" + pageInfo.getJoinsdate();

if (pageInfo.getJoinedate() == null) 	schargs += "&joinedate=";
else									schargs += "&joinedate=" + pageInfo.getJoinedate();

if (pageInfo.getLastsdate() == null) 	schargs += "&lastsdate=";
else									schargs += "&lastsdate=" + pageInfo.getLastsdate();

if (pageInfo.getLastedate() == null) 	schargs += "&lastedate=";
else									schargs += "&lastedate=" + pageInfo.getLastedate();

args = "?cpage=" + pageInfo.getCpage() + schargs;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
.text { size:500px; }
.wrapper { margin:0 auto; width:1250px; padding-top:10px; }
#tMem th, #tMem td  { text-align:center; height:35px; }
#cnt { font-size:20px; font-weight:bold; margin-bottom:5px; }
#sch { height:35px; }
.btn { width:90px; height:40px; border:1px solid lightblue; }
#btnsch { width:100px; height:35px; }
.ipt { width:140px; height:25px; font-size:18px; font-weight:bold; }
.cmb { width:140px; height:25px; }
tr, td { padding:10px 0; }
th { padding-left:20px; }
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

	$( "#joinsdate" ).datepicker({
		//defaultDate: "+1w",
		changeMonth: true,
		//numberOfMonths: 3,
		dateFormat:"yy-mm-dd",
		onClose: function( selectedDate ) {
			//$( "#eday" ).datepicker( "option", "minDate", selectedDate );
		}
	});
	$( "#joinedate" ).datepicker({
		//defaultDate: "+1w",
		changeMonth: true,
		//numberOfMonths: 3,
		dateFormat:"yy-mm-dd",
		onClose: function( selectedDate ) {
			//$( "#sday" ).datepicker( "option", "maxDate", selectedDate );
		}
	});
});

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

	$( "#lastsdate" ).datepicker({
		//defaultDate: "+1w",
		changeMonth: true,
		//numberOfMonths: 3,
		dateFormat:"yy-mm-dd",
		onClose: function( selectedDate ) {
			//$( "#eday" ).datepicker( "option", "minDate", selectedDate );
		}
	});
	$( "#lastedate" ).datepicker({
		//defaultDate: "+1w",
		changeMonth: true,
		//numberOfMonths: 3,
		dateFormat:"yy-mm-dd",
		onClose: function( selectedDate ) {
			//$( "#sday" ).datepicker( "option", "maxDate", selectedDate );
		}
	});
});

function getCnt() {
	var cnt = document.getElementById("cnt");
	
	cnt.innerHTML = "검색된 회원수 : <%=pageInfo.getRcnt() %>";
}

var isDesc = false;
function getSort(val) {
	if (!isDesc) {
		isDesc = true;
		location.href = "member_list.mema" + <%=args %> + "&ord=" + val + "d";
	} else {
		location.href = "member_list.mema" + <%=args %> + "&ord=" + val + "a";
		
	}
	
}
</script>
<div class="wrapper">
<h2>회원 관리</h2><hr /><br />
<form name="frmSch" action="" method="get">
<table width="1250" cellpadding="5" cellspacing="0" id="sch">
<tr>
<th width="10%">이메일</th>
<td width="40%">
<input type="text" name="email" class="text"
	value="<% if (pageInfo.getEmail() != null && !pageInfo.getEmail().equals("")) {
					out.println(pageInfo.getEmail()); } %>" />
</td>
<th width="10%">활동여부</th>
<td width="40%">
	<input type="radio" name="isactive" value="" <% if (pageInfo.getIsactive().equals("")) { %> checked="checked" <% } %> />전체 검색
	<input type="radio" name="isactive" value="y" <% if (pageInfo.getIsactive().equals("y")) { %> checked="checked" <% } %> />활동회원
	<input type="radio" name="isactive" value="n" <% if (pageInfo.getIsactive().equals("n")) { %> checked="checked" <% } %> />탈퇴회원
</td>
</tr>
<tr>
<th>별명</th>
<td>
<input type="text" name="nick" maxlength="12" class="text"
	value="<% if (pageInfo.getNick() != null && !pageInfo.getNick().equals("")) { 
					out.println(pageInfo.getNick()); } %>" />
</td>
<th>성별</th>
<td>
	<input type="radio" name="gender" value="" <% if (pageInfo.getGender().equals("")) { %> checked="checked" <% } %> />전체 검색
	<input type="radio" name="gender" value="m" <% if (pageInfo.getGender().equals("m")) { %> checked="checked" <% } %> />남성
	<input type="radio" name="gender" value="f" <% if (pageInfo.getGender().equals("f")) { %> checked="checked" <% } %> />여성
</td>
</tr>
<tr>
<th>전화번호</th>
<td><input type="text" name="phone" class="text" value="<%=pageInfo.getPhone() %>" /></td>
<th>가입일</th>
<td>
	<input type="text" name="joinsdate" id="joinsdate" size="10" value="<%=pageInfo.getJoinsdate() %>" class="ipt" /> ~
	<input type="text" name="joinedate" id="joinedate" size="10" value="<%=pageInfo.getJoinedate() %>" class="ipt" />
</td>
<tr>
<th>나이</th>
<td>
	<select name="sage" class="cmb">
      <option value="" ></option>
      <% 
      for (int i = 1 ; i <= 100 ; i++) {
         String slt = "";
         if (pageInfo.getSage() != null && !pageInfo.getSage().equals("")) {
        	 if (Integer.parseInt(pageInfo.getSage().trim()) == i)	slt = " selected='selected'";
         }
      %>
      <option value="<%=i %>" <%=slt %>><%=i %></option>
      <% } %>
   </select> ~
   <select name="eage" class="cmb">
      <option value="" ></option>
      <%
      for (int i = 1 ; i <= 100 ; i++) { 
         String slt = "";
         if (pageInfo.getEage() != null && !pageInfo.getEage().equals("")) {
        	 if (Integer.parseInt(pageInfo.getEage().trim()) == i)	slt = " selected='selected'";
         }
      %>
      <option value="<%=i %>" <%=slt %>><%=i %></option>
      <% } %>
   </select>
</td>
<th>최근 로그인</th>
<td>
	<input type="text" name="lastsdate" id="lastsdate" size="10" value="<%=pageInfo.getLastsdate() %>" class="ipt" /> ~
	<input type="text" name="lastedate" id="lastedate" size="10" value="<%=pageInfo.getLastedate() %>" class="ipt" />
</td>
</tr>
<tr><td height="20"></td></tr>
<tr><td colspan="4" align="center">
	<input type="submit" value="검색" id="btnsch" onclick="getCnt();" />
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="reset" value="초기화" id="btnsch" />
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="button" value="전체검색" id="btnsch" onclick="location.href='member_list.mema';" />
</td></tr>
</table>
</form>
<hr />
<span id="cnt">검색된 회원수 : <%=pageInfo.getRcnt() %></span><br />
<table style="border:1px solid black;" width="1250" cellpadding="5" cellspacing="0" id="tMem">
<tr><!-- <a href="member_list.mema args &ord=emaild"> -->
<th width="12%">이메일</th>
<th width="*">닉네임</th>
<th width="12%">전화번호</th><th width="10%">생년월일</th>
<th width="5%">성별</th><th width="10%">배송지</th>
<th width="8%">활동여부</th><th width="10%">최근로그인</th>
<th width="10%">가입일</th><th width="8%">상세보기</th>
</tr>
<%
if (memberList != null && memberList.size() > 0) {
// 회원 검색 결과가 있으면
	for (int i = 0 ; i < memberList.size() ; i++) {	
		//System.out.println(pdtList.size());
		MemberInfo mi = memberList.get(i);
%>
	<tr>
		<td align="center"><%=mi.getMi_email() %></td>
		<td align="center"><%=mi.getMi_nick() %></td>
		<td align="center"><%=mi.getMi_phone() %></td>
		<td align="center"><%=mi.getMi_birth() %></td>
		<td align="center"><%=mi.getMi_gender() %></td>
		<td align="center">기본주소지</td>
		<td align="center"><%=mi.getMi_isactive() %></td>
		<td align="center"><%=mi.getMi_lastlogin() %></td>
		<td align="center"><%=mi.getMi_joindate().substring(0, 10) %></td>
		<td align="center">
			<a href="member_info.mema?email=<%=mi.getMi_email() %>">
			상세보기</a>
		</td>
	</tr>
<%
	}
} else {
	out.println("<tr><th colspan='10'>검색 결과가 없습니다.</th></tr>");
}
%>
</div>
</table>
<br />
<br />
<br />
<br />
<br />
</body>
</html>
