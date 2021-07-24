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
	// ------> 싱글톤 방식을 위함
	
	public int getMemberCount(String where) {
	// 검색된 회원의 총 개수를 리턴하는 메소드(검색조건이 있을 경우 검색된 결과의 개수를 리턴)
		Statement stmt = null;	
		ResultSet rs = null;	
		int rcnt = 0;	// 회원 인원수를 저장할 변수
		
		try {
			String sql = "select count(*) from t_member_info " + where;
			System.out.println(sql);
			// 회원 총 인원수를 가져올 쿼리 작성
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {	
				rcnt = rs.getInt(1);
			}	// 게시글의 개수를 rcnt에 저장(게시글이 없으면 0이 저장됨)
			// count 함수는 결과값이 없을 수가 없으므로(값이 없으면 0이니까) if문을 사용하지 않아도 됨
			// 그러나 rs.next()는 반드시 해주어야 하므로 눈에 익숙한 형태로 보이기 위해 if문을 사용
			
		} catch(Exception e) {
			System.out.println("getMemberCount() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		return rcnt;
	}
	
	public ArrayList<MemberInfo> getMemberList(String where, String orderBy, int cpage, int psize) {
	// 검색된 회원 목록을 ArrayList<MemberInfo>형 인스턴스로 리턴하는 메소드
		ArrayList<MemberInfo> MemberList = new ArrayList<MemberInfo>();
		// 회원 목록을 저장할 ArrayList로 오직 MemberInfo형 인스턴스만 저장됨
		
		Statement stmt = null;
		ResultSet rs = null;		
		
		MemberInfo memberInfo = null;
		// MemberList에 담을 데이터를 담을 MemberInfo형 인스턴스를 선언
		
		int snum = (cpage - 1) * psize;
		// 쿼리의 limit 명령에서 데이터를 가져올 시작 인덱스 번호
		
		try {
			String sql = "select * from t_member_info " + where + orderBy + " limit " + snum + ", " + psize;
			
			stmt = conn.createStatement();				
			rs = stmt.executeQuery(sql);
			System.out.println(sql);
			while (rs.next()) {
			// rs에 보여줄 회원이 있을 경우 
				memberInfo = new MemberInfo();
				// MemberList에 저장할 한 상품의 정보를 담을 인스턴스 생성

				memberInfo.setMi_email(rs.getString("mi_email"));
				memberInfo.setMi_nick(rs.getString("mi_nick"));
				memberInfo.setMi_phone(rs.getString("mi_phone"));
				memberInfo.setMi_birth(rs.getString("mi_birth"));
				memberInfo.setMi_gender(rs.getString("mi_gender"));
				memberInfo.setMi_joindate(rs.getString("mi_joindate"));
				memberInfo.setMi_isactive(rs.getString("mi_isactive"));
				memberInfo.setMi_lastlogin(rs.getString("mi_lastlogin"));
				memberInfo.setMi_memo(rs.getString("mi_memo"));
	            // 받아온 레코드들로 상품 정보를 저장
				
				MemberList.add(memberInfo);
				// 한 게시글의 정보를 담은 MemberInfo 인스턴스를 MemberList에 저장
			 }
			
		} catch(Exception e) {
			System.out.println("getMemberList() 메소드 오류");
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
				// 구매횟수?
	            
			 }
			
		} catch(Exception e) {
			System.out.println("getMemberInfo() 메소드 오류");
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
			System.out.println("updateMemo() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}
}
