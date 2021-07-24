package action;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import svc.*;
import vo.*;

public class AddrDelProcAct implements Action {
	   public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		      request.setCharacterEncoding("utf-8");
		      int maidx = Integer.parseInt(request.getParameter("maidx"));
		      
		      AddrProcSvc addrProcSvc = new AddrProcSvc();
		      int result = addrProcSvc.addrDelete(maidx);
		      
		      if (result == 0) {	// 글 삭제에 실패했으면
		    	  response.setContentType("text/html; charset=utf-8");
		    	  PrintWriter out = response.getWriter();
		    	  out.println("<script>");
		    	  out.println("alert('주소 삭제에 실패했습니다.\n다시 시도해 주십시오.');");
		    	  out.println("history.back();");
		    	  out.println("<script>");
		    	  out.close();	// 여기서 끝냄(글 등록에 실패했을 때, 다음과정(글 내용 보기)으로 진행되지 않게)
		      }
		      
		      ActionForward forward = new ActionForward();
		      forward.setRedirect(true);   
		      // dispatch가 아닌 sendRedirect 방식으로 이동(sendRedirect방식으로 해야 url이 바뀜 : 바뀌지 않으면 새로고침할 때 다시 등록 or 수정실행)
		      forward.setPath("addr_list.addr?cpage=1");
		      // 게시글 보기 화면으로 금방 입력된 글 번호를 가지고 이동함
		      
		      return forward;
		   }
	}
