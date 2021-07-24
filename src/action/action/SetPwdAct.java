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

		System.out.println("�ƾ�, ����� SetPwdAct");
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
			out.println("alert('��й�ȣ �缳���� ������ �߻��߽��ϴ�. \n�ٽ� �õ��� �ּ���.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}

		System.out.println("�ƾ�, ����� SetPwdAct");
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(true);	// dispatch�� �ƴ� sendRedirect ������� �̵�
		forward.setPath("../login_form.jsp");
		
		return forward;
	
	}
}
