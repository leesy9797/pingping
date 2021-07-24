package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class FreeReplyProcSvc {
	public static boolean freeReplyInsert(FreeReplyInfo replyInfo) {
		boolean isSuccess = false;
		Connection conn = getConnection();
		FreeDao freeDao = FreeDao.getInstance();
		freeDao.setConnection(conn);
		
		int result = freeDao.freeReplyInsert(replyInfo);
		if (result > 0) {
			isSuccess = true;
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);

		return isSuccess;
	}
	
	public static boolean freeReplyUpdate(FreeReplyInfo replyInfo) {
		boolean isSuccess = false;
		Connection conn = getConnection();
		FreeDao freeDao = FreeDao.getInstance();
		freeDao.setConnection(conn);
		
		int result = freeDao.freeReplyUpdate(replyInfo);
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
