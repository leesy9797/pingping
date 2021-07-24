package action;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import svc.*;
import vo.*;

public class CampUpFormAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		CampingInfo campingInfo = new CampingInfo();	// ������ ���� ������ ������ �ν��Ͻ�
		
		System.out.println("����� CampUpFormAct");
		
		request.setCharacterEncoding("utf-8");
		int idx = Integer.parseInt(request.getParameter("idx"));	
		int cpage = Integer.parseInt(request.getParameter("cpage"));
		int psize	= Integer.parseInt(request.getParameter("psize"));	
		String ord 		= request.getParameter("ord");		// ��������
		// �˻� ���ǿ� ���� where��, order by�� ���� �ʿ����
		
		CampPageInfo pageInfo = new CampPageInfo();
		pageInfo.setCpage(cpage);		// ���� ������ ��ȣ
		pageInfo.setPsize(psize);	// �˻���
		pageInfo.setOrd(ord);			// ��������
		
		CampUpFormSvc campUpFormSvc = new CampUpFormSvc();
		campingInfo = campUpFormSvc.getCampInfo(idx);
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("campingInfo", campingInfo);

		System.out.println("����� CampUpFormAct");
		
		ActionForward forward = new ActionForward();
		//forward.setRedirect(true);	// dispatch�� �ƴ� sendRedirect ������� �̵�
		forward.setPath("camp_up_form.jsp");	// request�� ��� �ϹǷ� dispatch�������

		return forward;
	}
		
}