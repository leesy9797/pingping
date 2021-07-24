package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import action.*;
import vo.*;

@WebServlet("*.camp")
public class CampingCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public CampingCtrl() {
        super();
    }
    
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// ������� ��û�� get�̵� post�� ��� ó���ϴ� �޼ҵ�
		request.setCharacterEncoding("utf-8");
		
		String requestUri = request.getRequestURI();
		// URI(�����ΰ� ������Ʈ���� �� �ּ� ���ڿ�) 
		String contextPath = request.getContextPath();
		// URI���� ���ϸ� �κ��� ������ ���ڿ� 
		String command = requestUri.substring(contextPath.length());
		// requetUri���� contextPath�� �� ���ڿ� 

		System.out.println(command);
		
		ActionForward forward = null;
		// ó�� �� �̵��� view�� �ش��ϴ� ��ũ�� �̵������ ��� ���� �ν��Ͻ�
		
		Action action = null;
		
		// ������� ��û�� ���� ���� �ٸ� action�� �۾�
		switch (command) { 
		case "/camping/camp_list.camp" : 		// ķ���ı� ���
			action = new CampListAct();
			break;
		case "/camping/camp_view.camp" : 		// ķ���ı� �� ����
			action = new CampViewAct();
			break;
		case "/camping/camp_in_form.camp" : 	// ķ���ı� ��� ��
			action = new CampInFormAct();
			break;
		case "/camping/camp_up_form.camp" : 	// ķ���ı� ���� ��
			action = new CampUpFormAct();
			break;
		case "/camping/camp_proc.camp" : 		// ķ���ı� ���/����/���� ó��
			action = new CampProcAct();
			break;
		case "/camping/search_product.camp" : 	// ��ǰ �˻� �� 
			action = new SearchProductAct();
			break;
		case "/camping/camp_reply_proc.camp" : 	// ��� ���, ����, ����
			action = new CampReplyProcAct();
			break;
		}
		
		System.out.println("�ƾ�, ����� CampingCtrl");
		
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
