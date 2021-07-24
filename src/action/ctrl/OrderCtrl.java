package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import action.*;
import vo.*;

@WebServlet("*.ord")
public class OrderCtrl extends HttpServlet {
// �ֹ� ���� ��ɵ鿡 ���� ��Ʈ��(��ٱ���, ���� ��)
	private static final long serialVersionUID = 1L;
    public OrderCtrl() {
        super();
    }

    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// ������� ��û�� get�̵� post�� ��� ó���ϴ� �޼ҵ�
		request.setCharacterEncoding("utf-8");
		// �ش� ��û�� �����ϱ� ���� url�� �߶�
		
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestUri.substring(contextPath.length());
		System.out.println(command);
		
		ActionForward forward = null;
		// ó�� �� �̵��� view�� �ش��ϴ� ��ũ�� �̵������ ��� ���� �ν��Ͻ�
		
		Action action = null;
		// ��ɺ��� ó���� �� ������ �޼ҵ�� �۾��ϱ� ���� ����� �ν��Ͻ�
		// �������̽��̹Ƿ� �ν��Ͻ��� ������ ���� ����(implements ���� �� �̿� �Ʒ����� �ν��Ͻ� ����)
		
		// ������� ��û�� ���� ���� �ٸ� action�� �۾�
		switch (command) { 
		case "/cart_list.ord" : 		// ��ٱ��� �� ȭ�� ó��
			action = new CartListAct();
			break;
		case "/cart_in_proc.ord" : 		// ��ٱ��� ��� ó��
			action = new CartInProcAct();
			break;
		case "/cart_up_proc.ord" : 		// ��ٱ��� ���� ó��
			action = new CartUpProcAct();
			break;
		case "/cart_del_proc.ord" : 	// ��ٱ��� ���� ó��
			action = new CartDelProcAct();
			break;
		case "/order_form.ord" : 		// ���� �� ȭ�� ó��
			action = new OrderFormAct();
			break;
		case "/order_list.ord" : 		// �ֹ���۳��� ���
			action = new OrderListAct();
			break;
		case "/order_detail.ord" : 		// �ֹ��󼼳���
			action = new OrderDetailAct();
			break;
		case "/order_proc.ord" : 		// ���� ó��
			action = new OrderProcAct();
			break;
		case "/purchase_comfirmed.ord" : 		// ���� Ȯ�� ó��
			action = new PurchaseComfirmedAct();
			break;
		}
		
		System.out.println("�ƾ�, ����� OrderCtrl");
		
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
