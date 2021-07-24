package admin.dao;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import javax.sql.*;
import vo.*;

public class AdminMonthDao {
	private static AdminMonthDao adminMonthDao;
	private Connection conn;
	private AdminMonthDao() {}      
	
	public static AdminMonthDao getInstance() {
		if (adminMonthDao == null)   adminMonthDao = new AdminMonthDao();
		return adminMonthDao;
	}	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	public int getMonthCount(String where) {
		Statement stmt = null;   
		ResultSet rs = null;   
		int rcnt = 0;         
		
		try {
			String sql = "select count(*) from t_monthly_rec " + where;
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())   rcnt = rs.getInt(1);
			
		} catch(Exception e) {
			System.out.println("getMonthCount()메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return rcnt;
	}
	
	public ArrayList<MonthlyInfo> getMonthList(String where, String orderBy, int cpage, int psize) {
	// 검색된 게시글 목록을 ArrayList<MonthlyInfo> 형 인스턴스로 리턴하는 메소드
		ArrayList<MonthlyInfo> monthList = new ArrayList<MonthlyInfo>();
		// 게시글 목록을 저장할 ArrayList로 오직 MonthlyInfo형 인스턴스만 저장됨
		Statement stmt = null;
		ResultSet rs = null;
		MonthlyInfo monthInfo = null;
		// monthList에 담을 데이터인 MonthlyInfo 형 인스턴스를 선언   
		int snum = (cpage - 1) * psize;
		// 쿼리의 limit 명령에서 데이터를 가져올 시작 인덱스 번호
		
		try {
			String sql = "select * from t_monthly_rec " + where + orderBy + " limit " + snum + ", " + psize;
			System.out.println(sql);
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
			// rs에 보여줄 게시글이 있을 경우
				monthInfo = new MonthlyInfo();
				// monthList에 저장할 하나의 게시글 정보를 담을 인스턴스 생성

				monthInfo.setMr_idx(rs.getInt("mr_idx"));
				monthInfo.setMr_title(rs.getString("mr_title"));
				monthInfo.setMr_name(rs.getString("mr_name"));
				monthInfo.setMr_content(rs.getString("mr_content"));
				monthInfo.setMr_location(rs.getString("mr_location"));
				monthInfo.setMr_url(rs.getString("mr_url"));
				monthInfo.setMr_keyword(rs.getString("mr_keyword"));
				monthInfo.setMr_imgs(rs.getString("mr_imgs"));
				monthInfo.setMr_pdtids(rs.getString("mr_pdtids"));
				monthInfo.setMr_info(rs.getString("mr_info"));
				monthInfo.setMr_reply(rs.getInt("mr_reply"));
				monthInfo.setMr_read(rs.getInt("mr_read"));
				monthInfo.setMr_good(rs.getInt("mr_good"));
				monthInfo.setMr_month(rs.getString("mr_month"));
				monthInfo.setMr_isview(rs.getString("mr_isview"));
				monthInfo.setMr_date(rs.getString("mr_date"));
				monthInfo.setAi_idx(rs.getInt("ai_idx"));
				monthInfo.setLast_date(rs.getString("last_date"));
				monthInfo.setLast_admin(rs.getInt("last_admin"));
				// 받아온 레코드들로 게시글 정보를 저장
				
				monthList.add(monthInfo);
				// 한 상품의 정보를 담은 monthInfo 인스턴스를 monthList에 저장
			}
         
		} catch(Exception e) {
			System.out.println("getMonthList() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
      
		return monthList;
	}
	
	public MonthlyInfo getMonthInfo(int idx) {
	// 지정한 idx에 해당하는 특정 게시글 정보를 MonthlyInfo형 인스턴스로 리턴하는 메소드
		MonthlyInfo monthInfo = null;
		Statement stmt = null;
		ResultSet rs = null;
      
		try {
			String sql = "select * from t_monthly_rec where mr_idx = " + idx + " ";
			
			System.out.println(sql);
			stmt = conn.createStatement();   
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {   
				monthInfo = new MonthlyInfo();

				monthInfo.setMr_idx(rs.getInt("mr_idx"));
				monthInfo.setMr_title(rs.getString("mr_title"));
				monthInfo.setMr_name(rs.getString("mr_name"));
				monthInfo.setMr_content(rs.getString("mr_content"));
				monthInfo.setMr_location(rs.getString("mr_location"));
				monthInfo.setMr_url(rs.getString("mr_url"));
				monthInfo.setMr_keyword(rs.getString("mr_keyword"));
				monthInfo.setMr_imgs(rs.getString("mr_imgs"));
				monthInfo.setMr_pdtids(rs.getString("mr_pdtids"));
				monthInfo.setMr_info(rs.getString("mr_info"));
				monthInfo.setMr_reply(rs.getInt("mr_reply"));
				monthInfo.setMr_read(rs.getInt("mr_read"));
				monthInfo.setMr_good(rs.getInt("mr_good"));
				monthInfo.setMr_month(rs.getString("mr_month"));
				monthInfo.setMr_isview(rs.getString("mr_isview"));
				monthInfo.setMr_date(rs.getString("mr_date"));
				monthInfo.setAi_idx(rs.getInt("ai_idx"));
				monthInfo.setLast_date(rs.getString("last_date"));
				monthInfo.setLast_admin(rs.getInt("last_admin"));
			}
         
		} catch(Exception e) {
			System.out.println("getMonthInfo() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
      
		return monthInfo;
	}
	
	public int monthInsert(MonthlyInfo monthInfo) {	// 게시글 등록 처리를 위한 메소드
		int result = 0;
		Statement stmt = null;
		
		try {
			String sql = "insert into t_monthly_rec (mr_title, mr_name, mr_content, " + 
				"mr_location, mr_url, mr_keyword, mr_imgs, mr_pdtids, mr_info, mr_month, " + 
				"mr_isview, ai_idx, last_admin) values " + "('" + 
				monthInfo.getMr_title()		+ "', '" + 
				monthInfo.getMr_name()		+ "', '" + 
				monthInfo.getMr_content()	+ "', '" + 
				monthInfo.getMr_location()	+ "', '" + 
				monthInfo.getMr_url()		+ "', '" + 
				monthInfo.getMr_keyword()	+ "', '" + 
				monthInfo.getMr_imgs()		+ "', '" + 
				//monthInfo.getMr_pdtids()	+ "', '" + 상품등록 나중에
				"', '" +
				monthInfo.getMr_info()		+ "', '" + 
				monthInfo.getMr_month()		+ "', '" + 
				monthInfo.getMr_isview()	+ "', " + 
				monthInfo.getAi_idx()		+ ", " + 
				monthInfo.getAi_idx()		+ ")";		// 등록일 경우 ai_idx = last_admin
			System.out.println(sql);
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
			
		} catch(Exception e) {
			System.out.println("monthInsert() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}
	
	public int monthUpdate(MonthlyInfo monthInfo) {	// 게시글 수정 처리를 위한 메소드
		int result = 0;
		Statement stmt = null;

		String imgs = "";
		if (monthInfo.getMr_imgs().equals("null"))	imgs = "";
		else										imgs = monthInfo.getMr_imgs();
		
		try {
			stmt = conn.createStatement(); 			
			String sql = "update t_monthly_rec set " + 
				"mr_title = '"		+ monthInfo.getMr_title()	+ "', " + 
				"mr_name = '"		+ monthInfo.getMr_name()	+ "', " + 
				"mr_content = '"	+ monthInfo.getMr_content()	+ "', " + 
				"mr_location = '"	+ monthInfo.getMr_location()+ "', " + 
				"mr_url = '"		+ monthInfo.getMr_url()		+ "', " + 
				"mr_keyword = '" 	+ monthInfo.getMr_keyword()	+ "', " + 
				"mr_imgs = '" 		+ imgs						+ "', " + 
				"mr_pdtids = '"		+ monthInfo.getMr_pdtids()	+ "', " + 
				"mr_info = '"		+ monthInfo.getMr_info()	+ "', " + 
				"mr_month = '"		+ monthInfo.getMr_month()	+ "', " + 
				"mr_isview = '"		+ monthInfo.getMr_isview()	+ "', " + 
				"last_date = now(), " +										// 마지막으로 수정한 날짜
				"last_admin = "	+ monthInfo.getLast_admin()	+ " " +	// 마지막으로 수정한 관리자번호
				"where mr_idx = "	+ monthInfo.getMr_idx();
			System.out.println(sql);
			result = stmt.executeUpdate(sql);
			
		} catch(Exception e) {
			System.out.println("monthUpdate() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}
}
