package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class AddrInFormAct implements action.Action {	// ��Ʈ���� �׳� ��ư onclick�̺�Ʈ�� jsp�� �̵��ϸ� �ȵǴ���
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		ActionForward forward = new ActionForward();
		forward.setPath("/member/addr_in_form.jsp");

		System.out.println("����� AddrInFormAct");
		return forward;
	}
}
