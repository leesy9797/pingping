<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%
request.setCharacterEncoding("utf-8");

ArrayList<ProductInfo> pdtList = (ArrayList<ProductInfo>)request.getAttribute("pdtList");
PdtPageInfo pageInfo = (PdtPageInfo)request.getAttribute("pageInfo");

String schargs = "";

if (pageInfo.getKeyword() == null)	schargs += "&keyword=";
else								schargs += "&keyword=" + pageInfo.getKeyword();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
body { margin:0 auto; }
.outerBox {
	margin:0 auto; border:0px black solid; width:300px; height:300px;
	align:center; padding:5px;
}

.list {
	width:300px; height:300px; overflow:auto;
}

#cnt { display:none; }
</style>
<script>
var cnt = 0;
var img = "";

function getChecked(chk)  {
	var val = parent.document.frmCamp.pdtid.value;
	
	if (chk.checked) {
		cnt++;
		if (cnt > 5) {
			alert('상품 태그는 최대 5개까지만 가능합니다.');
			chk.checked = false;
			return;
		}
		val = val + "/" + chk.value;
		img = img + "<img src='../product/pdt_img/" + chk.value + ".jpg' width='126' height='126' class='img' />";
		//alert(parent.document.frmCamp.pdtid.value);
	} else {
		cnt--;
		var i = val.indexOf(chk.value);	// 있는지 확인
		var j = val.indexOf("/", i);	
		
		var a = img.indexOf("<img src='../product/pdt_img/" + chk.value);	// 83
		
		if (j == -1) {
			val = val.substring(0, i - 1);
			img = img.substring(0, a);
		} else {
			val = val.substring(0, i) + val.substring(j-1 + 2);
			img = img.substring(0, a) + img.substring(a+1 + 82);
		}
		
	}
	parent.document.frmCamp.pdtid.value = val;
	var pdtArea = parent.document.getElementById("pdtArea");
	var imgArea = parent.document.getElementById("imgArea");
	//alert(img);
	pdtArea.innerHTML = val;
	imgArea.innerHTML = img;
}
</script>
</head>
<body>
<div class="outerBox">
<form name="frmSch" method="get">
<div class="search">
<input type="text" name="keyword" size="28" value="의자" placeholder="브랜드명 또는 상품명을 검색하세요." />&nbsp;
<input type="submit" value="검색" valign="middle" /><br />
<span id="cnt">0</span><br />
</div>
<div class="list">
<table width="100%" height="500" cellpadding="5">
<% 
if (pdtList != null && pdtList.size() > 0) {
// 상품 검색 결과가 있으면
	for (int i = 0 ; i < pdtList.size() ; i++) {	// 상품이미지, 브랜드명, 상품명
		ProductInfo pi = pdtList.get(i);
		String lnk = null;
%>
<tr>
<td width="20%" align="center"><img src="../product/pdt_img/<%=pi.getPi_img1() %>" width="50" height="50" /></td>
<td width="*">[<%=pi.getBr_name() %>] <%=pi.getPi_name() %></td>
<td width="10%" align="left"><input type="checkbox" name="chk" value="<%=pi.getPi_id() %>" onclick="getChecked(this);" /></td>
</tr>
<%
	}
} else {
	out.println("<tr><th>검색 결과가 없습니다.</th></tr>");
}
%>
</table>
</div>
<!-- 여기에 넣을 것 -->
</form>
</div>
</body>
</html>