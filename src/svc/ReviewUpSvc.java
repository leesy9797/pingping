package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class ReviewUpSvc {
	public ReviewInfo getReviewInfo(int idx) {
	// 수정할 상품을 ProductInfo 형 인스턴스로 리턴하는 메소드
		ReviewInfo reviewInfo = new ReviewInfo();
		Connection conn = getConnection();
		ReviewDao reviewDao = ReviewDao.getInstance();
		reviewDao.setConnection(conn);
		reviewInfo = reviewDao.getReviewInfo(idx);
		close(conn);
		
		System.out.println("여기는 ReviewUpSvc");
		return reviewInfo;
	}
}
