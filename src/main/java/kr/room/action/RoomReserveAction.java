package kr.room.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.reserve.dao.ReserveDAO;
import kr.reserve.vo.ReserveVO;


public class RoomReserveAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//Map<String,String> mapAjax = new HashMap<String,String>();
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {
			 return "redirect:/member/loginForm.do";
		}else {
			request.setCharacterEncoding("utf-8");
			
			Integer room_num = Integer.parseInt(request.getParameter("room_num"));
			
			ReserveVO res = new ReserveVO();
			res.setMem_num(Integer.parseInt(request.getParameter("mem_num")));
			res.setRes_date(request.getParameter("res_date"));
			res.setRes_time(request.getParameter("res_time"));
			res.setRes_count(Integer.parseInt(request.getParameter("res_count")));
			res.setRoom_num(Integer.parseInt(request.getParameter("room_num")));
		
			ReserveDAO dao = ReserveDAO.getInstance();
			dao.insertReservation(res, room_num);
			
			request.setAttribute("res", res);
			request.setAttribute("room_num", room_num);
		}
		return "/WEB-INF/views/room/roomWrite.jsp";
	}
}