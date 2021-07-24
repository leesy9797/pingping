package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class FreeUpSvc {
	public FreeInfo getFreeInfo(int idx) {
	// ������ ��ǰ�� ProductInfo �� �ν��Ͻ��� �����ϴ� �޼ҵ�
		FreeInfo freeInfo = new FreeInfo();
		Connection conn = getConnection();
		FreeDao freeDao = FreeDao.getInstance();
		freeDao.setConnection(conn);
		freeInfo = freeDao.getFreeInfo(idx);
		close(conn);
		
		System.out.println("����� FreeUpSvc");
		return freeInfo;
	}
}
