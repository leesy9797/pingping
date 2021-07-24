<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%@include file ="../header.jsp" %>
<%
request.setCharacterEncoding("utf-8");
CampingInfo campInfo = (CampingInfo)request.getAttribute("campInfo");
CampPageInfo pageInfo = (CampPageInfo)request.getAttribute("pageInfo");
ArrayList<CampReplyInfo> replyList = (ArrayList<CampReplyInfo>)request.getAttribute("replyList");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="<%=incSrc %>/camping/camp_view.css" />
<script src="<%=incSrc %>/js/jquery-ui-1.10.3.custom.min.js"></script>
<script type="text/javascript" src="<%=incSrc %>/camping/camp_view.js" /></script>
<script>
function chkDel() {
   if (confirm("정말 삭제하시겠습니까?"))   return true;
   else                     return false;
}

function replyUp(crridx) {
   var cridx = eval("document.frmReply." + idx + ".value");
   alert(cridx);
   
   //var frm = document.frmReply;
   //frm.rcontent.value = cont;
   //frm.wtype.value = "up";
   //frmReply.bfridx.value = bfridx;
}

function replyDel(crridx) {
   if (confirm("정말 삭제하시겠습니까?")) {
      var frm = document.frmReply;
      frm.wtype.value = "del";
      frmReply.bfridx.value = bfridx;
      frm.submit();
      
      //location.href = "freeProc.jsp?";
   }
}
</script>
<style>
   .right {
            float : right; position : fixed;
            display : block;
            z-index:1;
            margin-left:800px;
   }
   
   .header {
   z-index : 2;
   }
   
   .wrapper {
      width:1250px; margin:0 auto; height:1500px;
   }
   
   .reply {
         width:750px;
   }
   #pdt1, #pdt2, #pdt4, #pdt0 { display:none; }
