package action;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import svc.*;
import vo.*;

public class CartInProcAct implements Action {
// Ư�� ��ǰ�� ��ٱ��Ͽ� ��� �۾��� ó���ϴ� Ŭ����
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");

		String piid = request.getParameter("piid");
		int occnt = Integer.parseInt(request.getParameter("occnt"));
		System.out.println(occnt);
		String ocoption = request.getParameter("ocoption");
		
		HttpSession session = request.getSession();
		MemberInfo memberInfo = (MemberInfo)session.getAttribute("memberInfo");
		
		CartInfo cart = new CartInfo();
		cart.setMi_email(memberInfo.getMi_email());
		cart.setPi_id(piid);
		cart.setOc_cnt(occnt);
		cart.setOc_option(ocoption);
		
		CartInProcSvc cartInProcSvc = new CartInProcSvc();
		int result = cartInProcSvc.cartInsert(cart);
		
		if (result == 0) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('��ٱ��Ͽ� ��ǰ �߰��� ������ �߻��߽��ϴ�. \n�ٽ� �õ��� �ּ���.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		
		System.out.println("�ƾ�, ����� CartInProcAct");
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(true);	// dispatch�� �ƴ� sendRedirect ������� �̵�
		forward.setPath("cart_list.ord");
		
		return forward;
	}
}
