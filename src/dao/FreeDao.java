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
	// �˻��� �Խñ��� �� ������ �����ϴ� �޼ҵ�(�˻������� ���� ��� �˻��� ����� ������ ����)
		Statement stmt = null;	
		ResultSet rs = null;	
		int rcnt = 0;	// �Խñ� ������ ������ ����
		
		try {
			String sql = "select count(*) from t_brd_free where bf_isview = 'y' " + where;
			// �Խñ��� �� ������ ������ ���� �ۼ�
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {	
				rcnt = rs.getInt(1);
			}	// �Խñ��� ������ rcnt�� ����(�Խñ��� ������ 0�� �����)
			// count �Լ��� ������� ���� ���� �����Ƿ�(���� ������ 0�̴ϱ�) if���� ������� �ʾƵ� ��
			// �׷��� rs.next()�� �ݵ�� ���־�� �ϹǷ� ���� �ͼ��� ���·� ���̱� ���� if���� ���
			
		} catch(Exception e) {
			System.out.println("getFreeCount() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		return rcnt;
	}
	
	public ArrayList<FreeInfo> getFreeList(String where, String orderBy, int cpage, int psize) {
	// �˻��� �Խñ� ����� ArrayList<FreeInfo>�� �ν��Ͻ��� �����ϴ� �޼ҵ�
		ArrayList<FreeInfo> freeList = new ArrayList<FreeInfo>();
		// �Խñ� ����� ������ ArrayList�� ���� FreeInfo�� �ν��Ͻ��� �����
		
		Statement stmt = null;
		ResultSet rs = null;
		
		FreeInfo freeInfo = null;
		// freeInfo�� ���� �����͸� ���� FreeInfo�� �ν��Ͻ��� ����
		
		int snum = (cpage - 1) * psize;
		// ������ limit ��ɿ��� �����͸� ������ ���� �ε��� ��ȣ
		
		try {
			String sql = "select * from t_brd_free where bf_isview = 'y' " + 
					where + orderBy + " limit " + snum + ", " + psize;
			
			System.out.println(sql);
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
			// rs�� ������ �Խñ��� ���� ��� 
				freeInfo = new FreeInfo();
				// freeList�� ������ �� �Խñ��� ������ ���� �ν��Ͻ� ����

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
	            // �޾ƿ� ���ڵ��� �Խñ� ������ ����
				
				freeList.add(freeInfo);
				// �� �Խñ��� ������ ���� freeInfo �ν��Ͻ��� freeList�� ����
			 }
			
		} catch(Exception e) {
			System.out.println("getFreeList() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		
		return freeList;
		
	}
	
	public FreeInfo getFreeInfo(int idx) {
	// ������ id�� �ش��ϴ� Ư�� ��ǰ ������ ProductInfo�� �ν��Ͻ��� �����ϴ� �޼ҵ�
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
			System.out.println("getFreeInfo() �޼ҵ� ����");
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
			System.out.println("readCountUp() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}
		
	public int freeInsert(FreeInfo freeInfo) {	// ���� ��� ó���� ���� �޼ҵ�
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
			System.out.println("freeInsert() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}
	
	public int freeUpdate(FreeInfo freeInfo) {	// ���� ���� ó���� ���� �޼ҵ�
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
			System.out.println("freeUpdate() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}
	
	public int freeDelete(FreeInfo freeInfo) {	// ���� ���� ó���� ���� �޼ҵ�
		int result = 0;
		Statement stmt = null;
		
		try {
			stmt = conn.createStatement(); 			
			String sql = "delete from t_brd_free where mi_email = '" + freeInfo.getMi_email() + "' and bf_idx = " + freeInfo.getBf_idx();
			System.out.println(sql);
			result = stmt.executeUpdate(sql);
			
		} catch(Exception e) {
			System.out.println("freeDelete() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}
	
	public int getFreeReplyCount() {
		Statement stmt = null;	
		ResultSet rs = null;	
		int rcnt = 0;	// �Խñ� ������ ������ ����
		
		try {
			String sql = "select count(*) from t_brd_free_reply where bfr_isview = 'y'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {	
				rcnt = rs.getInt(1);
			}	
			
		} catch(Exception e) {
			System.out.println("getFreeReplyCount() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		return rcnt;
	}
	
	public ArrayList<FreeReplyInfo> getFreeReplyList(int idx) {
	// �˻��� �Խñ� ����� ArrayList<FreeInfo>�� �ν��Ͻ��� �����ϴ� �޼ҵ�
		ArrayList<FreeReplyInfo> replyList = new ArrayList<FreeReplyInfo>();
		// �Խñ� ����� ������ ArrayList�� ���� FreeInfo�� �ν��Ͻ��� �����
		
		Statement stmt = null;
		ResultSet rs = null;
		
		FreeReplyInfo replyInfo = null;
		// freeInfo�� ���� �����͸� ���� FreeInfo�� �ν��Ͻ��� ����
		
		//int snum = (cpage - 1) * psize;
		// ������ limit ��ɿ��� �����͸� ������ ���� �ε��� ��ȣ
		
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
			System.out.println("FreeReplyInfo() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		
		return replyList;
		
	}

	public FreeReplyInfo getFreeReplyInfo(int idx) {
	// ������ id�� �ش��ϴ� Ư�� ��ǰ ������ ProductInfo�� �ν��Ͻ��� �����ϴ� �޼ҵ�
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
			System.out.println("getFreeReplyInfo() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
      
		return replyInfo;
	}
	
	public int freeReplyInsert(FreeReplyInfo replyInfo) {	// ���� ��� ó���� ���� �޼ҵ�
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
			System.out.println("freeReplyInsert() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}
	
	public int freeReplyUpdate(FreeReplyInfo replyInfo) {	// ���� ���� ó���� ���� �޼ҵ�
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
			System.out.println("freeReplyUpdate() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}
	
	public int freeReplyDelete(FreeReplyInfo replyInfo) {	// ���� ���� ó���� ���� �޼ҵ�
		int result = 0;
		Statement stmt = null;
		
		try {
			stmt = conn.createStatement(); 			
			String sql = "delete from t_brd_free where mi_email = '" + replyInfo.getMi_email() + "' and bf_idx = " + replyInfo.getBfr_idx();
			System.out.println(sql);
			result = stmt.executeUpdate(sql);
			
		} catch(Exception e) {
			System.out.println("freeReplyDelete() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}
}
