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
		// 질문 이미지들을 저장할 위치를 절대경로로 지정
		int maxSize = 5 * 1024 * 1024;	// 업로드 최대 용량은 5MB로 지정
		
		MultipartRequest multi = new MultipartRequest(
			request,	// request객체로 multi인스턴스로 전송된 데이터들을 받기 위함
			uploadPath,	// 서버에 실제로 파일이 저장될 위치 지정
			maxSize,	// 한 번에 업로드 할 수 있는 최대 크기 지정(byte 단위)
			"utf-8",	// 파일의 인코딩 방식
			new DefaultFileRenamePolicy());	// 파일 이름의 중복 처리

		String wtype	= multi.getParameter("wtype");	// 질문 등록/수정 여부
		int idx	= 1;
		if (wtype.equals("up") || wtype.equals("del")) {
			idx	= Integer.parseInt(multi.getParameter("idx"));	// 질문 수정/삭제일 경우 사용할 idx
		}
		String title	= multi.getParameter("title");
		String content	= multi.getParameter("content");
		
		Enumeration files = multi.getFileNames();
		// 업로드할 파일명들을 Enumeration형으로 받아옴(빈 file컨트롤은 받아오지 않음)
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
		// 상품정보 수정일 경우 이미지 업로드를 하지 않으면 기존의 이미지명을 받아옴
			if (img1 == null || img1.equals("null"))	img1 = multi.getParameter("oldImg1");
			if (img2 == null || img2.equals("null"))	img2 = multi.getParameter("oldImg2");
			if (img3 == null || img3.equals("null"))	img3 = multi.getParameter("oldImg3");
		}
		
		FreeInfo freeInfo = new FreeInfo();
		if (wtype.equals("up") || wtype.equals("del")) {
			freeInfo.setBf_idx(idx);	// 수정/삭제일 경우에만 사용되고, 등록일 경우 사용하지 않음
		}
		freeInfo.setBf_title(title);
		freeInfo.setBf_content(content);
		freeInfo.setBf_img1(img1);
		freeInfo.setBf_img2(img2);
		freeInfo.setBf_img3(img3);
		// 사용자가 입력한 정보를 상품정보 저장용 인스턴스에 넣음
		
		FreeProcSvc freeProcSvc = new FreeProcSvc();
		boolean isSuccess = false;
		String failMsg = "";
		String lnk = "";

		HttpSession session = request.getSession();
		MemberInfo memberInfo = (MemberInfo)session.getAttribute("memberInfo");
		freeInfo.setMi_email(memberInfo.getMi_email());	// 로그인한 회원의 이메일 저장
		freeInfo.setBf_nick(memberInfo.getMi_nick());	// 로그인한 회원의 별명 저장
		
		if (wtype.equals("in")) {	// 질문 등록일 경우
			isSuccess = freeProcSvc.freeInsert(freeInfo);
			failMsg = "질문 등록에 실패했습니다.";
			lnk = "free_list.free?";
		} else if (wtype.equals("up")) {	// 질문 수정일 경우
			isSuccess = freeProcSvc.freeUpdate(freeInfo);
			failMsg = "질문 수정에 실패했습니다.";
			lnk = "free_view.free?idx=" + idx + "&";
		} else {	// 질문 수정일 경우
			isSuccess = freeProcSvc.freeDelete(freeInfo);
			failMsg = "질문 삭제에 실패했습니다.";
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
		
		// 페이지 정보과 검색관련 정보들
		int cpage = 1;
		if (wtype.equals("up")) {
			cpage = Integer.parseInt(multi.getParameter("cpage"));
		}
		String keyword	= multi.getParameter("keyword");	// 검색어
		// 검색관련 쿼리스트링 제작
		String args = "";
		if (keyword == null)	args += "&keyword=";	
		else	args += "&keyword=" + keyword;
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(true);	// redirect 방식으로 이동하도록 지정
		forward.setPath(lnk + "cpage=" + cpage + args);
		
		return forward;
	}
}
