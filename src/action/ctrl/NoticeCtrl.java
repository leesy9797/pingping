package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import action.*;
import vo.*;

@WebServlet("*.ntc")
public class NoticeCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public NoticeCtrl() {
        super();
  
    }
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("utf-8");
    	String requestUri = request.getRequestURI();
    	String contextPath = request.getContextPath();
    	String command = requestUri.substring(contextPath.length());
    	System.out.println("command = " +command);
    	
    	ActionForward forward = null;
    	// ó���� �̵� �� view�� �ش��ϴ� ��ũ�� �̵������ ��� ���� �ν��Ͻ�
    	Action action = null;
    	// ��ɺ��� ó���� �� ������ �޼ҵ�� �۾��ϱ� ���� ����� �ν��Ͻ�
    	// �������̽��̹Ƿ� �ν��Ͻ��� ������ ���� ����
    	
    	// ������� ��û�� ���� ���� �ٸ� action�� �۾���
    	switch (command) {
    		case "/brd_list.ntc" : // �������� ��� ��û
    			action = new NoticeListAction();
    			// Action�� implements�ϴ� NoticeListActionŬ������ �̿��Ͽ� �ν��Ͻ� ����
    			break;
    		case "/brd_form.ntc" : // �������� �� ��û
    			action = new NoticeListAction();
    			break;
    		case "/brd_view.ntc" : // �������� ���� ��û
    			action = new NoticeViewAction();
    			break;
    		case "/brd_proc.ntc" : // �������� ó�� ��û
    			action = new NoticeListAction();
    			break;
    	}
    	try {
    		forward = action.execute(request, response);
    		// �� ��û�� ���� Action�� implements�� Ŭ������ execute()�޼ҵ带 ����
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
    			// �̵��� �������� ���� ������ �ִ� request�� response��ü�� �״�� �Ѱ���
    			
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
