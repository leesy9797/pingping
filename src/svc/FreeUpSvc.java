package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class FreeUpSvc {
	public FreeInfo getFreeInfo(int idx) {
	// 수정할 상품을 ProductInfo 형 인스턴스로 리턴하는 메소드
		FreeInfo freeInfo = new FreeInfo();
		Connection conn = getConnection();
		FreeDao freeDao = FreeDao.getInstance();
		freeDao.setConnection(conn);
		freeInfo = freeDao.getFreeInfo(idx);
		close(conn);
		
		System.out.println("여기는 FreeUpSvc");
		return freeInfo;
	}
}
