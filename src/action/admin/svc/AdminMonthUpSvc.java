package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class AdminMonthUpSvc {
	public MonthlyInfo getMonthInfo(int idx) {
	// ������ ��ǰ�� ProductInfo �� �ν��Ͻ��� �����ϴ� �޼ҵ�
		MonthlyInfo monthInfo = new MonthlyInfo();
		Connection conn = getConnection();
		AdminMonthDao adminMonthDao = AdminMonthDao.getInstance();
		adminMonthDao.setConnection(conn);
		monthInfo = adminMonthDao.getMonthInfo(idx);
		close(conn);
		
		System.out.println("����� AdminMonthUpSvc");
		return monthInfo;
	}
}
