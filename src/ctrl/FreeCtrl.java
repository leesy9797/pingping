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
		case "/free/free_list.free" : 		// 질문과답변 목록
			action = new FreeListAct();
			break;
		case "/free/free_view.free" : 		// 질문과답변 상세 보기
			action = new FreeViewAct();
			break;
		case "/free/free_in_form.free" : 	// 질문과답변 등록 폼
			action = new FreeInAct();
			break;
		case "/free/free_up_form.free" : 	// 질문과답변 수정 폼
			action = new FreeUpAct();
			break;
		case "/free/free_proc.free" : 		// 질문과답변 등록/수정/삭제 처리
			action = new FreeProcAct();
			break;
		case "/free/free_reply_proc.free" : 		// 질문과답변 댓글 등록/수정/삭제 처리
			action = new FreeReplyProcAct();
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
