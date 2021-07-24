package admin.act;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import svc.OrderDetailSvc;
import svc.PdtViewSvc;
import vo.*;

public class AdminOrderDetailUpdateAct implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String oiid = request.getParameter("oiid");
		String odid = request.getParameter("odid");
		String memo = request.getParameter("memo");
		String email = request.getParameter("email");
		
		System.out.println(oiid);
		System.out.println(odid);
		System.out.println(memo);
		System.out.println(email);
		//int cpage = Integer.parseInt(request.getParameter("cpage"));	// ���� ������ ��ȣ
		//int psize = Integer.parseInt(request.getParameter("psize"));	// ������ ũ�� & ��Ϻ��� ���
		
		//String tmpCpage = request.getParameter("cpage");
		//int cpage = 1;
		//if (tmpCpage != null && !tmpCpage.equals("")) cpage = Integer.parseInt(tmpCpage);
		//String tmpPsize = request.getParameter("psize");
		//int Psize = 10;
		//if (tmpPsize != null && !tmpPsize.equals("")) Psize = Integer.parseInt(tmpPsize);
		
		// �˻�����(�˻���, ��/�Һз�, �귣��, ���ݴ�) �� �������� ������Ʈ��
		// ������� ���ư� �� �ʿ���
		/*String  sdate = "", edate = "", status = "", schdate = "";
		sdate = request.getParameter("sdate");
		edate = request.getParameter("edate");
		status = request.getParameter("status");
		schdate = request.getParameter("schdate");
		System.out.println(sdate);
		System.out.println(edate);
		System.out.println(status);
		System.out.println(schdate);
		
		System.out.println("�ι�° : " + sdate);
		System.out.println("�ι�° : " + edate);
		System.out.println("�ι�° : " + status);
		System.out.println("�ι�° : " + schdate);
		
		// ����¡�� �ʿ��� ������
		pageInfo.setCpage(cpage);		// ���� ������ ��ȣ		
		pageInfo.setSdate(sdate);		
		pageInfo.setEdate(edate);	
		pageInfo.setOistatus(status);		
		pageInfo.setSchdate(schdate);		
		*/
		HttpSession session = request.getSession();
		AdminInfo AdminInfo = (AdminInfo)session.getAttribute("AdminInfo");

		//System.out.println(memberInfo.getMi_email());
		//System.out.println(oiid);
		
		AdminOrderDetailUpdateSvc adminOrderDetailUpdateSvc = new AdminOrderDetailUpdateSvc();
		int result = adminOrderDetailUpdateSvc.updateMemo(oiid, odid, memo);

		//OrderPageInfo pageInfo = new OrderPageInfo();	// ����¡�� �ʿ��� �������� ������ �ν��Ͻ�
		
		//request.setAttribute("orderInfo", orderInfo);
		//request.setAttribute("pageInfo", pageInfo);

		System.out.println("�ƾ�, ����� AdminOrderDetailUpdateAct");
		
		ActionForward forward = new ActionForward();
		forward.setPath("/admin/order_detail.orda?miemail=" + email + "&oiid=" + oiid);		
		return forward;
	}
	
}
