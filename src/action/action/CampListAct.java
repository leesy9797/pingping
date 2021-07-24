package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class CampListAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<CampingInfo> campList = new ArrayList<CampingInfo>();
		ArrayList<FollowInfo> followList = new ArrayList<FollowInfo>();
		ArrayList<GoodInfo> goodList = new ArrayList<GoodInfo>();
		ArrayList<ScrapInfo> scrapList = new ArrayList<ScrapInfo>();
		
		HttpSession session = request.getSession();
		MemberInfo memberInfo = (MemberInfo)session.getAttribute("memberInfo");
		String loginEmail = "";
		if (memberInfo != null) {
			loginEmail = memberInfo.getMi_email();
			System.out.println(loginEmail);
		}
		
		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 12, bsize = 10, spage, epage, rcnt, pcnt;
		// ���� ������ ��ȣ, �� ���������� ������ �Խñ� ����, �� ��Ͽ��� ������ ������ ����, ��Ϻ� ����/���� ������ ��ȣ, ���ڵ� ����, ������ ������ ������ ���� �ʱ�ȭ
		
		if (request.getParameter("cpage") != null) {
			cpage = Integer.parseInt(request.getParameter("cpage"));
		}	// ���� ������ ��ȣ�� ������ ���������� ����ȯ�Ͽ� ����
		if (request.getParameter("psize") != null) {
			psize = Integer.parseInt(request.getParameter("psize"));
		}	// ���� ������ ��ȣ�� ������ ���������� ����ȯ�Ͽ� ����
		
		// �˻�����(�ȷ���) ������Ʈ��
		//String following = "";
		//following = request.getParameter("following");	// �ȷ���
		
		// �˻����ǿ� ���� where�� ����(�Ʒ��� �⺻����)
		//String where = "";
		
		//if (!isEmpty(following))	where += " and a.pi_name like '%" + keyword + "%' ";
		//else	keyword = "";
		
		// ��������(�ֽż�(�⺻��_cr_idx) - ��������(d), �α��(��ũ����_cr_scrap) - ��������(d)) ������Ʈ��
		String ord = request.getParameter("ord");
		if (ord == null || ord.equals("")) {
			ord = "idx";	// ��� �������� ������ �⺻��
		}
		
		// �������ǿ� ���� order by�� ����
		String orderBy = " order by a.cr_" + ord.substring(0, ord.length()) + " desc";	
		
		CampListSvc campListSvc = new CampListSvc();
		ChkFGSSvc chkFGSSvc = new ChkFGSSvc();
		rcnt = campListSvc.getCampCount();	// �˻��� ��ǰ�� �� ����(������ ������ ���ϱ� ���� �ʿ�)
		campList = campListSvc.getCampList(orderBy, cpage, psize);
		// �� ������(cpage)���� ������ �˻��� ��ǰ ����� �޾ƿ�
		followList = chkFGSSvc.getFollowList(loginEmail);
		String kind = "c";
		goodList = chkFGSSvc.getGoodList(kind, loginEmail);
		scrapList = chkFGSSvc.getScrapList(kind, loginEmail);
		// �α��� ���� ȸ���� �ȷο� ����� �޾ƿ�
		
		pcnt = rcnt / psize;
		if (rcnt % psize > 0)	pcnt++;				// ��ü ������ ��
		spage = (cpage - 1) / bsize * bsize + 1;	// ��� ���� ������ ��ȣ
		epage = spage + bsize - 1;
		if (epage > pcnt)	epage = pcnt;			// ��� ���� ������ ��ȣ
		
		CampPageInfo pageInfo = new CampPageInfo();	// ����¡�� �ʿ��� �������� ������ �ν��Ͻ�
		// ����¡�� �ʿ��� ������
		pageInfo.setCpage(cpage);		// ���� ������ ��ȣ
		pageInfo.setRcnt(rcnt);			// ��ü �Խñ� ����
		pageInfo.setPcnt(pcnt);			// ��ü ������ ����
		pageInfo.setSpage(spage);		// ��� ���� ������ ��ȣ
		pageInfo.setEpage(epage);		// ��� ���� ������ ��ȣ 
		pageInfo.setPsize(psize);		// ������ ũ��
		pageInfo.setBsize(bsize);		// ��� ũ��
		pageInfo.setOrd(ord);			// ��������	
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("campList", campList);
		request.setAttribute("followList", followList);
		request.setAttribute("goodList", goodList);
		request.setAttribute("scrapList", scrapList);
		System.out.println("�ƾ�, ����� CampListAct");
		
		ActionForward forward = new ActionForward();
		forward.setPath("/camping/camp_list.jsp");
		// forward �ν��Ͻ��� setPath() �޼ҵ带 �̿��Ͽ� forward �ν��Ͻ��� path ��������� ���� ����
		
		return forward;
	}
	
	private boolean isEmpty(String str) {
	// �Ű������� � ���̵� ����ִ��� ���θ� �˻��ϴ� �޼ҵ� -> �˻� ���ؼ� �Ⱦ�
		boolean empty = true;
		
		if (str != null && !str.equals("")) {
			empty = false;
		}
		
		return empty;
	}
}
