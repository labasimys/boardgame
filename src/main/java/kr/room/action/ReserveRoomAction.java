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
		 if(user_num == null) { //�α��� ���� ������� 
			 return "redirect:/member/loginForm.do"; }
		 
		 Integer user_auth = (Integer)session.getAttribute("user_auth");
		 if(user_auth<9) { //�����ڷ� �α������� ���� ��� 
			 return "/WEB-INF/views/common/notice.jsp"; }
		 
		//�����ڷ� �α��� �� ���
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

		request.setAttribute("accessMsg", "��ϵǾ����ϴ�.");
		request.setAttribute("accessUrl", "write.do");

		return "/WEB-INF/views/room/write.jsp";
	}

}
