<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@include file ="../header.jsp" %>
<%
// 상품/대분류/소분류/브랜드 목록
ArrayList<ProductInfo> pdtList = (ArrayList<ProductInfo>)request.getAttribute("pdtList");
ArrayList<CataBigInfo> cataBigList = (ArrayList<CataBigInfo>)request.getAttribute("cataBigList");
ArrayList<CataSmallInfo> cataSmallList = (ArrayList<CataSmallInfo>)request.getAttribute("cataSmallList");
ArrayList<BrandInfo> brandList = (ArrayList<BrandInfo>)request.getAttribute("brandList");

PdtPageInfo pageInfo = (PdtPageInfo)request.getAttribute("pageInfo");

String args = "", schargs = "";
// 검색 관련 쿼리스트링 제작
if (pageInfo.getKeyword() == null)   schargs += "&keyword=";
else                        schargs += "&keyword=" + pageInfo.getKeyword();

if (pageInfo.getBcata() == null)   schargs += "&bcata=";
else                        schargs += "&bcata=" + pageInfo.getBcata();

if (pageInfo.getScata() == null)   schargs += "&scata=";
else                        schargs += "&scata=" + pageInfo.getScata();

if (pageInfo.getSprice() == null)    schargs += "&sprice=";
else                        schargs += "&sprice=" + pageInfo.getSprice();

if (pageInfo.getEprice() == null)    schargs += "&eprice=";
else                        schargs += "&eprice=" + pageInfo.getEprice();

args = "?cpage=" + pageInfo.getCpage() + schargs;
%>

