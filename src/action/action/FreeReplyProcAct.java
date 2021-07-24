package action;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import svc.*;
import vo.*;

public class FreeReplyProcAct implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		String wtype	= request.getParameter("wtype");	// 질문 등록/수정 여부
		
		String idx	= "";
		if (wtype.equals("up")) {
			idx	= request.getParameter("idx");	// 질문 수정일 경우 사용할 idx
		}
		String rcontent	= request.getParameter("rcontent");
		
		
		FreeReplyInfo replyInfo = new FreeReplyInfo();
		if (wtype.equals("up")) {
			replyInfo.setBf_idx(Integer.parseInt(idx));	// 수정일 경우에만 사용되고, 등록일 경우 사용하지 않음
		}
		replyInfo.setBfr_content(rcontent);
		
		FreeReplyProcSvc freeReplyProcSvc = new FreeReplyProcSvc();
		boolean isSuccess = false;
		String failMsg = "";
		String lnk = "free_view.free?idx=" + idx;
		HttpSession session = request.getSession();
		MemberInfo memberInfo = (MemberInfo)session.getAttribute("memberInfo");
		replyInfo.setMi_email(memberInfo.getMi_email());	// 로그인한 회원의 이메일 저장
		replyInfo.setBfr_nick(memberInfo.getMi_nick());	// 로그인한 회원의 별명 저장
		
		if (wtype.equals("in")) {	// 질문 등록일 경우
			isSuccess = FreeReplyProcSvc.freeReplyInsert(replyInfo);
			failMsg = "댓글 등록에 실패했습니다.";
		} else {	// 질문 수정일 경우
			isSuccess = FreeReplyProcSvc.freeReplyUpdate(replyInfo);
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
		
		// 페이지 정보과 검색관련 정보들
		int cpage = 1;
		if (wtype.equals("up")) {
			cpage = Integer.parseInt(request.getParameter("cpage"));
		}
		String keyword	= request.getParameter("keyword");	// 검색어
		// 검색관련 쿼리스트링 제작
		String args = "";
		if (keyword == null)	args += "&keyword=";	
		else	args += "&keyword=" + keyword;
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(true);	// redirect 방식으로 이동하도록 지정
		forward.setPath(lnk + "cpage=" + cpage + args);
		
		return forward;
	}
}
