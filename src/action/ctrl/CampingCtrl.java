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
	// 사용자의 요청이 get이든 post든 모두 처리하는 메소드
		request.setCharacterEncoding("utf-8");
		
		String requestUri = request.getRequestURI();
		// URI(도메인과 쿼리스트링을 뺀 주소 문자열) 
		String contextPath = request.getContextPath();
		// URI에서 파일명 부분을 제외한 문자열 
		String command = requestUri.substring(contextPath.length());
		// requetUri에서 contextPath를 뺀 문자열 

		System.out.println(command);
		
		ActionForward forward = null;
		// 처리 후 이동할 view에 해당하는 링크와 이동방식을 얻기 위한 인스턴스
		
		Action action = null;
		
		// 사용자의 요청에 따라 각기 다른 action을 작업
		switch (command) { 
		case "/camping/camp_list.camp" : 		// 캠핑후기 목록
			action = new CampListAct();
			break;
		case "/camping/camp_view.camp" : 		// 캠핑후기 상세 보기
			action = new CampViewAct();
			break;
		case "/camping/camp_in_form.camp" : 	// 캠핑후기 등록 폼
			action = new CampInFormAct();
			break;
		case "/camping/camp_up_form.camp" : 	// 캠핑후기 수정 폼
			action = new CampUpFormAct();
			break;
		case "/camping/camp_proc.camp" : 		// 캠핑후기 등록/수정/삭제 처리
			action = new CampProcAct();
			break;
		case "/camping/search_product.camp" : 	// 상품 검색 폼 
			action = new SearchProductAct();
			break;
		case "/camping/camp_reply_proc.camp" : 	// 댓글 등록, 수정, 삭제
			action = new CampReplyProcAct();
			break;
		}
		
		System.out.println("아아, 여기는 CampingCtrl");
		
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
