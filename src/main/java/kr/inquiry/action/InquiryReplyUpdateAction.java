package kr.inquiry.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.inquiry.dao.InquiryDAO;
import kr.inquiry.vo.InquiryVO;
import kr.util.FileUtil;

public class InquiryReplyUpdateAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		if(user_num == null) {//�α����� ���� ���� ���
			return "redirect:/member/loginForm.do";
		}
		
		MultipartRequest multi = FileUtil.createFile(request);
		int inqu_num = Integer.parseInt(multi.getParameter("inqu_num"));
		String inqu_file = multi.getFilesystemName("inqu_file");
		
		InquiryDAO dao = InquiryDAO.getInstance();
		
		//������ ������ ȣ��
		InquiryVO inquiry = dao.getInquiry(inqu_num);
		
		if(user_num != inquiry.getMem_num()) {
			//�α����� ȸ����ȣ�� �ۼ��� ȸ����ȣ�� ����ġ
			FileUtil.removeFile(request, inqu_file);//���ε�� ������ ������ ���� ����
			return "/WEB-INF/views/inquiry/inquiryList.jsp";
		}
		
		//�α����� ȸ����ȣ�� �ۼ��� ȸ����ȣ�� ��ġ
		InquiryVO inquiryVO = new InquiryVO();
		
		inquiryVO.setInqu_num(inqu_num);
		inquiryVO.setInqu_content(multi.getParameter("inqu_content"));
		
		dao.updateInquiryReply(inquiryVO);
		
		if(inqu_file != null) {
			//�����Ϸ� ��ü�� �� ���� ���� ����
			FileUtil.removeFile(request, inquiry.getInqu_file());
		}
		return "redirect:/inquiry/inquiryReplyDetail.do?inqu_num=" + inqu_num;
	}
}
