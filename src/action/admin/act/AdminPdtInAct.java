package admin.act;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import vo.*;

public class AdminPdtInAct implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		AdminPdtInSvc adminPdtInSvc = new AdminPdtInSvc();
		
		ArrayList<CataBigInfo> cataBigList = adminPdtInSvc.getCataBigList();	// 대분류 목록
		ArrayList<CataSmallInfo> cataSmallList = adminPdtInSvc.getCataSmallList();	// 소분류 목록
		//ArrayList<BrandInfo> brandList = adminPdtInSvc.getBrandList();	// 브랜드 목록
		
		request.setAttribute("cataBigList", cataBigList);
		request.setAttribute("cataSmallList", cataSmallList);
		//request.setAttribute("brandList", brandList);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/admin/product/pdt_in_form.jsp");

		System.out.println("여기는 AdminPdtInAct");
		return forward;
	}
}
