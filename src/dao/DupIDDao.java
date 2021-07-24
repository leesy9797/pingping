package dao;

import static db.JdbcUtil.*;
import java.sql.*;
import vo.*;

public class DupIDDao {
// ȸ�� �ߺ� ���̵�
	private static DupIDDao dupIDDao;
	private Connection conn; 
	private DupIDDao() {}
	public static DupIDDao getInstance() {
		if (dupIDDao == null) {
			dupIDDao = new DupIDDao();
		}
		return dupIDDao;
	}
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	public int chkDupID(String uid) {
		Statement stmt = null;
		ResultSet rs = null;
		int chkPoint = 0;	// �ߺ��� ���̵��� ������ ������ ����(�ߺ�:1, ���ߺ�:0)
		String sql = "select count(*) from t_member_info where mi_email = '"+ uid +"'";
		try {
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) chkPoint = rs.getInt(1);
		} catch(Exception e) {
			System.out.println("chkDupID() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs); 	close(stmt);
		}
		return chkPoint;
	}
}
