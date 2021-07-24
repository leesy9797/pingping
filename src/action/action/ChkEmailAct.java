package action;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import svc.*;
import vo.*;

public class ChkEmailAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");

		System.out.println("�ƾ�, ����� ChkEmailAct");
		
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
			out.println("alert('������ �Է��Ͻ� �̸��ϰ� ��ġ�ϴ� ȸ�������� �����ϴ�.\\n�ٽ� �� �� Ȯ�����ּ���.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		} else {
			request.setAttribute("memberInfo", memberInfo);
		}
		
		//System.out.println(memberInfo.getMi_email());
		
		System.out.println("�ƾ�, ����� ChkEmailAct");
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(true);	// dispatch�� �ƴ� sendRedirect ������� �̵�
		forward.setPath("reset_pwd.jsp");
		
		return forward;
	
	}
}
