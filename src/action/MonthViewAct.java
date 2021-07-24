package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class MonthViewAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		int idx = Integer.parseInt(request.getParameter("idx"));
		int cpage = Integer.parseInt(request.getParameter("cpage"));
		
		String ord = request.getParameter("ord");			// 정렬조건
		
		ArrayList<MonthReplyInfo> replyList = (ArrayList<MonthReplyInfo>)request.getAttribute("replyList");
		
		MonthViewSvc monthViewSvc = new MonthViewSvc();
		MonthlyInfo monthInfo = monthViewSvc.getMonthInfo(idx);
		// 특정 게시글의 데이터들을 NoticeInfo형 인스턴스 article에 저장
		
		int rcnt = monthViewSvc.getMonthReplyCount();	
		replyList = monthViewSvc.getMonthReplyList(idx);
	    
		request.setAttribute("monthInfo", monthInfo);
		request.setAttribute("replyList", replyList);
		// 이동할 페이지의 request객체에 게시글 객체를 담아 넘겨줌(dispatch방식으로 이동하므로 request 사용가능)
		
		System.out.println("아아, 여기는 MonthViewAct");
		
		ActionForward forward = new ActionForward();
		forward.setPath("/monthly/month_view.jsp");
		// forward인스턴스의 redirect멤버변수의 값을 true로 지정하지 않았으므로 dispatch방식으로 이동함
		
		return forward;
	}
}
