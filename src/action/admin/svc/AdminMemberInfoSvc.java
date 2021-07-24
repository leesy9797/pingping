package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class AdminMemberInfoSvc {
	public MemberInfo getMemberInfo(String email) {
		MemberInfo memberInfo = null;
		Connection conn = getConnection();	
		AdminMemberDao adminMemberDao = AdminMemberDao.getInstance();
		adminMemberDao.setConnection(conn);
		
		memberInfo = adminMemberDao.getMemberInfo(email);

		System.out.println("아아, 여기는 AdminMemberInfoSvc");
		
		return memberInfo;
	}
}
