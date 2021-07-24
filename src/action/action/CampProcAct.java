package action;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import svc.*;
import vo.*;

public class CampProcAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		String uploadPath = "E:/web/jsp/work/pingping/WebContent/camping/camp_img";
		// ��ǰ, ���� �̹������� ������ ��ġ�� �����η� ����
		
		int maxSize = 5 * 1024 * 1024;	// ���ε� �ִ� �뷮�� 5MB�� ����
		
		MultipartRequest multi = new MultipartRequest(
			request,		// request ��ü�� multi �ν��Ͻ��� ���۵� �����͵��� �ޱ� ����
			uploadPath,		// ������ ������ ������ ����� ��ġ ����
			maxSize,		// �� ���� ���ε� �� �� �ִ� �ִ� ũ�� ����(byte ����)
			"utf-8",		// ������ ���ڵ� ���
			new DefaultFileRenamePolicy());	// ���� �̸��� �ߺ� ó��

		String wtype = multi.getParameter("wtype");		// ķ���ı��� ���/���� ����
		System.out.println(wtype);
		
		String content = "", keyword = "", pdtImgs = "";
		if (wtype.equals("up") || wtype.equals("in")) {
			content = multi.getParameter("content");
			keyword = multi.getParameter("keyword");
			pdtImgs = multi.getParameter("pdtid");
			
			if (!pdtImgs.equals("")) {
				pdtImgs = pdtImgs.substring(1);
				pdtImgs = pdtImgs.replace("/", ".jpg/");
				//System.out.println(pdtImgs);
				pdtImgs += ".jpg";
				//System.out.println(pdtImgs);
			}
		}
	
		int idx	= 0;
		if (wtype.equals("up") || wtype.equals("del")) {
			idx	= Integer.parseInt(multi.getParameter("idx"));	// ķ���ı� ����/������ ��� ����� idx
		}

		Enumeration files = multi.getFileNames();
		// ���ε��� ���ϸ���� Enumeration������ �޾ƿ�(�� file ��Ʈ���� �޾ƿ��� ����)

		String img1 = "", img2 = "", img3 = "", img4 = "", img5 = "", img6 = "", img7 = "", img8 = "", img9 = "", img10 = "";
		String img11 = "", img12 = "", img13 = "", img14 = "", img15 = "", img16 = "", img17 = "", img18 = "", img19 = "", img20 = "";
		String img21 = "", img22 = "", img23 = "", img24 = "", img25 = "", img26 = "", img27 = "", img28 = "", img29 = "", img30 = "";
		
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
				case "img11" : img11 = multi.getFilesystemName(f);	break;
				case "img12" : img12 = multi.getFilesystemName(f);	break;
				case "img13" : img13 = multi.getFilesystemName(f);	break;
				case "img14" : img14 = multi.getFilesystemName(f);	break;
				case "img15" : img15 = multi.getFilesystemName(f);	break;
				case "img16" : img16 = multi.getFilesystemName(f);	break;
				case "img17" : img17 = multi.getFilesystemName(f);	break;
				case "img18" : img18 = multi.getFilesystemName(f);	break;
				case "img19" : img19 = multi.getFilesystemName(f);	break;
				case "img20" : img20 = multi.getFilesystemName(f);	break;
				case "img21" : img21 = multi.getFilesystemName(f);	break;
				case "img22" : img22 = multi.getFilesystemName(f);	break;
				case "img23" : img23 = multi.getFilesystemName(f);	break;
				case "img24" : img24 = multi.getFilesystemName(f);	break;
				case "img25" : img25 = multi.getFilesystemName(f);	break;
				case "img26" : img26 = multi.getFilesystemName(f);	break;
				case "img27" : img27 = multi.getFilesystemName(f);	break;
				case "img28" : img28 = multi.getFilesystemName(f);	break;
				case "img29" : img29 = multi.getFilesystemName(f);	break;
				case "img30" : img30 = multi.getFilesystemName(f);	break;
			}
		}

		String imgs = "";
		int cnt = 1;

		// ���ε� ������ ���ϸ� �ٲٱ�
		File file = new File(imgs);
		File fileNew = new File(imgs);

		if (wtype.equals("in")) {
			if (img1 == null || img1.equals("null") || img1.equals(""))	img1 = "";
			else	{ imgs += changeFileName(uploadPath, img1, cnt) + "/";		cnt  += 1; }
			
			if (img2 == null || img2.equals("null") || img2.equals(""))	img2 = "";
			else	{ imgs += changeFileName(uploadPath, img2, cnt) + "/";		cnt  += 1; }
			
			if (img3 == null || img3.equals("null") || img3.equals(""))	img3 = "";
			else	{ imgs += changeFileName(uploadPath, img3, cnt) + "/";		cnt  += 1; }
			
			if (img4 == null || img4.equals("null") || img4.equals(""))	img4 = "";
			else	{ imgs += changeFileName(uploadPath, img4, cnt) + "/";		cnt  += 1; }
			
			if (img5 == null || img5.equals("null") || img5.equals(""))	img5 = "";
			else	{ imgs += changeFileName(uploadPath, img5, cnt) + "/";		cnt  += 1; }
			
			if (img6 == null || img6.equals("null"))	img6 = "";
			else	{ imgs += changeFileName(uploadPath, img6, cnt) + "/";		cnt  += 1; }
			
			if (img7 == null || img7.equals("null"))	img7 = "";
			else	{ imgs += changeFileName(uploadPath, img7, cnt) + "/";		cnt  += 1; }
			
			if (img8 == null || img8.equals("null"))	img8 = "";
			else	{ imgs += changeFileName(uploadPath, img8, cnt) + "/";		cnt  += 1; }
			
			if (img9 == null || img9.equals("null"))	img9 = "";
			else	{ imgs += changeFileName(uploadPath, img9, cnt) + "/";		cnt  += 1; }
			
			if (img10 == null || img10.equals("null"))	img10 = "";
			else	{ imgs += changeFileName(uploadPath, img10, cnt) + "/";		cnt  += 1; }
	
			if (img11 == null || img11.equals("null"))	img11 = "";
			else	{ imgs += changeFileName(uploadPath, img11, cnt) + "/";		cnt  += 1; }
			
			if (img12 == null || img12.equals("null"))	img12 = "";
			else	{ imgs += changeFileName(uploadPath, img12, cnt) + "/";		cnt  += 1; }
			
			if (img13 == null || img13.equals("null"))	img13 = "";
			else	{ imgs += changeFileName(uploadPath, img13, cnt) + "/";		cnt  += 1; }
			
			if (img14 == null || img14.equals("null"))	img14 = "";
			else	{ imgs += changeFileName(uploadPath, img14, cnt) + "/";		cnt  += 1; }
			
			if (img15 == null || img15.equals("null"))	img15 = "";
			else	{ imgs += changeFileName(uploadPath, img15, cnt) + "/";		cnt  += 1; }
			
			if (img16 == null || img16.equals("null"))	img16 = "";
			else	{ imgs += changeFileName(uploadPath, img16, cnt) + "/";		cnt  += 1; }
			
			if (img17 == null || img17.equals("null"))	img17 = "";
			else	{ imgs += changeFileName(uploadPath, img17, cnt) + "/";		cnt  += 1; }
			
			if (img18 == null || img18.equals("null"))	img18 = "";
			else	{ imgs += changeFileName(uploadPath, img18, cnt) + "/";		cnt  += 1; }
			
			if (img19 == null || img19.equals("null"))	img19 = "";
			else	{ imgs += changeFileName(uploadPath, img19, cnt) + "/";		cnt  += 1; }
			
			if (img20 == null || img20.equals("null"))	img20 = "";
			else	{ imgs += changeFileName(uploadPath, img20, cnt) + "/";		cnt  += 1; }
	
			if (img21 == null || img21.equals("null"))	img21 = "";
			else	{ imgs += changeFileName(uploadPath, img21, cnt) + "/";		cnt  += 1; }
			
			if (img22 == null || img22.equals("null"))	img22 = "";
			else	{ imgs += changeFileName(uploadPath, img22, cnt) + "/";		cnt  += 1; }
			
			if (img23 == null || img23.equals("null"))	img23 = "";
			else	{ imgs += changeFileName(uploadPath, img23, cnt) + "/";		cnt  += 1; }
			
			if (img24 == null || img24.equals("null"))	img24 = "";
			else	{ imgs += changeFileName(uploadPath, img24, cnt) + "/";		cnt  += 1; }
			
			if (img25 == null || img25.equals("null"))	img25 = "";
			else	{ imgs += changeFileName(uploadPath, img25, cnt) + "/";		cnt  += 1; }
			
			if (img26 == null || img26.equals("null"))	img26 = "";
			else	{ imgs += changeFileName(uploadPath, img26, cnt) + "/";		cnt  += 1; }
			
			if (img27 == null || img27.equals("null"))	img27 = "";
			else	{ imgs += changeFileName(uploadPath, img27, cnt) + "/";		cnt  += 1; }
			
			if (img28 == null || img28.equals("null"))	img28 = "";
			else	{ imgs += changeFileName(uploadPath, img28, cnt) + "/";		cnt  += 1; }
			
			if (img29 == null || img29.equals("null"))	img29 = "";
			else	{ imgs += changeFileName(uploadPath, img29, cnt) + "/";		cnt  += 1; }
			
			if (img30 == null || img30.equals("null"))	img30 = "";
			else	{ imgs += changeFileName(uploadPath, img30, cnt) + "/";		cnt  += 1; }	
			
		} else if (wtype.equals("up")){	// ��ǰ���� ������ ��� �̹��� ���ε带 ���� ������ ������ �̹������� �޾ƿ�
			// ���� �̹��� ���� �޾ƿ�
			String oldImgs = multi.getParameter("oldImgs");	// cr_imgs �޾ƿ���
			System.out.println(oldImgs);
			
			String[] arrImg = oldImgs.split("/");
			System.out.println(arrImg[0]);
			
			int imgcnt = arrImg.length;	// ���ε� �Ǿ��ִ� ���� ����
			System.out.println(imgcnt);
			
			if (img1 == null || img1.equals("null") || img1.equals(""))	img1 = multi.getParameter("img1");
			/*
			for (int i = 1; i <= imgcnt ; i ++) {
				String imgnum = "img" + i;
				String arrnum = "arrImg[" + (i - 1) + "]";
				System.out.println(imgnum);
				System.out.println(arrnum);
				System.out.println(img1 == null || img1.equals("null") || imgnum.equals(""));
				
				if (imgnum == null || imgnum.equals("null") || imgnum.equals(""))	imgnum = multi.getParameter(arrnum);
			}
			*/
		}
		
		CampingInfo campInfo = new CampingInfo();	
		if (wtype.equals("up") || wtype.equals("del")) {
			campInfo.setCr_idx(idx);	// ����/������ ��쿡�� ���ǰ�, ����� ��� ������� ����
		}
		
		HttpSession session = request.getSession();
		MemberInfo memberInfo = (MemberInfo)session.getAttribute("memberInfo");
		campInfo.setMi_email(memberInfo.getMi_email());	// �α����� ȸ���� �̸��� ����
		campInfo.setMi_nick(memberInfo.getMi_nick());	// �α����� ȸ���� ���� ����
		
		if (wtype.equals("up") || wtype.equals("in")) {
			if (imgs != null || !imgs.equals("null"))	imgs = imgs.substring(0, imgs.length() - 1);
			
			campInfo.setCr_imgs(imgs);
			campInfo.setCr_pdtimgs(pdtImgs);
			campInfo.setCr_content(content);
			campInfo.setCr_keyword(keyword);
		}

		//request.setAttribute("campInfo", campInfo); 
		
		CampProcSvc campProcSvc = new CampProcSvc();
		boolean isSuccess = false;
		String failMsg = "";
		String lnk = "";

		if (wtype.equals("in")) {			// ķ���ı� ����� ���	
			isSuccess = campProcSvc.addReview(campInfo);
			failMsg = "ķ���ı� ��Ͽ� �����߽��ϴ�.";
			lnk = "camp_list.camp";
		} else if (wtype.equals("up")) {	// ķ���ı�  ������ ���
			isSuccess = campProcSvc.updateReview(campInfo);
			failMsg = "ķ���ı� ������ �����߽��ϴ�.";
			lnk = "free_view.free?idx=" + idx + "&cpage=" + multi.getParameter("cpage") +
				  "&psize=" + multi.getParameter("psize") +
				  "&ord=" + multi.getParameter("ord");
		} else if (wtype.equals("del")){	// ķ���ı�  ������ ���
			isSuccess = campProcSvc.deleteReview(campInfo);
			failMsg = "ķ���ı� ������ �����߽��ϴ�.";
			lnk = "camp_list.camp";
		}
		
		if (!isSuccess) {
			response.setContentType("text/html); charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('" + failMsg + "');");
			out.println("history.back();");
			out.println("</script>");
			out.close();			
		}
		
		System.out.println("�ƾ�, ����� CampProcAct");
			
		ActionForward forward = new ActionForward();
		forward.setRedirect(true);		// redirect ������� �̵��ϵ��� ����(ctrl �� �� Ȯ���ϱ�!)
		forward.setPath(lnk);	//lnk
		// forward �ν��Ͻ��� setPath() �޼ҵ带 �̿��Ͽ� forward �ν��Ͻ��� path ��������� ���� ����
		
		return forward;
	}
	
	private String changeFileName(String uploadPath, String str, int cnt) {	
		//String changeName = "";
		
		if (str == null || str.equals("null") || str.equals("")) {
			System.out.println("if�� " + str);
			return str;
		}
		
		String fileName = str;
		String extension = str.substring(str.length() - 4);	// .jpg

		CampProcSvc campProcSvc = new CampProcSvc();
		
		int idx = campProcSvc.getIdx();
		String sidx = "";
		if (idx < 10)			sidx = "100" + idx;
		else if (idx < 100)		sidx = "10" + idx;
		else if (idx < 1000)		sidx = "1" + idx;	
		System.out.println(idx);
				
		String newFileName = "cr" + sidx + "_" + cnt + extension;
		System.out.println(newFileName);
		// ������� DB�� �� �̹��� ���ϸ� ����
		
		
		// ���⼭���� ���ε� ������ ���ε� �� �̹��� ���ϸ� ����
		renameFile(uploadPath, str, newFileName);
		
		str = newFileName;

		System.out.println(str);
		
		return str;
	}
	
	public void renameFile(String uploadPath, String filename, String newFilename) {
	    File file = new File(uploadPath +  "/" + filename);
	    File fileNew = new File(uploadPath + "/" + newFilename);
	    if(!file.renameTo(fileNew)) {
	    	System.out.println("����");
	    } else {

	    	System.out.println("����");
	    }
	}
	
	
}
