package action;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import svc.*;
import vo.*;

public class OrderListAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<CartInfo> orderList = new ArrayList<CartInfo>();
		
		request.setCharacterEncoding("utf-8");
		/*
		HttpSession session = request.getSession();
		MemberInfo memberInfo = (MemberInfo)session.getAttribute("memberInfo");
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		if (memberInfo == null) {
			out.println("<script>");
			out.println("alert('로그인 후에 이용하실 수 있습니다.');");
			out.println("../login_form.jsp");
			out.println("</script>");
			out.close();
		}
		*/
		int cpage = 1, psize = 10, bsize = 10, spage, epage, rcnt, pcnt, cnt;
		// 현재 페이지 번호, 한 페이지에서 보여줄 게시글 개수, 한 블록에서 보여줄 페이지 개수, 블록별 시작/종료 페이지 번호, 레코드 개수, 페이지 개수를 저장할 변수 초기화

		String sdate = "", edate = "", status = "", schdate = "";
		
		if (request.getParameter("cpage") != null) {
			cpage = Integer.parseInt(request.getParameter("cpage"));
		}	// 현재 페이지 번호가 있으면 정수형으로 형변환하여 저장
		if (request.getParameter("psize") != null) {
			psize = Integer.parseInt(request.getParameter("psize"));
		}	// 현재 페이지 번호가 있으면 정수형으로 형변환하여 저장
		
		if (request.getParameter("sdate") != null) {
			sdate = request.getParameter("sdate");
		}	
		if (request.getParameter("edate") != null) {
			edate = request.getParameter("edate");
		}	
		if (request.getParameter("status") != null) {
			status = request.getParameter("status");
		}	
		if (request.getParameter("schdate") != null) {
			schdate = request.getParameter("schdate");
		}	
		
		/*
		String sdate, edate, status, schdate = null;
		
		sdate = request.getParameter("sdate");	
		edate = request.getParameter("edate");	
		status = request.getParameter("status");	
		
		
		int n = 0;
		if (request.getParameter("status") != null) {
			n = request.getParameter("schdate").indexOf("개");
			schdate = request.getParameter("schdate").substring(0, n);
		}

		System.out.println("schdate는" + schdate);
		
		Calendar today = Calendar.getInstance();
		int year = today.get(Calendar.YEAR);
		int month = today.get(Calendar.MONTH) + 1;
		int day = today.get(Calendar.DATE);
		//System.out.println("sdate는" + sdate);
		//System.out.println(year);
		//System.out.println(month);
		//System.out.println(day);
		
		int tmp = 0;
		if (schdate != null && !schdate.equals("")) {
			tmp = month - Integer.parseInt(schdate);
			System.out.println(tmp);
			if (tmp < 1 && tmp > -11) {
				year -= 1;
				month = 12 + tmp;
			} else if (tmp < 1 && tmp > -23) { 
				year -= 2;
				month = 24 + tmp;
			} else if (tmp < 1 && tmp > -35) { 
				year -= 3;
				month = 36 + tmp;
			} else {
				month = tmp;
			}
			sdate = year + "-" + ((month < 10) ? "0" + month : month) + "-" + ((day < 10) ? "0" + day : day);
		}
		//System.out.println(year);
		//System.out.println(month);
		//System.out.println(day);
		*/
		
		
		// 검색조건에 따른 where절 생성(아래가 기본조건)
		//String where = " where a.cb_id = b.cb_id and a.cs_id = c.cs_id and b.cb_id = c.cb_id and pi_isview = 'y' ";
		String where = " where a.oi_id = b.oi_id and b.pi_id = c.pi_id ";
		String where2 = " where 1=1 ";
		if (!isEmpty(sdate))	{
			where += " and a.oi_date >= '" + sdate + " 00:00:00' ";
			where2 += " and oi_date >= '" + sdate + " 00:00:00' ";
		}
		else					sdate = "";
		if (!isEmpty(edate))	{
			where += " and a.oi_date <= '" + edate + " 23:59:59' ";
			where2 += " and oi_date <= '" + edate + " 23:59:59' ";
		}
		else					edate = "";		
		if (!isEmpty(status))	{
			where += " and a.oi_status = '" + status + "' ";
			where2 += " and oi_status = '" + status + "' ";
		}
		else					status = "";		

		OrderListSvc orderListSvc = new OrderListSvc();

		HttpSession session = request.getSession();
		MemberInfo memberInfo = (MemberInfo)session.getAttribute("memberInfo");

		rcnt = orderListSvc.getOrderCount(memberInfo.getMi_email());			// 회원의 주문목록 총 개수(페이지 개수를 구하기 위해 필요)
		cnt = orderListSvc.getOrderListCount(memberInfo.getMi_email(), where2);	// 회원의 주문목록 총 개수(페이지 개수를 구하기 위해 필요)
		//statuscnt = orderListSvc.getStatusCount()
		orderList = orderListSvc.getOrderList(memberInfo.getMi_email(), where, cpage, psize);
		pcnt = rcnt / psize;
		System.out.println("rcnt" + rcnt);
		System.out.println("pcnt" + pcnt);
		System.out.println("cnt" + cnt);
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
		pageInfo.setCnt(cnt);			// 주문 목록 개수	
		
		// 검색 관련 정보들
		pageInfo.setSdate(sdate);		
		pageInfo.setEdate(edate);	
		pageInfo.setOistatus(status);		
		pageInfo.setSchdate(schdate);		
				

		OrderStatusInfo statusInfo = new OrderStatusInfo();	// 페이징에 필요한 정보들을 저장할 인스턴스
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("orderList", orderList);
		request.setAttribute("statusInfo", statusInfo);

		System.out.println("아아, 여기는 OrderListAct");
		
		ActionForward forward = new ActionForward();
		forward.setPath("/order/order_list.jsp");
		// forward 인스턴스의 setPath() 메소드를 이용하여 forward 인스턴스의 path 멤버변수의 값을 지정
		
		return forward;
	}
	
	private boolean isEmpty(String str) {
	// 매개변수에 어떤 값이든 들어있는지 여부를 검사하는 메소드 -> 검색 안해서 안씀
		boolean empty = true;
		
		if (str != null && !str.equals("")) {
			empty = false;
		}
		
		return empty;
	}

}
