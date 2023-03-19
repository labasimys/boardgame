package kr.list.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;

public class GameAdminAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//�α��� ���� ���� ���  (�� ����� ������) (if�� �� ��)
			return "redirect:/member/loginForm.do"; //�α����ϴ� ������ ��� �����ֱ�.
		}
		
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_auth<9) {//�����ڷ� �α������� ���� ���
			return "/WEB-INF/views/common/notice.jsp";  //�˷��ִ� ������ ��� �����ֱ�.
		}
		//�����ڷ� �α����� ���
		return "/WEB-INF/views/game/admin.jsp";
	}

}

//�����ڷ� �α����ϸ� ���ӵ�� ���̴� ��� �����ְ� ���� ���� ��ư ������ admin.jsp(���� ���)������ ������.????
