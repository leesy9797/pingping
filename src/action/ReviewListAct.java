package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class ReviewListAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<ReviewInfo> reviewList = new ArrayList<ReviewInfo>();

		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 10, bsize = 10, spage, epage, rcnt, pcnt;
		// ���� ������ ��ȣ, �� ���������� ������ �Խñ� ����, �� ��Ͽ��� ������ ������ ����, ��Ϻ� ����/���� ������ ��ȣ, ���ڵ� ����, ������ ������ ������ ���� �ʱ�ȭ
		
		if (request.getParameter("cpage") != null) {
			cpage = Integer.parseInt(request.getParameter("cpage"));
		}	// ���� ������ ��ȣ�� ������ ���������� ����ȯ�Ͽ� ����
		
		// ��������(�ֽż�(�⺻��) - ��������, ������ - ��������) ������Ʈ��
		String ord = request.getParameter("ord");
		if (ord == null || ord.equals("")) {
			ord = "lastdated";	// ��� �������� ������ �⺻��
		}

		// �������ǿ� ���� order by�� ����
		String orderBy = "";
		if (ord.equals("lastdated"))	orderBy = " order by pr_date desc";
		else							orderBy = " order by pr_star desc";

		ReviewListSvc reviewListSvc = new ReviewListSvc();
		rcnt = reviewListSvc.getReviewCount();	// �˻��� ������ �� ����(������ ������ ���ϱ� ���� �ʿ�)
		reviewList = reviewListSvc.getReviewList(orderBy, cpage, psize);
		// �� ������(cpage)���� ������ �˻��� ��ǰ ����� �޾ƿ�
		
		pcnt = rcnt / psize;
		if (rcnt % psize > 0)	pcnt++;		// ��ü ������ ��
		spage = (cpage - 1) / bsize * bsize + 1;	// ��� ���� ������ ��ȣ
		epage = spage + bsize - 1;
		if (epage > pcnt)	epage = pcnt;			// ��� ���� ������ ��ȣ
		
		ReviewPageInfo pageInfo = new ReviewPageInfo();	// ����¡�� �ʿ��� �������� ������ �ν��Ͻ�
		// ����¡�� �ʿ��� ������
		pageInfo.setCpage(cpage);		// ���� ������ ��ȣ
		pageInfo.setRcnt(rcnt);			// ��ü �Խñ� ����
		pageInfo.setPcnt(pcnt);			// ��ü ������ ����
		pageInfo.setSpage(spage);		// ��� ���� ������ ��ȣ
		pageInfo.setEpage(epage);		// ��� ���� ������ ��ȣ 
		pageInfo.setPsize(psize);		// ������ ũ��
		pageInfo.setBsize(bsize);		// ��� ũ��
		
		pageInfo.setOrd(ord);			// ��������
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("reviewList", reviewList);		
		
		ActionForward forward = new ActionForward();
		forward.setPath("/review/review_list.jsp");
		// forward �ν��Ͻ��� setPath() �޼ҵ带 �̿��Ͽ� forward �ν��Ͻ��� path ��������� ���� ����

		System.out.println("����� ReviewListAct");
		return forward;
	}
}
