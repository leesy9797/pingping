package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class AdminMonthListSvc {	
	public int getMonthCount(String where) {
	// 목록에서 보여줄 전체 게시글의 개수를 리턴하는 메소드
		int rcnt = 0;	// 전체 개시글 개수를 저장할 변수
		Connection conn = getConnection();	// DB에 연결
		AdminMonthDao adminMonthDao = AdminMonthDao.getInstance();
		// AdminPdtDao형 인스턴스를 getInstance()메소드를 통해 생성
		adminMonthDao.setConnection(conn);
		// AdminPdtDao형 인스턴스 adminPdtDao에 Connection객체를 지정
		rcnt = adminMonthDao.getMonthCount(where);
		// 게시글의 전체 개수를 구할 getPdtCount() 메소드 호출
		close(conn);
		
		return rcnt;
	}
	
	public ArrayList<MonthlyInfo> getMonthList(String where, String orderBy, int cpage, int psize) {
	// 검색된 상품 목록을 	ArrayList<ProductInfo>형 인스턴스로 리턴하는 메소드
		ArrayList<MonthlyInfo> MonthList = new ArrayList<MonthlyInfo>();
		Connection conn = getConnection();
		AdminMonthDao adminMonthDao = AdminMonthDao.getInstance();
		adminMonthDao.setConnection(conn);
		
		MonthList = adminMonthDao.getMonthList(where, orderBy, cpage, psize);
		close(conn);

		System.out.println("여기는 AdminMonthListSvc");
		return MonthList;
	}
}
