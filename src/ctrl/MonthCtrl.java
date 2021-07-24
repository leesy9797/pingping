package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import action.*;
import vo.*;

@WebServlet("*.month")
public class MonthCtrl extends HttpServlet {
// �̴�����õ ���� ��ɵ��� ����
	private static final long serialVersionUID = 1L;
    
    public MonthCtrl() {
        super();
    }
    
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// ������� ��û�� get�̵� post�� ��� ó���ϴ� �޼ҵ�
		request.setCharacterEncoding("utf-8");
		
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestUri.substring(contextPath.length());
		
		ActionForward forward = null;
		
		Action action = null;
		
		// ������� ��û�� ���� ���� �ٸ� action�� �۾�
		switch (command) {
			case "/monthly/month_list.month" : 	// �̴�����õ ��� ȭ��
				action = new MonthListAct();
				break;
			case "/monthly/month_view.month" : 	// �̴�����õ �� ȭ��
				action = new MonthViewAct();
				break;
		}

		System.out.println("����� MonthCtrl");
		
		try {
			forward = action.execute(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		} 
		
		if (forward != null) {
			if (forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			} else {
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
