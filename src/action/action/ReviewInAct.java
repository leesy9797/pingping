package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class ReviewInAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ReviewInfo orderInfo = new ReviewInfo();	// ���並 �� ��ǰ ������ ������ �ν��Ͻ�
		
		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");	// �ֹ��󼼹�ȣ
		
		ReviewInSvc reviewInSvc = new ReviewInSvc();
		orderInfo = reviewInSvc.getOrderInfo(id);

		request.setAttribute("orderInfo", orderInfo);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/review/review_in_form.jsp");

		System.out.println("����� ReviewInAct");
		return forward;
	}
}
