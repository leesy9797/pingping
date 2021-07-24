package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class AddrListSvc {
	public ArrayList<MemberInfo> getAddrList(String miemail) {
		ArrayList<MemberInfo> addrList = new ArrayList<MemberInfo>();
		
		Connection conn = getConnection();	
		MemberDao memberDao = MemberDao.getInstance();
		memberDao.setConnection(conn);
		addrList = memberDao.getAddrList(miemail);

		System.out.println("아아, 여기는 AddrListSvc");
		
		close(conn);
				
		return addrList;
	}
}
