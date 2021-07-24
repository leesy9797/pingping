package action;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import svc.*;
import vo.*;

public class CartUpProcAct implements Action {
	// 특정 상품을 장바구니에 담는 작업을 처리하는 클래스
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");

		String kind = request.getParameter("kind");	// 수량(cnt) or 옵션(opt) 
		String op = request.getParameter("op");		// 수량이면 연산자, 옵션이면 옵션값
		int idx = Integer.parseInt(request.getParameter("idx"));	// 카트 인덱스 번호(PK)
		String piid = request.getParameter("piid");	// 상품 ID로 옵션 변경시 필요		
		
		HttpSession session = request.getSession();
		MemberInfo memberInfo = (MemberInfo)session.getAttribute("memberInfo");
		
		CartUpProcSvc cartUpProcSvc = new CartUpProcSvc();
		int result = cartUpProcSvc.cartUpdate(kind, op, idx, memberInfo.getMi_email(), piid);
						
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(result);
		out.close();
		
		System.out.println("아아, 여기는 CartUpProcAct");
		
		return null;
	}
}
