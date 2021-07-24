package admin.ctrl;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import admin.svc.*;
import vo.*;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/adminLogin")
public class AdminLoginCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public AdminLoginCtrl() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// post������� �����͸� ������ ��� �����ϴ� ������ �޼ҵ�
    	System.out.println("����� AdminLoginCtrl");
		request.setCharacterEncoding("utf-8");
		// ������� ��û���� ���� �����͵��� ���ڵ� ����� �����ڵ�� ����
		String uid = request.getParameter("uid");
		String pwd = request.getParameter("pwd");
		// ����ڰ� ���� ������ ����ִ� ��Ʈ��(uid, pwd)���� ���� ���� uid, pwd�� ����

		AdminLoginSvc adminLoginSvc = new AdminLoginSvc();
		// �α��ο� �ʿ��� �۾��� ���� �α��� ����� �޼ҵ带 ���� Ŭ������ �ν��Ͻ� ����
		AdminInfo adminInfo = adminLoginSvc.getLoginAdmin(uid, pwd);
		// ����ڿ��� �Է¹��� ���̵�� ��й�ȣ�� �μ��� �Ͽ� adminLoginSvc �ν��Ͻ��� getLoginAdmin() �޼ҵ带 ȣ���ϰ�
		// �� ����� AdminInfo�� �ν��Ͻ� adminInfo�� ����
		// �޾ƿ� adminInfo �ν��Ͻ����� �α����� ȸ���� �������� �������
		// ��, �α��� ���� �� adminInfo �ν��Ͻ����� null���� ����ְ� ��
		
		HttpSession session = request.getSession();
		// JSP�� �ٸ��� ���� ��ü�� ����Ϸ��� ���� HttpSession�� �ν��Ͻ��� �����ؾ� ��

		response.setContentType("text/html; charset=utf-8");
		// �����ϴ� �������� Ÿ���� text�� html�� ���� - ���ڵ��� �����ڵ�� ����
		PrintWriter out = response.getWriter();

		if (adminInfo != null) {
			session.setAttribute("adminInfo", adminInfo);
			// �α��� ���¸� �����ϰ�, �ٸ� ������������ �α��� ������ ����� �� �ֵ��� ���ǿ� ����
			out.println("<script>");
			out.println("location.href = 'admin/member_list.mema';");
			out.println("</script>");
			//response.sendRedirect("admin/member_list.mema");
		} else {
			out.println("<script>");
			out.println("alert('�α��ο� �����߽��ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
		}
	}
}
