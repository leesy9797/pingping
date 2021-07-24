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
		replyInfo.setMi_email(memberInfo.getMi_email());	// 로그인한 회원의 이메일 저장
		replyInfo.setCrr_nick(memberInfo.getMi_nick());	// 로그인한 회원의 별명 저장
		
		if (wtype.equals("in")) {	// 질문 등록일 경우
			isSuccess = CampReplyProcSvc.CampReplyInsert(replyInfo);
			failMsg = "댓글 등록에 실패했습니다.";
		} else {	// 질문 수정일 경우
			isSuccess = CampReplyProcSvc.CampReplyUpdate(replyInfo);
			failMsg = "질문 수정에 실패했습니다.";
		}
		if (!isSuccess) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('" + failMsg + "');");
			out.println("</script>");
			out.close();
		}

		System.out.println("여기는 CampReplyProcAct");
		System.out.println(lnk + "&cpage=" + cpage + "&ord=idx&psize=12");
		
		ActionForward forward = new ActionForward();
		//forward.setRedirect(true);	// redirect 방식으로 이동하도록 지정
		forward.setPath(lnk + "&cpage=" + cpage + "&ord=idx&psize=12");
		
		return forward;
	}
}
