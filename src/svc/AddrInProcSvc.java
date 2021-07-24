package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class AddrInProcSvc {
	public int AddrInsert(MemberInfo memberInfo) {
		int result = 0;	
		Connection conn = getConnection();	
		MemberDao memberDao = MemberDao.getInstance();
		memberDao.setConnection(conn);

		result = memberDao.AddrInsert(memberInfo);
		
		if (result >= 1)	commit(conn);	
		else				rollback(conn);	
		
		close(conn);
		
		System.out.println("아아, 여기는 AddrInProcSvc");
				
		return result;
	}
}
