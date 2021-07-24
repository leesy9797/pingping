package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class ReviewListAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<ReviewInfo> reviewList = new ArrayList<ReviewInfo>();

		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 10, bsize = 10, spage, epage, rcnt, pcnt;
		// 현재 페이지 번호, 한 페이지에서 보여줄 게시글 개수, 한 블록에서 보여줄 페이지 개수, 블록별 시작/종료 페이지 번호, 레코드 개수, 페이지 개수를 저장할 변수 초기화
		
		if (request.getParameter("cpage") != null) {
			cpage = Integer.parseInt(request.getParameter("cpage"));
		}	// 현재 페이지 번호가 있으면 정수형으로 형변환하여 저장
		
		// 정렬조건(최신순(기본값) - 내림차순, 별점순 - 내림차순) 쿼리스트링
		String ord = request.getParameter("ord");
		if (ord == null || ord.equals("")) {
			ord = "lastdated";	// 등록 역순으로 정렬이 기본값
		}

		// 정렬조건에 따른 order by절 생성
		String orderBy = "";
		if (ord.equals("lastdated"))	orderBy = " order by pr_date desc";
		else							orderBy = " order by pr_star desc";

		ReviewListSvc reviewListSvc = new ReviewListSvc();
		rcnt = reviewListSvc.getReviewCount();	// 검색된 리뷰의 총 개수(페이지 개수를 구하기 위해 필요)
		reviewList = reviewListSvc.getReviewList(orderBy, cpage, psize);
		// 현 페이지(cpage)에서 보여줄 검색된 상품 목록을 받아옴
		
		pcnt = rcnt / psize;
		if (rcnt % psize > 0)	pcnt++;		// 전체 페이지 수
		spage = (cpage - 1) / bsize * bsize + 1;	// 블록 시작 페이지 번호
		epage = spage + bsize - 1;
		if (epage > pcnt)	epage = pcnt;			// 블록 종료 페이지 번호
		
		ReviewPageInfo pageInfo = new ReviewPageInfo();	// 페이징에 필요한 정보들을 저장할 인스턴스
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
		request.setAttribute("reviewList", reviewList);		
		
		ActionForward forward = new ActionForward();
		forward.setPath("/review/review_list.jsp");
		// forward 인스턴스의 setPath() 메소드를 이용하여 forward 인스턴스의 path 멤버변수의 값을 지정

		System.out.println("여기는 ReviewListAct");
		return forward;
	}
}
