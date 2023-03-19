package kr.inquiry.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.inquiry.dao.InquiryDAO;
import kr.inquiry.vo.InquiryVO;
import kr.util.FileUtil;

public class InquiryReplyAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		MultipartRequest multi = FileUtil.createFile(request);
		int inqu_num = Integer.parseInt(multi.getParameter("inqu_num"));
		
		InquiryVO inquiryVO = new InquiryVO();
		
		inquiryVO.setInqu_rpl(inqu_num);
		inquiryVO.setMem_num(user_num);
		inquiryVO.setInqu_title(multi.getParameter("inqu_title"));
		inquiryVO.setInqu_content(multi.getParameter("inqu_content"));
		inquiryVO.setInqu_check(Integer.parseInt(multi.getParameter("inqu_check")));
		
		InquiryDAO dao = InquiryDAO.getInstance();
		dao.insertInquiryReply(inquiryVO);
		
		return "redirect:/inquiry/inquiryList.do";
	}
}
