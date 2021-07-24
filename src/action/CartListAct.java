package action;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import svc.*;
import vo.*;

public class CartListAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<CartInfo> cartList = new ArrayList<CartInfo>();

		System.out.println("아아, 여기는 CartListAct");
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		MemberInfo memberInfo = (MemberInfo)session.getAttribute("memberInfo");

		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		if (memberInfo == null) {
			out.println("<script>");
			out.println("alert('로그인 후에 이용하실 수 있습니다.');");
			out.println("../login_form.jsp");
			out.println("</script>");
			out.close();
		}
		
		CartListSvc cartListSvc = new CartListSvc();
		cartList = cartListSvc.getCartList(memberInfo.getMi_email());
		
		request.setAttribute("cartList", cartList);
		// dispatch 방식으로 이동할 cart_list.jsp 파일에서 사용할 수 있도록 request 객체에 속성으로 저장

		System.out.println("아아, 여기는 CartListAct");
		
		ActionForward forward = new ActionForward();
		forward.setPath("/order/cart_list.jsp");
		
		return forward;
	}	
}
