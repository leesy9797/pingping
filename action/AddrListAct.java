package action;

import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import svc.*;
import vo.*;

public class AddrListAct implements Action {
   public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      ArrayList<MemberInfo> addrList = new ArrayList<MemberInfo>();
      
      request.setCharacterEncoding("utf-8");

      System.out.println("아아, 여기는 AddrListAct");
      
      HttpSession session = request.getSession();
      MemberInfo memberInfo = (MemberInfo)session.getAttribute("memberInfo");
      
      AddrListSvc addrListSvc = new AddrListSvc();
      addrList = addrListSvc.getAddrList(memberInfo.getMi_email());
      
      request.setAttribute("addrList", addrList);
      
      System.out.println("아아, 여기는 AddrListAct");
      
      ActionForward forward = new ActionForward();
      forward.setPath("addr_list.jsp");
      
      return forward;
   }
   
}