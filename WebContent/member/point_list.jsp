<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%@include file ="../header.jsp" %>
<%
if (!isLogin) {
	out.println("<script>");
	out.println("alert('로그인 후 사용하실 수 있습니다.');");
	out.println("location.href='../login_form.jsp';");
	out.println("</script>");
	out.close();
}

request.setCharacterEncoding("utf-8");
ArrayList<PointInfo> pointList = (ArrayList<PointInfo>)request.getAttribute("pointList");
PointPageInfo pageInfo = (PointPageInfo)request.getAttribute("pageInfo");

String args = "";
args = "?cpage=" + pageInfo.getCpage();	// 페이징에서 이용
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
.use { color:red; font-weight:bold; }
.save { color:#2788ff; font-weight:bold; }

#wrapper { margin:0 auto; width:1250px; padding-top:70px; }
</style>

<div id="wrapper">
	<h2>포인트 내역</h2>
	<table width="1250" cellpadding="5">
	<tr>
	<td colspan="2">
		<strong>사용가능한 포인트</strong>
	</td>
	<td width="120" align="right">
		<strong><%=pageInfo.getAvailablePoint() %> P</strong>
	</td>
	</tr>
	<tr><td colspan="3" height="40" valign="top"><hr /></td></tr>
	<% 
	if (pointList != null && pointList.size() > 0) {
	// 게시글 검색 결과가 있으면
		for (int i = 0 ; i < pointList.size() ; i++) {
			//System.out.println(freeList.size());
			PointInfo pi = pointList.get(i);
			// 사용,적립 유형에 따라 사용,적립 이미지 다르게 if(mp_kind.equals('e')) else(='a/b/c/d'이면)
			String lnk = "<a href=\"free_view.free" + args + "&idx=" + pi.getMp_idx() + "\">";
	%>
	<tr>
	<td rowspan="2" width="120">
	<% if (pi.getMp_kind().equals("u")) { // 사용이면 %>
		<img src="../img/point_use.png" width="75" height="50" alt="사용" />
	<% } else { // 적립이면  %>
		<img src="../img/point_save.png" width="75" height="50" alt="적립" />
	<% } %>
	</td>
	<td width="*">
		<%=pi.getMp_date().substring(0, 10).replace("-", ".") %><br />
		<%=pi.getMp_content() %>
	</td>
	<td rowspan="2" align="right">
	<% if (pi.getMp_kind().equals("u")) { // 사용이면 %>
		<span class="use">-<%=pi.getMp_point() %> P</span>
	<% } else { // 적립이면 %>
		<span class="save">+<%=pi.getMp_point() %> P</span>
		<% } %>
	</td>
	</tr>
	<tr>
	<td>
		<%=pi.getMp_info() %>
		<!-- 
		a:구매확정적립		- 해당 주문 상품 중 첫번째 상품정보? 클릭 시 해당 주문상세 페이지로 이동
		b:구매후기작성적립	- 일반 리뷰 작성 적립 + (상품 이름) / 포토 리뷰 작성 적립 + (상품 이름)
		c:질문과답변_답변적립	- ...
		d:기타적립			- 회원가입 시 : 회원가입을 축하합니다!
		u:구매사용			- 해당 주문 상품 중 첫번째 상품정보? 클릭 시 해당 주문상세 페이지로 이동
		-->
	</td>
	</tr>
	<tr><td colspan="3" height="60"><hr /></td></tr>
	<%	
		}
	} else {
		out.println("<tr align='center'><th colspan='3'>검색 결과가 없습니다.</th></tr>");
	}
	%>
	</table>
	<%
	if (pointList != null && pointList.size() > 0) {
	// 게시글 검색 결과가 있으면
		out.println("<p style=\"width:1250px\" align=\"center\">");
	
		if (pageInfo.getCpage() == 1) {	// 현재 페이지 번호가 1이면
			out.println("[&lt;&lt;]&nbsp;&nbsp;[&lt;]&nbsp;&nbsp;");
		} else {
			out.print("<a href='point_list.mem?cpage=1" + "'>");
			out.println("[&lt;&lt;]</a>&nbsp;&nbsp;");
			out.print("<a href='point_list.mem?cpage=" + (pageInfo.getCpage() - 1) + "'>");
			out.println("[&lt;]</a>&nbsp;&nbsp;");
		}	// 첫 페이지와 이전 페이지 링크
		
		for (int i = 1, k = pageInfo.getSpage() ; i <= pageInfo.getBsize() && k <= pageInfo.getEpage() ; i++, k++) {
			if (pageInfo.getCpage() == k) {	// 현재 페이지 번호일 경우 링크없이 강조만 함
				out.print("&nbsp;<strong>" + k + "</strong>&nbsp;");
			} else {
				out.print("&nbsp;<a href='point_list.mem?cpage=" + k + "'>");
				out.print(k + "</a>&nbsp;");
			}
		}
		
		if (pageInfo.getCpage() == pageInfo.getPcnt()) {	// 현재 페이지 번호가 마지막 페이지 번호이면
			out.println("&nbsp;&nbsp;[&gt;]&nbsp;&nbsp;[&gt;&gt;]");
		} else {
			out.println("&nbsp;&nbsp;<a href='point_list.mem?cpage=" + (pageInfo.getCpage() + 1) + "'>[&gt;]</a>");
			out.print("&nbsp;&nbsp;<a href='point_list.mem?cpage=" + pageInfo.getPcnt() + "'>");
			out.println("[&gt;&gt;]</a>");
		}
	
	out.println("</p>");
	}
	%>
</div>
<br /><br /><br /><br /><br /><br />
<%@ include file="../footer.jsp" %>