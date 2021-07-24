package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class PurchaseComfirmedSvc {
	public int purchaseComfirmed(CartInfo orderInfo) {
		int result = 0;	
		Connection conn = getConnection();	
		OrderDao orderDao = OrderDao.getInstance();
		orderDao.setConnection(conn);

		result = orderDao.purchaseComfirmed(orderInfo);
		
		if (result >= 3)	commit(conn);	
		else				rollback(conn);	
		
		close(conn);
		
		System.out.println("아아, 여기는 PurchaseComfirmedSvc");
				
		return result;
	}
}
