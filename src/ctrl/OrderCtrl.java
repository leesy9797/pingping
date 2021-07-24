package ctrl;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import action.*;
import vo.*;

@WebServlet("*.ord")
public class OrderCtrl extends HttpServlet {
// 주문 관련 기능들에 대한 컨트롤(장바구니, 결제 등)
	private static final long serialVersionUID = 1L;
    public OrderCtrl() {
        super();
    }

    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// 사용자의 요청이 get이든 post든 모두 처리하는 메소드
		request.setCharacterEncoding("utf-8");
		// 해당 요청을 구분하기 위해 url을 잘라냄
		
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestUri.substring(contextPath.length());
		System.out.println(command);
		
		ActionForward forward = null;
		// 처리 후 이동할 view에 해당하는 링크와 이동방식을 얻기 위한 인스턴스
		
		Action action = null;
		// 기능별로 처리할 때 동일한 메소드로 작업하기 위해 선언된 인스턴스
		// 인터페이스이므로 인스턴스를 생성할 수는 없음(implements 받은 걸 이용 아래에서 인스턴스 생성)
		
		// 사용자의 요청에 따라 각기 다른 action을 작업
		switch (command) { 
		case "/cart_list.ord" : 		// 장바구니 폼 화면 처리
			action = new CartListAct();
			break;
		case "/cart_in_proc.ord" : 		// 장바구니 담기 처리
			action = new CartInProcAct();
			break;
		case "/cart_up_proc.ord" : 		// 장바구니 수정 처리
			action = new CartUpProcAct();
			break;
		case "/cart_del_proc.ord" : 	// 장바구니 삭제 처리
			action = new CartDelProcAct();
			break;
		case "/order_form.ord" : 		// 결제 폼 화면 처리
			action = new OrderFormAct();
			break;
		case "/order_list.ord" : 		// 주문배송내역 목록
			action = new OrderListAct();
			break;
		case "/order_detail.ord" : 		// 주문상세내역
			action = new OrderDetailAct();
			break;
		case "/order_proc.ord" : 		// 결제 처리
			action = new OrderProcAct();
			break;
		case "/purchase_comfirmed.ord" : 		// 구매 확정 처리
			action = new PurchaseComfirmedAct();
			break;
		}
		
		System.out.println("아아, 여기는 OrderCtrl");
		
		try {
			forward = action.execute(request, response);
			// 각 요청에 따른 Action을 implements한 클래스의 execute() 메소드를 실행
		} catch(Exception e) {
			e.printStackTrace();
		} 
		
		if (forward != null) {
			if (forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
				// RequestDispatcher를 통해 이동시키면 이동한 페이지의 URL이 변하지 않고,
				// 이동한 페이지로 현재 가지고 있는 request와 response 객체를 그대로 넘겨줌
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
