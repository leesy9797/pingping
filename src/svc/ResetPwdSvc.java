package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class ResetPwdSvc {
	public MemberInfo resetPwd(String uname, String phone) {
		MemberInfo memberInfo = null;
		Connection conn = getConnection();
		MemberDao memberDao = MemberDao.getInstance();
		memberDao.setConnection(conn);
		
		memberInfo = MemberDao.resetPwd(uname, phone);
		
		
		System.out.println("아아, 여기는 ResetPwdSvc");
		System.out.println(memberInfo.getMi_email());
		
		return memberInfo;	
	}
}
