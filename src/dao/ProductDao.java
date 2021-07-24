package dao;

import static db.JdbcUtil.*;
import javax.sql.*;
import java.sql.*;
import java.util.*;
import vo.*;

public class ProductDao {
// 공지사항 관련 DB 작업을 실제로 처리하는 메소드들을 담은 클래스
	private static ProductDao productDao;
	private Connection conn;
	private ProductDao() {}
	
	public static ProductDao getInstance() {
		if (productDao == null) {	
			productDao = new ProductDao();
		}	
		return productDao;	
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	
	public ArrayList<CataBigInfo> getCataBigList() {
	// 대분류 목록을 리턴하는 메소드
		ArrayList<CataBigInfo> cataBigList = new ArrayList<CataBigInfo>();
		
		Statement stmt = null;
		ResultSet rs = null;		
		
		CataBigInfo cataBigInfo = null;
		
		try {
			String sql = "select * from t_cata_big";
			
			stmt = conn.createStatement();				
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				cataBigInfo = new CataBigInfo();
				
				cataBigInfo.setCb_id(rs.getString("cb_id"));
				cataBigInfo.setCb_name(rs.getString("cb_name"));
				
				cataBigList.add(cataBigInfo);
			 }
			
		} catch(Exception e) {
			System.out.println("getCataBigList() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}	
		
		return cataBigList;
		
	}
	
	public ArrayList<CataSmallInfo> getCataSmallList() {
	// 소분류 목록을 리턴하는 메소드
		ArrayList<CataSmallInfo> cataSmallList = new ArrayList<CataSmallInfo>();
		
		Statement stmt = null;
		ResultSet rs = null;		
		
		CataSmallInfo cataSmallInfo = null;  
		
	    try {
			String sql = "select * from t_cata_small ";
			
			stmt = conn.createStatement();				
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				cataSmallInfo = new CataSmallInfo();
				
	            cataSmallInfo.setCs_id(rs.getString("cs_id"));
	            cataSmallInfo.setCb_id(rs.getString("cb_id"));
	            cataSmallInfo.setCs_name(rs.getString("cs_name"));
	            
	            cataSmallList.add(cataSmallInfo);
			 }
			
		} catch(Exception e) {
			System.out.println("getCataSmallList() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}	
		
		return cataSmallList;
		
	}
	
	public ArrayList<BrandInfo> getBrandList() {
	// 브랜드 목록을 리턴하는 메소드
		ArrayList<BrandInfo> brandList  = new ArrayList<BrandInfo>();
		
		Statement stmt = null;
		ResultSet rs = null;		
		
		BrandInfo brandInfo = null;
		
		try {
			String sql = "select * from t_brand";
			
			stmt = conn.createStatement();				
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				brandInfo = new BrandInfo();
	            
	            brandInfo.setB_idx(rs.getInt("b_idx"));
	            brandInfo.setB_name(rs.getString("b_name"));
	            
	            brandList.add(brandInfo);
			 }
			
		} catch(Exception e) {
			System.out.println("getBrandList() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}	
		
		return brandList;
		
	}
	
	public int getPdtCount(String where) {
	// 검색된 상품의 총 개수를 리턴하는 메소드(검색조건이 있을 경우 검색된 결과의 개수를 리턴)
		Statement stmt = null;	
		ResultSet rs = null;	
		int rcnt = 0;	// 게시글 개수를 저장할 변수
		
		try {
			String sql = "select count(*) from t_product_info a, t_cata_big b, t_cata_small c" + where;
			// 게시글의 총 개수를 가져올 쿼리 작성
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {	
				rcnt = rs.getInt(1);
			}	// 게시글의 개수를 rcnt에 저장(게시글이 없으면 0이 저장됨)
			
		} catch(Exception e) {
			System.out.println("getPdtCount() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		return rcnt;
	}
	
	public ArrayList<ProductInfo> getPdtList(String where, String orderBy, int cpage, int psize) {
	// 검색된 상품 목록을 ArrayList<ProductInfo>형 인스턴스로 리턴하는 메소드
		ArrayList<ProductInfo> pdtList = new ArrayList<ProductInfo>();
		// 상품 목록을 저장할 ArrayList로 오직 ProductInfo형 인스턴스만 저장됨
		
		Statement stmt = null;
		ResultSet rs = null;		
		
		ProductInfo productInfo = null;
		// pdtList에 담을 데이터를 담을 ProductInfo형 인스턴스를 선언
		
		int snum = (cpage - 1) * psize;
		// 쿼리의 limit 명령에서 데이터를 가져올 시작 인덱스 번호
		
		try {
			String sql = "select a.*, b.cb_name, c.cs_name from t_product_info a, t_cata_big b, t_cata_small c " + 
					where + orderBy + " limit " + snum + ", " + psize;
			System.out.println(sql);
			stmt = conn.createStatement();				
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
			// rs에 보여줄 상품이 있을 경우 
				productInfo = new ProductInfo();
				// pdtList에 저장할 한 상품의 정보를 담을 인스턴스 생성

				productInfo.setPi_id(rs.getString("pi_id"));
	            productInfo.setCb_id(rs.getString("cb_id"));
	            productInfo.setCs_id(rs.getString("cs_id"));
	            productInfo.setBr_name(rs.getString("br_name"));
	            productInfo.setPi_name(rs.getString("pi_name"));
	            productInfo.setPi_option(rs.getString("pi_option"));
	            productInfo.setPi_img1(rs.getString("pi_img1"));
	            productInfo.setPi_img2(rs.getString("pi_img2"));
	            productInfo.setPi_img3(rs.getString("pi_img3"));
	            productInfo.setPi_img4(rs.getString("pi_img4"));
	            productInfo.setPi_img5(rs.getString("pi_img5"));
	            productInfo.setPi_desc(rs.getString("pi_desc"));
	            productInfo.setPi_isview(rs.getString("pi_isview"));
	            productInfo.setPi_date(rs.getString("pi_date"));
	            productInfo.setLast_date(rs.getString("last_date"));
	            productInfo.setLast_admin(rs.getInt("last_admin"));
	            productInfo.setPi_cost(rs.getInt("pi_cost"));
	            productInfo.setPi_price(rs.getInt("pi_price"));
	            //productInfo.setPi_discount(rs.getInt("pi_discount"));
	            productInfo.setPi_dcrate(rs.getInt("pi_dcrate"));
	            productInfo.setPi_stock(rs.getInt("pi_stock"));
	            productInfo.setPi_salecnt(rs.getInt("pi_salecnt"));
	            productInfo.setPi_reviewcnt(rs.getInt("pi_reviewcnt"));
	            productInfo.setPi_qnacnt(rs.getInt("pi_qnacnt"));
	            productInfo.setAi_idx(rs.getInt("ai_idx"));
	            productInfo.setPi_staravg(rs.getDouble("pi_staravg"));
	            productInfo.setCb_name(rs.getString("cb_name"));
	            productInfo.setCs_name(rs.getString("cs_name"));
	            // 받아온 레코드들로 상품 정보를 저장
				
				pdtList.add(productInfo);
				// 한 상품의 정보를 담은 ProductInfo 인스턴스를 pdtList에 저장
			 }
			
		} catch(Exception e) {
			System.out.println("getPdtList() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		
		return pdtList;
		
	}

	public ProductInfo getPdtInfo(String id) {
	// 지정한 id에 해당하는 특정 상품 정보를 ProductInfo형 인스턴스로 리턴하는 메소드
		Statement stmt = null;
		ResultSet rs = null;
		ProductInfo productInfo = null;
		
		try {
			String sql = "select a.*, b.cb_name, c.cs_name from t_product_info a, t_cata_big b, t_cata_small c " + 
					"where a.cb_id = b.cb_id and b.cb_id = c.cb_id and a.cs_id = c.cs_id and pi_isview = 'y' " + 
					"and a.pi_id = '" + id + "' and a.pi_stock <> 0";
			System.out.println(sql);
			stmt = conn.createStatement();				
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
			// rs에 보여줄 상품이 있을 경우 
				productInfo = new ProductInfo();

				productInfo.setPi_id(rs.getString("pi_id"));
	            productInfo.setCb_id(rs.getString("cb_id"));
	            productInfo.setCs_id(rs.getString("cs_id"));
	            productInfo.setBr_name(rs.getString("br_name"));
	            productInfo.setPi_name(rs.getString("pi_name"));
	            productInfo.setPi_option(rs.getString("pi_option"));
	            productInfo.setPi_img1(rs.getString("pi_img1"));
	            productInfo.setPi_img2(rs.getString("pi_img2"));
	            productInfo.setPi_img3(rs.getString("pi_img3"));
	            productInfo.setPi_img4(rs.getString("pi_img4"));
	            productInfo.setPi_img5(rs.getString("pi_img5"));
	            productInfo.setPi_desc(rs.getString("pi_desc"));
	            productInfo.setPi_isview(rs.getString("pi_isview"));
	            productInfo.setPi_date(rs.getString("pi_date"));
	            productInfo.setLast_date(rs.getString("last_date"));
	            productInfo.setLast_admin(rs.getInt("last_admin"));
	            productInfo.setPi_cost(rs.getInt("pi_cost"));
	            productInfo.setPi_price(rs.getInt("pi_price"));
	            //productInfo.setPi_discount(rs.getInt("pi_discount"));
	            productInfo.setPi_dcrate(rs.getInt("pi_dcrate"));
	            productInfo.setPi_stock(rs.getInt("pi_stock"));
	            productInfo.setPi_salecnt(rs.getInt("pi_salecnt"));
	            productInfo.setPi_reviewcnt(rs.getInt("pi_reviewcnt"));
	            productInfo.setPi_qnacnt(rs.getInt("pi_qnacnt"));
	            productInfo.setAi_idx(rs.getInt("ai_idx"));
	            productInfo.setPi_staravg(rs.getDouble("pi_staravg"));
	            productInfo.setCb_name(rs.getString("cb_name"));
	            productInfo.setCs_name(rs.getString("cs_name"));
	            // 받아온 레코드들로 상품 정보를 저장
	            
			 }
			
		} catch(Exception e) {
			System.out.println("getPdtInfo() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		return productInfo;

	}
}
