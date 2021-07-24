package admin.act;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import vo.*;

public class AdminPdtUpAct implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProductInfo pdtInfo = new ProductInfo();	// ������ ��ǰ ������ ������ �ν��Ͻ�
		
		request.setCharacterEncoding("utf-8");
		int cpage = Integer.parseInt(request.getParameter("cpage"));
		String id = request.getParameter("id");	// ������ ��ǰ ID
		
		// �˻�����(�˻���, ��/�Һз�, �귣��, ���ݴ�) ������Ʈ��
		String isview	= request.getParameter("isview");	// �Խÿ���(y, n)
		String schtype	= request.getParameter("schtype");	// �˻�����(id, ��ǰ��)
		String keyword	= request.getParameter("keyword");	// �˻���
		String bcata	= request.getParameter("bcata");	// ��з�
		String scata	= request.getParameter("scata");	// �Һз�
		String brand	= request.getParameter("brand");	// �귣��
		String sprice	= request.getParameter("sprice");	// ���ݴ� �� ���� ����
		String eprice	= request.getParameter("eprice");	// ���ݴ� �� ���� ����
		String sdate	= request.getParameter("sdate");	// ��ϱⰣ �� ������
		String edate	= request.getParameter("edate");	// ��ϱⰣ �� ������
		String stock	= request.getParameter("stock");	// ���(�̻�)
		String ord 		= request.getParameter("ord");		// ��������
		// �˻� ���ǿ� ���� where��, order by�� ���� �ʿ����
		
		PdtPageInfo pageInfo = new PdtPageInfo();
		pageInfo.setCpage(cpage);		// ���� ������ ��ȣ
		pageInfo.setSchtype(schtype);	// �˻�����(���̵� or ��ǰ��)
		pageInfo.setKeyword(keyword);	// �˻���
		pageInfo.setBcata(bcata);		// ��з�
		pageInfo.setScata(scata);		// �Һз�
		pageInfo.setBrand(brand);		// �귣��
		pageInfo.setSprice(sprice);		// ���ݴ�(���۰�)
		pageInfo.setEprice(eprice);		// ���ݴ�(���ᰡ)
		pageInfo.setIsview(isview);		// �Խÿ���
		pageInfo.setSdate(sdate);		// ��ϱⰣ �� ������
		pageInfo.setEdate(edate);		// ��ϱⰣ �� ������
		pageInfo.setStock(stock);		// ���
		pageInfo.setOrd(ord);			// ��������
		
		AdminPdtUpSvc adminPdtUpSvc = new AdminPdtUpSvc();
		pdtInfo = adminPdtUpSvc.getPdtInfo(id);

		AdminPdtListSvc adminPdtListSvc = new AdminPdtListSvc();
		ArrayList<CataBigInfo> cataBigList = adminPdtListSvc.getCataBigList();	// ��з� ���
		ArrayList<CataSmallInfo> cataSmallList = adminPdtListSvc.getCataSmallList();	// �Һз� ���
		//ArrayList<BrandInfo> brandList = adminPdtListSvc.getBrandList();	// �귣�� ���
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("pdtInfo", pdtInfo);
		request.setAttribute("cataBigList", cataBigList);
		request.setAttribute("cataSmallList", cataSmallList);
		//request.setAttribute("brandList", brandList);

		ActionForward forward = new ActionForward();
		forward.setPath("/admin/product/pdt_up_form.jsp");	// request�� ��� �ϹǷ� dispatch�������

		System.out.println("����� act");
		return forward;
	}
}
