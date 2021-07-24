package action;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import svc.*;
import vo.*;

public class OrderProcAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");

		HttpSession session = request.getSession();
		MemberInfo memberInfo = (MemberInfo)session.getAttribute("memberInfo");

		System.out.println("아아, 여기는 OrderProcAct");
		
		String kind = request.getParameter("kind");	// 수량(cnt) or 옵션(opt) 
		//System.out.println(kind);
		
		int total = Integer.parseInt((request.getParameter("total") != null) ? request.getParameter("total") : "0");
		int delipay = Integer.parseInt(request.getParameter("delipay"));	
		int pdtprice = Integer.parseInt(request.getParameter("pdtprice"));	
		/* 
		System.out.println(total);
		System.out.println(delipay);
		System.out.println(pdtprice);
		*/
		
		int oiusepoint = Integer.parseInt((request.getParameter("oipoint") != null) ? request.getParameter("oipoint") : "0");
		String oipayment = request.getParameter("payment");
		String oistatus = "b"; // oipayment.equals("b") ? "a" : "b";
		/*
		System.out.println(oiusepoint);
		System.out.println(oipayment);
		System.out.println(oistatus);
		*/
		
		String oiname = request.getParameter("miname");
		String miemail = request.getParameter("miemail");
		String p1 = request.getParameter("p1");
		String p2 = request.getParameter("p2");
		String p3 = request.getParameter("p3");
		String oiphone = p1 + "-" + p2 + "-" + p3;
		
		String oizip = memberInfo.getMa_zip();
		String oiaddr1 = memberInfo.getMa_addr1();
		String oiaddr2 = memberInfo.getMa_addr2();
		String oicmt = "";
		if (request.getParameter("oicmt1") != null && !request.getParameter("oicmt1").equals("")) {
			if (request.getParameter("oicmt1").equals("e")) {
				oicmt = request.getParameter("oicmt2");
			} else {
				String arrCmt[] = {"부재시 문앞에 놓아주세요.", "배송전에 미리 연락주세요.", "부재시 경비실에 맡겨주세요.", "부재시 전화주시거나 문자 남겨 주세요."};
				if (request.getParameter("oicmt1").equals("a")) {
					oicmt = arrCmt[0];
				} else if (request.getParameter("oicmt1").equals("b")) {
					oicmt = arrCmt[1];
				} else if (request.getParameter("oicmt1").equals("c")) {
					oicmt = arrCmt[2];
				} else if (request.getParameter("oicmt1").equals("d")) {
					oicmt = arrCmt[3];
				}
			}
		}

		CartInfo buyInfo = new CartInfo();
		// t_order_info에 들어갈 값들 저장
		buyInfo.setMi_email(miemail);
		buyInfo.setOi_name(oiname);
		buyInfo.setOi_phone(oiphone);
		buyInfo.setOi_zip(oizip);
		buyInfo.setOi_addr1(oiaddr1);
		buyInfo.setOi_addr2(oiaddr2);
		buyInfo.setOi_cmt(oicmt);
		buyInfo.setOi_pay(total);				
		buyInfo.setOi_delipay(delipay);
		buyInfo.setOi_pdtprice(pdtprice);
		buyInfo.setOi_usepoint(oiusepoint);
		buyInfo.setOi_payment(oipayment);
		buyInfo.setOi_status(oistatus);
		
		ArrayList<CartInfo> orderList = (ArrayList<CartInfo>)session.getAttribute("cartList");
		ArrayList<CartInfo> tmpList = new ArrayList<CartInfo>();
		
		if (kind.equals("direct")) {
			ProductInfo pdtInfo = (ProductInfo)request.getAttribute("pdtInfo");
			CartInfo orderInfo = new CartInfo();
			// od_idx, od_id, pi_id, od_pdtprice, od_cnt, od_option
			orderInfo.setPi_id(request.getParameter("piid"));
			orderInfo.setOd_pdtprice(Integer.parseInt(request.getParameter("odpdtprice")));
			orderInfo.setOd_cnt(Integer.parseInt(request.getParameter("odcnt")));
			orderInfo.setOd_option(request.getParameter("odoption"));
			
			tmpList.add(orderInfo);
			
		} else if (kind.equals("cart")) {
			if (orderList != null && orderList.size() > 0) {
				System.out.println(orderList.size());
				
				for (CartInfo orderInfo : orderList) {

					orderInfo.setPi_id(orderInfo.getPi_id());
					orderInfo.setOd_pdtprice(orderInfo.getPi_price());
					orderInfo.setOd_cnt(orderInfo.getOc_cnt());
					orderInfo.setOd_option(orderInfo.getOc_option());
					
					System.out.println(orderInfo.getPi_id());
					System.out.println(orderInfo.getPi_price());
					System.out.println(orderInfo.getOc_cnt());
					System.out.println(orderInfo.getOc_option());
					tmpList.add(orderInfo);
				}
			}
		}
		
		request.setAttribute("tmpList", tmpList);
		
		OrderProcSvc orderProcSvc = new OrderProcSvc();
		//System.out.println(tmpList.size());
		
		int result = orderProcSvc.buyProduct(kind, buyInfo, tmpList);
		result += orderProcSvc.addOrderDetail(kind, buyInfo, tmpList);
		
		if (result != tmpList.size() + 1) {
			orderProcSvc.delOrderInfo(kind, buyInfo);
		}
						
		System.out.println("아아, 여기는 OrderProcAct");
		
		ActionForward forward = new ActionForward();
		forward.setPath("/order_list.ord");
		
		return forward;
	}
}