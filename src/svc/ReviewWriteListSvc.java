package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class ReviewWriteListSvc {
	public int getReviewWriteCount() {
	// 목록에서 보여줄 전체 리뷰의 개수를 리턴하는 메소드
		int rcnt = 0;	// 전체 리뷰 개수를 저장할 변수
		Connection conn = getConnection();	// DB에 연결
		ReviewDao reviewDao = ReviewDao.getInstance();
		reviewDao.setConnection(conn);
		rcnt = reviewDao.getReviewWriteCount();
		close(conn);
		
		return rcnt;
	}
	
	public ArrayList<ReviewInfo> getReviewWriteList(int cpage, int psize) {
	// 검색된 리뷰 목록을 ArrayList<ReviewInfo>형 인스턴스로 리턴하는 메소드
		ArrayList<ReviewInfo> reviewList = new ArrayList<ReviewInfo>();
		Connection conn = getConnection();
		ReviewDao reviewDao = ReviewDao.getInstance();
		reviewDao.setConnection(conn);
		reviewList = reviewDao.getReviewWriteList(cpage, psize);
		
		close(conn);
		
		return reviewList;
	}
}
