package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class CampListSvc {
	public int getCampCount() {
	// ��Ͽ��� ������ ķ���ı� ������ �����ϴ� �޼ҵ�
		int rcnt = 0;	// ��ü ķ���ı� ������ ������ ����
		Connection conn = getConnection();	
		CampingDao campingDao = CampingDao.getInstance();
		campingDao.setConnection(conn);
		
		rcnt = campingDao.getCampCount();
		// �Խñ��� ��ü ������ ���� getCampCount �޼ҵ� ȣ��
		close(conn);

		System.out.println("�ƾ�, ����� CampListSvc");
		
		return rcnt;
	}
	
	public ArrayList<CampingInfo> getCampList(String orderBy, int cpage, int psize) {
	// �˻��� ��ǰ ����� ArrayList<ProductInfo>�� �ν��Ͻ��� �����ϴ� �޼ҵ�
		ArrayList<CampingInfo> campList = new ArrayList<CampingInfo>();
		Connection conn = getConnection();	
		CampingDao campingDao = CampingDao.getInstance();
		campingDao.setConnection(conn);
		campList = campingDao.getCampList(orderBy, cpage, psize);

		System.out.println("�ƾ�, ����� CampListSvc");
		
		close(conn);
				
		return campList;
	}
}
