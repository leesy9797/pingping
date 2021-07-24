package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class ReviewUpAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ReviewInfo reviewInfo = new ReviewInfo();	// 수정할 리뷰 정보를 저장할 인스턴스
		
		request.setCharacterEncoding("utf-8");
		int cpage = Integer.parseInt(request.getParameter("cpage"));
		int idx = Integer.parseInt(request.getParameter("idx"));	// 수정할 리뷰 idx
		
		String ord 		= request.getParameter("ord");		// 정렬조건
		// 검색 조건에 따른 where절, order by절 생성 필요없음
		
		ReviewPageInfo pageInfo = new ReviewPageInfo();
		pageInfo.setCpage(cpage);		// 현재 페이지 번호
		pageInfo.setOrd(ord);			// 정렬조건
		
		ReviewUpSvc reviewUpSvc = new ReviewUpSvc();
		reviewInfo = reviewUpSvc.getReviewInfo(idx);
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("reviewInfo", reviewInfo);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/review/review_up_form.jsp");	// request를 써야 하므로 dispatch방식으로
		
		System.out.println("여기는 ReviewUpAct");
		return forward;
	}
}
