package kr.inquiry.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.inquiry.dao.InquiryDAO;
import kr.inquiry.vo.InquiryVO;
import kr.util.StringUtil;

public class InquiryReplyFormAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		
		Integer user_num = (Integer)session.getAttribute("user_num");
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		
		if(user_num == null) {//�α����� ���� ���� ���
			return "redirect:/member/loginForm.do";
		}
	
		if(user_auth != 9) {
			return "/WEB-INF/views/inquiry/inquiryList.jsp";
		}
		
		int inqu_num = Integer.parseInt(request.getParameter("inqu_num"));
		
		InquiryDAO dao = InquiryDAO.getInstance();
		InquiryVO inquiry = dao.getInquiry(inqu_num);

		//�α����� �Ǿ� �ְ� �α����� ȸ����ȣ�� �ۼ��� ȸ����ȣ�� ��ġ
		
		//���� ū ����ǥ�� ������ input �±׿� �����͸� ǥ���� ��
		//�������� �Ͼ�� ������ " -> &quot; �� ��ȯ
		inquiry.setInqu_title(StringUtil.parseQuot(inquiry.getInqu_title()));
		
		request.setAttribute("inquiry", inquiry);

		return "/WEB-INF/views/inquiry/inquiryReplyForm.jsp";
	}
}
