package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;


public class NoticeListSvc {
// �Խñ� ��� ������ ����Ͻ� ������ ó���ϴ� Ŭ����(��ü �Խñ� ������ Ư�� �������� ����� ����)
	public int getArticleCount(String where) {
	// ��Ͽ��� ������ ��ü �Խñ��� ������ �����ϴ� �޼ҵ�
		int rcnt = 0; // ��ü �Խñ� ������ ������ ����
		Connection conn = getConnection();	// DB�� ����
		NoticeDao noticeDao = NoticeDao.getInstance();
		// NoticeDao�� �ν��Ͻ��� getInstance()�޼ҵ带 ���� ����
		noticeDao.setConnection(conn);
		// NoticeDao�� �ν��Ͻ� noticeDao�� Connection��ü�� ����
		rcnt = noticeDao.getArticleCount(where);
		// �Խñ��� ��ü ������ ���� getArticleCount() �޼ҵ� ȣ��
		close(conn);
		
		return rcnt;
	}
	public ArrayList<NoticeInfo> getArticleList (String where, int cpage, int psize) {
	// �Ű����� : ������, ���� ������ ��ȣ, ������ ũ��� ������ ���ڵ� ������ ���
		ArrayList<NoticeInfo> articleList = null;
		// �Խñ� ����� ���� ArrayList �ν��Ͻ� ����
		Connection conn = getConnection();
		NoticeDao noticeDao = NoticeDao.getInstance();
		noticeDao.setConnection(conn);
		articleList = noticeDao.getArticleList(where, cpage, psize);
		close(conn);
		
		
		return articleList;
	}
}
