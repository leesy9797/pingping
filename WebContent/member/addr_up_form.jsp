<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%
MemberInfo memberInfo = (MemberInfo)request.getAttribute("memberInfo");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
.utdAddr { width:600px; text-align:center; }
.title { width:100%; font-size:25px; }
.info { text-align:left; }
.ipt { width:220px; }
</style>
</head>
<body>
<div class="addAddr">
<div class="title">
배송지 수정
</div>
<div class="info">
<form name="utdAddr" action="addr_up_proc.mem" method="post">
<input type="hidden" name="maidx" value="<%=memberInfo.getMa_idx() %>" />
<table width="100%" class="table">
<tr><th width="25%">배송지명</th><td width="*"><input type="text" name="maname" value="<%=memberInfo.getMa_name() %>" class="ipt" /></td></tr>
<tr><th>받는사람</th><td><input type="text" name="mareceiver" value="<%=memberInfo.getMa_receiver() %>" class="ipt" /></td></tr>
<tr>
<th>전화번호</th>
<td>
<% String arrPhone[] = memberInfo.getMa_phone().split("-"); %>
	<select name="p1"> -
		<option value="010" <% if (arrPhone[0].equals("010")) { %>selected="selected" <% } %>>010</option>
		<option value="011" <% if (arrPhone[0].equals("011")) { %>selected="selected" <% } %>>011</option>
		<option value="016" <% if (arrPhone[0].equals("016")) { %>selected="selected" <% } %>>016</option>
		<option value="019" <% if (arrPhone[0].equals("019")) { %>selected="selected" <% } %>>019</option>
	</select> - 
	<input type="text" name="p2" size="4" maxlength="4" value="<%=arrPhone[1] %>" /> - 
	<input type="text" name="p3" size="4" maxlength="4" value="<%=arrPhone[2] %>" />
</td>
</tr>
<tr><th>주소</th><td><input type="text" name="mazip" value="<%=memberInfo.getMa_zip() %>" style="width:145px;" /> <input type="button" style="valign:center;" value="주소찾기" onclick="" /></td></tr>
<tr><th></th><td><input type="text" name="maaddr1" value="<%=memberInfo.getMa_addr1() %>" class="ipt" /></td></tr>
<tr><th></th><td><input type="text" name="maaddr2" value="<%=memberInfo.getMa_addr2() %>" class="ipt" /></td></tr>
<tr><td colspan="2" align="center"><input type="checkbox" id="basic" name="mabasic" value="y" <% if (memberInfo.getMa_basic().equals("y")) { %> checked="checked" <% } %> />
<label for="basic">기본 배송지로 저장</label></td></tr>
</table>
</div>
<input type="submit" value="수정" />
</form>
</div>
</body>
</html>