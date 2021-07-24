package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class FollowListSvc {
	public FollowInfo getFollowInfo(String loginEmail) {
	// �ȷο�/�ȷ��� ���� �����ϴ� �޼ҵ�
		FollowInfo followInfo = new FollowInfo();
		Connection conn = getConnection();
		MemberDao memberDao = MemberDao.getInstance();
		memberDao.setConnection(conn);
		followInfo = memberDao.getFollowInfo(loginEmail);
		
		close(conn);
		
		return followInfo;
	}
	
	public ArrayList<FollowInfo> getFollowerList(String loginEmail) {
	// �˻��� �ȷο� ����� ArrayList<FollowInfo>�� �ν��Ͻ��� �����ϴ� �޼ҵ�
		ArrayList<FollowInfo> followerList = new ArrayList<FollowInfo>();
		Connection conn = getConnection();
		MemberDao memberDao = MemberDao.getInstance();
		memberDao.setConnection(conn);
		followerList = memberDao.getFollowerList(loginEmail);
		
		close(conn);
		
		return followerList;
	}

	public ArrayList<FollowInfo> getFollowingList(String loginEmail) {
	// �˻��� �ȷ��� ����� ArrayList<FollowInfo>�� �ν��Ͻ��� �����ϴ� �޼ҵ�
		ArrayList<FollowInfo> followingList = new ArrayList<FollowInfo>();
		Connection conn = getConnection();
		MemberDao memberDao = MemberDao.getInstance();
		memberDao.setConnection(conn);
		followingList = memberDao.getFollowingList(loginEmail);
		
		close(conn);
		
		return followingList;
	}
}
