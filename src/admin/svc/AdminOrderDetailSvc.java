package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class AdminOrderDetailSvc {
	public ArrayList<CartInfo> getOrderInfo(String miemail, String oiid) {
		ArrayList<CartInfo> orderInfo = null;
		Connection conn = getConnection();	
		AdminOrderDao adminOrderDao = AdminOrderDao.getInstance();
		adminOrderDao.setConnection(conn);
		
		orderInfo = adminOrderDao.getOrderInfo(miemail, oiid);

		System.out.println("아아, 여기는 OrderDetailSvc");
		
		return orderInfo;
	}
}
