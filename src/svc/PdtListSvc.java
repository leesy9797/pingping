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
	// 목록에서 보여줄 전체 상품의 개수를 리턴하는 메소드
		int rcnt = 0;	// 전체 상품 개수를 저장할 변수
		Connection conn = getConnection();	// DB에 연결
		ProductDao productDao = ProductDao.getInstance();
		// ProductDao형 인스턴스를 getInstance() 메소드를 통해 생성
		productDao.setConnection(conn);
		// ProductDao형 인스턴스 productDao에 Connection 객체를 지정
		rcnt = productDao.getPdtCount(where);
		// 게시글의 전체 개수를 구할 getPdtCount 메소드 호출
		close(conn);
				
		return rcnt;
	}
	
	public ArrayList<ProductInfo> getPdtList(String where, String orderBy, int cpage, int psize) {
	// 검색된 상품 목록을 ArrayList<ProductInfo>형 인스턴스로 리턴하는 메소드
		ArrayList<ProductInfo> pdtList = new ArrayList<ProductInfo>();
		Connection conn = getConnection();	
		ProductDao productDao = ProductDao.getInstance();
		productDao.setConnection(conn);
		pdtList = productDao.getPdtList(where, orderBy, cpage, psize);
		// select 쿼리이기 때문에 commit, rollback을 안해주는 것
		// insert, update, delete 쿼리의 경우 반드시 commit, rollback를 해줘야 함 

		close(conn);
				
		return pdtList;
	}

}
