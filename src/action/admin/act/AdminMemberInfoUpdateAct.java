package admin.act;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import svc.OrderDetailSvc;
import svc.PdtViewSvc;
import vo.*;

public class AdminMemberInfoUpdateAct implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String memo = request.getParameter("memo");
		String email = request.getParameter("email");
		
		System.out.println(memo);
		System.out.println(email);
		
		HttpSession session = request.getSession();
		AdminInfo AdminInfo = (AdminInfo)session.getAttribute("AdminInfo");

		
		AdminMemberInfoUpdateSvc adminMemberInfoUpdateSvc = new AdminMemberInfoUpdateSvc();
		int result = adminMemberInfoUpdateSvc.updateMemo(email, memo);

		//OrderPageInfo pageInfo = new OrderPageInfo();	// ����¡�� �ʿ��� �������� ������ �ν��Ͻ�
		
		//request.setAttribute("orderInfo", orderInfo);
		//request.setAttribute("pageInfo", pageInfo);

		System.out.println("�ƾ�, ����� AdminMemberInfoUpdateAct");
		
		ActionForward forward = new ActionForward();
		forward.setPath("/admin/member_info.mema?miemail=" + email);		
		return forward;
	}
	
}
