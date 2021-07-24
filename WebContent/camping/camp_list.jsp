<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@include file ="../header.jsp" %>
<%
request.setCharacterEncoding("utf-8");
ArrayList<CampingInfo> campList = (ArrayList<CampingInfo>)request.getAttribute("campList");
CampPageInfo pageInfo = (CampPageInfo)request.getAttribute("pageInfo");
ArrayList<FollowInfo> followList = (ArrayList<FollowInfo>)request.getAttribute("followList");
ArrayList<GoodInfo> goodList = (ArrayList<GoodInfo>)request.getAttribute("goodList");
ArrayList<ScrapInfo> scrapList = (ArrayList<ScrapInfo>)request.getAttribute("scrapList");

String login_email = "";
if (memberInfo != null)      login_email = memberInfo.getMi_email();
System.out.println("login_email : " + login_email);

// followList가 null이 아니면 팔로우한 회원 이메일을 배열로 받아옴
ArrayList<String> arrFollow = new ArrayList<String>();
if (followList != null && followList.size() > 0) {
   for (int i = 0 ; i < followList.size() ; i++) {
      FollowInfo fi = followList.get(i);
      //System.out.println(fi.getMfg_email());
      arrFollow.add(fi.getMfg_email());
   }
   System.out.println("arrFollow : " + arrFollow);
} else {
   arrFollow.add("");
}

// goodList null이 아니면 좋아요한 게시글 번호을 배열로 받아옴
ArrayList<String> arrGood = new ArrayList<String>();
if (goodList != null && goodList.size() > 0) {
   for (int i = 0 ; i < goodList.size() ; i++) {
      GoodInfo gi = goodList.get(i);
      //System.out.println(gi.getMg_linkidx());
      arrGood.add(gi.getMg_linkidx());
   }
   System.out.println("arrGood : " + arrGood);
} else {
   arrGood.add("");
}

// scrapList가 null이 아니면 스크랩한 게시글 번호을 배열로 받아옴
ArrayList<String> arrScrap = new ArrayList<String>();
if (scrapList != null && scrapList.size() > 0) {
   for (int i = 0 ; i < scrapList.size() ; i++) {
      ScrapInfo si = scrapList.get(i);
      //System.out.println(si.getMs_linkidx());
      arrScrap.add(si.getMs_linkidx());
   }
   System.out.println("arrScrap : " + arrScrap);
} else {
   arrScrap.add("");
}

