package admin.dao;

import static db.JdbcUtil.*;
import java.sql.*;
import vo.*;

public class AdminLoginDao {
// 로그인 관련 쿼리를 생성하여 실행시키는 클래스
	private static AdminLoginDao adminLoginDao;
	// 인스턴스 멤버가 아닌 클래스 멤버로 adminLoginDao 인스턴스를 선언함으로써 여러 개가 아닌 하나만 존재하게 됨
	private Connection conn;
	// 클래스 전체에서 Connection 인스턴스 conn을 사용할 수 있게 됨
	private AdminLoginDao() {}
	// 외부에서 직접적으로 인스턴스를 생성하는 것(new 키워드 사용)을 막기 위해 private으로 기본 생성자를 선언
	
	public static AdminLoginDao getInstance() {
	// 인스턴스를 생성해주는 메소드로 하나의 인스턴스만 생성시킴(singleton 방식)
	// DB작업을 많이 하는 클래스 특성상 여러 개의 인스턴스가 생성되면 
	// 그만큼 많은 수의 DB연결(Connection)이 생기므로 전체적인 속도 저하의 우려가 있어 싱글톤 방식을 사용
		if (adminLoginDao == null) {
		// 멤버로 선언된 AdminLoginDao 형 인스턴스 adminLoginDao가 null이면(인스턴스가 생성되지 않았으면)
			adminLoginDao = new AdminLoginDao();
			// 생성된 adminLoginDao 인스턴스가 없으므로 새롭게 하나 생성함
		}
		return adminLoginDao;
	}
	public void setConnection(Connection conn) {
	// AdminLoginSvc 클래스에서 보낸 Connection객체를 받아 멤버인 conn에 저장
	// 외부에서 Connection 객체를 받는 이유는 DB작업이 여러 번일 경우 
	// Connection 객체를 여러 번 생성하는 것을 막기 위해 멤버로 지정하여 사용
		this.conn = conn;
	}
	public AdminInfo getLoginAdmin(String uid, String pwd) {	// 리턴타입이 MemberInfo형 인스턴스
	// 사용자가 입력한 아이디와 비밀번호를 이용하여 로그인 후 필요한 사용자 정보를 추출하여 MemberInfo형으로 리턴하는 메소드
		AdminInfo adminInfo = null;	// 로그인 후 사용자 정보를 저장할 인스턴스
		Statement stmt = null;
		ResultSet rs = null;
		try {
		// DB관련 작업에서는 대부분의 메소드가 'throws SQLException'으로 선언되어 있기 때문에 예외처리를 해야 함 
			stmt = conn.createStatement();
			String sql = "select * from t_admin_info where ai_id = '" + uid + "' and ai_pwd = '" + pwd + "' and ai_isrun = 'y'";
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {	// 로그인 성공 시
				adminInfo = new AdminInfo();	// 로그인 성공 시에만 생성되는 인스턴스
				// 로그인 실패 시에는 adminInfo가 비어있는 상태(null)로 리턴되게 함
				adminInfo.setAi_id(uid);
//	            adminInfo.setAi_pwd(pwd);
	            adminInfo.setAi_idx(rs.getInt("ai_idx"));
	            adminInfo.setAi_name(rs.getString("ai_name"));
	            adminInfo.setAi_pms(rs.getString("ai_pms"));
//	            adminInfo.setAi_isrun(rs.getString("ai_isrun"));
	            adminInfo.setAi_regdate(rs.getString("ai_regdate"));
				// AdminInfo클래스의 인스턴스 adminInfo에 회원정보들과 기본주소를 저장
			}
			
		} catch(Exception e) {
			System.out.println("getLoginAdmin() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);	// conn 안닫고 AdminLoginSvc.java에서 닫음
		}
		
		return adminInfo;	// AdminLoginSvc.java로 리턴
	}
}
