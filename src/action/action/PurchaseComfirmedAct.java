package action;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import svc.*;
import vo.*;

public class PurchaseComfirmedAct implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");

		System.out.println("아아, 여기는 PurchaseComfirmedAct");

		String oiid = request.getParameter("oiid");
		int price = Integer.parseInt(request.getParameter("price"));
		
		HttpSession session = request.getSession();
		MemberInfo memberInfo = (MemberInfo)session.getAttribute("memberInfo");

		CartInfo orderInfo = new CartInfo();
		orderInfo.setOi_id(oiid);
		orderInfo.setMi_email(memberInfo.getMi_email());
		orderInfo.setOi_pdtprice(price);

		String cpage = request.getParameter("cpage");
		String sdate = request.getParameter("sdate");
		String edate = request.getParameter("edate");
		String schdate = request.getParameter("schdate");
		
		PurchaseComfirmedSvc purchaseComfirmedSvc = new PurchaseComfirmedSvc();
		int result = purchaseComfirmedSvc.purchaseComfirmed(orderInfo);
		
		String lnk = "";
		if (result == 0) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('구매확정시 오류가 발생했습니다. \n다시 시도해 주세요.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		} else {
			lnk = "order_detail.ord?cpage=" + cpage + "&sdate=" + sdate + "&edate=" + edate + "&schdate=" + schdate + "&oiid=" + oiid;
		}
		
		System.out.println("아아, 여기는 PurchaseComfirmedAct");
		
		ActionForward forward = new ActionForward();
		//forward.setRedirect(true);	// dispatch가 아닌 sendRedirect 방식으로 이동
		forward.setPath(lnk);
		
		return forward;
	}
}
