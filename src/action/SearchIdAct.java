package action;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import svc.*;
import vo.*;

public class SearchIdAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");

		System.out.println("아아, 여기는 SearchIdAct");
		
		MemberInfo memberInfo = null;
		String uname = "", p1 = "", p2 = "", p3 = "";
		if (request.getParameter("uname") != null) {
			uname = request.getParameter("uname").trim();
		}	

		p1 = request.getParameter("p1");
		
		if (request.getParameter("p2") != null) {
			p2 = request.getParameter("p2").trim();
		}	
		if (request.getParameter("p3") != null) {
			p3 = request.getParameter("p3").trim();
		}	
		String phone = p1 + "-" + p2 + "-" + p3;
		
		/*
		String uname = request.getParameter("uname");
		String p1 = request.getParameter("p1");
		String p2 = request.getParameter("p2");
		String p3 = request.getParameter("p3");
		String phone = p1 + "-" + p2 + "-" + p3;
		*/
		
		memberInfo = new MemberInfo();
		memberInfo.setMi_name(uname);
		memberInfo.setMi_phone(phone);
		
		SearchIdSvc searchIdSvc = new SearchIdSvc();
		
		memberInfo = searchIdSvc.searchId(uname, phone);
		request.setAttribute("memberInfo", memberInfo);

		System.out.println("아아, 여기는 SearchIdAct");
		System.out.println(memberInfo.getMi_email());
		
		ActionForward forward = new ActionForward();
		//forward.setRedirect(true);	// dispatch가 아닌 sendRedirect 방식으로 이동
		forward.setPath("show_id.jsp");
		
		return forward;
	
	}
}
