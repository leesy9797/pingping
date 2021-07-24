<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>

<style>
   .eSlide { 
     position: absolute;
  	 left: 50%;
     transform: translateX(-50%); 
     max-width:700px;
          }
     
   .bReview {
   	 position : relative;
   	 top:355px;
   }
   
   .qnaMain {
    left: 50%;
     transform: translateX(-50%); 
   	 position : relative;
   	 top:350px;
   	 max-width : 950px;
   }
   
   .footer {
   	position:relative;
   	top:200px;
   }

</style>
<%@include file ="./header.jsp" %>

<% if (memberInfo == null) { %>
<a href="login_form.jsp">로그인</a><br />
<a href="member/join_form.jsp">회원가입</a>
<% } else { %>
<%=memberInfo.getMi_email() %>(<%=memberInfo.getMi_nick() %>)님 환영합니다.<br />
<a href="member/mypage.mem">정보수정</a><br />
<a href="member/addr_list.mem">회원주소록</a><br />
<a href="review/review_write_list.review">리뷰쓰기</a><br />
<a href="review/review_list.review">내가 쓴 리뷰 목록(수정)</a><br />
<a href="member/point_list.mem">포인트 내역</a><br />
<a href="member/follower_list.mem">팔로워 목록(원래는 마이페이지에서 팔로워/팔로잉 누르면 이동)</a><br />
<a href="member/following_list.mem">팔로잉 목록</a><br />
<a href="service/qna_in_form.service">1:1문의</a><br />
<a href="logout.jsp">로그아웃</a>
<% } %>
<hr />
<a href="brd_list.ntc">공지 사항</a>
<hr />
<a href="pdt_list.pdt">상품 목록</a>
<hr />
<a href="order_list.ord">주문/배송내역</a>
<hr />
<a href="cart_list.ord">장바구니</a>
<hr />
<a href="camping/camp_list.camp">캠핑후기 목록</a>
<hr />
<a href="free/free_list.free">질문과답변 목록</a>
<hr />
<a href="monthly/month_list.month">이달의추천 목록</a>
<hr />
<a href=""></a>
<div class="eSlide"><%@include file ="./event_slide.jsp" %></div>
<div class="bReview"><%@include file ="./best_review.jsp" %></div>
<div class="qnaMain"><%@include file ="./qnaMain.jsp" %></div>
<div class="contents"></div>
<div class="footer"><%@ include file="./footer.jsp" %></div>