package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import action.*;
import vo.*;

@WebServlet("*.service")
public class ServiceCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ServiceCtrl() {
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
		case "/service/qna_in_form.service" : 		// 1:1���� ��� ��
			action = new QnaInFormAct();
			break;
		}
		
		System.out.println("����� FreeCtrl");
		
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
