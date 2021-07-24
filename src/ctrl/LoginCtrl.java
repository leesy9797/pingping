package ctrl;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;	
import svc.*;
import vo.*;

@WebServlet("/login")
public class LoginCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public LoginCtrl() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		String email = request.getParameter("email");
		String pwd = request.getParameter("pwd");
		
		String url = request.getParameter("url");
		// �α��� �Ŀ� �̵��� �ּҷ� ���� ��쿡�� �� ���ڿ��� ����
		
		if (url.equals("")) {
			url = "index.jsp";
		} // �α��� �Ŀ� �̵��� �ּҷ� ���� ��쿡�� index ȭ������ �̵��ϵ��� �ּҸ� ����
		
		LoginSvc loginSvc = new LoginSvc();
		
		MemberInfo memberInfo = loginSvc.getMemberInfo(email, pwd);
		
		HttpSession session = request.getSession();
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();

		System.out.println("�ƾ�, ����� LoginCtrl");
		
		if (memberInfo != null) {	// �α��� ������			
			session.setAttribute("memberInfo", memberInfo);
			response.sendRedirect(url);
			
		} else {	// �α��� ���н�
			out.println("<script>");
			out.println("alert('�α��ο� �����Ͽ����ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
			
		}
		
	}

}
