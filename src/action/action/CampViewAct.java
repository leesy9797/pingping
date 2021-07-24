package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class CampViewAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		int idx = Integer.parseInt(request.getParameter("idx"));	// ķ���ı� idx
		int cpage = Integer.parseInt(request.getParameter("cpage"));	// ���� ������ ��ȣ
		int psize = Integer.parseInt(request.getParameter("psize"));	// ������ ũ�� & ��Ϻ��� ���
		
		// �˻�����(�˻���, ��/�Һз�, �귣��, ���ݴ�) �� �������� ������Ʈ��
		// ������� ���ư� �� �ʿ���
		String ord 	= request.getParameter("ord");		// ��������
		
		ArrayList<CampReplyInfo> replyList = (ArrayList<CampReplyInfo>)request.getAttribute("replyList");

		CampPageInfo pageInfo = new CampPageInfo();	// ����¡�� �ʿ��� �������� ������ �ν��Ͻ�
		// ����¡�� �ʿ��� ������
		pageInfo.setCpage(cpage);		// ���� ������ ��ȣ
		pageInfo.setPsize(psize);		// ������ ũ��
		pageInfo.setOrd(ord);			// ��������	
		
		CampViewSvc campViewSvc = new CampViewSvc();
		CampingInfo campInfo = campViewSvc.getCampInfo(idx);

		int rcnt = campViewSvc.getCampReplyCount();	
		replyList = campViewSvc.getCampReplyList(idx);
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("campInfo", campInfo); 
		request.setAttribute("replyList", replyList);

		System.out.println("�ƾ�, ����� CampViewAct");

		ActionForward forward = new ActionForward();
		forward.setPath("/camping/camp_view.jsp");
		
		return forward;
	}

}
