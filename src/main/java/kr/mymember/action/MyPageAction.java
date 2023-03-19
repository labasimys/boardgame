package kr.mymember.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.mycartcount.dao.MyCartCountDAO;
import kr.myinqu.dao.MyinquDAO;
import kr.mymember.dao.MemberDAO;
import kr.mymember.vo.MemberVO;
import kr.myrev.dao.MyrevDAO;

public class MyPageAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = 
				 (Integer)session.getAttribute("user_num");
		if(user_num == null) {//�α����� ���� ���� ���
			return "redirect:/member/loginForm.do";//�α��� �� ��η� �Ŀ� ��ü
		}
		
		//�α����� �� ���
		MemberDAO dao = MemberDAO.getInstance();
		MemberVO member = dao.getMember(user_num);
		
		//���Ǳ� ��
		MyinquDAO dao1 = MyinquDAO.getInstance();
		int count1 = dao1.getInQuCount(user_num);
		//���� ��� �� 
		MyrevDAO dao2 = MyrevDAO.getInstance();
		int count2 = dao2.getRevCount(user_num);
		//��ٱ��� ��
		MyCartCountDAO dao3 = MyCartCountDAO.getInstance();
		int count3 = dao3.getMyCartCount(user_num);
		
		
		
		request.setAttribute("member", member);
		request.setAttribute("count1", count1);
		request.setAttribute("count2", count2);
		request.setAttribute("count3", count3);
		return "/WEB-INF/views/mymember/myPage.jsp";
	}

}
