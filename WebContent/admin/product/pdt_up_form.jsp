<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%@include file="../../admin_header.jsp" %>
<%
PdtPageInfo pageInfo = (PdtPageInfo)request.getAttribute("pageInfo");
ProductInfo pdtInfo = (ProductInfo)request.getAttribute("pdtInfo");	// 수정할 상품 정보
ArrayList<CataBigInfo> cataBigList = 
	(ArrayList<CataBigInfo>)request.getAttribute("cataBigList");	// 대분류 목록
ArrayList<CataSmallInfo> cataSmallList = 
	(ArrayList<CataSmallInfo>)request.getAttribute("cataSmallList");// 소분류 목록
//ArrayList<BrandInfo> brandList = 
	//(ArrayList<BrandInfo>)request.getAttribute("brandList");		// 브랜드 목록
%>
<script>
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
.ipt, .cmb { width:300px; }
a:link { color:#4f4f4f; text-decoration:none; }
a:visited { color:#4f4f4f; text-decoration:none; }
a:hover { color:#2788ff; text-decoration:none; }
a:focus { color:#2788ff; text-decoration:none; }
a:active { color:#2788ff; text-decoration:none; }

tr, td { padding:10px 0; }
td { padding-left:50px; }
.left { text-align:left; padding-left:50px; }
#btnsch { width:100px; height:35px; }
.wrapper { margin:0 auto; width:1250px; padding-top:10px; }
</style>

<div class="wrapper">
	<h2>상품 수정</h2><hr /><br />
	<form name="frmPdt" action="pdt_proc.pdta" method="post" enctype="multipart/form-data">
	<input type="hidden" name="wtype" value="up" />
	<input type="hidden" name="id" value="<%=pdtInfo.getPi_id() %>" />
	<input type="hidden" name="oldImg1" value="<%=pdtInfo.getPi_img1() %>" />
	<input type="hidden" name="oldImg2" value="<%=pdtInfo.getPi_img2() %>" />
	<input type="hidden" name="oldImg3" value="<%=pdtInfo.getPi_img3() %>" />
	<input type="hidden" name="oldImg4" value="<%=pdtInfo.getPi_img4() %>" />
	<input type="hidden" name="oldImg5" value="<%=pdtInfo.getPi_img5() %>" />
	<input type="hidden" name="oldDesc" value="<%=pdtInfo.getPi_desc() %>" />
	<input type="hidden" name="cpage" value="<%=pageInfo.getCpage() %>" />
	<input type="hidden" name="schtype" value="<%=pageInfo.getSchtype() %>" />
	<input type="hidden" name="keyword" value="<%=pageInfo.getKeyword() %>" />
	<input type="hidden" name="bcata" value="<%=pageInfo.getBcata() %>" />
	<input type="hidden" name="scata" value="<%=pageInfo.getScata() %>" />
	<input type="hidden" name="brand" value="<%=pageInfo.getBrand() %>" /><!-- 브랜드검색x -->
	<input type="hidden" name="sprice" value="<%=pageInfo.getSprice() %>" />
	<input type="hidden" name="eprice" value="<%=pageInfo.getEprice() %>" />
	<input type="hidden" name="isview" value="<%=pageInfo.getIsview() %>" />
	<input type="hidden" name="sdate" value="<%=pageInfo.getSdate() %>" />
	<input type="hidden" name="edate" value="<%=pageInfo.getEdate() %>" />
	<input type="hidden" name="stock" value="<%=pageInfo.getStock() %>" />
	<input type="hidden" name="ord" value="<%=pageInfo.getOrd() %>" />
	<table width="1250" cellpadding="5">
	<tr>
	<th>상품코드</th><td><%=pdtInfo.getPi_id() %></td>
	<th>브랜드</th><td><%=pdtInfo.getBr_name() %></td>
	</tr>
	<tr>
	<th width="170">대분류</th>
	<td width="250">
		<select name="cbid" onchange="setCategory(this.value, this.form.csid);" class="cmb">
			<option value="">대분류 전체</option>
	<%
	for (CataBigInfo cata : cataBigList) {
		String slt = "";
		if (pdtInfo.getCb_id().equals(cata.getCb_id()))
			slt = " selected='selected'";
	%>
			<option value="<%=cata.getCb_id()%>"<%=slt%>><%=cata.getCb_name()%></option>
	<% } %>
		</select>
	</td>
	<th width="170">소분류</th>
	<td width="250">
		<select name="csid" class="cmb">
			<option value="">소분류 전체</option>
	<%
	for (CataSmallInfo cata : cataSmallList) {
		if (pdtInfo.getCb_id().equals(cata.getCb_id())) {
		// 상품의 대분류와 동일한 대분류를 가진 소분류일 경우
			String slt = "";
			if (pdtInfo.getCs_id().equals(cata.getCs_id()))	slt = " selected='selected'";
		%>
			<option value="<%=cata.getCs_id()%>"<%=slt%>><%=cata.getCs_name()%></option>
	<%
		}
	}
	%>
		</select>
	</td>
	</tr>
	<tr>
	<th>상품명</th>
	<td><input type="text" name="name" class="ipt" value="<%=pdtInfo.getPi_name() %>" /></td>
	<th>판매가</th>
	<td><input type="text" name="price" class="ipt" value="<%=pdtInfo.getPi_price() %>" /></td>
	</tr>
	<tr>
	<th>원가</th>
	<td><input type="text" name="cost" class="ipt" value="<%=pdtInfo.getPi_cost() %>" /></td>
	<th>할인율</th>
	<td><input type="text" name="discount" class="ipt" value="<%=pdtInfo.getPi_dcrate() %>" /> %</td>
	</tr>
	<tr>
	<th>옵션</th>
	<td colspan="3">
		<input type="text" name="option" class="ipt" value="<%=pdtInfo.getPi_option() %>" placeholder="예) 블랙;레드;블루" />&nbsp;&nbsp;
		<span style="font-size:13px;">;을 구분자로 띄어쓰기 없이 입력해주세요.</span>
	</td>
	</tr>
	<tr>
	<th>이미지1</th>
	<td>
		<input type="file" name="img1" /><br />
	<% if (pdtInfo.getPi_img1() == null || pdtInfo.getPi_img1().equals("")) { %>
		기존 이미지가 없습니다.
	<% } else { %>
		<img src="../product/pdt_img/<%=pdtInfo.getPi_img1() %>" width="50" height="60" />
	<% } %>
	</td>
	<th>이미지2</th>
	<td>
		<input type="file" name="img2" /><br />
	<% if (pdtInfo.getPi_img2() == null || pdtInfo.getPi_img2().equals("")) { %>
		기존 이미지가 없습니다.
	<% } else { %>
		<img src="../product/pdt_img/<%=pdtInfo.getPi_img2() %>" width="50" height="60" />
	<% } %>
	</td>
	</tr>
	<tr>
	<th>이미지3</th>
	<td>
		<input type="file" name="img3" /><br />
	<% if (pdtInfo.getPi_img3() == null || pdtInfo.getPi_img3().equals("")) { %>
		기존 이미지가 없습니다.
	<% } else { %>
		<img src="../product/pdt_img/<%=pdtInfo.getPi_img3() %>" width="50" height="60" />
	<% } %>
	</td>
	<th>이미지4</th>
	<td>
		<input type="file" name="img4" /><br />
	<% if (pdtInfo.getPi_img4() == null || pdtInfo.getPi_img4().equals("")) { %>
		기존 이미지가 없습니다.
	<% } else { %>
		<img src="../product/pdt_img/<%=pdtInfo.getPi_img4() %>" width="50" height="60" />
	<% } %>
	</td>
	</tr>
	<tr>
	<th>이미지5</th>
	<td>
		<input type="file" name="img5" /><br />
	<% if (pdtInfo.getPi_img5() == null || pdtInfo.getPi_img5().equals("")) { %>
		기존 이미지가 없습니다.
	<% } else { %>
		<img src="../product/pdt_img/<%=pdtInfo.getPi_img5() %>" width="50" height="60" />
	<% } %>
	</td>
	<th>설명이미지</th>
	<td>
		<input type="file" name="desc" /><br />
	<% if (pdtInfo.getPi_desc() == null || pdtInfo.getPi_desc().equals("")) { %>
		기존 이미지가 없습니다.
	<% } else { %>
		<img src="../product/pdt_img/<%=pdtInfo.getPi_desc() %>" width="50" height="60" />
	<% } %>
	</td>
	</tr>
	<tr>
	<th>재고량</th>
	<td><input type="text" name="pstock" class="ipt" value="<%=pdtInfo.getPi_stock() %>" /></td>
	<th>게시여부</th>
	<td>
		<input type="radio" name="pisview" value="y" <% if (pdtInfo.getPi_isview().equals("y")) { %>checked="checked"<% } %> />상품게시&nbsp;&nbsp;
		<input type="radio" name="pisview" value="n" <% if (pdtInfo.getPi_isview().equals("n")) { %>checked="checked"<% } %> />상품미게시
	</td>
	</tr>
	<tr><td colspan="4" align="center">
		<input type="submit" value="상품 수정" />
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="reset" value="다시 입력" />
	</td></tr>
	</table>
	</form>
</div>
</body>
</html>
