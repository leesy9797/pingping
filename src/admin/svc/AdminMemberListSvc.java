package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class AdminMemberListSvc {
	public int getMemberCount(String where) {
		int rcnt = 0;	
		Connection conn = getConnection();	
		AdminMemberDao adminMemberDao = AdminMemberDao.getInstance();
		adminMemberDao.setConnection(conn);
		rcnt = adminMemberDao.getMemberCount(where);
		close(conn);
		
		System.out.println("아아, 여기는 AdminMemberListSvc");
				
		return rcnt;
	}
	
	public ArrayList<MemberInfo> getMemberList(String where, String orderBy, int cpage, int psize) {
		ArrayList<MemberInfo> MemberList = new ArrayList<MemberInfo>();
		Connection conn = getConnection();	
		AdminMemberDao adminMemberDao = AdminMemberDao.getInstance();
		adminMemberDao.setConnection(conn);
		MemberList = adminMemberDao.getMemberList(where, orderBy, cpage, psize);
		close(conn);
		
		System.out.println("아아, 여기는 AdminMemberListSvc");
				
		return MemberList;
	}
}
