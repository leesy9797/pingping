package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class MonthViewSvc {
	public MonthlyInfo getMonthInfo(int idx) {
		MonthlyInfo monthInfo = null;
		Connection conn = getConnection();
		MonthDao monthDao = MonthDao.getInstance();
		monthDao.setConnection(conn);

		int result = monthDao.readCountUp(idx);
		if (result > 0) commit(conn);
		else			rollback(conn);				
		
		monthInfo = monthDao.getMonthInfo(idx);
		close(conn);
		
		return monthInfo;
	}

	public int getMonthReplyCount() {
		System.out.println("여기는 MonthViewSvc");
		int rcnt = 0;
		Connection conn = getConnection();	
		MonthDao monthDao = MonthDao.getInstance();
		monthDao.setConnection(conn);
		rcnt = monthDao.getMonthReplyCount();
		close(conn);
		
		return rcnt;
	}

	public ArrayList<MonthReplyInfo> getMonthReplyList(int idx) {
		System.out.println("여기는 MonthViewSvc");
		ArrayList<MonthReplyInfo> replyList = new ArrayList<MonthReplyInfo>();
		Connection conn = getConnection();
		MonthDao monthDao = MonthDao.getInstance();
		monthDao.setConnection(conn);
		replyList = monthDao.getMonthReplyList(idx);
		
		close(conn);
		
		return replyList;
	}
}
