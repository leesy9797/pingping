package admin.act;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import vo.*;

public class AdminOrderListAct implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<CartInfo> cartList = new ArrayList<CartInfo>();
		
		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 15, bsize = 10, spage, epage, rcnt, pcnt;
		// 현재 페이지 번호, 한 페이지에서 보여줄 게시글 개수, 한 블록에서 보여줄 페이지 개수, 블록별 시작/종료 페이지 번호, 레코드 개수, 페이지 개수를 저장할 변수 초기화

		System.out.println("아아, 여기는 AdminOrderListAct");
		
		if (request.getParameter("cpage") != null) {
			cpage = Integer.parseInt(request.getParameter("cpage"));
		}	// 현재 페이지 번호가 있으면 정수형으로 형변환하여 저장
		
		// 검색조건(주문번호, 주문자이메일, 주문자이름, 주문자전화번호 주문일 시작, 주문일 종료) 쿼리스트링
		String oiid, miemail, miname, miphone, sdate, edate;	//oistatus, 
		oiid = request.getParameter("oiid");	
		miemail = request.getParameter("miemail");
		miname= request.getParameter("miname");
		miphone= request.getParameter("miphone");
		//oistatus = request.getParameter("oistatus");
		sdate = request.getParameter("sdate");	
		edate = request.getParameter("edate");	
		String oistatuses[] = null;
		String path = "";
		if (request.getParameter("path") != null) {
			path = request.getParameter("path");
		}
		
		if (request.getParameterValues("oistatuses") != null) {
			oistatuses = request.getParameterValues("oistatuses");
			Arrays.toString(oistatuses);
		}
		
		System.out.println("액트" + Arrays.toString(oistatuses));
		
		// 검색조건에 따른 where절 생성(아래가 기본조건)
		//String where = " where a.cs_id = c.cs_id and b.cb_id = c.cb_id and a.b_id = d.b_id ";
		String where = " where a.mi_email = b.mi_email ";
		if (!isEmpty(oiid))		where += " and a.oi_id like '%" + oiid + "%' ";
		else	oiid = "";
		if (!isEmpty(miemail))	where += " and a.mi_email like '%" + miemail + "%' ";
		else	miemail = "";
		if (!isEmpty(miname))	where += " and b.mi_name like '%" + miname + "%' ";
		else	miname = "";
		if (!isEmpty(miphone))	where += " and b.mi_phone like '%" + miphone + "%' ";
		else	miphone = "";
		//if (!isEmpty(oistatus))	where += " and a.oi_status = '" + oistatus + "' ";
		//else	oistatus = "";
		if (!isEmpty(sdate))	where += " and a.oi_date >= '" + sdate + " 00:00:00' ";
		else	sdate = "";
		if (!isEmpty(edate))	where += " and a.oi_date <= '" + edate + " 23:59:59' ";
		else	edate = "";
		

		String status = "";
		String tmpWhere = "";
		if (oistatuses == null) 	where += "";
		else {
			
			for (int i = 0 ; i < oistatuses.length ; i++) {
				tmpWhere += " or a.oi_status = '" + oistatuses[i] + "' ";
				status += "/" + oistatuses[i];
			}
			
			where += " and (" + tmpWhere.substring(4) + ") ";
			status = status.substring(1);
			System.out.println("액트2" + status);
			//for (String status : oistatuses) {
				//where += " and a.oi_status = '" + status + "' ";
			//}
		}

		System.out.println("액트" + where);
		AdminOrderListSvc adminOrderListSvc = new AdminOrderListSvc();
		rcnt = adminOrderListSvc.getOrderCount(where);	// 검색된 상품의 총 개수(페이지 개수를 구하기 위해 필요)
		
		pcnt = rcnt / psize;
		if (rcnt % psize > 0)	pcnt++;		// 전체 페이지 수
		spage = (cpage - 1) / bsize * bsize + 1;	// 블록 시작 페이지 번호
		epage = spage + bsize - 1;
		if (epage > pcnt)	epage = pcnt;			// 블록 종료 페이지 번호
		
		OrderPageInfo pageInfo = new OrderPageInfo();	// 페이징에 필요한 정보들을 저장할 인스턴스
		// 페이징에 필요한 정보들
		pageInfo.setCpage(cpage);		// 현재 페이지 번호
		pageInfo.setRcnt(rcnt);			// 전체 게시글 개수
		pageInfo.setPcnt(pcnt);			// 전체 페이지 개수
		pageInfo.setSpage(spage);		// 블록 시작 페이지 번호
		pageInfo.setEpage(epage);		// 블록 종료 페이지 번호 
		pageInfo.setPsize(psize);		// 페이지 크기
		pageInfo.setBsize(bsize);		// 블록 크기
		 
		// 검색 관련 정보들
		pageInfo.setOiid(oiid);			// 주문번호
		pageInfo.setMiemail(miemail);	// 주문자이메일
		pageInfo.setMiname(miname);		// 주문자이름
		pageInfo.setMiphone(miphone);	// 주문자전화번호
		pageInfo.setSdate(sdate);		// 주문기간 중 시작일
		pageInfo.setEdate(edate);		// 주문기간 중 종료일
		pageInfo.setOistatuses(oistatuses);	
		pageInfo.setOistatus(status);
		
		cartList = adminOrderListSvc.getOrderList(where, cpage, psize);
		// 현 페이지(cpage)에서 보여줄 검색된 상품 목록을 받아옴
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("cartList", cartList);
		
		System.out.println("아아, 여기는 AdminOrderListAct");
		
		ActionForward forward = new ActionForward();
		forward.setPath("/admin/order/order_list.jsp?path=" + path);
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
