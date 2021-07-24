package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class CartDelProcSvc {
	public int cartDelete(String where) {
		int result = 0;	
		Connection conn = getConnection();	
		OrderDao orderDao = OrderDao.getInstance();
		orderDao.setConnection(conn);

		result = orderDao.cartDelete(where);
		
		if (result >= 1)	commit(conn);	// 변경된 레코드(장바구니 (여러) 상품 삭제)가 있으면 쿼리를 적용시킴
		else				rollback(conn);	// 변경된 레코드가 없으면 원래대로 돌림
		
		close(conn);
		
		System.out.println("아아, 여기는 CartDelProcSvc");
				
		return result;
	}
}
