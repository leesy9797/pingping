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
		
		// 사용자의 요청에 따라 각기 다른 action을 작업
		switch (command) {
			case "/admin/order_list.orda" : 	// 주문/취소/교환/환불 목록 화면
				action = new AdminOrderListAct();
				break;
			case "/admin/order_detail.orda" : 	// 주문 상세보기
				action = new AdminOrderDetailAct();
				break;
			case "/admin/order_detail_update.orda" : 	// 주문 상세보기 관리자 메모 업데이트
				action = new AdminOrderDetailUpdateAct();
				break;
		}

		System.out.println("아아, 여기는 AdminOrderCtrl");
		
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
				// RequestDispatcher를 통해 이동시키면 이동한 페이지의 URL이 변하지 않고,
				// 이동한 페이지로 현재 가지고 있는 request와 response 객체를 그대로 넘겨줌
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
