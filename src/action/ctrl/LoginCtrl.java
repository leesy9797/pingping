package ctrl;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;	
import svc.*;
import vo.*;

@WebServlet("/login")
public class LoginCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public LoginCtrl() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		String email = request.getParameter("email");
		String pwd = request.getParameter("pwd");
		
		String url = request.getParameter("url");
		// 로그인 후에 이동할 주소로 없을 경우에는 빈 문자열로 받음
		
		if (url.equals("")) {
			url = "index.jsp";
		} // 로그인 후에 이동할 주소로 없을 경우에는 index 화면으로 이동하도록 주소를 지정
		
		LoginSvc loginSvc = new LoginSvc();
		
		MemberInfo memberInfo = loginSvc.getMemberInfo(email, pwd);
		
		HttpSession session = request.getSession();
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();

		System.out.println("아아, 여기는 LoginCtrl");
		
		if (memberInfo != null) {	// 로그인 성공시			
			session.setAttribute("memberInfo", memberInfo);
			response.sendRedirect(url);
			
		} else {	// 로그인 실패시
			out.println("<script>");
			out.println("alert('로그인에 실패하였습니다.');");
			out.println("history.back();");
			out.println("</script>");
			
		}
		
	}

}
