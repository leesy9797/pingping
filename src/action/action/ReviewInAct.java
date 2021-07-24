package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class ReviewInAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ReviewInfo orderInfo = new ReviewInfo();	// 리뷰를 쓸 상품 정보를 저장할 인스턴스
		
		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");	// 주문상세번호
		
		ReviewInSvc reviewInSvc = new ReviewInSvc();
		orderInfo = reviewInSvc.getOrderInfo(id);

		request.setAttribute("orderInfo", orderInfo);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/review/review_in_form.jsp");

		System.out.println("여기는 ReviewInAct");
		return forward;
	}
}
