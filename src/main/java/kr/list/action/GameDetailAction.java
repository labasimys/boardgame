package kr.list.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.list.dao.ListDAO;
import kr.list.vo.ListVO;

public class GameDetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 상품번호 반환
		
		  int pro_num = Integer.parseInt(request.getParameter("pro_num"));
		  
		  ListDAO dao = ListDAO.getInstance(); 
		  ListVO detail = dao.getList(pro_num);
		 
		  request.setAttribute("detail", detail);
		 
		
		return "/WEB-INF/views/game/detail.jsp";
	}

}

// 게임 사진 누르면 그 게임에대한 상세페이지로 이동시켜주는 action.