package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class AdminMonthProcSvc {
	public boolean monthInsert(MonthlyInfo monthInfo) {
		boolean isSuccess = false;
		Connection conn = getConnection();
		AdminMonthDao adminMonthDao = AdminMonthDao.getInstance();
		adminMonthDao.setConnection(conn);
		
		int result = adminMonthDao.monthInsert(monthInfo);
		if (result > 0) {
			isSuccess = true;
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);

		return isSuccess;
	}
	
	public boolean monthUpdate(MonthlyInfo monthInfo) {
		boolean isSuccess = false;
		Connection conn = getConnection();
		AdminMonthDao adminMonthDao = AdminMonthDao.getInstance();
		adminMonthDao.setConnection(conn);
		
		int result = adminMonthDao.monthUpdate(monthInfo);
		if (result > 0) {
			isSuccess = true;
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);

		return isSuccess;
	}
}
