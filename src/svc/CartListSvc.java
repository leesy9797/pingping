package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class CartListSvc {
	public ArrayList<CartInfo> getCartList(String miemail) {
		ArrayList<CartInfo> cartList = null;
		
		Connection conn = getConnection();	
		OrderDao orderDao = OrderDao.getInstance();
		orderDao.setConnection(conn);

		cartList = orderDao.getCartList(miemail, "");
		
		close(conn);
		
		System.out.println("아아, 여기는 CartListSvc");
				
		return cartList;
	}

}
