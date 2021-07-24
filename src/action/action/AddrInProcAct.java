package action;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import svc.*;
import vo.*;

public class AddrInProcAct implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");

		System.out.println("아아, 여기는 AddrInProcAct");

		String maname = request.getParameter("maname");
		String mareceiver = request.getParameter("mareceiver");
		String p1 = request.getParameter("p1");
		String p2 = request.getParameter("p2");
		String p3 = request.getParameter("p3");
		String maphone = p1 + "-" + p2 + "-" + p3;
		String mazip = request.getParameter("mazip");
		String maaddr1 = request.getParameter("maaddr1");
		String maaddr2 = request.getParameter("maaddr2");
		String mabasic = "";
		if (request.getParameter("mabasic") != null && request.getParameter("mabasic").equals("y")) {
			mabasic = request.getParameter("mabasic");
		} else {
			mabasic = "n";
		}
		System.out.println(request.getParameter("mabasic"));
		
		HttpSession session = request.getSession();
		MemberInfo memberInfo = (MemberInfo)session.getAttribute("memberInfo");
		
		memberInfo.setMa_name(maname);
		memberInfo.setMa_receiver(mareceiver);
		memberInfo.setMa_phone(maphone);
		memberInfo.setMa_zip(mazip);
		memberInfo.setMa_addr1(maaddr1);
		memberInfo.setMa_addr2(maaddr2);
		memberInfo.setMa_basic(mabasic);
		
		AddrInProcSvc addrInProcSvc = new AddrInProcSvc();
		int result = addrInProcSvc.AddrInsert(memberInfo);
		
		if (result == 0) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('새로운 배송지 등록시 문제가 발생했습니다. \n다시 시도해 주세요.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		
		System.out.println("아아, 여기는 AddrInProcAct");
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(true);	// dispatch가 아닌 sendRedirect 방식으로 이동
		forward.setPath("addr_list.mem");
		
		return forward;
	}
}
