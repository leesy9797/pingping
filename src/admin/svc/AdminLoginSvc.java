package admin.svc;

import static db.JdbcUtil.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class AdminLoginSvc {
// 로그인에 대한 처리작업을 DB를 제외한 모든 처리를 하는 클래스
	public AdminInfo getLoginAdmin(String uid, String pwd) {
	// 사용자가 입력한 아이디와 비밀번호를 매개변수로 받아 로그인을 처리하는 메소드로 AdminInfo형 인스턴스로 결과를 리턴함
		AdminLoginDao adminLoginDao = AdminLoginDao.getInstance();
		// 로그인의 DB작업을 위해 AdminLoginDao형 인스턴스 adminLoginDao를 생성
		Connection conn = getConnection();
		// JdbcUtil 클래스의 getConnection() 메소드로 Connection 객체 conn 생성
		adminLoginDao.setConnection(conn);
		// adminLoginDao인스턴스에서 사용할 Connection객체 전달

		AdminInfo adminInfo = adminLoginDao.getLoginAdmin(uid, pwd);
		// adminLoginDao 인스턴스의 getLoginAdmin() 메소드를 실행시키고
		// 그 결과를 AdminInfo형 인스턴스 adminInfo에 저장
		
		close(conn);
		
		return adminInfo;	// LoginCtrl.java로 이동
	}
}
