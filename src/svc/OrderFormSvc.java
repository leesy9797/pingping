package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class OrderFormSvc {
	public ArrayList<CartInfo> getCartList(String miemail, String where) {
		ArrayList<CartInfo> cartList = null;
		
		Connection conn = getConnection();	
		OrderDao orderDao = OrderDao.getInstance();
		orderDao.setConnection(conn);

		cartList = orderDao.getCartList(miemail, where);
		
		close(conn);
		
		System.out.println("아아, 여기는 OrderFormSvc");
				
		return cartList;
	}

	public ProductInfo getPdtInfo(String id) {	// piid
		ProductInfo pdtInfo = null;
		Connection conn = getConnection();	
		ProductDao productDao = ProductDao.getInstance();
		productDao.setConnection(conn);
		// select이므로 commit, rollback 필요없음
		pdtInfo = productDao.getPdtInfo(id);

		System.out.println("아아, 여기는 OrderFormSvc");
		
		return pdtInfo;
	}
}
