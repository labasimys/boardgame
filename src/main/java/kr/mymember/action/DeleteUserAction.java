package kr.mymember.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.mymember.dao.MemberDAO;
import kr.mymember.vo.MemberVO;
import kr.util.FileUtil;

public class DeleteUserAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = 
				(Integer)session.getAttribute("user_num");
		if(user_num==null) {//�α����� ���� ���� ���
			return "redirect:/member/loginForm.jsp";
		}
		
		//�α��� �� ���
		//���۵� ������ ���ڵ� ó��
		request.setCharacterEncoding("utf-8");
		//���۵� ������ ��ȯ
		//���� ��й�ȣ
		String passwd = request.getParameter("passwd");
		//���� �α����� ���̵�
		String user_id = (String)session.getAttribute("user_id");
		
		
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberVO member = dao.checkMember2(user_id);
		boolean check = false;
		//��й�ȣ ��ġ ���� üũ
		check = member.isCheckedPassword(passwd);
		
		
		if(check) {//���� ����
			//ȸ������ ����
			dao.deleteMember(user_num);
			//������ ���� ����
			FileUtil.removeFile(request, member.getMem_photo());
			//�α׾ƿ�
			session.invalidate();
		}
		
		request.setAttribute("check", check);	
		
		return "/WEB-INF/views/mymember/deleteUser.jsp";
	}

}
