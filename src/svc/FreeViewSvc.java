package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class FreeViewSvc {
	public FreeInfo getFreeInfo(int idx) {
		FreeInfo freeInfo = null;
		Connection conn = getConnection();
		FreeDao freeDao = FreeDao.getInstance();
		freeDao.setConnection(conn);
		
		int result = freeDao.readCountUp(idx);
		if (result > 0) commit(conn);
		else			rollback(conn);				
		
		freeInfo = freeDao.getFreeInfo(idx);
		close(conn);
		
		return freeInfo;
	}
}
