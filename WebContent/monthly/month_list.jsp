<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%@include file ="../header.jsp" %>
<%
ArrayList<MonthlyInfo> monthList = (ArrayList<MonthlyInfo>)request.getAttribute("monthList");
MonthlyPageInfo pageInfo = (MonthlyPageInfo)request.getAttribute("pageInfo");
ArrayList<GoodInfo> goodList = (ArrayList<GoodInfo>)request.getAttribute("goodList");

String login_email = "";
if (memberInfo != null)		login_email = memberInfo.getMi_email();
System.out.println("login_email : " + login_email);

//goodList null이 아니면 좋아요한 게시글 번호을 배열로 받아옴
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

Calendar today = Calendar.getInstance();
int month = today.get(Calendar.MONTH) + 1;	// 현재 월

String args = "", schargs = "";
// 검색관련 쿼리스트링 제작
if (pageInfo.getName() == null)		schargs += "&name=";
else								schargs += "&name=" + pageInfo.getName();
if (pageInfo.getLocation() == null)	schargs += "&location=";
else								schargs += "&location=" + pageInfo.getLocation();

String[] arrMonth = {month+""}, arrInfo = {""};
if (pageInfo.getMonth() == null) {
	schargs += "&month=";
} else {
	arrMonth = pageInfo.getMonth().split("/");
	for (int i = 0 ; i < arrMonth.length ; i++) {
		schargs += "&month=" + arrMonth[i];
	}
}
if (pageInfo.getInfo() == null) {
	schargs += "&info=";
} else {
	arrInfo = pageInfo.getInfo().split("/");
	for (int i = 0 ; i < arrInfo.length ; i++) {
		schargs += "&info=" + arrInfo[i];
	}
}

args = "?cpage=" + pageInfo.getCpage() + schargs;

String[] arrInfoTest = {"오토캠핑", "글램핑", "카라반", "방갈로", "펜션", "기타"};
%>

