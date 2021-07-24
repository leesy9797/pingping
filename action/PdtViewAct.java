package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class PdtViewAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");		// 상품ID
		//int cpage = Integer.parseInt(request.getParameter("cpage"));	// 현재 페이지 번호
		//int psize = Integer.parseInt(request.getParameter("psize"));	// 페이지 크기 & 목록보기 방식
		
		String tmpCpage = request.getParameter("cpage");
		int cpage = 1;
		if (tmpCpage != null && !tmpCpage.equals("")) cpage = Integer.parseInt(tmpCpage);
		String tmpPsize = request.getParameter("psize");
		int Psize = 12;
		if (tmpPsize != null && !tmpPsize.equals("")) Psize = Integer.parseInt(tmpPsize);
		
		// 검색조건(검색어, 대/소분류, 브랜드, 가격대) 및 정렬조건 쿼리스트링
		// 목록으로 돌아갈 때 필요함
		String keyword, bcata, scata, brand, sprice, eprice, ord;
		keyword = request.getParameter("keyword");	// 검색어
		bcata	= request.getParameter("bcata");	// 대분류
		scata	= request.getParameter("scata");	// 소분류
		//brand	= request.getParameter("brand");	// 브랜드
		sprice	= request.getParameter("sprice");	// 가격대 중 시작가격
		eprice	= request.getParameter("eprice");	// 가격대 중 종료가격
		ord 	= request.getParameter("ord");		// 정렬조건
		
		PdtViewSvc pdtViewSvc = new PdtViewSvc();
		ProductInfo pdtInfo = pdtViewSvc.getPdtInfo(id);
		
		request.setAttribute("pdtInfo", pdtInfo);
		

		ActionForward forward = new ActionForward();
		forward.setPath("/product/pdt_view.jsp");
		// forward 인스턴스의 setPath() 메소드를 이용하여 forward 인스턴스의 path 멤버변수의 값을 지정
		
		return forward;
	}

}
