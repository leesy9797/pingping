package svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import dao.*;
import vo.*;

public class NoticeViewSvc {
// �Խñ� ������ ����Ͻ� ������ ó���ϴ� Ŭ����
	public NoticeInfo getArticle(int idx) {
	// �޾ƿ� idx�� �ش��ϴ� �Խñ��� �����͵��� NoticeInfo �� �ν��Ͻ��� ����
		NoticeInfo article = null;
		Connection conn = getConnection();	// DB�� ����
		NoticeDao noticeDao = NoticeDao.getInstance();
		noticeDao.setConnection(conn);
		
		int result = noticeDao.readCountUp(idx);	// ��ȸ�� ����
		if (result > 0) commit(conn);	// update�� ���ڵ尡 ������ commit ��Ŵ
		else 			rollback(conn);	// update�� ���ڵ尡 ������ rollback ��Ŵ
		// ��ȸ�� ������ �Խ��� ��� ū ������ ��ġ�� �����Ƿ� rollback �Ǿ ��� �۾��� �̾
		
		article = noticeDao.getArticle(idx);
		// ����ڰ� ������ �Խñ��� ������ �޾ƿ� article�� ����
		
		return article;
	}
}
