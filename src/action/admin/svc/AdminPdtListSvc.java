package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class AdminPdtListSvc {
	public ArrayList<CataBigInfo> getCataBigList() {
		ArrayList<CataBigInfo> cataBigList = null;
		Connection conn = getConnection();
		AdminPdtDao adminPdtDao = AdminPdtDao.getInstance();
		adminPdtDao.setConnection(conn);
		
		cataBigList = adminPdtDao.getCataBigList();
		close(conn);
		
		return cataBigList;
	}
	
	public ArrayList<CataSmallInfo> getCataSmallList() {
		ArrayList<CataSmallInfo> cataSmallList = null;   
		Connection conn = getConnection();
		AdminPdtDao adminPdtDao = AdminPdtDao.getInstance();
		adminPdtDao.setConnection(conn);
		
		cataSmallList = adminPdtDao.getCataSmallList();
		close(conn);   
	      
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
	public int getPdtCount(String where) {
	// ��Ͽ��� ������ ��ü �Խñ��� ������ �����ϴ� �޼ҵ�
		int rcnt = 0;	// ��ü ���ñ� ������ ������ ����
		Connection conn = getConnection();	// DB�� ����
		AdminPdtDao adminPdtDao = AdminPdtDao.getInstance();
		// AdminPdtDao�� �ν��Ͻ��� getInstance()�޼ҵ带 ���� ����
		adminPdtDao.setConnection(conn);
		// AdminPdtDao�� �ν��Ͻ� adminPdtDao�� Connection��ü�� ����
		rcnt = adminPdtDao.getPdtCount(where);
		// �Խñ��� ��ü ������ ���� getPdtCount() �޼ҵ� ȣ��
		close(conn);
		
		return rcnt;
	}
	
	public ArrayList<ProductInfo> getPdtList(String where, String orderBy, int cpage, int psize) {
	// �˻��� ��ǰ ����� 	ArrayList<ProductInfo>�� �ν��Ͻ��� �����ϴ� �޼ҵ�
		ArrayList<ProductInfo> pdtList = new ArrayList<ProductInfo>();
		Connection conn = getConnection();
		AdminPdtDao adminPdtDao = AdminPdtDao.getInstance();
		adminPdtDao.setConnection(conn);
		
		pdtList = adminPdtDao.getPdtList(where, orderBy, cpage, psize);
		close(conn);

		System.out.println("����� AdminPdtListSvc");
		return pdtList;
	}
}
