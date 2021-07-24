package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class FreeInFormAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		ActionForward forward = new ActionForward();
		forward.setPath("/free/free_in_form.jsp");

		System.out.println("¿©±â´Â FreeInFormAct");
		return forward;
	}
}
