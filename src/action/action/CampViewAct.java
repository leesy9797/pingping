package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class CampViewAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		int idx = Integer.parseInt(request.getParameter("idx"));	// 캠핑후기 idx
		int cpage = Integer.parseInt(request.getParameter("cpage"));	// 현재 페이지 번호
		int psize = Integer.parseInt(request.getParameter("psize"));	// 페이지 크기 & 목록보기 방식
		
		// 검색조건(검색어, 대/소분류, 브랜드, 가격대) 및 정렬조건 쿼리스트링
		// 목록으로 돌아갈 때 필요함
		String ord 	= request.getParameter("ord");		// 정렬조건
		
		ArrayList<CampReplyInfo> replyList = (ArrayList<CampReplyInfo>)request.getAttribute("replyList");

		CampPageInfo pageInfo = new CampPageInfo();	// 페이징에 필요한 정보들을 저장할 인스턴스
		// 페이징에 필요한 정보들
		pageInfo.setCpage(cpage);		// 현재 페이지 번호
		pageInfo.setPsize(psize);		// 페이지 크기
		pageInfo.setOrd(ord);			// 정렬조건	
		
		CampViewSvc campViewSvc = new CampViewSvc();
		CampingInfo campInfo = campViewSvc.getCampInfo(idx);

		int rcnt = campViewSvc.getCampReplyCount();	
		replyList = campViewSvc.getCampReplyList(idx);
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("campInfo", campInfo); 
		request.setAttribute("replyList", replyList);

		System.out.println("아아, 여기는 CampViewAct");

		ActionForward forward = new ActionForward();
		forward.setPath("/camping/camp_view.jsp");
		
		return forward;
	}

}
