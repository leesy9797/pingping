package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class OrderDetailAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String oiid = request.getParameter("oiid");		// ��ǰID
		//int cpage = Integer.parseInt(request.getParameter("cpage"));	// ���� ������ ��ȣ
		//int psize = Integer.parseInt(request.getParameter("psize"));	// ������ ũ�� & ��Ϻ��� ���
		
		String tmpCpage = request.getParameter("cpage");
		int cpage = 1;
		if (tmpCpage != null && !tmpCpage.equals("")) cpage = Integer.parseInt(tmpCpage);
		//String tmpPsize = request.getParameter("psize");
		//int Psize = 10;
		//if (tmpPsize != null && !tmpPsize.equals("")) Psize = Integer.parseInt(tmpPsize);
		
		// �˻�����(�˻���, ��/�Һз�, �귣��, ���ݴ�) �� �������� ������Ʈ��
		// ������� ���ư� �� �ʿ���
		String  sdate = "", edate = "", status = "", schdate = "";
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
		
		OrderPageInfo pageInfo = new OrderPageInfo();
		
		// ����¡�� �ʿ��� ������
		pageInfo.setCpage(cpage);		// ���� ������ ��ȣ		
		pageInfo.setSdate(sdate);		
		pageInfo.setEdate(edate);	
		pageInfo.setOistatus(status);		
		pageInfo.setSchdate(schdate);	
		
		HttpSession session = request.getSession();
		MemberInfo memberInfo = (MemberInfo)session.getAttribute("memberInfo");

		System.out.println(memberInfo.getMi_email());
		System.out.println(oiid);
		
		OrderDetailSvc orderDetailSvc = new OrderDetailSvc();
		ArrayList<CartInfo> orderInfo = orderDetailSvc.getOrderInfo(memberInfo.getMi_email(), oiid);
		
		request.setAttribute("orderInfo", orderInfo);
		request.setAttribute("pageInfo", pageInfo);

		System.out.println("�ƾ�, ����� OrderDetailAct");
		
		ActionForward forward = new ActionForward();
		forward.setPath("/order/order_detail.jsp");
		// forward �ν��Ͻ��� setPath() �޼ҵ带 �̿��Ͽ� forward �ν��Ͻ��� path ��������� ���� ����
		
		return forward;
	}

}
