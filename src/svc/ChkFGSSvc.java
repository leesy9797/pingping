package svc;

import static db.JdbcUtil.*;
import java.sql.*;
import java.util.*;
import dao.*;
import vo.*;

public class ChkFGSSvc {
	public ArrayList<FollowInfo> getFollowList(String loginEmail) {
	// 검색된 팔로잉 목록을 ArrayList<FollowInfo>형 인스턴스로 리턴하는 메소드
		ArrayList<FollowInfo> followList = new ArrayList<FollowInfo>();
		Connection conn = getConnection();	
		ChkFGSDao chkFGSDao = ChkFGSDao.getInstance();
		chkFGSDao.setConnection(conn);
		followList = chkFGSDao.getFollowList(loginEmail);

		System.out.println("아아, 여기는 ChkFGSSvc");
		
		close(conn);
		
		return followList;
	}
	
	public ArrayList<ScrapInfo> getScrapList(String kind, String loginEmail) {
	// 검색된 스크랩 목록을 ArrayList<ScrapInfo>형 인스턴스로 리턴하는 메소드
		ArrayList<ScrapInfo> scrapList = new ArrayList<ScrapInfo>();
		Connection conn = getConnection();
		ChkFGSDao chkFGSDao = ChkFGSDao.getInstance();
		chkFGSDao.setConnection(conn);
		scrapList = chkFGSDao.getScrapList(kind, loginEmail);
		
		System.out.println("아아, 여기는 ChkFGSSvc");
		
		close(conn);
		
		return scrapList;
	}

	public ArrayList<GoodInfo> getGoodList(String kind, String loginEmail) {
	// 검색된 스크랩 목록을 ArrayList<ScrapInfo>형 인스턴스로 리턴하는 메소드
		ArrayList<GoodInfo> goodList = new ArrayList<GoodInfo>();
		Connection conn = getConnection();
		ChkFGSDao chkFGSDao = ChkFGSDao.getInstance();
		chkFGSDao.setConnection(conn);
		goodList = chkFGSDao.getGoodList(kind, loginEmail);
		
		System.out.println("아아, 여기는 ChkFGSSvc");
		
		close(conn);
		
		return goodList;
	}
	
	// 팔로우
	public int doFollow(String ciemail, String miemail) {
		int result = 0;
		ChkFGSDao chkFGSDao = ChkFGSDao.getInstance();
		Connection conn = getConnection();
		chkFGSDao.setConnection(conn);
		
		result = chkFGSDao.doFollow(ciemail, miemail);

		if (result == 4)	commit(conn);	
		else				rollback(conn);
		close(conn);

		System.out.println("아아, 여기는 ChkFGSSvc");
		
		return result;	
	}
	
	// 언팔로우
	public int unFollow(String ciemail, String miemail) {
		int result = 0;
		ChkFGSDao chkFGSDao = ChkFGSDao.getInstance();
		Connection conn = getConnection();
		chkFGSDao.setConnection(conn);
		
		result = chkFGSDao.unFollow(ciemail, miemail);

		if (result == 4)	commit(conn);	
		else				rollback(conn);
		close(conn);

		System.out.println("아아, 여기는 ChkFGSSvc");
		
		return result;	
	}
	
	// 좋아요
	public int doGood(String kind, String linkidx, String miemail) {
		int result = 0;
		ChkFGSDao chkFGSDao = ChkFGSDao.getInstance();
		Connection conn = getConnection();
		chkFGSDao.setConnection(conn);
		
		result = chkFGSDao.doGood(kind, linkidx, miemail);
		
		if (result == 2)	commit(conn);
		else				rollback(conn);
		close(conn);
		
		System.out.println("아아, 여기는 ChkFGSSvc");
		
		return result;	
	}
	
	// 좋아요 취소
	public int unGood(String kind, String linkidx, String miemail) {
		int result = 0;
		ChkFGSDao chkFGSDao = ChkFGSDao.getInstance();
		Connection conn = getConnection();
		chkFGSDao.setConnection(conn);
		
		result = chkFGSDao.unGood(kind, linkidx, miemail);
		
		if (result == 2)	commit(conn);
		else				rollback(conn);
		close(conn);

		System.out.println("아아, 여기는 ChkFGSSvc");
		
		return result;	
	}
	
	
	// 스크랩
	public int doScrap(String kind, String linkidx, String miemail) {
		int result = 0;
		ChkFGSDao chkFGSDao = ChkFGSDao.getInstance();
		Connection conn = getConnection();
		chkFGSDao.setConnection(conn);
		
		result = chkFGSDao.doScrap(kind, linkidx, miemail);
		
		if (result == 2)	commit(conn);
		else				rollback(conn);
		close(conn);

		System.out.println("아아, 여기는 ChkFGSSvc");
		
		return result;	
	}
	
	// 스크랩 취소
	public int unScrap(String kind, String linkidx, String miemail) {
		int result = 0;
		ChkFGSDao chkFGSDao = ChkFGSDao.getInstance();
		Connection conn = getConnection();
		chkFGSDao.setConnection(conn);
		
		result = chkFGSDao.unScrap(kind, linkidx, miemail);
		
		if (result == 2)	commit(conn);
		else				rollback(conn);
		close(conn);
		
		System.out.println("아아, 여기는 ChkFGSSvc");
		
		return result;	
	}
}
