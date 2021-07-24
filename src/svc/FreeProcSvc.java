package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class FreeProcSvc {
	public boolean freeInsert(FreeInfo freeInfo) {
		boolean isSuccess = false;
		Connection conn = getConnection();
		FreeDao freeDao = FreeDao.getInstance();
		freeDao.setConnection(conn);
		
		int result = freeDao.freeInsert(freeInfo);
		if (result > 0) {
			isSuccess = true;
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);

		return isSuccess;
	}
	
	public boolean freeUpdate(FreeInfo freeInfo) {
		boolean isSuccess = false;
		Connection conn = getConnection();
		FreeDao freeDao = FreeDao.getInstance();
		freeDao.setConnection(conn);
		
		int result = freeDao.freeUpdate(freeInfo);
		if (result > 0) {
			isSuccess = true;
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);

		return isSuccess;
	}
	
	public boolean freeDelete(FreeInfo freeInfo) {
		boolean isSuccess = false;
		Connection conn = getConnection();
		FreeDao freeDao = FreeDao.getInstance();
		freeDao.setConnection(conn);
		
		int result = freeDao.freeDelete(freeInfo);
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
