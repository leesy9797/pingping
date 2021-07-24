package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class SearchIdSvc {
	public MemberInfo searchId(String uname, String phone) {
		MemberInfo memberInfo = null;
		Connection conn = getConnection();
		MemberDao memberDao = MemberDao.getInstance();
		memberDao.setConnection(conn);
		
		memberInfo = MemberDao.searchId(uname, phone);
		
		
		System.out.println("아아, 여기는 SearchIdSvc");
		System.out.println(memberInfo.getMi_email());
		
		return memberInfo;	
	}
}
