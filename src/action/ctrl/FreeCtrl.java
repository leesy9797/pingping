package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import action.*;
import vo.*;

@WebServlet("*.free")
public class FreeCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public FreeCtrl() {
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
		case "/free/free_list.free" : 		// �������亯 ���
			action = new FreeListAct();
			break;
		case "/free/free_view.free" : 		// �������亯 �� ����
			action = new FreeViewAct();
			break;
		case "/free/free_in_form.free" : 	// �������亯 ��� ��
			action = new FreeInAct();
			break;
		case "/free/free_up_form.free" : 	// �������亯 ���� ��
			action = new FreeUpAct();
			break;
		case "/free/free_proc.free" : 		// �������亯 ���/����/���� ó��
			action = new FreeProcAct();
			break;
		case "/free/free_reply_proc.free" : 		// �������亯 ��� ���/����/���� ó��
			action = new FreeReplyProcAct();
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
