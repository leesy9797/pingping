package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class AdminOrderDetailUpdateSvc {
	public int updateMemo(String oiid, String odid, String memo) {
		int result = 0;
		Connection conn = getConnection();	
		AdminOrderDao adminOrderDao = AdminOrderDao.getInstance();
		adminOrderDao.setConnection(conn);
		
		result = adminOrderDao.updateMemo(oiid, odid, memo);
		
		if (result == 1)	commit(conn);
		else				rollback(conn);	
		
		close(conn);

		System.out.println("아아, 여기는 AdminOrderDetailUpdateSvc");
		
		return result;
	}
}
