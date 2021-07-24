package admin.ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import action.Action;
import admin.act.*;
import vo.*;
 
@WebServlet("*.montha")
public class AdminMonthCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public AdminMonthCtrl() {
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
    		case "/admin/month_list.montha" :		// 이달의추천 목록 화면
				action = new AdminMonthListAct();
				break;
    		case "/admin/month_in_form.montha" :	// 이달의추천 등록 폼 화면
				action = new AdminMonthInAct();
				break;
    		case "/admin/month_up_form.montha" :	// 이달의추천 수정 폼 화면
				action = new AdminMonthUpAct();
				break;
    		case "/admin/month_proc.montha" :		// 이달의추천 처리(등록, 수정(삭제)) 기능
				action = new AdminMonthProcAct();
				break;
    	}

    	try {
        	System.out.println("여기는 AdminMonthCtrl");
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
