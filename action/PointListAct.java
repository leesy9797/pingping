package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class PointListAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<PointInfo> pointList = new ArrayList<PointInfo>();

		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		MemberInfo memberInfo = (MemberInfo)session.getAttribute("memberInfo");
		String loginEmail = "";
		if (memberInfo != null) {
			loginEmail = memberInfo.getMi_email();
			//System.out.println(loginEmail);
		}
		
		int cpage = 1, psize = 10, bsize = 10, spage, epage, rcnt, pcnt;
		// 현재 페이지 번호, 한 페이지에서 보여줄 게시글 개수, 한 블록에서 보여줄 페이지 개수, 블록별 시작/종료 페이지 번호, 레코드 개수, 페이지 개수를 저장할 변수 초기화
		int availablePoint;	// 사용가능한 포인트
		
		if (request.getParameter("cpage") != null) {
			cpage = Integer.parseInt(request.getParameter("cpage"));
		}	// 현재 페이지 번호가 있으면 정수형으로 형변환하여 저장
		
		PointListSvc pointListSvc = new PointListSvc();
		rcnt = pointListSvc.getPointCount(loginEmail);	// 검색된 포인트 내역의 총 개수(페이지 개수를 구하기 위해 필요)
		availablePoint = pointListSvc.getAvailablePoint(loginEmail);
		pointList = pointListSvc.getPointList(loginEmail, cpage, psize);
		// 현 페이지(cpage)에서 보여줄 검색된 상품 목록을 받아옴
		
		pcnt = rcnt / psize;
		if (rcnt % psize > 0)	pcnt++;		// 전체 페이지 수
		spage = (cpage - 1) / bsize * bsize + 1;	// 블록 시작 페이지 번호
		epage = spage + bsize - 1;
		if (epage > pcnt)	epage = pcnt;			// 블록 종료 페이지 번호
		
		PointPageInfo pageInfo = new PointPageInfo();	// 페이징에 필요한 정보들을 저장할 인스턴스
		// 페이징에 필요한 정보들
		pageInfo.setCpage(cpage);		// 현재 페이지 번호
		pageInfo.setRcnt(rcnt);			// 전체 게시글 개수
		pageInfo.setPcnt(pcnt);			// 전체 페이지 개수
		pageInfo.setSpage(spage);		// 블록 시작 페이지 번호
		pageInfo.setEpage(epage);		// 블록 종료 페이지 번호 
		pageInfo.setPsize(psize);		// 페이지 크기
		pageInfo.setBsize(bsize);		// 블록 크기

		pageInfo.setAvailablePoint(availablePoint);	// 사용가능한 포인트
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("pointList", pointList);		
		
		ActionForward forward = new ActionForward();
		forward.setPath("/member/point_list.jsp");
		// forward 인스턴스의 setPath() 메소드를 이용하여 forward 인스턴스의 path 멤버변수의 값을 지정
		
		System.out.println("여기는 PointListAct");
		return forward;
	}
}
