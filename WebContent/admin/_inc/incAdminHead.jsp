<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="vo.*" %>
<%
request.setCharacterEncoding("utf-8");

AdminInfo adminInfo = (AdminInfo)session.getAttribute("adminInfo");

if (adminInfo == null) {
	out.println("<script>");
	out.println("alert('로그인 후 사용하실 수 있습니다.');");
	out.println("location.href='login_form.jsp';");
	out.println("</script>");
	out.close();
}

///mvcSite/admin/product/pdt_list.jsp
//out.println(arrUri[(arrUri.length-2)]);

//String cURI = request.getRequestURI();
//int endIdx = cURI.lastIndexOf('/');
//int startIdx = cURI.lastIndexOf('/', endIdx - 1);
//out.println(cURI.substring(startIdx + 1, endIdx));

// 권한(a:전체, b:고객센터-custom, c:주문-order, d:상품-product)
String[] arrUri = request.getRequestURI().split("/");
String cFolder = arrUri[(arrUri.length-2)];		// 현재 파일의 기능명
String admin_pms = adminInfo.getAi_pms();	// 권한 여부를 저장할 변수

boolean isPms = false;	
if (admin_pms.equals("a"))		isPms = true;
else if (cFolder.equals("custom") && admin_pms.equals("b"))		isPms = true;
else if (cFolder.equals("order") && admin_pms.equals("c"))		isPms = true;
else if (cFolder.equals("product") && admin_pms.equals("d"))	isPms = true;

if (!isPms) {
	out.println("<script>");
	out.println("alert('사용 권한이 없습니다.');");
	out.println("location.href='../logout.jsp';");
	out.println("</script>");
	out.close();
}
%>
<a href="../admin/">인덱스</a>