package dao;

import static db.JdbcUtil.*;
import javax.sql.*;
import java.sql.*;
import java.util.*;
import vo.*;

public class MemberDao {
	private static MemberDao memberDao;
	private static Connection conn;		// static���� �ٲ�
	private MemberDao() {}
	
	public static MemberDao getInstance() {
		if (memberDao == null) {	
			memberDao = new MemberDao();
		}
		return memberDao;
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	// 회원(가입 수정 탈퇴)
	public int memberJoin(MemberInfo memberInfo) {
		PreparedStatement pstmt = null;
		int result = 0;

		try {
			String sql = "insert into t_member_info(mi_email, mi_pwd, mi_name, mi_nick, mi_birth, "
					+ " mi_gender, mi_phone, mi_issms, mi_isemail, mi_point, mi_recommend) "
					+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberInfo.getMi_email());
			pstmt.setString(2,memberInfo.getMi_pwd());
			pstmt.setString(3,memberInfo.getMi_name());
			pstmt.setString(4,memberInfo.getMi_nick());
			pstmt.setString(5,memberInfo.getMi_birth());
			pstmt.setString(6,memberInfo.getMi_gender());
			pstmt.setString(7,memberInfo.getMi_phone());
			pstmt.setString(8,memberInfo.getMi_issms());
			pstmt.setString(9,memberInfo.getMi_isemail());
			pstmt.setInt(10, 2000);
			pstmt.setString(11,memberInfo.getMi_recommend());

			System.out.println("회원가입 = " +pstmt);

			result = pstmt.executeUpdate();

			if (result > 0) {
				sql = "insert into t_member_point (mi_email, mp_point, mp_kind, mp_content) " +
						" values (?, ?, ?, ?)";

				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, memberInfo.getMi_email());
				pstmt.setInt(2, 2000);
				pstmt.setString(3, "d");
				pstmt.setString(4, "가입 축하금");

				System.out.println("회원가입 = " + pstmt);
				result += pstmt.executeUpdate();
			}


		} catch(Exception e) {
			System.out.println("memberJoin() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int memberUpdate(MemberInfo memberInfo) {
		int result = 0;
		Statement stmt = null;

		try {
			String sql = "update t_member_info set ";

			if (!memberInfo.getMi_pwd().equals("")) {
				sql += " mi_pwd = '" + memberInfo.getMi_pwd() + "', ";
			}
			sql += " mi_nick = '"	+ memberInfo.getMi_nick()	+ "', ";
			sql += " mi_phone = '"	+ memberInfo.getMi_phone()	+ "', ";
			sql += " mi_issms = '"	+ memberInfo.getMi_issms()	+ "', ";
			sql += " mi_isemail = '"	+ memberInfo.getMi_isemail()	+ "' ";
			sql += " where mi_email = '"	+ memberInfo.getMi_email()	+ "' ";

			System.out.println("수정쿼리=" + sql);
			stmt = conn.createStatement();
			System.out.println("stmt=" + stmt);
			result = stmt.executeUpdate(sql);

		} catch(Exception e) {
			System.out.println("memberUpdate() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		System.out.println(result);

		return result + 1;	//서비스 파일에서 + 수량을 맞춰줘야 해서 이미 2개가 있고 +1개 추가요
	}

	public int memberDelete(String miid) {
		int result = 0;
		Statement stmt = null;

		try {
			String sql = "update t_member_info set mi_isactive = 'n' " +
					" where mi_email = '" + miid + "' ";
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);

		} catch(Exception e) {
			System.out.println("memberDelete() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		return result;
	}
	
	public static MemberInfo searchId(String uname, String phone) { // static���� �ٲ�
		Statement stmt = null;
		ResultSet rs = null;
		MemberInfo memberInfo = null;
		
		try {
			String sql = "select mi_email from t_member_info where mi_name = '" + uname +
						 "' and mi_phone = '" + phone + "' ";
			System.out.println(sql);
			stmt = conn.createStatement();				
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
			// rs�� ������ ��ǰ�� ���� ��� 
				memberInfo = new MemberInfo();
				memberInfo.setMi_email(rs.getString(1));
	            System.out.println(memberInfo.getMi_email());
			 }
			
		} catch(Exception e) {
			System.out.println("searchId() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		return memberInfo;

	}
	
	public static MemberInfo resetPwd(String uname, String phone) { // static���� �ٲ�
		Statement stmt = null;
		ResultSet rs = null;
		MemberInfo memberInfo = null;
		
		try {
			String sql = "select mi_email from t_member_info where mi_name = '" + uname +
						 "' and mi_phone = '" + phone + "' ";
			System.out.println(sql);
			stmt = conn.createStatement();				
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
			// rs�� ������ ��ǰ�� ���� ��� 
				memberInfo = new MemberInfo();
				memberInfo.setMi_email(rs.getString(1));
				
	            System.out.println(memberInfo.getMi_email());
			 }
			
		} catch(Exception e) {
			System.out.println("resetPwd() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		return memberInfo;

	}

	public static MemberInfo chkEmail(String email) { // static���� �ٲ�
		Statement stmt = null;
		ResultSet rs = null;
		MemberInfo memberInfo = null;
		
		try {
			String sql = "select * from t_member_info where mi_email = '" + email + "' ";
			System.out.println(sql);
			stmt = conn.createStatement();				
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
			// rs�� ������ ��ǰ�� ���� ��� 
				memberInfo = new MemberInfo();
				memberInfo.setMi_email(rs.getString(1));
	            System.out.println(memberInfo.getMi_email());
			 }
			
		} catch(Exception e) {
			System.out.println("resetPwd() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		return memberInfo;

	}
	
	public static int setPwd(MemberInfo memberInfo) { // static���� �ٲ�
		Statement stmt = null;
		int result = 0;
		
		try {
			stmt = conn.createStatement();
			String sql = "update t_member_info set mi_pwd = '" + memberInfo.getMi_pwd() + 
						 "' where mi_email = '" + memberInfo.getMi_email() + "' ";
			System.out.println(sql);
			result = stmt.executeUpdate(sql);
			
		} catch(Exception e) {
			System.out.println("resetPwd() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}
	
	public ArrayList<MemberInfo> getAddrList(String miemail) {
		ArrayList<MemberInfo> addrList = new ArrayList<MemberInfo>();
		
		Statement stmt = null;
		ResultSet rs = null;		
		
		MemberInfo memberInfo = null;
		
		try {
			String sql = "select * from t_member_addr where mi_email = '" + miemail + "' ";
			System.out.println(sql);
			
			stmt = conn.createStatement();				
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				memberInfo = new MemberInfo();

				memberInfo.setMa_idx(rs.getInt("ma_idx"));
				memberInfo.setMa_name(rs.getString("ma_name"));
				memberInfo.setMa_receiver(rs.getString("ma_receiver"));
				memberInfo.setMa_phone(rs.getString("ma_phone"));
				memberInfo.setMa_zip(rs.getString("ma_zip"));
				memberInfo.setMa_addr1(rs.getString("ma_addr1"));
				memberInfo.setMa_addr2(rs.getString("ma_addr2"));
				memberInfo.setMa_basic(rs.getString("ma_basic"));
				memberInfo.setMa_date(rs.getString("ma_date"));
				
				addrList.add(memberInfo);
				
			 }
			
		} catch(Exception e) {
			System.out.println("getAddrList() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		
		return addrList;
	}
	
	public int addrInsert(MemberInfo memberInfo) {
		int result = 0;
		
		Statement stmt = null;

		try {
			stmt = conn.createStatement();
			String sql = "";
			
			if (memberInfo.getMa_basic().equals("y")) {
				sql = "update t_member_addr set ma_basic = 'n' where mi_email = '" + memberInfo.getMi_email() + "' ";
				result = stmt.executeUpdate(sql);
				System.out.println("ù ��° sql : " + sql);
				
			} 
			
			sql = "insert into t_member_addr(mi_email, ma_name, ma_receiver, ma_phone, ma_zip, ma_addr1, ma_addr2, ma_basic) " + 
						 " values ('" + memberInfo.getMi_email() + "', '" + 
						 memberInfo.getMa_name() + "', '" + memberInfo.getMa_receiver() + "', '" + 
						 memberInfo.getMa_phone() + "', '" + memberInfo.getMa_zip() + "', '" + 
						 memberInfo.getMa_addr1() + "','" + memberInfo.getMa_addr2() + "', '" + 
						 memberInfo.getMa_basic() + "')";
			System.out.println("�� ��° sql : " + sql);
					
			result = stmt.executeUpdate(sql);

		} catch(Exception e) {
			System.out.println("AddrInsert() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}
	
	public int addrUpdate(MemberInfo memberInfo) {
		Statement stmt = null;
		
		int result = 0;

		try {
			stmt = conn.createStatement();
			String sql = "";
			
			if (memberInfo.getMa_basic().equals("y")) {
				sql = "update t_member_addr set ma_basic = 'n' where mi_email = '" + memberInfo.getMi_email() + "' ";
				result = stmt.executeUpdate(sql);
				System.out.println("ù ��° sql : " + sql);
				
			} 
			sql = "update t_member_addr set ma_name = '" + memberInfo.getMa_name() + "', ma_receiver = '" +
				  memberInfo.getMa_receiver() + "', ma_phone = '" + memberInfo.getMa_phone() + "', ma_zip = '" +
				  memberInfo.getMa_zip() + "', ma_addr1 = '" + memberInfo.getMa_addr1() + "', ma_addr2 = '" +
				  memberInfo.getMa_addr2() + "', ma_basic = '" + memberInfo.getMa_basic() + "' " + 
				  " where ma_idx = '" + memberInfo.getMa_idx() + "' and mi_email = '" + memberInfo.getMi_email() + "' ";
			System.out.println("�� ��° sql : " + sql);
						
			result = stmt.executeUpdate(sql);

		} catch(Exception e) {
			System.out.println("cartUpdate() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}
	
	public int addrDelete(int maidx, String miemail) {
		Statement stmt = null;
		
		int result = 0;

		try {
			stmt = conn.createStatement();
			
			String sql = "delete from t_member_addr  where ma_idx = '" + maidx + "' and mi_email = '" + miemail + "' ";
			System.out.println("�� ��° sql : " + sql);
						
			result = stmt.executeUpdate(sql);

		} catch(Exception e) {
			System.out.println("AddrDelete() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
		
	}
	
	public MemberInfo getAddrInfo(int maidx) {
		Statement stmt = null;
		ResultSet rs = null;
		MemberInfo memberInfo = null;
		
		try {
			String sql = "select * from t_member_addr where ma_idx = '" + maidx + "' ";	
			
			System.out.println(sql);
			stmt = conn.createStatement();				
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				memberInfo = new MemberInfo();

				memberInfo.setMa_idx(rs.getInt("ma_idx"));
				memberInfo.setMi_email(rs.getString("mi_email"));
				memberInfo.setMa_name(rs.getString("ma_name"));
				memberInfo.setMa_receiver(rs.getString("ma_receiver"));
				memberInfo.setMa_phone(rs.getString("ma_phone"));
				memberInfo.setMa_zip(rs.getString("ma_zip"));
				memberInfo.setMa_addr1(rs.getString("ma_addr1"));
				memberInfo.setMa_addr2(rs.getString("ma_addr2"));
				memberInfo.setMa_basic(rs.getString("ma_basic"));
				memberInfo.setMa_date(rs.getString("ma_date"));
			        
			 }
			
		} catch(Exception e) {
			System.out.println("getAddrInfo() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
		return memberInfo;

	}
	

	   public int getPointCount(String loginEmail) {
	      Statement stmt = null;   
	      ResultSet rs = null;   
	      int rcnt = 0;
	      
	      try {
	         String sql = "select count(*) from t_member_point where mi_email = '" + loginEmail + "'";
	         stmt = conn.createStatement();
	         rs = stmt.executeQuery(sql);
	         if (rs.next()) {
	            rcnt = rs.getInt(1);
	         }
	         
	      } catch(Exception e) {
	         System.out.println("getPointCount() �޼ҵ� ����");
	         e.printStackTrace();
	      } finally {
	         close(rs);
	         close(stmt);
	      }
	      return rcnt;
	   }

	   public int getAvailablePoint(String loginEmail) {
	      Statement stmt = null;   
	      ResultSet rs = null;   
	      int save = 0, use = 0, availablePoint = 0;

	      try {   // ��밡�� ����Ʈ = ��������Ʈ - �������Ʈ
	         String sql = "select sum(mp_point) from t_member_point where mi_email = 'test1@gmail.com' and mp_kind <> 'u'";
	         stmt = conn.createStatement();
	         rs = stmt.executeQuery(sql);
	         rs.next();
	         save = rs.getInt(1);
	         
	         sql = "select sum(mp_point) from t_member_point where mi_email = 'test1@gmail.com' and mp_kind = 'u'";
	         rs = stmt.executeQuery(sql);
	         rs.next();
	         use = rs.getInt(1);
	         
	         availablePoint = save - use;
	         
	      } catch(Exception e) {
	         System.out.println("getAvailablePoint() �޼ҵ� ����");
	         e.printStackTrace();
	      } finally {
	         close(stmt);
	      }
	      return availablePoint;
	   }
	   
	   public ArrayList<PointInfo> getPointList(String loginEmail, int cpage, int psize) {
	      ArrayList<PointInfo> pointList = new ArrayList<PointInfo>();
	      
	      Statement stmt = null;
	      ResultSet rs = null;
	      PointInfo pointInfo = null;
	      
	      int snum = (cpage - 1) * psize;
	      
	      try {
	         String sql = "select * from t_member_point where mi_email = '" + loginEmail + "'  order by mp_date desc " + 
	               "limit " + snum + ", " + psize;
	         System.out.println(sql);
	         
	         stmt = conn.createStatement();
	         rs = stmt.executeQuery(sql);
	         
	         while (rs.next()) {
	            pointInfo = new PointInfo();
	            
	            pointInfo.setMp_idx(rs.getInt("mp_idx"));
	            pointInfo.setMi_email(rs.getString("mi_email"));
	            pointInfo.setMp_point(rs.getInt("mp_point"));
	            pointInfo.setMp_kind(rs.getString("mp_kind"));
	            pointInfo.setMp_linkidx(rs.getString("mp_linkidx"));
	            pointInfo.setMp_content(rs.getString("mp_content"));
	            pointInfo.setMp_info(rs.getString("mp_info"));
	            pointInfo.setMp_date(rs.getString("mp_date"));
	            pointInfo.setAi_idx(rs.getInt("ai_idx"));
	            
	            pointList.add(pointInfo);
	         }
	         
	      } catch(Exception e) {
	         System.out.println("getPointList() �޼ҵ� ����");
	         e.printStackTrace();
	      } finally {
	         close(rs);
	         close(stmt);
	      }
	      
	      return pointList;
	   }
	   
	   public FollowInfo getFollowInfo(String loginEmail) {
	      FollowInfo followInfo = null;
	      
	      Statement stmt = null;
	      ResultSet rs = null;
	      
	      try {
	         String sql = "select mi_follower, mi_following from t_member_info where mi_email = '" + loginEmail + "'";
	         System.out.println(sql);
	         
	         stmt = conn.createStatement();
	         rs = stmt.executeQuery(sql);
	         
	         while (rs.next()) {
	            followInfo = new FollowInfo();
	            
	            followInfo.setMi_follower(rs.getInt("mi_follower"));
	            followInfo.setMi_following(rs.getInt("mi_following"));      
	         }
	         
	      } catch(Exception e) {
	         System.out.println("getFollowInfo() �޼ҵ� ����");
	         e.printStackTrace();
	      } finally {
	         close(rs);
	         close(stmt);
	      }
	      
	      return followInfo;
	   }
	   
	   public ArrayList<FollowInfo> getFollowerList(String loginEmail) {
	      ArrayList<FollowInfo> followerList = new ArrayList<FollowInfo>();
	      
	      Statement stmt = null;
	      ResultSet rs = null;
	      FollowInfo followInfo = null;
	      
	      try {
	         String sql = "select a.*, b.mi_img, b.mi_nick, b.mi_follower, b.mi_following from t_member_follower a, t_member_info b " + 
	            "where a.mfr_email = b.mi_email and a.mi_email = '" + loginEmail + "' order by mfr_date desc";
	         System.out.println(sql);
	         
	         stmt = conn.createStatement();
	         rs = stmt.executeQuery(sql);
	         
	         while (rs.next()) {
	            followInfo = new FollowInfo();
	            
	            followInfo.setMfr_idx(rs.getInt("mfr_idx"));
	            followInfo.setMi_email(loginEmail);
	            followInfo.setMfr_email(rs.getString("mfr_email"));
	            followInfo.setMfr_date(rs.getString("mfr_date"));
	            followInfo.setMi_nick(rs.getString("mi_nick"));
	            followInfo.setMi_img(rs.getString("mi_img"));
	            followInfo.setMi_follower(rs.getInt("mi_follower"));
	            followInfo.setMi_following(rs.getInt("mi_following"));
	            
	            followerList.add(followInfo);
	         }
	         
	      } catch(Exception e) {
	         System.out.println("getFollowerList() �޼ҵ� ����");
	         e.printStackTrace();
	      } finally {
	         close(rs);
	         close(stmt);
	      }
	      
	      return followerList;
	   }

	   public ArrayList<FollowInfo> getFollowingList(String loginEmail) {
	      ArrayList<FollowInfo> followingList = new ArrayList<FollowInfo>();
	      
	      Statement stmt = null;
	      ResultSet rs = null;
	      FollowInfo followInfo = null;
	      
	      try {
	         String sql = "select a.*, b.mi_img, b.mi_nick, b.mi_follower, b.mi_following from t_member_following a, t_member_info b " + 
	            "where a.mfg_email = b.mi_email and a.mi_email = '" + loginEmail + "' order by mfg_date desc";
	         System.out.println(sql);
	         
	         stmt = conn.createStatement();
	         rs = stmt.executeQuery(sql);
	         
	         while (rs.next()) {
	            followInfo = new FollowInfo();
	            
	            followInfo.setMfg_idx(rs.getInt("mfg_idx"));
	            followInfo.setMi_email(loginEmail);
	            followInfo.setMfg_email(rs.getString("mfg_email"));
	            followInfo.setMfg_date(rs.getString("mfg_date"));
	            followInfo.setMi_nick(rs.getString("mi_nick"));
	            followInfo.setMi_img(rs.getString("mi_img"));
	            followInfo.setMi_follower(rs.getInt("mi_follower"));
	            followInfo.setMi_following(rs.getInt("mi_following"));
	            
	            followingList.add(followInfo);
	         }
	         
	      } catch(Exception e) {
	         System.out.println("getFollowingList() �޼ҵ� ����");
	         e.printStackTrace();
	      } finally {
	         close(rs);
	         close(stmt);
	      }
	      
	      return followingList;
	   }
}
