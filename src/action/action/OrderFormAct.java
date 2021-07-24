package action;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import svc.*;
import vo.*;

public class OrderFormAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<CartInfo> cartList = new ArrayList<CartInfo>();

		request.setCharacterEncoding("utf-8");
		
		String kind = request.getParameter("kind");
		String where = "";

		HttpSession session = request.getSession();
		MemberInfo memberInfo = (MemberInfo)session.getAttribute("memberInfo");
		OrderFormSvc orderFormSvc = new OrderFormSvc();
		
		if (kind.equals("cart")) {	// 장바구니를 통해 구매할 경우
			String[] arrIdx = request.getParameterValues("chk");
			// 체크되어 있는 체크박스의 value 값들을 문자열 배열로 받아옴
			
			for (int i = 0 ; i < arrIdx.length ; i++) {
				where += " or a.oc_idx = " + arrIdx[i];
			}
			where = " and (" + where.substring(4) + ") ";
			// 구매할 상품들만 추출하기 위한 조건
			
			cartList = orderFormSvc.getCartList(memberInfo.getMi_email(), where);
			session.setAttribute("cartList", cartList);	// dispatch 방식으로 이동할 cart_form.jsp 파일에서 사용할 수 있도록 request 객체에 속성으로 저장
			
		} else {	// 상품 상세화면에서 '바로구매' 클릭시
			String piid = request.getParameter("piid");			// 상품ID
			String cnt = request.getParameter("cnt");		// 구매수량
			String ocoption = request.getParameter("ocoption");	// 상품 옵션
			
			ProductInfo pdtInfo = orderFormSvc.getPdtInfo(piid);

			request.setAttribute("pdtInfo", pdtInfo);
			request.setAttribute("occnt", cnt);
			request.setAttribute("ocoption", ocoption);
			
		}
		

		System.out.println("아아, 여기는 OrderFormAct");
		
		ActionForward forward = new ActionForward();
		forward.setPath("/order/order_form.jsp");
		
		return forward;
	}	
}
