package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class CampReplyProcSvc {
	public static boolean CampReplyInsert(CampReplyInfo replyInfo) {
		boolean isSuccess = false;
		Connection conn = getConnection();
		CampingDao campingDao = CampingDao.getInstance();
		campingDao.setConnection(conn);
		
		int result = campingDao.CampReplyInsert(replyInfo);
		if (result > 0) {
			isSuccess = true;
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		
		System.out.println("여기는 CampReplyProcSvc");

		return isSuccess;
	}
	
	public static boolean CampReplyUpdate(CampReplyInfo replyInfo) {
		boolean isSuccess = false;
		Connection conn = getConnection();
		CampingDao campingDao = CampingDao.getInstance();
		campingDao.setConnection(conn);
		
		int result = campingDao.CampReplyUpdate(replyInfo);
		if (result > 0) {
			isSuccess = true;
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);

		System.out.println("여기는 CampReplyProcSvc");
		
		return isSuccess;
	}
}
