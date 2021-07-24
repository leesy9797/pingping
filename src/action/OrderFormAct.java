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
		
		if (kind.equals("cart")) {	// ��ٱ��ϸ� ���� ������ ���
			String[] arrIdx = request.getParameterValues("chk");
			// üũ�Ǿ� �ִ� üũ�ڽ��� value ������ ���ڿ� �迭�� �޾ƿ�
			
			for (int i = 0 ; i < arrIdx.length ; i++) {
				where += " or a.oc_idx = " + arrIdx[i];
			}
			where = " and (" + where.substring(4) + ") ";
			// ������ ��ǰ�鸸 �����ϱ� ���� ����
			
			cartList = orderFormSvc.getCartList(memberInfo.getMi_email(), where);
			session.setAttribute("cartList", cartList);	// dispatch ������� �̵��� cart_form.jsp ���Ͽ��� ����� �� �ֵ��� request ��ü�� �Ӽ����� ����
			
		} else {	// ��ǰ ��ȭ�鿡�� '�ٷα���' Ŭ����
			String piid = request.getParameter("piid");			// ��ǰID
			String cnt = request.getParameter("cnt");		// ���ż���
			String ocoption = request.getParameter("ocoption");	// ��ǰ �ɼ�
			
			ProductInfo pdtInfo = orderFormSvc.getPdtInfo(piid);

			request.setAttribute("pdtInfo", pdtInfo);
			request.setAttribute("occnt", cnt);
			request.setAttribute("ocoption", ocoption);
			
		}
		

		System.out.println("�ƾ�, ����� OrderFormAct");
		
		ActionForward forward = new ActionForward();
		forward.setPath("/order/order_form.jsp");
		
		return forward;
	}	
}
