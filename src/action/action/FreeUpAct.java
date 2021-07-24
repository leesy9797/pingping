package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class FreeUpAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		FreeInfo freeInfo = new FreeInfo();	// 수정할 질문 정보를 저장할 인스턴스
		
		request.setCharacterEncoding("utf-8");
		int cpage = Integer.parseInt(request.getParameter("cpage"));
		int idx = Integer.parseInt(request.getParameter("idx"));	// 수정할 질문 idx
		
		// 검색조건(검색어, 대/소분류, 브랜드, 가격대) 쿼리스트링
		String keyword	= request.getParameter("keyword");	// 검색어
		String ord 		= request.getParameter("ord");		// 정렬조건
		// 검색 조건에 따른 where절, order by절 생성 필요없음
		
		FreePageInfo pageInfo = new FreePageInfo();
		pageInfo.setCpage(cpage);		// 현재 페이지 번호
		pageInfo.setKeyword(keyword);	// 검색어
		pageInfo.setOrd(ord);			// 정렬조건
		
		FreeUpSvc freeUpSvc = new FreeUpSvc();
		freeInfo = freeUpSvc.getFreeInfo(idx);
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("freeInfo", freeInfo);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/free/free_up_form.jsp");	// request를 써야 하므로 dispatch방식으로
		
		System.out.println("여기는 FreeUpAct");
		return forward;
	}
}
