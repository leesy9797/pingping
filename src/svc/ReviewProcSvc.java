package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class ReviewProcSvc {
	public boolean reviewInsert(ReviewInfo reviewInfo) {
		boolean isSuccess = false;
		Connection conn = getConnection();
		ReviewDao reviewDao = ReviewDao.getInstance();
		reviewDao.setConnection(conn);
		
		int result = reviewDao.reviewInsert(reviewInfo);
		if (result > 0) {
			isSuccess = true;
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);

		return isSuccess;
	}
	
	public boolean reviewUpdate(ReviewInfo reviewInfo) {
		boolean isSuccess = false;
		Connection conn = getConnection();
		ReviewDao reviewDao = ReviewDao.getInstance();
		reviewDao.setConnection(conn);
		
		int result = reviewDao.reviewUpdate(reviewInfo);
		if (result > 0) {
			isSuccess = true;
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);

		return isSuccess;
	}
}
