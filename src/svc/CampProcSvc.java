package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class CampProcSvc {
	public boolean addReview(CampingInfo campInfo) {
		boolean isSuccess = false;
		Connection conn = getConnection();	
		CampingDao campingDao = CampingDao.getInstance();
		campingDao.setConnection(conn);

		int result = campingDao.addReview(campInfo);
		
		if (result > 0) {
			isSuccess = true;
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		
		System.out.println("아아, 여기는 CampProcSvc");
		
		return isSuccess;
	}
	
	public boolean updateReview(CampingInfo campInfo) {
		boolean isSuccess = false;
		Connection conn = getConnection();
		CampingDao campingDao = CampingDao.getInstance();
		campingDao.setConnection(conn);
		
		int result = campingDao.updateReview(campInfo);
		if (result > 0) {
			isSuccess = true;
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		
		System.out.println("아아, 여기는 CampProcSvc");

		return isSuccess;
	}

	public boolean deleteReview(CampingInfo campInfo) {

		System.out.println("아아, 여기는 CampProcSvc");
		boolean isSuccess = false;
		Connection conn = getConnection();
		CampingDao campingDao = CampingDao.getInstance();
		campingDao.setConnection(conn);
		
		int result = campingDao.deleteReview(campInfo);
		if (result > 0) {
			isSuccess = true;
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		
		System.out.println("아아, 여기는 CampProcSvc");

		return isSuccess;
	}
	
	public int getIdx() {
		Connection conn = getConnection();
		CampingDao campingDao = CampingDao.getInstance();
		campingDao.setConnection(conn);
		
		int idx = campingDao.getIdx();
		
		close(conn);
		
		return idx;
	}
}
