package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class FreeViewAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		int idx = Integer.parseInt(request.getParameter("idx"));
		int cpage = Integer.parseInt(request.getParameter("cpage"));
		String keyword	= request.getParameter("keyword");
		String ord = request.getParameter("ord");			// 정렬조건
		
		FreeViewSvc freeViewSvc = new FreeViewSvc();
		FreeInfo freeInfo = freeViewSvc.getFreeInfo(idx);
		// 특정 게시글의 데이터들을 NoticeInfo형 인스턴스 article에 저장
		
		FreePageInfo pageInfo = new FreePageInfo();	// 페이징에 필요한 정보들을 저장할 인스턴스
		pageInfo.setCpage(cpage);		// 현재 페이지 번호
		pageInfo.setKeyword(keyword);	// 검색어
		pageInfo.setOrd(ord);			// 정렬조건
				
		request.setAttribute("pageInfo", pageInfo);	    
		request.setAttribute("freeInfo", freeInfo);
		// 이동할 페이지의 request객체에 게시글 객체를 담아 넘겨줌(dispatch방식으로 이동하므로 request 사용가능)
		
		ActionForward forward = new ActionForward();
		forward.setPath("/free/free_view.jsp");
		// forward인스턴스의 redirect멤버변수의 값을 true로 지정하지 않았으므로 dispatch방식으로 이동함
		
		return forward;
	}
}
