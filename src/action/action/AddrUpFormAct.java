package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;
// 폼을 보여주는 act
public class AddrUpFormAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		MemberInfo memberInfo = new MemberInfo();	// 수정할 상품정보를 저장할 인스턴스
		
		request.setCharacterEncoding("utf-8");
		int ma_idx = Integer.parseInt(request.getParameter("ma_idx"));	// 수정할 주소 ID

		AddrUpSvc memberAddrUpSvc = new AddrUpSvc();
		memberInfo = memberAddrUpSvc.getAddr(ma_idx);

		request.setAttribute("memberInfo", memberInfo);

		ActionForward forward = new ActionForward();
		forward.setPath("/addr/addr_up_form.jsp");

		return forward;
	}
}
