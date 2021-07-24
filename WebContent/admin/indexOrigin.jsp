<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%@include file="../admin_header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- 어드민은 회원용 url과 달라야 하므로 a를 붙임 -->

회원관리<br />
<% if(pms.equals("a") || pms.equals("b")) { %>
<a href="member_list.mema">회원 목록</a>
<a href="">회원 포인트 적립내역</a>
<hr />
<% } %>
<% if(pms.equals("a") || pms.equals("d")) { %>
<a href="pdt_list.pdta">상품 관리</a>
<hr />
<% } %>
<% if(pms.equals("a") || pms.equals("c")) { %>
<a href="order_list.orda">주문 관리</a
>
<hr />
<% } %>
<% if(pms.equals("a") || pms.equals("b")) { %>
<a href="month_list.montha">이달의추천 관리</a>
<hr />
<% } %>
<% if(pms.equals("a") || pms.equals("b")) { %>
<a href="notice_list.ntca">공지사항 관리</a>
<hr />
<% } %>
<% if(pms.equals("a") || pms.equals("b")) { %>
<a href="event_list.eventa">이벤트 관리</a>
<hr />
<% } %>
<% if(pms.equals("a") || pms.equals("b")) { %>
<a href="special_list.speciala">기획전 관리</a>
<hr />
<% } %>
<a href="logout.jsp">로그 아웃</a>
<hr />
<%
//Calendar today = Calendar.getInstance();
//int year = today.get(Calendar.YEAR);
//int month = today.get(Calendar.MONTH);
//int day = today.get(Calendar.DATE);
//today.set(year, month, day);
//System.out.println(year);
//System.out.println(month);
//System.out.println(day);
%>
</body>
</html>
