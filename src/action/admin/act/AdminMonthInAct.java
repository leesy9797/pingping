package admin.act;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import vo.*;

public class AdminMonthInAct implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		ActionForward forward = new ActionForward();
		forward.setPath("/admin/monthly/month_in_form.jsp");

		System.out.println("¿©±â´Â AdminMonthInAct");
		return forward;
	}
}
