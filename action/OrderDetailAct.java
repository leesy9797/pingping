package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class OrderDetailAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String oiid = request.getParameter("oiid");		// 상품ID
		//int cpage = Integer.parseInt(request.getParameter("cpage"));	// 현재 페이지 번호
		//int psize = Integer.parseInt(request.getParameter("psize"));	// 페이지 크기 & 목록보기 방식
		
		String tmpCpage = request.getParameter("cpage");
		int cpage = 1;
		if (tmpCpage != null && !tmpCpage.equals("")) cpage = Integer.parseInt(tmpCpage);
		//String tmpPsize = request.getParameter("psize");
		//int Psize = 10;
		//if (tmpPsize != null && !tmpPsize.equals("")) Psize = Integer.parseInt(tmpPsize);
		
		// 검색조건(검색어, 대/소분류, 브랜드, 가격대) 및 정렬조건 쿼리스트링
		// 목록으로 돌아갈 때 필요함
		String  sdate = "", edate = "", status = "", schdate = "";
		sdate = request.getParameter("sdate");
		edate = request.getParameter("edate");
		status = request.getParameter("status");
		schdate = request.getParameter("schdate");
		System.out.println(sdate);
		System.out.println(edate);
		System.out.println(status);
		System.out.println(schdate);
		
		System.out.println("두번째 : " + sdate);
		System.out.println("두번째 : " + edate);
		System.out.println("두번째 : " + status);
		System.out.println("두번째 : " + schdate);
		
		OrderPageInfo pageInfo = new OrderPageInfo();
		
		// 페이징에 필요한 정보들
		pageInfo.setCpage(cpage);		// 현재 페이지 번호		
		pageInfo.setSdate(sdate);		
		pageInfo.setEdate(edate);	
		pageInfo.setOistatus(status);		
		pageInfo.setSchdate(schdate);	
		
		HttpSession session = request.getSession();
		MemberInfo memberInfo = (MemberInfo)session.getAttribute("memberInfo");

		System.out.println(memberInfo.getMi_email());
		System.out.println(oiid);
		
		OrderDetailSvc orderDetailSvc = new OrderDetailSvc();
		ArrayList<CartInfo> orderInfo = orderDetailSvc.getOrderInfo(memberInfo.getMi_email(), oiid);
		
		request.setAttribute("orderInfo", orderInfo);
		request.setAttribute("pageInfo", pageInfo);

		System.out.println("아아, 여기는 OrderDetailAct");
		
		ActionForward forward = new ActionForward();
		forward.setPath("/order/order_detail.jsp");
		// forward 인스턴스의 setPath() 메소드를 이용하여 forward 인스턴스의 path 멤버변수의 값을 지정
		
		return forward;
	}

}
