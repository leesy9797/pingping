package admin.act;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import admin.svc.*;
import vo.*;

public class AdminPdtProcAct implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String uploadPath = "D:/web/jsp/work/pingping/WebContent/product/pdt_img";
		// ��ǰ, �����̹������� ������ ��ġ�� �����η� ����
		int maxSize = 5 * 1024 * 1024;	// ���ε� �ִ� �뷮�� 5MB�� ����
		
		MultipartRequest multi = new MultipartRequest(
			request,	// request��ü�� multi�ν��Ͻ��� ���۵� �����͵��� �ޱ� ����
			uploadPath,	// ������ ������ ������ ����� ��ġ ����
			maxSize,	// �� ���� ���ε� �� �� �ִ� �ִ� ũ�� ����(byte ����)
			"utf-8",	// ������ ���ڵ� ���
			new DefaultFileRenamePolicy());	// ���� �̸��� �ߺ� ó��

		String wtype	= multi.getParameter("wtype");	// ��ǰ������ ���/���� ����
		String id		= multi.getParameter("id");		// ��ǰ���� ������ ��� ����� ��ǰID
		String cbid		= multi.getParameter("cbid");
		String csid		= multi.getParameter("csid");
		String brname	= multi.getParameter("brname");
		String name		= multi.getParameter("name");
		String price	= multi.getParameter("price");
		if (price == null || price.equals(""))	price = "0";
		String cost		= multi.getParameter("cost");
		if (cost == null || cost.equals(""))	cost = "0";
		String dcrate	= multi.getParameter("dcrate");
		if (dcrate == null || dcrate.equals("")) dcrate = "0";
		String option	= multi.getParameter("option");
		String pstock	= multi.getParameter("pstock");
		String pisview	= multi.getParameter("pisview");
		
		Enumeration files = multi.getFileNames();
		// ���ε��� ���ϸ���� Enumeration������ �޾ƿ�(�� file��Ʈ���� �޾ƿ��� ����)
		String img1 = "", img2 = "", img3 = "", img4 = "", img5 = "", desc = "";
		while (files.hasMoreElements()) {
			String f = (String)files.nextElement();
			switch (f) {
				case "img1" : img1 = multi.getFilesystemName(f);	break;
				case "img2" : img2 = multi.getFilesystemName(f);	break;
				case "img3" : img3 = multi.getFilesystemName(f);	break;
				case "img4" : img4 = multi.getFilesystemName(f);	break;
				case "img5" : img5 = multi.getFilesystemName(f);	break;
				case "desc" : desc = multi.getFilesystemName(f);	break;
			}
		}
		
		if (wtype.equals("in")) {
			if (img1 == null || img1.equals("null"))	img1 = "";
			if (img2 == null || img2.equals("null"))	img2 = "";
			if (img3 == null || img3.equals("null"))	img3 = "";
			if (img4 == null || img4.equals("null"))	img4 = "";
			if (img5 == null || img5.equals("null"))	img5 = "";
			if (desc == null || desc.equals("null"))	desc = "";
		} else {
		// ��ǰ���� ������ ��� �̹��� ���ε带 ���� ������ ������ �̹������� �޾ƿ�
			if (img1 == null || img1.equals("null"))	img1 = multi.getParameter("oldImg1");
			if (img2 == null || img2.equals("null"))	img2 = multi.getParameter("oldImg2");
			if (img3 == null || img3.equals("null"))	img3 = multi.getParameter("oldImg3");
			if (img4 == null || img4.equals("null"))	img4 = multi.getParameter("oldImg4");
			if (img5 == null || img5.equals("null"))	img5 = multi.getParameter("oldImg5");
			if (desc == null || desc.equals("null"))	desc = multi.getParameter("oldDesc");
		}
		
		ProductInfo pdtInfo = new ProductInfo();
		pdtInfo.setPi_id(id);	// ������ ��쿡�� ���ǰ�, ����� ��� ������� ����
		pdtInfo.setCb_id(cbid);				
		pdtInfo.setCs_id(csid);
		pdtInfo.setBr_name(brname);			
		pdtInfo.setPi_name(name);		
		pdtInfo.setPi_option(option);	
		pdtInfo.setPi_isview(pisview);	
		pdtInfo.setPi_img1(img1);		
		pdtInfo.setPi_img2(img2);		
		pdtInfo.setPi_img3(img3);	
		pdtInfo.setPi_img4(img4);		
		pdtInfo.setPi_img5(img5);			
		pdtInfo.setPi_desc(desc);
		pdtInfo.setPi_price(Integer.parseInt(price));
		pdtInfo.setPi_cost(Integer.parseInt(cost));
		pdtInfo.setPi_dcrate(Integer.parseInt(dcrate));
		pdtInfo.setPi_stock(Integer.parseInt(pstock));
		// ����ڰ� �Է��� ������ ��ǰ���� ����� �ν��Ͻ��� ����
		
		AdminPdtProcSvc adminPdtProcSvc = new AdminPdtProcSvc();
		boolean isSuccess = false;
		String failMsg = "";
		
		if (wtype.equals("in")) {	// ��ǰ ����� ���
			HttpSession session = request.getSession();
			AdminInfo ai = (AdminInfo)session.getAttribute("adminInfo");
			pdtInfo.setAi_idx(ai.getAi_idx());	// ��� ������ ��ȣ ����
			
			isSuccess = adminPdtProcSvc.pdtInsert(pdtInfo);
			failMsg = "��ǰ ��Ͽ� �����߽��ϴ�.";
		} else {	// ��ǰ���� ������ ���
			HttpSession session = request.getSession();
			AdminInfo ai = (AdminInfo)session.getAttribute("adminInfo");
			pdtInfo.setLast_admin(ai.getAi_idx());	// ��� ������ ��ȣ ����
			
			isSuccess = adminPdtProcSvc.pdtUpdate(pdtInfo);
			failMsg = "��ǰ ���� ������ �����߽��ϴ�.";
		}
		if (!isSuccess) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('" + failMsg + "');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		
		// ������ ������ �˻����� ������
		int cpage = 1;
		if (wtype.equals("up")) {
			cpage = Integer.parseInt(multi.getParameter("cpage"));
		}
		String isview	= multi.getParameter("isview");		// �Խÿ���(y, n)
		String schtype	= multi.getParameter("schtype");	// �˻�����(id, ��ǰ��)
		String keyword	= multi.getParameter("keyword");	// �˻���
		String bcata	= multi.getParameter("bcata");		// ��з�
		String scata	= multi.getParameter("scata");		// �Һз�
		String brand	= multi.getParameter("brand");		// �귣��
		String sprice	= multi.getParameter("sprice");		// ���ݴ� �� ���� ����
		String eprice	= multi.getParameter("eprice");		// ���ݴ� �� ���� ����
		String sdate	= multi.getParameter("sdate");		// ��ϱⰣ �� ������
		String edate	= multi.getParameter("edate");		// ��ϱⰣ �� ������
		String stock	= multi.getParameter("stock");		// ���(�̻�)

		// �˻����� ������Ʈ�� ����
		String args = "";
		if (isview == null)	args += "&isview=";		else args += "&isview=" + isview;
		if (schtype == null)args += "&schtype=";	else args += "&schtype=" + schtype;
		if (keyword == null)args += "&keyword=";	else args += "&keyword=" + keyword;
		if (bcata == null)	args += "&bcata=";		else args += "&bcata=" + bcata;
		if (scata == null)	args += "&scata=";		else args += "&scata=" + scata;
		if (brand == null)	args += "&brand=";		else args += "&brand=" + brand;
		if (sprice == null)	args += "&sprice=";		else args += "&sprice=" + sprice;
		if (eprice == null)	args += "&eprice=";		else args += "&eprice=" + eprice;
		if (sdate == null)	args += "&sdate=";		else args += "&sdate=" + sdate;
		if (edate == null)	args += "&edate=";		else args += "&edate=" + edate;
		if (stock == null)	args += "&stock=";		else args += "&stock=" + stock;
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(true);	// redirect ������� �̵��ϵ��� ����
		forward.setPath("pdt_list.pdta?cpage=" + cpage + args);
		
		return forward;
	}
}
