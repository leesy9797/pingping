package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class PdtListSvc {
	public ArrayList<CataBigInfo> getCataBigList() {
		ArrayList<CataBigInfo> cataBigList = null;
		Connection conn = getConnection();	
		ProductDao productDao = ProductDao.getInstance();
		productDao.setConnection(conn);
		cataBigList = productDao.getCataBigList();
		close(conn);
				
		return cataBigList;
	}
	
	public ArrayList<CataSmallInfo> getCataSmallList() {
		ArrayList<CataSmallInfo> cataSmallList = null;   
		Connection conn = getConnection();
		ProductDao productDao = ProductDao.getInstance();
		productDao.setConnection(conn);
		cataSmallList = productDao.getCataSmallList();
		close(conn);   
	      
		return cataSmallList;
   }
	   

	public ArrayList<BrandInfo> getBrandList() {
		ArrayList<BrandInfo> brandList = null;   
		Connection conn = getConnection();
		ProductDao productDao = ProductDao.getInstance();
		productDao.setConnection(conn);
		brandList = productDao.getBrandList();
		close(conn);   
	
		return brandList;
	}
	
	public int getPdtCount(String where) {
	// ��Ͽ��� ������ ��ü ��ǰ�� ������ �����ϴ� �޼ҵ�
		int rcnt = 0;	// ��ü ��ǰ ������ ������ ����
		Connection conn = getConnection();	// DB�� ����
		ProductDao productDao = ProductDao.getInstance();
		// ProductDao�� �ν��Ͻ��� getInstance() �޼ҵ带 ���� ����
		productDao.setConnection(conn);
		// ProductDao�� �ν��Ͻ� productDao�� Connection ��ü�� ����
		rcnt = productDao.getPdtCount(where);
		// �Խñ��� ��ü ������ ���� getPdtCount �޼ҵ� ȣ��
		close(conn);
				
		return rcnt;
	}
	
	public ArrayList<ProductInfo> getPdtList(String where, String orderBy, int cpage, int psize) {
	// �˻��� ��ǰ ����� ArrayList<ProductInfo>�� �ν��Ͻ��� �����ϴ� �޼ҵ�
		ArrayList<ProductInfo> pdtList = new ArrayList<ProductInfo>();
		Connection conn = getConnection();	
		ProductDao productDao = ProductDao.getInstance();
		productDao.setConnection(conn);
		pdtList = productDao.getPdtList(where, orderBy, cpage, psize);
		// select �����̱� ������ commit, rollback�� �����ִ� ��
		// insert, update, delete ������ ��� �ݵ�� commit, rollback�� ����� �� 

		close(conn);
				
		return pdtList;
	}

}
