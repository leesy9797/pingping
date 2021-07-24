package action;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import svc.*;
import vo.*;

public class ReviewProcAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String uploadPath = "D:/web/jsp/work/pingping/WebContent/review/review_img";
		// ���� �̹������� ������ ��ġ�� �����η� ����
		int maxSize = 5 * 1024 * 1024;	// ���ε� �ִ� �뷮�� 5MB�� ����
		
		MultipartRequest multi = new MultipartRequest(
			request,	// request��ü�� multi�ν��Ͻ��� ���۵� �����͵��� �ޱ� ����
			uploadPath,	// ������ ������ ������ ����� ��ġ ����
			maxSize,	// �� ���� ���ε� �� �� �ִ� �ִ� ũ�� ����(byte ����)
			"utf-8",	// ������ ���ڵ� ���
			new DefaultFileRenamePolicy());	// ���� �̸��� �ߺ� ó��

		String wtype	= multi.getParameter("wtype");	// ���� ���/���� ����
		int idx	= 1;
		String odid = "", piid = "", bropt = "";
		if (wtype.equals("up")) {
			idx	= Integer.parseInt(multi.getParameter("idx"));	// ���� ������ ��� ����� idx
		} else {
			odid = multi.getParameter("odid");
			piid = multi.getParameter("piid");
			bropt = multi.getParameter("bropt");
		}
		String content	= multi.getParameter("content");
		int star		= Integer.parseInt(multi.getParameter("star"));
		
		Enumeration files = multi.getFileNames();
		// ���ε��� ���ϸ���� Enumeration������ �޾ƿ�(�� file��Ʈ���� �޾ƿ��� ����)
		String img1 = "", img2 = "", img3 = "";
		while (files.hasMoreElements()) {
			String f = (String)files.nextElement();
			switch (f) {
				case "img1" : img1 = multi.getFilesystemName(f);	break;
				case "img2" : img2 = multi.getFilesystemName(f);	break;
				case "img3" : img3 = multi.getFilesystemName(f);	break;
			}
		}
		
		if (wtype.equals("in")) {
			if (img1 == null || img1.equals("null"))	img1 = "";
			if (img2 == null || img2.equals("null"))	img2 = "";
			if (img3 == null || img3.equals("null"))	img3 = "";
		} else {
		// ���� ������ ��� �̹��� ���ε带 ���� ������ ������ �̹������� �޾ƿ�
			if (img1 == null || img1.equals("null"))	img1 = multi.getParameter("oldImg1");
			if (img2 == null || img2.equals("null"))	img2 = multi.getParameter("oldImg2");
			if (img3 == null || img3.equals("null"))	img3 = multi.getParameter("oldImg3");
		}
		
		ReviewInfo reviewInfo = new ReviewInfo();
		if (wtype.equals("up") || wtype.equals("del")) {
			reviewInfo.setPr_idx(idx);	// ������ ��쿡�� ���ǰ�, ����� ��� ������� ����
		} else {
			reviewInfo.setOd_id(odid);
			reviewInfo.setPi_id(piid);
			reviewInfo.setBr_opt(bropt);
		}
		reviewInfo.setPr_content(content);
		reviewInfo.setPr_star(star);
		reviewInfo.setPr_img1(img1);
		reviewInfo.setPr_img2(img2);
		reviewInfo.setPr_img3(img3);
		// ����ڰ� �Է��� ������ �������� ����� �ν��Ͻ��� ����
		
		ReviewProcSvc reviewProcSvc = new ReviewProcSvc();
		boolean isSuccess = false;
		String failMsg = "";
		String lnk = "";

		HttpSession session = request.getSession();
		MemberInfo memberInfo = (MemberInfo)session.getAttribute("memberInfo");
		reviewInfo.setMi_email(memberInfo.getMi_email());	// �α����� ȸ���� �̸��� ����
		
		if (wtype.equals("in")) {	// ���� ����� ���
			isSuccess = reviewProcSvc.reviewInsert(reviewInfo);
			failMsg = "���� ��Ͽ� �����߽��ϴ�.";
			lnk = "review_write_list.review?";
		} else {	// ���� ������ ���
			isSuccess = reviewProcSvc.reviewUpdate(reviewInfo);
			failMsg = "���� ������ �����߽��ϴ�.";
			lnk = "review_list.review?";
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
		String ord = "";
		if (wtype.equals("up")) {
			cpage = Integer.parseInt(multi.getParameter("cpage"));
			ord = multi.getParameter("ord");
		}
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(true);	// redirect ������� �̵��ϵ��� ����
		forward.setPath(lnk + "cpage=" + cpage + "&ord=" + ord);
		
		return forward;
	}
}
