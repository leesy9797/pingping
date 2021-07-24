<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%@include file ="../header.jsp" %>
<%
ArrayList<ReviewInfo> reviewList = (ArrayList<ReviewInfo>)request.getAttribute("reviewList");
ReviewPageInfo pageInfo = (ReviewPageInfo)request.getAttribute("pageInfo");

String args = "?cpage=" + pageInfo.getCpage();

String[] arrStar = {"★☆☆☆☆", "★★☆☆☆", "★★★☆☆", "★★★★☆", "★★★★★"};
%>

<style>
a:link { color:#4f4f4f; text-decoration:none; }
a:visited { color:#4f4f4f; text-decoration:none; }
a:hover { color:#4f4f4f; text-decoration:underline; }
a:focus { color:#4f4f4f; text-decoration:underline; }
a:active { color:#4f4f4f; text-decoration:underline; }

#wrapper { margin:0 auto; width:1250px; }
#select { color:#2788ff; font-weight:bold; }
</style>

<div id="wrapper">
   <h2>내가 작성한 리뷰</h2><br />
   <div class="" align="center"><hr />
      <a href="review_list.review" id="select">나의 리뷰</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <a href="review_write_list.review">작성가능한 리뷰</a>
   <hr /></div><br />
   <p style="width:1250px;" align="left">
   <!-- 정렬조건(등록순 - 내림차순(기본값), 별점순 - 내림차순) -->
      <a href="review_list.review?cpage=1&ord=lastdated" style="font-weight:<%=(pageInfo.getOrd().equals("lastdated")) ? "bold" : "normal" %>">최신순</a>&nbsp;
      <a href="review_list.review?cpage=1&ord=stard" style="font-weight:<%=(pageInfo.getOrd().equals("stard")) ? "bold" : "normal" %>">별점순</a>&nbsp;
   </p>
   <table width="1250" cellpadding="5" align="center">
   <tr><td colspan="3" height="50" valign="top"><hr /></td></tr>
   <% 
   if (reviewList != null && reviewList.size() > 0) {
   // 리뷰 검색 결과가 있으면
      for (int i = 0 ; i < reviewList.size() ; i++) {
         //System.out.println(reviewList.size());
         ReviewInfo ri = reviewList.get(i);
         String lnk = "<a href=\"../product/pdt_view.pdt?id=" + ri.getPi_id() + "\">";
   %>
   <tr valign="top">
   <td width="8%">
      <%=lnk %><img src="../product/pdt_img/<%=ri.getPi_img1() %>" width="70" height="70" /></a>
   </td>
   <td width="*" valign="top">
      <%=lnk %><strong>[<%=ri.getBr_name() %>]&nbsp;<%=ri.getPi_name() %></strong></a><br />
      <%=ri.getBr_opt() %><br />
      <%=ri.getOd_pdtprice() %>원 ㅣ <%=ri.getOd_cnt() %>개
   </td>
   <td width="15%" align="right">
      <input type="button" value="수정하기" onclick="location.href='review_up_form.review<%=args %>&idx=<%=ri.getPr_idx() %>&ord=<%=pageInfo.getOrd() %>'" />
   </td>
   </tr>
   <tr>
   <td colspan="2">
      <%
      for (int j = 0 ; j < arrStar.length ; j++) {
         if (ri.getPr_star() == j+1){
            out.println(arrStar[j]);
         }
      }
      %>&nbsp;&nbsp;&nbsp;
      <%=ri.getPr_date().substring(0, 10) %><br />
      <%=ri.getPr_content() %>
   </td>
   <td align="right">
   <% if (ri.getPr_img1() != null && !ri.getPr_img1().equals("")) { %>
      <img src="../review/review_img/<%=ri.getPr_img1() %>" width="100" height="100" />
   <% } %>
   </td>
   </tr>
   <tr><td colspan="3" height="80"><hr /></td></tr>
   <% 
      }
   } else {
      out.println("<tr><th>작성한 리뷰가 없습니다.</th></tr>");
   } 
   %>
   </table>
   <%
   if (reviewList != null && reviewList.size() > 0) {
   // 게시글 검색 결과가 있으면
      args = "?ord=" + pageInfo.getOrd();
      out.println("<p style=\"width:1250px\" align=\"center\">");
   
      if (pageInfo.getCpage() == 1) {   // 현재 페이지 번호가 1이면
         out.println("[&lt;&lt;]&nbsp;&nbsp;[&lt;]&nbsp;&nbsp;");
      } else {
         out.print("<a href='review_list.review" + args + "&cpage=1" + "'>");
         out.println("[&lt;&lt;]</a>&nbsp;&nbsp;");
         out.print("<a href='review_list.review" + args + "&cpage=" + (pageInfo.getCpage() - 1) + "'>");
         out.println("[&lt;]</a>&nbsp;&nbsp;");
      }   // 첫 페이지와 이전 페이지 링크
      
      for (int i = 1, k = pageInfo.getSpage() ; i <= pageInfo.getBsize() && k <= pageInfo.getEpage() ; i++, k++) {
         if (pageInfo.getCpage() == k) {   // 현재 페이지 번호일 경우 링크없이 강조만 함
            out.print("&nbsp;<strong>" + k + "</strong>&nbsp;");
         } else {
            out.print("&nbsp;<a href='review_list.review" + args + "&cpage=" + k + "'>");
            out.print(k + "</a>&nbsp;");
         }
      }
      
      if (pageInfo.getCpage() == pageInfo.getPcnt()) {   // 현재 페이지 번호가 마지막 페이지 번호이면
         out.println("&nbsp;&nbsp;[&gt;]&nbsp;&nbsp;[&gt;&gt;]");
      } else {
         out.println("&nbsp;&nbsp;<a href='review_list.review" + args + "&cpage=" + (pageInfo.getCpage() + 1) + "'>[&gt;]</a>");
         out.print("&nbsp;&nbsp;<a href='review_list.review" + args + "&cpage=" + pageInfo.getPcnt() + "'>");
         out.println("[&gt;&gt;]</a>");   
      }
   
   out.println("</p>");
   }
   %>
</div>
<%@include file ="../footer.jsp" %>