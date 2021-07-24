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
	// �˻��� �Խñ��� �� ������ �����ϴ� �޼ҵ�(�˻������� ���� ��� �˻��� ����� ������ ����)
		Statement stmt = null;	
		ResultSet rs = null;	
		int rcnt = 0;	// �Խñ� ������ ������ ����
		
		try {
			String sql = "select count(*) from t_monthly_rec where mr_isview = 'y'" + where;
			// �Խñ��� �� ������ ������ ���� �ۼ�
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {	
				rcnt = rs.getInt(1);
			}	// �Խñ��� ������ rcnt�� ����(�Խñ��� ������ 0�� �����)
			// count �Լ��� ������� ���� ���� �����Ƿ�(���� ������ 0�̴ϱ�) if���� ������� �ʾƵ� ��
			// �׷��� rs.next()�� �ݵ�� ���־�� �ϹǷ� ���� �ͼ��� ���·� ���̱� ���� if���� ���
			
		} catch(Exception e) {
			System.out.println("getMonthCount() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		return rcnt;
	}
	
	public ArrayList<MonthlyInfo> getMonthList(String where, String orderBy, int cpage, int psize) {
	// �˻��� �Խñ� ����� ArrayList<MonthlyInfo>�� �ν��Ͻ��� �����ϴ� �޼ҵ�
		ArrayList<MonthlyInfo> monthList = new ArrayList<MonthlyInfo>();
		// �Խñ� ����� ������ ArrayList�� ���� MonthlyInfo�� �ν��Ͻ��� �����
		
		Statement stmt = null;
		ResultSet rs = null;		
		
		MonthlyInfo monthInfo = null;
		// monthInfo�� ���� �����͸� ���� MonthlyInfo�� �ν��Ͻ��� ����
		
		int snum = (cpage - 1) * psize;
		// ������ limit ��ɿ��� �����͸� ������ ���� �ε��� ��ȣ
		
		try {
			String sql = "select * from t_monthly_rec where mr_isview = 'y'" + 
					where + orderBy + " limit " + snum + ", " + psize;
			
			System.out.println(sql);
			
			stmt = conn.createStatement();				
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
			// rs�� ������ �Խñ��� ���� ��� 
				monthInfo = new MonthlyInfo();
				// monthList�� ������ �� �Խñ��� ������ ���� �ν��Ͻ� ����

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
	            // �޾ƿ� ���ڵ��� �Խñ� ������ ����
				
	            monthList.add(monthInfo);
				// �� �Խñ��� ������ ���� monthInfo �ν��Ͻ��� monthList�� ����
			 }
			
		} catch(Exception e) {
			System.out.println("getMonthList() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		
		return monthList;
		
	}

	public MonthlyInfo getMonthInfo(int idx) {
	// ������ idx�� �ش��ϴ� Ư�� �Խñ� ������ MonthlyInfo�� �ν��Ͻ��� �����ϴ� �޼ҵ�
		Statement stmt = null;
		ResultSet rs = null;
		MonthlyInfo monthInfo = null;
		// �ϳ��� �Խñ� ������ �����Ͽ� ������  MonthlyInfo�� �ν��Ͻ��� ����
		
		try {
			String sql = "select * from t_monthly_rec where mr_idx = '" + idx + "' and mr_isview = 'y'";
			System.out.println(sql);
			stmt = conn.createStatement();				
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
			// rs�� ������ �Խñ��� ���� ��� 
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
				// �޾ƿ� ���ڵ�� �Խñ� ������ ����
			 }
			
		} catch(Exception e) {
			System.out.println("getMonthInfo() �޼ҵ� ����");
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
			System.out.println("readCountUp() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}

	// ��� ó��
	public int getMonthReplyCount() {
		Statement stmt = null;	
		ResultSet rs = null;	
		int rcnt = 0;	// �Խñ� ������ ������ ����
		
		try {
			String sql = "select count(*) from t_monthly_rec_reply where mrr_isview = 'y'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {	
				rcnt = rs.getInt(1);
			}	
			
		} catch(Exception e) {
			System.out.println("getMonthReplyCount() �޼ҵ� ����");
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
		// ������ limit ��ɿ��� �����͸� ������ ���� �ε��� ��ȣ
		
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
			System.out.println("getMonthReplyList() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		
		return replyList;
		
	}
	
	public int MonthReplyInsert(MonthReplyInfo replyInfo) {	// ��� ��� ó���� ���� �޼ҵ�
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
			System.out.println("MonthReplyInsert() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}
	
	public int MonthReplyUpdate(MonthReplyInfo replyInfo) {	// ��� ���� ó���� ���� �޼ҵ�
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
			System.out.println("MonthReplyUpdate() �޼ҵ� ����");
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
			System.out.println("MonthReplyDelete() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}
}
