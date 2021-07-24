package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class ChkEmailSvc {
	public MemberInfo chkEmail(String email) {
		MemberInfo memberInfo = null;
		Connection conn = getConnection();
		MemberDao memberDao = MemberDao.getInstance();
		memberDao.setConnection(conn);
		
		memberInfo = MemberDao.chkEmail(email);
		
		
		System.out.println("아아, 여기는 ChkEmailSvc");
		
		return memberInfo;	
	}
}