</style>
<div class="wrapper">
	<!-- 캠핑후기 왼쪽 영역  시작 -->
	<div id="left">
	<%
	// 캠핑후기 이미지
	String[] arrImgs = null;
	String imgs = campInfo.getCr_imgs(); 
	System.out.println(imgs);
	
	arrImgs = imgs.split("/");
	
	// 태그 상품 이미지
	String[] arrPdts = null;
	String pdtimgs = campInfo.getCr_pdtimgs(); 
	System.out.println(pdtimgs);
	
	arrPdts = pdtimgs.split("/");
	
	/*   마지막 인덱스 비어있으면 지우기
	int n = arrImgs.length - 1;
	if (arrImgs[n] == "") {
	   arrImgs.remove(n);
	}
	*/
	//if (arrImgs[arrImgs.length] == "") {}
	
	   //System.out.println("arrImgs[0]" + arrImgs[0]);
	
	%>   
	   <div class="date" style="text-align:right; padding-right:50px; margin-bottom:10px; font-size:14px;"><%=campInfo.getCr_date().substring(0, 11) %></div>
	
	   <div class="slideshow">
	      <div class="slideshow-slides">
	      <% for (int i = 0 ; i < arrImgs.length ; i ++) { %>
	         <a href="#" class="slide" id="slide-<%=i + 1%>"><img src="<%=incSrc %>/camping/camp_img/<%=arrImgs[i] %>" width="700" height="400" /></a>
	      <% } %>
	      </div>
	      <div class="slideshow-nav">
	         <a href="#" class="prev">Prev</a>
	         <a href="#" class="next">Next</a>
	      </div>
	      <div class="slideshow-indicator"></div>
	   </div>
	   <!-- 캠핑후기 작은 이미지 시작 -->
	   <div id = "smallImg">
	   <% //for (int i = 0 ; i < arrImgs.length ; i++) { %>
	      
	   <% //} %>
	   </div>
	   <!-- 캠핑후기 작은 이미지 종료 -->
	   <div class="pdtimgs">
	   <% System.out.println(arrPdts[0].substring(0, arrPdts[0].length() - 4));  %>
	   <% for (int i = 0 ; i < arrPdts.length ; i++) { %>
	      <a href="../pdt_view.pdt?id=B6S641009" id="pdt<%=i %>"><img src="<%=incSrc %>/product/pdt_img/B6S641009.jpg" width="130" height="130" /></a>
	   <% } %>   <!-- a태그 상품 상세페이지로 이동 -->
	   
	   </div>
	   <!-- 캠핑후기 설명 / 태그  / 조회,스크랩,댓글 정보 시작 -->
	   <div class="content">
	   <%=campInfo.getCr_content() %>
	   </div>
	   <div class="keyword">
	   <% 
	   String[] arrKeyword = campInfo.getCr_keyword().replace("#", " #").split(" ");
	   System.out.println(arrKeyword[1]);
	   for (int i = 1 ; i < arrKeyword.length ; i++) {
	   %>
	   <a href=""><%=arrKeyword[i] %></a>
	   <%
	   }
	   %>
	   </div>
	   <div class="info">
	   조회 <%=campInfo.getCr_read() %> / 스크랩 <%=campInfo.getCr_scrap() %> / 댓글 <%=campInfo.getCr_reply() %> &nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="신고" onclick="" />
	   </div>
	   <hr />
	   <div class="reply">
	   <img src="../member/member_img/<%=memberInfo.getMi_img() %>" alt="mdo" width="32" height="32" class="rounded-circle">&nbsp;&nbsp;<%=memberInfo.getMi_nick() %>&nbsp;&nbsp;<input type="text" placeholder="댓글을 입력해주세요." size="90" />&nbsp;&nbsp;
	   <input type="button" value="등록" size="10" align="absmiddle" onclick="location.href='<%=incSrc %>/camping/camp_reply_proc.camp?wtype=in&idx=<%=campInfo.getCr_idx() %>';" />
	   </div>
	   <hr />
	   <div>
	   <table width="100%" cellpadding="5">
	   <% 
	   if (replyList != null && replyList.size() > 0) {   
	      for (int i = 0 ; i < replyList.size() ; i++) {
	         CampReplyInfo reply = replyList.get(i);
	         int idx = reply.getCr_idx();   // 캠핑후기 인덱스 번호
	   %>
	      <tr>
	      <td align="left" width="15%"><%=reply.getCrr_nick() %></td>
	      <td align="left" width="*"><%=reply.getCrr_content() %></td>
	      <td align="right" width="26%" align="right" valign="top">
	         <%=reply.getCrr_date().substring(0, 16) %>
	         <a href="javascript:<% if (isLogin) { %>replyUp(<%=reply.getCrr_idx() %>);<%
	            } else { %>alert('수정 권한이 없습니다.');<% } %>">[u]</a>
	         
	         <a href="javascript:<% if (isLogin) { %>replyDel(<%=reply.getCrr_idx() %>);<%
	            } else { %>alert('삭제 권한이 없습니다.');<% } %>">[x]</a>
	      </td>
	      </tr>
	   <% } %>
	      <tr><td colspan="3" height="60"><hr /></td></tr>
	   </table>
	   <% 
	   } else {   // 장바구니가 비었으면
	   %>
	   <p style="width:800px; text-align:center;">등록된 댓글이 없습니다.</p>
	   <%
	   }
	   %>
	   </form>
	   </div>
	
