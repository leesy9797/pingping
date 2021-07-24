package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class AdminOrderListSvc {
	public int getOrderCount(String where) {
	// ��Ͽ��� ������ ��ü ��ǰ�� ������ �����ϴ� �޼ҵ�
		int rcnt = 0;	// ��ü ��ǰ ������ ������ ����
		Connection conn = getConnection();	// DB�� ����
		AdminOrderDao adminOrderDao = AdminOrderDao.getInstance();
		// ProductDao�� �ν��Ͻ��� getInstance() �޼ҵ带 ���� ����
		adminOrderDao.setConnection(conn);
		// ProductDao�� �ν��Ͻ� productDao�� Connection ��ü�� ����
		rcnt = adminOrderDao.getOrderCount(where);
		// �Խñ��� ��ü ������ ���� getPdtCount �޼ҵ� ȣ��
		close(conn);
				
		return rcnt;
	}
	
	public ArrayList<CartInfo> getOrderList(String where, int cpage, int psize) {
		ArrayList<CartInfo> cartList = new ArrayList<CartInfo>();
		Connection conn = getConnection();	
		AdminOrderDao adminOrderDao = AdminOrderDao.getInstance();
		adminOrderDao.setConnection(conn);
		cartList = adminOrderDao.getOrderList(where, cpage, psize);

		close(conn);
				
		return cartList;
	}

}
