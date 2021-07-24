package action;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import svc.*;
import vo.*;

public class MemberProcAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String wtype = request.getParameter("wtype");
		
		MemberInfo memberInfo = null;
		HttpSession session = request.getSession();
		MemberInfo tmpMemberInfo = (MemberInfo)session.getAttribute("memberInfo");

		if (wtype.equals("in") || wtype.equals("up")) {
			memberInfo = new MemberInfo();
			
			if (wtype.equals("in")) {
				System.out.println("아아, 여기는 MemberProcAct");
				memberInfo.setMi_email(request.getParameter("mi_email"));
				memberInfo.setMi_name(request.getParameter("mi_name"));
				memberInfo.setMi_nick(request.getParameter("mi_nick"));
				memberInfo.setMi_birth(request.getParameter("by") + "-" + request.getParameter("bm") + "-" + request.getParameter("bd"));
				memberInfo.setMi_gender(request.getParameter("mi_gender"));

			} else {
				memberInfo.setMi_email(tmpMemberInfo.getMi_email());				
				
				if (!tmpMemberInfo.getMi_pwd().equals(request.getParameter("old_pwd"))) { 
					response.setContentType("text/html; charset=utf-8");
					PrintWriter out = response.getWriter();
					
					out.println("<script>");
					out.println("alert('이전 비밀번호가 틀렸습니다.');");
					out.println("history.back();");
					out.println("</script>");
					out.close();
				}
				
				if (request.getParameter("mi_pwd").trim() != null && !request.getParameter("mi_pwd").trim().equals("")) {
					if (request.getParameter("mi_pwd").trim().equals(request.getParameter("mi_pwd2").trim())) {
						memberInfo.setMi_pwd(request.getParameter("mi_pwd").trim());
					}
				} else {
					memberInfo.setMi_pwd(request.getParameter("old_pwd").trim());
				}			
				
				memberInfo.setMi_name(tmpMemberInfo.getMi_name());
				memberInfo.setMi_nick(request.getParameter("mi_nick").trim());
				memberInfo.setMi_birth(tmpMemberInfo.getMi_birth());
				memberInfo.setMi_gender(tmpMemberInfo.getMi_gender());
			}
			
			memberInfo.setMi_phone(request.getParameter("p1") + "-" + 
				request.getParameter("p2").trim() + "-" + request.getParameter("p3").trim());
			memberInfo.setMi_issms(request.getParameter("mi_issms"));
			memberInfo.setMi_isemail(request.getParameter("mi_isemail"));
		}
			
		MemberProcSvc memberProcSvc = new MemberProcSvc();
		int result = 0;
		String lnk = "../index.jsp";
		switch (wtype) {
			case "in" :		
				result = memberProcSvc.memberProc(memberInfo, wtype);
				lnk = "../login_form.jsp";
				break;
			case "up" :		
				result = memberProcSvc.memberProc(memberInfo, wtype);
				if (!memberInfo.getMi_pwd().equals("")) {
					tmpMemberInfo.setMi_pwd(memberInfo.getMi_pwd());
				}
				tmpMemberInfo.setMi_nick(memberInfo.getMi_nick());
				tmpMemberInfo.setMi_phone(memberInfo.getMi_phone());
				tmpMemberInfo.setMi_issms(memberInfo.getMi_issms());
				tmpMemberInfo.setMi_isemail(memberInfo.getMi_isemail());
				break;
			case "del" :	
				result = memberProcSvc.memberDelete(tmpMemberInfo.getMi_email());
				lnk = "../index.jsp";
				break;
		}
		
		System.out.println("아아, 여기는 MemberProcAct");

		ActionForward forward = new ActionForward();
		forward.setRedirect(true);	// dispatch가 아닌 sendRedirect 방식으로 이동
		forward.setPath(lnk);

		
		
		return forward;
		
		
	}
}
