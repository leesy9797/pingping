package admin.act;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import svc.PdtViewSvc;
import vo.*;

public class AdminMemberInfoAct implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		//String id = request.getParameter("id");		// 상품ID
		//int cpage = Integer.parseInt(request.getParameter("cpage"));	// 현재 페이지 번호
		//int psize = Integer.parseInt(request.getParameter("psize"));	// 페이지 크기 & 목록보기 방식
		/*
		String tmpCpage = request.getParameter("cpage");
		int cpage = 1;
		if (tmpCpage != null && !tmpCpage.equals("")) cpage = Integer.parseInt(tmpCpage);
		String tmpPsize = request.getParameter("psize");
		int Psize = 12;
		if (tmpPsize != null && !tmpPsize.equals("")) Psize = Integer.parseInt(tmpPsize);
		*/
		// 검색조건(검색어, 대/소분류, 브랜드, 가격대) 및 정렬조건 쿼리스트링
		// 목록으로 돌아갈 때 필요함
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

		System.out.println("아아, 여기는 AdminMemberInfoAct");

		HttpSession session = request.getSession();
		AdminInfo AdminInfo = (AdminInfo)session.getAttribute("AdminInfo");
		
		AdminMemberInfoSvc adminMemberInfoSvc = new AdminMemberInfoSvc();
		MemberInfo memberInfo = adminMemberInfoSvc.getMemberInfo(email);
		
		request.setAttribute("memberInfo", memberInfo);
		

		ActionForward forward = new ActionForward();
		forward.setPath("/admin/member/member_info.jsp");
		// forward 인스턴스의 setPath() 메소드를 이용하여 forward 인스턴스의 path 멤버변수의 값을 지정
		
		return forward;
	}
	
}
