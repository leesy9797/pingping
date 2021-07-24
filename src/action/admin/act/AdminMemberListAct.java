package admin.act;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import vo.*;

public class AdminMemberListAct implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<MemberInfo> memberList = new ArrayList<MemberInfo>();

		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 15, bsize = 10, spage, epage, rcnt, pcnt;
		// 현재 페이지 번호, 한 페이지에서 보여줄 게시글 개수, 한 블록에서 보여줄 페이지 개수, 블록별 시작/종료 페이지 번호, 레코드 개수, 페이지 개수를 저장할 변수 초기화

		if (request.getParameter("cpage") != null) {
			cpage = Integer.parseInt(request.getParameter("cpage"));
		}	// 현재 페이지 번호가 있으면 정수형으로 형변환하여 저장
		
		
		//검색조건 쿼리스트링
		String email, nick, isactive, phone;
		String sage, eage, joinsdate, joinedate, gender, lastsdate, lastedate;
		
		email = request.getParameter("email");
		nick = request.getParameter("nick");
		isactive = request.getParameter("isactive");	// 활동/탈퇴여부(y, n)
		phone = request.getParameter("phone");
		sage = request.getParameter("sage");			// 나이 시작조건
		eage = request.getParameter("eage");			// 나이 종료조건
		joinsdate = request.getParameter("joinsdate");	
		joinedate = request.getParameter("joinedate");	
		gender = request.getParameter("gender");	
		lastsdate = request.getParameter("lastsdate");	// 마지막 로그인 일자 시작조건
		lastedate = request.getParameter("lastedate");	// 마지막 로그인 일자 종료조건
		
		Calendar today = Calendar.getInstance();
		int syear = 0;
		int eyear = 0;
		System.out.println(sage);
		if (sage != null && !sage.equals("")) {
			if (eage == null || eage.equals("")) {
				eage = "100";
			}
			syear = today.get(Calendar.YEAR) - Integer.parseInt(sage) + 1;
		}
		if (eage != null && !sage.equals("")) {
			if (sage == null || sage.equals("")) {
				sage = "0";
			}
			eyear = today.get(Calendar.YEAR) - Integer.parseInt(eage) + 1;
		}		
		
		// 검색조건에 따른 where절 생성(아래가 기본조건)
		String where = "where 1=1 ";
		
		if (!isEmpty(email))		where += " and mi_email like '%" + email + "%' ";
		else	email = "";
		if (!isEmpty(nick))			where += " and mi_nick like '%" + nick + "%' ";
		else	nick = "";
		if (!isEmpty(isactive))		where += " and mi_isactive = '" + isactive + "' ";
		else	isactive = "";
		if (!isEmpty(phone))		where += " and mi_phone like '%" + phone + "%' ";
		else	phone = "";
		if (!isEmpty(sage))			where += " and left(mi_birth, 4) <= '" + syear + "' ";
		else	sage = "";
		if (!isEmpty(eage))			where += " and left(mi_birth, 4) >= '" + eyear + "' ";
		else	eage = "";
		if (!isEmpty(joinsdate))	where += " and mi_joindate >= '" + joinsdate + " 00:00:00' ";
		else	joinsdate = "";
		if (!isEmpty(joinedate))	where += " and mi_joindate <= '" + joinedate + " 23:59:59' ";
		else	joinedate = "";
		if (!isEmpty(gender))		where += " and mi_gender = '" + gender + "' ";
		else	gender = "";
		if (!isEmpty(lastsdate))	where += " and mi_lastlogin >= '" + lastsdate + " 00:00:00' ";
		else	lastsdate = "";
		if (!isEmpty(lastedate))	where += " and mi_lastlogin <= '" + lastedate + " 23:59:59' ";
		else	lastedate = "";
		
		
		// 정렬조건(판매량(인기순) - 내림차순, 가격 - 오름차순/내림차순, 등록 - 내림차순(기본값), 상품명 - 오름차순, 리뷰 - 내림차순) 쿼리스트링
		String ord = request.getParameter("ord");
		if (ord == null || ord.equals("")) {
			ord = "joindated";	// 등록 역순으로 정렬이 기본값
		}
		// 정렬조건에 따른 order by절 생성
		String orderBy = " order by mi_" + ord.substring(0, ord.length() - 1) + 	// idd라면 desc을 의미하는 d를 빼고 가져옴
			(ord.charAt(ord.length() - 1) == 'a' ? " asc" : " desc");	// idd의  desc을 의미하는 d를 가져(가장 마지막 글자)
		
		AdminMemberListSvc adminMemberListSvc = new AdminMemberListSvc();
		rcnt = adminMemberListSvc.getMemberCount(where);	// 검색된 상품의 총 개수(페이지 개수를 구하기 위해 필요)		
		
		pcnt = rcnt / psize;
		if (rcnt % psize > 0)	pcnt++;		// 전체 페이지 수
		spage = (cpage - 1) / bsize * bsize + 1;	// 블록 시작 페이지 번호
		epage = spage + bsize - 1;
		if (epage > pcnt)	epage = pcnt;			// 블록 종료 페이지 번호
		
		
		MemberPageInfo pageInfo = new MemberPageInfo();	// 페이징에 필요한 정보들을 저장할 인스턴스
		// 페이징에 필요한 정보들
		pageInfo.setCpage(cpage);		// 현재 페이지 번호
		pageInfo.setRcnt(rcnt);			// 전체 게시글 개수
		pageInfo.setPcnt(pcnt);			// 전체 페이지 개수
		pageInfo.setSpage(spage);		// 블록 시작 페이지 번호
		pageInfo.setEpage(epage);		// 블록 종료 페이지 번호 
		pageInfo.setPsize(psize);		// 페이지 크기
		pageInfo.setBsize(bsize);		// 블록 크기
		 
		// 검색 관련 정보들
		pageInfo.setEmail(email);	
		pageInfo.setNick(nick);	
		pageInfo.setPhone(phone);	
		pageInfo.setIsactive(isactive);	
		pageInfo.setSage(sage);	
		pageInfo.setEage(eage);		
		pageInfo.setJoinsdate(joinsdate);		// 가입일자 중 시작일
		pageInfo.setJoinedate(joinedate);		// 가입일자 중 종료일
		pageInfo.setGender(gender);		
		pageInfo.setLastsdate(lastsdate);		// 마지막 로그인 일자 중 시작일	
		pageInfo.setLastedate(lastedate);		// 마지막 로그인 일자 중 종료일
		pageInfo.setOrd(ord);
		

		memberList = adminMemberListSvc.getMemberList(where, orderBy, cpage, psize);
		// 현 페이지(cpage)에서 보여줄 검색된 상품 목록을 받아옴	
		
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("memberList", memberList);

		System.out.println("아아, 여기는 AdminMemberListAct");
		
		ActionForward forward = new ActionForward();
		forward.setPath("/admin/member/member_list.jsp");
		// forward 인스턴스의 setPath() 메소드를 이용하여 forward 인스턴스의 path 멤버변수의 값을 지정
		
		return forward;
	}
	
	private boolean isEmpty(String str) {	// 매개변수에 어떤 값이든 들어있는지 여부를 검사하는 메소드
		boolean empty = true;
		
		if (str != null && !str.equals("")) {
			empty = false;
		}
		
		return empty;
	}
	
}
