package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class SetPwdSvc {
	public int setPwd(MemberInfo memberInfo) {
		int result = 0;	
		Connection conn = getConnection();
		MemberDao memberDao = MemberDao.getInstance();
		memberDao.setConnection(conn);
		
		result = MemberDao.setPwd(memberInfo);

		System.out.println("아아, 여기는 SetPwdSvc : " + memberInfo.getMi_pwd());
		System.out.println("아아, 여기는 SetPwdSvc : " + memberInfo.getMi_email());

		if (result > 0)	commit(conn);
		else				rollback(conn);	

		System.out.println("아아, 여기는 SetPwdSvc");
		
		return result;	
	}
}
