package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class QnaInFormAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		ActionForward forward = new ActionForward();
		forward.setPath("/service/qna_in_form.jsp");

		System.out.println("¿©±â´Â QnaInFormAct");
		return forward;
	}
}
