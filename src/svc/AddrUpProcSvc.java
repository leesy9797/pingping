package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class AddrUpProcSvc {
	public int AddrUpdate(MemberInfo memberInfo) {
		int result = 0;	
		Connection conn = getConnection();	
		MemberDao memberDao = MemberDao.getInstance();
		memberDao.setConnection(conn);

		result = memberDao.AddrUpdate(memberInfo);
		
		if (result >= 1)	commit(conn);	
		else				rollback(conn);	
		
		close(conn);
		
		System.out.println("아아, 여기는 AddrUpProcSvc");
				
		return result;
	}
}
