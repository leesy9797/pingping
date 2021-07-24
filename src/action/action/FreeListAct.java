package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class FreeListAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<FreeInfo> freeList = new ArrayList<FreeInfo>();

		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 10, bsize = 10, spage, epage, rcnt, pcnt;
		// ���� ������ ��ȣ, �� ���������� ������ �Խñ� ����, �� ��Ͽ��� ������ ������ ����, ��Ϻ� ����/���� ������ ��ȣ, ���ڵ� ����, ������ ������ ������ ���� �ʱ�ȭ

		if (request.getParameter("cpage") != null) {
			cpage = Integer.parseInt(request.getParameter("cpage"));
		}	// ���� ������ ��ȣ�� ������ ���������� ����ȯ�Ͽ� ����
		
		// �˻�����(�˻���) ������Ʈ��
		String keyword	= request.getParameter("keyword");	// �˻���
		
		// ��������(�ֽż�(�⺻��) - ��������, �α��(���ƿ��) - ��������) ������Ʈ��
		String ord = request.getParameter("ord");
		if (ord == null || ord.equals("")) {
			ord = "lastdated";	// ��� �������� ������ �⺻��
		}
		
		// �˻� ���ǿ� ���� where�� ����
		String where = "";
		if (!isEmpty(keyword))	where += " and bf_title like '%" + keyword + "%' or bf_content like '%" + keyword + "%' ";
		else	keyword = "";
		
		// �������ǿ� ���� order by�� ����
		String orderBy = "";
		if (ord.equals("lastdated"))	orderBy = " order by last_date " + " desc";
		else							orderBy = "and bf_good >= 100 order by bf_good " + " desc";

		FreeListSvc freeListSvc = new FreeListSvc();
		rcnt = freeListSvc.getFreeCount(where);	// �˻��� ��ǰ�� �� ����(������ ������ ���ϱ� ���� �ʿ�)
		freeList = freeListSvc.getFreeList(where, orderBy, cpage, psize);
		// �� ������(cpage)���� ������ �˻��� ��ǰ ����� �޾ƿ�
		
		pcnt = rcnt / psize;
		if (rcnt % psize > 0)	pcnt++;		// ��ü ������ ��
		spage = (cpage - 1) / bsize * bsize + 1;	// ��� ���� ������ ��ȣ
		epage = spage + bsize - 1;
		if (epage > pcnt)	epage = pcnt;			// ��� ���� ������ ��ȣ
		
		FreePageInfo pageInfo = new FreePageInfo();	// ����¡�� �ʿ��� �������� ������ �ν��Ͻ�
		// ����¡�� �ʿ��� ������
		pageInfo.setCpage(cpage);		// ���� ������ ��ȣ
		pageInfo.setRcnt(rcnt);			// ��ü �Խñ� ����
		pageInfo.setPcnt(pcnt);			// ��ü ������ ����
		pageInfo.setSpage(spage);		// ��� ���� ������ ��ȣ
		pageInfo.setEpage(epage);		// ��� ���� ������ ��ȣ 
		pageInfo.setPsize(psize);		// ������ ũ��
		pageInfo.setBsize(bsize);		// ��� ũ��
		
		// �˻� ���� ������
		pageInfo.setKeyword(keyword);	// �˻���
		pageInfo.setOrd(ord);			// ��������
				
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("freeList", freeList);		
		
		ActionForward forward = new ActionForward();
		forward.setPath("/free/free_list.jsp");
		// forward �ν��Ͻ��� setPath() �޼ҵ带 �̿��Ͽ� forward �ν��Ͻ��� path ��������� ���� ����

		System.out.println("����� FreeListAct");
		return forward;
	}

	private boolean isEmpty(String str) {	// �Ű������� � ���̵� ����ִ��� ���θ� �˻��ϴ� �޼ҵ�
		boolean empty = true;
		if (str != null && !str.equals(""))	empty = false;
		return empty;
	}
}
