package admin.act;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import svc.PdtViewSvc;
import vo.*;

public class AdminMemberInfoAct implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		//String id = request.getParameter("id");		// ��ǰID
		//int cpage = Integer.parseInt(request.getParameter("cpage"));	// ���� ������ ��ȣ
		//int psize = Integer.parseInt(request.getParameter("psize"));	// ������ ũ�� & ��Ϻ��� ���
		/*
		String tmpCpage = request.getParameter("cpage");
		int cpage = 1;
		if (tmpCpage != null && !tmpCpage.equals("")) cpage = Integer.parseInt(tmpCpage);
		String tmpPsize = request.getParameter("psize");
		int Psize = 12;
		if (tmpPsize != null && !tmpPsize.equals("")) Psize = Integer.parseInt(tmpPsize);
		*/
		// �˻�����(�˻���, ��/�Һз�, �귣��, ���ݴ�) �� �������� ������Ʈ��
		// ������� ���ư� �� �ʿ���
		String email, nick, phone, gender, isactive, sage, eage;
		String joinsdate, joinedate, lastsdate, lastedate;
		email 		= request.getParameter("email");	
		nick		= request.getParameter("nick");	
		phone		= request.getParameter("phone");	
		gender		= request.getParameter("gender");	
		isactive	= request.getParameter("isactive");	
		sage		= request.getParameter("sage");	
		eage 		= request.getParameter("eage");		
		joinsdate	= request.getParameter("joinsdate");	
		joinedate	= request.getParameter("joinedate");	
		lastsdate	= request.getParameter("lastsdate");
		lastedate 	= request.getParameter("lastedate");	

		System.out.println("�ƾ�, ����� AdminMemberInfoAct");

		HttpSession session = request.getSession();
		AdminInfo AdminInfo = (AdminInfo)session.getAttribute("AdminInfo");
		
		AdminMemberInfoSvc adminMemberInfoSvc = new AdminMemberInfoSvc();
		MemberInfo memberInfo = adminMemberInfoSvc.getMemberInfo(email);
		
		request.setAttribute("memberInfo", memberInfo);
		

		ActionForward forward = new ActionForward();
		forward.setPath("/admin/member/member_info.jsp");
		// forward �ν��Ͻ��� setPath() �޼ҵ带 �̿��Ͽ� forward �ν��Ͻ��� path ��������� ���� ����
		
		return forward;
	}
	
}
