package admin.act;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import vo.*;

public class AdminMonthListAct implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<MonthlyInfo> monthList = new ArrayList<MonthlyInfo>();
		
		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 15, bsize = 10, spage, epage, rcnt, pcnt;
		if (request.getParameter("cpage") != null)	// cpage�� null�� �ƴ� ��쿡�� �޾ƿ�
			cpage = Integer.parseInt(request.getParameter("cpage"));
		
		// �˻�����(�˻���, ��/�Һз�, �귣��, ���ݴ�) ������Ʈ��
		String isview	= request.getParameter("isview");	// �Խÿ���(y, n)
		String schtype	= request.getParameter("schtype");	// �˻�����(�Խñ�����, ķ�����̸�)
		String keyword	= request.getParameter("keyword");	// �˻���
		String sdate	= request.getParameter("sdate");	// ��ϱⰣ �� ������
		String edate	= request.getParameter("edate");	// ��ϱⰣ �� ������
		String month = "";
		
		// �˻� ���ǿ� ���� where�� ����
		String where = " where 1=1 ", tmpWhere = "";
		if (!isEmpty(isview))	where += " and mr_isview = '" + isview + "' ";
		else	isview = "";
		if (!isEmpty(keyword))	where += " and mr_" + schtype + " like '%" + keyword + "%' ";
		else { keyword = "";	schtype = ""; }
		if (!isEmpty(sdate))	where += " and mr_date >= '" + sdate + " 00:00:00' ";
		else	sdate = "";
		if (!isEmpty(edate))	where += " and mr_date <= '" + edate + " 23:59:59' ";
		else	edate = "";
		
		// üũ�ڽ��� �Ѿ�� ���� ������ �迭�� �޾ƿ��� ���ÿ� where�� ����, �����ڷ� �̾ pageInfo�� ����
		if (request.getParameterValues("month") != null) {	// ��õ ��
			String[] arrMonth = request.getParameterValues("month");	
			// üũ�Ǿ� �ִ� üũ�ڽ��� value ������ ���ڿ� �迭�� �޾ƿ�
			tmpWhere = "";
			for (int i = 0 ; i < arrMonth.length ; i++) {
				tmpWhere += " or mr_month like '%" + arrMonth[i] + "%' ";
				month += "/" + arrMonth[i];
			}
			month = month.substring(1);
			//System.out.println(month);
			where += " and (" + tmpWhere.substring(4) + ") ";
			//System.out.println(where);
		}
		
		// ��������(�Ǹŷ�-��, ����-��/��, ���-��(�⺻), ��ǰ��-��, ����-��) ������Ʈ��
		String ord = request.getParameter("ord");
		if (ord == null || ord.equals(""))		ord = "lastdated";	// ��� �������� ������ �⺻��
		String orderBy = "";
		if (ord.equals("lastdated")) orderBy = " order by last_date" + 
			(ord.charAt(ord.length() - 1) == 'a' ? " asc" : " desc");
		else	orderBy = " order by mr_" + ord.substring(0, ord.length() - 1) + 
				(ord.charAt(ord.length() - 1) == 'a' ? " asc" : " desc");
		// ���� ���ǿ� ���� order by�� ����
		
		AdminMonthListSvc adminMonthListSvc = new AdminMonthListSvc();
		rcnt = adminMonthListSvc.getMonthCount(where);	// �˻��� ��ǰ�� �� ����(������ ������ ���ϱ� ���� �ʿ�)
		monthList = adminMonthListSvc.getMonthList(where, orderBy, cpage, psize);
		// �� ������(cpage)���� ������ �˻��� ��ǰ ����� �޾ƿ�
		
		pcnt = rcnt / psize;
		if (rcnt % psize > 0)	pcnt++;				// ��ü ������ ��
		spage = (cpage - 1) / bsize * bsize + 1;	// ����� ���� ������ ��ȣ
		epage = spage + bsize - 1;
		if (epage > pcnt)		epage = pcnt;		// ����� ���� ������ ��ȣ
		
		MonthlyPageInfo pageInfo = new MonthlyPageInfo();	// ����¡�� �ʿ��� �������� ������ �ν��Ͻ�
		// ����¡�� �ʿ��� ������
		pageInfo.setCpage(cpage);		// ���� ������ ��ȣ
		pageInfo.setRcnt(rcnt);			// ��ü �Խñ� ����
		pageInfo.setPcnt(pcnt);			// ��ü ������ ����
		pageInfo.setSpage(spage);		// ��� ���� ������ ��ȣ
		pageInfo.setEpage(epage);		// ��� ���� ������ ��ȣ
		pageInfo.setPsize(psize);		// ������ ũ��
		pageInfo.setBsize(bsize);		// ��� ũ��
		
		// �˻� ���� ������
		pageInfo.setSchtype(schtype);	// �˻�����(�Խñ�����, ķ�����̸�)
		pageInfo.setKeyword(keyword);	// �˻���
		pageInfo.setIsview(isview);		// �Խÿ���
		pageInfo.setSdate(sdate);		// ��ϱⰣ �� ������
		pageInfo.setEdate(edate);		// ��ϱⰣ �� ������
		pageInfo.setMonth(month);		// ��õ ��

		pageInfo.setOrd(ord);			// ��������
				
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("monthList", monthList);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/admin/monthly/month_list.jsp");

		System.out.println("����� AdminMonthListAct");
		return forward;
	}

	private boolean isEmpty(String str) {	// �Ű������� � ���̵� ����ִ��� ���θ� �˻��ϴ� �޼ҵ�
		boolean empty = true;
		if (str != null && !str.equals(""))	empty = false;
		return empty;
	}
}
