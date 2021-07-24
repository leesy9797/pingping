package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class CartInProcSvc {
	public int cartInsert(CartInfo cart) {
		int result = 0;	
		Connection conn = getConnection();	
		OrderDao orderDao = OrderDao.getInstance();
		orderDao.setConnection(conn);

		result = orderDao.cartInsert(cart);
		
		if (result == 1)	commit(conn);	// 변경된 레코드(회원탈퇴 성공)가 있으면 쿼리를 적용시킴
		else				rollback(conn);	// 변경된 레코드(회원탈퇴 실패)가 없으면 원래대로 돌림
		
		close(conn);
		
		System.out.println("아아, 여기는 CartInProcSvc");
				
		return result;
	}
}
