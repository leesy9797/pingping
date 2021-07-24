package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class CampViewSvc {
	public CampingInfo getCampInfo(int idx) {
		CampingInfo campInfo = null;
		Connection conn = getConnection();	
		CampingDao campingDao = CampingDao.getInstance();
		campingDao.setConnection(conn);
		
		campInfo = campingDao.getCampInfo(idx);

		System.out.println("아아, 여기는 CampViewSvc");
		
		return campInfo;
	}

	public int getCampReplyCount() {
		System.out.println("여기는 CampViewSvc");
		int rcnt = 0;	
		Connection conn = getConnection();	
		CampingDao campingDao = CampingDao.getInstance();
		campingDao.setConnection(conn);
		rcnt = campingDao.getCampReplyCount();
		close(conn);
		
		return rcnt;
	}

	public ArrayList<CampReplyInfo> getCampReplyList(int idx) {
		System.out.println("여기는 CampViewSvc");
		ArrayList<CampReplyInfo> replyList = new ArrayList<CampReplyInfo>();
		Connection conn = getConnection();
		CampingDao campingDao = CampingDao.getInstance();
		campingDao.setConnection(conn);
		replyList = campingDao.getCampReplyList(idx);
		
		close(conn);
		
		return replyList;
	}
}
