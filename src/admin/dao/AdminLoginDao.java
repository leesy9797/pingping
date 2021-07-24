package admin.dao;

import static db.JdbcUtil.*;
import java.sql.*;
import vo.*;

public class AdminLoginDao {
// �α��� ���� ������ �����Ͽ� �����Ű�� Ŭ����
	private static AdminLoginDao adminLoginDao;
	// �ν��Ͻ� ����� �ƴ� Ŭ���� ����� adminLoginDao �ν��Ͻ��� ���������ν� ���� ���� �ƴ� �ϳ��� �����ϰ� ��
	private Connection conn;
	// Ŭ���� ��ü���� Connection �ν��Ͻ� conn�� ����� �� �ְ� ��
	private AdminLoginDao() {}
	// �ܺο��� ���������� �ν��Ͻ��� �����ϴ� ��(new Ű���� ���)�� ���� ���� private���� �⺻ �����ڸ� ����
	
	public static AdminLoginDao getInstance() {
	// �ν��Ͻ��� �������ִ� �޼ҵ�� �ϳ��� �ν��Ͻ��� ������Ŵ(singleton ���)
	// DB�۾��� ���� �ϴ� Ŭ���� Ư���� ���� ���� �ν��Ͻ��� �����Ǹ� 
	// �׸�ŭ ���� ���� DB����(Connection)�� ����Ƿ� ��ü���� �ӵ� ������ ����� �־� �̱��� ����� ���
		if (adminLoginDao == null) {
		// ����� ����� AdminLoginDao �� �ν��Ͻ� adminLoginDao�� null�̸�(�ν��Ͻ��� �������� �ʾ�����)
			adminLoginDao = new AdminLoginDao();
			// ������ adminLoginDao �ν��Ͻ��� �����Ƿ� ���Ӱ� �ϳ� ������
		}
		return adminLoginDao;
	}
	public void setConnection(Connection conn) {
	// AdminLoginSvc Ŭ�������� ���� Connection��ü�� �޾� ����� conn�� ����
	// �ܺο��� Connection ��ü�� �޴� ������ DB�۾��� ���� ���� ��� 
	// Connection ��ü�� ���� �� �����ϴ� ���� ���� ���� ����� �����Ͽ� ���
		this.conn = conn;
	}
	public AdminInfo getLoginAdmin(String uid, String pwd) {	// ����Ÿ���� MemberInfo�� �ν��Ͻ�
	// ����ڰ� �Է��� ���̵�� ��й�ȣ�� �̿��Ͽ� �α��� �� �ʿ��� ����� ������ �����Ͽ� MemberInfo������ �����ϴ� �޼ҵ�
		AdminInfo adminInfo = null;	// �α��� �� ����� ������ ������ �ν��Ͻ�
		Statement stmt = null;
		ResultSet rs = null;
		try {
		// DB���� �۾������� ��κ��� �޼ҵ尡 'throws SQLException'���� ����Ǿ� �ֱ� ������ ����ó���� �ؾ� �� 
			stmt = conn.createStatement();
			String sql = "select * from t_admin_info where ai_id = '" + uid + "' and ai_pwd = '" + pwd + "' and ai_isrun = 'y'";
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {	// �α��� ���� ��
				adminInfo = new AdminInfo();	// �α��� ���� �ÿ��� �����Ǵ� �ν��Ͻ�
				// �α��� ���� �ÿ��� adminInfo�� ����ִ� ����(null)�� ���ϵǰ� ��
				adminInfo.setAi_id(uid);
//	            adminInfo.setAi_pwd(pwd);
	            adminInfo.setAi_idx(rs.getInt("ai_idx"));
	            adminInfo.setAi_name(rs.getString("ai_name"));
	            adminInfo.setAi_pms(rs.getString("ai_pms"));
//	            adminInfo.setAi_isrun(rs.getString("ai_isrun"));
	            adminInfo.setAi_regdate(rs.getString("ai_regdate"));
				// AdminInfoŬ������ �ν��Ͻ� adminInfo�� ȸ��������� �⺻�ּҸ� ����
			}
			
		} catch(Exception e) {
			System.out.println("getLoginAdmin() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);	// conn �ȴݰ� AdminLoginSvc.java���� ����
		}
		
		return adminInfo;	// AdminLoginSvc.java�� ����
	}
}
