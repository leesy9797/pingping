package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import action.*;
import vo.*;

@WebServlet("*.review")
public class ReviewCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ReviewCtrl() {
        super();
    }
    
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // ������� ��û�� get�̵� post�� ��� ó���ϴ� �޼ҵ�
		request.setCharacterEncoding("utf-8");
		
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestUri.substring(contextPath.length());
		System.out.println(command);
		
		ActionForward forward = null;
		// ó�� �� �̵��� view�� �ش��ϴ� ��ũ�� �̵������ ��� ���� �ν��Ͻ�
		
		Action action = null;
		
		// ������� ��û�� ���� ���� �ٸ� action�� �۾�
		switch (command) {
		case "/review/review_list.review" : 	// ���� �� ���� ���
			action = new ReviewListAct();
			break;
		case "/review/review_write_list.review" :	// ���� ������ ��ǰ ���
			action = new ReviewWriteListAct();
			break;
		case "/review/review_in_form.review" : 	// ���� ��� ��
			action = new ReviewInAct();
			break;
		case "/review/review_up_form.review" : 	// ���� ���� ��
			action = new ReviewUpAct();
			break;
		case "/review/review_proc.review" : 	// ���� ���,���� ó��
			action = new ReviewProcAct();
			break;
		}
		
		System.out.println("�ƾ�, ����� ReviewCtrl");
		
		try {
			forward = action.execute(request, response);
			// �� ��û�� ���� Action�� implements�� Ŭ������ execute() �޼ҵ带 ����
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
