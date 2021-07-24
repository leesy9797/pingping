<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%@include file ="../header.jsp" %>
<%
MonthlyInfo monthInfo = (MonthlyInfo)request.getAttribute("monthInfo");
ArrayList<MonthReplyInfo> replyList = (ArrayList<MonthReplyInfo>)request.getAttribute("replyList");
/*
if (monthInfo == null) {
	out.println("<script>");
	out.println("alert('잘못된 경로로 들어오셨습니다.');");
	out.println("history.back();");
	out.println("</script>");
	out.close();
}
*/

String[] arrImg = monthInfo.getMr_imgs().split("/");
String[] arrKeyword = monthInfo.getMr_keyword().split("#");
String[] arrMrinfo = monthInfo.getMr_info().split("/");
String[] arrInfo1 = {"오토캠핑", "글램핑", "카라반", "방갈로", "펜션", "기타"};
String[] arrInfo2 = {"화장실", "샤워실", "개수대", "매점"};
String[] arrInfo3 = {"무선인터넷", "전기사용", "화로대사용", "장비대여", "장작판매", "온수사용", "애완동물"};

String[] pdtids = monthInfo.getMr_pdtids().split("/");
%>
<style>
#wrapper { margin:0 auto; width:1250px; background-color:#fff; padding-top:70px; }

.camping-header {
	background:#fff; position:absolute; width:1250px; padding-top:50px;
	-webkit-box-shadow:0 1px 1px rgba(0, 0, 0, 0.25);
			box-shadow:0 1px 1px rgba(0, 0, 0, 0.25);
}
.camping-header.sticky {position:fixed; top:0;}
.camping-header > .inner {margin:auto; width:680px;}
.no-boxshadow .camping-header {border-bottom:1px solid rgb(204, 204, 204);}

.primary-nav {float:left; line-height:65px; letter-spacing:1px; text-transform:uppercase;}
.primary-nav li {float:left;}
.primary-nav a {display:block; padding:0 1.0em;}
.primary-nav a:hover {background:rgb(240, 240, 240);}

