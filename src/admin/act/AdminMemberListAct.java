package admin.act;

import javax.servlet.http.*;
import java.util.*;
import admin.svc.*;
import vo.*;

public class AdminMemberListAct implements action.Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<MemberInfo> memberList = new ArrayList<MemberInfo>();

		request.setCharacterEncoding("utf-8");
		int cpage = 1, psize = 15, bsize = 10, spage, epage, rcnt, pcnt;
		// ���� ������ ��ȣ, �� ���������� ������ �Խñ� ����, �� ��Ͽ��� ������ ������ ����, ��Ϻ� ����/���� ������ ��ȣ, ���ڵ� ����, ������ ������ ������ ���� �ʱ�ȭ

		if (request.getParameter("cpage") != null) {
			cpage = Integer.parseInt(request.getParameter("cpage"));
		}	// ���� ������ ��ȣ�� ������ ���������� ����ȯ�Ͽ� ����
		
		
		//�˻����� ������Ʈ��
		String email, nick, isactive, phone;
		String sage, eage, joinsdate, joinedate, gender, lastsdate, lastedate;
		
		email = request.getParameter("email");
		nick = request.getParameter("nick");
		isactive = request.getParameter("isactive");	// Ȱ��/Ż�𿩺�(y, n)
		phone = request.getParameter("phone");
		sage = request.getParameter("sage");			// ���� ��������
		eage = request.getParameter("eage");			// ���� ��������
		joinsdate = request.getParameter("joinsdate");	
		joinedate = request.getParameter("joinedate");	
		gender = request.getParameter("gender");	
		lastsdate = request.getParameter("lastsdate");	// ������ �α��� ���� ��������
		lastedate = request.getParameter("lastedate");	// ������ �α��� ���� ��������
		
		Calendar today = Calendar.getInstance();
		int syear = 0;
		int eyear = 0;
		System.out.println(sage);
		if (sage != null && !sage.equals("")) {
			if (eage == null || eage.equals("")) {
				eage = "100";
			}
			syear = today.get(Calendar.YEAR) - Integer.parseInt(sage) + 1;
		}
		if (eage != null && !sage.equals("")) {
			if (sage == null || sage.equals("")) {
				sage = "0";
			}
			eyear = today.get(Calendar.YEAR) - Integer.parseInt(eage) + 1;
		}		
		
		// �˻����ǿ� ���� where�� ����(�Ʒ��� �⺻����)
		String where = "where 1=1 ";
		
		if (!isEmpty(email))		where += " and mi_email like '%" + email + "%' ";
		else	email = "";
		if (!isEmpty(nick))			where += " and mi_nick like '%" + nick + "%' ";
		else	nick = "";
		if (!isEmpty(isactive))		where += " and mi_isactive = '" + isactive + "' ";
		else	isactive = "";
		if (!isEmpty(phone))		where += " and mi_phone like '%" + phone + "%' ";
		else	phone = "";
		if (!isEmpty(sage))			where += " and left(mi_birth, 4) <= '" + syear + "' ";
		else	sage = "";
		if (!isEmpty(eage))			where += " and left(mi_birth, 4) >= '" + eyear + "' ";
		else	eage = "";
		if (!isEmpty(joinsdate))	where += " and mi_joindate >= '" + joinsdate + " 00:00:00' ";
		else	joinsdate = "";
		if (!isEmpty(joinedate))	where += " and mi_joindate <= '" + joinedate + " 23:59:59' ";
		else	joinedate = "";
		if (!isEmpty(gender))		where += " and mi_gender = '" + gender + "' ";
		else	gender = "";
		if (!isEmpty(lastsdate))	where += " and mi_lastlogin >= '" + lastsdate + " 00:00:00' ";
		else	lastsdate = "";
		if (!isEmpty(lastedate))	where += " and mi_lastlogin <= '" + lastedate + " 23:59:59' ";
		else	lastedate = "";
		
		
		// ��������(�Ǹŷ�(�α��) - ��������, ���� - ��������/��������, ��� - ��������(�⺻��), ��ǰ�� - ��������, ���� - ��������) ������Ʈ��
		String ord = request.getParameter("ord");
		if (ord == null || ord.equals("")) {
			ord = "joindated";	// ��� �������� ������ �⺻��
		}
		// �������ǿ� ���� order by�� ����
		String orderBy = " order by mi_" + ord.substring(0, ord.length() - 1) + 	// idd��� desc�� �ǹ��ϴ� d�� ���� ������
			(ord.charAt(ord.length() - 1) == 'a' ? " asc" : " desc");	// idd��  desc�� �ǹ��ϴ� d�� ����(���� ������ ����)
		
		AdminMemberListSvc adminMemberListSvc = new AdminMemberListSvc();
		rcnt = adminMemberListSvc.getMemberCount(where);	// �˻��� ��ǰ�� �� ����(������ ������ ���ϱ� ���� �ʿ�)		
		
		pcnt = rcnt / psize;
		if (rcnt % psize > 0)	pcnt++;		// ��ü ������ ��
		spage = (cpage - 1) / bsize * bsize + 1;	// ��� ���� ������ ��ȣ
		epage = spage + bsize - 1;
		if (epage > pcnt)	epage = pcnt;			// ��� ���� ������ ��ȣ
		
		
		MemberPageInfo pageInfo = new MemberPageInfo();	// ����¡�� �ʿ��� �������� ������ �ν��Ͻ�
		// ����¡�� �ʿ��� ������
		pageInfo.setCpage(cpage);		// ���� ������ ��ȣ
		pageInfo.setRcnt(rcnt);			// ��ü �Խñ� ����
		pageInfo.setPcnt(pcnt);			// ��ü ������ ����
		pageInfo.setSpage(spage);		// ��� ���� ������ ��ȣ
		pageInfo.setEpage(epage);		// ��� ���� ������ ��ȣ 
		pageInfo.setPsize(psize);		// ������ ũ��
		pageInfo.setBsize(bsize);		// ��� ũ��
		 
		// �˻� ���� ������
		pageInfo.setEmail(email);	
		pageInfo.setNick(nick);	
		pageInfo.setPhone(phone);	
		pageInfo.setIsactive(isactive);	
		pageInfo.setSage(sage);	
		pageInfo.setEage(eage);		
		pageInfo.setJoinsdate(joinsdate);		// �������� �� ������
		pageInfo.setJoinedate(joinedate);		// �������� �� ������
		pageInfo.setGender(gender);		
		pageInfo.setLastsdate(lastsdate);		// ������ �α��� ���� �� ������	
		pageInfo.setLastedate(lastedate);		// ������ �α��� ���� �� ������
		pageInfo.setOrd(ord);
		

		memberList = adminMemberListSvc.getMemberList(where, orderBy, cpage, psize);
		// �� ������(cpage)���� ������ �˻��� ��ǰ ����� �޾ƿ�	
		
		
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("memberList", memberList);

		System.out.println("�ƾ�, ����� AdminMemberListAct");
		
		ActionForward forward = new ActionForward();
		forward.setPath("/admin/member/member_list.jsp");
		// forward �ν��Ͻ��� setPath() �޼ҵ带 �̿��Ͽ� forward �ν��Ͻ��� path ��������� ���� ����
		
		return forward;
	}
	
	private boolean isEmpty(String str) {	// �Ű������� � ���̵� ����ִ��� ���θ� �˻��ϴ� �޼ҵ�
		boolean empty = true;
		
		if (str != null && !str.equals("")) {
			empty = false;
		}
		
		return empty;
	}
	
}
