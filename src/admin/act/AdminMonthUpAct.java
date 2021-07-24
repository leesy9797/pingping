package admin.act;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import svc.FreeUpSvc;
import vo.*;

public class AdminMonthUpAct implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		MonthlyInfo monthInfo = new MonthlyInfo();	// 수정할 질문 정보를 저장할 인스턴스
		
		request.setCharacterEncoding("utf-8");
		int cpage = Integer.parseInt(request.getParameter("cpage"));
		int idx = Integer.parseInt(request.getParameter("idx"));	// 수정할 질문 idx
		
		// 검색조건(검색어, 등록기간, 추천 월, 게시여부) 쿼리스트링
		String schtype	= request.getParameter("schtype");	// 검색조건(글제목, 캠핑장명)
		String keyword	= request.getParameter("keyword");	// 검색어
		String sdate	= request.getParameter("sdate");	// 등록기간 중 시작일
		String edate	= request.getParameter("edate");	// 등록기간 중 종료일
		String month	= request.getParameter("month");	// 추천 월
		String isview	= request.getParameter("isview");	// 게시여부(y, n)
		String ord 		= request.getParameter("ord");		// 정렬조건
		// 검색 조건에 따른 where절, order by절 생성 필요없음
		
		MonthlyPageInfo pageInfo = new MonthlyPageInfo();
		pageInfo.setCpage(cpage);		// 현재 페이지 번호
		pageInfo.setSchtype(schtype);	// 검색조건(아이디 or 상품명)
		pageInfo.setKeyword(keyword);	// 검색어
		pageInfo.setSdate(sdate);		// 등록기간 중 시작일
		pageInfo.setEdate(edate);		// 등록기간 중 종료일
		pageInfo.setMonth(month);		// 추천 월
		pageInfo.setIsview(isview);		// 게시여부
		pageInfo.setOrd(ord);			// 정렬조건
		
		AdminMonthUpSvc adminMonthUpSvc = new AdminMonthUpSvc();
		monthInfo = adminMonthUpSvc.getMonthInfo(idx);
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("monthInfo", monthInfo);

		ActionForward forward = new ActionForward();
		forward.setPath("/admin/monthly/month_up_form.jsp");	// request를 써야 하므로 dispatch방식으로

		System.out.println("여기는 AdminMonthUpAct");
		return forward;
	}
}
