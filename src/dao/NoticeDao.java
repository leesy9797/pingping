package dao;

import static db.JdbcUtil.*;
import javax.sql.*;
import java.sql.*;
import java.util.*;
import vo.*;


public class NoticeDao {
// 공지사항 관련 DB작업을 실제로 처리하는 메소드들을 담은 클래스
	private static NoticeDao noticeDao;
	// NoticeDao 형 인스턴스를 하나만 생성케 하기 위해 static으로 선언
	private Connection conn;
	private NoticeDao() {}
	// 기본 생성자로 외부에서 함부로 생성하지 못하게 private으로 선언됨
	
	public static NoticeDao getInstance() {
	// NoticeDao의 인스턴스를 생성하여 리턴하는 메소드로 외부에서 접글할 수 있도록 static으로 선언됨
		if (noticeDao == null) {
		// 현재 생성된 인스턴스가 없으면 새롭게 인스턴스를 생성
			noticeDao = new NoticeDao();
		}
		return noticeDao;
	}
	public void setConnection(Connection conn) {
	// 현 클래스에서 DB작업을 위해 Connection객체를 받아오는 메소드	
		this.conn = conn;
		// 멤버변수로 Connection객체를 지정하면 클래스 전체에서 자유롭게 사용 가능
		
	}
	
	public int getArticleCount(String where ) {
	// 게시글의 총 개수를 리턴하는 메소드 (검색조건이 있을 경우 검색된 결과의 개수를 리턴)
		Statement stmt = null;	// 쿼리를 DB에 보내는 Statement 선언
		ResultSet rs = null;	// select쿼리의 결과를 받아오기 선언 \ㅇ
		
		int rcnt = 0;	// 게시글 개수를 저장할 변수
		
		try {
			String sql = "select count(*) from t_brd_notice" + where;
			// 게시글의 총 개수를 가져올  쿼리 작성
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) {	
				rcnt = rs.getInt(1);
				// 게시글의 개수를 rcnt에 저장(게시글이 없으면 0이 저장됨)
			}
		} catch(Exception e) {
			System.out.println("getArticleCount() 메소드 오류");
			e.printStackTrace();
		} finally {
			close(rs); close(stmt);
		}
		
		return rcnt;
	}
	
	public ArrayList<NoticeInfo> getArticleList(String where, int cpage, int psize ) {
		// 게시글의 목록을 ArrayList<NoticeInfo>형으로 리턴하는 메소드 
			Statement stmt = null;
			ResultSet rs = null;
			ArrayList<NoticeInfo> articleList = new ArrayList<NoticeInfo>();
			// 공지사항 게시글의 목록을 저장할 ArrayList 인스턴스 
			NoticeInfo noticeInfo = null;
			// articleList에 담을 데이터인 NoticeInfo 형 인스턴스를 선언 
			int snum = (cpage - 1) * psize;
			// 쿼리의 limit 명령에서 데이터를 가져올 시작 인덱스 번호
			try {
				String sql = "select * from t_brd_notice " + where +
						" order by bn_idx desc limit " + snum + ", " + psize;
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
				// rs에 보여줄 게시글이 있을 경우 
					noticeInfo = new NoticeInfo();
					// articleList에 저장할 한 게시글의 정보를 담는 인스턴스 생성
					
					noticeInfo.setBn_idx(rs.getInt("bn_idx"));
					noticeInfo.setBn_title(rs.getString("bn_title"));
					noticeInfo.setBn_content(rs.getString("bn_content"));
					noticeInfo.setBn_read(rs.getInt("bn_read"));
					noticeInfo.setBn_isview(rs.getString("bn_isview"));
					noticeInfo.setBn_date(rs.getString("bn_date"));
					noticeInfo.setAi_idx(rs.getInt("ai_idx"));
					noticeInfo.setLast_date(rs.getString("last_date"));
					noticeInfo.setLast_admin(rs.getString("last_admin"));
					// 받아온 레코드들로 게시글 정보를 저장
					
					articleList.add(noticeInfo);
					// 한 게시글의 정보를 담은 noticeInfo 인스턴스를 articleList에 저장
				}
			} catch(Exception e) {
				System.out.println("getArticleList() 메소드 오류");
				e.printStackTrace();
			} finally {
				close(rs); close(stmt);
			}
			
			return articleList;
		}
	
		public int readCountUp(int idx) {
		// 특정 게시글의 조회수를 증가시키는 메소드
			Statement stmt = null;
			int result = 0;
			
			try {
				String sql = "update t_brd_notice set bn_read = bn_read + 1 where bn_idx = " + idx;
				stmt = conn.createStatement();
				result = stmt.executeUpdate(sql);
				
			} catch (Exception e){
				System.out.println("readCountUp() 메소드 오류");
				e.printStackTrace();
			} finally {
				close(stmt);
			}
			return result;
		}
		
		public NoticeInfo getArticle(int idx) {
		// 사용자가 선택한 게시글의 정보를 NoticeInfo 형 인스턴스로 리턴하는 메소드 
			Statement stmt = null;
			ResultSet rs = null;
			NoticeInfo article = null;
			
			try {
				String sql = "select * from t_brd_notice " + " where bn_idx = " + idx;
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				if (rs.next()) {
				// rs에 보여줄 게시글이 있을 경우 
					article = new NoticeInfo();
										
					article.setBn_idx(idx);
					article.setBn_title(rs.getString("bn_title"));
					article.setBn_content(rs.getString("bn_content"));
					article.setBn_read(rs.getInt("bn_read"));
					article.setBn_isview(rs.getString("bn_isview"));
					article.setBn_date(rs.getString("bn_date"));
					article.setAi_idx(rs.getInt("ai_idx"));
					// 받아온 레코드로 게시글 정보를 저장
					
				}
			} catch(Exception e) {
				System.out.println("getArticleList() 메소드 오류");
				e.printStackTrace();
			} finally {
				close(rs); close(stmt);
			}
			
			return article;
	}
	
}
