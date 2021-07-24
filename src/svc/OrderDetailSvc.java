package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class OrderDetailSvc {
	public ArrayList<CartInfo> getOrderInfo(String miemail, String oiid) {
		ArrayList<CartInfo> orderInfo = null;
		Connection conn = getConnection();	
		OrderDao orderDao = OrderDao.getInstance();
		orderDao.setConnection(conn);
		
		orderInfo = orderDao.getOrderInfo(miemail, oiid);

		System.out.println("아아, 여기는 OrderDetailSvc");
		
		return orderInfo;
	}
}
