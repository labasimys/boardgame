package kr.inquiry.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.inquiry.dao.InquiryDAO;
import kr.inquiry.vo.InquiryVO;
import kr.util.StringUtil;

public class InquiryReplyDetailAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//�۹�ȣ ��ȯ
		int inqu_num = Integer.parseInt(request.getParameter("inqu_num"));
						
		InquiryDAO dao = InquiryDAO.getInstance();
						
		//��ȸ�� ����
		dao.updateReadcount(inqu_num);
						
		InquiryVO inquiry = dao.getInquiryReply(inqu_num);
						
		//HTML �±׸� ������� ����
		inquiry.setInqu_title(StringUtil.useNoHtml(inquiry.getInqu_title()));
						
		//HTML �±׸� ������� �����鼭 �ٹٲ� ó��
		inquiry.setInqu_content(StringUtil.useBrNoHtml(inquiry.getInqu_content()));
				
		String inquiryContent = StringUtil.useBrNoHtml(dao.getInqu_content(inqu_num));
		
		request.setAttribute("inquiry", inquiry);
		request.setAttribute("inquiryContent", inquiryContent);
					
		return "/WEB-INF/views/inquiry/inquiryReplyDetail.jsp";
	}
}