<style>
	a:link { color:#4f4f4f; text-decoration:none; }
	a:visited { color:#4f4f4f; text-decoration:none; }
	a:hover { color:#2788ff; text-decoration:none; }
	a:focus { color:#2788ff; text-decoration:none; }
	a:active { color:#2788ff; text-decoration:none; }
	
   .outer { margin-left:300px; }
   
   .categoryBar {
            float : left; position : fixed;
            display : block; padding-top:300px;
            width:80px;
            z-index:1;
   }
   
   .eSlide { float : left; padding-left:230px; padding-bottom:70px;
            max-width:900px;}
            
   .eBanner { float : left; padding-left:10px; padding-bottom:70px;
            max-width:900px;}
            
   .b-example-divider { float : left;
   color: rgba(255, 255, 255, 1.0);}
   
   .pdtList { padding-left:225px; width:1500px; }
   
   .pop { position:relative;
            padding-left : 230px;
            max-width:1300px;
   }
   
   .list { display:table; width:900px; margin-left:50px; margin-right:50px; margin-bottom:10px;  }
   
   tr, td { padding:10px 0; }
   
   .card {
   border:1px #4f4f4f solid; width:250px; height:200px; text-align:center;
   padding:5px; margin:15px 10px; float:left; display:table-cell;
   }

   .page { padding-left:225px; margin:0 auto; margin-left:130px; float:left; padding-top:30px; }

   .header {
   z-index : 2;
   }
   
   .frmSch {
      width:1060px;
   }
   
   .cmb {
      width:150px;
   }
   
   .btns {
      width:200px;
   }
</style>

<script>
<%
String cbid = "", arrName = "";   // 대분류ID, 대분류/소분류
int j = 0;
for (int i = 0 ; i < cataSmallList.size() ; i++) {   // cataSmallList.size() = 6
   if (!cbid.equals(cataSmallList.get(i).getCb_id())) {
      cbid = cataSmallList.get(i).getCb_id();
      arrName = "arr" + cbid;
      out.println("var " + arrName + " = new Array();");
      out.println(arrName + "[0] = new Option(\"\", \" 소분류 전체 \");");
      j = 1;
   }
   out.println(arrName + "[" + j + "] = new Option(\"" +
      cataSmallList.get(i).getCs_id() + "\", \"" + 
      cataSmallList.get(i).getCs_name() + "\");");
   j++;
}
%>
function setCategory(x, target) {
// x : 선택한 대분류ID, target : 선택한 대분류에 속한 소분류를 보여줄 컨트롤 객체
   // 1. 원래 소분류 콤보박스에 있던 데이터(option 태그)들을 모두 삭제
   for (var i = target.options.length - 1 ; i > 0  ; i--) {
   // 삭제는 반대방향으로 해야 효율적임(속도가 빠름) : 0번은 지우지 않고 남겨둠(콤보박스의 모양유지를 위해서)
   // 앞에서부터 지우면 한 칸씩 당겨지는데 굳이 그럴 필요가 없음
      target.options[i] = null;   // null을 넣으면 삭제가 됨
   }

   // 2. 선택한 대분류에 속하는 소분류 데이터를 소분류 콤보박스에 넣음
   if (x != "") {   // 대분류를 선택했으면
      var arr = eval("arr" + x);
      // 대분류에서 선택한 값에 따라 사용할 배열을 지정 - 소분류 콤보박스에서 보여줄 option 요소들이 있는 배열
      // if문으로 x==1 -> arr1, x==2 -> arr2f로 해도 됨

      for (var i = 0 ; i < arr.length ; i++)   {
         target.options[i] = new Option(arr[i].value, arr[i].text);
         // 지정한 arr 배열만큼 target에 option 요소 지정
      }
      target.options[0].selected = true;
      // target의 0번 인덱스에 해당하는 option 태그를 선택한 상태로 만듬
   }
}
</script>
<div class="outer">
<div class="flex-shrink-0 p-3 bg-white categoryBar" style="width: 200px;">
    <a href="" class="d-flex align-items-center pb-3 mb-3 link-dark text-decoration-none border-bottom">
      <svg class="bi me-2" width="30" height="24"><use xlink:href="#bootstrap"></use></svg>
      <span class="fs-5 fw-semibold">카테고리</span>
    </a>
    <ul class="list-unstyled ps-0">
      <li class="mb-1">
        <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse" data-bs-target="#home-collapse" aria-expanded="true">
          텐트 / 침낭
        </button>
        <div class="collapse show" id="home-collapse">
          <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
            <li><a href="pdt_list.pdt?&bcata=B1&scata=S11" class="link-dark rounded">텐트</a></li>
            <li><a href="pdt_list.pdt?&bcata=B1&scata=S12" class="link-dark rounded">타프</a></li>
            <li><a href="pdt_list.pdt?&bcata=B2&scata=S21" class="link-dark rounded">침낭</a></li>
            <li><a href="pdt_list.pdt?&bcata=B2&scata=S22" class="link-dark rounded">매트리스</a></li>
          </ul>
        </div>
      </li>
      <li class="mb-1">
        <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse" data-bs-target="#dashboard-collapse" aria-expanded="true">
          테이블 / 박스
        </button>
        <div class="collapse" id="dashboard-collapse">
          <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
            <li><a href="pdt_list.pdt?&bcata=B3&scata=S31" class="link-dark rounded">테이블</a></li>
            <li><a href="pdt_list.pdt?&bcata=B3&scata=S32" class="link-dark rounded">의자</a></li>
            <li><a href="pdt_list.pdt?&bcata=B4&scata=S41" class="link-dark rounded">박스</a></li>
            <li><a href="pdt_list.pdt?&bcata=B4&scata=S42" class="link-dark rounded">아이스박스</a></li>
          </ul>
        </div>
      </li>
      <li class="mb-1">
        <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse" data-bs-target="#orders-collapse" aria-expanded="true">
          조명 / 취사도구
        </button>
        <div class="collapse" id="orders-collapse">
          <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
            <li><a href="pdt_list.pdt?&bcata=B5&scata=S51" class="link-dark rounded">랜턴</a></li>
            <li><a href="pdt_list.pdt?&bcata=B5&scata=S52" class="link-dark rounded">조명</a></li>
            <li><a href="pdt_list.pdt?&bcata=B6&scata=S61" class="link-dark rounded">버너</a></li>
            <li><a href="pdt_list.pdt?&bcata=B6&scata=S62" class="link-dark rounded">코펠</a></li>
            <li><a href="pdt_list.pdt?&bcata=B6&scata=S63" class="link-dark rounded">식기</a></li>
            <li><a href="pdt_list.pdt?&bcata=B6&scata=S64" class="link-dark rounded">화로/그릴</a></li>
          </ul>
        </div>
      </li>
    </ul>
</div>
<script src="<%=incSrc %>/js/sidebars.js"></script>
<link href="<%=incSrc %>/css/sidebars.css" rel="stylesheet">

<div class="pop"><%@include file="./popProduct.jsp" %></div>
<div class="eBanner"></div>

<div class="pdtList">
<form name="frmSch" method="get" class="frmSch">   
<input type="hidden" name="ord" value="<%=pageInfo.getOrd() %>" />
<input type="hidden" name="psize" value="<%=pageInfo.getPsize() %>" />
<table width="90%" align="center" style="margin-left:100px;" cellpadding="5" class="srcBox">
<tr>
<th width="10%" height="50">분류</th>
<td width="40%">
   <select name="bcata" class="cmb" onchange="setCategory(this.value, this.form.scata);">
      <option value="">대분류 전체</option>
      <% 
      for (CataBigInfo cata : cataBigList) {   // for (int i = 0 ; i < cataBigList.size() ; i++)
         String slt = "";
         if (pageInfo.getBcata() != null && pageInfo.getBcata().equals(cata.getCb_id())) {
            slt = " selected='selected'";
         }
      %>
      <option value="<%=cata.getCb_id() %>"<%=slt %>><%=cata.getCb_name() %></option>
      <% } // cataBigLZist.get(i).getCb_id(), cataBigList.get(i).getCb_name() %>
   </select>&nbsp;&nbsp;&nbsp;
   <select name="scata" class="cmb">
      <option value="">소분류 전체</option>
<%
if (pageInfo.getBcata() != null && !pageInfo.getBcata().equals("")) {
// 대분류 검색 조건이 있을 경우
   for (CataSmallInfo cata : cataSmallList) {   
      if (pageInfo.getBcata().equals(cata.getCb_id())) {
      // 검색 조건의 대분류와 동일한 대분류를 가진 소분류일 경우
         String slt = "";
         if (pageInfo.getScata() != null && pageInfo.getScata().equals(cata.getCs_id())) {
            slt = " selected='selected'";
         }
      %>
         <option value="<%=cata.getCs_id() %>"<%=slt %>><%=cata.getCs_name() %></option>
<%
      } 
   }
}
%>
</select>
</td>
</tr>
<tr>
<th>상품명</th>
<td><input type="text" name="keyword" size="42" value="<%=pageInfo.getKeyword() %>" /></td>
<th>가격대</th>
<td>
   <input type="text" name="sprice" size="15" value="<%=pageInfo.getSprice()%>"/>원 ~
   <input type="text" name="eprice" size="15" value="<%=pageInfo.getEprice()%>"/>원 
</td>
</tr>
<tr height="20"></tr>
<tr><td colspan="4" align="center">
   <input type="submit" value="상품 검색" class="btns">
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   <input type="reset" value="다시 입력" class="btns">
</td></tr>
<tr height="20"></tr>
</table>
</form>
<p style="width:1060px;" align="right">정렬조건 : 
<!-- 정렬조건(인기순 - 내림차순, 가격 - 오름차순/내림차순, 등록 - 내림차순(기본값), 상품명 - 오름차순, 리뷰 - 내림차순) -->
   <a href="pdt_list.pdt?cpage=1<%=schargs %>&ord=idd" style="font-weight:<%=(pageInfo.getOrd().equals("idd")) ? "bold" : "normal" %>">신상품순</a>&nbsp;
   <a href="pdt_list.pdt?cpage=1<%=schargs %>&ord=salecntd" style="font-weight:<%=(pageInfo.getOrd().equals("salecntd")) ? "bold" : "normal" %>">인기순</a>&nbsp;
   <a href="pdt_list.pdt?cpage=1<%=schargs %>&ord=namea" style="font-weight:<%=(pageInfo.getOrd().equals("namea")) ? "bold" : "normal" %>">상품명</a>&nbsp;
   <a href="pdt_list.pdt?cpage=1<%=schargs %>&ord=pricea" style="font-weight:<%=(pageInfo.getOrd().equals("pricea")) ? "bold" : "normal" %>">낮은 가격순</a>&nbsp;
   <a href="pdt_list.pdt?cpage=1<%=schargs %>&ord=priced" style="font-weight:<%=(pageInfo.getOrd().equals("priced")) ? "bold" : "normal" %>">높은 가격순</a>&nbsp;
   <a href="pdt_list.pdt?cpage=1<%=schargs %>&ord=reviewd" style="font-weight:<%=(pageInfo.getOrd().equals("reviewd")) ? "bold" : "normal" %>">리뷰순</a>&nbsp;
   <a href="pdt_list.pdt?cpage=1<%=schargs %>&ord=<%=pageInfo.getOrd() %>&psize=10">
   <img src="img/pdt_list_<%=(pageInfo.getPsize() == 10) ? "on" : "off" %>.png" width="15" align="absmiddle" /></a>
   <a href="pdt_list.pdt?cpage=1<%=schargs %>&ord=<%=pageInfo.getOrd() %>&psize=12">
   <img src="img/pdt_img_<%=(pageInfo.getPsize() == 12) ? "on" : "off" %>.png" width="15" align="absmdle" /></a>&nbsp;&nbsp;
</p>
<table cellpadding="5" class="list">
<div class="schlist">
<% 
if (pdtList != null && pdtList.size() > 0) {
// 상품 검색 결과가 있으면
   for (int i = 0 ; i < pdtList.size() ; i++) {   // 상품이미지, 상품명, 가격 
      //System.out.println(pdtList.size());
      ProductInfo pi = pdtList.get(i);
      String lnk = null;
      if (pi.getPi_stock() != 0) {
         lnk = "<a href=\"pdt_view.pdt" + args + "&id=" + pi.getPi_id() +
         "&ord=" + pageInfo.getOrd() + "&psize=" + pageInfo.getPsize() + "\">";
      } else {
         lnk = "<a href=\"javascript:alert('품절상품입니다.');\">";
      } if (pageInfo.getPsize() == 12) {
         %>
            <%=lnk %><div class="card h-50">
                 <!— Product image—>
                 <img class="card-img-top" src="product/pdt_img/<%=pi.getPi_img1() %>" width="236" height="236" alt="…">
                 <!— Product details—>
                 <div class="card-body p-4">
                     <div class="text-center">
                     <%=lnk %>[<%=pi.getBr_name() %>]<hr />
                     <%=pi.getPi_name() %><br />
                     <%=pi.getPi_price() %>원</a>
                     </div>
                 </div>
                 <!— Product actions—>
                 <div class="card-footer p-4 pt-0 border-top-0 bg-transparent">
                     <div class="text-center"><a class="btn btn-outline-dark mt-auto" href="pdt_view.pdt<%=args %>&id=<%=pi.getPi_id() %>&ord=<%=pageInfo.getOrd() %>&psize=<%=pageInfo.getPsize() %>">View Details</a></div>
                 </div>
             </div>
                <% } else {   // 상품 목록을 한 줄에 하나씩 보여주기 %>
<tr>
<td width="150" align="center"><%=lnk %><img src="product/pdt_img/<%=pi.getPi_img1() %>" width="120" height="120" /></a></td>
<td width="*"><%=lnk %>[<%=pi.getBr_name() %>] <%=pi.getPi_name() %></td>
<td width="100"><strong><%=pi.getPi_price() %></strong>원</td>
</tr>
<%
      }   
      
   }
} else {
   out.println("<tr><th>검색 결과가 없습니다.</th></tr>");
}
%>
</div><!-- schlist 닫음 -->
</table>
</div><!-- pdtlist 닫음 -->
<div class="page">
<% 
if (pdtList != null && pdtList.size() > 0) {
// 상품 검색 결과가 있으면
   args = "?ord=" + pageInfo.getOrd() + "&psize=" + pageInfo.getPsize() + schargs;
   out.println("<p style=\"width:800px\" align=\"center\">");

   if (pageInfo.getCpage() == 1) {   // 현재 페이지 번호가 1이면
      out.println("[&lt;&lt;]&nbsp;&nbsp;[&lt;]&nbsp;&nbsp;");
   } else {
      out.print("<a href='pdt_list.pdt" + args + "&cpage=1" + "'>");
      out.println("[&lt;&lt;]</a>&nbsp;&nbsp;");
      out.print("<a href='pdt_list.pdt" + args +
         "&cpage=" + (pageInfo.getCpage() - 1) + "'>");
      out.println("[&lt;]</a>&nbsp;&nbsp;");
   }   // 첫 페이지와 이전 페이지 링크
   
   for (int i = 1, k = pageInfo.getSpage() ; i <= pageInfo.getBsize() && k <= pageInfo.getEpage() ; i++, k++) {
      if (pageInfo.getCpage() == k) {   // 현재 페이지 번호일 경우 링크없이 강조만 함
         out.print("&nbsp;<strong>" + k + "</strong>&nbsp;");
      } else {
         out.print("&nbsp;<a href='pdt_list.pdt" + args + "&cpage=" + k + "'>");
         out.print(k + "</a>&nbsp;");
      }
   }
   
   if (pageInfo.getCpage() == pageInfo.getPcnt()) {   // 현재 페이지 번호가 마지막 페이지 번호이면
      out.println("&nbsp;&nbsp;[&gt;]&nbsp;&nbsp;[&gt;&gt;]");
   } else {
      out.println("&nbsp;&nbsp;<a href='pdt_list.pdt" + args +
         "&cpage=" + (pageInfo.getCpage() + 1) + "'>[&gt;]</a>");
      out.print("&nbsp;&nbsp;<a href='pdt_list.pdt" + args +
         "&cpage=" + pageInfo.getPcnt() + "'>");
      out.println("[&gt;&gt;]</a>");   
   }

out.println("</p>");
}
%>
</div>
</div><br /><br />
<%@ include file="../footer.jsp" %>