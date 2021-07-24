package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class ReviewInSvc {
	public ReviewInfo getOrderInfo(String id) {
	// ������ ��ǰ�� ProductInfo �� �ν��Ͻ��� �����ϴ� �޼ҵ�
		ReviewInfo orderInfo = new ReviewInfo();
		Connection conn = getConnection();
		ReviewDao reviewDao = ReviewDao.getInstance();
		reviewDao.setConnection(conn);
		orderInfo = reviewDao.getOrderInfo(id);
		close(conn);
		
		System.out.println("����� ReviewInSvc");
		return orderInfo;
	}
}
