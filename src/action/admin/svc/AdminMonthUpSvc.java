package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class AdminMonthUpSvc {
	public MonthlyInfo getMonthInfo(int idx) {
	// 수정할 상품을 ProductInfo 형 인스턴스로 리턴하는 메소드
		MonthlyInfo monthInfo = new MonthlyInfo();
		Connection conn = getConnection();
		AdminMonthDao adminMonthDao = AdminMonthDao.getInstance();
		adminMonthDao.setConnection(conn);
		monthInfo = adminMonthDao.getMonthInfo(idx);
		close(conn);
		
		System.out.println("여기는 AdminMonthUpSvc");
		return monthInfo;
	}
}
