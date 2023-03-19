package kr.list.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.list.dao.ListDAO;
import kr.list.vo.ListVO;
import kr.util.FileUtil;

public class GameDeleteAction2 implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {// //�α��� ���� ���� ��� (�� ����� ������) 
			return "redirect:/member/loginForm.do";  //�α����ϴ� ������
		}
		
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_auth<9) {//�����ڷ� �α������� ���� ���        
			return "/WEB-INF/views/common/notice.jsp"; //�˷��ִ� ������ 
		}
		//�����ڷ� �α��� �� ���
		 
		int pro_num = Integer.parseInt(request.getParameter("pro_num"));
		ListDAO dao = ListDAO.getInstance();
		ListVO db_game = dao.getList(pro_num);
		dao.deleteGame(pro_num);
		
		FileUtil.removeFile(request, db_game.getPro_picture());
		 
		System.out.println("~~~new file~~~~");
		  
		return "redirect:/game/gameList.do";
	}

}