package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class FollowListSvc {
	public FollowInfo getFollowInfo(String loginEmail) {
	// 팔로우/팔로잉 수를 리턴하는 메소드
		FollowInfo followInfo = new FollowInfo();
		Connection conn = getConnection();
		MemberDao memberDao = MemberDao.getInstance();
		memberDao.setConnection(conn);
		followInfo = memberDao.getFollowInfo(loginEmail);
		
		close(conn);
		
		return followInfo;
	}
	
	public ArrayList<FollowInfo> getFollowerList(String loginEmail) {
	// 검색된 팔로워 목록을 ArrayList<FollowInfo>형 인스턴스로 리턴하는 메소드
		ArrayList<FollowInfo> followerList = new ArrayList<FollowInfo>();
		Connection conn = getConnection();
		MemberDao memberDao = MemberDao.getInstance();
		memberDao.setConnection(conn);
		followerList = memberDao.getFollowerList(loginEmail);
		
		close(conn);
		
		return followerList;
	}

	public ArrayList<FollowInfo> getFollowingList(String loginEmail) {
	// 검색된 팔로잉 목록을 ArrayList<FollowInfo>형 인스턴스로 리턴하는 메소드
		ArrayList<FollowInfo> followingList = new ArrayList<FollowInfo>();
		Connection conn = getConnection();
		MemberDao memberDao = MemberDao.getInstance();
		memberDao.setConnection(conn);
		followingList = memberDao.getFollowingList(loginEmail);
		
		close(conn);
		
		return followingList;
	}
}
