package svc;

import static db.JdbcUtil.*;
import java.sql.*;
import dao.*;
import vo.*;


public class DupIDSvc {
	public int chkDupID(String uid) {
		DupIDDao dupIDDao = DupIDDao.getInstance();
		Connection conn = getConnection();
		dupIDDao.setConnection(conn);
		int chkPoint = dupIDDao.chkDupID(uid);
		close(conn);
		System.out.println("¿©±â´ÂDupIDSvc" );
	
		return chkPoint;
	}
}
