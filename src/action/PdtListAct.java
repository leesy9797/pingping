package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class PdtListAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<ProductInfo> pdtList = new ArrayList<ProductInfo>();
		
		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 12, bsize = 10, spage, epage, rcnt, pcnt;
		// ���� ������ ��ȣ, �� ���������� ������ �Խñ� ����, �� ��Ͽ��� ������ ������ ����, ��Ϻ� ����/���� ������ ��ȣ, ���ڵ� ����, ������ ������ ������ ���� �ʱ�ȭ

		if (request.getParameter("cpage") != null) {
			cpage = Integer.parseInt(request.getParameter("cpage"));
		}	// ���� ������ ��ȣ�� ������ ���������� ����ȯ�Ͽ� ����
		if (request.getParameter("psize") != null) {
			psize = Integer.parseInt(request.getParameter("psize"));
		}	// ���� ������ ��ȣ�� ������ ���������� ����ȯ�Ͽ� ����
		
		// �˻�����(�˻���, ��/�Һз�, �귣��, ���ݴ�) ������Ʈ��
		String keyword, bcata, scata, brand, sprice, eprice;
		keyword = request.getParameter("keyword");	// �˻���
		bcata = request.getParameter("bcata");		// ��з�
		scata = request.getParameter("scata");		// �Һз�
		//brand = request.getParameter("brand");		// �귣��
		sprice = request.getParameter("sprice");	// ���ݴ� �� ���۰���
		eprice = request.getParameter("eprice");	// ���ݴ� �� ���ᰡ��
		
		// �˻����ǿ� ���� where�� ����(�Ʒ��� �⺻����)
		String where = " where a.cb_id = b.cb_id and a.cs_id = c.cs_id and b.cb_id = c.cb_id and pi_isview = 'y' ";
		
		if (!isEmpty(keyword))	where += " and a.pi_name like '%" + keyword + "%' ";
		else	keyword = "";
		if (!isEmpty(bcata))	where += " and b.cb_id = '" + bcata + "' ";
		else	bcata = "";
		if (!isEmpty(scata))	where += " and c.cs_id = '" + scata + "' ";
		else	scata = "";
		//if (!isEmpty(brand))	where += " and d.b_id = '" + brand + "' ";
		//else	brand = "";
		if (!isEmpty(sprice))	where += " and a.pi_price >= '" + sprice + "' ";
		else	sprice = "";
		if (!isEmpty(eprice))	where += " and a.pi_price <= '" + eprice + "' ";
		else	eprice = "";
		
		// ��������(�Ǹŷ�(�α��) - ��������, ���� - ��������/��������, ��� - ��������(�⺻��), ��ǰ�� - ��������, ���� - ��������) ������Ʈ��
		String ord = request.getParameter("ord");
		if (ord == null || ord.equals("")) {
			ord = "idd";	// ��� �������� ������ �⺻��
		}
		// �������ǿ� ���� order by�� ����
		String orderBy = " order by a.pi_" + ord.substring(0, ord.length() - 1) + 	// idd��� desc�� �ǹ��ϴ� d�� ���� ������
			(ord.charAt(ord.length() - 1) == 'a' ? " asc" : " desc");	// idd��  desc�� �ǹ��ϴ� d�� ����(���� ������ ����)
		
		PdtListSvc pdtListSvc = new PdtListSvc();
		rcnt = pdtListSvc.getPdtCount(where);	// �˻��� ��ǰ�� �� ����(������ ������ ���ϱ� ���� �ʿ�)
		pdtList = pdtListSvc.getPdtList(where, orderBy, cpage, psize);
		// �� ������(cpage)���� ������ �˻��� ��ǰ ����� �޾ƿ�
		
		pcnt = rcnt / psize;
		if (rcnt % psize > 0)	pcnt++;		// ��ü ������ ��
		spage = (cpage - 1) / bsize * bsize + 1;	// ��� ���� ������ ��ȣ
		epage = spage + bsize - 1;
		if (epage > pcnt)	epage = pcnt;			// ��� ���� ������ ��ȣ
		
		PdtPageInfo pageInfo = new PdtPageInfo();	// ����¡�� �ʿ��� �������� ������ �ν��Ͻ�
		// ����¡�� �ʿ��� ������
		pageInfo.setCpage(cpage);		// ���� ������ ��ȣ
		pageInfo.setRcnt(rcnt);			// ��ü �Խñ� ����
		pageInfo.setPcnt(pcnt);			// ��ü ������ ����
		pageInfo.setSpage(spage);		// ��� ���� ������ ��ȣ
		pageInfo.setEpage(epage);		// ��� ���� ������ ��ȣ 
		pageInfo.setPsize(psize);		// ������ ũ��
		pageInfo.setBsize(bsize);		// ��� ũ��
		 
		// �˻� ���� ������
		pageInfo.setKeyword(keyword);	// �˻���
		pageInfo.setBcata(bcata);		// ��з�
		pageInfo.setScata(scata);		// �Һз�
		//pageInfo.setBrand(brand);		// �귣��
		pageInfo.setSprice(sprice);		// ���ݴ�(���۰�)
		pageInfo.setEprice(eprice);		// ���ݴ�(���ᰡ)
		
		pageInfo.setOrd(ord);			// ��������
		
		ArrayList<CataBigInfo> cataBigList = pdtListSvc.getCataBigList();		// ��з� ���
		ArrayList<CataSmallInfo> cataSmallList = pdtListSvc.getCataSmallList();	// �Һз� ���
		ArrayList<BrandInfo> brandList = pdtListSvc.getBrandList();				// �귣�� ���
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("pdtList", pdtList);
		request.setAttribute("cataBigList", cataBigList);
		request.setAttribute("cataSmallList", cataSmallList);
		request.setAttribute("brandList", brandList);
		
		
		
		ActionForward forward = new ActionForward();
		forward.setPath("/product/pdt_list.jsp");
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
