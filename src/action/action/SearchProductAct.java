package action;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import svc.*;
import vo.*;

public class SearchProductAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");

		ArrayList<ProductInfo> pdtList = new ArrayList<ProductInfo>();
		String keyword = request.getParameter("keyword");	// �˻���
		
		String where = " where a.cb_id = b.cb_id and a.cs_id = c.cs_id and b.cb_id = c.cb_id and pi_isview = 'y' ";
		
		if (!isEmpty(keyword))	where += " and a.pi_name like '%" + keyword + "%' or a.br_name like '%" + keyword + "%' ";
		else	keyword = "";
		
		PdtListSvc pdtListSvc = new PdtListSvc();
		int rcnt = pdtListSvc.getPdtCount(where);	// �˻��� ��ǰ�� �� ����(������ ������ ���ϱ� ���� �ʿ�)
		pdtList = pdtListSvc.getPdtList(where, "", 1, 10000);
		
		PdtPageInfo pageInfo = new PdtPageInfo();	// ����¡�� �ʿ��� �������� ������ �ν��Ͻ�
		pageInfo.setKeyword(keyword);	// �˻���
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("pdtList", pdtList);
		
		System.out.println("�ƾ�, ����� SearchProductAct");
		
		ActionForward forward = new ActionForward();
		//forward.setRedirect(true);	// dispatch�� �ƴ� sendRedirect ������� �̵�
		forward.setPath("/camping/search_product.jsp");
		
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