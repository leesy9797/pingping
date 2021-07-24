package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class FreeListSvc {
	public int getFreeCount(String where) {
	// ��Ͽ��� ������ ��ü �Խñ��� ������ �����ϴ� �޼ҵ�
		int rcnt = 0;	// ��ü ��ǰ ������ ������ ����
		Connection conn = getConnection();	// DB�� ����
		FreeDao freeDao = FreeDao.getInstance();
		freeDao.setConnection(conn);
		rcnt = freeDao.getFreeCount(where);
		close(conn);
		
		return rcnt;
	}
	
	public ArrayList<FreeInfo> getFreeList(String where, String orderBy, int cpage, int psize) {
	// �˻��� �Խñ� ����� ArrayList<FreeInfo>�� �ν��Ͻ��� �����ϴ� �޼ҵ�
		ArrayList<FreeInfo> freeList = new ArrayList<FreeInfo>();
		Connection conn = getConnection();
		FreeDao freeDao = FreeDao.getInstance();
		freeDao.setConnection(conn);
		freeList = freeDao.getFreeList(where, orderBy, cpage, psize);
		
		close(conn);
		
		return freeList;
	}
}
