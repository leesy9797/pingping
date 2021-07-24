package action;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import svc.*;
import vo.*;

public class CartDelProcAct implements Action {
// 특정 상품을 장바구니에 담는 작업을 처리하는 클래스
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");

		String kind = request.getParameter("kind");	// 전체(0), 일부(-1), 하나의 삭제 여부
		String idx = request.getParameter("idx");	// 삭제할 카트 인덱스 번호(들)
		
		HttpSession session = request.getSession();
		MemberInfo memberInfo = (MemberInfo)session.getAttribute("memberInfo");
		
		String where = " where mi_email = '" + memberInfo.getMi_email() + "' ";
		String tmpWhere = "";
		if (kind.equals("-1")) {	// 선택한 상품(들)을 삭제할 경우
			String[] arr = idx.split(",");
			for (int i = 0 ; i < arr.length ; i++) {
				tmpWhere += " or oc_idx = " + arr[i];  
			}
			tmpWhere = tmpWhere.substring(4);
			where += " and (" + tmpWhere + ")";
			
		} else if (!kind.equals("0")) {	// 한 상품만 삭제할 경우
			where += " and oc_idx = " + idx;
		}
		
		//System.out.println(where);
		
		CartDelProcSvc cartDelProcSvc = new CartDelProcSvc();
		int result = cartDelProcSvc.cartDelete(where);
						
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(result);
		out.close();
		
		System.out.println("아아, 여기는 CartDelProcAct");
		
		return null;
	}
}
