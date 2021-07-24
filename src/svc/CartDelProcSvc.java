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
		
		if (result >= 1)	commit(conn);	// ����� ���ڵ�(��ٱ��� (����) ��ǰ ����)�� ������ ������ �����Ŵ
		else				rollback(conn);	// ����� ���ڵ尡 ������ ������� ����
		
		close(conn);
		
		System.out.println("�ƾ�, ����� CartDelProcSvc");
				
		return result;
	}
}
