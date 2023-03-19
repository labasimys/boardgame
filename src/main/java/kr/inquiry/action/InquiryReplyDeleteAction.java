package kr.inquiry.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.inquiry.dao.InquiryDAO;
import kr.inquiry.vo.InquiryVO;
import kr.util.FileUtil;

public class InquiryReplyDeleteAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//�α����� ���� ���� ���
			return "redirect:/member/loginForm.do";
		}
		
		//�α��� �� ���
		int inqu_num = Integer.parseInt(request.getParameter("inqu_num"));
		InquiryDAO dao = InquiryDAO.getInstance();
		InquiryVO inquiry = dao.getInquiry(inqu_num);
		
		if(user_num != inquiry.getMem_num()) {
			//�α����� ȸ����ȣ�� �ۼ��� ȸ����ȣ�� ����ġ
			return "/WEB-INF/views/inquiry/inquiryList.jsp";
		}
		
		//�α����� ȸ����ȣ�� �ۼ��� ȸ����ȣ�� ��ġ
		dao.deleteInquiryReply(inqu_num);
		
		//���� ����
		FileUtil.removeFile(request, inquiry.getInqu_file());
		
		return "redirect:/inquiry/inquiryList.do";
	}
}
