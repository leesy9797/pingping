package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class ReviewUpAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ReviewInfo reviewInfo = new ReviewInfo();	// ������ ���� ������ ������ �ν��Ͻ�
		
		request.setCharacterEncoding("utf-8");
		int cpage = Integer.parseInt(request.getParameter("cpage"));
		int idx = Integer.parseInt(request.getParameter("idx"));	// ������ ���� idx
		
		String ord 		= request.getParameter("ord");		// ��������
		// �˻� ���ǿ� ���� where��, order by�� ���� �ʿ����
		
		ReviewPageInfo pageInfo = new ReviewPageInfo();
		pageInfo.setCpage(cpage);		// ���� ������ ��ȣ
		pageInfo.setOrd(ord);			// ��������
		
		ReviewUpSvc reviewUpSvc = new ReviewUpSvc();
		reviewInfo = reviewUpSvc.getReviewInfo(idx);
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("reviewInfo", reviewInfo);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/review/review_up_form.jsp");	// request�� ��� �ϹǷ� dispatch�������
		
		System.out.println("����� ReviewUpAct");
		return forward;
	}
}