<style>
a:link { color:#000; text-decoration:none; }
a:visited { color:#000; text-decoration:none; }
a:hover { color:#2788ff; text-decoration:none; }
a:focus { color:#2788ff; text-decoration:none; }
a:active { color:#2788ff; text-decoration:none; }

#wrapper { margin:0 auto; width:1250px; background-color:#fff; }
#frmSch { margin:0 auto; padding-top:40px; width:720px; }
.card { margin-bottom:30px; padding:8px; width:307px; height:407px; display:inline-block; overflow:hidden; }
.goodreplyread { padding:15px 0; }
#read { text-align:right; }
.sch-line { padding:8px 0; }
.card-text { width:290px; padding-left:5px; height:136px; }
.mr-title { height:17px; font-size:17px; font-weight:bold; }
.mr-name { height:5px; }
.mr-content { height:19px; vertical-align:top; }
</style>
<script>
function chkLogin() {
// 비로그인 상태로 팔로우, 좋아요, 스크랩 클릭 시 로그인 폼으로 이동시키고 로그인 시 다시 돌아감
	var lnk = "monthly/month_list.month";
<% if (!isLogin) { %>
	location.href = "../login_form.jsp?url=" + lnk;
<% } else { %>
	location.href = "month_list.month";
<% } %>
}

// 좋아요
function doGood(kind, idx, miemail) {	// 좋아요 한 게시글 번호, 내 이메일
	$.ajax({
		type : "POST",
		url : "/pingping/doGood.chkFGS",
		data : {"kind":kind, "idx":idx, "miemail":miemail},
		success : function(result) {
			if (result != 2) {	// 좋아요 실패 시
				alert("좋아요에 실패했습니다.");
			} else {	// 좋아요 성공 시
				location.reload();
			}
		}
	});
}

// 좋아요 취소
function unGood(kind, idx, miemail) {	// 좋아요 취소할 게시글 번호, 내 이메일
	$.ajax({
		type : "POST",
		url : "/pingping/unGood.chkFGS",
		data : {"kind":kind, "idx":idx, "miemail":miemail},
		success : function(result) {
			if (result != 2) {	// 좋아요 취소 실패 시
				alert("좋아요 취소에 실패했습니다.");
			} else {	// 좋아요 취소 성공 시
				location.reload();
			}
		}
	});
}
</script>

<div id="wrapper">
	<div class="eSlide"><%@include file ="./monthly_slide.jsp" %></div>
	<div id="frmSch">
		<form name="frmSch" action="" method="get">
		<input type="hidden" name="ord" value="<%=pageInfo.getOrd() %>" />
		<table cellpadding="5">
		<tr>
		<th width="15%" class="sch-line">캠핑장 이름</th>
		<td width="39%">
			<input type="text" name="name" size="25" value="<%=pageInfo.getName() %>" />
		</td>
		<th width="17%">캠핑장 위치</th>
		<td width="29%">
			<input type="text" name="location" size="25" value="<%=pageInfo.getLocation() %>" />
		</td>
		</tr>
		<tr>
		<th class="sch-line">추천 월</th>
		<td colspan="3">
		<% 
		for (int i = 1 ; i <= 12 ; i++) { 
			String j = i < 10 ? "0" + i : "" + i;
			if (i == month) {
		%>
			<label for="month<%=j%>"><input type="checkbox" name="month" id="month<%=j%>" value="<%=j%>" <% if(Arrays.asList(arrMonth).contains(i+"") || Arrays.asList(arrMonth).contains(j)) { %>checked="checked"<% } %>> <%=i%>월</label>
		<% } else { %>
			<label for="month<%=j%>"><input type="checkbox" name="month" id="month<%=j%>" value="<%=j%>" <% if(Arrays.asList(arrMonth).contains(j)) { %>checked="checked"<% } %>> <%=i%>월</label>
		<% 
			}
		}
		%>
		</td>
		</tr>
		<tr>
		<th class="sch-line">캠핑형태</th>
		<td colspan="3">
		<% for (int i = 0 ; i < arrInfoTest.length ; i++) { %>
				<label for="info<%=i+1%>"><input type="checkbox" name="info" id="info<%=i+1%>" value="<%=arrInfoTest[i]%>" <% if (Arrays.asList(arrInfo).contains(arrInfoTest[i])) { %>checked="checked"<% } %>> <%=arrInfoTest[i]%></label>
		<% } %>
		</td>
		</tr>
		<tr><td colspan="4" align="center" class="sch-line">
			<input type="submit" value="검색하기" />
			&nbsp;&nbsp;
			<input type="reset" value="다시 입력" />
			&nbsp;&nbsp;
			<input type="button" value="전체검색" onclick="location.href='./month_list.month';" /><!-- 폼안에 있으면 왜 안되지 -->
		</td></tr>
		<tr><td colspan="4" height="60"></td></tr>
		</table>
		</form>
	</div>
	<p style="width:1250px;" align="right">
		<a href="month_list.month?cpage=1<%=schargs %>&ord=lastdated" <% if (pageInfo.getOrd().equals("lastdated")) { %>style="font-weight:bold;color:#2788ff;"<% } %>>최신순</a>&nbsp;
		<a href="month_list.month?cpage=1<%=schargs %>&ord=goodd" <% if (pageInfo.getOrd().equals("goodd")) { %>style="font-weight:bold;color:#2788ff;"<% } %>>인기순</a>&nbsp;
	</p>
	<table width="1250" cellpadding="10">
	<% 
	if (monthList != null && monthList.size() > 0) {	// 게시물 검색결과가 있으면
		for (int i = 0 ; i < monthList.size() ; i++) {	// 캠핑장이미지, 글 제목, 캠핑장이름, 캠핑장소개 
			MonthlyInfo mi = monthList.get(i);
			String lnk = "<a href=\"month_view.month" + args + "&idx=" + mi.getMr_idx() + "&ord=" + pageInfo.getOrd() + "\">";
			String idx = mi.getMr_idx() + "";
			if (i % 4 == 0) { %>
			<tr align="left" valign="top">
			<% } %>
			<td class="card">
				<table>
				<tr align="center">
				<td colspan="3">
					<%=lnk %><img src="month_img/<%=mi.getMr_imgs().substring(0, mi.getMr_imgs().indexOf("/")) %>" width="280" height="200" /></a>
				</td>
				</tr>
				<tr>
				<td width="30%" class="goodreplyread">
					&nbsp;
				<% if (memberInfo != null) { // 로그인을 한 상태이면 
					if (goodList != null) {	// goodList가 null이 아니면
						if (!arrGood.contains(idx)) { // 좋아요 목록에 없는 게시글이면 %>
							<span id="doGood<%=idx%>" class="goodscrapreply">
								<a href="javascript:doGood('m',  <%=idx%>, '<%=login_email%>');">
									<img src="../img/heart.png" width="16" height="16" />
								&nbsp;&nbsp;<%=mi.getMr_good() %></a>
							</span>
						<% } else if (arrGood.contains(idx)) {	// 좋아요 목록에 있는 게시글이면 %>
							<span id="unGood<%=idx%>" class="goodscrapreply">
								<a href="javascript:unGood('m',  <%=idx%>, '<%=login_email%>');">
									<img src="../img/heart_fill.png" width="16" height="16" class="fill" />
								&nbsp;&nbsp;<%=mi.getMr_good() %></a>
							</span>
						<% }
					}
				} else if (memberInfo == null) { %>
					<span class="goodscrapreply">
						<a href="javascript:chkLogin();">
							<img src="../img/heart.png" width="16" height="16" />
						&nbsp;&nbsp;<%=mi.getMr_good() %></a>
					</span>
				<% } %>
				</td>
				<td width="30%" class="goodreplyread">
					<img src="../img/chat.png" width="16" height="16" alt="댓글"/>
					&nbsp;<%=mi.getMr_reply() %>
				</td>
				<td width="40%" class="goodreplyread" id="read">
					조회수&nbsp;&nbsp;<%=mi.getMr_read() %>&nbsp;
				</td>
				</tr>
				<tr><td colspan="3">
					<div class="card-text">
						<div class="mr-title"><%=lnk %><%=mi.getMr_title() %></div><br />
						<div class="mr-name"><%=mi.getMr_name() %></div><br /><br />
						<div class="mr-content"><%=mi.getMr_content().replace("\r\n", "<br />") %></a></div>
					</div>
				</td></tr>
				</table>
			</td>
			<% if (i % 4 == 3 || i == monthList.size() - 1) { %></tr><% }  
		}
	} else {
		out.println("<tr><th>이달의추천 게시물이 없습니다.</th></tr>");
	}
	%>
	</table>
	<br /><br />
	<%
	if (monthList != null && monthList.size() > 0) {
		args = "?ord=" + pageInfo.getOrd() + schargs;
		
		out.println("<p style=\"width:1250px;\" align=\"center\">");
		
		if (pageInfo.getCpage() == 1) {	// 현재 페이지 번호가 1이면
			out.println("[&lt;&lt;]&nbsp;&nbsp;&nbsp;[&lt;]&nbsp;&nbsp;");
		} else {
			out.print("<a href='month_list.month" + args + "&cpage=1" + "'>");
			out.println("[&lt;&lt;]</a>&nbsp;&nbsp;");
			out.print("<a href='month_list.month" + args + "&cpage=" + (pageInfo.getCpage() - 1) + "'>");
			out.println("[&lt;]</a>&nbsp;&nbsp;");
		} // 첫 페이지와 이전 페이지 링크
		
		for (int i = 1, k = pageInfo.getSpage() ; i <= pageInfo.getBsize() && k <= pageInfo.getEpage() ; i++, k++) {
			if (pageInfo.getCpage() == k) {
				out.print("&nbsp;<strong>" + k + "</strong>&nbsp;");
			} else {
				out.print("&nbsp;<a href='month_list.month" + args + "&cpage=" + k + "'>");
				out.print(k + "</a>&nbsp;");
			}
		}
	
		if (pageInfo.getCpage() == pageInfo.getPcnt()) {	// 현재 페이지 번호가 마지막 페이지 번호이면
			out.println("&nbsp;&nbsp;[&gt;]&nbsp;&nbsp;[&gt;&gt;]");
		} else {
			out.println("&nbsp;&nbsp;<a href='month_list.month" + args + "&cpage=" + (pageInfo.getCpage() + 1) + "'>&nbsp;[&gt;]</a>");
			out.print("&nbsp;&nbsp;<a href='month_list.month" + args + "&cpage=" + pageInfo.getPcnt() + "'>");
			out.println("[&gt;&gt;]</a>");
		} // 마지막 페이지와 이전 페이지 링크
	
		out.println("</p>");
	}
	%>
</div>
<br /><br />
<%@include file ="../footer.jsp" %>
