package admin.ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import action.Action;
import admin.act.*;
import vo.*;
 
@WebServlet("*.pdta")
public class AdminPdtCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public AdminPdtCtrl() {
        super();
    }
    
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("utf-8");

    	String requestUri = request.getRequestURI();
    	String contextPath = request.getContextPath();
    	String command = requestUri.substring(contextPath.length());

    	ActionForward forward = null;
    	Action action = null;

    	switch (command) {
    		case "/admin/pdt_list.pdta" :		// ��ǰ ��� ȭ��
				action = new AdminPdtListAct();
				break;
    		case "/admin/pdt_in_form.pdta" :	// ��ǰ ��� �� ȭ��
				action = new AdminPdtInAct();
				break;
    		case "/admin/pdt_up_form.pdta" :	// ��ǰ ���� �� ȭ��
				action = new AdminPdtUpAct();
				break;
    		case "/admin/pdt_proc.pdta" :		// ��ǰ ó��(���, ����) ���
				action = new AdminPdtProcAct();
				break;
    	}

    	try {
        	System.out.println("����� AdminPdtCtrl");
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
