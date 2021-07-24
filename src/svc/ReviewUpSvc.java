package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class ReviewUpSvc {
	public ReviewInfo getReviewInfo(int idx) {
	// ������ ��ǰ�� ProductInfo �� �ν��Ͻ��� �����ϴ� �޼ҵ�
		ReviewInfo reviewInfo = new ReviewInfo();
		Connection conn = getConnection();
		ReviewDao reviewDao = ReviewDao.getInstance();
		reviewDao.setConnection(conn);
		reviewInfo = reviewDao.getReviewInfo(idx);
		close(conn);
		
		System.out.println("����� ReviewUpSvc");
		return reviewInfo;
	}
}
