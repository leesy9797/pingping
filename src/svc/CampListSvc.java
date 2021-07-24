package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class CampListSvc {
	public int getCampCount() {
	// 목록에서 보여줄 캠핑후기 개수를 리턴하는 메소드
		int rcnt = 0;	// 전체 캠핑후기 개수를 저장할 변수
		Connection conn = getConnection();	
		CampingDao campingDao = CampingDao.getInstance();
		campingDao.setConnection(conn);
		
		rcnt = campingDao.getCampCount();
		// 게시글의 전체 개수를 구할 getCampCount 메소드 호출
		close(conn);

		System.out.println("아아, 여기는 CampListSvc");
		
		return rcnt;
	}
	
	public ArrayList<CampingInfo> getCampList(String orderBy, int cpage, int psize) {
	// 검색된 상품 목록을 ArrayList<ProductInfo>형 인스턴스로 리턴하는 메소드
		ArrayList<CampingInfo> campList = new ArrayList<CampingInfo>();
		Connection conn = getConnection();	
		CampingDao campingDao = CampingDao.getInstance();
		campingDao.setConnection(conn);
		campList = campingDao.getCampList(orderBy, cpage, psize);

		System.out.println("아아, 여기는 CampListSvc");
		
		close(conn);
				
		return campList;
	}
}
