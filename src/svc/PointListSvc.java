package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class PointListSvc {
	public int getPointCount(String loginEmail) {
	// ��Ͽ��� ������ ��ü ����Ʈ ������ ������ �����ϴ� �޼ҵ�
		int rcnt = 0;	// ��ü  ����Ʈ ���� ������ ������ ����
		Connection conn = getConnection();	// DB�� ����
		MemberDao memberDao = MemberDao.getInstance();
		memberDao.setConnection(conn);
		rcnt = memberDao.getPointCount(loginEmail);
		close(conn);
		
		return rcnt;
	}

	public int getAvailablePoint(String loginEmail) {
	// ��밡���� ����Ʈ�� �����ϴ� �޼ҵ�
		int availablePoint = 0;	// ��밡���� ����Ʈ�� ������ ����
		Connection conn = getConnection();	// DB�� ����
		MemberDao memberDao = MemberDao.getInstance();
		memberDao.setConnection(conn);
		availablePoint = memberDao.getAvailablePoint(loginEmail);
		close(conn);
		
		return availablePoint;
	}
	
	public ArrayList<PointInfo> getPointList(String loginEmail, int cpage, int psize) {
	// �˻��� ����Ʈ ���� ����� ArrayList<PointInfo>�� �ν��Ͻ��� �����ϴ� �޼ҵ�
		ArrayList<PointInfo> pointList = new ArrayList<PointInfo>();
		Connection conn = getConnection();
		MemberDao memberDao = MemberDao.getInstance();
		memberDao.setConnection(conn);
		pointList = memberDao.getPointList(loginEmail, cpage, psize);
		
		close(conn);
		
		return pointList;
	}
}
