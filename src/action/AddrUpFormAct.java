package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;
// ���� �����ִ� act
public class AddrUpFormAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		MemberInfo memberInfo = new MemberInfo();	// ������ ��ǰ������ ������ �ν��Ͻ�
		
		request.setCharacterEncoding("utf-8");
		int ma_idx = Integer.parseInt(request.getParameter("ma_idx"));	// ������ �ּ� ID

		AddrUpFormSvc memberAddrUpSvc = new AddrUpFormSvc();
		memberInfo = memberAddrUpSvc.getAddrInfo(ma_idx);

		request.setAttribute("memberInfo", memberInfo);

		ActionForward forward = new ActionForward();
		forward.setPath("/addr/addr_up_form.jsp");

		return forward;
	}
}
