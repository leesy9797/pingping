package dao;

import static db.JdbcUtil.*;
import java.sql.*;
import java.util.ArrayList;

import vo.*;

public class ChkFGSDao {
// 팔로우, 좋아요, 스크랩 여부 확인용 클래스
	private static ChkFGSDao chkFGSDao;
	private Connection conn;
	private ChkFGSDao() {}
	
	public static ChkFGSDao getInstance() {
		if (chkFGSDao == null) {
			chkFGSDao = new ChkFGSDao();
		}
		return chkFGSDao;
	}
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	public ArrayList<FollowInfo> getFollowList(String loginEmail) {
		ArrayList<FollowInfo> followList = new ArrayList<FollowInfo>();
		
		Statement stmt = null;
		ResultSet rs = null;		
		
		FollowInfo followInfo = null;
		
		try {
			String sql = "select * from t_member_following where mi_email = '" + loginEmail + "'";
			System.out.println(sql);
			
			stmt = conn.createStatement();				
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				followInfo = new FollowInfo();
			
				followInfo.setMi_email(rs.getString("mi_email"));
				followInfo.setMfg_email(rs.getString("mfg_email"));
				
				System.out.println(followInfo.getMfg_email());			
				followList.add(followInfo);
			}
			
		} catch(Exception e) {
			System.out.println("getFollowList() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		
		return followList;
	}

	public ArrayList<GoodInfo> getGoodList(String kind, String loginEmail) {
		ArrayList<GoodInfo> goodList = new ArrayList<GoodInfo>();
		
		Statement stmt = null;
		ResultSet rs = null;		
		
		GoodInfo goodInfo = null;
		
		try {
			String sql = "select * from t_member_good where mi_email = '" + loginEmail + "' and mg_kind = '" + kind + "'";
			System.out.println(sql);
			
			stmt = conn.createStatement();				
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				goodInfo = new GoodInfo();
			
				goodInfo.setMi_email(rs.getString("mi_email"));
				goodInfo.setMg_kind(rs.getString("mg_kind"));
				goodInfo.setMg_linkidx(rs.getString("mg_linkidx"));
				goodInfo.setMg_date(rs.getString("mg_date"));
						
				goodList.add(goodInfo);
			}
			
		} catch(Exception e) {
			System.out.println("getGoodList() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		
		return goodList;
	}

	public ArrayList<ScrapInfo> getScrapList(String kind, String loginEmail) {
		ArrayList<ScrapInfo> scrapList = new ArrayList<ScrapInfo>();
		
		Statement stmt = null;
		ResultSet rs = null;		
		
		ScrapInfo scrapInfo = null;
		
		try {
			String sql = "select * from t_member_scrap where mi_email = '" + loginEmail + "' and ms_kind = '" + kind + "'";
			System.out.println(sql);
			
			stmt = conn.createStatement();				
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				scrapInfo = new ScrapInfo();
			
				scrapInfo.setMi_email(rs.getString("mi_email"));
				scrapInfo.setMs_kind(rs.getString("ms_kind"));
				scrapInfo.setMs_linkidx(rs.getString("ms_linkidx"));
				scrapInfo.setMs_date(rs.getString("ms_date"));
						
				scrapList.add(scrapInfo);
			}
			
		} catch(Exception e) {
			System.out.println("getScrapList() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		
		return scrapList;
	}
	
	public int doFollow(String ciemail, String miemail) {
	// 내가 해당 회원을 팔로우 중인지 검사해서 없으면 팔로우 하는 메소드
		Statement stmt = null;
		ResultSet rs = null;
		int result = 0;
		try {
			String sql = "select count(*) from t_member_following where mfg_email = '" + ciemail + "' and mi_email = '" + miemail + "'";
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			int n = rs.getInt(1);
			if (ciemail.equals(miemail))	n = 1;	// 자기자신을 팔로우 하는 경우
			System.out.println(n);
			
			if (n == 0)	{
				// 내 팔로잉 테이블 insert
				sql = "insert into t_member_following (mfg_email, mi_email) " + 
					"values ('" + ciemail + "', '" + miemail + "')";
				System.out.println(sql);
				result += stmt.executeUpdate(sql);
				
				// 상대 팔로워 테이블 insert
				sql = "insert into t_member_follower (mi_email, mfr_email) " + 
					"values ('" + ciemail + "', '" + miemail + "')";
				System.out.println(sql);
				result += stmt.executeUpdate(sql);
				
				// 내 회원정보 테이블 update : 팔로잉 수 +1
				sql = "update t_member_info set mi_following = mi_following + 1 where mi_email = '" + miemail + "'";
				System.out.println(sql);
				result += stmt.executeUpdate(sql);
				
				// 상대 회원정보 테이블 update : 팔로워 수 +1
				sql = "update t_member_info set mi_follower = mi_follower + 1 where mi_email = '" + ciemail + "'";
				System.out.println(sql);
				result += stmt.executeUpdate(sql);
				
				System.out.println(result);
			}
			
		} catch(Exception e) {
			System.out.println("doFollow() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return result;
	}

	public int unFollow(String ciemail, String miemail) {
	// 내가 해당 회원을 팔로우 중인지 검사해서 있으면 팔로우 취소하는 메소드
		Statement stmt = null;
		ResultSet rs = null;
		int result = 0;
		try {
			String sql = "select count(*) from t_member_following where mfg_email = '" + ciemail + "' and mi_email = '" + miemail + "'";
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			int n = rs.getInt(1);
			if (ciemail.equals(miemail))	n = 0;	// 자기자신을 팔로우 취소 하는 경우
			System.out.println(n);
			
			if (n == 1)	{
				// 내 팔로잉 테이블 delete
				sql = "delete from t_member_following where mi_email = '" + miemail + "' and mfg_email = '" + ciemail + "'";
				System.out.println(sql);
				result += stmt.executeUpdate(sql);
				
				// 상대 팔로워 테이블 delete
				sql = "delete from t_member_follower where mi_email = '" + ciemail + "' and mfr_email = '" + miemail + "'";
				System.out.println(sql);
				result += stmt.executeUpdate(sql);
				
				// 내 회원정보 테이블 update : 팔로잉 수 -1
				sql = "update t_member_info set mi_following = mi_following - 1 where mi_email = '" + miemail + "'";
				System.out.println(sql);
				result += stmt.executeUpdate(sql);
				
				// 상대 회원정보 테이블 update : 팔로워 수 -1
				sql = "update t_member_info set mi_follower = mi_follower - 1 where mi_email = '" + ciemail + "'";
				System.out.println(sql);
				result += stmt.executeUpdate(sql);
				
				System.out.println(result);
			}
			
		} catch(Exception e) {
			System.out.println("unFollow() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return result;
	}

	public int doGood(String kind, String linkidx, String miemail) {
	// 내가 해당 게시글을 좋아요 중인지 검사해서 없으면 좋아요 하는 메소드
		Statement stmt = null;
		ResultSet rs = null;
		int result = 0;
		try {
			// kind = mg_kind
			// mg_linkidx = 글 일련번호
			String sql = "select count(*) from t_member_good " + 
				"where mi_email = '" + miemail + "' and mg_kind = '" + kind + "' and mg_linkidx = '" + linkidx + "'";
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			int n = rs.getInt(1);
			System.out.println(n);
			
			if (n == 0)	{	// 해당 게시글을 좋아요하고 있지 않으면
				// 내 좋아요 테이블 insert
				sql = "insert into t_member_good (mi_email, mg_kind, mg_linkidx) " + 
					"values ('" + miemail + "', '" + kind + "', '" + linkidx + "')";
				System.out.println(sql);
				result += stmt.executeUpdate(sql);
				
				// kind값에 따라 해당 게시글 테이블 update : 좋아요 수(_good) + 1
				if (kind.equals("m")) {	// 게시글이 이달의 추천이면
					sql = "update t_monthly_rec set mr_good = mr_good + 1 where mr_idx = '" + linkidx + "'";
					System.out.println(sql);
					result += stmt.executeUpdate(sql);
				} else if (kind.equals("c")) {	// 게시글이 캠핑후기이면
					sql = "update t_camping_review set cr_good = cr_good + 1 where cr_idx = '" + linkidx + "'";
					System.out.println(sql);
					result += stmt.executeUpdate(sql);
				} else if (kind.equals("f")) {	// 게시글이 질문과답변이면
					sql = "update t_brd_free set bf_good = bf_good + 1 where bf_idx = '" + linkidx + "'";
					System.out.println(sql);
					result += stmt.executeUpdate(sql);
				}
				
				System.out.println(result);
			}
			
		} catch(Exception e) {
			System.out.println("doGood() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return result;
	}

	public int unGood(String kind, String linkidx, String miemail) {
	// 내가 해당 게시글을 좋아요 중인지 검사해서 없으면 좋아요 하는 메소드
		Statement stmt = null;
		ResultSet rs = null;
		int result = 0;
		try {
			// kind = ms_kind
			// ms_linkidx = 글 일련번호
			String sql = "select count(*) from t_member_good " + 
				"where mi_email = '" + miemail + "' and mg_kind = '" + kind + "' and mg_linkidx = '" + linkidx + "'";
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			int n = rs.getInt(1);
			System.out.println(n);
			
			if (n == 1)	{	// 해당 게시글을 좋아요 중이면
				// 내 좋아요 테이블 delete
				sql = "delete from t_member_good " +
					"where mi_email = '" + miemail + "' and mg_kind = '" + kind + "' and mg_linkidx = '" + linkidx + "'";
				System.out.println(sql);
				result += stmt.executeUpdate(sql);
				
				// kind값에 따라 해당 게시글 테이블 update : 좋아요 수(_scrap) - 1
				if (kind.equals("m")) {	// 게시글이 이달의 추천이면
					sql = "update t_monthly_rec set mr_good = mr_good - 1 where mr_idx = '" + linkidx + "'";
					System.out.println(sql);
					result += stmt.executeUpdate(sql);
				} else if (kind.equals("c")) {	// 게시글이 캠핑후기이면
					sql = "update t_camping_review set cr_good = cr_good - 1 where cr_idx = '" + linkidx + "'";
					System.out.println(sql);
					result += stmt.executeUpdate(sql);
				} else if (kind.equals("f")) {	// 게시글이 질문과답변이면
					sql = "update t_brd_free set bf_good = bf_good - 1 where bf_idx = '" + linkidx + "'";
					System.out.println(sql);
					result += stmt.executeUpdate(sql);
				}
				
				System.out.println(result);
			}
			
		} catch(Exception e) {
			System.out.println("unGood() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return result;
	}
	
	public int doScrap(String kind, String linkidx, String miemail) {
	// 내가 해당 게시글을 스크랩 중인지 검사해서 없으면 스크랩 하는 메소드
		Statement stmt = null;
		ResultSet rs = null;
		int result = 0;
		try {
			// kind = ms_kind
			// ms_linkidx = 글 일련번호
			String sql = "select count(*) from t_member_scrap " + 
				"where mi_email = '" + miemail + "' and ms_kind = '" + kind + "' and ms_linkidx = '" + linkidx + "'";
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			int n = rs.getInt(1);
			System.out.println(n);
			
			if (n == 0)	{	// 해당 게시글을 스크랩하고 있지 않으면
				// 내 스크랩 테이블 insert
				sql = "insert into t_member_scrap (mi_email, ms_kind, ms_linkidx) " + 
					"values ('" + miemail + "', '" + kind + "', '" + linkidx + "')";
				System.out.println(sql);
				result += stmt.executeUpdate(sql);
				
				// kind값에 따라 해당 게시글 테이블 update : 스크랩 수(_scrap) + 1
				if (kind.equals("p")) {	// 게시글이 상품이면
					sql = "update 상품 테이블 set _scrap = _scrap + 1 where _idx = '" + linkidx + "'";
					System.out.println(sql);
					result += stmt.executeUpdate(sql);
				} else if (kind.equals("c")) {	// 게시글이 캠핑후기이면
					sql = "update t_camping_review set cr_scrap = cr_scrap + 1 where cr_idx = '" + linkidx + "'";
					System.out.println(sql);
					result += stmt.executeUpdate(sql);
				}
				
				System.out.println(result);
			}
			
		} catch(Exception e) {
			System.out.println("doScrap() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return result;
	}

	public int unScrap(String kind, String linkidx, String miemail) {
	// 내가 해당 게시글을 스크랩 중인지 검사해서 없으면 스크랩 하는 메소드
		Statement stmt = null;
		ResultSet rs = null;
		int result = 0;
		try {
			// kind = ms_kind
			// ms_linkidx = 글 일련번호
			String sql = "select count(*) from t_member_scrap " + 
				"where mi_email = '" + miemail + "' and ms_kind = '" + kind + "' and ms_linkidx = '" + linkidx + "'";
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			int n = rs.getInt(1);
			System.out.println(n);
			
			if (n == 1)	{	// 해당 게시글을 스크랩 중이면
				// 내 스크랩 테이블 delete
				sql = "delete from t_member_scrap " +
					"where mi_email = '" + miemail + "' and ms_kind = '" + kind + "' and ms_linkidx = '" + linkidx + "'";
				System.out.println(sql);
				result += stmt.executeUpdate(sql);
				
				// kind값에 따라 해당 게시글 테이블 update : 스크랩 수(_scrap) - 1
				if (kind.equals("p")) {	// 게시글이 상품이면
					sql = "update 상품 테이블 set _scrap = _scrap - 1 where _idx = '" + linkidx + "'";
					System.out.println(sql);
					result += stmt.executeUpdate(sql);
				} else if (kind.equals("c")) {	// 게시글이 캠핑후기이면
					sql = "update t_camping_review set cr_scrap = cr_scrap - 1 where cr_idx = '" + linkidx + "'";
					System.out.println(sql);
					result += stmt.executeUpdate(sql);
				}
				
				System.out.println(result);
			}
			
		} catch(Exception e) {
			System.out.println("unScrap() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return result;
	}
}
