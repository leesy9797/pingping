package admin.ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import action.Action;
import admin.act.*;
import vo.*;

@WebServlet("*.orda")
public class AdminOrderCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public AdminOrderCtrl() {
        super();
    }
    
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestUri.substring(contextPath.length());
		System.out.println(requestUri);
		System.out.println(contextPath);
		System.out.println(command);
		ActionForward forward = null;
		
		Action action = null;
		
		// ������� ��û�� ���� ���� �ٸ� action�� �۾�
		switch (command) {
			case "/admin/order_list.orda" : 	// �ֹ�/���/��ȯ/ȯ�� ��� ȭ��
				action = new AdminOrderListAct();
				break;
			case "/admin/order_detail.orda" : 	// �ֹ� �󼼺���
				action = new AdminOrderDetailAct();
				break;
			case "/admin/order_detail_update.orda" : 	// �ֹ� �󼼺��� ������ �޸� ������Ʈ
				action = new AdminOrderDetailUpdateAct();
				break;
		}

		System.out.println("�ƾ�, ����� AdminOrderCtrl");
		
		try {
			forward = action.execute(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		} 
		
		if (forward != null) {
			if (forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			} else {
				//request.setAttribute(command, "command");
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
				// RequestDispatcher�� ���� �̵���Ű�� �̵��� �������� URL�� ������ �ʰ�,
				// �̵��� �������� ���� ������ �ִ� request�� response ��ü�� �״�� �Ѱ���
			}
		}
		
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}
