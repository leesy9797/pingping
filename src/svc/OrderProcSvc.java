package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class OrderProcSvc {
	public int buyProduct(String kind, CartInfo buyInfo, ArrayList<CartInfo> tmpList) {
		int result = 0;	
		
		Connection conn = getConnection();	
		OrderDao orderDao = OrderDao.getInstance();
		orderDao.setConnection(conn);

		result = orderDao.buyProduct(kind, buyInfo);
		
		if (result == 1)	commit(conn);
		else				rollback(conn);	
		
		System.out.println(result);
		close(conn);
		
		System.out.println("아아, 여기는 OrderProcSvc");
				
		return result;
	}
	
	public int addOrderDetail(String kind, CartInfo buyInfo, ArrayList<CartInfo> tmpList) {
		int result = 0;	
		
		Connection conn = getConnection();	
		OrderDao orderDao = OrderDao.getInstance();
		orderDao.setConnection(conn);

		result = orderDao.addOrderDetail(kind, buyInfo, tmpList);
		
		if (result == tmpList.size())	commit(conn);
		else							rollback(conn);	
	
		System.out.println(result);
		close(conn);
		
		System.out.println("아아, 여기는 OrderProcSvc");
				
		return result;
	}
	
	public int delOrderInfo(String kind, CartInfo buyInfo) {
		int result = 0;	
		
		Connection conn = getConnection();	
		OrderDao orderDao = OrderDao.getInstance();
		orderDao.setConnection(conn);

		result = orderDao.delOrderInfo(kind, buyInfo);
		
		if (result == 1)	commit(conn);
		else				rollback(conn);	
	
		System.out.println(result);
		close(conn);
		
		System.out.println("아아, 여기는 OrderProcSvc");
				
		return result;
	}
}
