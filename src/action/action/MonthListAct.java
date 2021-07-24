package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class MonthListAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<MonthlyInfo> monthList = new ArrayList<MonthlyInfo>();
		ArrayList<GoodInfo> goodList = new ArrayList<GoodInfo>();

		HttpSession session = request.getSession();
		MemberInfo memberInfo = (MemberInfo)session.getAttribute("memberInfo");
		String loginEmail = "";
		if (memberInfo != null) {
			loginEmail = memberInfo.getMi_email();
			System.out.println(loginEmail);
		}
		
		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 12, bsize = 10, spage, epage, rcnt, pcnt;
		// 현재 페이지 번호, 한 페이지에서 보여줄 게시글 개수, 한 블록에서 보여줄 페이지 개수, 블록별 시작/종료 페이지 번호, 레코드 개수, 페이지 개수를 저장할 변수 초기화
		
		if (request.getParameter("cpage") != null) {
			cpage = Integer.parseInt(request.getParameter("cpage"));
		}	// 현재 페이지 번호가 있으면 정수형으로 형변환하여 저장
		String where = "", tmpWhere = "";
		
		// 검색조건(캠핑장이름, 캠핑장위치, 추천 월, 캠핑형태) 쿼리스트링
		String name		= request.getParameter("name");		// 캠핑장 이름
		String location	= request.getParameter("location");	// 캠핑장 위치
		String month = "", info = "";
		
		// 검색 조건에 따른 where절 생성
		if (!isEmpty(name))		where += " and mr_name like '%" + name + "%' ";
		else { name = ""; }
		if (!isEmpty(location))	where += " and mr_location like '%" + location + "%' ";
		else { location = ""; }
		
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
		
		if (request.getParameterValues("info") != null) {	// 캠핑형태
			String[] arrInfo = request.getParameterValues("info");	
			// 체크되어 있는 체크박스의 value 값들을 문자열 배열로 받아옴
			tmpWhere = "";
			for (int i = 0 ; i < arrInfo.length ; i++) {
				tmpWhere += " or mr_info like '%" + arrInfo[i] + "%' ";
				info += "/" + arrInfo[i];
			}
			info = info.substring(1);
			//System.out.println(info);
			where += " and (" + tmpWhere.substring(4) + ") ";
			//System.out.println(where);
		}
		
		// 정렬조건(최신순(기본값) - 등록내림차순, 인기순(좋아요순) - 내림차순) 쿼리스트링
		String ord = request.getParameter("ord");
		if (ord == null || ord.equals("")) {
			ord = "lastdated";	// 등록 역순으로 정렬이 기본값
		}
		// 정렬조건에 따른 order by절 생성
		String orderBy = "";
		if (ord.equals("lastdated"))	orderBy = " order by mr_idx desc";
		else							orderBy = " order by mr_good desc, mr_read desc";
		
		MonthListSvc monthListSvc = new MonthListSvc();
		ChkFGSSvc chkFGSSvc = new ChkFGSSvc();
		rcnt = monthListSvc.getMonthCount(where);	// 검색된 상품의 총 개수(페이지 개수를 구하기 위해 필요)
		monthList = monthListSvc.getMonthList(where, orderBy, cpage, psize);
		// 현 페이지(cpage)에서 보여줄 검색된 게시글 목록을 받아옴
		String kind = "m";
		goodList = chkFGSSvc.getGoodList(kind, loginEmail);
		
		pcnt = rcnt / psize;
		if (rcnt % psize > 0)	pcnt++;		// 전체 페이지 수
		spage = (cpage - 1) / bsize * bsize + 1;	// 블록 시작 페이지 번호
		epage = spage + bsize - 1;
		if (epage > pcnt)	epage = pcnt;			// 블록 종료 페이지 번호
		
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
		pageInfo.setName(name);			// 캠핑장 이름
		pageInfo.setLocation(location);	// 캠핑장 위치
		pageInfo.setMonth(month);		// 추천 월
		pageInfo.setInfo(info);			// 캠핑형태
		
		pageInfo.setOrd(ord);			// 정렬조건
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("monthList", monthList);	
		request.setAttribute("goodList", goodList);	
		
		ActionForward forward = new ActionForward();
		forward.setPath("/monthly/month_list.jsp");
		// forward 인스턴스의 setPath() 메소드를 이용하여 forward 인스턴스의 path 멤버변수의 값을 지정
		
		System.out.println("여기는 MonthListAct");
		return forward;
	}
	
	private boolean isEmpty(String str) {	// 매개변수에 어떤 값이든 들어있는지 여부를 검사하는 메소드
		boolean empty = true;
		if (str != null && !str.equals(""))	empty = false;
		return empty;
	}
}