String args = "";
args = "?cpage=" + pageInfo.getCpage();   // 페이징에서 이용
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
a:link { color:#4f4f4f; text-decoration:none; }
a:visited { color:#4f4f4f; text-decoration:none; }
a:hover { color:#2788ff; text-decoration:none; font-weight:bold; }
a:focus { color:#2788ff; text-decoration:none; }
a:active { color:#2788ff; text-decoration:none; }

.btn { width:90px; height:40px; border:2px solid lightblue; }
#btnsch { width:40px; height:25px; }
.ipt { width:150px; font-size:18px; font-weight:bold; }
.cmb { width:120px; height:25px; }
.schBox { padding-top:30px; padding-left:30px; }
.followingbtn { width:60px; height:35px; border:1px solid black; margin-left:10px; }
.followbtn { width:60px; height:35px; border:2px solid lightblue; margin-left:10px; }

body { margin:0; }
ol, ul { list-style:none; }
#wrapper, p { width:1250px; margin:0 auto; display:table; font-size:13px; }
.align { float:left; }
#top {
   width:100%; height:50px; text-align:center; font-size:25px; color:#4f4f4f; 
   font-weight:bold; padding:30px 0 50px 0;
}
.outerBox {
   border:1px #4f4f4f solid; width:290px; height:450px; text-align:center;
   padding:5px; margin:15px 10px; float:left;
}
.member {
   width:260px; height:30px; padding:5px; text-align:left;
}
.memberImg {
   padding:5px; margin:10px; float:left;
}
.memberInfo {
   margin:10px; float:left;
}
.follow {
   margin:10px 0 10px 10px; float:right; vertical-align:middle;
}
.imgBox {
   border:0px #000 solid; width:260px; height:190px; padding:5px; 
   text-align:center; display:table-cell; vertical-align:middle;
}
.crinfo {
   width:260px; height:30px; padding:5px; margin-left:10px; margin-right:10px; text-align:left; vertical-align:middle;
   font-size:20px; display:table;
}
.goodscrapreply { padding:5px; margin:10px; display:table-cell; }
.fill { color:#2788ff; }
.crcontent { 
   padding:5px; margin:0 auto;
   width:240px; height:45px; text-align:left; overflow:hidden;
}
.pdtName { font-size:15px; padding-top:7px; padding-bottom:3px; font-weight:bold; }
.pdtPrice { color:red; font-weight:bold; }
#bottom { float:left; width:100%; margin-left:550px; margin-top:50px; }
</style>
<script>
function chkLogin() {
// 비로그인 상태로 팔로우, 좋아요, 스크랩 클릭 시 로그인 폼으로 이동시키고 로그인 시 다시 돌아감
   var lnk = "camping/camp_list.camp";
<% if (!isLogin) { %>
   location.href = "../login_form.jsp?url=" + lnk;
<% } else { %>
   location.href = "camp_list.camp";
<% } %>
}

//팔로우
function dofollow(ciemail, miemail) {   // 글쓴 회원 이메일, 내 이메일
   $.ajax({
      type : "POST",                  // 데이터 전송 방식 : post
      url : "/pingping/dofollow.chkFGS",   // 데이터를 받을 서버의 URL로 컨트롤러
      data : {"ciemail":ciemail, "miemail":miemail},    // 컨트롤러로 보낼 데이터 : 매개변수로 받아온 ciemail를 ciemail라는 이름으로 가져감
      success : function(result) {
      // 데이터를 보내어 실행한 결과를 result로 받아옴
         if (result != 4) {   // 팔로우 실패 시
            alert("팔로우에 실패했습니다.");
         } else {   // 팔로우 성공 시
            location.reload();
         }
      }
   });
}

// 팔로우 취소
function unfollow(ciemail, miemail) {   // 글쓴 회원 이메일, 내 이메일
   $.ajax({
      type : "POST",
      url : "/pingping/unfollow.chkFGS",
      data : {"ciemail":ciemail, "miemail":miemail},
      success : function(result) {
         if (result != 4) {   // 언팔로우 실패 시
            alert("팔로우 취소에 실패했습니다.");
         } else {   // 팔로우 취소 성공 시
            location.reload();
         }
      }
   });
}

// 좋아요
function doGood(kind, idx, miemail) {   // 좋아요 한 게시글 번호, 내 이메일
   var doGood = document.getElementById("doGood"+idx);
   var unGood = document.getElementById("unGood"+idx);
   $.ajax({
      type : "POST",
      url : "/pingping/doGood.chkFGS",
      data : {"kind":kind, "idx":idx, "miemail":miemail},
      success : function(result) {
         if (result != 2) {   // 좋아요 실패 시
            alert("좋아요에 실패했습니다.");
         } else {   // 좋아요 성공 시
            //doGood.style.display = "none";
            //unGood.style.display = "inline";
            location.reload();
         }
      }
   });
}

// 좋아요 취소
function unGood(kind, idx, miemail) {   // 좋아요 취소할 게시글 번호, 내 이메일
   $.ajax({
      type : "POST",
      url : "/pingping/unGood.chkFGS",
      data : {"kind":kind, "idx":idx, "miemail":miemail},
      success : function(result) {
         if (result != 2) {   // 좋아요 취소 실패 시
            alert("좋아요 취소에 실패했습니다.");
         } else {   // 좋아요 취소 성공 시
            location.reload();
         }
      }
   });
}

// 스크랩
function doScrap(kind, idx, miemail) {   // 스크랩 한 게시글 번호, 내 이메일
   $.ajax({
      type : "POST",
      url : "/pingping/doScrap.chkFGS",
      data : {"kind":kind, "idx":idx, "miemail":miemail},
      success : function(result) {
         if (result != 2) {   // 스크랩 실패 시
            alert("스크랩에 실패했습니다.");
         } else {   // 스크랩 성공 시
            location.reload();
         }
      }
   });
}

// 스크랩 취소
function unScrap(kind, idx, miemail) {   // 스크랩 취소할 게시글 번호, 내 이메일
   $.ajax({
      type : "POST",
      url : "/pingping/unScrap.chkFGS",
      data : {"kind":kind, "idx":idx, "miemail":miemail},
      success : function(result) {
         if (result != 2) {   // 스크랩 취소 실패 시
            alert("스크랩 취소에 실패했습니다.");
         } else {   // 스크랩 취소 성공 시
            location.reload();
         }
      }
   });
}
</script>
<!-- 
<select class="align">
   <option value="new">최신순</option>
   <option value="good">인기순</option>
</select>

<div class="align"><a href="">최신순</a><a href="">인기순</a></div>
-->
<div id="wrapper">
<div id="top">캠핑후기 목록</div>
<div><input type="button" style="width:100px; height:35px; margin-left:1100px;" value="캠핑후기 등록" onclick="location.href='camp_in_form.camp';" /></div>
<%
if (campList != null && campList.size() > 0) {
   for (int i = 0 ; i < campList.size() ; i++) {
      CampingInfo ci = campList.get(i);
      String lnk = "<a href=\"camp_view.camp" + args + "&idx=" + ci.getCr_idx() +
            "&ord=" + pageInfo.getOrd() + "&psize=" + pageInfo.getPsize() + "\">";
      String[] arrImgs = ci.getCr_imgs().split("/");
      String topImg = arrImgs[0];
      //System.out.println(topImg);
      String idx = ci.getCr_idx() + "";
%>
      <%=lnk %><div class="outerBox">
         <div class="member">
            <div class="memberImg"><img src="../member/member_img/<%=ci.getCr_img() %>" width="40" height="40" valign="absmiddle" /></div>
            <div class="memberInfo">
               <%=ci.getMi_nick() %><br />
               <%=ci.getMi_introduce() %></a><!-- a태그를 여기서 닫아야 팔좋스 버튼 작동 -->
            </div>
            <div class="follow">
               <span>
               <% if (memberInfo != null && !login_email.equals(ci.getMi_email())) { // 로그인을 한 상태이고 글쓴이가 본인이 아니면
                  String email = ci.getMi_email();
                  //System.out.println(email);
                  if (followList != null) {   // followList가 null이 아니면
                     if (!arrFollow.contains(email)) { // 팔로잉 목록에 없는 회원이면 %>
                        <input type="button" value="팔로우" class="followbtn" onclick="dofollow('<%=ci.getMi_email()%>', '<%=login_email%>');" />
                     <% } else if (arrFollow.contains(email)) {   // 팔로잉 목록에 있는 회원이면 %>
                        <input type="button" value="팔로잉" class="followingbtn" onclick="unfollow('<%=ci.getMi_email()%>', '<%=login_email%>');" />
                     <% } 
                  }
               } else if (memberInfo == null) { %>
                  <input type="button" value="팔로우" class="followbtn" onclick="chkLogin();" />
               <% } %>
               </span>
            </div>
         </div>
         <%=lnk %><div class="imgBox"><img src="camp_img/<%=topImg %>" width="256" height="256" /></div></a>
         <div class="crinfo">
            <% if (memberInfo != null) { // 로그인을 한 상태이면 
               if (goodList != null) {   // goodList가 null이 아니면
                  if (!arrGood.contains(idx)) { // 좋아요 목록에 없는 게시글이면 %>
                     <span id="doGood<%=idx%>" class="goodscrapreply">
                        <a href="javascript:doGood('c',  <%=idx%>, '<%=login_email%>');">
                           <img src="../img/heart.png" width="20" height="20" />
                        &nbsp;&nbsp;<%=ci.getCr_good() %></a>
                     </span>
                  <% } else if (arrGood.contains(idx)) {   // 좋아요 목록에 있는 게시글이면 %>
                     <span id="unGood<%=idx%>" class="goodscrapreply">
                        <a href="javascript:unGood('c',  <%=idx%>, '<%=login_email%>');">
                           <img src="../img/heart_fill.png" width="20" height="20" class="fill" />
                        &nbsp;&nbsp;<%=ci.getCr_good() %></a>
                     </span>
                  <% }
               }
            } else if (memberInfo == null) { %>
               <span class="goodscrapreply">
                  <a href="javascript:chkLogin();">
                     <img src="../img/heart.png" width="20" height="20" />
                  &nbsp;&nbsp;<%=ci.getCr_good() %></a>
               </span>
            <% } %>
            <% if (memberInfo != null) { // 로그인을 한 상태이면 
               if (scrapList != null) {   // scrapList가 null이 아니면
                  if (!arrScrap.contains(idx)) { // 스크랩 목록에 없는 게시글이면 %>
                     <span id="doScrap<%=idx%>" class="goodscrapreply">
                        <a href="javascript:doScrap('c',  <%=idx%>, '<%=login_email%>');">
                           <img src="../img/bookmark.png" width="20" height="20" />
                        &nbsp;&nbsp;<%=ci.getCr_scrap() %></a>
                     </span>
                  <% } else if (arrScrap.contains(idx)) {   // 스크랩 목록에 있는 게시글이면 %>
                     <span id="unScrap<%=idx%>" class="goodscrapreply">
                        <a href="javascript:unScrap('c',  <%=idx%>, '<%=login_email%>');">
                           <img src="../img/bookmark_fill.png" width="20" height="20" class="fill" />
                        &nbsp;&nbsp;<%=ci.getCr_scrap() %></a>
                     </span>
                  <% }
               }
            } else if (memberInfo == null) { %>
               <span class="goodscrapreply">
                  <a href="javascript:chkLogin();">
                           <img src="../img/bookmark.png" width="20" height="20" />
                  &nbsp;&nbsp;<%=ci.getCr_scrap() %></a>
               </span>
            <% } %>
            <span class="goodscrapreply">
               <img src="../img/chat.png" width="20" height="20" />
               &nbsp;&nbsp;<%=ci.getCr_reply() %>
            </span>
         </div>
         <%=lnk %><div class="crcontent"><%=ci.getCr_content() %></div></a>
      </div>
<% 
   }
} else {
   out.println("<div align='center' style='font-size:15px; font-weight:bold;'>캠핑후기 목록이 없습니다.</div>");
}
%>
<div id="page">
<% // 정렬기준이 최신순일 때만 페이징 영역 보임
if (campList != null && campList.size() > 0) {	//&& !pageInfo.getOrd().equals("goodd"
// 게시글 검색 결과가 있으면
	args = "?ord=" + pageInfo.getOrd();
	out.println("<p style=\"width:1250px\" align=\"center\">");

	if (pageInfo.getCpage() == 1) {	// 현재 페이지 번호가 1이면
		out.println("[&lt;&lt;]&nbsp;&nbsp;[&lt;]&nbsp;&nbsp;");
	} else {
		out.print("<a href='camp_list.camp" + args + "&cpage=1" + "'>");
		out.println("[&lt;&lt;]</a>&nbsp;&nbsp;");
		out.print("<a href='camp_list.camp" + args + "&cpage=" + (pageInfo.getCpage() - 1) + "'>");
		out.println("[&lt;]</a>&nbsp;&nbsp;");
	}	// 첫 페이지와 이전 페이지 링크
	
	for (int i = 1, k = pageInfo.getSpage() ; i <= pageInfo.getBsize() && k <= pageInfo.getEpage() ; i++, k++) {
		if (pageInfo.getCpage() == k) {	// 현재 페이지 번호일 경우 링크없이 강조만 함
			out.print("&nbsp;<strong>" + k + "</strong>&nbsp;");
		} else {
			out.print("&nbsp;<a href='camp_list.camp" + args + "&cpage=" + k + "'>");
			out.print(k + "</a>&nbsp;");
		}
	}
	
	if (pageInfo.getCpage() == pageInfo.getPcnt()) {	// 현재 페이지 번호가 마지막 페이지 번호이면
		out.println("&nbsp;&nbsp;[&gt;]&nbsp;&nbsp;[&gt;&gt;]");
	} else {
		out.println("&nbsp;&nbsp;<a href='camp_list.camp" + args + "&cpage=" + (pageInfo.getCpage() + 1) + "'>[&gt;]</a>");
		out.print("&nbsp;&nbsp;<a href='camp_list.camp" + args + "&cpage=" + pageInfo.getPcnt() + "'>");
		out.println("[&gt;&gt;]</a>");	
	}

out.println("</p>");
}
%>
</div>
</div>
<%@include file ="../footer.jsp" %>