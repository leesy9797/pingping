package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class AddrInFormAct implements action.Action {	// 액트없이 그냥 버튼 onclick이벤트로 jsp로 이동하면 안되는지
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		ActionForward forward = new ActionForward();
		forward.setPath("/member/addr_in_form.jsp");

		System.out.println("여기는 AddrInFormAct");
		return forward;
	}
}
