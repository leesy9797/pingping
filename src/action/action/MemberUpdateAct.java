package action;

import javax.servlet.http.*;
import vo.*;

public class MemberUpdateAct implements Action{
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		forward.setPath("/member/mypage_form.jsp");

		System.out.println("아아, 여기는 MemberUpdateAct");
		
		return forward;
	}
}
