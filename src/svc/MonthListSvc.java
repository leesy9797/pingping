package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class MonthListSvc {	
	public int getMonthCount(String where) {
	// ��Ͽ��� ������ ��ü �Խñ��� ������ �����ϴ� �޼ҵ�
		System.out.println("����� MonthListSvc");
		int rcnt = 0;	// ��ü ��ǰ ������ ������ ����
		Connection conn = getConnection();	// DB�� ����
		MonthDao monthDao = MonthDao.getInstance();
		monthDao.setConnection(conn);
		rcnt = monthDao.getMonthCount(where);
		close(conn);

		return rcnt;
	}
	
	public ArrayList<MonthlyInfo> getMonthList(String where, String orderBy, int cpage, int psize) {
	// �˻��� �Խñ� ����� ArrayList<MonthlyInfo>�� �ν��Ͻ��� �����ϴ� �޼ҵ�
		System.out.println("����� MonthListSvc");
		ArrayList<MonthlyInfo> monthList = new ArrayList<MonthlyInfo>();
		Connection conn = getConnection();	
		MonthDao monthDao = MonthDao.getInstance();
		monthDao.setConnection(conn);
		monthList = monthDao.getMonthList(where, orderBy, cpage, psize);

		close(conn);

		return monthList;
	}
}
