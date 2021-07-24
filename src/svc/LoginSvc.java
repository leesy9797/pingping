package svc;

import static db.JdbcUtil.*;
// db 패키지 내의 JdbcUtil 클래스가 가진 모든 멤버들을 자유롭게 사용하겠다는 의미
import java.sql.*;
import dao.*;
import vo.*;

public class LoginSvc {
	public MemberInfo getMemberInfo(String email, String pwd) {
		LoginDao loginDao = LoginDao.getInstance();
		
		Connection conn = getConnection();
		loginDao.setConnection(conn);
		
		MemberInfo memberInfo = loginDao.getMemberInfo(email, pwd);

		System.out.println("아아, 여기는 LoginSvc");
		
		commit(conn);
		
		close(conn);
		
		return memberInfo;
	}
}
