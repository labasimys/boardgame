package kr.list.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.list.dao.ListDAO;
import kr.list.vo.ListVO;
import kr.util.FileUtil;

public class GameWriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//�α��� ���� ���� ���
			return "redirect:/member/loginForm.do";
		}
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_auth<9) {//�����ڷ� �α������� ���� ���
			return "/WEB-INF/views/common/notice.jsp";
		}
		//�����ڷ� �α����� ���
		MultipartRequest multi = FileUtil.createFile(request);
		ListVO game = new ListVO();
		game.setPro_name(multi.getParameter("pro_name"));
		game.setPro_price(Integer.parseInt(multi.getParameter("pro_price")));
		game.setPro_picture(multi.getFilesystemName("pro_picture"));
		game.setPro_level(multi.getParameter("pro_level"));
		game.setPro_count(Integer.parseInt(multi.getParameter("pro_count")));
		game.setPerson(Integer.parseInt(multi.getParameter("person")));
		game.setExplanation(multi.getParameter("explanation"));
		/* game.setPro_status(Integer.parseInt(multi.getParameter("pro_status"))); */
		
		ListDAO dao = ListDAO.getInstance();
		dao.insertGame(game);
		
		//refresh ������ ���� ����� �߰�	???????????
		response.addHeader("Refresh", "2;url=gameList.do");
		
		request.setAttribute("accessMsg", "���������� ��ϵǾ����ϴ�.");
		request.setAttribute("accessUrl", "gameList.do");
		
		return "/WEB-INF/views/common/notice.jsp";		//?????
	}

}
