package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class PointListSvc {
	public int getPointCount(String loginEmail) {
	// 목록에서 보여줄 전체 포인트 내역의 개수를 리턴하는 메소드
		int rcnt = 0;	// 전체  포인트 내역 개수를 저장할 변수
		Connection conn = getConnection();	// DB에 연결
		MemberDao memberDao = MemberDao.getInstance();
		memberDao.setConnection(conn);
		rcnt = memberDao.getPointCount(loginEmail);
		close(conn);
		
		return rcnt;
	}

	public int getAvailablePoint(String loginEmail) {
	// 사용가능한 포인트를 리턴하는 메소드
		int availablePoint = 0;	// 사용가능한 포인트를 저장할 변수
		Connection conn = getConnection();	// DB에 연결
		MemberDao memberDao = MemberDao.getInstance();
		memberDao.setConnection(conn);
		availablePoint = memberDao.getAvailablePoint(loginEmail);
		close(conn);
		
		return availablePoint;
	}
	
	public ArrayList<PointInfo> getPointList(String loginEmail, int cpage, int psize) {
	// 검색된 포인트 내역 목록을 ArrayList<PointInfo>형 인스턴스로 리턴하는 메소드
		ArrayList<PointInfo> pointList = new ArrayList<PointInfo>();
		Connection conn = getConnection();
		MemberDao memberDao = MemberDao.getInstance();
		memberDao.setConnection(conn);
		pointList = memberDao.getPointList(loginEmail, cpage, psize);
		
		close(conn);
		
		return pointList;
	}
}
