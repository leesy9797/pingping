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

		//OrderPageInfo pageInfo = new OrderPageInfo();	// 페이징에 필요한 정보들을 저장할 인스턴스
		
		//request.setAttribute("orderInfo", orderInfo);
		//request.setAttribute("pageInfo", pageInfo);

		System.out.println("아아, 여기는 AdminMemberInfoUpdateAct");
		
		ActionForward forward = new ActionForward();
		forward.setPath("/admin/member_info.mema?miemail=" + email);		
		return forward;
	}
	
}
