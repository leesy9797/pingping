package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class PdtViewAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");		// ��ǰID
		//int cpage = Integer.parseInt(request.getParameter("cpage"));	// ���� ������ ��ȣ
		//int psize = Integer.parseInt(request.getParameter("psize"));	// ������ ũ�� & ��Ϻ��� ���
		
		String tmpCpage = request.getParameter("cpage");
		int cpage = 1;
		if (tmpCpage != null && !tmpCpage.equals("")) cpage = Integer.parseInt(tmpCpage);
		String tmpPsize = request.getParameter("psize");
		int Psize = 12;
		if (tmpPsize != null && !tmpPsize.equals("")) Psize = Integer.parseInt(tmpPsize);
		
		// �˻�����(�˻���, ��/�Һз�, �귣��, ���ݴ�) �� �������� ������Ʈ��
		// ������� ���ư� �� �ʿ���
		String keyword, bcata, scata, brand, sprice, eprice, ord;
		keyword = request.getParameter("keyword");	// �˻���
		bcata	= request.getParameter("bcata");	// ��з�
		scata	= request.getParameter("scata");	// �Һз�
		//brand	= request.getParameter("brand");	// �귣��
		sprice	= request.getParameter("sprice");	// ���ݴ� �� ���۰���
		eprice	= request.getParameter("eprice");	// ���ݴ� �� ���ᰡ��
		ord 	= request.getParameter("ord");		// ��������
		
		PdtViewSvc pdtViewSvc = new PdtViewSvc();
		ProductInfo pdtInfo = pdtViewSvc.getPdtInfo(id);
		
		request.setAttribute("pdtInfo", pdtInfo);
		

		ActionForward forward = new ActionForward();
		forward.setPath("/product/pdt_view.jsp");
		// forward �ν��Ͻ��� setPath() �޼ҵ带 �̿��Ͽ� forward �ν��Ͻ��� path ��������� ���� ����
		
		return forward;
	}

}
