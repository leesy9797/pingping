package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class FreeUpAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		FreeInfo freeInfo = new FreeInfo();	// ������ ���� ������ ������ �ν��Ͻ�
		
		request.setCharacterEncoding("utf-8");
		int cpage = Integer.parseInt(request.getParameter("cpage"));
		int idx = Integer.parseInt(request.getParameter("idx"));	// ������ ���� idx
		
		// �˻�����(�˻���, ��/�Һз�, �귣��, ���ݴ�) ������Ʈ��
		String keyword	= request.getParameter("keyword");	// �˻���
		String ord 		= request.getParameter("ord");		// ��������
		// �˻� ���ǿ� ���� where��, order by�� ���� �ʿ����
		
		FreePageInfo pageInfo = new FreePageInfo();
		pageInfo.setCpage(cpage);		// ���� ������ ��ȣ
		pageInfo.setKeyword(keyword);	// �˻���
		pageInfo.setOrd(ord);			// ��������
		
		FreeUpSvc freeUpSvc = new FreeUpSvc();
		freeInfo = freeUpSvc.getFreeInfo(idx);
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("freeInfo", freeInfo);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/free/free_up_form.jsp");	// request�� ��� �ϹǷ� dispatch�������
		
		System.out.println("����� FreeUpAct");
		return forward;
	}
}
