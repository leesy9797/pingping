package admin.dao;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import javax.sql.*;
import vo.*;

public class AdminMonthDao {
	private static AdminMonthDao adminMonthDao;
	private Connection conn;
	private AdminMonthDao() {}      
	
	public static AdminMonthDao getInstance() {
		if (adminMonthDao == null)   adminMonthDao = new AdminMonthDao();
		return adminMonthDao;
	}	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	public int getMonthCount(String where) {
		Statement stmt = null;   
		ResultSet rs = null;   
		int rcnt = 0;         
		
		try {
			String sql = "select count(*) from t_monthly_rec " + where;
			System.out.println(sql);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())   rcnt = rs.getInt(1);
			
		} catch(Exception e) {
			System.out.println("getMonthCount()�޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs);	close(stmt);
		}
		
		return rcnt;
	}
	
	public ArrayList<MonthlyInfo> getMonthList(String where, String orderBy, int cpage, int psize) {
	// �˻��� �Խñ� ����� ArrayList<MonthlyInfo> �� �ν��Ͻ��� �����ϴ� �޼ҵ�
		ArrayList<MonthlyInfo> monthList = new ArrayList<MonthlyInfo>();
		// �Խñ� ����� ������ ArrayList�� ���� MonthlyInfo�� �ν��Ͻ��� �����
		Statement stmt = null;
		ResultSet rs = null;
		MonthlyInfo monthInfo = null;
		// monthList�� ���� �������� MonthlyInfo �� �ν��Ͻ��� ����   
		int snum = (cpage - 1) * psize;
		// ������ limit ��ɿ��� �����͸� ������ ���� �ε��� ��ȣ
		
		try {
			String sql = "select * from t_monthly_rec " + where + orderBy + " limit " + snum + ", " + psize;
			System.out.println(sql);
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
			// rs�� ������ �Խñ��� ���� ���
				monthInfo = new MonthlyInfo();
				// monthList�� ������ �ϳ��� �Խñ� ������ ���� �ν��Ͻ� ����

				monthInfo.setMr_idx(rs.getInt("mr_idx"));
				monthInfo.setMr_title(rs.getString("mr_title"));
				monthInfo.setMr_name(rs.getString("mr_name"));
				monthInfo.setMr_content(rs.getString("mr_content"));
				monthInfo.setMr_location(rs.getString("mr_location"));
				monthInfo.setMr_url(rs.getString("mr_url"));
				monthInfo.setMr_keyword(rs.getString("mr_keyword"));
				monthInfo.setMr_imgs(rs.getString("mr_imgs"));
				monthInfo.setMr_pdtids(rs.getString("mr_pdtids"));
				monthInfo.setMr_info(rs.getString("mr_info"));
				monthInfo.setMr_reply(rs.getInt("mr_reply"));
				monthInfo.setMr_read(rs.getInt("mr_read"));
				monthInfo.setMr_good(rs.getInt("mr_good"));
				monthInfo.setMr_month(rs.getString("mr_month"));
				monthInfo.setMr_isview(rs.getString("mr_isview"));
				monthInfo.setMr_date(rs.getString("mr_date"));
				monthInfo.setAi_idx(rs.getInt("ai_idx"));
				monthInfo.setLast_date(rs.getString("last_date"));
				monthInfo.setLast_admin(rs.getInt("last_admin"));
				// �޾ƿ� ���ڵ��� �Խñ� ������ ����
				
				monthList.add(monthInfo);
				// �� ��ǰ�� ������ ���� monthInfo �ν��Ͻ��� monthList�� ����
			}
         
		} catch(Exception e) {
			System.out.println("getMonthList() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
      
		return monthList;
	}
	
	public MonthlyInfo getMonthInfo(int idx) {
	// ������ idx�� �ش��ϴ� Ư�� �Խñ� ������ MonthlyInfo�� �ν��Ͻ��� �����ϴ� �޼ҵ�
		MonthlyInfo monthInfo = null;
		Statement stmt = null;
		ResultSet rs = null;
      
		try {
			String sql = "select * from t_monthly_rec where mr_idx = " + idx + " ";
			
			System.out.println(sql);
			stmt = conn.createStatement();   
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {   
				monthInfo = new MonthlyInfo();

				monthInfo.setMr_idx(rs.getInt("mr_idx"));
				monthInfo.setMr_title(rs.getString("mr_title"));
				monthInfo.setMr_name(rs.getString("mr_name"));
				monthInfo.setMr_content(rs.getString("mr_content"));
				monthInfo.setMr_location(rs.getString("mr_location"));
				monthInfo.setMr_url(rs.getString("mr_url"));
				monthInfo.setMr_keyword(rs.getString("mr_keyword"));
				monthInfo.setMr_imgs(rs.getString("mr_imgs"));
				monthInfo.setMr_pdtids(rs.getString("mr_pdtids"));
				monthInfo.setMr_info(rs.getString("mr_info"));
				monthInfo.setMr_reply(rs.getInt("mr_reply"));
				monthInfo.setMr_read(rs.getInt("mr_read"));
				monthInfo.setMr_good(rs.getInt("mr_good"));
				monthInfo.setMr_month(rs.getString("mr_month"));
				monthInfo.setMr_isview(rs.getString("mr_isview"));
				monthInfo.setMr_date(rs.getString("mr_date"));
				monthInfo.setAi_idx(rs.getInt("ai_idx"));
				monthInfo.setLast_date(rs.getString("last_date"));
				monthInfo.setLast_admin(rs.getInt("last_admin"));
			}
         
		} catch(Exception e) {
			System.out.println("getMonthInfo() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(rs);
			close(stmt);
		}
      
		return monthInfo;
	}
	
	public int monthInsert(MonthlyInfo monthInfo) {	// �Խñ� ��� ó���� ���� �޼ҵ�
		int result = 0;
		Statement stmt = null;
		
		try {
			String sql = "insert into t_monthly_rec (mr_title, mr_name, mr_content, " + 
				"mr_location, mr_url, mr_keyword, mr_imgs, mr_pdtids, mr_info, mr_month, " + 
				"mr_isview, ai_idx, last_admin) values " + "('" + 
				monthInfo.getMr_title()		+ "', '" + 
				monthInfo.getMr_name()		+ "', '" + 
				monthInfo.getMr_content()	+ "', '" + 
				monthInfo.getMr_location()	+ "', '" + 
				monthInfo.getMr_url()		+ "', '" + 
				monthInfo.getMr_keyword()	+ "', '" + 
				monthInfo.getMr_imgs()		+ "', '" + 
				//monthInfo.getMr_pdtids()	+ "', '" + ��ǰ��� ���߿�
				"', '" +
				monthInfo.getMr_info()		+ "', '" + 
				monthInfo.getMr_month()		+ "', '" + 
				monthInfo.getMr_isview()	+ "', " + 
				monthInfo.getAi_idx()		+ ", " + 
				monthInfo.getAi_idx()		+ ")";		// ����� ��� ai_idx = last_admin
			System.out.println(sql);
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
			
		} catch(Exception e) {
			System.out.println("monthInsert() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}
	
	public int monthUpdate(MonthlyInfo monthInfo) {	// �Խñ� ���� ó���� ���� �޼ҵ�
		int result = 0;
		Statement stmt = null;

		String imgs = "";
		if (monthInfo.getMr_imgs().equals("null"))	imgs = "";
		else										imgs = monthInfo.getMr_imgs();
		
		try {
			stmt = conn.createStatement(); 			
			String sql = "update t_monthly_rec set " + 
				"mr_title = '"		+ monthInfo.getMr_title()	+ "', " + 
				"mr_name = '"		+ monthInfo.getMr_name()	+ "', " + 
				"mr_content = '"	+ monthInfo.getMr_content()	+ "', " + 
				"mr_location = '"	+ monthInfo.getMr_location()+ "', " + 
				"mr_url = '"		+ monthInfo.getMr_url()		+ "', " + 
				"mr_keyword = '" 	+ monthInfo.getMr_keyword()	+ "', " + 
				"mr_imgs = '" 		+ imgs						+ "', " + 
				"mr_pdtids = '"		+ monthInfo.getMr_pdtids()	+ "', " + 
				"mr_info = '"		+ monthInfo.getMr_info()	+ "', " + 
				"mr_month = '"		+ monthInfo.getMr_month()	+ "', " + 
				"mr_isview = '"		+ monthInfo.getMr_isview()	+ "', " + 
				"last_date = now(), " +										// ���������� ������ ��¥
				"last_admin = "	+ monthInfo.getLast_admin()	+ " " +	// ���������� ������ �����ڹ�ȣ
				"where mr_idx = "	+ monthInfo.getMr_idx();
			System.out.println(sql);
			result = stmt.executeUpdate(sql);
			
		} catch(Exception e) {
			System.out.println("monthUpdate() �޼ҵ� ����");
			e.printStackTrace();
		} finally {
			close(stmt);
		}
		
		return result;
	}
}
