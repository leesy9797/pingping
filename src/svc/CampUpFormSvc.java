package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class CampUpFormSvc {
	public CampingInfo getCampInfo(int idx) {
		CampingInfo campingInfo = new CampingInfo();
		Connection conn = getConnection();
		CampingDao campingDao = CampingDao.getInstance();
		campingDao.setConnection(conn);
		campingInfo = campingDao.getCampInfo(idx);
		close(conn);
		
		System.out.println("¿©±â´Â CampUpFormSvc");
		return campingInfo;
	}
}
