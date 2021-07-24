package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import action.*;
import vo.*;

@WebServlet("*.mem")
public class MemberCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public MemberCtrl() {
        super();
    }
    
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("utf-8");
		
		// �ش� ��û�� �����ϱ� ���� url�� �߶�
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestUri.substring(contextPath.length());
		//System.out.println(command);
		ActionForward forward = null;
		
		Action action = null;
		
		// ������� ��û�� ���� ���� �ٸ� action�� �۾�
		switch (command) {
			case "/member/search_id.mem" : 	// ���̵� ã��
				action = new SearchIdAct();
				break;
			case "/member/chk_email.mem" : 	// ��й�ȣ �缳��(���̵� ���翩�� Ȯ��)
				action = new ChkEmailAct();
				break;
			case "/member/reset_pwd.mem" : 	// ��й�ȣ �缳��(��������)
				action = new ResetPwdAct();
				break;
			case "/member/set_pwd.mem" : 	// ��й�ȣ �缳��(�缳��)
				action = new SetPwdAct();
				break;
			case "/member/addr_list.mem" : 		// �ּҷ� ���
				action = new AddrListAct();
				break;
			case "/member/addr_in_form.mem" : 	// �ּҷ� �����
				action = new AddrInFormAct();
				break;
			case "/member/addr_up_form.mem" : 	// �ּҷ� ������
				action = new AddrUpFormAct();
				break;
			case "/member/addr_del.mem" : 		// �ּҷ� ����
				action = new AddrDelAct();
				break;
			case "/member/addr_in_proc.mem" : 	// �ּҷ� ��� ó��
				action = new AddrInProcAct();
				break;
			case "/member/addr_up_proc.mem" : 	// �ּҷ� ����ó��
				action = new AddrUpProcAct();
				break;
			case "/member/point_list.mem" :   		// ����Ʈ ���� ���
			    action = new PointListAct();		
			    break;
			case "/member/follower_list.mem" :    	// �ȷο� ���
				action = new FollowerListAct();
				break;
			case "/member/following_list.mem" : 	// �ȷ��� ���
				action = new FollowingListAct();
				break;
			case "/member/mypage.mem" : 	// �ȷ��� ���
				//action = new FollowingListAct();
				break;

		}

		System.out.println("�ƾ�, ����� MemberCtrl");
		
		try {
			forward = action.execute(request, response);
			// �� ��û�� ���� Action�� implements�� Ŭ������ execute() �޼ҵ带 ����
		} catch(Exception e) {
			e.printStackTrace();
		} 
		
		if (forward != null) {
			if (forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
				// RequestDispatcher�� ���� �̵���Ű�� �̵��� �������� URL�� ������ �ʰ�,
				// �̵��� �������� ���� ������ �ִ� request�� response ��ü�� �״�� �Ѱ���
			}
		}
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}
