package admin.dao;

import static db.JdbcUtil.*;
import javax.sql.*;
import java.sql.*;
import java.util.*;
import vo.*;

public class AdminMemberDao {
	private static AdminMemberDao adminMemberDao;
	private Connection conn;
	private AdminMemberDao() {}
	
	public static AdminMemberDao getInstance() {
		if (adminMemberDao == null) {	
			adminMemberDao = new AdminMemberDao();
		}	
		return adminMemberDao;	
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	// ------> �̱��� ����� ����
	
	public int getMemberCount(String where) {
	// �˻��� ȸ���� �� ������ �����ϴ� �޼ҵ�(�˻������� ���� ��� �˻��� ����� ������ ����)
		Statement stmt = null;	
		ResultSet rs = null;	
		int rcnt = 0;	// ȸ�� �ο����� ������ ����
		
		try {
			String sql = "select count(*) from t_member_info " + where;
			System.out.println(sql);
			// ȸ�� �� �ο����� ������ ���� �ۼ�
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {	
				rcnt = rs.getInt(1);
			}	// �Խñ��� ������ rcnt�� ����(�Խñ��� ������ 0�� �����)
			// count �Լ��� ������� ���� ���� �����Ƿ�(���� ������ 0�̴ϱ�) if���� ������� �ʾƵ� ��
			// �׷��� rs.next()�� �ݵ�� ���־�� �ϹǷ� ���� �ͼ��� ���·� ���̱� ���� if���� ���
			
		} catch(Exception e) {
			System.out.println("getMemberCount() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		return rcnt;
	}
	
	public ArrayList<MemberInfo> getMemberList(String where, String orderBy, int cpage, int psize) {
	// �˻��� ȸ�� ����� ArrayList<MemberInfo>�� �ν��Ͻ��� �����ϴ� �޼ҵ�
		ArrayList<MemberInfo> MemberList = new ArrayList<MemberInfo>();
		// ȸ�� ����� ������ ArrayList�� ���� MemberInfo�� �ν��Ͻ��� �����
		
		Statement stmt = null;
		ResultSet rs = null;		
		
		MemberInfo memberInfo = null;
		// MemberList�� ���� �����͸� ���� MemberInfo�� �ν��Ͻ��� ����
		
		int snum = (cpage - 1) * psize;
		// ������ limit ��ɿ��� �����͸� ������ ���� �ε��� ��ȣ
		
		try {
			String sql = "select * from t_member_info " + where + orderBy + " limit " + snum + ", " + psize;
			
			stmt = conn.createStatement();				
			rs = stmt.executeQuery(sql);
			System.out.println(sql);
			while (rs.next()) {
			// rs�� ������ ȸ���� ���� ��� 
				memberInfo = new MemberInfo();
				// MemberList�� ������ �� ��ǰ�� ������ ���� �ν��Ͻ� ����

				memberInfo.setMi_email(rs.getString("mi_email"));
				memberInfo.setMi_nick(rs.getString("mi_nick"));
				memberInfo.setMi_phone(rs.getString("mi_phone"));
				memberInfo.setMi_birth(rs.getString("mi_birth"));
				memberInfo.setMi_gender(rs.getString("mi_gender"));
				memberInfo.setMi_joindate(rs.getString("mi_joindate"));
				memberInfo.setMi_isactive(rs.getString("mi_isactive"));
				memberInfo.setMi_lastlogin(rs.getString("mi_lastlogin"));
				memberInfo.setMi_memo(rs.getString("mi_memo"));
	            // �޾ƿ� ���ڵ��� ��ǰ ������ ����
				
				MemberList.add(memberInfo);
				// �� �Խñ��� ������ ���� MemberInfo �ν��Ͻ��� MemberList�� ����
			 }
			
		} catch(Exception e) {
			System.out.println("getMemberList() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		
		return MemberList;
		
	}
	
	public MemberInfo getMemberInfo(String email) {
		Statement stmt = null;
		ResultSet rs = null;
		MemberInfo memberInfo = null;
		
		try {
			String sql = "select a.*, b.ma_zip, b.ma_addr1, b.ma_addr2, b.ma_basic " + 
						 " from t_member_info a, t_member_addr b " + 
						 " where a.mi_email = b.mi_email and b.ma_basic = 'y' " +
						 " and a.mi_email = '" + email + "' ";
			System.out.println(sql);
			stmt = conn.createStatement();				
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				memberInfo = new MemberInfo();
				memberInfo.setMi_email(rs.getString("mi_email"));
				memberInfo.setMi_nick(rs.getString("mi_nick"));
				memberInfo.setMi_pwd(rs.getString("mi_pwd"));
				memberInfo.setMi_phone(rs.getString("mi_phone"));
				memberInfo.setMi_birth(rs.getString("mi_birth"));
				memberInfo.setMi_gender(rs.getString("mi_gender"));
				memberInfo.setMi_joindate(rs.getString("mi_joindate"));
				memberInfo.setMi_isactive(rs.getString("mi_isactive"));
				memberInfo.setMi_lastlogin(rs.getString("mi_lastlogin"));
				memberInfo.setMa_zip(rs.getString("ma_zip"));
				memberInfo.setMa_addr1(rs.getString("ma_addr1"));
				memberInfo.setMa_addr2(rs.getString("ma_addr2"));
				memberInfo.setMi_memo(rs.getString("mi_memo"));
				// ����Ƚ��?
	            
			 }
			
		} catch(Exception e) {
			System.out.println("getMemberInfo() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		return memberInfo;

	}
	
	public int updateMemo(String email, String memo) {	
		int result = 0;
		Statement stmt = null;

		try {
			stmt = conn.createStatement(); 			
			String sql = "update t_member_info set mi_memo = '" + memo + "' " +
						 "where mi_email = '" + email + "' ";
			System.out.println(sql);
			result = stmt.executeUpdate(sql);
			
		} catch(Exception e) {
			System.out.println("updateMemo() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}
}
