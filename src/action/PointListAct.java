package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class PointListAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<PointInfo> pointList = new ArrayList<PointInfo>();

		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		MemberInfo memberInfo = (MemberInfo)session.getAttribute("memberInfo");
		String loginEmail = "";
		if (memberInfo != null) {
			loginEmail = memberInfo.getMi_email();
			//System.out.println(loginEmail);
		}
		
		int cpage = 1, psize = 10, bsize = 10, spage, epage, rcnt, pcnt;
		// ���� ������ ��ȣ, �� ���������� ������ �Խñ� ����, �� ��Ͽ��� ������ ������ ����, ��Ϻ� ����/���� ������ ��ȣ, ���ڵ� ����, ������ ������ ������ ���� �ʱ�ȭ
		int availablePoint;	// ��밡���� ����Ʈ
		
		if (request.getParameter("cpage") != null) {
			cpage = Integer.parseInt(request.getParameter("cpage"));
		}	// ���� ������ ��ȣ�� ������ ���������� ����ȯ�Ͽ� ����
		
		PointListSvc pointListSvc = new PointListSvc();
		rcnt = pointListSvc.getPointCount(loginEmail);	// �˻��� ����Ʈ ������ �� ����(������ ������ ���ϱ� ���� �ʿ�)
		availablePoint = pointListSvc.getAvailablePoint(loginEmail);
		pointList = pointListSvc.getPointList(loginEmail, cpage, psize);
		// �� ������(cpage)���� ������ �˻��� ��ǰ ����� �޾ƿ�
		
		pcnt = rcnt / psize;
		if (rcnt % psize > 0)	pcnt++;		// ��ü ������ ��
		spage = (cpage - 1) / bsize * bsize + 1;	// ��� ���� ������ ��ȣ
		epage = spage + bsize - 1;
		if (epage > pcnt)	epage = pcnt;			// ��� ���� ������ ��ȣ
		
		PointPageInfo pageInfo = new PointPageInfo();	// ����¡�� �ʿ��� �������� ������ �ν��Ͻ�
		// ����¡�� �ʿ��� ������
		pageInfo.setCpage(cpage);		// ���� ������ ��ȣ
		pageInfo.setRcnt(rcnt);			// ��ü �Խñ� ����
		pageInfo.setPcnt(pcnt);			// ��ü ������ ����
		pageInfo.setSpage(spage);		// ��� ���� ������ ��ȣ
		pageInfo.setEpage(epage);		// ��� ���� ������ ��ȣ 
		pageInfo.setPsize(psize);		// ������ ũ��
		pageInfo.setBsize(bsize);		// ��� ũ��

		pageInfo.setAvailablePoint(availablePoint);	// ��밡���� ����Ʈ
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("pointList", pointList);		
		
		ActionForward forward = new ActionForward();
		forward.setPath("/member/point_list.jsp");
		// forward �ν��Ͻ��� setPath() �޼ҵ带 �̿��Ͽ� forward �ν��Ͻ��� path ��������� ���� ����
		
		System.out.println("����� PointListAct");
		return forward;
	}
}
