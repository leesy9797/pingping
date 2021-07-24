package dao;

import static db.JdbcUtil.*;
import javax.sql.*;
import java.sql.*;
import java.util.*;
import vo.*;

public class ProductDao {
// �������� ���� DB �۾��� ������ ó���ϴ� �޼ҵ���� ���� Ŭ����
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
	// ��з� ����� �����ϴ� �޼ҵ�
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
			System.out.println("getCataBigList() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}	
		
		return cataBigList;
		
	}
	
	public ArrayList<CataSmallInfo> getCataSmallList() {
	// �Һз� ����� �����ϴ� �޼ҵ�
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
			System.out.println("getCataSmallList() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}	
		
		return cataSmallList;
		
	}
	
	public ArrayList<BrandInfo> getBrandList() {
	// �귣�� ����� �����ϴ� �޼ҵ�
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
			System.out.println("getBrandList() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}	
		
		return brandList;
		
	}
	
	public int getPdtCount(String where) {
	// �˻��� ��ǰ�� �� ������ �����ϴ� �޼ҵ�(�˻������� ���� ��� �˻��� ����� ������ ����)
		Statement stmt = null;	
		ResultSet rs = null;	
		int rcnt = 0;	// �Խñ� ������ ������ ����
		
		try {
			String sql = "select count(*) from t_product_info a, t_cata_big b, t_cata_small c" + where;
			// �Խñ��� �� ������ ������ ���� �ۼ�
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {	
				rcnt = rs.getInt(1);
			}	// �Խñ��� ������ rcnt�� ����(�Խñ��� ������ 0�� �����)
			
		} catch(Exception e) {
			System.out.println("getPdtCount() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		return rcnt;
	}
	
	public ArrayList<ProductInfo> getPdtList(String where, String orderBy, int cpage, int psize) {
	// �˻��� ��ǰ ����� ArrayList<ProductInfo>�� �ν��Ͻ��� �����ϴ� �޼ҵ�
		ArrayList<ProductInfo> pdtList = new ArrayList<ProductInfo>();
		// ��ǰ ����� ������ ArrayList�� ���� ProductInfo�� �ν��Ͻ��� �����
		
		Statement stmt = null;
		ResultSet rs = null;		
		
		ProductInfo productInfo = null;
		// pdtList�� ���� �����͸� ���� ProductInfo�� �ν��Ͻ��� ����
		
		int snum = (cpage - 1) * psize;
		// ������ limit ��ɿ��� �����͸� ������ ���� �ε��� ��ȣ
		
		try {
			String sql = "select a.*, b.cb_name, c.cs_name from t_product_info a, t_cata_big b, t_cata_small c " + 
					where + orderBy + " limit " + snum + ", " + psize;
			System.out.println(sql);
			stmt = conn.createStatement();				
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
			// rs�� ������ ��ǰ�� ���� ��� 
				productInfo = new ProductInfo();
				// pdtList�� ������ �� ��ǰ�� ������ ���� �ν��Ͻ� ����

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
	            // �޾ƿ� ���ڵ��� ��ǰ ������ ����
				
				pdtList.add(productInfo);
				// �� ��ǰ�� ������ ���� ProductInfo �ν��Ͻ��� pdtList�� ����
			 }
			
		} catch(Exception e) {
			System.out.println("getPdtList() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		
		return pdtList;
		
	}

	public ProductInfo getPdtInfo(String id) {
	// ������ id�� �ش��ϴ� Ư�� ��ǰ ������ ProductInfo�� �ν��Ͻ��� �����ϴ� �޼ҵ�
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
			// rs�� ������ ��ǰ�� ���� ��� 
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
	            // �޾ƿ� ���ڵ��� ��ǰ ������ ����
	            
			 }
			
		} catch(Exception e) {
			System.out.println("getPdtInfo() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		return productInfo;

	}
}
