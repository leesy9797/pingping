package action;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import svc.*;
import vo.*;

public class OrderListAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<CartInfo> orderList = new ArrayList<CartInfo>();
		
		request.setCharacterEncoding("utf-8");
		/*
		HttpSession session = request.getSession();
		MemberInfo memberInfo = (MemberInfo)session.getAttribute("memberInfo");
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		if (memberInfo == null) {
			out.println("<script>");
			out.println("alert('�α��� �Ŀ� �̿��Ͻ� �� �ֽ��ϴ�.');");
			out.println("../login_form.jsp");
			out.println("</script>");
			out.close();
		}
		*/
		int cpage = 1, psize = 10, bsize = 10, spage, epage, rcnt, pcnt, cnt;
		// ���� ������ ��ȣ, �� ���������� ������ �Խñ� ����, �� ��Ͽ��� ������ ������ ����, ��Ϻ� ����/���� ������ ��ȣ, ���ڵ� ����, ������ ������ ������ ���� �ʱ�ȭ

		String sdate = "", edate = "", status = "", schdate = "";
		
		if (request.getParameter("cpage") != null) {
			cpage = Integer.parseInt(request.getParameter("cpage"));
		}	// ���� ������ ��ȣ�� ������ ���������� ����ȯ�Ͽ� ����
		if (request.getParameter("psize") != null) {
			psize = Integer.parseInt(request.getParameter("psize"));
		}	// ���� ������ ��ȣ�� ������ ���������� ����ȯ�Ͽ� ����
		
		if (request.getParameter("sdate") != null) {
			sdate = request.getParameter("sdate");
		}	
		if (request.getParameter("edate") != null) {
			edate = request.getParameter("edate");
		}	
		if (request.getParameter("status") != null) {
			status = request.getParameter("status");
		}	
		if (request.getParameter("schdate") != null) {
			schdate = request.getParameter("schdate");
		}	
		
		/*
		String sdate, edate, status, schdate = null;
		
		sdate = request.getParameter("sdate");	
		edate = request.getParameter("edate");	
		status = request.getParameter("status");	
		
		
		int n = 0;
		if (request.getParameter("status") != null) {
			n = request.getParameter("schdate").indexOf("��");
			schdate = request.getParameter("schdate").substring(0, n);
		}

		System.out.println("schdate��" + schdate);
		
		Calendar today = Calendar.getInstance();
		int year = today.get(Calendar.YEAR);
		int month = today.get(Calendar.MONTH) + 1;
		int day = today.get(Calendar.DATE);
		//System.out.println("sdate��" + sdate);
		//System.out.println(year);
		//System.out.println(month);
		//System.out.println(day);
		
		int tmp = 0;
		if (schdate != null && !schdate.equals("")) {
			tmp = month - Integer.parseInt(schdate);
			System.out.println(tmp);
			if (tmp < 1 && tmp > -11) {
				year -= 1;
				month = 12 + tmp;
			} else if (tmp < 1 && tmp > -23) { 
				year -= 2;
				month = 24 + tmp;
			} else if (tmp < 1 && tmp > -35) { 
				year -= 3;
				month = 36 + tmp;
			} else {
				month = tmp;
			}
			sdate = year + "-" + ((month < 10) ? "0" + month : month) + "-" + ((day < 10) ? "0" + day : day);
		}
		//System.out.println(year);
		//System.out.println(month);
		//System.out.println(day);
		*/
		
		
		// �˻����ǿ� ���� where�� ����(�Ʒ��� �⺻����)
		//String where = " where a.cb_id = b.cb_id and a.cs_id = c.cs_id and b.cb_id = c.cb_id and pi_isview = 'y' ";
		String where = " where a.oi_id = b.oi_id and b.pi_id = c.pi_id ";
		String where2 = " where 1=1 ";
		if (!isEmpty(sdate))	{
			where += " and a.oi_date >= '" + sdate + " 00:00:00' ";
			where2 += " and oi_date >= '" + sdate + " 00:00:00' ";
		}
		else					sdate = "";
		if (!isEmpty(edate))	{
			where += " and a.oi_date <= '" + edate + " 23:59:59' ";
			where2 += " and oi_date <= '" + edate + " 23:59:59' ";
		}
		else					edate = "";		
		if (!isEmpty(status))	{
			where += " and a.oi_status = '" + status + "' ";
			where2 += " and oi_status = '" + status + "' ";
		}
		else					status = "";		

		OrderListSvc orderListSvc = new OrderListSvc();

		HttpSession session = request.getSession();
		MemberInfo memberInfo = (MemberInfo)session.getAttribute("memberInfo");

		rcnt = orderListSvc.getOrderCount(memberInfo.getMi_email());			// ȸ���� �ֹ���� �� ����(������ ������ ���ϱ� ���� �ʿ�)
		cnt = orderListSvc.getOrderListCount(memberInfo.getMi_email(), where2);	// ȸ���� �ֹ���� �� ����(������ ������ ���ϱ� ���� �ʿ�)
		//statuscnt = orderListSvc.getStatusCount()
		orderList = orderListSvc.getOrderList(memberInfo.getMi_email(), where, cpage, psize);
		pcnt = rcnt / psize;
		System.out.println("rcnt" + rcnt);
		System.out.println("pcnt" + pcnt);
		System.out.println("cnt" + cnt);
		if (rcnt % psize > 0)	pcnt++;		// ��ü ������ ��
		spage = (cpage - 1) / bsize * bsize + 1;	// ��� ���� ������ ��ȣ
		epage = spage + bsize - 1;
		if (epage > pcnt)	epage = pcnt;			// ��� ���� ������ ��ȣ
	
		
		OrderPageInfo pageInfo = new OrderPageInfo();	// ����¡�� �ʿ��� �������� ������ �ν��Ͻ�
		// ����¡�� �ʿ��� ������
		pageInfo.setCpage(cpage);		// ���� ������ ��ȣ
		pageInfo.setRcnt(rcnt);			// ��ü �Խñ� ����
		pageInfo.setPcnt(pcnt);			// ��ü ������ ����
		pageInfo.setSpage(spage);		// ��� ���� ������ ��ȣ
		pageInfo.setEpage(epage);		// ��� ���� ������ ��ȣ 
		pageInfo.setPsize(psize);		// ������ ũ��
		pageInfo.setBsize(bsize);		// ��� ũ��
		pageInfo.setCnt(cnt);			// �ֹ� ��� ����	
		
		// �˻� ���� ������
		pageInfo.setSdate(sdate);		
		pageInfo.setEdate(edate);	
		pageInfo.setOistatus(status);		
		pageInfo.setSchdate(schdate);		
				

		OrderStatusInfo statusInfo = new OrderStatusInfo();	// ����¡�� �ʿ��� �������� ������ �ν��Ͻ�
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("orderList", orderList);
		request.setAttribute("statusInfo", statusInfo);

		System.out.println("�ƾ�, ����� OrderListAct");
		
		ActionForward forward = new ActionForward();
		forward.setPath("/order/order_list.jsp");
		// forward �ν��Ͻ��� setPath() �޼ҵ带 �̿��Ͽ� forward �ν��Ͻ��� path ��������� ���� ����
		
		return forward;
	}
	
	private boolean isEmpty(String str) {
	// �Ű������� � ���̵� ����ִ��� ���θ� �˻��ϴ� �޼ҵ� -> �˻� ���ؼ� �Ⱦ�
		boolean empty = true;
		
		if (str != null && !str.equals("")) {
			empty = false;
		}
		
		return empty;
	}

}
