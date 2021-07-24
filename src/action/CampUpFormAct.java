package action;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import svc.*;
import vo.*;

public class CampUpFormAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		CampingInfo campingInfo = new CampingInfo();	// 수정할 질문 정보를 저장할 인스턴스
		
		System.out.println("여기는 CampUpFormAct");
		
		request.setCharacterEncoding("utf-8");
		int idx = Integer.parseInt(request.getParameter("idx"));	
		int cpage = Integer.parseInt(request.getParameter("cpage"));
		int psize	= Integer.parseInt(request.getParameter("psize"));	
		String ord 		= request.getParameter("ord");		// 정렬조건
		// 검색 조건에 따른 where절, order by절 생성 필요없음
		
		CampPageInfo pageInfo = new CampPageInfo();
		pageInfo.setCpage(cpage);		// 현재 페이지 번호
		pageInfo.setPsize(psize);	// 검색어
		pageInfo.setOrd(ord);			// 정렬조건
		
		CampUpFormSvc campUpFormSvc = new CampUpFormSvc();
		campingInfo = campUpFormSvc.getCampInfo(idx);
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("campingInfo", campingInfo);

		System.out.println("여기는 CampUpFormAct");
		
		ActionForward forward = new ActionForward();
		//forward.setRedirect(true);	// dispatch가 아닌 sendRedirect 방식으로 이동
		forward.setPath("camp_up_form.jsp");	// request를 써야 하므로 dispatch방식으로

		return forward;
	}
		
}