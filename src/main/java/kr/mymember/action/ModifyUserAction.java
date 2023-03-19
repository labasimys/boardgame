package kr.mymember.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.mymember.dao.MemberDAO;
import kr.mymember.vo.MemberVO;

public class ModifyUserAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = 
				(Integer)session.getAttribute("user_num");
		if(user_num==null) {//�α��� ���� ���� ���
			return "redirect:/member/loginForm.do";
		}
		
		//�α��� �� ���
		//���۵� ������ ���ڵ� ó��
		request.setCharacterEncoding("utf-8");
		//�ڹٺ�(VO)�� ����, ���۵� �����͸� ����
		MemberVO member = new MemberVO();
		member.setMem_num(user_num);//ȸ����ȣ
		member.setMem_name(request.getParameter("name"));
		member.setMem_phone(request.getParameter("phone"));
		member.setMem_email(request.getParameter("email"));
		member.setMem_zipcode(request.getParameter("zipcode"));
		member.setMem_address1(request.getParameter("address1"));
		member.setMem_address2(request.getParameter("address2"));
		
		MemberDAO dao = MemberDAO.getInstance();
		dao.updateMember(member);
		
		return "/WEB-INF/views/mymember/modifyUser.jsp";
	}

}
