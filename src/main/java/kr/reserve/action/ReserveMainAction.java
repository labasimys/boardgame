package kr.reserve.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.room.dao.RoomDAO;
import kr.room.vo.RoomVO;

public class ReserveMainAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		RoomDAO roomDao = RoomDAO.getInstance();
		List<RoomVO> roomList = roomDao.getListRoom();
		
		request.setAttribute("roomList", roomList);
		
		return "/WEB-INF/views/reserve/reserveMain.jsp";
	}

}
