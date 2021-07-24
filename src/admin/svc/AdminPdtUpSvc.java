package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class AdminPdtUpSvc {
	public ProductInfo getPdtInfo(String id) {
	// ������ ��ǰ�� ProductInfo �� �ν��Ͻ��� �����ϴ� �޼ҵ�
		ProductInfo pdtInfo = new ProductInfo();
		Connection conn = getConnection();
		AdminPdtDao adminPdtDao = AdminPdtDao.getInstance();
		adminPdtDao.setConnection(conn);
		pdtInfo = adminPdtDao.getPdtInfo(id);
		close(conn);
		
		System.out.println("����� AdminPdtUpSvc");
		return pdtInfo;
	}
}
