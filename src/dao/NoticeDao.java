package dao;

import static db.JdbcUtil.*;
import javax.sql.*;
import java.sql.*;
import java.util.*;
import vo.*;


public class NoticeDao {
// �������� ���� DB�۾��� ������ ó���ϴ� �޼ҵ���� ���� Ŭ����
	private static NoticeDao noticeDao;
	// NoticeDao �� �ν��Ͻ��� �ϳ��� ������ �ϱ� ���� static���� ����
	private Connection conn;
	private NoticeDao() {}
	// �⺻ �����ڷ� �ܺο��� �Ժη� �������� ���ϰ� private���� �����
	
	public static NoticeDao getInstance() {
	// NoticeDao�� �ν��Ͻ��� �����Ͽ� �����ϴ� �޼ҵ�� �ܺο��� ������ �� �ֵ��� static���� �����
		if (noticeDao == null) {
		// ���� ������ �ν��Ͻ��� ������ ���Ӱ� �ν��Ͻ��� ����
			noticeDao = new NoticeDao();
		}
		return noticeDao;
	}
	public void setConnection(Connection conn) {
	// �� Ŭ�������� DB�۾��� ���� Connection��ü�� �޾ƿ��� �޼ҵ�	
		this.conn = conn;
		// ��������� Connection��ü�� �����ϸ� Ŭ���� ��ü���� �����Ӱ� ��� ����
		
	}
	
	public int getArticleCount(String where ) {
	// �Խñ��� �� ������ �����ϴ� �޼ҵ� (�˻������� ���� ��� �˻��� ����� ������ ����)
		Statement stmt = null;	// ������ DB�� ������ Statement ����
		ResultSet rs = null;	// select������ ����� �޾ƿ��� ���� \��
		
		int rcnt = 0;	// �Խñ� ������ ������ ����
		
		try {
			String sql = "select count(*) from t_brd_notice" + where;
			// �Խñ��� �� ������ ������  ���� �ۼ�
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) {	
				rcnt = rs.getInt(1);
				// �Խñ��� ������ rcnt�� ����(�Խñ��� ������ 0�� �����)
			}
		} catch(Exception e) {
			System.out.println("getArticleCount() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs); close(stmt);
		}
		
		return rcnt;
	}
	
	public ArrayList<NoticeInfo> getArticleList(String where, int cpage, int psize ) {
		// �Խñ��� ����� ArrayList<NoticeInfo>������ �����ϴ� �޼ҵ� 
			Statement stmt = null;
			ResultSet rs = null;
			ArrayList<NoticeInfo> articleList = new ArrayList<NoticeInfo>();
			// �������� �Խñ��� ����� ������ ArrayList �ν��Ͻ� 
			NoticeInfo noticeInfo = null;
			// articleList�� ���� �������� NoticeInfo �� �ν��Ͻ��� ���� 
			int snum = (cpage - 1) * psize;
			// ������ limit ��ɿ��� �����͸� ������ ���� �ε��� ��ȣ
			try {
				String sql = "select * from t_brd_notice " + where +
						" order by bn_idx desc limit " + snum + ", " + psize;
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
				// rs�� ������ �Խñ��� ���� ��� 
					noticeInfo = new NoticeInfo();
					// articleList�� ������ �� �Խñ��� ������ ��� �ν��Ͻ� ����
					
					noticeInfo.setBn_idx(rs.getInt("bn_idx"));
					noticeInfo.setBn_title(rs.getString("bn_title"));
					noticeInfo.setBn_content(rs.getString("bn_content"));
					noticeInfo.setBn_read(rs.getInt("bn_read"));
					noticeInfo.setBn_isview(rs.getString("bn_isview"));
					noticeInfo.setBn_date(rs.getString("bn_date"));
					noticeInfo.setAi_idx(rs.getInt("ai_idx"));
					noticeInfo.setLast_date(rs.getString("last_date"));
					noticeInfo.setLast_admin(rs.getString("last_admin"));
					// �޾ƿ� ���ڵ��� �Խñ� ������ ����
					
					articleList.add(noticeInfo);
					// �� �Խñ��� ������ ���� noticeInfo �ν��Ͻ��� articleList�� ����
				}
			} catch(Exception e) {
				System.out.println("getArticleList() �޼ҵ� ����");
				e.printStackTrace();
			} finally {
				close(rs); close(stmt);
			}
			
			return articleList;
		}
	
		public int readCountUp(int idx) {
		// Ư�� �Խñ��� ��ȸ���� ������Ű�� �޼ҵ�
			Statement stmt = null;
			int result = 0;
			
			try {
				String sql = "update t_brd_notice set bn_read = bn_read + 1 where bn_idx = " + idx;
				stmt = conn.createStatement();
				result = stmt.executeUpdate(sql);
				
			} catch (Exception e){
				System.out.println("readCountUp() �޼ҵ� ����");
				e.printStackTrace();
			} finally {
				close(stmt);
			}
			return result;
		}
		
		public NoticeInfo getArticle(int idx) {
		// ����ڰ� ������ �Խñ��� ������ NoticeInfo �� �ν��Ͻ��� �����ϴ� �޼ҵ� 
			Statement stmt = null;
			ResultSet rs = null;
			NoticeInfo article = null;
			
			try {
				String sql = "select * from t_brd_notice " + " where bn_idx = " + idx;
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				if (rs.next()) {
				// rs�� ������ �Խñ��� ���� ��� 
					article = new NoticeInfo();
										
					article.setBn_idx(idx);
					article.setBn_title(rs.getString("bn_title"));
					article.setBn_content(rs.getString("bn_content"));
					article.setBn_read(rs.getInt("bn_read"));
					article.setBn_isview(rs.getString("bn_isview"));
					article.setBn_date(rs.getString("bn_date"));
					article.setAi_idx(rs.getInt("ai_idx"));
					// �޾ƿ� ���ڵ�� �Խñ� ������ ����
					
				}
			} catch(Exception e) {
				System.out.println("getArticleList() �޼ҵ� ����");
				e.printStackTrace();
			} finally {
				close(rs); close(stmt);
			}
			
			return article;
	}
	
}
