package admin.act;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import admin.svc.*;
import vo.*;

public class AdminMonthProcAct implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String uploadPath = "D:/web/jsp/work/pingping/WebContent/monthly/month_img";
		// �̴�����õ �̹������� ������ ��ġ�� �����η� ����
		int maxSize = 5 * 1024 * 1024;	// ���ε� �ִ� �뷮�� 5MB�� ����
		
		MultipartRequest multi = new MultipartRequest(
			request,	// request��ü�� multi�ν��Ͻ��� ���۵� �����͵��� �ޱ� ����
			uploadPath,	// ������ ������ ������ ����� ��ġ ����
			maxSize,	// �� ���� ���ε� �� �� �ִ� �ִ� ũ�� ����(byte ����)
			"utf-8",	// ������ ���ڵ� ���
			new DefaultFileRenamePolicy());	// ���� �̸��� �ߺ� ó��
		
		String wtype	= multi.getParameter("wtype");	// �̴�����õ ���/���� ����
		int idx			= Integer.parseInt(multi.getParameter("idx"));	// �̴�����õ ������ ��� ����� �Խñ� idx
		String title	= multi.getParameter("title");
		String name		= multi.getParameter("name");
		String content	= multi.getParameter("content");
		String location	= multi.getParameter("location");
		String url		= multi.getParameter("url");
		String mkeyword	= multi.getParameter("mkeyword");
		//String pdtids	= multi.getParameter("pdtids");
		String pdtids	= "";
		String misview	= multi.getParameter("misview");
		
		String month = "", info = "";
		if (multi.getParameterValues("month") != null) {
			String[] arrMonth = multi.getParameterValues("month");	
			// üũ�Ǿ� �ִ� üũ�ڽ��� value ������ ���ڿ� �迭�� �޾ƿ�
			for (int i = 0 ; i < arrMonth.length ; i++) {
				month += "/" + arrMonth[i];
			}
			month = month.substring(1);
			//System.out.println(month);
		}
		
		if (multi.getParameterValues("info") != null) {
			String[] arrInfo = multi.getParameterValues("info");	
			// üũ�Ǿ� �ִ� üũ�ڽ��� value ������ ���ڿ� �迭�� �޾ƿ�
			for (int i = 0 ; i < arrInfo.length ; i++) {
				info += "/" + arrInfo[i];
			}
			info = info.substring(1);
			//System.out.println(info);
		}
		
		Enumeration files = multi.getFileNames();
		// ���ε��� ���ϸ���� Enumeration������ �޾ƿ�(�� file��Ʈ���� �޾ƿ��� ����)
		String img1 = "", img2 = "", img3 = "", img4 = "", img5 = "";
		String img6 = "", img7 = "", img8 = "", img9 = "", img10 = "";
		while (files.hasMoreElements()) {
			String f = (String)files.nextElement();
			switch (f) {
				case "img1" : img1 = multi.getFilesystemName(f);	break;
				case "img2" : img2 = multi.getFilesystemName(f);	break;
				case "img3" : img3 = multi.getFilesystemName(f);	break;
				case "img4" : img4 = multi.getFilesystemName(f);	break;
				case "img5" : img5 = multi.getFilesystemName(f);	break;
				case "img6" : img6 = multi.getFilesystemName(f);	break;
				case "img7" : img7 = multi.getFilesystemName(f);	break;
				case "img8" : img8 = multi.getFilesystemName(f);	break;
				case "img9" : img9 = multi.getFilesystemName(f);	break;
				case "img10" : img10 = multi.getFilesystemName(f);	break;
			}
		}

		String imgs = "";
		if (wtype.equals("in")) {
			if (img1 == null || img1.equals("null"))	img1 = "";
			else	imgs += img1;	// 1�� ������ ����ִ� ���� �ڹٽ�ũ��Ʈ�� �˻�?
			if (img2 == null || img2.equals("null"))	img2 = "";
			else	imgs += "/" + img2;
			if (img3 == null || img3.equals("null"))	img3 = "";
			else	imgs += "/" + img3;
			if (img4 == null || img4.equals("null"))	img4 = "";
			else	imgs += "/" + img4;
			if (img5 == null || img5.equals("null"))	img5 = "";
			else	imgs += "/" + img5;
			if (img6 == null || img6.equals("null"))	img6 = "";
			else	imgs += "/" + img6;
			if (img7 == null || img7.equals("null"))	img7 = "";
			else	imgs += "/" + img7;
			if (img8 == null || img8.equals("null"))	img8 = "";
			else	imgs += "/" + img8;
			if (img9 == null || img9.equals("null"))	img9 = "";
			else	imgs += "/" + img9;
			if (img10 == null || img10.equals("null"))	img10 = "";
			else	imgs += "/" + img10;
		} else {
		// �̴�����õ ������ ��� �̹��� ���ε带 ���� ������ ������ �̹������� �޾ƿ�
			if (img1 == null || img1.equals("null")) { img1 = multi.getParameter("oldImg1");	imgs += img1; } 
			else	imgs += img1;
			if (img2 == null || img2.equals("null")) { img2 = multi.getParameter("oldImg2");	imgs += "/" + img2; } 
			else	imgs += "/" + img2;
			if (img3 == null || img3.equals("null")) { img3 = multi.getParameter("oldImg3");	imgs += "/" + img3; } 
			else	imgs += "/" + img3;
			if (img4 == null || img4.equals("null")) { img4 = multi.getParameter("oldImg4");	imgs += "/" + img4; } 
			else	imgs += "/" + img4;
			if (img5 == null || img5.equals("null")) { img5 = multi.getParameter("oldImg5");	imgs += "/" + img5; } 
			else	imgs += "/" + img5;
			if (img6 == null || img6.equals("null")) { img6 = multi.getParameter("oldImg6");	imgs += "/" + img6; } 
			else	imgs += "/" + img6;
			if (img7 == null || img7.equals("null")) { img7 = multi.getParameter("oldImg7");	imgs += "/" + img7; } 
			else	imgs += "/" + img7;
			if (img8 == null || img8.equals("null")) { img8 = multi.getParameter("oldImg8");	imgs += "/" + img8; } 
			else	imgs += "/" + img8;
			if (img9 == null || img9.equals("null")) { img9 = multi.getParameter("oldImg9");	imgs += "/" + img9; } 
			else	imgs += "/" + img9;
			if (img10 == null || img10.equals("null")) { img10 = multi.getParameter("oldImg10"); imgs += "/" + img10; } 
			else	imgs += "/" + img10;
		}
		
		MonthlyInfo monthInfo = new MonthlyInfo();
		monthInfo.setMr_idx(idx);	// ������ ��쿡�� ���ǰ�, ����� ��� ������� ����
		monthInfo.setMr_title(title);
		monthInfo.setMr_name(name);
		monthInfo.setMr_content(content);
		monthInfo.setMr_location(location);
		monthInfo.setMr_url(url);
		monthInfo.setMr_keyword(mkeyword);
		monthInfo.setMr_imgs(imgs);
		monthInfo.setMr_pdtids(pdtids);
		monthInfo.setMr_month(month);
		monthInfo.setMr_info(info);
		monthInfo.setMr_isview(misview);
		// ����ڰ� �Է��� ������ �̴�����õ ���� ����� �ν��Ͻ��� ����
		
		AdminMonthProcSvc adminMonthProcSvc = new AdminMonthProcSvc();
		boolean isSuccess = false;
		String failMsg = "";
		
		if (wtype.equals("in")) {	// �̴�����õ ����� ���
			HttpSession session = request.getSession();
			AdminInfo ai = (AdminInfo)session.getAttribute("adminInfo");
			monthInfo.setAi_idx(ai.getAi_idx());	// ��� ������ ��ȣ ����
			
			isSuccess = adminMonthProcSvc.monthInsert(monthInfo);
			failMsg = "�Խñ� ��Ͽ� �����߽��ϴ�.";
		} else {	// �̴�����õ ������ ���
			HttpSession session = request.getSession();
			AdminInfo ai = (AdminInfo)session.getAttribute("adminInfo");
			monthInfo.setLast_admin(ai.getAi_idx());	// ��� ������ ��ȣ ����
			
			isSuccess = adminMonthProcSvc.monthUpdate(monthInfo);
			failMsg = "�Խñ� ������ �����߽��ϴ�.";
		}
		if (!isSuccess) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('" + failMsg + "');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		
		// ������ ������ �˻����� ������
		int cpage = 1;
		if (wtype.equals("up")) {
			cpage = Integer.parseInt(multi.getParameter("cpage"));
		}
		String isview	= multi.getParameter("isview");		// �Խÿ���(y, n)
		String schtype	= multi.getParameter("schtype");	// �˻�����(id, ��ǰ��)
		String keyword	= multi.getParameter("keyword");	// �˻���
		String sdate	= multi.getParameter("sdate");		// ��ϱⰣ �� ������
		String edate	= multi.getParameter("edate");		// ��ϱⰣ �� ������
		
		// �˻����� ������Ʈ�� ����
		String args = "";
		if (isview == null)	args += "&isview=";		else args += "&isview=" + isview;
		if (schtype == null)args += "&schtype=";	else args += "&schtype=" + schtype;
		if (keyword == null)args += "&keyword=";	else args += "&keyword=" + keyword;
		if (sdate == null)	args += "&sdate=";		else args += "&sdate=" + sdate;
		if (edate == null)	args += "&edate=";		else args += "&edate=" + edate;
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(true);	// redirect ������� �̵��ϵ��� ����
		forward.setPath("month_list.montha?cpage=" + cpage + args);
		
		return forward;
	}
}
