package admin.act;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import vo.*;

public class AdminOrderListAct implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<CartInfo> cartList = new ArrayList<CartInfo>();
		
		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 15, bsize = 10, spage, epage, rcnt, pcnt;
		// ���� ������ ��ȣ, �� ���������� ������ �Խñ� ����, �� ��Ͽ��� ������ ������ ����, ��Ϻ� ����/���� ������ ��ȣ, ���ڵ� ����, ������ ������ ������ ���� �ʱ�ȭ

		System.out.println("�ƾ�, ����� AdminOrderListAct");
		
		if (request.getParameter("cpage") != null) {
			cpage = Integer.parseInt(request.getParameter("cpage"));
		}	// ���� ������ ��ȣ�� ������ ���������� ����ȯ�Ͽ� ����
		
		// �˻�����(�ֹ���ȣ, �ֹ����̸���, �ֹ����̸�, �ֹ�����ȭ��ȣ �ֹ��� ����, �ֹ��� ����) ������Ʈ��
		String oiid, miemail, miname, miphone, sdate, edate;	//oistatus, 
		oiid = request.getParameter("oiid");	
		miemail = request.getParameter("miemail");
		miname= request.getParameter("miname");
		miphone= request.getParameter("miphone");
		//oistatus = request.getParameter("oistatus");
		sdate = request.getParameter("sdate");	
		edate = request.getParameter("edate");	
		String oistatuses[] = null;
		String path = "";
		if (request.getParameter("path") != null) {
			path = request.getParameter("path");
		}
		
		if (request.getParameterValues("oistatuses") != null) {
			oistatuses = request.getParameterValues("oistatuses");
			Arrays.toString(oistatuses);
		}
		
		System.out.println("��Ʈ" + Arrays.toString(oistatuses));
		
		// �˻����ǿ� ���� where�� ����(�Ʒ��� �⺻����)
		//String where = " where a.cs_id = c.cs_id and b.cb_id = c.cb_id and a.b_id = d.b_id ";
		String where = " where a.mi_email = b.mi_email ";
		if (!isEmpty(oiid))		where += " and a.oi_id like '%" + oiid + "%' ";
		else	oiid = "";
		if (!isEmpty(miemail))	where += " and a.mi_email like '%" + miemail + "%' ";
		else	miemail = "";
		if (!isEmpty(miname))	where += " and b.mi_name like '%" + miname + "%' ";
		else	miname = "";
		if (!isEmpty(miphone))	where += " and b.mi_phone like '%" + miphone + "%' ";
		else	miphone = "";
		//if (!isEmpty(oistatus))	where += " and a.oi_status = '" + oistatus + "' ";
		//else	oistatus = "";
		if (!isEmpty(sdate))	where += " and a.oi_date >= '" + sdate + " 00:00:00' ";
		else	sdate = "";
		if (!isEmpty(edate))	where += " and a.oi_date <= '" + edate + " 23:59:59' ";
		else	edate = "";
		

		String status = "";
		String tmpWhere = "";
		if (oistatuses == null) 	where += "";
		else {
			
			for (int i = 0 ; i < oistatuses.length ; i++) {
				tmpWhere += " or a.oi_status = '" + oistatuses[i] + "' ";
				status += "/" + oistatuses[i];
			}
			
			where += " and (" + tmpWhere.substring(4) + ") ";
			status = status.substring(1);
			System.out.println("��Ʈ2" + status);
			//for (String status : oistatuses) {
				//where += " and a.oi_status = '" + status + "' ";
			//}
		}

		System.out.println("��Ʈ" + where);
		AdminOrderListSvc adminOrderListSvc = new AdminOrderListSvc();
		rcnt = adminOrderListSvc.getOrderCount(where);	// �˻��� ��ǰ�� �� ����(������ ������ ���ϱ� ���� �ʿ�)
		
		pcnt = rcnt / psize;
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
		 
		// �˻� ���� ������
		pageInfo.setOiid(oiid);			// �ֹ���ȣ
		pageInfo.setMiemail(miemail);	// �ֹ����̸���
		pageInfo.setMiname(miname);		// �ֹ����̸�
		pageInfo.setMiphone(miphone);	// �ֹ�����ȭ��ȣ
		pageInfo.setSdate(sdate);		// �ֹ��Ⱓ �� ������
		pageInfo.setEdate(edate);		// �ֹ��Ⱓ �� ������
		pageInfo.setOistatuses(oistatuses);	
		pageInfo.setOistatus(status);
		
		cartList = adminOrderListSvc.getOrderList(where, cpage, psize);
		// �� ������(cpage)���� ������ �˻��� ��ǰ ����� �޾ƿ�
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("cartList", cartList);
		
		System.out.println("�ƾ�, ����� AdminOrderListAct");
		
		ActionForward forward = new ActionForward();
		forward.setPath("/admin/order/order_list.jsp?path=" + path);
		// forward �ν��Ͻ��� setPath() �޼ҵ带 �̿��Ͽ� forward �ν��Ͻ��� path ��������� ���� ����
		
		return forward;
	}
	
	private boolean isEmpty(String str) {	// �Ű������� � ���̵� ����ִ��� ���θ� �˻��ϴ� �޼ҵ�
		boolean empty = true;
		
		if (str != null && !str.equals("")) {
			empty = false;
		}
		
		return empty;
	}
	
}
