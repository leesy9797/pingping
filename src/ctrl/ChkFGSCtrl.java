package ctrl;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

@WebServlet("*.chkFGS")
public class ChkFGSCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ChkFGSCtrl() {
        super();
    }
    
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestUri.substring(contextPath.length());
		System.out.println(command);
		
		String ciemail = request.getParameter("ciemail");
		String miemail = request.getParameter("miemail");
		String kind = request.getParameter("kind");
		String linkidx = request.getParameter("idx");
				
		// ������� ��û�� ���� ���� �ٸ� �۾��� ����
		switch (command) { 
		case "/dofollow.chkFGS" : 	// ���� �ش� ȸ���� �ȷο� ������ �˻� �� �ȷο�
			try {
				ChkFGSSvc chkFGSSvc = new ChkFGSSvc();
				int result = chkFGSSvc.doFollow(ciemail, miemail);
				out.println(result);
			} catch(Exception e) {
				e.printStackTrace();
				out.println(0);
			}
			break;
		case "/unfollow.chkFGS" : 	// ���� �ش� ȸ���� �ȷο� ������ �˻� �� �ȷο� ���
			try {
				ChkFGSSvc chkFGSSvc = new ChkFGSSvc();
				int result = chkFGSSvc.unFollow(ciemail, miemail);
				out.println(result);
			} catch(Exception e) {
				e.printStackTrace();
				out.println(0);
			}
			break;
		case "/doGood.chkFGS" : 	// ���� �ش� �Խñ��� ���ƿ� ������ �˻� �� ���ƿ�
			try {
				ChkFGSSvc chkFGSSvc = new ChkFGSSvc();
				int result = chkFGSSvc.doGood(kind, linkidx, miemail);
				out.println(result);
			} catch(Exception e) {
				e.printStackTrace();
				out.println(0);
			}
			break;
		case "/unGood.chkFGS" : 	// ���� �ش� �Խñ��� ���ƿ� ������ �˻� �� ���ƿ� ���
			try {
				ChkFGSSvc chkFGSSvc = new ChkFGSSvc();
				int result = chkFGSSvc.unGood(kind, linkidx, miemail);
				out.println(result);
			} catch(Exception e) {
				e.printStackTrace();
				out.println(0);
			}
			break;
		case "/doScrap.chkFGS" : 	// ���� �ش� �Խñ��� ��ũ�� ������ �˻� �� ��ũ��
			try {
				ChkFGSSvc chkFGSSvc = new ChkFGSSvc();
				int result = chkFGSSvc.doScrap(kind, linkidx, miemail);
				out.println(result);
			} catch(Exception e) {
				e.printStackTrace();
				out.println(0);
			}
			break;
		case "/unScrap.chkFGS" : 	// ���� �ش� �Խñ��� ��ũ�� ������ �˻� �� ��ũ�� ���
			try {
				ChkFGSSvc chkFGSSvc = new ChkFGSSvc();
				int result = chkFGSSvc.unScrap(kind, linkidx, miemail);
				out.println(result);
			} catch(Exception e) {
				e.printStackTrace();
				out.println(0);
			}
			break;
		}
		
		System.out.println("�ƾ�, ����� ChkFGSCtrl");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
}
