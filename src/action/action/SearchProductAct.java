package action;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import svc.*;
import vo.*;

public class SearchProductAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");

		ArrayList<ProductInfo> pdtList = new ArrayList<ProductInfo>();
		String keyword = request.getParameter("keyword");	// 검색어
		
		String where = " where a.cb_id = b.cb_id and a.cs_id = c.cs_id and b.cb_id = c.cb_id and pi_isview = 'y' ";
		
		if (!isEmpty(keyword))	where += " and a.pi_name like '%" + keyword + "%' or a.br_name like '%" + keyword + "%' ";
		else	keyword = "";
		
		PdtListSvc pdtListSvc = new PdtListSvc();
		int rcnt = pdtListSvc.getPdtCount(where);	// 검색된 상품의 총 개수(페이지 개수를 구하기 위해 필요)
		pdtList = pdtListSvc.getPdtList(where, "", 1, 10000);
		
		PdtPageInfo pageInfo = new PdtPageInfo();	// 페이징에 필요한 정보들을 저장할 인스턴스
		pageInfo.setKeyword(keyword);	// 검색어
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("pdtList", pdtList);
		
		System.out.println("아아, 여기는 SearchProductAct");
		
		ActionForward forward = new ActionForward();
		//forward.setRedirect(true);	// dispatch가 아닌 sendRedirect 방식으로 이동
		forward.setPath("/camping/search_product.jsp");
		
		return forward;
	}
	
	private boolean isEmpty(String str) {	// 매개변수에 어떤 값이든 들어있는지 여부를 검사하는 메소드
		boolean empty = true;
		
		if (str != null && !str.equals("")) {
			empty = false;
		}
		
		return empty;
	}
}