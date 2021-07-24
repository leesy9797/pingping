<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%@include file ="../../admin_header.jsp" %>
<% 
MemberInfo memberInfo = (MemberInfo)request.getAttribute("memberInfo");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
/*#sch td, #sch th, #sch tr { border:1px solid black; }*/
.cata { font-size:18px; }
.wrapper { margin:0 auto; width:1250px; padding-top:10px; }
tr, td { padding:10px 0; }
</style>
</head>
<body>
<div class="wrapper">
<h2>회원 상세보기</h2><hr /><br />
<table width="1250" cellpadding="5" cellspacing="0" id="sch">
<tr>
<th width="15%" class="cata">회원정보</th>
<td width="35%" ></td>
<th width="15%" class="cata">기본배송지정보</th>
<td width="35%" align="right"><input type="button" value="배송지 목록 보기" onclick="location.href='../member/addr_list.mem?email=<%=memberInfo.getMi_email() %>';" />&nbsp;&nbsp;</td>
</tr>
<tr>
<th>이메일</th>
<td><%=memberInfo.getMi_email() %></td>
<th>우편번호</th>
<td><%=memberInfo.getMa_zip() %></td>
</tr>
<tr>
<th>닉네임</th>
<td><%=memberInfo.getMi_nick() %></td>
<th>도로명주소</th>
<td><%=memberInfo.getMa_addr1() %></td>
</tr>
<tr>
<th>비밀번호</th>
<td>
	<%=memberInfo.getMi_pwd() %>&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="button" value="임시 비밀번호 발송" onclick="alert('임시 비밀번호 전송\n\n가입시 등록한 회원 이메일로 회원에게 임시 비밀번호를 발송합니다.\n\n전송 성공여부와 관계없이 임시 비밀번호로 재설정됩니다.');" /></td>
<th>상세주소</th>
<td><%=memberInfo.getMa_addr2() %></td>
</tr>
<tr>
<th>연락처</th>
<td><%=memberInfo.getMi_phone() %></td>
<form name="frm" action="member_info_update.mema" method="get">
<th class="cata">관리자 메모</th>
<td width="35%"><span style="float:right; padding-right:15px;"><input type="submit" value="저장" /></span></td>
</tr>
<tr>
<th>생년월일</th>
<td><%=memberInfo.getMi_birth() %></td>
<td colspan="2" rowspan="5" align="center">
<input type="hidden" name="email" value="<%=memberInfo.getMi_email() %>" />
<% 
String memo = "";
if (memberInfo.getMi_memo() != null && !memberInfo.getMi_memo().equals("null")) {
	memo = memberInfo.getMi_memo();
}
%>
<textarea rows="10" cols="80" name="memo"><%=memo %>
</textarea>
</form>
</div>
</td>
</tr>
<tr>
<th>성별</th>
<td><%=(memberInfo.getMi_gender().equals("m")) ? "남성" : "여성" %></td>
</tr>
<tr>
<th>활동여부</th>
<td><%=(memberInfo.getMi_isactive().equals("y")) ? "활동회원" : "탈퇴회원" %></td>
</tr>
<tr>
<th>최근로그인</th>
<td><%=memberInfo.getMi_lastlogin() %></td>
</tr>
<tr>
<th>가입일</th>
<td><%=memberInfo.getMi_joindate() %></td>
</tr>
<tr><td height="20"></td></tr>
<tr>
<td colspan="4" align="center">
	<input type="button" value="전체 회원 목록" onclick="location.href='member_list.mema';" /></td>
</tr>
<tr>
<td colspan="4" style="border:1px solid #efefef;">
<iframe width="100%" height="1300" src="../admin/order_list.orda?miemail=<%=memberInfo.getMi_email() %>" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
</td>
</tr>
</table>
</div>
<br />
<br />
<br />
<br />
<br />
</body>
</html>