package dao;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import vo.*;

public class LoginDao {
   private static LoginDao loginDao;
   private Connection conn;
   private LoginDao() {}
   
   public static LoginDao getInstance() {
      if (loginDao == null) {
         loginDao = new LoginDao();
      }
      return loginDao;
   }
   
   public void setConnection(Connection conn) {
      this.conn = conn;
   }
   
   
   public MemberInfo getMemberInfo(String email, String pwd) {
      MemberInfo memberInfo = null;   
      Statement stmt = null;
      ResultSet rs = null;
      int result = 0;
      
      try {
         stmt = conn.createStatement();
         String sql = "select a.*, b.* " + 
               " from t_member_info a inner join t_member_addr b on a.mi_email = b.mi_email " + 
               " where a.mi_email = '" + email + "' and a.mi_pwd = '" + pwd + 
               "' and a.mi_isactive = 'y' and b.ma_basic = 'y'";
         System.out.println(sql);
         rs = stmt.executeQuery(sql);
         if (rs.next()) {   // 로그인 성공시            
            memberInfo = new MemberInfo();   
            // 로그인 실패시에는 loginMember가 비어있는 상태(null)로 리턴되게 함
            
            memberInfo.setMi_email(email);
            memberInfo.setMi_pwd(pwd);
            memberInfo.setMi_name(rs.getString("mi_name"));
            memberInfo.setMi_nick(rs.getString("mi_nick"));
            memberInfo.setMi_phone(rs.getString("mi_phone"));
            memberInfo.setMi_birth(rs.getString("mi_birth"));
            memberInfo.setMi_gender(rs.getString("mi_gender"));
            memberInfo.setMi_introduce(rs.getString("mi_introduce"));
            memberInfo.setMi_issms(rs.getString("mi_issms"));
            memberInfo.setMi_isemail(rs.getString("mi_isemail"));
            memberInfo.setMi_recommend(rs.getString("mi_recommend"));
            memberInfo.setMi_joindate(rs.getString("mi_joindate"));
            memberInfo.setMi_isactive(rs.getString("mi_isactive"));
            //memberInfo.setMi_lastlogin(rs.getString("mi_lastlogin"));
            memberInfo.setMi_outdate(rs.getString("mi_outdate"));
            memberInfo.setMa_name(rs.getString("ma_name"));
            memberInfo.setMa_receiver(rs.getString("ma_receiver"));
            memberInfo.setMa_phone(rs.getString("ma_phone"));
            memberInfo.setMa_zip(rs.getString("ma_zip"));
            memberInfo.setMa_addr1(rs.getString("ma_addr1"));
            memberInfo.setMa_addr2(rs.getString("ma_addr2"));
            memberInfo.setMa_basic(rs.getString("ma_basic"));
            memberInfo.setMi_following(rs.getInt("mi_following"));
            memberInfo.setMi_follower(rs.getInt("mi_follower"));
            memberInfo.setMi_point(rs.getInt("mi_point"));
            memberInfo.setMa_idx(rs.getInt("ma_idx"));   
            memberInfo.setMi_img(rs.getString("mi_img"));   
            // MemberInfo 클래스의 인스턴스 loginMember에 회원정보들과 기본주소를 저장
            
            Calendar today = Calendar.getInstance( );
            int year = today.get(Calendar.YEAR);
            int month = today.get(Calendar.MONTH) + 1;
            int day = today.get(Calendar.DATE);
            int hour = today.get(Calendar.HOUR_OF_DAY);
            int minute = today.get(Calendar.MINUTE);
            int second = today.get(Calendar.SECOND);
            String date = year + "-" + (month < 10 ? "0" + month : month) + "-" + (day < 10 ? "0" + day : day) + "-" + 
                      (hour < 10 ? "0" + hour : hour) + "-" + (minute < 10 ? "0" + minute : minute) + "-" + (second < 10 ? "0" + second : second); 
            sql = "update t_member_info set mi_lastlogin = '" + date + "' where mi_email = '" + email + "' ";
            System.out.println(sql);
            result = stmt.executeUpdate(sql);
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
}