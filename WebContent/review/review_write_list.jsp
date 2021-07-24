<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%@include file ="../header.jsp" %>
<%
ArrayList<ReviewInfo> reviewList = (ArrayList<ReviewInfo>)request.getAttribute("reviewList");
ReviewPageInfo pageInfo = (ReviewPageInfo)request.getAttribute("pageInfo");
%>

<style>
a:link { color:#4f4f4f; text-decoration:none; }
a:visited { color:#4f4f4f; text-decoration:none; }
a:hover { color:#4f4f4f; text-decoration:none; }
a:focus { color:#4f4f4f; text-decoration:none; }
a:active { color:#4f4f4f; text-decoration:none; }

#wrapper { margin:0 auto; width:1250px; }
#select { color:#2788ff; font-weight:bold; }
</style>

<div id="wrapper">
   <h2>리뷰쓰기</h2><br />
   <div class="" align="center"><hr />
      <a href="review_list.review">나의 리뷰</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <a href="review_write_list.review" id="select">작성가능한 리뷰</a>
   <hr /></div><br />
   <table width="1250" cellpadding="5" align="center">
   <tr><td colspan="3" height="50"><hr /></td></tr>
   <% 
   if (reviewList != null && reviewList.size() > 0) {
   // 리뷰 검색 결과가 있으면
      for (int i = 0 ; i < reviewList.size() ; i++) {
         //System.out.println(reviewList.size());
         ReviewInfo ri = reviewList.get(i);
         String lnk = "<a href=\"../product/pdt_view.pdt?id=" + ri.getPi_id() + "\">";
   %>
   <tr valign="top">
   <td width="10%">
      <%=lnk %><img src="../product/pdt_img/<%=ri.getPi_img1() %>" width="100" height="100" /></a>
   </td>
   <td width="*">
      <%=lnk %><%=ri.getBr_name() %><br />
      <strong><%=ri.getPi_name() %></strong></a><br />
      옵션 : <%=ri.getOd_option() %><br />
      <%=ri.getOd_pdtprice() %>원 ㅣ    <%=ri.getOd_cnt() %>개
   </td>
   <td width="15%" align="right" valign="middle">
      <button style="padding: 20px 3px;" onclick="location.href='review_in_form.review?id=<%=ri.getOd_id() %>'">리뷰쓰기<br />포토리뷰 +500P</button>
   </td>
   </tr>
   <tr><td colspan="3" height="50"><hr /></td></tr>
   <% 
      }
   } else {
      out.println("<tr><th>작성 가능한 리뷰가 없습니다.</th></tr>");
   } 
   %>
   </table>
   <%
   if (reviewList != null && reviewList.size() > 0) {
   // 게시글 검색 결과가 있으면
      out.println("<p style=\"width:1250px\" align=\"center\">");
   
      if (pageInfo.getCpage() == 1) {   // 현재 페이지 번호가 1이면
         out.println("[&lt;&lt;]&nbsp;&nbsp;[&lt;]&nbsp;&nbsp;");
      } else {
         out.print("<a href='review_write_list.review?cpage=1" + "'>");
         out.println("[&lt;&lt;]</a>&nbsp;&nbsp;");
         out.print("<a href='review_write_list.review?cpage=" + (pageInfo.getCpage() - 1) + "'>");
         out.println("[&lt;]</a>&nbsp;&nbsp;");
      }   // 첫 페이지와 이전 페이지 링크
      
      for (int i = 1, k = pageInfo.getSpage() ; i <= pageInfo.getBsize() && k <= pageInfo.getEpage() ; i++, k++) {
         if (pageInfo.getCpage() == k) {   // 현재 페이지 번호일 경우 링크없이 강조만 함
            out.print("&nbsp;<strong>" + k + "</strong>&nbsp;");
         } else {
            out.print("&nbsp;<a href='review_write_list.review?cpage=" + k + "'>");
            out.print(k + "</a>&nbsp;");
         }
      }
      
      if (pageInfo.getCpage() == pageInfo.getPcnt()) {   // 현재 페이지 번호가 마지막 페이지 번호이면
         out.println("&nbsp;&nbsp;[&gt;]&nbsp;&nbsp;[&gt;&gt;]");
      } else {
         out.println("&nbsp;&nbsp;<a href='review_write_list.review?cpage=" + (pageInfo.getCpage() + 1) + "'>[&gt;]</a>");
         out.print("&nbsp;&nbsp;<a href='review_write_list.review?cpage=" + pageInfo.getPcnt() + "'>");
         out.println("[&gt;&gt;]</a>");   
      }
   
   out.println("</p>");
   }
   %>
</div>
<%@include file ="../footer.jsp" %>