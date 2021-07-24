package ctrl;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import svc.*;
import vo.*;

@WebServlet("*.chkFGS")
public class ChkFGSCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ChkFGSCtrl() {
        super();
    }
    
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestUri.substring(contextPath.length());
		System.out.println(command);
		
		String ciemail = request.getParameter("ciemail");
		String miemail = request.getParameter("miemail");
		String kind = request.getParameter("kind");
		String linkidx = request.getParameter("idx");
				
		// 사용자의 요청에 따라 각기 다른 작업을 수행
		switch (command) { 
		case "/dofollow.chkFGS" : 	// 내가 해당 회원을 팔로우 중인지 검사 후 팔로우
			try {
				ChkFGSSvc chkFGSSvc = new ChkFGSSvc();
				int result = chkFGSSvc.doFollow(ciemail, miemail);
				out.println(result);
			} catch(Exception e) {
				e.printStackTrace();
				out.println(0);
			}
			break;
		case "/unfollow.chkFGS" : 	// 내가 해당 회원을 팔로우 중인지 검사 후 팔로우 취소
			try {
				ChkFGSSvc chkFGSSvc = new ChkFGSSvc();
				int result = chkFGSSvc.unFollow(ciemail, miemail);
				out.println(result);
			} catch(Exception e) {
				e.printStackTrace();
				out.println(0);
			}
			break;
		case "/doGood.chkFGS" : 	// 내가 해당 게시글을 좋아요 중인지 검사 후 좋아요
			try {
				ChkFGSSvc chkFGSSvc = new ChkFGSSvc();
				int result = chkFGSSvc.doGood(kind, linkidx, miemail);
				out.println(result);
			} catch(Exception e) {
				e.printStackTrace();
				out.println(0);
			}
			break;
		case "/unGood.chkFGS" : 	// 내가 해당 게시글을 좋아요 중인지 검사 후 좋아요 취소
			try {
				ChkFGSSvc chkFGSSvc = new ChkFGSSvc();
				int result = chkFGSSvc.unGood(kind, linkidx, miemail);
				out.println(result);
			} catch(Exception e) {
				e.printStackTrace();
				out.println(0);
			}
			break;
		case "/doScrap.chkFGS" : 	// 내가 해당 게시글을 스크랩 중인지 검사 후 스크랩
			try {
				ChkFGSSvc chkFGSSvc = new ChkFGSSvc();
				int result = chkFGSSvc.doScrap(kind, linkidx, miemail);
				out.println(result);
			} catch(Exception e) {
				e.printStackTrace();
				out.println(0);
			}
			break;
		case "/unScrap.chkFGS" : 	// 내가 해당 게시글을 스크랩 중인지 검사 후 스크랩 취소
			try {
				ChkFGSSvc chkFGSSvc = new ChkFGSSvc();
				int result = chkFGSSvc.unScrap(kind, linkidx, miemail);
				out.println(result);
			} catch(Exception e) {
				e.printStackTrace();
				out.println(0);
			}
			break;
		}
		
		System.out.println("아아, 여기는 ChkFGSCtrl");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
}
