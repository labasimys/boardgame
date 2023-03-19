package kr.inquiry.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.inquiry.dao.InquiryDAO;
import kr.inquiry.vo.InquiryVO;
import kr.util.FileUtil;

public class InquiryUpdateAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		MultipartRequest multi = FileUtil.createFile(request);
		int inqu_num = Integer.parseInt(multi.getParameter("inqu_num"));
		String inqu_file = multi.getFilesystemName("inqu_file");
		
		InquiryDAO dao = InquiryDAO.getInstance();
		
		//수정전 데이터 호출
		InquiryVO inquiry = dao.getInquiry(inqu_num);
		
		if(user_num != inquiry.getMem_num()) {
			//로그인한 회원번호와 작성자 회원번호가 불일치
			FileUtil.removeFile(request, inqu_file);//업로드된 파일이 있으면 파일 삭제
			return "/WEB-INF/views/inquiry/inquiryList.jsp";
		}
		
		//로그인한 회원번호와 작성자 회원번호가 일치
		InquiryVO inquiryVO = new InquiryVO();
		
		inquiryVO.setInqu_num(inqu_num);
		inquiryVO.setInqu_cate(multi.getParameter("inqu_cate"));
		inquiryVO.setInqu_title(multi.getParameter("inqu_title"));
		inquiryVO.setInqu_content(multi.getParameter("inqu_content"));
		inquiryVO.setInqu_check(Integer.parseInt(multi.getParameter("inqu_check")));
		inquiryVO.setInqu_file(inqu_file);
		
		dao.updateInquiry(inquiryVO);
		
		if(inqu_file != null) {
			//새파일로 교체할 때 원래 파일 제거
			FileUtil.removeFile(request, inquiry.getInqu_file());
		}
		return "redirect:/inquiry/inquiryDetail.do?inqu_num=" + inqu_num;
	}
}
