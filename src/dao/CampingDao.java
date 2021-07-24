package dao;

import static db.JdbcUtil.*;
import javax.sql.*;
import java.sql.*;
import java.util.*;
import vo.*;

public class CampingDao {
	private static CampingDao campingDao;
	private Connection conn;
	private CampingDao() {}
	
	public static CampingDao getInstance() {
		if (campingDao == null) {	
			campingDao = new CampingDao();
		}	
		return campingDao;	
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	public int getCampCount() {
		Statement stmt = null;	
		ResultSet rs = null;	
		int rcnt = 0;	
		
		try {
			String sql = "select count(*) " +		// , b.mi_follower
						 " from t_camping_review a, t_member_info b " +
						 " where a.mi_email = b.mi_email and a.cr_isview = 'y' ";

			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {	
				rcnt = rs.getInt(1);
			}	// 게시글의 개수를 rcnt에 저장(게시글이 없으면 0이 저장됨)
			
		} catch(Exception e) {
			System.out.println("getCampCount() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		return rcnt;
	}
	
	public ArrayList<CampingInfo> getCampList(String orderBy, int cpage, int psize) {
		ArrayList<CampingInfo> campList = new ArrayList<CampingInfo>();
		
		Statement stmt = null;
		ResultSet rs = null;		
		
		CampingInfo campingInfo = null;
		
		int snum = (cpage - 1) * psize;
		
		try {
			String sql = "select a.*, b.mi_nick, b.mi_introduce, b.mi_img " +		// , b.mi_follower
						 " from t_camping_review a, t_member_info b " +
						 " where a.mi_email = b.mi_email  and a.cr_isview = 'y' " + 
						 orderBy + " limit " + snum + ", " + psize;
			System.out.println(sql);
			
			stmt = conn.createStatement();				
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				campingInfo = new CampingInfo();

				campingInfo.setCr_idx(rs.getInt("cr_idx"));
				campingInfo.setCr_read(rs.getInt("cr_read"));
				campingInfo.setCr_good(rs.getInt("cr_good"));
				campingInfo.setCr_scrap(rs.getInt("cr_scrap"));
				campingInfo.setCr_reply(rs.getInt("cr_reply"));
				campingInfo.setMi_email(rs.getString("mi_email"));
				campingInfo.setCr_imgs(rs.getString("cr_imgs"));
				campingInfo.setCr_pdtimgs(rs.getString("cr_pdtimgs"));
				campingInfo.setCr_content(rs.getString("cr_content"));
				campingInfo.setCr_keyword(rs.getString("cr_keyword"));
				campingInfo.setCr_last(rs.getString("cr_last"));
				campingInfo.setCr_isview(rs.getString("cr_isview"));
				campingInfo.setCr_date(rs.getString("cr_date"));
				campingInfo.setMi_nick(rs.getString("mi_nick"));
				campingInfo.setMi_introduce(rs.getString("mi_introduce"));
				campingInfo.setCr_img(rs.getString("mi_img"));
			
				campList.add(campingInfo);
				
			 }
			
		} catch(Exception e) {
			System.out.println("getCampList() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		
		return campList;
		
	}
	
	public CampingInfo getCampInfo(int idx) {
		Statement stmt = null;
		ResultSet rs = null;
		CampingInfo campingInfo = null;
		
		try {
			String sql = "select a.*, b.mi_nick, b.mi_img, b.mi_introduce from t_camping_review a, t_member_info b " +		// , b.mi_follower
						 " where a.mi_email = b.mi_email and a.cr_isview ='y' and a.cr_idx = '" + idx + "'";	
			
			System.out.println(sql);
			stmt = conn.createStatement();				
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				campingInfo = new CampingInfo();
				String email = rs.getString("mi_email");
				campingInfo.setCr_idx(rs.getInt("cr_idx"));
				campingInfo.setCr_read(rs.getInt("cr_read"));
				campingInfo.setCr_good(rs.getInt("cr_good"));
				campingInfo.setCr_scrap(rs.getInt("cr_scrap"));
				campingInfo.setCr_reply(rs.getInt("cr_reply"));
				campingInfo.setMi_email(rs.getString("mi_email"));
				campingInfo.setCr_imgs(rs.getString("cr_imgs"));
				campingInfo.setCr_pdtimgs(rs.getString("cr_pdtimgs"));
				campingInfo.setCr_content(rs.getString("cr_content"));
				campingInfo.setCr_keyword(rs.getString("cr_keyword"));
				campingInfo.setCr_last(rs.getString("cr_last"));
				campingInfo.setCr_isview(rs.getString("cr_isview"));
				campingInfo.setCr_date(rs.getString("cr_date"));
				campingInfo.setMi_nick(rs.getString("mi_nick"));
				campingInfo.setMi_introduce(rs.getString("mi_introduce"));
				campingInfo.setCr_img(rs.getString("mi_img"));
				
				String sql2 = "select cr_idx from t_camping_review " +  
	                    "where mi_email = '" + email + "' and cr_isview = 'y' order by cr_good desc limit 4 ";
		        System.out.println(sql2);
		        rs = stmt.executeQuery(sql2);
		        
		        String history = "";
		        while (rs.next()) {
		        	history += rs.getInt(1) + "/";
		        }

	        	history = history.substring(0, history.length() - 1);
	            System.out.println(history);
	            campingInfo.setCr_history(history);
			 }
			
		} catch(Exception e) {
			System.out.println("getCampInfo() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		return campingInfo;

	}
	
	public int addReview(CampingInfo campInfo) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "";
		
		try {
			sql = "insert into t_camping_review(mi_email, cr_imgs, cr_pdtimgs, cr_content, cr_keyword) " + 
				  "values (?, ?, ?, ?, ?)";

			System.out.println(sql);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, campInfo.getMi_email());
			pstmt.setString(2, campInfo.getCr_imgs());
			pstmt.setString(3, campInfo.getCr_pdtimgs());
			pstmt.setString(4, campInfo.getCr_content());
			pstmt.setString(5, campInfo.getCr_keyword());
			result = pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("addReview() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int updateReview(CampingInfo campInfo) {	
		int result = 0;
		Statement stmt = null;	

		String crimgs = "";
		if (campInfo.getCr_imgs().equals("null"))	crimgs = "";
		else										crimgs = campInfo.getCr_imgs();
		
		try {				
			stmt = conn.createStatement(); 		
			
			String sql = "update t_camping_review set " + 
				"cr_content = '"	+ campInfo.getCr_content()	+ "', " + 
				"cr_keyword = '"	+ campInfo.getCr_keyword()	+ "', " + 
				"cr_imgs = '"		+ crimgs	+ "', " + 
				"where cr_idx = "	+ campInfo.getCr_idx()		+ " " + 
				"and mi_email = '"	+ campInfo.getMi_email()	+ "' ";
			System.out.println(sql);
			result = stmt.executeUpdate(sql);
			
		} catch(Exception e) {
			System.out.println("updateReview() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}

	public int deleteReview(CampingInfo campInfo) {	
		int result = 0;
		Statement stmt = null;	

		try {				
			stmt = conn.createStatement(); 		
			
			String sql = "update t_camping_review set cr_isview = 'n' where mi_email = '" + campInfo.getMi_email() + "' and cr_idx = " + campInfo.getCr_idx();
			System.out.println(sql);
			result = stmt.executeUpdate(sql);
			
		} catch(Exception e) {
			System.out.println("deleteReview() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}
	
	public int getIdx() {	
		Statement stmt = null;	
		ResultSet rs = null;

		int idx = 1;	// 게시글 번호
		
		try {				
			stmt = conn.createStatement(); 		
			
			// 게시글 idx 가져오기
			String sql = "select max(cr_idx) + 1 from t_camping_review";
			rs = stmt.executeQuery(sql);
			if (rs.next())	idx = rs.getInt(1);
			
		} catch(Exception e) {
			System.out.println("getIdx() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return idx;
	}
	
	
	// 댓글 처리
	public int getCampReplyCount() {
		Statement stmt = null;	
		ResultSet rs = null;	
		int rcnt = 0;	// 게시글 개수를 저장할 변수
		
		try {
			String sql = "select count(*) from t_camping_review_reply where crr_isview = 'y'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {	
				rcnt = rs.getInt(1);
			}	
			
		} catch(Exception e) {
			System.out.println("getCampReplyCount() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		return rcnt;
	}
	
	public ArrayList<CampReplyInfo> getCampReplyList(int idx) {
		ArrayList<CampReplyInfo> replyList = new ArrayList<CampReplyInfo>();
		
		Statement stmt = null;
		ResultSet rs = null;
		
		CampReplyInfo replyInfo = null;
		
		//int snum = (cpage - 1) * psize;
		// 쿼리의 limit 명령에서 데이터를 가져올 시작 인덱스 번호
		
		try {
			String sql = "select * from t_camping_review_reply where cr_idx = " + idx + " and crr_isview = 'y' "; 
					//orderBy + " limit " + snum + ", " + psize;
			
			System.out.println(sql);
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				replyInfo = new CampReplyInfo();

				replyInfo.setCrr_idx(rs.getInt("crr_idx"));
				replyInfo.setCr_idx(rs.getInt("cr_idx"));
				replyInfo.setCrr_good(rs.getInt("crr_good"));
				replyInfo.setCrr_rereply(rs.getInt("crr_rereply"));
				replyInfo.setMi_email(rs.getString("mi_email"));
				replyInfo.setCrr_nick(rs.getString("crr_nick"));
				replyInfo.setCrr_content(rs.getString("crr_content"));
				replyInfo.setCrr_date(rs.getString("crr_date"));
				replyInfo.setCrr_isview(rs.getString("crr_isview"));
				
				replyList.add(replyInfo);
			 }
			
		} catch(Exception e) {
			System.out.println("getCampReplyList() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		
		return replyList;
		
	}
	
	public int CampReplyInsert(CampReplyInfo replyInfo) {	// 질문 등록 처리를 위한 메소드
		int result = 0;
		Statement stmt = null;
		
		try {
			stmt = conn.createStatement(); 	
			String sql = "insert into t_camping_review_reply (cr_idx, mi_email, crr_nick, crr_content) values (" + replyInfo.getCr_idx() + ", '" + replyInfo.getMi_email() + "', '" + replyInfo.getCrr_nick() + "', '" + replyInfo.getCrr_content() + "')";
			String sql2 = "update t_camping_review set cr_reply = cr_reply + 1 where cr_idx = " + replyInfo.getCr_idx();
			System.out.println(sql);
			result = stmt.executeUpdate(sql);
			System.out.println(sql2);
			result = stmt.executeUpdate(sql2);
			
		} catch(Exception e) {
			System.out.println("CampReplyInsert() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}
	
	public int CampReplyUpdate(CampReplyInfo replyInfo) {	// 질문 수정 처리를 위한 메소드
		int result = 0;
		Statement stmt = null;

		try {
			stmt = conn.createStatement(); 			
			int Crridx = replyInfo.getCrr_idx();
			String sql = "update t_camping_review_reply set crr_content = '" + replyInfo.getCrr_content() 
				+ "' where crr_idx = " + replyInfo.getCrr_idx() + " and mi_email = '" + replyInfo.getMi_email()  
				+ "' ";
			System.out.println(sql);
			result = stmt.executeUpdate(sql);
			
		} catch(Exception e) {
			System.out.println("CampReplyUpdate() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}
	
	public int CampReplyDelete(CampReplyInfo replyInfo) {
		int result = 0;
		Statement stmt = null;
		
		try {
			stmt = conn.createStatement(); 			
			String sql = "delete from t_camping_review_reply where mi_email = '" + replyInfo.getMi_email() + "' and cr_idx = " + replyInfo.getCrr_idx();
			System.out.println(sql);
			result = stmt.executeUpdate(sql);
			
		} catch(Exception e) {
			System.out.println("CampReplyDelete() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}
}
