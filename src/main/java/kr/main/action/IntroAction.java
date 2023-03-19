package kr.main.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.notice.dao.NoticeDAO;
import kr.notice.vo.NoticeVO;

public class IntroAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//게시판 목록
		NoticeDAO noticeDao = NoticeDAO.getInstance();
		List<NoticeVO> noticeList = noticeDao.getNoticeList(1, 5);
				
		request.setAttribute("noticeList", noticeList);
				
		return "/WEB-INF/views/main/intro.jsp";
	}

}
