package ctrl;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.DupIDSvc;

@WebServlet("/uploadImg")
public class uploadImg extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public uploadImg() {
        super();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String uid = request.getParameter("uid");
		response.setContentType("test/tml); charset=utf-8");
		PrintWriter out = response.getWriter();
		
		try {
			DupIDSvc dupIDSvc = new DupIDSvc();
			int chkPoint = dupIDSvc.chkDupID(uid);
			out.println(chkPoint);
			
			
		} catch(Exception e) {
			e.printStackTrace();
			out.println(1);
		}
	}

}


