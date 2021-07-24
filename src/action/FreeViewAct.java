package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class FreeViewAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		int idx = Integer.parseInt(request.getParameter("idx"));
		int cpage = Integer.parseInt(request.getParameter("cpage"));
		String keyword	= request.getParameter("keyword");
		String ord = request.getParameter("ord");			// ��������
		
		FreeViewSvc freeViewSvc = new FreeViewSvc();
		FreeInfo freeInfo = freeViewSvc.getFreeInfo(idx);
		// Ư�� �Խñ��� �����͵��� NoticeInfo�� �ν��Ͻ� article�� ����
		
		FreePageInfo pageInfo = new FreePageInfo();	// ����¡�� �ʿ��� �������� ������ �ν��Ͻ�
		pageInfo.setCpage(cpage);		// ���� ������ ��ȣ
		pageInfo.setKeyword(keyword);	// �˻���
		pageInfo.setOrd(ord);			// ��������
				
		request.setAttribute("pageInfo", pageInfo);	    
		request.setAttribute("freeInfo", freeInfo);
		// �̵��� �������� request��ü�� �Խñ� ��ü�� ��� �Ѱ���(dispatch������� �̵��ϹǷ� request ��밡��)
		
		ActionForward forward = new ActionForward();
		forward.setPath("/free/free_view.jsp");
		// forward�ν��Ͻ��� redirect��������� ���� true�� �������� �ʾ����Ƿ� dispatch������� �̵���
		
		return forward;
	}
}
