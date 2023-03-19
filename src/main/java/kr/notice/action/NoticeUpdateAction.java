package kr.notice.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.notice.dao.NoticeDAO;
import kr.notice.vo.NoticeVO;
import kr.util.FileUtil;
 
public class NoticeUpdateAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		
		Integer user_num = (Integer)session.getAttribute("user_num");
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		
		if(user_num == null) {//�α����� ���� ���� ���
			return "redirect:/member/loginForm.do";
		}
		
		MultipartRequest multi = FileUtil.createFile(request);
		int noti_num = Integer.parseInt(multi.getParameter("noti_num"));
		String noti_file = multi.getFilesystemName("noti_file");
		
		NoticeDAO dao = NoticeDAO.getInstance();
		
		//������ ������ ȣ��
		NoticeVO notice = dao.getNotice(noti_num);
		
		if(user_auth != 9) {
			//�α����� ȸ����ȣ�� �ۼ��� ȸ����ȣ�� ����ġ
			FileUtil.removeFile(request, noti_file);//���ε�� ������ ������ ���� ����
			return "/WEB-INF/views/notice/noticeList.jsp";
		}
		
		//�α����� ȸ����ȣ�� �ۼ��� ȸ����ȣ�� ��ġ
		NoticeVO noticeVO = new NoticeVO();
		
		noticeVO.setNoti_num(noti_num);
		noticeVO.setNoti_title(multi.getParameter("noti_title"));
		noticeVO.setNoti_content(multi.getParameter("noti_content"));
		noticeVO.setNoti_file(noti_file);
		
		dao.updateNotice(noticeVO);
		
		if(noti_file != null) {
			//�����Ϸ� ��ü�� �� ���� ���� ����
			FileUtil.removeFile(request, notice.getNoti_file());
		}
		return "redirect:/notice/noticeDetail.do?noti_num=" + noti_num;
	}
}
