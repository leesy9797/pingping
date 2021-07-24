package admin.act;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import svc.FreeUpSvc;
import vo.*;

public class AdminMonthUpAct implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		MonthlyInfo monthInfo = new MonthlyInfo();	// ������ ���� ������ ������ �ν��Ͻ�
		
		request.setCharacterEncoding("utf-8");
		int cpage = Integer.parseInt(request.getParameter("cpage"));
		int idx = Integer.parseInt(request.getParameter("idx"));	// ������ ���� idx
		
		// �˻�����(�˻���, ��ϱⰣ, ��õ ��, �Խÿ���) ������Ʈ��
		String schtype	= request.getParameter("schtype");	// �˻�����(������, ķ�����)
		String keyword	= request.getParameter("keyword");	// �˻���
		String sdate	= request.getParameter("sdate");	// ��ϱⰣ �� ������
		String edate	= request.getParameter("edate");	// ��ϱⰣ �� ������
		String month	= request.getParameter("month");	// ��õ ��
		String isview	= request.getParameter("isview");	// �Խÿ���(y, n)
		String ord 		= request.getParameter("ord");		// ��������
		// �˻� ���ǿ� ���� where��, order by�� ���� �ʿ����
		
		MonthlyPageInfo pageInfo = new MonthlyPageInfo();
		pageInfo.setCpage(cpage);		// ���� ������ ��ȣ
		pageInfo.setSchtype(schtype);	// �˻�����(���̵� or ��ǰ��)
		pageInfo.setKeyword(keyword);	// �˻���
		pageInfo.setSdate(sdate);		// ��ϱⰣ �� ������
		pageInfo.setEdate(edate);		// ��ϱⰣ �� ������
		pageInfo.setMonth(month);		// ��õ ��
		pageInfo.setIsview(isview);		// �Խÿ���
		pageInfo.setOrd(ord);			// ��������
		
		AdminMonthUpSvc adminMonthUpSvc = new AdminMonthUpSvc();
		monthInfo = adminMonthUpSvc.getMonthInfo(idx);
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("monthInfo", monthInfo);

		ActionForward forward = new ActionForward();
		forward.setPath("/admin/monthly/month_up_form.jsp");	// request�� ��� �ϹǷ� dispatch�������

		System.out.println("����� AdminMonthUpAct");
		return forward;
	}
}
