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
		      String miemail = "";	// 이거 뭐지
		      
		      AddrDelSvc addrDelSvc = new AddrDelSvc();
		      int result = addrDelSvc.addrDelete(maidx, miemail);
		      
		      if (result == 0) {	// �� ������ ����������
		    	  response.setContentType("text/html; charset=utf-8");
		    	  PrintWriter out = response.getWriter();
		    	  out.println("<script>");
		    	  out.println("alert('�ּ� ������ �����߽��ϴ�.\n�ٽ� �õ��� �ֽʽÿ�.');");
		    	  out.println("history.back();");
		    	  out.println("<script>");
		    	  out.close();	// ���⼭ ����(�� ��Ͽ� �������� ��, ��������(�� ���� ����)���� ������� �ʰ�)
		      }
		      
		      ActionForward forward = new ActionForward();
		      forward.setRedirect(true);   
		      // dispatch�� �ƴ� sendRedirect ������� �̵�(sendRedirect������� �ؾ� url�� �ٲ� : �ٲ��� ������ ���ΰ�ħ�� �� �ٽ� ��� or ��������)
		      forward.setPath("addr_list.addr?cpage=1");
		      // �Խñ� ���� ȭ������ �ݹ� �Էµ� �� ��ȣ�� ������ �̵���
		      
		      return forward;
		   }
	}
