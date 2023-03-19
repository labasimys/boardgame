package kr.room.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.room.dao.RoomDAO;
import kr.room.vo.RoomVO;
import kr.util.FileUtil;

public class ReserveRoomAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		
		 HttpSession session = request.getSession(); 
		 Integer user_num = (Integer)session.getAttribute("user_num"); 
		 if(user_num == null) { //로그인 되지 않은경우 
			 return "redirect:/member/loginForm.do"; }
		 
		 Integer user_auth = (Integer)session.getAttribute("user_auth");
		 if(user_auth<9) { //관리자로 로그인하지 않은 경우 
			 return "/WEB-INF/views/common/notice.jsp"; }
		 
		//관리자로 로그인 한 경우
		MultipartRequest multi = FileUtil.createFile(request);

		RoomVO room = new RoomVO();
		room.setRoom_name(multi.getParameter("name"));
		room.setRoom_size(Integer.parseInt(multi.getParameter("size")));
		room.setRoom_detail(multi.getParameter("detail"));
		room.setRoom_detail2(multi.getParameter("detail2"));
		room.setPhoto1(multi.getFilesystemName("photo1"));
		room.setPhoto2(multi.getFilesystemName("photo2"));
		room.setPhoto3(multi.getFilesystemName("photo3"));

		RoomDAO dao = RoomDAO.getInstance();
		dao.insertRoom(room);

		response.addHeader("Refresh", "2;url:write.do");

		request.setAttribute("accessMsg", "등록되었습니다.");
		request.setAttribute("accessUrl", "write.do");

		return "/WEB-INF/views/room/write.jsp";
	}

}
