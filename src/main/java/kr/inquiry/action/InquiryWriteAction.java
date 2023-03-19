package kr.inquiry.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.inquiry.dao.InquiryDAO;
import kr.inquiry.vo.InquiryVO;
import kr.util.FileUtil;

public class InquiryWriteAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		
		Integer user_num = (Integer)session.getAttribute("user_num");

		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		//로그인 된 경우
		MultipartRequest multi = FileUtil.createFile(request);
		
		InquiryVO inquiry = new InquiryVO();
		inquiry.setMem_num(user_num);
		inquiry.setInqu_cate(multi.getParameter("inqu_cate"));
		inquiry.setInqu_title(multi.getParameter("inqu_title"));
		inquiry.setInqu_content(multi.getParameter("inqu_content"));
		inquiry.setInqu_file(multi.getFilesystemName("inqu_file"));
		inquiry.setInqu_check(Integer.parseInt(multi.getParameter("inqu_check")));
		
		InquiryDAO dao = InquiryDAO.getInstance();
		dao.insertInquiry(inquiry);
		
		return "redirect:/inquiry/inquiryList.do";
	}
}
