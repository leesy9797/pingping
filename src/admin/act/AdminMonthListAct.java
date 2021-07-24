package admin.act;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import vo.*;

public class AdminMonthListAct implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<MonthlyInfo> monthList = new ArrayList<MonthlyInfo>();
		
		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 15, bsize = 10, spage, epage, rcnt, pcnt;
		if (request.getParameter("cpage") != null)	// cpage가 null이 아닐 경우에만 받아옴
			cpage = Integer.parseInt(request.getParameter("cpage"));
		
		// 검색조건(검색어, 대/소분류, 브랜드, 가격대) 쿼리스트링
		String isview	= request.getParameter("isview");	// 게시여부(y, n)
		String schtype	= request.getParameter("schtype");	// 검색조건(게시글제목, 캠핑장이름)
		String keyword	= request.getParameter("keyword");	// 검색어
		String sdate	= request.getParameter("sdate");	// 등록기간 중 시작일
		String edate	= request.getParameter("edate");	// 등록기간 중 종료일
		String month = "";
		
		// 검색 조건에 따른 where절 생성
		String where = " where 1=1 ", tmpWhere = "";
		if (!isEmpty(isview))	where += " and mr_isview = '" + isview + "' ";
		else	isview = "";
		if (!isEmpty(keyword))	where += " and mr_" + schtype + " like '%" + keyword + "%' ";
		else { keyword = "";	schtype = ""; }
		if (!isEmpty(sdate))	where += " and mr_date >= '" + sdate + " 00:00:00' ";
		else	sdate = "";
		if (!isEmpty(edate))	where += " and mr_date <= '" + edate + " 23:59:59' ";
		else	edate = "";
		
		// 체크박스로 넘어온 값이 있으면 배열로 받아오고 동시에 where절 생성, 구분자로 이어서 pageInfo에 저장
		if (request.getParameterValues("month") != null) {	// 추천 월
			String[] arrMonth = request.getParameterValues("month");	
			// 체크되어 있는 체크박스의 value 값들을 문자열 배열로 받아옴
			tmpWhere = "";
			for (int i = 0 ; i < arrMonth.length ; i++) {
				tmpWhere += " or mr_month like '%" + arrMonth[i] + "%' ";
				month += "/" + arrMonth[i];
			}
			month = month.substring(1);
			//System.out.println(month);
			where += " and (" + tmpWhere.substring(4) + ") ";
			//System.out.println(where);
		}
		
		// 정렬조건(판매량-내, 가격-오/내, 등록-내(기본), 상품명-오, 리뷰-내) 쿼리스트링
		String ord = request.getParameter("ord");
		if (ord == null || ord.equals(""))		ord = "lastdated";	// 등록 역순으로 정렬이 기본값
		String orderBy = "";
		if (ord.equals("lastdated")) orderBy = " order by last_date" + 
			(ord.charAt(ord.length() - 1) == 'a' ? " asc" : " desc");
		else	orderBy = " order by mr_" + ord.substring(0, ord.length() - 1) + 
				(ord.charAt(ord.length() - 1) == 'a' ? " asc" : " desc");
		// 정렬 조건에 따른 order by절 생성
		
		AdminMonthListSvc adminMonthListSvc = new AdminMonthListSvc();
		rcnt = adminMonthListSvc.getMonthCount(where);	// 검색된 상품의 총 개수(페이지 개수를 구하기 위해 필요)
		monthList = adminMonthListSvc.getMonthList(where, orderBy, cpage, psize);
		// 현 페이지(cpage)에서 보여줄 검색된 상품 목록을 받아옴
		
		pcnt = rcnt / psize;
		if (rcnt % psize > 0)	pcnt++;				// 전체 페이지 수
		spage = (cpage - 1) / bsize * bsize + 1;	// 블록의 시작 페이지 번호
		epage = spage + bsize - 1;
		if (epage > pcnt)		epage = pcnt;		// 블록의 종료 페이지 번호
		
		MonthlyPageInfo pageInfo = new MonthlyPageInfo();	// 페이징에 필요한 정보들을 저장할 인스턴스
		// 페이징에 필요한 정보들
		pageInfo.setCpage(cpage);		// 현재 페이지 번호
		pageInfo.setRcnt(rcnt);			// 전체 게시글 개수
		pageInfo.setPcnt(pcnt);			// 전체 페이지 개수
		pageInfo.setSpage(spage);		// 블록 시작 페이지 번호
		pageInfo.setEpage(epage);		// 블록 종료 페이지 번호
		pageInfo.setPsize(psize);		// 페이지 크기
		pageInfo.setBsize(bsize);		// 블록 크기
		
		// 검색 관련 정보들
		pageInfo.setSchtype(schtype);	// 검색조건(게시글제목, 캠핑장이름)
		pageInfo.setKeyword(keyword);	// 검색어
		pageInfo.setIsview(isview);		// 게시여부
		pageInfo.setSdate(sdate);		// 등록기간 중 시작일
		pageInfo.setEdate(edate);		// 등록기간 중 종료일
		pageInfo.setMonth(month);		// 추천 월

		pageInfo.setOrd(ord);			// 정렬조건
				
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("monthList", monthList);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/admin/monthly/month_list.jsp");

		System.out.println("여기는 AdminMonthListAct");
		return forward;
	}

	private boolean isEmpty(String str) {	// 매개변수에 어떤 값이든 들어있는지 여부를 검사하는 메소드
		boolean empty = true;
		if (str != null && !str.equals(""))	empty = false;
		return empty;
	}
}
