package action;

import javax.servlet.http.*;
import java.util.*;
import svc.*;
import vo.*;

public class MonthViewAct implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		int idx = Integer.parseInt(request.getParameter("idx"));
		int cpage = Integer.parseInt(request.getParameter("cpage"));
		
		String ord = request.getParameter("ord");			// ��������
		
		ArrayList<MonthReplyInfo> replyList = (ArrayList<MonthReplyInfo>)request.getAttribute("replyList");
		
		MonthViewSvc monthViewSvc = new MonthViewSvc();
		MonthlyInfo monthInfo = monthViewSvc.getMonthInfo(idx);
		// Ư�� �Խñ��� �����͵��� NoticeInfo�� �ν��Ͻ� article�� ����
		
		int rcnt = monthViewSvc.getMonthReplyCount();	
		replyList = monthViewSvc.getMonthReplyList(idx);
	    
		request.setAttribute("monthInfo", monthInfo);
		request.setAttribute("replyList", replyList);
		// �̵��� �������� request��ü�� �Խñ� ��ü�� ��� �Ѱ���(dispatch������� �̵��ϹǷ� request ��밡��)
		
		System.out.println("�ƾ�, ����� MonthViewAct");
		
		ActionForward forward = new ActionForward();
		forward.setPath("/monthly/month_view.jsp");
		// forward�ν��Ͻ��� redirect��������� ���� true�� �������� �ʾ����Ƿ� dispatch������� �̵���
		
		return forward;
	}
}
