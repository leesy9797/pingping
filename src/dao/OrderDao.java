package dao;

import static db.JdbcUtil.*;
import javax.sql.*;
import java.sql.*;
import java.util.*;
import vo.*;

public class OrderDao {
	private static OrderDao orderDao;
	private Connection conn;
	private OrderDao() {}
	
	public static OrderDao getInstance() {
		if (orderDao == null) {	
			orderDao = new OrderDao();
		}
		return orderDao;
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	public ArrayList<CartInfo> getCartList(String miemail, String where) {
	// 장바구니에서 보여줄 특정 회원의 장바구니 목록 또는 결제폼에서 보여줄 결제할 상품 목록을 리턴하는 메소드
		ArrayList<CartInfo> cartList = new ArrayList<CartInfo>();
		Statement stmt = null;
		ResultSet rs = null;
		CartInfo cart = null;

		try {
			String sql = "select a.*, b.pi_name, b.pi_img1, b.br_name, b.pi_option, " + 
			"b.pi_price, b.pi_stock, b.pi_dcrate from t_order_cart a, t_product_info b " + 
			"where a.pi_id = b.pi_id and b.pi_isview = 'y' and " + 
			"(b.pi_stock >= a.oc_cnt or b.pi_stock = -1) and a.mi_email = '" + miemail + "' " +
			where + " order by a.pi_id ";
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				cart = new CartInfo();
				cart.setOc_idx(rs.getInt("oc_idx"));
				cart.setOc_cnt(rs.getInt("oc_cnt"));
				cart.setMi_email(rs.getString("mi_email"));
				cart.setPi_id(rs.getString("pi_id"));
				cart.setOc_option(rs.getString("oc_option"));
				cart.setPi_name(rs.getString("pi_name"));
				cart.setPi_img1(rs.getString("pi_img1"));
				cart.setBr_name(rs.getString("br_name"));
				cart.setPi_option(rs.getString("pi_option"));
				cart.setPi_stock(rs.getInt("pi_stock"));
				int price = rs.getInt("pi_price");
				if (rs.getInt("pi_dcrate") > 0) {	// 할인율이 있으면
					float rate = (float)rs.getInt("pi_dcrate") / 100;	// 할인율
					price = Math.round(price - (price * rate));
					// 할인율이 있는 상품일 경우 할인율을 적용한 가격을 저장함
				}
				cart.setPi_price(price);

				cartList.add(cart);
			}
		} catch(Exception e) {
			System.out.println("getCartList() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return cartList;
	}
	
	public int cartInsert(CartInfo cart) {
	// 장바구니에 특정 상품을 등록하는 메소드
		int result = 0;
		
		Statement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.createStatement();
			
			String sql = "select a.oc_idx, a.oc_cnt, b.pi_stock " + 
						 "from t_order_cart a, t_product_info b where a.pi_id = b.pi_id and " + 
						 "a.mi_email = '" + cart.getMi_email() + "' and a.pi_id = '" + 
						 cart.getPi_id() + "' and a.oc_option = '" + cart.getOc_option() + "'";
			System.out.println("첫 번째 sql : " + sql);
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
			// 현재 추가하려는 상품과 동일한 상품이 이미 장바구니에 있을 경우
				sql = "update t_order_cart set oc_cnt = oc_cnt + " + cart.getOc_cnt() + 
					  " where oc_idx = " + rs.getInt("oc_idx");
				
				if (rs.getInt("pi_stock") > 0)	{	// 재고가 무제한(-1)이 아니면
					sql += " and (oc_cnt + " + cart.getOc_cnt() + ") <= " + rs.getInt("pi_stock");
				}
				System.out.println("두 번째 sql : " + sql);
				
			} else {
				sql = "insert into t_order_cart (mi_email, pi_id, oc_option, oc_cnt) " + 
					  "values ('" + cart.getMi_email() + "', '" + cart.getPi_id() + 
					  "', '" + cart.getOc_option() + "', " + cart.getOc_cnt() + ")";
				System.out.println("두 번째 sql : " + sql);
			}
			
			result = stmt.executeUpdate(sql);

		} catch(Exception e) {
			System.out.println("cartInsert() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return result;
	}

	public int cartUpdate(String kind, String op, int idx, String miemail, String piid) {
	// 장바구니 내 수량 및 옵션 변경 메소드
		Statement stmt = null;
		ResultSet rs = null;
		
		int result = 0;

		try {
			stmt = conn.createStatement();
			String sql = "";
			
			if (kind.equals("cnt")) {	// 수량 변경일 경우
				sql = "update t_order_cart set oc_cnt =  oc_cnt " + op + " 1 " + 
					  " where mi_email = '" + miemail + "' and oc_idx = " + idx;
			} else {	// 옵션 변경일 경우
				sql = "select oc_idx, oc_cnt from t_order_cart " + 
					  "where mi_email = '" + miemail + "' and  pi_id = '" + piid + 
					  "' and oc_option = '" + op + "' ";
				System.out.println("첫 번째 sql : " + sql);
				
				// 현재 회원의 장바구니 속에 동일한 상품의 동일한 옵션이 있는지 검사할 쿼리
				rs = stmt.executeQuery(sql);
				if (rs.next()) {	// 동일한 상품의 동일한 옵션이 있으면
					sql = "update t_order_cart set oc_option = '" + op + "', oc_cnt = oc_cnt + " + rs.getInt("oc_cnt") +
						  " where oc_idx = " + idx;
					// 변경하는 상품의 개수를 기존 상품의 개수와 합침
					
					String where = " where mi_email = '" + miemail +  "' and oc_idx = " + rs.getInt("oc_idx"); 
					// 기존에 있던 동일상품 동일옵션의 카트 레코드를 삭제
					cartDelete(where);
					
				} else {	// 처음 선택되는 옵션이면
					sql = "update t_order_cart set oc_option = '" + op + "' where oc_idx = " + idx; 
				}
				System.out.println("두 번째 sql : " + sql);
			}
			
			result = stmt.executeUpdate(sql);

		} catch(Exception e) {
			System.out.println("cartUpdate() 메소드 오류");
			e.printStackTrace();
		} finally {
			if (kind.equals("opt"))	close(rs);	// 옵션변경일 경우에만 rs를 닫아줌
			close(stmt);
		}
		
		
		return result;
	}
	
	public int cartDelete(String where) {
	// 장바구니 내 상품 삭제 메소드
		Statement stmt = null;		
		int result = 0;

		try {
			stmt = conn.createStatement();
			String sql = "delete from t_order_cart " + where;
			System.out.println(sql);
			result = stmt.executeUpdate(sql);

		} catch(Exception e) {
			System.out.println("cartDelete() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		
		return result;
	}

	public int getOrderCount(String miemail) {
		Statement stmt = null;	
		ResultSet rs = null;	
		int rcnt = 0;	
		
		try {
			String sql = "select count(*) from t_order_info where mi_email = '" + miemail + "' ";

			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {	
				rcnt = rs.getInt(1);
			}	// 주문목록의 개수를 rcnt에 저장(주문목록이 없으면 0이 저장됨)
			
		} catch(Exception e) {
			System.out.println("getOrderCount() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		return rcnt;
	}
	
	public int getOrderListCount(String miemail, String where) {
		ArrayList<CartInfo> orderList = new ArrayList<CartInfo>();
		Statement stmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		CartInfo orderInfo = null;
		int cnt = 0;
		try {
			String sql = "select count(*) from t_order_info " + where + " and mi_email = '" + miemail + "' ";
			System.out.println(sql);
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			cnt = rs.getInt(1);
			
		} catch(Exception e) {
			System.out.println("getOrderListCount() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return cnt;
	}
	
	public ArrayList<CartInfo> getOrderList(String miemail, String where, int cpage, int psize) {
	// 주문목록에서 보여줄 특정 회원의 주문목록을 리턴하는 메소드
		ArrayList<CartInfo> orderList = new ArrayList<CartInfo>();
		Statement stmt = null;
		ResultSet rs = null;
		CartInfo orderInfo = null;

		int snum = (cpage - 1) * psize;
		
		try {			
			String sql = "select * from t_order_info a, t_order_detail b, t_product_info c " +
						 where + " and mi_email = '" + miemail + "' " + " group by a.oi_id order by a.oi_id desc limit " + snum + ", " + psize;
			System.out.println(sql);
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				orderInfo = new CartInfo();

				orderInfo.setOi_id(rs.getString("oi_id"));
				orderInfo.setMi_email(rs.getString("mi_email"));
				orderInfo.setOi_name(rs.getString("oi_name"));
				orderInfo.setOi_phone(rs.getString("oi_phone"));
				orderInfo.setOi_zip(rs.getString("oi_zip"));
				orderInfo.setOi_addr1(rs.getString("oi_addr1"));
				orderInfo.setOi_addr2(rs.getString("oi_addr2"));
				orderInfo.setOi_cmt(rs.getString("oi_cmt"));
				orderInfo.setOi_payment(rs.getString("oi_payment"));
				orderInfo.setOi_pay(rs.getInt("oi_pay"));
				orderInfo.setOi_usepoint(rs.getInt("oi_usepoint"));
				orderInfo.setOi_delipay(rs.getInt("oi_delipay"));
				orderInfo.setOi_status(rs.getString("oi_status"));
				orderInfo.setOi_pdtprice(rs.getInt("oi_pdtprice"));
				orderInfo.setOi_invoice(rs.getString("oi_invoice"));
				orderInfo.setOi_rebank(rs.getString("oi_rebank"));
				orderInfo.setOi_reaacount(rs.getString("oi_reaccount"));
				orderInfo.setOi_redepositer(rs.getString("oi_redepositer"));
				orderInfo.setOi_date(rs.getString("oi_date"));
				orderInfo.setOi_last(rs.getString("oi_date"));
				orderInfo.setAi_idx(rs.getInt("ai_idx"));
				orderInfo.setPi_id(rs.getString("pi_id"));
				orderInfo.setOd_pdtprice(rs.getInt("od_pdtprice"));
				orderInfo.setOd_cnt(rs.getInt("od_cnt"));
				orderInfo.setOd_option(rs.getString("od_option"));
				orderInfo.setBr_name(rs.getString("br_name"));
				orderInfo.setPi_name(rs.getString("pi_name"));
				orderInfo.setPi_price(rs.getInt("pi_price"));
				orderInfo.setPi_img1(rs.getString("pi_img1"));
								
				orderList.add(orderInfo);
			}
		} catch(Exception e) {
			System.out.println("getOrderList() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}

		return orderList;
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
	
	public int buyProduct(String kind, CartInfo buyInfo) {
		int result = 0;
		
		Statement stmt = null;
		ResultSet rs = null;

		try {			
			String oiid = "";
			
			Calendar today = Calendar.getInstance();
			int year = today.get(Calendar.YEAR);
			int month = today.get(Calendar.MONTH) + 1;
			int day= today.get(Calendar.DATE);
			
			String oidate = year + "-" + (month < 10 ? "0" + month : month) + "-" + (day < 10 ? "0" + day : day);
			
			// oiid 구하기
			String sql = "select max(oi_id) from t_order_info where oi_date >= '" + oidate + "' ";
			System.out.println("sql1 : " + sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				if (rs.getString(1) == null || rs.getString(1).equals("null") || rs.getString(1).equals("")) {
					oiid = String.valueOf(year).substring(2) + (month < 10 ? "0" + month : month) + (day < 10 ? "0" + day : day) + "1001"; 
				//System.out.println(oiid);
				} else {
				int tmpoiid = (Integer.parseInt(rs.getString(1)) + 1);
				oiid = Integer.toString(tmpoiid);
				}
			}

			System.out.println(oiid);
			
			if (buyInfo != null) {	// t_order_info(주문정보) 인서트
				sql = "insert into t_order_info(oi_id, mi_email, oi_name, oi_phone, oi_zip, oi_addr1, oi_addr2, oi_cmt, oi_payment, oi_pay, oi_usepoint, oi_delipay, oi_status, oi_pdtprice) " + 
						"values ('" + oiid + "', '" + buyInfo.getMi_email() + "', '" + buyInfo.getOi_name() +  "', '" + buyInfo.getOi_phone() +
						"', '" + buyInfo.getOi_zip() + "', '" + buyInfo.getOi_addr1() + "', '" + buyInfo.getOi_addr2() +
						"', '" + buyInfo.getOi_cmt() +  "', '" + buyInfo.getOi_payment() +  "', '" + buyInfo.getOi_pay() + 
						"', '" + buyInfo.getOi_usepoint() +  "', '" + buyInfo.getOi_delipay() +  "', '" + buyInfo.getOi_status() +  "', '" + buyInfo.getOi_pdtprice() + "')";
				System.out.println("sql2 : " + sql);
				//oiid = oiid + 1;

				result += stmt.executeUpdate(sql);
			
			}
		} catch(Exception e) {
			System.out.println("buyProduct() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return result;
	}

	public int addOrderDetail(String kind, CartInfo buyInfo, ArrayList<CartInfo> tmpList) {
		int result = 0;
		
		Statement stmt = null;
		ResultSet rs = null;

		try {			
			String oiid = "";
			
			Calendar today = Calendar.getInstance();
			int year = today.get(Calendar.YEAR);
			int month = today.get(Calendar.MONTH) + 1;
			int day= today.get(Calendar.DATE);
			
			String oidate = year + "-" + (month < 10 ? "0" + month : month) + "-" + (day < 10 ? "0" + day : day);
			
			// oiid 구하기
			String sql = "select max(oi_id) from t_order_info where oi_date >= '" + oidate + "' ";
			System.out.println("sql1 : " + sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				int tmpoiid = (Integer.parseInt(rs.getString(1)));
				oiid = Integer.toString(tmpoiid);
			}

			System.out.println(oiid);

			int num = 11;
			System.out.println(tmpList.size());
			
			for (int i = 0; i < tmpList.size() ; i++) {
				CartInfo orderInfo = tmpList.get(i);
				sql = "insert into t_order_detail(od_id, oi_id, pi_id, od_pdtprice, od_cnt, od_option) " + 
					  "values ('" + oiid + "_" + num + "', '" + oiid + "', '" + orderInfo.getPi_id() + "', '" + 
					  orderInfo.getOd_pdtprice() + "', '" + orderInfo.getOd_cnt() + "', '" + orderInfo.getOd_option() + "')";
				System.out.println("sql3 : " + sql);
				result += stmt.executeUpdate(sql);
				num += 1;
			}
			
		} catch(Exception e) {
			System.out.println("addOrderDetail() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return result;
	}
	
	public int delOrderInfo(String kind, CartInfo buyInfo) {
		int result = 0;
		
		Statement stmt = null;
		ResultSet rs = null;

		try {			
			String oiid = "";
			
			Calendar today = Calendar.getInstance();
			int year = today.get(Calendar.YEAR);
			int month = today.get(Calendar.MONTH) + 1;
			int day= today.get(Calendar.DATE);
			
			String oidate = year + "-" + (month < 10 ? "0" + month : month) + "-" + (day < 10 ? "0" + day : day);
			
			// oiid 구하기
			String sql = "select max(oi_id) from t_order_info where oi_date >= '" + oidate + "' and mi_email = '" + buyInfo.getMi_email() + "' ";
			System.out.println("sql1 : " + sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				int tmpoiid = (Integer.parseInt(rs.getString(1)));
				oiid = Integer.toString(tmpoiid);
			}

			System.out.println(oiid);

			sql = "delete from t_order_info where oi_id = '" + oiid + "' and mi_email = '" + buyInfo.getMi_email() + "' ";
			System.out.println("sql3 : " + sql);
			result += stmt.executeUpdate(sql);
			
		} catch(Exception e) {
			System.out.println("delOrderInfo() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return result;
	}
	
	public int purchaseComfirmed(CartInfo orderInfo) {
		Statement stmt = null;		
		int result = 0;

		try {
			stmt = conn.createStatement();
			String sql = "update t_order_info set oi_status = 'e'  where oi_id = '" + orderInfo.getOi_id() + "' and mi_email = '" + orderInfo.getMi_email() + "' ";
			System.out.println(sql);
			result = stmt.executeUpdate(sql);
			
			sql = "update t_member_info set mi_point = mi_point + " + (orderInfo.getOi_pdtprice() / 100 *3) + 
				  " where mi_email = '" + orderInfo.getMi_email() + "' ";
			System.out.println(sql);
			result += stmt.executeUpdate(sql);

			sql = "insert into t_member_point (mi_email, mp_point, mp_kind, mp_linkidx, mp_content, ai_idx) " + 
				  "values('" + orderInfo.getMi_email() + "', '" + (orderInfo.getOi_pdtprice() * 0.03) + "', 'a', '" + 
				  orderInfo.getOi_id() + "', '구매확정 포인트 " + (orderInfo.getOi_pdtprice() / 100 *3) + "P 지급', '1') ";
			System.out.println(sql);
			result += stmt.executeUpdate(sql);
			
		} catch(Exception e) {
			System.out.println("purchaseComfirmed() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}
}