<!-- 캠핑후기 왼쪽 영역 종료 -->
<!-- 캠핑후기 오른쪽 영역  시작 -->
	<div class="right">
		<div class="goodscrapreply">
			<img src="../img/heart.png" width="25" height="25" align="absmiddle" />&nbsp;&nbsp;<%=campInfo.getCr_good() %>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="../img/bookmark.png" width="25" height="25" align="absmiddle" />&nbsp;&nbsp;<%=campInfo.getCr_scrap() %>
		</div>
		<div class="member">
			<div class="memberImg"><img src="../member/member_img/<%=campInfo.getCr_img() %>" alt="mdo" width="32" height="32" class="rounded-circle"></div>
			<div class="memberInfo">
				<%=campInfo.getMi_nick() %><br />
				<%=campInfo.getMi_introduce() %>
			</div>
			<div class="follow">
				<input type="button" class="followingbtn" value="팔로잉" onclick="" />
			</div>
		</div>
		<%
		String[] arrHistory = campInfo.getCr_history().split("/");
	    //System.out.println(arrHistory[0]);
	    //System.out.println(arrHistory[1]);
	    //System.out.println(arrHistory[2]);
	    //System.out.println(arrHistory[3]);
	    
	    String idx = "";
	    String name = "";
	    
	    String[] arrName = new String[4];
	    
	    for (int i = 0 ; i < arrHistory.length ; i++) {
	    	idx = arrHistory[i];
	    	if (idx.length() == 1) {
	   	    	name = "cr100" + idx;
	   	    	
	   	    } else if (idx.length() == 2) {
	   	    	name = "cr10" + idx;
	   	    	
	   	    } else if (idx.length() == 3) {
	   	    	name = "cr1" + idx;
	   	    	
	   	    } else {
	   	    	name = "cr" + idx;
	   	    }
	    	arrName[i] = name;
	        System.out.println(arrName[i]);
	    }
	   
		%>
		<div class="imgs">
			<div class="img1" style="border:0px blue solid; float:left;"><% if (arrHistory[0] != null && !arrHistory[0].equals("")) { %>
				<a href="<%=incSrc %>/camping/camp_view.camp?cpage=1&idx=<%=arrHistory[0] %>&ord=idx&psize=12">
				<img src="<%=incSrc %>/camping/camp_img/<%=arrName[0] %>_1.jpg" alt="..." width="180" height="180"><% } %></a>
			</div>
			<div class="img2" style="border:0px blue solid; float:left;"><% if (arrHistory[1] != null && !arrHistory[1].equals("")) { %>
				<a href="<%=incSrc %>/camping/camp_view.camp?cpage=1&idx=<%=arrHistory[1] %>&ord=idx&psize=12">
				<img src="<%=incSrc %>/camping/camp_img/<%=arrName[1] %>_1.jpg" alt="..." width="180" height="180"><% } %></a>
			</div>
			<div class="img3" style="border:0px blue solid; float:left;"><% if (arrHistory[2] != null && !arrHistory[2].equals("")) { %>
				<a href="<%=incSrc %>/camping/camp_view.camp?cpage=1&idx=<%=arrHistory[2] %>&ord=idx&psize=12">
				<img src="<%=incSrc %>/camping/camp_img/<%=arrName[2] %>_1.jpg" alt="..." width="180" height="180"><% } %></a>
			</div>
			<div class="img4" style="border:0px blue solid; float:left;"><% if (arrHistory[3] != null && !arrHistory[0].equals("")) { %>
				<a href="<%=incSrc %>/camping/camp_view.camp?cpage=1&idx=<%=arrHistory[3] %>&ord=idx&psize=12">
				<img src="<%=incSrc %>/camping/camp_img/<%=arrName[3] %>_1.jpg" alt="..." width="180" height="180"><% } %></a>
			</div>
		</div>
		<form action="camp_proc.camp?wtype=del&idx=<%=campInfo.getCr_idx() %>" method="post" enctype="multipart/form-data" onsubmit="return chkDel();">
			<input type="button" value="수정하기" id="btnsch" onclick="location.href='camp_up_form.camp?idx=<%=campInfo.getCr_idx() %>&cpage=<%=pageInfo.getCpage() %>&psize=<%=pageInfo.getPsize() %>&ord=<%=pageInfo.getOrd() %>';" />
			<input type="submit" value="삭제" id="btnsch" />
		</form>
	</div>
<!-- 캠핑후기 오른쪽 영역 종료 -->
</div>
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<br />
<%@ include file="../footer.jsp" %>