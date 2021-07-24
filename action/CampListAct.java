package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class CampListAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<CampingInfo> campList = new ArrayList<CampingInfo>();
		ArrayList<FollowInfo> followList = new ArrayList<FollowInfo>();
		ArrayList<GoodInfo> goodList = new ArrayList<GoodInfo>();
		ArrayList<ScrapInfo> scrapList = new ArrayList<ScrapInfo>();
		
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
		if (request.getParameter("psize") != null) {
			psize = Integer.parseInt(request.getParameter("psize"));
		}	// 현재 페이지 번호가 있으면 정수형으로 형변환하여 저장
		
		// 검색조건(팔로잉) 쿼리스트링
		//String following = "";
		//following = request.getParameter("following");	// 팔로잉
		
		// 검색조건에 따른 where절 생성(아래가 기본조건)
		//String where = "";
		
		//if (!isEmpty(following))	where += " and a.pi_name like '%" + keyword + "%' ";
		//else	keyword = "";
		
		// 정렬조건(최신순(기본값_cr_idx) - 내림차순(d), 인기순(스크랩순_cr_scrap) - 내림차순(d)) 쿼리스트링
		String ord = request.getParameter("ord");
		if (ord == null || ord.equals("")) {
			ord = "idx";	// 등록 역순으로 정렬이 기본값
		}
		
		// 정렬조건에 따른 order by절 생성
		String orderBy = " order by a.cr_" + ord.substring(0, ord.length()) + " desc";	
		
		CampListSvc campListSvc = new CampListSvc();
		ChkFGSSvc chkFGSSvc = new ChkFGSSvc();
		rcnt = campListSvc.getCampCount();	// 검색된 상품의 총 개수(페이지 개수를 구하기 위해 필요)
		campList = campListSvc.getCampList(orderBy, cpage, psize);
		// 현 페이지(cpage)에서 보여줄 검색된 상품 목록을 받아옴
		followList = chkFGSSvc.getFollowList(loginEmail);
		String kind = "c";
		goodList = chkFGSSvc.getGoodList(kind, loginEmail);
		scrapList = chkFGSSvc.getScrapList(kind, loginEmail);
		// 로그인 중인 회원의 팔로우 목록을 받아옴
		
		pcnt = rcnt / psize;
		if (rcnt % psize > 0)	pcnt++;				// 전체 페이지 수
		spage = (cpage - 1) / bsize * bsize + 1;	// 블록 시작 페이지 번호
		epage = spage + bsize - 1;
		if (epage > pcnt)	epage = pcnt;			// 블록 종료 페이지 번호
		
		CampPageInfo pageInfo = new CampPageInfo();	// 페이징에 필요한 정보들을 저장할 인스턴스
		// 페이징에 필요한 정보들
		pageInfo.setCpage(cpage);		// 현재 페이지 번호
		pageInfo.setRcnt(rcnt);			// 전체 게시글 개수
		pageInfo.setPcnt(pcnt);			// 전체 페이지 개수
		pageInfo.setSpage(spage);		// 블록 시작 페이지 번호
		pageInfo.setEpage(epage);		// 블록 종료 페이지 번호 
		pageInfo.setPsize(psize);		// 페이지 크기
		pageInfo.setBsize(bsize);		// 블록 크기
		pageInfo.setOrd(ord);			// 정렬조건	
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("campList", campList);
		request.setAttribute("followList", followList);
		request.setAttribute("goodList", goodList);
		request.setAttribute("scrapList", scrapList);
		System.out.println("아아, 여기는 CampListAct");
		
		ActionForward forward = new ActionForward();
		forward.setPath("/camping/camp_list.jsp");
		// forward 인스턴스의 setPath() 메소드를 이용하여 forward 인스턴스의 path 멤버변수의 값을 지정
		
		return forward;
	}
	
	private boolean isEmpty(String str) {
	// 매개변수에 어떤 값이든 들어있는지 여부를 검사하는 메소드 -> 검색 안해서 안씀
		boolean empty = true;
		
		if (str != null && !str.equals("")) {
			empty = false;
		}
		
		return empty;
	}
}
