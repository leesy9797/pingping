package dao;

import static db.JdbcUtil.*;
import javax.sql.*;
import java.sql.*;
import java.util.*;
import vo.*;

public class MonthDao {
	private static MonthDao monthDao;
	private Connection conn;
	private MonthDao() {}
	
	public static MonthDao getInstance() {
		if (monthDao == null) {	
			monthDao = new MonthDao();
		}	
		return monthDao;	
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	public int getMonthCount(String where) {
	// 검색된 게시글의 총 개수를 리턴하는 메소드(검색조건이 있을 경우 검색된 결과의 개수를 리턴)
		Statement stmt = null;	
		ResultSet rs = null;	
		int rcnt = 0;	// 게시글 개수를 저장할 변수
		
		try {
			String sql = "select count(*) from t_monthly_rec where mr_isview = 'y'" + where;
			// 게시글의 총 개수를 가져올 쿼리 작성
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {	
				rcnt = rs.getInt(1);
			}	// 게시글의 개수를 rcnt에 저장(게시글이 없으면 0이 저장됨)
			// count 함수는 결과값이 없을 수가 없으므로(값이 없으면 0이니까) if문을 사용하지 않아도 됨
			// 그러나 rs.next()는 반드시 해주어야 하므로 눈에 익숙한 형태로 보이기 위해 if문을 사용
			
		} catch(Exception e) {
			System.out.println("getMonthCount() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		return rcnt;
	}
	
	public ArrayList<MonthlyInfo> getMonthList(String where, String orderBy, int cpage, int psize) {
	// 검색된 게시글 목록을 ArrayList<MonthlyInfo>형 인스턴스로 리턴하는 메소드
		ArrayList<MonthlyInfo> monthList = new ArrayList<MonthlyInfo>();
		// 게시글 목록을 저장할 ArrayList로 오직 MonthlyInfo형 인스턴스만 저장됨
		
		Statement stmt = null;
		ResultSet rs = null;		
		
		MonthlyInfo monthInfo = null;
		// monthInfo에 담을 데이터를 담을 MonthlyInfo형 인스턴스를 선언
		
		int snum = (cpage - 1) * psize;
		// 쿼리의 limit 명령에서 데이터를 가져올 시작 인덱스 번호
		
		try {
			String sql = "select * from t_monthly_rec where mr_isview = 'y'" + 
					where + orderBy + " limit " + snum + ", " + psize;
			
			System.out.println(sql);
			
			stmt = conn.createStatement();				
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
			// rs에 보여줄 게시글이 있을 경우 
				monthInfo = new MonthlyInfo();
				// monthList에 저장할 한 게시글의 정보를 담을 인스턴스 생성

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
				// 한 게시글의 정보를 담은 monthInfo 인스턴스를 monthList에 저장
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
		Statement stmt = null;
		ResultSet rs = null;
		MonthlyInfo monthInfo = null;
		// 하나의 게시글 정보를 저장하여 리턴할  MonthlyInfo형 인스턴스를 선언
		
		try {
			String sql = "select * from t_monthly_rec where mr_idx = '" + idx + "' and mr_isview = 'y'";
			System.out.println(sql);
			stmt = conn.createStatement();				
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
			// rs에 보여줄 게시글이 있을 경우 
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
				// 받아온 레코드로 게시글 정보를 저장
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
	
	public int readCountUp(int idx) {
		Statement stmt = null;
		int result = 0;
		
		try {
			String sql = "update t_monthly_rec set mr_read = mr_read + 1 " + 
				" where mr_idx = " + idx;
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
			
		} catch(Exception e) {
			System.out.println("readCountUp() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}

	// 댓글 처리
	public int getMonthReplyCount() {
		Statement stmt = null;	
		ResultSet rs = null;	
		int rcnt = 0;	// 게시글 개수를 저장할 변수
		
		try {
			String sql = "select count(*) from t_monthly_rec_reply where mrr_isview = 'y'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {	
				rcnt = rs.getInt(1);
			}	
			
		} catch(Exception e) {
			System.out.println("getMonthReplyCount() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		return rcnt;
	}
	
	public ArrayList<MonthReplyInfo> getMonthReplyList(int idx) {
		ArrayList<MonthReplyInfo> replyList = new ArrayList<MonthReplyInfo>();
		
		Statement stmt = null;
		ResultSet rs = null;
		
		MonthReplyInfo replyInfo = null;
		
		//int snum = (cpage - 1) * psize;
		// 쿼리의 limit 명령에서 데이터를 가져올 시작 인덱스 번호
		
		try {
			String sql = "select * from t_monthly_rec_reply where mr_idx = " + idx + " and mrr_isview = 'y' "; 
					//orderBy + " limit " + snum + ", " + psize;
			
			System.out.println(sql);
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				replyInfo = new MonthReplyInfo();

				replyInfo.setMrr_idx(rs.getInt("mrr_idx"));
				replyInfo.setMr_idx(rs.getInt("mr_idx"));
				replyInfo.setMrr_good(rs.getInt("mrr_good"));
				replyInfo.setMrr_rereply(rs.getInt("mrr_rereply"));
				replyInfo.setMi_email(rs.getString("mi_email"));
				replyInfo.setMrr_nick(rs.getString("mrr_nick"));
				replyInfo.setMrr_content(rs.getString("mrr_content"));
				replyInfo.setMrr_date(rs.getString("mrr_date"));
				replyInfo.setMrr_isview(rs.getString("mrr_isview"));
				
				replyList.add(replyInfo);
			 }
			
		} catch(Exception e) {
			System.out.println("getMonthReplyList() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		
		return replyList;
		
	}
	
	public int MonthReplyInsert(MonthReplyInfo replyInfo) {	// 댓글 등록 처리를 위한 메소드
		int result = 0;
		Statement stmt = null;
		
		try {
			stmt = conn.createStatement(); 	
			String sql = "insert into t_monthly_rec_reply (mr_idx, mi_email, mrr_nick, mrr_content) values (" + replyInfo.getMr_idx() + ", '" + replyInfo.getMi_email() + "', '" + replyInfo.getMrr_nick() + "', '" + replyInfo.getMrr_content() + "')";
			String sql2 = "update t_monthly_rec set mr_reply = mr_reply + 1 where mr_idx = " + replyInfo.getMr_idx();
			System.out.println(sql);
			result = stmt.executeUpdate(sql);
			System.out.println(sql2);
			result = stmt.executeUpdate(sql2);
			
		} catch(Exception e) {
			System.out.println("MonthReplyInsert() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}
	
	public int MonthReplyUpdate(MonthReplyInfo replyInfo) {	// 댓글 수정 처리를 위한 메소드
		int result = 0;
		Statement stmt = null;

		try {
			stmt = conn.createStatement(); 			
			int Mrridx = replyInfo.getMrr_idx();
			String sql = "update t_monthly_rec_reply set mrr_content = '" + replyInfo.getMrr_content() 
				+ "' where mrr_idx = " + replyInfo.getMrr_idx() + " and mi_email = '" + replyInfo.getMi_email()  
				+ "' ";
			System.out.println(sql);
			result = stmt.executeUpdate(sql);
			
		} catch(Exception e) {
			System.out.println("MonthReplyUpdate() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}
	
	public int MonthReplyDelete(MonthReplyInfo replyInfo) {
		int result = 0;
		Statement stmt = null;
		
		try {
			stmt = conn.createStatement(); 			
			String sql = "delete from t_monthly_rec_reply where mi_email = '" + replyInfo.getMi_email() + "' and mr_idx = " + replyInfo.getMrr_idx();
			System.out.println(sql);
			result = stmt.executeUpdate(sql);
			
		} catch(Exception e) {
			System.out.println("MonthReplyDelete() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}
}
