package action;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import svc.*;
import vo.*;

public class ChkEmailAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");

		System.out.println("아아, 여기는 ChkEmailAct");
		
		MemberInfo memberInfo = null;
		String email = "";
		if (request.getParameter("email") != null) {
			email = request.getParameter("email").trim();
		}	
		
		memberInfo = new MemberInfo();
		memberInfo.setMi_email(email);
		
		ChkEmailSvc chkEmailSvc = new ChkEmailSvc();
		
		memberInfo = chkEmailSvc.chkEmail(email);

		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		if (memberInfo == null) {
			out.println("<script>");
			out.println("alert('고객님이 입력하신 이메일과 일치하는 회원정보가 없습니다.\\n다시 한 번 확인해주세요.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		} else {
			request.setAttribute("memberInfo", memberInfo);
		}
		
		//System.out.println(memberInfo.getMi_email());
		
		System.out.println("아아, 여기는 ChkEmailAct");
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(true);	// dispatch가 아닌 sendRedirect 방식으로 이동
		forward.setPath("reset_pwd.jsp");
		
		return forward;
	
	}
}
