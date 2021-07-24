package dao;

import static db.JdbcUtil.*;
import javax.sql.*;
import java.sql.*;
import java.util.*;
import vo.*;

public class ReviewDao {
	private static ReviewDao reviewDao;
	private Connection conn;
	private ReviewDao() {}
	
	public static ReviewDao getInstance() {
		if (reviewDao == null) {	
			reviewDao = new ReviewDao();
		}	
		return reviewDao;	
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	public int getReviewCount() {
	// 검색된 리뷰의 총 개수를 리턴하는 메소드
		Statement stmt = null;	
		ResultSet rs = null;	
		int rcnt = 0;	// 리뷰 개수를 저장할 변수
		
		try {
			String sql = "select count(*) from t_product_review where pr_isview = 'y' ";
			// 리뷰의 총 개수를 가져올 쿼리 작성
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {	
				rcnt = rs.getInt(1);
			}	// 리뷰의 개수를 rcnt에 저장(리뷰가 없으면 0이 저장됨)
			// count 함수는 결과값이 없을 수가 없으므로(값이 없으면 0이니까) if문을 사용하지 않아도 됨
			// 그러나 rs.next()는 반드시 해주어야 하므로 눈에 익숙한 형태로 보이기 위해 if문을 사용
			
		} catch(Exception e) {
			System.out.println("getReviewCount() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		return rcnt;
	}
	
	public ArrayList<ReviewInfo> getReviewList(String orderBy, int cpage, int psize) {
	// 검색된 리뷰 목록을 ArrayList<ReviewInfo>형 인스턴스로 리턴하는 메소드
		ArrayList<ReviewInfo> reviewList = new ArrayList<ReviewInfo>();
		// 리뷰 목록을 저장할 ArrayList로 오직 ReviewInfo형 인스턴스만 저장됨
		
		Statement stmt = null;
		ResultSet rs = null;
		
		ReviewInfo reviewInfo = null;
		// reviewInfo에 담을 데이터를 담을 ReviewInfo형 인스턴스를 선언
		
		int snum = (cpage - 1) * psize;
		// 쿼리의 limit 명령에서 데이터를 가져올 시작 인덱스 번호
		
		try {
			String sql = "select a.*, b.br_name, b.pi_name, b.pi_img1, c.od_pdtprice, c.od_cnt " + 
				"from t_product_review a, t_product_info b, t_order_detail c " + 
				"where a.pi_id = b.pi_id and a.od_id = c.od_id and a.pr_isview = 'y' " + 
				orderBy + " limit " + snum + ", " + psize;
			
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
			// rs에 보여줄 리뷰가 있을 경우 
				reviewInfo = new ReviewInfo();
				// reviewList에 저장할 한 리뷰의 정보를 담을 인스턴스 생성

				reviewInfo.setPr_idx(rs.getInt("pr_idx"));
				reviewInfo.setMi_email(rs.getString("mi_email"));
				reviewInfo.setOd_id(rs.getString("od_id"));
				reviewInfo.setPi_id(rs.getString("pi_id"));
				reviewInfo.setBr_opt(rs.getString("br_opt"));
				reviewInfo.setPr_content(rs.getString("pr_content"));
				reviewInfo.setPr_img1(rs.getString("pr_img1"));
				reviewInfo.setPr_img2(rs.getString("pr_img2"));
				reviewInfo.setPr_img3(rs.getString("pr_img3"));
				reviewInfo.setPr_star(rs.getInt("pr_star"));
				reviewInfo.setPr_isview(rs.getString("pr_isview"));
				reviewInfo.setPr_date(rs.getString("pr_date"));
				reviewInfo.setBr_name(rs.getString("br_name"));
				reviewInfo.setPi_name(rs.getString("pi_name"));
				reviewInfo.setPi_img1(rs.getString("pi_img1"));
				reviewInfo.setOd_pdtprice(rs.getInt("od_pdtprice"));
				reviewInfo.setOd_cnt(rs.getInt("od_cnt"));
	            // 받아온 레코드들로 리뷰 정보를 저장
				
				reviewList.add(reviewInfo);
				// 한 리뷰의 정보를 담은 reviewInfo 인스턴스를 reviewList에 저장
			 }
			
		} catch(Exception e) {
			System.out.println("getReviewList() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		
		return reviewList;
		
	}
	
	public int getReviewWriteCount() {
	// 검색된 리뷰 가능 상품의 총 개수를 리턴하는 메소드
		Statement stmt = null;	
		ResultSet rs = null;	
		int rcnt = 0;	// 리뷰 가능 상품 개수를 저장할 변수
		
		try {
			String sql = "select count(*) " + 
				"from t_order_detail a, t_product_info b, t_order_info c " + 
				"where a.pi_id = b.pi_id and a.oi_id = c.oi_id and " + 
				"c.oi_status = 'f' and a.od_status = 'n'";
			// 리뷰 가능 상품의 총 개수를 가져올 쿼리 작성
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				rcnt = rs.getInt(1);
			}	// 리뷰 가능 상품의 개수를 rcnt에 저장(리뷰가 없으면 0이 저장됨)
			// count 함수는 결과값이 없을 수가 없으므로(값이 없으면 0이니까) if문을 사용하지 않아도 됨
			// 그러나 rs.next()는 반드시 해주어야 하므로 눈에 익숙한 형태로 보이기 위해 if문을 사용
			
		} catch(Exception e) {
			System.out.println("getReviewWriterCount() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		return rcnt;
	}
	
	public ArrayList<ReviewInfo> getReviewWriteList(int cpage, int psize) {
	// 검색된 리뷰 가능 상품 목록을 ArrayList<ReviewInfo>형 인스턴스로 리턴하는 메소드
		ArrayList<ReviewInfo> reviewList = new ArrayList<ReviewInfo>();
		// 리뷰 가능 상품 목록을 저장할 ArrayList로 오직 ReviewInfo형 인스턴스만 저장됨
		
		Statement stmt = null;
		ResultSet rs = null;
		
		ReviewInfo reviewInfo = null;
		// reviewInfo에 담을 데이터를 담을 ReviewInfo형 인스턴스를 선언
		
		int snum = (cpage - 1) * psize;
		// 쿼리의 limit 명령에서 데이터를 가져올 시작 인덱스 번호
		
		try {
			String sql = "select a.*, b.br_name, b.pi_name, b.pi_img1 " + 
				"from t_order_detail a, t_product_info b, t_order_info c " + 
				"where a.pi_id = b.pi_id and a.oi_id = c.oi_id and " + 
				"c.oi_status = 'f' and a.od_status = 'n' " + 
				"order by oi_date desc limit " + snum + ", " + psize;
			
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
			// rs에 보여줄 리뷰 가능 상품이 있을 경우 
				reviewInfo = new ReviewInfo();
				// reviewList에 저장할 한 리뷰 가능 상품의 정보를 담을 인스턴스 생성

				reviewInfo.setOd_id(rs.getString("od_id"));
				reviewInfo.setOi_id(rs.getString("oi_id"));
				reviewInfo.setPi_id(rs.getString("pi_id"));
				reviewInfo.setOd_pdtprice(rs.getInt("od_pdtprice"));
				reviewInfo.setOd_cnt(rs.getInt("od_cnt"));
				reviewInfo.setOd_option(rs.getString("od_option"));
				reviewInfo.setOd_status(rs.getString("od_status"));
				reviewInfo.setBr_name(rs.getString("br_name"));
				reviewInfo.setPi_name(rs.getString("pi_name"));
				reviewInfo.setPi_img1(rs.getString("pi_img1"));
	            // 받아온 레코드들로 리뷰 가능 상품 정보를 저장
				
				reviewList.add(reviewInfo);
				// 한  리뷰 가능 상품의 정보를 담은 reviewInfo 인스턴스를 reviewList에 저장
			 }
			
		} catch(Exception e) {
			System.out.println("getReviewWriterList() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		
		return reviewList;
		
	}
	
	public ReviewInfo getReviewInfo(int idx) {
	// 지정한 idx에 해당하는 특정 상품 정보를 ReviewInfo형 인스턴스로 리턴하는 메소드
		ReviewInfo reviewInfo = null;
		Statement stmt = null;
		ResultSet rs = null;
      
		try {
			String sql = "select a.*, b.br_name, b.pi_name, b.pi_img1, c.od_pdtprice, c.od_cnt " + 
					"from t_product_review a, t_product_info b, t_order_detail c " + 
					"where a.pi_id = b.pi_id and a.od_id = c.od_id and a.pr_isview = 'y' and pr_idx = " + idx;
			
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				reviewInfo = new ReviewInfo();

				reviewInfo.setPr_idx(rs.getInt("pr_idx"));
				reviewInfo.setMi_email(rs.getString("mi_email"));
				reviewInfo.setOd_id(rs.getString("od_id"));
				reviewInfo.setPi_id(rs.getString("pi_id"));
				reviewInfo.setBr_opt(rs.getString("br_opt"));
				reviewInfo.setPr_content(rs.getString("pr_content"));
				reviewInfo.setPr_img1(rs.getString("pr_img1"));
				reviewInfo.setPr_img2(rs.getString("pr_img2"));
				reviewInfo.setPr_img3(rs.getString("pr_img3"));
				reviewInfo.setPr_star(rs.getInt("pr_star"));
				reviewInfo.setPr_isview(rs.getString("pr_isview"));
				reviewInfo.setPr_date(rs.getString("pr_date"));
				reviewInfo.setBr_name(rs.getString("br_name"));
				reviewInfo.setPi_name(rs.getString("pi_name"));
				reviewInfo.setPi_img1(rs.getString("pi_img1"));
				reviewInfo.setOd_pdtprice(rs.getInt("od_pdtprice"));
				reviewInfo.setOd_cnt(rs.getInt("od_cnt"));
			}
        
		} catch(Exception e) {
			System.out.println("getReviewInfo() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
      
		return reviewInfo;
	}
	
	public ReviewInfo getOrderInfo(String id) {
	// 지정한 id에 해당하는 특정 주문 상세 정보를 ReviewInfo형 인스턴스로 리턴하는 메소드
		ReviewInfo orderInfo = null;
		Statement stmt = null;
		ResultSet rs = null;
      
		try {
			String sql = "select a.* , b.br_name, b.pi_name, b.pi_img1 " + 
					"from t_order_detail a, t_product_info b " + 
					"where a.pi_id = b.pi_id and a.od_id = '" + id + "'";
			
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				orderInfo = new ReviewInfo();

				orderInfo.setOd_id(rs.getString("od_id"));
				orderInfo.setOi_id(rs.getString("oi_id"));
				orderInfo.setPi_id(rs.getString("pi_id"));
				orderInfo.setOd_pdtprice(rs.getInt("od_pdtprice"));
				orderInfo.setOd_cnt(rs.getInt("od_cnt"));
				orderInfo.setOd_option(rs.getString("od_option"));
				orderInfo.setBr_name(rs.getString("br_name"));
				orderInfo.setPi_name(rs.getString("pi_name"));
				orderInfo.setPi_img1(rs.getString("pi_img1"));
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
	
	public int reviewInsert(ReviewInfo reviewInfo) {	// 리뷰 등록 처리를 위한 메소드
		int result = 0;
		int pr_idx = 0, mp_point = 0;
		String mp_content = "", mp_info = "", br_name = "", pi_name = "";
		Statement stmt = null;
		ResultSet rs = null;
		
		if (reviewInfo.getPr_img1() == null || reviewInfo.getPr_img1().equals("") || reviewInfo.getPr_img1().equals("null")) {
			mp_point = 100;
			mp_content = "일반리뷰 작성";
		} else {
			mp_point = 500;
			mp_content = "포토리뷰 작성";
		}
		
		try {
			// 리뷰 번호 저장
			String sql = "select max(pr_idx) from t_product_review";
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			pr_idx = rs.getInt(1) + 1;
			
			// 리뷰 테이블 insert
			sql = "insert into t_product_review (mi_email, od_id, pi_id, br_opt, pr_content, pr_img1, pr_img2, pr_img3, pr_star, pr_isview) " + 
				" values ('" + 
				reviewInfo.getMi_email()	+ "', '" + 
				reviewInfo.getOd_id()		+ "', '" + 
				reviewInfo.getPi_id()		+ "', '" + 
				reviewInfo.getBr_opt()		+ "', '" + 
				reviewInfo.getPr_content()	+ "', '" + 
				reviewInfo.getPr_img1()		+ "', '" + 
				reviewInfo.getPr_img2()		+ "', '" + 
				reviewInfo.getPr_img3()		+ "', " + 
				reviewInfo.getPr_star()		+ ", 'y')";
			System.out.println(sql);
			result = stmt.executeUpdate(sql);
			
			// mp_info에 들어갈 값 저장
			sql = "select a.br_name, a.pi_name from t_product_info a, t_order_detail b, t_product_review c " + 
				"where a.pi_id = b.pi_id and b.od_id = c.od_id and c.pr_idx = '" + pr_idx + "'";
			System.out.println(sql);
			rs = stmt.executeQuery(sql);
			rs.next();
			br_name = rs.getString("br_name");
			pi_name = rs.getString("pi_name");
			mp_info = "[" + br_name + "] " + pi_name;
			
			// 리뷰 작성 포인트 지급
			sql = "insert into t_member_point (mi_email, mp_point, mp_kind, mp_linkidx, mp_content, mp_info) " + 
				" values ('" + 
				reviewInfo.getMi_email()	+ "', '" + 
				mp_point					+ "', 'b', '" + 
				pr_idx		+ "', '" + 
				mp_content					+ "', '" + 
				mp_info						+ "')";
			System.out.println(sql);
			result += stmt.executeUpdate(sql);
			
			// 리뷰 작성 여부 'y'로 업데이트
			sql = "update t_order_detail set " + 
				"od_status = 'y' "	+ 
				"where od_id = '"	+ reviewInfo.getOd_id() + "'";
			System.out.println(sql);
			result += stmt.executeUpdate(sql);
			
		} catch(Exception e) {
			System.out.println("reviewInsert() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}
	
	public int reviewUpdate(ReviewInfo reviewInfo) {	// 리뷰 수정 처리를 위한 메소드
		int result = 0;
		Statement stmt = null;

		String img1 = "", img2 = "", img3 = "";
		if (reviewInfo.getPr_img1().equals("null"))	img1 = "";
		else										img1 = reviewInfo.getPr_img1();
		if (reviewInfo.getPr_img2().equals("null"))	img2 = "";
		else										img2 = reviewInfo.getPr_img2();
		if (reviewInfo.getPr_img3().equals("null"))	img3 = "";
		else										img3 = reviewInfo.getPr_img3();
		
		try {
			stmt = conn.createStatement(); 			
			String sql = "update t_product_review set " + 
				"pr_content = '"	+ reviewInfo.getPr_content()+ "', " + 
				"pr_img1 = '"		+ img1	+ "', " + 
				"pr_img2 = '"		+ img2	+ "', " + 
				"pr_img3 = '"		+ img3	+ "', " + 
				"pr_star = '"		+ reviewInfo.getPr_star()	+ "' " + 
				"where pr_idx = "	+ reviewInfo.getPr_idx()	+ " " + 
				"and mi_email = '"	+ reviewInfo.getMi_email()	+ "' ";
			System.out.println(sql);
			result = stmt.executeUpdate(sql);
			
		} catch(Exception e) {
			System.out.println("reviewUpdate() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}
}
