package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class AddrDelAct implements action.Action {	
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		int maidx = Integer.parseInt(request.getParameter("maidx"));	
		
		HttpSession session = request.getSession();
		MemberInfo memberInfo = (MemberInfo)session.getAttribute("memberInfo");
		
		AddrDelSvc addrDelSvc = new AddrDelSvc();
		int result = addrDelSvc.addrDelete(maidx, memberInfo.getMi_email());
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(true);	// dispatch�� �ƴ� sendRedirect ������� �̵�
		forward.setPath("addr_list.mem");

		System.out.println("����� AddrDelAct");
		return forward;
	}
}