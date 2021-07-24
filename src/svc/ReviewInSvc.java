package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class ReviewInSvc {
	public ReviewInfo getOrderInfo(String id) {
	// 수정할 상품을 ProductInfo 형 인스턴스로 리턴하는 메소드
		ReviewInfo orderInfo = new ReviewInfo();
		Connection conn = getConnection();
		ReviewDao reviewDao = ReviewDao.getInstance();
		reviewDao.setConnection(conn);
		orderInfo = reviewDao.getOrderInfo(id);
		close(conn);
		
		System.out.println("여기는 ReviewInSvc");
		return orderInfo;
	}
}
