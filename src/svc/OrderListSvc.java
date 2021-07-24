package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class OrderListSvc {
	public int getOrderCount(String miemail) {
	// 목록에서 보여줄 주문목록 개수를 리턴하는 메소드
		int rcnt = 0;	// 전체 주문목록 개수를 저장할 변수
		Connection conn = getConnection();	
		OrderDao orderDao = OrderDao.getInstance();
		orderDao.setConnection(conn);
		
		rcnt = orderDao.getOrderCount(miemail);
		// 주문목록 전체 개수를 구할 getOrderCount 메소드 호출
		close(conn);

		System.out.println("아아, 여기는 OrderListSvc");
		
		return rcnt;
	}
	
	public int getOrderListCount(String miemail, String where) {
	// 한 주문건에 포함된 상품 개수를 리턴하는 메소드
		int cnt = 0;	// 한 주문건에 포함된 상품 개수를 저장할 변수
		Connection conn = getConnection();	
		OrderDao orderDao = OrderDao.getInstance();
		orderDao.setConnection(conn);
		
		cnt = orderDao.getOrderListCount(miemail, where);
		close(conn);

		System.out.println("아아, 여기는 OrderListSvc");
		
		return cnt;
	}

	public ArrayList<CartInfo> getOrderList(String miemail, String where, int cpage, int psize) {
	// 검색된 상품 목록을 ArrayList<ProductInfo>형 인스턴스로 리턴하는 메소드
		ArrayList<CartInfo> orderList = new ArrayList<CartInfo>();
		Connection conn = getConnection();	
		OrderDao orderDao = OrderDao.getInstance();
		orderDao.setConnection(conn);
		orderList = orderDao.getOrderList(miemail, where, cpage, psize);

		System.out.println("아아, 여기는 OrderListSvc");
		
		close(conn);
				
		return orderList;
	}
}
