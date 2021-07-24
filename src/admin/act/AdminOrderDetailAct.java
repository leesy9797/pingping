package admin.act;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import svc.OrderDetailSvc;
import svc.PdtViewSvc;
import vo.*;

public class AdminOrderDetailAct implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String oiid = request.getParameter("oiid");
		String miemail = request.getParameter("miemail");
		
		System.out.println(oiid);
		System.out.println(miemail);
		
		HttpSession session = request.getSession();
		AdminInfo AdminInfo = (AdminInfo)session.getAttribute("AdminInfo");
	
		AdminOrderDetailSvc adminOrderDetailSvc = new AdminOrderDetailSvc();
		ArrayList<CartInfo> orderInfo = adminOrderDetailSvc.getOrderInfo(miemail, oiid);
		
		request.setAttribute("orderInfo", orderInfo);

		System.out.println("�ƾ�, ����� AdminOrderDetailAct");
		
		ActionForward forward = new ActionForward();
		forward.setPath("/admin/order/order_detail.jsp");
		// forward �ν��Ͻ��� setPath() �޼ҵ带 �̿��Ͽ� forward �ν��Ͻ��� path ��������� ���� ����
		
		return forward;
	}
	
}
