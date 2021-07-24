package action;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import svc.*;
import vo.*;

public class CampReplyProcAct implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		String wtype = request.getParameter("wtype");
		String idx = request.getParameter("idx");	

		int cpage = Integer.parseInt(request.getParameter("cpage"));
		
		System.out.println(wtype);
		System.out.println(idx);
		String rcontent = "";
		if (wtype.equals("in")) {
			rcontent	= request.getParameter("rcontent");	
		}
		
		CampReplyInfo replyInfo = new CampReplyInfo();
		replyInfo.setCr_idx(Integer.parseInt(idx));
		replyInfo.setCrr_content(rcontent);
		if (wtype.equals("up") || wtype.equals("del")) {
			//replyInfo.setCrr_idx(crridx);
		}
		
		CampReplyProcSvc CampReplyProcSvc = new CampReplyProcSvc();
		boolean isSuccess = false;
		String failMsg = "";
		String lnk = "camp_view.camp?idx=" + idx;
		HttpSession session = request.getSession();
		MemberInfo memberInfo = (MemberInfo)session.getAttribute("memberInfo");
		replyInfo.setMi_email(memberInfo.getMi_email());	// �α����� ȸ���� �̸��� ����
		replyInfo.setCrr_nick(memberInfo.getMi_nick());	// �α����� ȸ���� ���� ����
		
		if (wtype.equals("in")) {	// ���� ����� ���
			isSuccess = CampReplyProcSvc.CampReplyInsert(replyInfo);
			failMsg = "��� ��Ͽ� �����߽��ϴ�.";
		} else {	// ���� ������ ���
			isSuccess = CampReplyProcSvc.CampReplyUpdate(replyInfo);
			failMsg = "���� ������ �����߽��ϴ�.";
		}
		if (!isSuccess) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('" + failMsg + "');");
			out.println("</script>");
			out.close();
		}

		System.out.println("����� CampReplyProcAct");
		System.out.println(lnk + "&cpage=" + cpage + "&ord=idx&psize=12");
		
		ActionForward forward = new ActionForward();
		//forward.setRedirect(true);	// redirect ������� �̵��ϵ��� ����
		forward.setPath(lnk + "&cpage=" + cpage + "&ord=idx&psize=12");
		
		return forward;
	}
}
