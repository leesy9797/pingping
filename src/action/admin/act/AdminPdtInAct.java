package admin.act;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import vo.*;

public class AdminPdtInAct implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		AdminPdtInSvc adminPdtInSvc = new AdminPdtInSvc();
		
		ArrayList<CataBigInfo> cataBigList = adminPdtInSvc.getCataBigList();	// ��з� ���
		ArrayList<CataSmallInfo> cataSmallList = adminPdtInSvc.getCataSmallList();	// �Һз� ���
		//ArrayList<BrandInfo> brandList = adminPdtInSvc.getBrandList();	// �귣�� ���
		
		request.setAttribute("cataBigList", cataBigList);
		request.setAttribute("cataSmallList", cataSmallList);
		//request.setAttribute("brandList", brandList);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/admin/product/pdt_in_form.jsp");

		System.out.println("����� AdminPdtInAct");
		return forward;
	}
}
