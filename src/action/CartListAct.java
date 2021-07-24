package action;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import svc.*;
import vo.*;

public class CartListAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<CartInfo> cartList = new ArrayList<CartInfo>();

		System.out.println("�ƾ�, ����� CartListAct");
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		MemberInfo memberInfo = (MemberInfo)session.getAttribute("memberInfo");

		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		if (memberInfo == null) {
			out.println("<script>");
			out.println("alert('�α��� �Ŀ� �̿��Ͻ� �� �ֽ��ϴ�.');");
			out.println("../login_form.jsp");
			out.println("</script>");
			out.close();
		}
		
		CartListSvc cartListSvc = new CartListSvc();
		cartList = cartListSvc.getCartList(memberInfo.getMi_email());
		
		request.setAttribute("cartList", cartList);
		// dispatch ������� �̵��� cart_list.jsp ���Ͽ��� ����� �� �ֵ��� request ��ü�� �Ӽ����� ����

		System.out.println("�ƾ�, ����� CartListAct");
		
		ActionForward forward = new ActionForward();
		forward.setPath("/order/cart_list.jsp");
		
		return forward;
	}	
}
