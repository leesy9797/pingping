package action;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import svc.*;
import vo.*;

public class CampInFormAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");

		System.out.println("�ƾ�, ����� CampInFormAct");
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(true);	// dispatch�� �ƴ� sendRedirect ������� �̵�
		forward.setPath("camp_in_form.jsp");
		
		return forward;
	}
}