package admin.svc;

import static db.JdbcUtil.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class AdminLoginSvc {
// �α��ο� ���� ó���۾��� DB�� ������ ��� ó���� �ϴ� Ŭ����
	public AdminInfo getLoginAdmin(String uid, String pwd) {
	// ����ڰ� �Է��� ���̵�� ��й�ȣ�� �Ű������� �޾� �α����� ó���ϴ� �޼ҵ�� AdminInfo�� �ν��Ͻ��� ����� ������
		AdminLoginDao adminLoginDao = AdminLoginDao.getInstance();
		// �α����� DB�۾��� ���� AdminLoginDao�� �ν��Ͻ� adminLoginDao�� ����
		Connection conn = getConnection();
		// JdbcUtil Ŭ������ getConnection() �޼ҵ�� Connection ��ü conn ����
		adminLoginDao.setConnection(conn);
		// adminLoginDao�ν��Ͻ����� ����� Connection��ü ����

		AdminInfo adminInfo = adminLoginDao.getLoginAdmin(uid, pwd);
		// adminLoginDao �ν��Ͻ��� getLoginAdmin() �޼ҵ带 �����Ű��
		// �� ����� AdminInfo�� �ν��Ͻ� adminInfo�� ����
		
		close(conn);
		
		return adminInfo;	// LoginCtrl.java�� �̵�
	}
}
