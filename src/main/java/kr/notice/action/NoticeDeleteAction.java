package kr.notice.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.notice.dao.NoticeDAO;
import kr.notice.vo.NoticeVO;
import kr.util.FileUtil;
  
public class NoticeDeleteAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		
		Integer user_num = (Integer)session.getAttribute("user_num");
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		int noti_num = Integer.parseInt(request.getParameter("noti_num"));
		NoticeDAO dao = NoticeDAO.getInstance();
		NoticeVO notice = dao.getNotice(noti_num);
		
		if(user_auth != 9) {
			//로그인한 회원번호와 작성자 회원번호가 불일치
			return "/WEB-INF/views/notice/noticeList.jsp";
		}
		
		dao.deleteNotice(noti_num);
		
		//파일 삭제
		FileUtil.removeFile(request, notice.getNoti_file());
		
		return "redirect:/notice/noticeList.do";
	}
}
