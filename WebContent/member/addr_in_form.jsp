<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
.addAddr { width:600px; text-align:center; }
.title { width:100%; font-size:25px; }
.info { text-align:left; }
.ipt { width:220px; }
</style>
</head>
<body>
<div class="addAddr">
<div class="title">
배송지 등록
</div>
<div class="info">
<form name="addAddr" action="addr_in_proc.mem" method="post">
<table width="100%" class="table">
<tr><th width="25%">배송지명</th><td width="*"><input type="text" name="maname" value="" class="ipt" /></td></tr>
<tr><th>받는사람</th><td><input type="text" name="mareceiver" value="" class="ipt" /></td></tr>
<tr>
<th>전화번호</th>
<td>
	<select name="p1"> -
		<option value="010">010</option>
		<option value="011">011</option>
		<option value="016">016</option>
		<option value="019">019</option>
	</select> - 
	<input type="text" name="p2" size="4" maxlength="4" /> - 
	<input type="text" name="p3" size="4" maxlength="4" />
</td>
</tr>
<tr><th>주소</th><td><input type="text" name="mazip" value="" style="width:145px;" /> <input type="button" style="valign:center;" value="주소찾기" onclick="" /></td></tr>
<tr><th></th><td><input type="text" name="maaddr1" value="" class="ipt" /></td></tr>
<tr><th></th><td><input type="text" name="maaddr2" value="" class="ipt" /></td></tr>
<tr><td colspan="2" align="center"><input type="checkbox" id="basic" name="mabasic" value="y" />
<label for="basic">기본 배송지로 저장</label></td></tr>
</table>
</div>
<input type="submit" value="등록" />
</form>
</div>
</body>
</html>