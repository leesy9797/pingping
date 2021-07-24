package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class CartUpProcSvc {
	public int cartUpdate(String kind, String op, int idx, String uid, String piid) {
		int result = 0;	
		Connection conn = getConnection();	
		OrderDao orderDao = OrderDao.getInstance();
		orderDao.setConnection(conn);

		result = orderDao.cartUpdate(kind, op, idx, uid, piid);
		
		if (result == 1)	commit(conn);	// ����� ���ڵ�(ȸ��Ż�� ����)�� ������ ������ �����Ŵ
		else				rollback(conn);	// ����� ���ڵ�(ȸ��Ż�� ����)�� ������ ������� ����
		
		close(conn);
		
		System.out.println("�ƾ�, ����� CartUpProcSvc");
				
		return result;
	}
}
