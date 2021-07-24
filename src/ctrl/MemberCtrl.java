package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import action.*;
import vo.*;

@WebServlet("*.mem")
public class MemberCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public MemberCtrl() {
        super();
    }
    
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("utf-8");
		
		// 해당 요청을 구분하기 위해 url을 잘라냄
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestUri.substring(contextPath.length());
		//System.out.println(command);
		ActionForward forward = null;
		
		Action action = null;
		
		// 사용자의 요청에 따라 각기 다른 action을 작업
		switch (command) {
			case "/member/search_id.mem" : 	// 아이디 찾기
				action = new SearchIdAct();
				break;
			case "/member/chk_email.mem" : 	// 비밀번호 재설정(아이디 존재여부 확인)
				action = new ChkEmailAct();
				break;
			case "/member/reset_pwd.mem" : 	// 비밀번호 재설정(본인인증)
				action = new ResetPwdAct();
				break;
			case "/member/set_pwd.mem" : 	// 비밀번호 재설정(재설정)
				action = new SetPwdAct();
				break;
			case "/member/addr_list.mem" : 		// 주소록 목록
				action = new AddrListAct();
				break;
			case "/member/addr_in_form.mem" : 	// 주소록 등록폼
				action = new AddrInFormAct();
				break;
			case "/member/addr_up_form.mem" : 	// 주소록 수정폼
				action = new AddrUpFormAct();
				break;
			case "/member/addr_del.mem" : 		// 주소록 삭제
				action = new AddrDelAct();
				break;
			case "/member/addr_in_proc.mem" : 	// 주소록 등록 처리
				action = new AddrInProcAct();
				break;
			case "/member/addr_up_proc.mem" : 	// 주소록 수정처리
				action = new AddrUpProcAct();
				break;
			case "/member/point_list.mem" :   		// 포인트 내역 목록
			    action = new PointListAct();		
			    break;
			case "/member/follower_list.mem" :    	// 팔로워 목록
				action = new FollowerListAct();
				break;
			case "/member/following_list.mem" : 	// 팔로잉 목록
				action = new FollowingListAct();
				break;
			case "/member/mypage.mem" : 	// 팔로잉 목록
				//action = new FollowingListAct();
				break;

		}

		System.out.println("아아, 여기는 MemberCtrl");
		
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
