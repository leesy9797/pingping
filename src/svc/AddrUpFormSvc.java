package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class AddrUpFormSvc {
	public MemberInfo getAddrInfo(int maidx) {
		MemberInfo memberInfo = null;
		Connection conn = getConnection();	
		MemberDao memberDao = MemberDao.getInstance();
		memberDao.setConnection(conn);
		
		memberInfo = memberDao.getAddrInfo(maidx);
	
		System.out.println("아아, 여기는 AddrUpFormSvc");
		
		return memberInfo;
	}
}
