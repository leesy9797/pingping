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
		int result = addrDelSvc.AddrDelete(maidx, memberInfo.getMi_email());
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(true);	// dispatch가 아닌 sendRedirect 방식으로 이동
		forward.setPath("addr_list.mem");

		System.out.println("여기는 AddrDelAct");
		return forward;
	}
}