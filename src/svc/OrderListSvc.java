package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class OrderListSvc {
	public int getOrderCount(String miemail) {
	// ��Ͽ��� ������ �ֹ���� ������ �����ϴ� �޼ҵ�
		int rcnt = 0;	// ��ü �ֹ���� ������ ������ ����
		Connection conn = getConnection();	
		OrderDao orderDao = OrderDao.getInstance();
		orderDao.setConnection(conn);
		
		rcnt = orderDao.getOrderCount(miemail);
		// �ֹ���� ��ü ������ ���� getOrderCount �޼ҵ� ȣ��
		close(conn);

		System.out.println("�ƾ�, ����� OrderListSvc");
		
		return rcnt;
	}
	
	public int getOrderListCount(String miemail, String where) {
	// �� �ֹ��ǿ� ���Ե� ��ǰ ������ �����ϴ� �޼ҵ�
		int cnt = 0;	// �� �ֹ��ǿ� ���Ե� ��ǰ ������ ������ ����
		Connection conn = getConnection();	
		OrderDao orderDao = OrderDao.getInstance();
		orderDao.setConnection(conn);
		
		cnt = orderDao.getOrderListCount(miemail, where);
		close(conn);

		System.out.println("�ƾ�, ����� OrderListSvc");
		
		return cnt;
	}

	public ArrayList<CartInfo> getOrderList(String miemail, String where, int cpage, int psize) {
	// �˻��� ��ǰ ����� ArrayList<ProductInfo>�� �ν��Ͻ��� �����ϴ� �޼ҵ�
		ArrayList<CartInfo> orderList = new ArrayList<CartInfo>();
		Connection conn = getConnection();	
		OrderDao orderDao = OrderDao.getInstance();
		orderDao.setConnection(conn);
		orderList = orderDao.getOrderList(miemail, where, cpage, psize);

		System.out.println("�ƾ�, ����� OrderListSvc");
		
		close(conn);
				
		return orderList;
	}
}
