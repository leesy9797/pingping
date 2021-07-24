package admin.dao;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import javax.sql.*;
import vo.*;

public class AdminPdtDao {
	private static AdminPdtDao adminPdtDao;
	private Connection conn;
	private AdminPdtDao() {}      
   
	public static AdminPdtDao getInstance() {
		if (adminPdtDao == null)   adminPdtDao = new AdminPdtDao();
		return adminPdtDao;
	}	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
   
	public ArrayList<CataBigInfo> getCataBigList() {
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<CataBigInfo> cataBigList = new ArrayList<CataBigInfo>();
		CataBigInfo cataBigInfo = null;  
		
		try {
			String sql = "select * from t_cata_big ";
         
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
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<CataSmallInfo> cataSmallList = new ArrayList<CataSmallInfo>();
		CataSmallInfo cataSmallInfo = null;  
      
		try {
			String sql = "select * from t_cata_small";
         
			stmt = conn.createStatement();   
			rs = stmt.executeQuery(sql);
			while (rs.next()) {   
				cataSmallInfo = new CataSmallInfo();
            
				cataSmallInfo.setCs_id(rs.getString("cs_id"));
				cataSmallInfo.setCs_name(rs.getString("cs_name"));
				cataSmallInfo.setCb_id(rs.getString("cb_id"));
            
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
/*
	public ArrayList<BrandInfo> getBrandList() {
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<BrandInfo> brandList = new ArrayList<BrandInfo>();
		BrandInfo brandInfo = null;  
	      
	try {
		String sql = "select * from t_brand ";
         
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
*/
	public int getPdtCount(String where) {
		Statement stmt = null;   
		ResultSet rs = null;   
		int rcnt = 0;         
      
		try {
			String sql = "select count(*) from t_product_info a, t_cata_big b, t_cata_small c " + where;
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())   rcnt = rs.getInt(1);
         
		} catch(Exception e) {
			System.out.println("getPdtCount()메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
      
		return rcnt;
	}
   
	public ArrayList<ProductInfo> getPdtList(String where, String orderBy, int cpage, int psize) {
	// 검색된 상품 목록을 ArrayList<ProductInfo> 형 인스턴스로 리턴하는 메소드
		ArrayList<ProductInfo> pdtList = new ArrayList<ProductInfo>();
		// 상품목록을 저장할 ArrayList로 오직 ProductInfo형 인스턴스만 저장됨
		Statement stmt = null;
		ResultSet rs = null;
		ProductInfo productInfo = null;
		// pdtList에 담을 데이터인 ProductInfo 형 인스턴스를 선언   
		int snum = (cpage - 1) * psize;
		// 쿼리의 limit 명령에서 데이터를 가져올 시작 인덱스 번호
      
		try {
			String sql = "select a.*, b.cb_id, b.cb_name, c.cs_name from t_product_info a, t_cata_big b, t_cata_small c " + 
					where + orderBy + " limit " + snum + ", " + psize;
			System.out.println(sql);
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
			// rs에 보여줄 상품이 있을 경우
				productInfo = new ProductInfo();
				// pdtList에 저장할 하나의 상품 정보를 담을 인스턴스 생성

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
				productInfo.setPi_cost(rs.getInt("pi_cost"));
				productInfo.setPi_price(rs.getInt("pi_price"));
				productInfo.setPi_dcrate(rs.getInt("pi_dcrate"));
				productInfo.setPi_stock(rs.getInt("pi_stock"));
				productInfo.setPi_salecnt(rs.getInt("pi_salecnt"));
				productInfo.setPi_reviewcnt(rs.getInt("pi_reviewcnt"));
	            productInfo.setPi_qnacnt(rs.getInt("pi_qnacnt"));
				productInfo.setAi_idx(rs.getInt("ai_idx"));
				productInfo.setPi_staravg(rs.getDouble("pi_staravg"));
				productInfo.setCb_name(rs.getString("cb_name"));
				productInfo.setCs_name(rs.getString("cs_name"));
	            productInfo.setLast_date(rs.getString("last_date"));
				productInfo.setLast_admin(rs.getInt("last_admin"));
				// 받아온 레코드들로 상품 정보를 저장
				
				pdtList.add(productInfo);
				// 한 상품의 정보를 담은 productInfo 인스턴스를 pdtList에 저장
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
		ProductInfo pdtInfo = null;
		Statement stmt = null;
		ResultSet rs = null;
      
		try {
			String sql = "select a.*, b.cb_id, b.cb_name, c.cs_name from t_product_info a, t_cata_big b, t_cata_small c " + 
					" where pi_id = '" + id + "' ";
			
			System.out.println(sql);
			stmt = conn.createStatement();   
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {   
				pdtInfo = new ProductInfo();

				pdtInfo.setPi_id(rs.getString("pi_id"));
				pdtInfo.setCb_id(rs.getString("cb_id"));
				pdtInfo.setCs_id(rs.getString("cs_id"));
				pdtInfo.setBr_name(rs.getString("br_name"));
				pdtInfo.setPi_name(rs.getString("pi_name"));
				pdtInfo.setPi_option(rs.getString("pi_option"));
				pdtInfo.setPi_img1(rs.getString("pi_img1"));
				pdtInfo.setPi_img2(rs.getString("pi_img2"));
				pdtInfo.setPi_img3(rs.getString("pi_img3"));
				pdtInfo.setPi_img4(rs.getString("pi_img4"));
				pdtInfo.setPi_img5(rs.getString("pi_img5"));
				pdtInfo.setPi_desc(rs.getString("pi_desc"));
				pdtInfo.setPi_isview(rs.getString("pi_isview"));
				pdtInfo.setPi_date(rs.getString("pi_date"));
				pdtInfo.setPi_cost(rs.getInt("pi_cost"));
				pdtInfo.setPi_price(rs.getInt("pi_price"));
				pdtInfo.setPi_dcrate(rs.getInt("pi_dcrate"));
				pdtInfo.setPi_stock(rs.getInt("pi_stock"));
				pdtInfo.setPi_salecnt(rs.getInt("pi_salecnt"));
				pdtInfo.setPi_reviewcnt(rs.getInt("pi_reviewcnt"));
				pdtInfo.setPi_qnacnt(rs.getInt("pi_qnacnt"));
				pdtInfo.setAi_idx(rs.getInt("ai_idx"));
				pdtInfo.setPi_staravg(rs.getDouble("pi_staravg"));
				pdtInfo.setCb_name(rs.getString("cb_name"));
				pdtInfo.setCs_name(rs.getString("cs_name"));
				pdtInfo.setLast_date(rs.getString("last_date"));
				pdtInfo.setLast_admin(rs.getInt("last_admin"));
			}
         
		} catch(Exception e) {
			System.out.println("getPdtInfo() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
      
		return pdtInfo;
	}
	
	public int pdtInsert(ProductInfo pdtInfo) {	// 상품 등록 처리를 위한 메소드
		int result = 0;
		Statement stmt = null;
		ResultSet rs = null;
		String piid = "B1S111001";
		
		try {
			String sql = "select max(right(pi_id, 4)) from t_product_info where left(pi_id, 2) = '" + 
					pdtInfo.getCb_id() + "' and substring(pi_id, 3, 3) = '" + pdtInfo.getCs_id() + "';";
			//System.out.println(sql);

			stmt = conn.createStatement();   
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				if (rs.getString(1) != null) {
					int n = Integer.parseInt(rs.getString(1)) + 1;
					piid = pdtInfo.getCb_id() + pdtInfo.getCs_id() + n;
					System.out.println(piid);
				}
			} // 새로 등록할 상품의 id 생성
			
			sql = "insert into t_product_info (pi_id, cb_id, cs_id, br_name, pi_name, pi_cost, pi_price, " + 
				"pi_option, pi_img1, pi_img2, pi_img3, pi_img4, pi_img5, pi_desc, " + 
				"pi_dcrate, pi_stock, pi_isview, ai_idx, last_admin) values " + "('" + 
				piid					+ "', '" + 
				pdtInfo.getCb_id()		+ "', '" + 
				pdtInfo.getCs_id()		+ "', '" + 
				pdtInfo.getBr_name()	+ "', '" + 
				pdtInfo.getPi_name()	+ "', " + 
				pdtInfo.getPi_cost()	+ ", " + 
				pdtInfo.getPi_price()	+ ", '" + 
				pdtInfo.getPi_option() + "', '" + 
				pdtInfo.getPi_img1()	+ "', '" + 
				pdtInfo.getPi_img2()	+ "', '" + 
				pdtInfo.getPi_img3()	+ "', '" + 
				pdtInfo.getPi_img4()	+ "', '" + 
				pdtInfo.getPi_img5()	+ "', '" + 
				pdtInfo.getPi_desc()	+ "', " + 
				pdtInfo.getPi_dcrate()	+ ", " + 
				pdtInfo.getPi_stock()	+ ", '" + 
				pdtInfo.getPi_isview()	+ "', " + 
				pdtInfo.getAi_idx()		+ ", " + 
				pdtInfo.getAi_idx()		+ ")";		// 등록일 경우 ai_idx = last_admin
			System.out.println(sql);
			result = stmt.executeUpdate(sql);
			
		} catch(Exception e) {
			System.out.println("pdtInsert() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return result;
	}
	
	public int pdtUpdate(ProductInfo pdtInfo) {	// 상품 수정 처리를 위한 메소드
		int result = 0;
		Statement stmt = null;

		String img1 = "", img2 = "", img3 = "", img4 = "", img5 = "", desc = "";
		if (pdtInfo.getPi_img1().equals("null"))	img1 = "";
		else										img1 = pdtInfo.getPi_img1();
		if (pdtInfo.getPi_img2().equals("null"))	img2 = "";
		else										img2 = pdtInfo.getPi_img2();
		if (pdtInfo.getPi_img3().equals("null"))	img3 = "";
		else										img3 = pdtInfo.getPi_img3();
		if (pdtInfo.getPi_img4().equals("null"))	img4 = "";
		else										img4 = pdtInfo.getPi_img4();
		if (pdtInfo.getPi_img5().equals("null"))	img5 = "";
		else										img5 = pdtInfo.getPi_img5();
		if (pdtInfo.getPi_desc().equals("null"))	desc = "";
		else										desc = pdtInfo.getPi_desc();
		
		try {
			stmt = conn.createStatement(); 			
			String sql = "update t_product_info set " + 
				"pi_name = '"	+ pdtInfo.getPi_name()		+ "', " + 
				"cs_id = '"		+ pdtInfo.getCs_id()		+ "', " + 
				//"br_name = '"	+ pdtInfo.getBr_name()		+ "', " + 
				"pi_cost = "	+ pdtInfo.getPi_cost()		+ ", " + 
				"pi_price = "	+ pdtInfo.getPi_price()		+ ", " + 
				"pi_dcrate = "	+ pdtInfo.getPi_dcrate()	+ ", " + 
				"pi_option = '" + pdtInfo.getPi_option()	+ "', " + 
				"pi_img1 = '"	+ img1	+ "', " + 
				"pi_img2 = '"	+ img2	+ "', " + 
				"pi_img3 = '"	+ img3	+ "', " + 
				"pi_img4 = '"	+ img4	+ "', " + 
				"pi_img5 = '"	+ img5	+ "', " + 
				"pi_desc = '"	+ desc	+ "', " + 
				"pi_stock = "	+ pdtInfo.getPi_stock()		+ ", " + 
				"pi_isview = '"	+ pdtInfo.getPi_isview()	+ "', " + 
				"last_date = now(), " +								// 마지막으로 수정한 날짜
				"last_admin = "	+ pdtInfo.getLast_admin()	+ " " +	// 마지막으로 수정한 관리자번호
				"where pi_id = '" + pdtInfo.getPi_id() + "' ";
			System.out.println(sql);
			result = stmt.executeUpdate(sql);
			
		} catch(Exception e) {
			System.out.println("pdtUpdate() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}
}
