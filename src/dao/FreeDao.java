package dao;

import static db.JdbcUtil.*;
import javax.sql.*;
import java.sql.*;
import java.util.*;
import vo.*;

public class FreeDao {
	private static FreeDao freeDao;
	private Connection conn;
	private FreeDao() {}
	
	public static FreeDao getInstance() {
		if (freeDao == null) {	
			freeDao = new FreeDao();
		}	
		return freeDao;	
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	public int getFreeCount(String where) {
	// 검색된 게시글의 총 개수를 리턴하는 메소드(검색조건이 있을 경우 검색된 결과의 개수를 리턴)
		Statement stmt = null;	
		ResultSet rs = null;	
		int rcnt = 0;	// 게시글 개수를 저장할 변수
		
		try {
			String sql = "select count(*) from t_brd_free where bf_isview = 'y' " + where;
			// 게시글의 총 개수를 가져올 쿼리 작성
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {	
				rcnt = rs.getInt(1);
			}	// 게시글의 개수를 rcnt에 저장(게시글이 없으면 0이 저장됨)
			// count 함수는 결과값이 없을 수가 없으므로(값이 없으면 0이니까) if문을 사용하지 않아도 됨
			// 그러나 rs.next()는 반드시 해주어야 하므로 눈에 익숙한 형태로 보이기 위해 if문을 사용
			
		} catch(Exception e) {
			System.out.println("getFreeCount() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		return rcnt;
	}
	
	public ArrayList<FreeInfo> getFreeList(String where, String orderBy, int cpage, int psize) {
	// 검색된 게시글 목록을 ArrayList<FreeInfo>형 인스턴스로 리턴하는 메소드
		ArrayList<FreeInfo> freeList = new ArrayList<FreeInfo>();
		// 게시글 목록을 저장할 ArrayList로 오직 FreeInfo형 인스턴스만 저장됨
		
		Statement stmt = null;
		ResultSet rs = null;
		
		FreeInfo freeInfo = null;
		// freeInfo에 담을 데이터를 담을 FreeInfo형 인스턴스를 선언
		
		int snum = (cpage - 1) * psize;
		// 쿼리의 limit 명령에서 데이터를 가져올 시작 인덱스 번호
		
		try {
			String sql = "select * from t_brd_free where bf_isview = 'y' " + 
					where + orderBy + " limit " + snum + ", " + psize;
			
			System.out.println(sql);
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
			// rs에 보여줄 게시글이 있을 경우 
				freeInfo = new FreeInfo();
				// freeList에 저장할 한 게시글의 정보를 담을 인스턴스 생성

				freeInfo.setBf_idx(rs.getInt("bf_idx"));
				freeInfo.setMi_email(rs.getString("mi_email"));
				freeInfo.setBf_nick(rs.getString("bf_nick"));
				freeInfo.setBf_title(rs.getString("bf_title"));
				freeInfo.setBf_content(rs.getString("bf_content"));
				freeInfo.setBf_img1(rs.getString("bf_img1"));
				freeInfo.setBf_img2(rs.getString("bf_img2"));
				freeInfo.setBf_img3(rs.getString("bf_img3"));
				freeInfo.setBf_reply(rs.getInt("bf_reply"));
				freeInfo.setBf_read(rs.getInt("bf_read"));
				freeInfo.setBf_good(rs.getInt("bf_good"));
				freeInfo.setBf_isview(rs.getString("bf_isview"));
				freeInfo.setBf_date(rs.getString("bf_date"));
				freeInfo.setLast_date(rs.getString("last_date"));
	            // 받아온 레코드들로 게시글 정보를 저장
				
				freeList.add(freeInfo);
				// 한 게시글의 정보를 담은 freeInfo 인스턴스를 freeList에 저장
			 }
			
		} catch(Exception e) {
			System.out.println("getFreeList() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		
		return freeList;
		
	}
	
	public FreeInfo getFreeInfo(int idx) {
	// 지정한 id에 해당하는 특정 상품 정보를 ProductInfo형 인스턴스로 리턴하는 메소드
		FreeInfo freeInfo = null;
		Statement stmt = null;
		ResultSet rs = null;
      
		try {
			String sql = "select * from t_brd_free where bf_idx = " + idx + " ";
			
			System.out.println(sql);
			stmt = conn.createStatement();   
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {   
				freeInfo = new FreeInfo();

				freeInfo.setBf_idx(rs.getInt("bf_idx"));
				freeInfo.setMi_email(rs.getString("mi_email"));
				freeInfo.setBf_nick(rs.getString("bf_nick"));
				freeInfo.setBf_title(rs.getString("bf_title"));
				freeInfo.setBf_content(rs.getString("bf_content"));
				freeInfo.setBf_img1(rs.getString("bf_img1"));
				freeInfo.setBf_img2(rs.getString("bf_img2"));
				freeInfo.setBf_img3(rs.getString("bf_img3"));
				freeInfo.setBf_reply(rs.getInt("bf_reply"));
				freeInfo.setBf_read(rs.getInt("bf_read"));
				freeInfo.setBf_good(rs.getInt("bf_good"));
				freeInfo.setBf_date(rs.getString("bf_date"));
				freeInfo.setBf_isview(rs.getString("bf_isview"));
				freeInfo.setLast_date(rs.getString("last_date"));
			}
        
		} catch(Exception e) {
			System.out.println("getFreeInfo() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
      
		return freeInfo;
	}
	
	public int readCountUp(int idx) {
		Statement stmt = null;
		int result = 0;
		
		try {
			String sql = "update t_brd_free set bf_read = bf_read + 1 " + 
				" where bf_idx = " + idx;
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
		
	public int freeInsert(FreeInfo freeInfo) {	// 질문 등록 처리를 위한 메소드
		int result = 0;
		Statement stmt = null;
		
		try {
			stmt = conn.createStatement(); 	
			String sql = "insert into t_brd_free (mi_email, bf_nick, bf_title, bf_content, bf_img1, bf_img2, bf_img3) " + 
				" values ('" + 
				freeInfo.getMi_email()		+ "', '" + 
				freeInfo.getBf_nick()		+ "', '" + 
				freeInfo.getBf_title()		+ "', '" + 
				freeInfo.getBf_content()	+ "', '" + 
				freeInfo.getBf_img1()		+ "', '" + 
				freeInfo.getBf_img2()		+ "', '" + 
				freeInfo.getBf_img3()		+ "')";
			System.out.println(sql);
			result = stmt.executeUpdate(sql);
			
		} catch(Exception e) {
			System.out.println("freeInsert() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}
	
	public int freeUpdate(FreeInfo freeInfo) {	// 질문 수정 처리를 위한 메소드
		int result = 0;
		Statement stmt = null;

		String img1 = "", img2 = "", img3 = "";
		if (freeInfo.getBf_img1().equals("null"))	img1 = "";
		else										img1 = freeInfo.getBf_img1();
		if (freeInfo.getBf_img2().equals("null"))	img2 = "";
		else										img2 = freeInfo.getBf_img2();
		if (freeInfo.getBf_img3().equals("null"))	img3 = "";
		else										img3 = freeInfo.getBf_img3();
		
		try {
			stmt = conn.createStatement(); 			
			String sql = "update t_brd_free set " + 
				"bf_title = '"		+ freeInfo.getBf_title()	+ "', " + 
				"bf_content = '"	+ freeInfo.getBf_content()	+ "', " + 
				"bf_img1 = '"		+ img1	+ "', " + 
				"bf_img2 = '"		+ img2	+ "', " + 
				"bf_img3 = '"		+ img3	+ "' " + 
				"where bf_idx = "	+ freeInfo.getBf_idx()		+ " " + 
				"and mi_email = '"	+ freeInfo.getMi_email()	+ "' ";
			System.out.println(sql);
			result = stmt.executeUpdate(sql);
			
		} catch(Exception e) {
			System.out.println("freeUpdate() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}
	
	public int freeDelete(FreeInfo freeInfo) {	// 질문 삭제 처리를 위한 메소드
		int result = 0;
		Statement stmt = null;
		
		try {
			stmt = conn.createStatement(); 			
			String sql = "delete from t_brd_free where mi_email = '" + freeInfo.getMi_email() + "' and bf_idx = " + freeInfo.getBf_idx();
			System.out.println(sql);
			result = stmt.executeUpdate(sql);
			
		} catch(Exception e) {
			System.out.println("freeDelete() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}
	
	public int getFreeReplyCount() {
		Statement stmt = null;	
		ResultSet rs = null;	
		int rcnt = 0;	// 게시글 개수를 저장할 변수
		
		try {
			String sql = "select count(*) from t_brd_free_reply where bfr_isview = 'y'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {	
				rcnt = rs.getInt(1);
			}	
			
		} catch(Exception e) {
			System.out.println("getFreeReplyCount() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		return rcnt;
	}
	
	public ArrayList<FreeReplyInfo> getFreeReplyList(int idx) {
	// 검색된 게시글 목록을 ArrayList<FreeInfo>형 인스턴스로 리턴하는 메소드
		ArrayList<FreeReplyInfo> replyList = new ArrayList<FreeReplyInfo>();
		// 게시글 목록을 저장할 ArrayList로 오직 FreeInfo형 인스턴스만 저장됨
		
		Statement stmt = null;
		ResultSet rs = null;
		
		FreeReplyInfo replyInfo = null;
		// freeInfo에 담을 데이터를 담을 FreeInfo형 인스턴스를 선언
		
		//int snum = (cpage - 1) * psize;
		// 쿼리의 limit 명령에서 데이터를 가져올 시작 인덱스 번호
		
		try {
			String sql = "select * from t_brd_free_reply where bf_idx = " + idx + " and bfr_isview = 'y' "; 
					//orderBy + " limit " + snum + ", " + psize;
			
			System.out.println(sql);
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				replyInfo = new FreeReplyInfo();

				replyInfo.setBfr_idx(rs.getInt("bfr_idx"));
				replyInfo.setBf_idx(rs.getInt("bf_idx"));
				replyInfo.setBfr_read(rs.getInt("bfr_read"));
				replyInfo.setBfr_rereply(rs.getInt("bfr_rereply"));
				replyInfo.setMi_email(rs.getString("mi_email"));
				replyInfo.setBfr_nick(rs.getString("bfr_nick"));
				replyInfo.setBfr_content(rs.getString("bfr_content"));
				replyInfo.setBfr_date(rs.getString("bfr_date"));
				replyInfo.setBfr_isview(rs.getString("bfr_isview"));
				
				replyList.add(replyInfo);
			 }
			
		} catch(Exception e) {
			System.out.println("FreeReplyInfo() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		
		return replyList;
		
	}

	public FreeReplyInfo getFreeReplyInfo(int idx) {
	// 지정한 id에 해당하는 특정 상품 정보를 ProductInfo형 인스턴스로 리턴하는 메소드
		FreeReplyInfo replyInfo = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "select * from t_brd_free_reply where bf_idx = " + idx + " ";
			
			System.out.println(sql);
			stmt = conn.createStatement();   
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {   
				replyInfo = new FreeReplyInfo();

				replyInfo.setBfr_idx(rs.getInt("bfr_idx"));
				replyInfo.setBf_idx(rs.getInt("bf_idx"));
				replyInfo.setBfr_read(rs.getInt("bfr_read"));
				replyInfo.setBfr_rereply(rs.getInt("bfr_rereply"));
				replyInfo.setMi_email(rs.getString("mi_email"));
				replyInfo.setBfr_nick(rs.getString("bfr_nick"));
				replyInfo.setBfr_content(rs.getString("bfr_content"));
				replyInfo.setBfr_date(rs.getString("bfr_date"));
				replyInfo.setBfr_isview(rs.getString("bfr_isview"));
			}
        
		} catch(Exception e) {
			System.out.println("getFreeReplyInfo() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
      
		return replyInfo;
	}
	
	public int freeReplyInsert(FreeReplyInfo replyInfo) {	// 질문 등록 처리를 위한 메소드
		int result = 0;
		Statement stmt = null;
		
		try {
			stmt = conn.createStatement(); 	
			String sql = "insert into t_brd_free_reply (bf_idx, mi_email, bfr_content) values (" + replyInfo.getBf_idx() + ", '" + replyInfo.getMi_email() + "', '" + replyInfo.getBfr_content() + "')";
			String sql2 = "update t_brd_free set bf_reply = bf_reply + 1 where bf_idx = " + replyInfo.getBf_idx();
			System.out.println(sql);
			result = stmt.executeUpdate(sql);
			System.out.println(sql2);
			result = stmt.executeUpdate(sql2);
			
		} catch(Exception e) {
			System.out.println("freeReplyInsert() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}
	
	public int freeReplyUpdate(FreeReplyInfo replyInfo) {	// 질문 수정 처리를 위한 메소드
		int result = 0;
		Statement stmt = null;

		try {
			stmt = conn.createStatement(); 			
			int bfridx = replyInfo.getBfr_idx();
			String sql = "update t_brd_free_reply set bfr_content = '" + replyInfo.getBfr_content() 
				+ "' where bfr_idx = " + replyInfo.getBfr_idx() + " and mi_email = '" + replyInfo.getMi_email()  
				+ "' ";
			System.out.println(sql);
			result = stmt.executeUpdate(sql);
			
		} catch(Exception e) {
			System.out.println("freeReplyUpdate() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}
	
	public int freeReplyDelete(FreeReplyInfo replyInfo) {	// 질문 삭제 처리를 위한 메소드
		int result = 0;
		Statement stmt = null;
		
		try {
			stmt = conn.createStatement(); 			
			String sql = "delete from t_brd_free where mi_email = '" + replyInfo.getMi_email() + "' and bf_idx = " + replyInfo.getBfr_idx();
			System.out.println(sql);
			result = stmt.executeUpdate(sql);
			
		} catch(Exception e) {
			System.out.println("freeReplyDelete() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}
}
