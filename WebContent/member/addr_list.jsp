<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%@ include file="../_inc/incHead.jsp" %>
<%
request.setCharacterEncoding("utf-8");

//MemberInfo memberInfo = (MemberInfo)session.getAttribute("memberInfo");
ArrayList<MemberInfo> addrList = (ArrayList<MemberInfo>)request.getAttribute("addrList");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
.addrList { width:700px; border:1px block solid; font-size:25px; }
.top { margin:0 auto; padding:5px 15px; text-align:center; }
#addAddrBtn { align:right; }
</style>
<script>
function fnBasic(chk) {
	if (confirm("선택하신 주소를 '기본주소'로 등록하시겠습니까?")){
		location.href = "addrProc.jsp?wtype=basic&idx=" + chk;
	} else {
		location.href = "";
	}
}

function fnDel(idx) {
	if (confirm("선택하신 주소를 삭제하시겠습니까?")){
		location.href = "addr_del.mem?maidx=" + idx;
	}
}

var isView = false;
function showAddr(kind, idx) {
	var frm = document.frmAddr;
	var obj = document.getElementById("addrForm");

	frm.zip.value = "";
	frm.addr1.value = "";
	frm.addr2.value = "";
	
	if (kind == "up") {
		isView = false;	// 수정 상황이면 무조건 폼이 보이게 함
		frm.idx.value = idx;	// 수정할 주소의 idx 값
		frm.zip.value = eval("frm.zip" + idx + ".value");
		frm.addr1.value = eval("frm.addr1" + idx + ".value");
		frm.addr2.value = eval("frm.addr2" + idx + ".value");
	}
	if (!isView) {	// btn.value == "새주소 등록 폼"
		isView = true;
		obj.style.display = "block";	
		//btn.value = "새주소 등록 취소";
		frm.wtype.value = kind;
		
	} else {
		isView = false;
		obj.style.display = "none";
		//btn.value = "새주소 등록 폼";
		frm.wtype.value = "";
	}
	
	//var frm = document.frmAddr;
	//var obj = document.getElementById("addrForm");
	//var showbtn = document.getElementById("showbtn");
	//show = !show;
	//if (show) {
	//	obj.style.display = "none";
	//	showbtn.value = "새주소 등록 폼";
	//} else {
	//	obj.style.display = "block";
	//	showbtn.value = "새주소 등록 취소";
	//	frm.wtype.value = "in";
	//}

	//return showbtn; 	// 없어도 됨
}
</script>
</head>
<body>
<div class="addrList">
<div class="top"><%=memberInfo.getMi_email() %>님의 배송지 목록</h2><br />
<span id="addAddrBtn"><input type="button" name="addAddr" value="새 배송지 등록" onclick="location.href='addr_in_form.mem';" /></span></div>
<hr />
<%
if (addrList != null && addrList.size() > 0) {
	for (int i = 0 ; i < addrList.size() ; i++) {
		MemberInfo mi = addrList.get(i);		
%>
<div class="addr<%=i %>">
<table width="100%">
<tr>
<td><strong><%=mi.getMa_name() %></strong></td><td><% if (mi.getMa_basic().equals("y")) { %>기본배송지 <% } %></td>
</tr>
<tr>
<td colspan="2">[<%=mi.getMa_zip() %>] <%=mi.getMa_addr1() %> <%=mi.getMa_addr2() %></td>
</tr>
<tr>
<td colspan="2"><%=mi.getMa_receiver() %> <%=mi.getMa_phone() %></td>
</tr>
<tr>
<td colspan="2" align="right">
	<input type="button" value="수정" onclick="location.href='addr_up_form.mem?maidx=<%=mi.getMa_idx() %>';" />
	<input type="button" value="삭제" onclick="fnDel(<%=mi.getMa_idx() %>);" />
</td>
</tr>
</table>
</div>
<%
	}
}
%>

</div>
</body>
</html>
