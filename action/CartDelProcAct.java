package action;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import svc.*;
import vo.*;

public class CartDelProcAct implements Action {
// Ư�� ��ǰ�� ��ٱ��Ͽ� ��� �۾��� ó���ϴ� Ŭ����
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");

		String kind = request.getParameter("kind");	// ��ü(0), �Ϻ�(-1), �ϳ��� ���� ����
		String idx = request.getParameter("idx");	// ������ īƮ �ε��� ��ȣ(��)
		
		HttpSession session = request.getSession();
		MemberInfo memberInfo = (MemberInfo)session.getAttribute("memberInfo");
		
		String where = " where mi_email = '" + memberInfo.getMi_email() + "' ";
		String tmpWhere = "";
		if (kind.equals("-1")) {	// ������ ��ǰ(��)�� ������ ���
			String[] arr = idx.split(",");
			for (int i = 0 ; i < arr.length ; i++) {
				tmpWhere += " or oc_idx = " + arr[i];  
			}
			tmpWhere = tmpWhere.substring(4);
			where += " and (" + tmpWhere + ")";
			
		} else if (!kind.equals("0")) {	// �� ��ǰ�� ������ ���
			where += " and oc_idx = " + idx;
		}
		
		//System.out.println(where);
		
		CartDelProcSvc cartDelProcSvc = new CartDelProcSvc();
		int result = cartDelProcSvc.cartDelete(where);
						
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(result);
		out.close();
		
		System.out.println("�ƾ�, ����� CartDelProcAct");
		
		return null;
	}
}
