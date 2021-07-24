package action;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import svc.*;
import vo.*;

public class CartUpProcAct implements Action {
	// Ư�� ��ǰ�� ��ٱ��Ͽ� ��� �۾��� ó���ϴ� Ŭ����
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");

		String kind = request.getParameter("kind");	// ����(cnt) or �ɼ�(opt) 
		String op = request.getParameter("op");		// �����̸� ������, �ɼ��̸� �ɼǰ�
		int idx = Integer.parseInt(request.getParameter("idx"));	// īƮ �ε��� ��ȣ(PK)
		String piid = request.getParameter("piid");	// ��ǰ ID�� �ɼ� ����� �ʿ�		
		
		HttpSession session = request.getSession();
		MemberInfo memberInfo = (MemberInfo)session.getAttribute("memberInfo");
		
		CartUpProcSvc cartUpProcSvc = new CartUpProcSvc();
		int result = cartUpProcSvc.cartUpdate(kind, op, idx, memberInfo.getMi_email(), piid);
						
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(result);
		out.close();
		
		System.out.println("�ƾ�, ����� CartUpProcAct");
		
		return null;
	}
}
