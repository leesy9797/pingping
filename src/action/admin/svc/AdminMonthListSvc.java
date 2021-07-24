package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class AdminMonthListSvc {	
	public int getMonthCount(String where) {
	// ��Ͽ��� ������ ��ü �Խñ��� ������ �����ϴ� �޼ҵ�
		int rcnt = 0;	// ��ü ���ñ� ������ ������ ����
		Connection conn = getConnection();	// DB�� ����
		AdminMonthDao adminMonthDao = AdminMonthDao.getInstance();
		// AdminPdtDao�� �ν��Ͻ��� getInstance()�޼ҵ带 ���� ����
		adminMonthDao.setConnection(conn);
		// AdminPdtDao�� �ν��Ͻ� adminPdtDao�� Connection��ü�� ����
		rcnt = adminMonthDao.getMonthCount(where);
		// �Խñ��� ��ü ������ ���� getPdtCount() �޼ҵ� ȣ��
		close(conn);
		
		return rcnt;
	}
	
	public ArrayList<MonthlyInfo> getMonthList(String where, String orderBy, int cpage, int psize) {
	// �˻��� ��ǰ ����� 	ArrayList<ProductInfo>�� �ν��Ͻ��� �����ϴ� �޼ҵ�
		ArrayList<MonthlyInfo> MonthList = new ArrayList<MonthlyInfo>();
		Connection conn = getConnection();
		AdminMonthDao adminMonthDao = AdminMonthDao.getInstance();
		adminMonthDao.setConnection(conn);
		
		MonthList = adminMonthDao.getMonthList(where, orderBy, cpage, psize);
		close(conn);

		System.out.println("����� AdminMonthListSvc");
		return MonthList;
	}
}
