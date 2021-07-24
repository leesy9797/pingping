package dao;

import static db.JdbcUtil.*;
import java.sql.*;
import java.util.ArrayList;

import vo.*;

public class ChkFGSDao {
// �ȷο�, ���ƿ�, ��ũ�� ���� Ȯ�ο� Ŭ����
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
			System.out.println("getFollowList() �޼ҵ� ����");
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
			System.out.println("getGoodList() �޼ҵ� ����");
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
			System.out.println("getScrapList() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		
		return scrapList;
	}
	
	public int doFollow(String ciemail, String miemail) {
	// ���� �ش� ȸ���� �ȷο� ������ �˻��ؼ� ������ �ȷο� �ϴ� �޼ҵ�
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
			if (ciemail.equals(miemail))	n = 1;	// �ڱ��ڽ��� �ȷο� �ϴ� ���
			System.out.println(n);
			
			if (n == 0)	{
				// �� �ȷ��� ���̺� insert
				sql = "insert into t_member_following (mfg_email, mi_email) " + 
					"values ('" + ciemail + "', '" + miemail + "')";
				System.out.println(sql);
				result += stmt.executeUpdate(sql);
				
				// ��� �ȷο� ���̺� insert
				sql = "insert into t_member_follower (mi_email, mfr_email) " + 
					"values ('" + ciemail + "', '" + miemail + "')";
				System.out.println(sql);
				result += stmt.executeUpdate(sql);
				
				// �� ȸ������ ���̺� update : �ȷ��� �� +1
				sql = "update t_member_info set mi_following = mi_following + 1 where mi_email = '" + miemail + "'";
				System.out.println(sql);
				result += stmt.executeUpdate(sql);
				
				// ��� ȸ������ ���̺� update : �ȷο� �� +1
				sql = "update t_member_info set mi_follower = mi_follower + 1 where mi_email = '" + ciemail + "'";
				System.out.println(sql);
				result += stmt.executeUpdate(sql);
				
				System.out.println(result);
			}
			
		} catch(Exception e) {
			System.out.println("doFollow() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return result;
	}

	public int unFollow(String ciemail, String miemail) {
	// ���� �ش� ȸ���� �ȷο� ������ �˻��ؼ� ������ �ȷο� ����ϴ� �޼ҵ�
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
			if (ciemail.equals(miemail))	n = 0;	// �ڱ��ڽ��� �ȷο� ��� �ϴ� ���
			System.out.println(n);
			
			if (n == 1)	{
				// �� �ȷ��� ���̺� delete
				sql = "delete from t_member_following where mi_email = '" + miemail + "' and mfg_email = '" + ciemail + "'";
				System.out.println(sql);
				result += stmt.executeUpdate(sql);
				
				// ��� �ȷο� ���̺� delete
				sql = "delete from t_member_follower where mi_email = '" + ciemail + "' and mfr_email = '" + miemail + "'";
				System.out.println(sql);
				result += stmt.executeUpdate(sql);
				
				// �� ȸ������ ���̺� update : �ȷ��� �� -1
				sql = "update t_member_info set mi_following = mi_following - 1 where mi_email = '" + miemail + "'";
				System.out.println(sql);
				result += stmt.executeUpdate(sql);
				
				// ��� ȸ������ ���̺� update : �ȷο� �� -1
				sql = "update t_member_info set mi_follower = mi_follower - 1 where mi_email = '" + ciemail + "'";
				System.out.println(sql);
				result += stmt.executeUpdate(sql);
				
				System.out.println(result);
			}
			
		} catch(Exception e) {
			System.out.println("unFollow() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return result;
	}

	public int doGood(String kind, String linkidx, String miemail) {
	// ���� �ش� �Խñ��� ���ƿ� ������ �˻��ؼ� ������ ���ƿ� �ϴ� �޼ҵ�
		Statement stmt = null;
		ResultSet rs = null;
		int result = 0;
		try {
			// kind = mg_kind
			// mg_linkidx = �� �Ϸù�ȣ
			String sql = "select count(*) from t_member_good " + 
				"where mi_email = '" + miemail + "' and mg_kind = '" + kind + "' and mg_linkidx = '" + linkidx + "'";
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			int n = rs.getInt(1);
			System.out.println(n);
			
			if (n == 0)	{	// �ش� �Խñ��� ���ƿ��ϰ� ���� ������
				// �� ���ƿ� ���̺� insert
				sql = "insert into t_member_good (mi_email, mg_kind, mg_linkidx) " + 
					"values ('" + miemail + "', '" + kind + "', '" + linkidx + "')";
				System.out.println(sql);
				result += stmt.executeUpdate(sql);
				
				// kind���� ���� �ش� �Խñ� ���̺� update : ���ƿ� ��(_good) + 1
				if (kind.equals("m")) {	// �Խñ��� �̴��� ��õ�̸�
					sql = "update t_monthly_rec set mr_good = mr_good + 1 where mr_idx = '" + linkidx + "'";
					System.out.println(sql);
					result += stmt.executeUpdate(sql);
				} else if (kind.equals("c")) {	// �Խñ��� ķ���ı��̸�
					sql = "update t_camping_review set cr_good = cr_good + 1 where cr_idx = '" + linkidx + "'";
					System.out.println(sql);
					result += stmt.executeUpdate(sql);
				} else if (kind.equals("f")) {	// �Խñ��� �������亯�̸�
					sql = "update t_brd_free set bf_good = bf_good + 1 where bf_idx = '" + linkidx + "'";
					System.out.println(sql);
					result += stmt.executeUpdate(sql);
				}
				
				System.out.println(result);
			}
			
		} catch(Exception e) {
			System.out.println("doGood() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return result;
	}

	public int unGood(String kind, String linkidx, String miemail) {
	// ���� �ش� �Խñ��� ���ƿ� ������ �˻��ؼ� ������ ���ƿ� �ϴ� �޼ҵ�
		Statement stmt = null;
		ResultSet rs = null;
		int result = 0;
		try {
			// kind = ms_kind
			// ms_linkidx = �� �Ϸù�ȣ
			String sql = "select count(*) from t_member_good " + 
				"where mi_email = '" + miemail + "' and mg_kind = '" + kind + "' and mg_linkidx = '" + linkidx + "'";
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			int n = rs.getInt(1);
			System.out.println(n);
			
			if (n == 1)	{	// �ش� �Խñ��� ���ƿ� ���̸�
				// �� ���ƿ� ���̺� delete
				sql = "delete from t_member_good " +
					"where mi_email = '" + miemail + "' and mg_kind = '" + kind + "' and mg_linkidx = '" + linkidx + "'";
				System.out.println(sql);
				result += stmt.executeUpdate(sql);
				
				// kind���� ���� �ش� �Խñ� ���̺� update : ���ƿ� ��(_scrap) - 1
				if (kind.equals("m")) {	// �Խñ��� �̴��� ��õ�̸�
					sql = "update t_monthly_rec set mr_good = mr_good - 1 where mr_idx = '" + linkidx + "'";
					System.out.println(sql);
					result += stmt.executeUpdate(sql);
				} else if (kind.equals("c")) {	// �Խñ��� ķ���ı��̸�
					sql = "update t_camping_review set cr_good = cr_good - 1 where cr_idx = '" + linkidx + "'";
					System.out.println(sql);
					result += stmt.executeUpdate(sql);
				} else if (kind.equals("f")) {	// �Խñ��� �������亯�̸�
					sql = "update t_brd_free set bf_good = bf_good - 1 where bf_idx = '" + linkidx + "'";
					System.out.println(sql);
					result += stmt.executeUpdate(sql);
				}
				
				System.out.println(result);
			}
			
		} catch(Exception e) {
			System.out.println("unGood() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return result;
	}
	
	public int doScrap(String kind, String linkidx, String miemail) {
	// ���� �ش� �Խñ��� ��ũ�� ������ �˻��ؼ� ������ ��ũ�� �ϴ� �޼ҵ�
		Statement stmt = null;
		ResultSet rs = null;
		int result = 0;
		try {
			// kind = ms_kind
			// ms_linkidx = �� �Ϸù�ȣ
			String sql = "select count(*) from t_member_scrap " + 
				"where mi_email = '" + miemail + "' and ms_kind = '" + kind + "' and ms_linkidx = '" + linkidx + "'";
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			int n = rs.getInt(1);
			System.out.println(n);
			
			if (n == 0)	{	// �ش� �Խñ��� ��ũ���ϰ� ���� ������
				// �� ��ũ�� ���̺� insert
				sql = "insert into t_member_scrap (mi_email, ms_kind, ms_linkidx) " + 
					"values ('" + miemail + "', '" + kind + "', '" + linkidx + "')";
				System.out.println(sql);
				result += stmt.executeUpdate(sql);
				
				// kind���� ���� �ش� �Խñ� ���̺� update : ��ũ�� ��(_scrap) + 1
				if (kind.equals("p")) {	// �Խñ��� ��ǰ�̸�
					sql = "update ��ǰ ���̺� set _scrap = _scrap + 1 where _idx = '" + linkidx + "'";
					System.out.println(sql);
					result += stmt.executeUpdate(sql);
				} else if (kind.equals("c")) {	// �Խñ��� ķ���ı��̸�
					sql = "update t_camping_review set cr_scrap = cr_scrap + 1 where cr_idx = '" + linkidx + "'";
					System.out.println(sql);
					result += stmt.executeUpdate(sql);
				}
				
				System.out.println(result);
			}
			
		} catch(Exception e) {
			System.out.println("doScrap() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return result;
	}

	public int unScrap(String kind, String linkidx, String miemail) {
	// ���� �ش� �Խñ��� ��ũ�� ������ �˻��ؼ� ������ ��ũ�� �ϴ� �޼ҵ�
		Statement stmt = null;
		ResultSet rs = null;
		int result = 0;
		try {
			// kind = ms_kind
			// ms_linkidx = �� �Ϸù�ȣ
			String sql = "select count(*) from t_member_scrap " + 
				"where mi_email = '" + miemail + "' and ms_kind = '" + kind + "' and ms_linkidx = '" + linkidx + "'";
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			int n = rs.getInt(1);
			System.out.println(n);
			
			if (n == 1)	{	// �ش� �Խñ��� ��ũ�� ���̸�
				// �� ��ũ�� ���̺� delete
				sql = "delete from t_member_scrap " +
					"where mi_email = '" + miemail + "' and ms_kind = '" + kind + "' and ms_linkidx = '" + linkidx + "'";
				System.out.println(sql);
				result += stmt.executeUpdate(sql);
				
				// kind���� ���� �ش� �Խñ� ���̺� update : ��ũ�� ��(_scrap) - 1
				if (kind.equals("p")) {	// �Խñ��� ��ǰ�̸�
					sql = "update ��ǰ ���̺� set _scrap = _scrap - 1 where _idx = '" + linkidx + "'";
					System.out.println(sql);
					result += stmt.executeUpdate(sql);
				} else if (kind.equals("c")) {	// �Խñ��� ķ���ı��̸�
					sql = "update t_camping_review set cr_scrap = cr_scrap - 1 where cr_idx = '" + linkidx + "'";
					System.out.println(sql);
					result += stmt.executeUpdate(sql);
				}
				
				System.out.println(result);
			}
			
		} catch(Exception e) {
			System.out.println("unScrap() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return result;
	}
}
