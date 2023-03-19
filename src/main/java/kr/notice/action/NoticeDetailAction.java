package kr.notice.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.notice.dao.NoticeDAO;
import kr.notice.vo.NoticeVO;
import kr.util.StringUtil;
 
public class NoticeDetailAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//�۹�ȣ ��ȯ
		int noti_num = Integer.parseInt(request.getParameter("noti_num"));
				
		NoticeDAO dao = NoticeDAO.getInstance();
				
		//��ȸ�� ����
		dao.updateReadcount(noti_num);
				
		NoticeVO notice = dao.getNotice(noti_num);
				
		//HTML �±׸� ������� ����
		notice.setNoti_title(StringUtil.useNoHtml(notice.getNoti_title()));
				
		//HTML �±׸� ������� �����鼭 �ٹٲ� ó��
		notice.setNoti_content(StringUtil.useBrNoHtml(notice.getNoti_content()));
			
		request.setAttribute("notice", notice);
				
		return "/WEB-INF/views/notice/noticeDetail.jsp";
	}
}
