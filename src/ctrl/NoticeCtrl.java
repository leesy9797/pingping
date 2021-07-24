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
    	// 처리후 이동 할 view에 해당하는 링크와 이동방식을 얻기 위한 인스턴스
    	Action action = null;
    	// 기능별로 처리할 때 동일한 메소드로 작업하기 위해 선언된 인스턴스
    	// 인터페이스이므로 인스턴스를 생성할 수는 없음
    	
    	// 사용자의 요청에 따라 각기 다른 action을 작업함
    	switch (command) {
    		case "/brd_list.ntc" : // 공지사항 목록 요청
    			action = new NoticeListAction();
    			// Action을 implements하는 NoticeListAction클래스를 이용하여 인스턴스 생성
    			break;
    		case "/brd_form.ntc" : // 공지사항 폼 요청
    			action = new NoticeListAction();
    			break;
    		case "/brd_view.ntc" : // 공지사항 보기 요청
    			action = new NoticeViewAction();
    			break;
    		case "/brd_proc.ntc" : // 공지사항 처리 요청
    			action = new NoticeListAction();
    			break;
    	}
    	try {
    		forward = action.execute(request, response);
    		// 각 요청에 따른 Action을 implements한 클래스의 execute()메소드를 실행
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
    			// 이동한 페이지로 현재 가지고 있는 request와 response객체를 그대로 넘겨줌
    			
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
