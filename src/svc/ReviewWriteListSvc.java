package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class ReviewWriteListSvc {
	public int getReviewWriteCount() {
	// ��Ͽ��� ������ ��ü ������ ������ �����ϴ� �޼ҵ�
		int rcnt = 0;	// ��ü ���� ������ ������ ����
		Connection conn = getConnection();	// DB�� ����
		ReviewDao reviewDao = ReviewDao.getInstance();
		reviewDao.setConnection(conn);
		rcnt = reviewDao.getReviewWriteCount();
		close(conn);
		
		return rcnt;
	}
	
	public ArrayList<ReviewInfo> getReviewWriteList(int cpage, int psize) {
	// �˻��� ���� ����� ArrayList<ReviewInfo>�� �ν��Ͻ��� �����ϴ� �޼ҵ�
		ArrayList<ReviewInfo> reviewList = new ArrayList<ReviewInfo>();
		Connection conn = getConnection();
		ReviewDao reviewDao = ReviewDao.getInstance();
		reviewDao.setConnection(conn);
		reviewList = reviewDao.getReviewWriteList(cpage, psize);
		
		close(conn);
		
		return reviewList;
	}
}
