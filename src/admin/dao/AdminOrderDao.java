package admin.dao;

import static db.JdbcUtil.*;
import javax.sql.*;
import java.sql.*;
import java.util.*;
import vo.*;

public class AdminOrderDao {
	private static AdminOrderDao adminOrderDao;
	private Connection conn;
	private AdminOrderDao() {}
	
	public static AdminOrderDao getInstance() {
		if (adminOrderDao == null) {	
			adminOrderDao = new AdminOrderDao();
		}	
		return adminOrderDao;	
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	// ------> 싱글톤 방식을 위함
	
	public int getOrderCount(String where) {
		Statement stmt = null;	
		ResultSet rs = null;	
		int rcnt = 0;	
		
		try {
			String sql = "select count(*) from t_order_info a, t_member_info b " + where;
			System.out.println(sql);

			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {	
				rcnt = rs.getInt(1);
			}	
			
		} catch(Exception e) {
			System.out.println("getOrderCount() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		return rcnt;
	}
	
	public ArrayList<CartInfo> getOrderList(String where, int cpage, int psize) {
		ArrayList<CartInfo> cartList = new ArrayList<CartInfo>();
		
		Statement stmt = null;
		ResultSet rs = null;		
		
		CartInfo cartInfo = null;
		
		int snum = (cpage - 1) * psize;
		
		try {
			String sql = "select a.*, b.mi_name, b.mi_phone, b.mi_point " +
						 "from t_order_info a, t_member_info b " +  
						 where + " limit " + snum + ", " + psize;

			stmt = conn.createStatement();				
			rs = stmt.executeQuery(sql);
			System.out.println(sql);
			while (rs.next()) {
				cartInfo = new CartInfo();
				
				cartInfo.setOi_id(rs.getString("oi_id"));
				cartInfo.setMi_email(rs.getString("mi_email"));
				cartInfo.setMi_name(rs.getString("mi_name"));
				cartInfo.setMi_phone(rs.getString("mi_phone"));
				cartInfo.setOi_payment(rs.getString("oi_payment"));
				cartInfo.setOi_date(rs.getString("oi_date"));
				cartInfo.setOi_status(rs.getString("oi_status"));
				
				cartList.add(cartInfo);
			 }
			
		} catch(Exception e) {
			System.out.println("getOrderList() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		
		return cartList;
		
	}
	
	public ArrayList<CartInfo> getOrderInfo(String miemail, String oiid) {
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<CartInfo> orderInfo = new ArrayList<CartInfo>();
		CartInfo orderDetail = null;
		try {
			String sql = "select * " +		//"select a.*, b.pi_id, b.od_pdtprice, b.od_cnt, b.od_option, c.br_name, c.pi_name, c.pi_img1 " + 
						 " from t_order_info a, t_order_detail b, t_product_info c " + 
						 " where a.oi_id = b.oi_id and b.pi_id = c.pi_id  and mi_email = '" + miemail +
						 "' and a.oi_id = '" + oiid + "' limit 0, 10";
			System.out.println(sql);
			stmt = conn.createStatement();				
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
			// rs에 보여줄 상품이 있을 경우 
				orderDetail = new CartInfo();
				
				orderDetail.setOi_id(rs.getString("oi_id"));
				orderDetail.setMi_email(rs.getString("mi_email"));
				orderDetail.setOi_name(rs.getString("oi_name"));
				orderDetail.setOi_phone(rs.getString("oi_phone"));
				orderDetail.setOi_zip(rs.getString("oi_zip"));
				orderDetail.setOi_addr1(rs.getString("oi_addr1"));
				orderDetail.setOi_addr2(rs.getString("oi_addr2"));
				orderDetail.setOi_cmt(rs.getString("oi_cmt"));
				orderDetail.setOi_payment(rs.getString("oi_payment"));
				orderDetail.setOi_pay(rs.getInt("oi_pay"));
				orderDetail.setOi_usepoint(rs.getInt("oi_usepoint"));
				orderDetail.setOi_delipay(rs.getInt("oi_delipay"));
				orderDetail.setOi_status(rs.getString("oi_status"));
				orderDetail.setOi_pdtprice(rs.getInt("oi_pdtprice"));
				//orderDetail.setOi_invoice(rs.getString("oi_invoice"));
				orderDetail.setOi_rebank(rs.getString("oi_rebank"));
				orderDetail.setOi_reaacount(rs.getString("oi_reaccount"));
				orderDetail.setOi_redepositer(rs.getString("oi_redepositer"));
				orderDetail.setOi_date(rs.getString("oi_date"));
				orderDetail.setOi_last(rs.getString("oi_last"));
				//orderDetail.setAi_idx(rs.getInt("ai_idx"));
				orderDetail.setPi_id(rs.getString("pi_id"));
				orderDetail.setOd_pdtprice(rs.getInt("od_pdtprice"));
				orderDetail.setOd_cnt(rs.getInt("od_cnt"));
				orderDetail.setOd_option(rs.getString("od_option"));
				orderDetail.setBr_name(rs.getString("br_name"));
				orderDetail.setPi_name(rs.getString("pi_name"));
				orderDetail.setPi_price(rs.getInt("pi_price"));
				orderDetail.setPi_img1(rs.getString("pi_img1"));
				orderDetail.setPi_id(rs.getString("pi_id"));	
				orderDetail.setOi_memo(rs.getString("oi_memo"));
				orderDetail.setOd_id(rs.getString("od_id"));	       
	            
				orderInfo.add(orderDetail);
			 }
			
		} catch(Exception e) {
			System.out.println("getOrderInfo() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		return orderInfo;

	}
	
	public int updateMemo(String oiid, String odid, String memo) {	
		int result = 0;
		Statement stmt = null;

		try {
			stmt = conn.createStatement(); 			
			String sql = "update t_order_info set oi_memo = '" + memo + "' " +
						 "where oi_id = '" + oiid + "' ";
			//String sql = "update t_order_detail set od_cmt = '" + memo + "' " +
			//"where oi_id = '" + oiid + "' and od_id = '" + odid + "' ";
			System.out.println(sql);
			result = stmt.executeUpdate(sql);
			
		} catch(Exception e) {
			System.out.println("updateMemo() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}
	
	/*
	public ProductInfo getPdtInfo(String id) {
	// 지정한 id에 해당하는 특정 상품 정보를 ProductInfo형 인스턴스로 리턴하는 메소드
		Statement stmt = null;
		ResultSet rs = null;
		ProductInfo pdtInfo = null;
		// 하나의 상품 정보를 저장하여 리턴할  ProductInfo형 인스턴스를 선언
		
		try {
			String sql = "select * from v_pdt_list where pi_id = '" + id + "' ";
					
			System.out.println(sql);
			stmt = conn.createStatement();				
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
			// rs에 보여줄 게시글이 있을 경우 
				pdtInfo = new ProductInfo();

				pdtInfo.setPi_id(rs.getString("pi_id"));
				// 받아온 레코드로 상품 정보를 저장
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
	
	public int pdtInsert(ProductInfo pdtInfo) {		// 상품 등록 처리를 위한 메소드
		int result = 0;
		Statement stmt = null;
		ResultSet rs = null;
		// ResultSet : 상품 등록시 상품id를 새로 만들어야하는데 t_product_info 테이블에서 pi_id 컬럼이 ai가 아니므로
		// 현재 있는 값들 중 가장 큰 값을 가져와서 +1을 해주기 위해서 ResultSet이 필요함
		
		String piid = "pdt001";
		
		try {
			String sql = "select max(right(pi_id, 3)) from v_pdt_list";					
			//System.out.println(sql);
			stmt = conn.createStatement();				
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				if (rs.getString(1) != null) {	// 새로 등록할 상품의 id 생성
					int n = Integer.parseInt(rs.getString(1)) + 1;
					if (n < 10) 		piid = "pdt00" + n;
					else if (n < 100)	piid = "pdt0" + n;
					else				piid = "pdt" + n;
				} else {	// 등록된 상품이 하나도 없을 경우
					piid = "pdt001";
				}
			}	
			
			sql = "insert into t_product_info (pi_id, pi_name, cs_id, b_id, " + 
				" pi_origin, pi_cost, pi_price, pi_discount, pi_option, " + 
				" pi_img1, pi_img2, pi_img3, pi_desc, pi_stock, pi_isview, ai_idx) values " + 
				" ('" + piid + "', '" + pdtInfo.getPi_name() + "', '" + pdtInfo.getCs_id() +
				"', '" + pdtInfo.getB_id() + "', '" + pdtInfo.getPi_origin() + "', '" + pdtInfo.getPi_cost() + 
				"', '" + pdtInfo.getPi_price() + "', '" + pdtInfo.getPi_discount() + "', '" + pdtInfo.getPi_option() + 
				"', '" + pdtInfo.getPi_img1() + "', '" + pdtInfo.getPi_img2() + "', '" + pdtInfo.getPi_img3() + 
				"', '" + pdtInfo.getPi_desc() + "', '" + pdtInfo.getPi_stock() + 
				"', '" + pdtInfo.getPi_isview() + "', " + pdtInfo.getAi_idx() + " )";
			
			result = stmt.executeUpdate(sql);
			
		} catch(Exception e) {
			System.out.println("pdtInsert() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		
		return result;
	}
	

	public int pdtUpdate(ProductInfo pdtInfo) {		// 상품정보 수정 처리를 위한 메소드
		int result = 0;
		Statement stmt = null;
		
		try {
			stmt = conn.createStatement();				
			
			String sql = "update t_product_info set " + 
				"pi_name = '"	+ pdtInfo.getPi_name()		+ "', " + 
				"cs_id = '"		+ pdtInfo.getCs_id()		+ "', " + 
				"b_id = '"		+ pdtInfo.getB_id()			+ "', " + 
				"pi_origin = '"	+ pdtInfo.getPi_origin()	+ "', " + 
				"pi_cost = "	+ pdtInfo.getPi_cost()		+ ", " + 
				"pi_price = "	+ pdtInfo.getPi_price()		+ ", " + 
				"pi_discount = "+ pdtInfo.getPi_discount()	+ ", " + 
				"pi_option = '"	+ pdtInfo.getPi_option()	+ "', " + 
				"pi_img1 = '"	+ pdtInfo.getPi_img1()		+ "', " + 
				"pi_img2 = '"	+ pdtInfo.getPi_img2()		+ "', " + 
				"pi_img3 = '"	+ pdtInfo.getPi_img3()		+ "', " + 
				"pi_desc = '"	+ pdtInfo.getPi_desc()		+ "', " + 
				"pi_stock = "	+ pdtInfo.getPi_stock()		+ ", " + 
				"pi_isview = '"	+ pdtInfo.getPi_isview()	+ "' " + 
				"where pi_id = '" + pdtInfo.getPi_id()		+ "' ";
				
			result = stmt.executeUpdate(sql);
			
		} catch(Exception e) {
			System.out.println("pdtUpdate() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}
	*/

}
