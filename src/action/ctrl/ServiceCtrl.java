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
	// 사용자의 요청이 get이든 post든 모두 처리하는 메소드
		request.setCharacterEncoding("utf-8");
		
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestUri.substring(contextPath.length());
		System.out.println(command);
		
		ActionForward forward = null;
		// 처리 후 이동할 view에 해당하는 링크와 이동방식을 얻기 위한 인스턴스
		
		Action action = null;
		
		// 사용자의 요청에 따라 각기 다른 action을 작업
		switch (command) { 
		case "/service/qna_in_form.service" : 		// 1:1문의 등록 폼
			action = new QnaInFormAct();
			break;
		}
		
		System.out.println("여기는 FreeCtrl");
		
		try {
			forward = action.execute(request, response);
			// 각 요청에 따른 Action을 implements한 클래스의 execute() 메소드를 실행
		} catch(Exception e) {
			e.printStackTrace();
		} 
		
		if (forward != null) {
			if (forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			} else {
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
