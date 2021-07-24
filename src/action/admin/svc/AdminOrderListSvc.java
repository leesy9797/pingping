package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class AdminOrderListSvc {
	public int getOrderCount(String where) {
	// 목록에서 보여줄 전체 상품의 개수를 리턴하는 메소드
		int rcnt = 0;	// 전체 상품 개수를 저장할 변수
		Connection conn = getConnection();	// DB에 연결
		AdminOrderDao adminOrderDao = AdminOrderDao.getInstance();
		// ProductDao형 인스턴스를 getInstance() 메소드를 통해 생성
		adminOrderDao.setConnection(conn);
		// ProductDao형 인스턴스 productDao에 Connection 객체를 지정
		rcnt = adminOrderDao.getOrderCount(where);
		// 게시글의 전체 개수를 구할 getPdtCount 메소드 호출
		close(conn);
				
		return rcnt;
	}
	
	public ArrayList<CartInfo> getOrderList(String where, int cpage, int psize) {
		ArrayList<CartInfo> cartList = new ArrayList<CartInfo>();
		Connection conn = getConnection();	
		AdminOrderDao adminOrderDao = AdminOrderDao.getInstance();
		adminOrderDao.setConnection(conn);
		cartList = adminOrderDao.getOrderList(where, cpage, psize);

		close(conn);
				
		return cartList;
	}

}
