package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class AdminPdtInSvc {
	public ArrayList<CataBigInfo> getCataBigList() {
		ArrayList<CataBigInfo> cataBigList = null;
		Connection conn = getConnection();
		AdminPdtDao adminPdtDao = AdminPdtDao.getInstance();
		adminPdtDao.setConnection(conn);
		
		cataBigList = adminPdtDao.getCataBigList();
		close(conn);

    	System.out.println("여기는 AdminPdtInSvc");
		return cataBigList;
	}

	public ArrayList<CataSmallInfo> getCataSmallList() {
		ArrayList<CataSmallInfo> cataSmallList = null;
		Connection conn = getConnection();
		AdminPdtDao adminPdtDao = AdminPdtDao.getInstance();
		adminPdtDao.setConnection(conn);
		
		cataSmallList = adminPdtDao.getCataSmallList();
		close(conn);

    	System.out.println("여기는 AdminPdtInSvc");
		return cataSmallList;
	}
/*
	public ArrayList<BrandInfo> getBrandList() {
		ArrayList<BrandInfo> brandList = null;
		Connection conn = getConnection();
		AdminPdtDao adminPdtDao = AdminPdtDao.getInstance();
		adminPdtDao.setConnection(conn);
		
		brandList = adminPdtDao.getBrandList();
		close(conn);

		return brandList;
	}
*/
}
