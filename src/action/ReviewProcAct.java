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
		// 리뷰 이미지들을 저장할 위치를 절대경로로 지정
		int maxSize = 5 * 1024 * 1024;	// 업로드 최대 용량은 5MB로 지정
		
		MultipartRequest multi = new MultipartRequest(
			request,	// request객체로 multi인스턴스로 전송된 데이터들을 받기 위함
			uploadPath,	// 서버에 실제로 파일이 저장될 위치 지정
			maxSize,	// 한 번에 업로드 할 수 있는 최대 크기 지정(byte 단위)
			"utf-8",	// 파일의 인코딩 방식
			new DefaultFileRenamePolicy());	// 파일 이름의 중복 처리

		String wtype	= multi.getParameter("wtype");	// 리뷰 등록/수정 여부
		int idx	= 1;
		String odid = "", piid = "", bropt = "";
		if (wtype.equals("up")) {
			idx	= Integer.parseInt(multi.getParameter("idx"));	// 리뷰 수정일 경우 사용할 idx
		} else {
			odid = multi.getParameter("odid");
			piid = multi.getParameter("piid");
			bropt = multi.getParameter("bropt");
		}
		String content	= multi.getParameter("content");
		int star		= Integer.parseInt(multi.getParameter("star"));
		
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
		// 리뷰 수정일 경우 이미지 업로드를 하지 않으면 기존의 이미지명을 받아옴
			if (img1 == null || img1.equals("null"))	img1 = multi.getParameter("oldImg1");
			if (img2 == null || img2.equals("null"))	img2 = multi.getParameter("oldImg2");
			if (img3 == null || img3.equals("null"))	img3 = multi.getParameter("oldImg3");
		}
		
		ReviewInfo reviewInfo = new ReviewInfo();
		if (wtype.equals("up") || wtype.equals("del")) {
			reviewInfo.setPr_idx(idx);	// 수정일 경우에만 사용되고, 등록일 경우 사용하지 않음
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
		// 사용자가 입력한 정보를 리뷰정보 저장용 인스턴스에 넣음
		
		ReviewProcSvc reviewProcSvc = new ReviewProcSvc();
		boolean isSuccess = false;
		String failMsg = "";
		String lnk = "";

		HttpSession session = request.getSession();
		MemberInfo memberInfo = (MemberInfo)session.getAttribute("memberInfo");
		reviewInfo.setMi_email(memberInfo.getMi_email());	// 로그인한 회원의 이메일 저장
		
		if (wtype.equals("in")) {	// 리뷰 등록일 경우
			isSuccess = reviewProcSvc.reviewInsert(reviewInfo);
			failMsg = "질문 등록에 실패했습니다.";
			lnk = "review_write_list.review?";
		} else {	// 리뷰 수정일 경우
			isSuccess = reviewProcSvc.reviewUpdate(reviewInfo);
			failMsg = "질문 수정에 실패했습니다.";
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
		
		// 페이지 정보과 검색관련 정보들
		int cpage = 1;
		String ord = "";
		if (wtype.equals("up")) {
			cpage = Integer.parseInt(multi.getParameter("cpage"));
			ord = multi.getParameter("ord");
		}
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(true);	// redirect 방식으로 이동하도록 지정
		forward.setPath(lnk + "cpage=" + cpage + "&ord=" + ord);
		
		return forward;
	}
}
