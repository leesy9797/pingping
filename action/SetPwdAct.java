package action;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import svc.*;
import vo.*;

public class SetPwdAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String email = request.getParameter("email");

		MemberInfo memberInfo = null;

		System.out.println("아아, 여기는 SetPwdAct");
		System.out.println(email);
		
		//MemberInfo memberInfo = null;
		String pwd = "";
		if (request.getParameter("pwd") != null) {
			pwd = request.getParameter("pwd").trim();
		}	
		
		memberInfo = new MemberInfo();
		memberInfo.setMi_email(email);
		memberInfo.setMi_pwd(pwd);
		
		SetPwdSvc setPwdSvc = new SetPwdSvc();
		
		int result = setPwdSvc.setPwd(memberInfo);
		request.setAttribute("memberInfo", memberInfo);
		
		if (result == 0) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('비밀번호 재설정에 문제가 발생했습니다. \n다시 시도해 주세요.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}

		System.out.println("아아, 여기는 SetPwdAct");
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(true);	// dispatch가 아닌 sendRedirect 방식으로 이동
		forward.setPath("../login_form.jsp");
		
		return forward;
	
	}
}
