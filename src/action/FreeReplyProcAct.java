package action;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import svc.*;
import vo.*;

public class FreeReplyProcAct implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		String wtype	= request.getParameter("wtype");	// ���� ���/���� ����
		
		String idx	= "";
		if (wtype.equals("up")) {
			idx	= request.getParameter("idx");	// ���� ������ ��� ����� idx
		}
		String rcontent	= request.getParameter("rcontent");
		
		
		FreeReplyInfo replyInfo = new FreeReplyInfo();
		if (wtype.equals("up")) {
			replyInfo.setBf_idx(Integer.parseInt(idx));	// ������ ��쿡�� ���ǰ�, ����� ��� ������� ����
		}
		replyInfo.setBfr_content(rcontent);
		
		FreeReplyProcSvc freeReplyProcSvc = new FreeReplyProcSvc();
		boolean isSuccess = false;
		String failMsg = "";
		String lnk = "free_view.free?idx=" + idx;
		HttpSession session = request.getSession();
		MemberInfo memberInfo = (MemberInfo)session.getAttribute("memberInfo");
		replyInfo.setMi_email(memberInfo.getMi_email());	// �α����� ȸ���� �̸��� ����
		replyInfo.setBfr_nick(memberInfo.getMi_nick());	// �α����� ȸ���� ���� ����
		
		if (wtype.equals("in")) {	// ���� ����� ���
			isSuccess = FreeReplyProcSvc.freeReplyInsert(replyInfo);
			failMsg = "��� ��Ͽ� �����߽��ϴ�.";
		} else {	// ���� ������ ���
			isSuccess = FreeReplyProcSvc.freeReplyUpdate(replyInfo);
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
		
		// ������ ������ �˻����� ������
		int cpage = 1;
		if (wtype.equals("up")) {
			cpage = Integer.parseInt(request.getParameter("cpage"));
		}
		String keyword	= request.getParameter("keyword");	// �˻���
		// �˻����� ������Ʈ�� ����
		String args = "";
		if (keyword == null)	args += "&keyword=";	
		else	args += "&keyword=" + keyword;
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(true);	// redirect ������� �̵��ϵ��� ����
		forward.setPath(lnk + "cpage=" + cpage + args);
		
		return forward;
	}
}
