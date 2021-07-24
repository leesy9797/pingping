package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class MonthListSvc {	
	public int getMonthCount(String where) {
	// 목록에서 보여줄 전체 게시글의 개수를 리턴하는 메소드
		System.out.println("여기는 MonthListSvc");
		int rcnt = 0;	// 전체 상품 개수를 저장할 변수
		Connection conn = getConnection();	// DB에 연결
		MonthDao monthDao = MonthDao.getInstance();
		monthDao.setConnection(conn);
		rcnt = monthDao.getMonthCount(where);
		close(conn);

		return rcnt;
	}
	
	public ArrayList<MonthlyInfo> getMonthList(String where, String orderBy, int cpage, int psize) {
	// 검색된 게시글 목록을 ArrayList<MonthlyInfo>형 인스턴스로 리턴하는 메소드
		System.out.println("여기는 MonthListSvc");
		ArrayList<MonthlyInfo> monthList = new ArrayList<MonthlyInfo>();
		Connection conn = getConnection();	
		MonthDao monthDao = MonthDao.getInstance();
		monthDao.setConnection(conn);
		monthList = monthDao.getMonthList(where, orderBy, cpage, psize);

		close(conn);

		return monthList;
	}
}
