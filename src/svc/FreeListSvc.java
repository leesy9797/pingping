package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class FreeListSvc {
	public int getFreeCount(String where) {
	// 목록에서 보여줄 전체 게시글의 개수를 리턴하는 메소드
		int rcnt = 0;	// 전체 상품 개수를 저장할 변수
		Connection conn = getConnection();	// DB에 연결
		FreeDao freeDao = FreeDao.getInstance();
		freeDao.setConnection(conn);
		rcnt = freeDao.getFreeCount(where);
		close(conn);
		
		return rcnt;
	}
	
	public ArrayList<FreeInfo> getFreeList(String where, String orderBy, int cpage, int psize) {
	// 검색된 게시글 목록을 ArrayList<FreeInfo>형 인스턴스로 리턴하는 메소드
		ArrayList<FreeInfo> freeList = new ArrayList<FreeInfo>();
		Connection conn = getConnection();
		FreeDao freeDao = FreeDao.getInstance();
		freeDao.setConnection(conn);
		freeList = freeDao.getFreeList(where, orderBy, cpage, psize);
		
		close(conn);
		
		return freeList;
	}
}