a:link { color:#4f4f4f; text-decoration:none; }
a:visited { color:#4f4f4f; text-decoration:none; }
a:hover { color:#2788ff; text-decoration:none; }
a:focus { color:#2788ff; text-decoration:none; }
a:active { color:#2788ff; text-decoration:none; }

.link { color:#2788ff; }
#bImg1, #bImg2, #bImg3, #bImg4, #bImg5, #bImg6, #bImg7, #bImg8, #bImg9 { display:none; }
.bImg { width:1000px; height:600px; }
.sImg { width:200px; height:130px; }

.fontBlue { color:#2788ff; font-weight:bold; }
.camping-info { height:0px; vertical-align:middle; }
.camping-info td { padding:0 40px; }
.info-header { font-weight:bold; font-size:24px; text-align:center; padding:50px 0 50px; }
.month-title { font-size:22px; font-weight:bold; }
.info { width:1000px; padding-top:100px; padding-left:125px; }

.reply-text { font-size:18px; font-weight:bold; padding-left:170px; padding-top:25px; }
.reply-cnt { color:#2788ff; }
.reply { width:1250px; padding:15px 0 35px 170px; }
</style>
<script>
$(document).ready(function() {
	$(".camping-header").each(function() {
		var $window = $(window);	// 브라우저 창을 jQuery객체로 저장
		var $header = $(this);		// camping-header를 jQuery객체로 저장(고정시켜야 할 객체)
		var headerOffsetTop = $header.offset().top;
		// $header의 상단 지점의 위치(이 값에 따라 고정시킬지 여부가 결정됨)

		$window.on("scroll", function() {
		// 브라우저($window)에서 스크롤 이벤트가 발생했을 때 동작됨
			if ($window.scrollTop() > headerOffsetTop) {
			// 브라우저의 스크롤바 상단 위치(window.scrollTop())가 메뉴바 상단위치(headerOffsetTop)보다 크면
			// 메뉴바가 가려질 정도로 스크롤바가 내려왔을 경우
				$header.addClass("sticky");
				// $header에 'sticky'라는 클래스를 추가(sticky : css에서 상단에 고정시키는 스타일을 적용한 클래스
			} else {
				$header.removeClass("sticky");
				// $header에 'sticky'라는 클래스를 제거(sticky : css에서 상단에 고정시키는 스타일을 제거한 클래스
			}
		});

		$window.trigger("scroll");
		// trigger() : 선택한 객체에 지정한 이벤트를 인위적으로 발생시키는 메소드
		// 파일 로딩 후 헤더의 초기 위치를 조정하기 위해 실행됨
	});
});

function showImg(num) {
<% for (int n = 0 ; n < arrImg.length ; n++) { %>
		var img<%=n%> = document.getElementsByClassName("bImg")[<%=n%>];
		img<%=n%>.style.display = "none";
<% } %>
	var bImg1 = "bImg" + num;
	var bImg2 = document.getElementById(bImg1);
	bImg2.style.display = "block";
}

function replyUp(mrridx) {
   var mridx = eval("document.frmReply." + idx + ".value");
   alert(mridx);
   
   //var frm = document.frmReply;
   //frm.rcontent.value = cont;
   //frm.wtype.value = "up";
   //frmReply.bfridx.value = bfridx;
}

function replyDel(mrridx) {
   if (confirm("정말 삭제하시겠습니까?")) {
      var frm = document.frmReply;
      frm.wtype.value = "del";
      frmReply.bfridx.value = bfridx;
      frm.submit();
      
      //location.href = "freeProc.jsp?";
   }
}
// 좋아요 기능 만들기
</script>
<div id="wrapper">
<table width="1250" cellpadding="0" cellspacing="0" border="0">
<tr width="100%">
<td width="55%" align="center">
	<!-- 상단 영역 시작 -->
	<table width="1000" cellpadding="0" cellspacing="0" border="0">
	<tr>
	<td width="60%">
		<img id="img1" src="month_img/<%=arrImg[0] %>" width="550" height="330" />
	</td>
	<td width="*" valign="top">
		<div class="month-title"><%=monthInfo.getMr_title() %></div><br />
		<%=monthInfo.getMr_name() %> / <%=monthInfo.getMr_location() %><br /><br />
		<%=monthInfo.getMr_content().replace("\r\n", "<br />") %><br /><br />
		<a href="<%=monthInfo.getMr_url() %>" target="_blank"><span class="link"><%=monthInfo.getMr_url() %></span></a><br /><br />
		가져가면 좋은
<% for (int i = 0 ; i < arrKeyword.length ; i++) { %>
		<a href="../pdt_list.pdt?ord=idd&psize=12&&keyword=버너"><span class="link">#<%=arrKeyword[i] %></span></a>
<% } %><br /><br />
		<img src="../img/heart.png" width="16" height="16" alt="좋아요" />
		&nbsp;<%=monthInfo.getMr_good() %>
	</td>
	</tr>
	</table>
	<!-- 상단 영역 종료 -->
</td></tr>

<tr><td>
<br /><br /><br /><hr /><br /><br /><br />
	<!-- 캠핑장 정보 보기 영역 시작 -->
	<table width="1000" cellpadding="0" cellspacing="0" border="0">
	<tr class="camping-info">
	<td align="center">
		<table>
		<tr><td colspan="3" class="info-header">캠핑장 정보</td></tr>
		<tr>
		<td width="200" class="fontBlue" style="padding-top:50px;">캠핑형태</td>
		<td width="200" class="fontBlue" style="padding-top:50px;">기본시설</td>
		<td width="200" class="fontBlue" style="padding-top:50px;">이용조건</td>
		</tr>
		<tr valign="top">
		<td>
<%
for (int i = 0, j = 0 ; i < arrInfo1.length ; i++) {
	if (Arrays.asList(arrMrinfo).contains(arrInfo1[i])) {
		out.println("■ " + arrInfo1[j] + "<br />");
		j++;
	} else {
		out.println("□ " + arrInfo1[j] + "<br />");
		j++;
	}
}
%>
		</td>
		<td>
<%
for (int i = 0, j = 0 ; i < arrInfo2.length ; i++) {
	if (Arrays.asList(arrMrinfo).contains(arrInfo2[i])) {
		out.println("■ " + arrInfo2[j] + "<br />");
		j++;
	} else {
		out.println("□ " + arrInfo2[j] + "<br />");
		j++;
	}
}
%>
		</td>
		<td style="padding-bottom:70px;">
<%
for (int i = 0, j = 0 ; i < arrInfo3.length ; i++) {
	if (Arrays.asList(arrMrinfo).contains(arrInfo3[i])) {
		out.println("■ " + arrInfo3[j] + "<br />");
		j++;
	} else {
		out.println("□ " + arrInfo3[j] + "<br />");
		j++;
	}
}
%>
		</td>
		</tr>
		</table>
	</td>
	</tr>
	<!-- 추천상품 영역 시작 -->
	<tr><td class="info-header">추천상품</td></tr>
	<tr>
	<td align="center" style="padding-bottom:100px;">
		<a href="../pdt_view.pdt?cpage=1&bcata=B6&scata=S61&id=B6S611002&ord=idd&psize=12">
<%
for (int k = 0 ; k < pdtids.length ; k++) {
	out.println("<img src='../product/pdt_img/" + pdtids[k] + ".jpg' width='180' heigth='180' />");
}
%>
		</a>
	</td>
	</tr>
	<!-- 추천상품 영역 종료 -->
	<!-- 이미지 영역 시작 -->
	<tr><td class="info-header">캠핑장 이미지</td></tr>
	<tr valign="top">
	<td width="1250" align="center">
		<img id="bImg0" class="bImg" src="month_img/<%=arrImg[0] %>" /><br /><br />
		<div>
<% for (int i = 1 ; i < arrImg.length ; i++) { %>
	<% if (arrImg[i] != null && !arrImg[i].equals("")) { %>
		<img id="bImg<%=i %>" class="bImg" src="month_img/<%=arrImg[i] %>" alt="bImg<%=i%>" />
	<% } %>
<% } %>
		</div>
		<div style="width:1000px; height:160px; display:block; overflow-x: scroll; white-space:nowrap;">
			<img id="sImg0" class="sImg" src="month_img/<%=arrImg[0] %>" onclick="showImg(0);" />
<% for (int i = 1 ; i < arrImg.length ; i++) { %>
	<% if (arrImg[i] != null && !arrImg[i].equals("")) { %>
		<img id="sImg<%=i %>" class="sImg" src="month_img/<%=arrImg[i] %>" onclick="showImg(<%=i%>);" />
	<% } %>
<% } %>
		</div>
	</td></tr>
	<!-- 이미지 영역 종료 -->
	<tr>
	<td width="*" valign="top" align="left">
		<table width="100%">
			<div class="info">
			조회 <%=monthInfo.getMr_read() %> / 좋아요 <%=monthInfo.getMr_good() %> &nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="신고" onclick="" class="btn" />
			</div>
			<hr width="1250" />
			<p class="reply-text">댓글 <span class="reply-cnt"><%=monthInfo.getMr_reply() %></span></p>
			<div class="reply">
			회원사진&nbsp;&nbsp;<input type="text" placeholder="댓글을 입력해주세요." size="90" />&nbsp;&nbsp;
			<input type="button" value="등록" size="10" align="absmiddle" onclick="" />
			</div>
			<div>
			<table width="100%" cellpadding="5">
			<% 
			if (replyList != null && replyList.size() > 0) {   // 장바구니에 상품이 들어 있으면
			   for (int i = 0 ; i < replyList.size() ; i++) {
			      MonthReplyInfo reply = replyList.get(i);
			      int idx = reply.getMr_idx();   // 캠핑후기 인덱스 번호
			%>
			   <tr>
			   <td align="left" width="15%"><%=reply.getMrr_nick() %></td>
			   <td align="left" width="*"><%=reply.getMrr_content() %></td>
			   <td align="right" width="26%" align="right" valign="top">
			      <%=reply.getMrr_date().substring(0, 16) %>
			      <a href="javascript:<% if (isLogin) { %>replyUp(<%=reply.getMrr_idx() %>);<%
			         } else { %>alert('수정 권한이 없습니다.');<% } %>">[u]</a>
			      
			      <a href="javascript:<% if (isLogin) { %>replyDel(<%=reply.getMrr_idx() %>);<%
			         } else { %>alert('삭제 권한이 없습니다.');<% } %>">[x]</a>
			   </td>
			   </tr>
			<% } %>
			   <tr><td colspan="2" height="60"><hr /></td></tr>
			</table>
			<% 
			} else {   // 장바구니가 비었으면
			%>
			<p style="width:1250px; text-align:center;">등록된 댓글이 없습니다.</p>
			<%
			}
			%>
			</form>
			</div>
		</table>
	</td>
	</tr>
	</table>
	<!-- 캠핑장 정보 보기 영역 종료 -->
</td></tr>
</table>
</div>
<div style="padding-bottom:100px;"> </div>
<%@include file ="../footer.jsp" %>
