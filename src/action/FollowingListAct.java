package action;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import svc.*;
import vo.*;

public class FollowingListAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<FollowInfo> followingList = new ArrayList<FollowInfo>();
		FollowInfo followInfo = new FollowInfo();
		
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		MemberInfo memberInfo = (MemberInfo)session.getAttribute("memberInfo");
		String loginEmail = "";
		if (memberInfo != null) {
			loginEmail = memberInfo.getMi_email();
			//System.out.println(loginEmail);
		}

		FollowListSvc followListSvc = new FollowListSvc();
		followInfo = followListSvc.getFollowInfo(loginEmail);
		followingList = followListSvc.getFollowingList(loginEmail);
		// �˻��� �ȷ��� ����� �޾ƿ�

		request.setAttribute("followInfo", followInfo);
		request.setAttribute("followingList", followingList);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/member/following_list.jsp");

		System.out.println("�ƾ�, ����� FollowingListAct");
		return forward;
	}
}
