package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class AdminMemberInfoUpdateSvc {
	public int updateMemo(String email, String memo) {
		int result = 0;
		Connection conn = getConnection();	
		AdminMemberDao adminMemberDao = AdminMemberDao.getInstance();
		adminMemberDao.setConnection(conn);
		
		result = adminMemberDao.updateMemo(email, memo);
		
		if (result == 1)	commit(conn);
		else				rollback(conn);	
		
		close(conn);

		System.out.println("아아, 여기는 AdminMemberInfoUpdateSvc");
		
		return result;
	}
}
