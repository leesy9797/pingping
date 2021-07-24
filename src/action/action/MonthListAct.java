package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class MonthListAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<MonthlyInfo> monthList = new ArrayList<MonthlyInfo>();
		ArrayList<GoodInfo> goodList = new ArrayList<GoodInfo>();

		HttpSession session = request.getSession();
		MemberInfo memberInfo = (MemberInfo)session.getAttribute("memberInfo");
		String loginEmail = "";
		if (memberInfo != null) {
			loginEmail = memberInfo.getMi_email();
			System.out.println(loginEmail);
		}
		
		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 12, bsize = 10, spage, epage, rcnt, pcnt;
		// ���� ������ ��ȣ, �� ���������� ������ �Խñ� ����, �� ��Ͽ��� ������ ������ ����, ��Ϻ� ����/���� ������ ��ȣ, ���ڵ� ����, ������ ������ ������ ���� �ʱ�ȭ
		
		if (request.getParameter("cpage") != null) {
			cpage = Integer.parseInt(request.getParameter("cpage"));
		}	// ���� ������ ��ȣ�� ������ ���������� ����ȯ�Ͽ� ����
		String where = "", tmpWhere = "";
		
		// �˻�����(ķ�����̸�, ķ������ġ, ��õ ��, ķ������) ������Ʈ��
		String name		= request.getParameter("name");		// ķ���� �̸�
		String location	= request.getParameter("location");	// ķ���� ��ġ
		String month = "", info = "";
		
		// �˻� ���ǿ� ���� where�� ����
		if (!isEmpty(name))		where += " and mr_name like '%" + name + "%' ";
		else { name = ""; }
		if (!isEmpty(location))	where += " and mr_location like '%" + location + "%' ";
		else { location = ""; }
		
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
		
		if (request.getParameterValues("info") != null) {	// ķ������
			String[] arrInfo = request.getParameterValues("info");	
			// üũ�Ǿ� �ִ� üũ�ڽ��� value ������ ���ڿ� �迭�� �޾ƿ�
			tmpWhere = "";
			for (int i = 0 ; i < arrInfo.length ; i++) {
				tmpWhere += " or mr_info like '%" + arrInfo[i] + "%' ";
				info += "/" + arrInfo[i];
			}
			info = info.substring(1);
			//System.out.println(info);
			where += " and (" + tmpWhere.substring(4) + ") ";
			//System.out.println(where);
		}
		
		// ��������(�ֽż�(�⺻��) - ��ϳ�������, �α��(���ƿ��) - ��������) ������Ʈ��
		String ord = request.getParameter("ord");
		if (ord == null || ord.equals("")) {
			ord = "lastdated";	// ��� �������� ������ �⺻��
		}
		// �������ǿ� ���� order by�� ����
		String orderBy = "";
		if (ord.equals("lastdated"))	orderBy = " order by mr_idx desc";
		else							orderBy = " order by mr_good desc, mr_read desc";
		
		MonthListSvc monthListSvc = new MonthListSvc();
		ChkFGSSvc chkFGSSvc = new ChkFGSSvc();
		rcnt = monthListSvc.getMonthCount(where);	// �˻��� ��ǰ�� �� ����(������ ������ ���ϱ� ���� �ʿ�)
		monthList = monthListSvc.getMonthList(where, orderBy, cpage, psize);
		// �� ������(cpage)���� ������ �˻��� �Խñ� ����� �޾ƿ�
		String kind = "m";
		goodList = chkFGSSvc.getGoodList(kind, loginEmail);
		
		pcnt = rcnt / psize;
		if (rcnt % psize > 0)	pcnt++;		// ��ü ������ ��
		spage = (cpage - 1) / bsize * bsize + 1;	// ��� ���� ������ ��ȣ
		epage = spage + bsize - 1;
		if (epage > pcnt)	epage = pcnt;			// ��� ���� ������ ��ȣ
		
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
		pageInfo.setName(name);			// ķ���� �̸�
		pageInfo.setLocation(location);	// ķ���� ��ġ
		pageInfo.setMonth(month);		// ��õ ��
		pageInfo.setInfo(info);			// ķ������
		
		pageInfo.setOrd(ord);			// ��������
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("monthList", monthList);	
		request.setAttribute("goodList", goodList);	
		
		ActionForward forward = new ActionForward();
		forward.setPath("/monthly/month_list.jsp");
		// forward �ν��Ͻ��� setPath() �޼ҵ带 �̿��Ͽ� forward �ν��Ͻ��� path ��������� ���� ����
		
		System.out.println("����� MonthListAct");
		return forward;
	}
	
	private boolean isEmpty(String str) {	// �Ű������� � ���̵� ����ִ��� ���θ� �˻��ϴ� �޼ҵ�
		boolean empty = true;
		if (str != null && !str.equals(""))	empty = false;
		return empty;
	}
}
