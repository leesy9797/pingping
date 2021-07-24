package action;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import svc.*;
import vo.*;

public class FreeProcAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String uploadPath = "D:/web/jsp/work/pingping/WebContent/free/free_img";
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
		if (wtype.equals("up") || wtype.equals("del")) {
			idx	= Integer.parseInt(multi.getParameter("idx"));	// ���� ����/������ ��� ����� idx
		}
		String title	= multi.getParameter("title");
		String content	= multi.getParameter("content");
		
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
		// ��ǰ���� ������ ��� �̹��� ���ε带 ���� ������ ������ �̹������� �޾ƿ�
			if (img1 == null || img1.equals("null"))	img1 = multi.getParameter("oldImg1");
			if (img2 == null || img2.equals("null"))	img2 = multi.getParameter("oldImg2");
			if (img3 == null || img3.equals("null"))	img3 = multi.getParameter("oldImg3");
		}
		
		FreeInfo freeInfo = new FreeInfo();
		if (wtype.equals("up") || wtype.equals("del")) {
			freeInfo.setBf_idx(idx);	// ����/������ ��쿡�� ���ǰ�, ����� ��� ������� ����
		}
		freeInfo.setBf_title(title);
		freeInfo.setBf_content(content);
		freeInfo.setBf_img1(img1);
		freeInfo.setBf_img2(img2);
		freeInfo.setBf_img3(img3);
		// ����ڰ� �Է��� ������ ��ǰ���� ����� �ν��Ͻ��� ����
		
		FreeProcSvc freeProcSvc = new FreeProcSvc();
		boolean isSuccess = false;
		String failMsg = "";
		String lnk = "";

		HttpSession session = request.getSession();
		MemberInfo memberInfo = (MemberInfo)session.getAttribute("memberInfo");
		freeInfo.setMi_email(memberInfo.getMi_email());	// �α����� ȸ���� �̸��� ����
		freeInfo.setBf_nick(memberInfo.getMi_nick());	// �α����� ȸ���� ���� ����
		
		if (wtype.equals("in")) {	// ���� ����� ���
			isSuccess = freeProcSvc.freeInsert(freeInfo);
			failMsg = "���� ��Ͽ� �����߽��ϴ�.";
			lnk = "free_list.free?";
		} else if (wtype.equals("up")) {	// ���� ������ ���
			isSuccess = freeProcSvc.freeUpdate(freeInfo);
			failMsg = "���� ������ �����߽��ϴ�.";
			lnk = "free_view.free?idx=" + idx + "&";
		} else {	// ���� ������ ���
			isSuccess = freeProcSvc.freeDelete(freeInfo);
			failMsg = "���� ������ �����߽��ϴ�.";
			lnk = "free_list.free?";
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
		String keyword	= multi.getParameter("keyword");	// �˻���
		// �˻����� ������Ʈ�� ����
		String args = "";
		if (keyword == null)	args += "&keyword=";	
		else	args += "&keyword=" + keyword;
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(true);	// redirect ������� �̵��ϵ��� ����
		forward.setPath(lnk + "cpage=" + cpage + args);
		
		return forward;
	}
}
