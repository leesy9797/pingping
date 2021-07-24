package svc;

import static db.JdbcUtil.*;
// db ��Ű�� ���� JdbcUtil Ŭ������ ���� ��� ������� �����Ӱ� ����ϰڴٴ� �ǹ�
import java.sql.*;
import dao.*;
import vo.*;

public class LoginSvc {
	public MemberInfo getMemberInfo(String email, String pwd) {
		LoginDao loginDao = LoginDao.getInstance();
		
		Connection conn = getConnection();
		loginDao.setConnection(conn);
		
		MemberInfo memberInfo = loginDao.getMemberInfo(email, pwd);

		System.out.println("�ƾ�, ����� LoginSvc");
		
		commit(conn);
		
		close(conn);
		
		return memberInfo;
	}
}
