package kr.inquiry.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.inquiry.dao.InquiryDAO;
import kr.inquiry.vo.InquiryVO;
import kr.util.PagingUtil;

public class InquiryListAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) pageNum = "1";
		
		InquiryDAO dao = InquiryDAO.getInstance();
		int count = dao.getCount();
		
		PagingUtil page = new PagingUtil(Integer.parseInt(pageNum), count, 15, 5, "inquiryList.do");
		
		List<InquiryVO> list = null;
		if(count > 0) {
			list = dao.getInquiryList(page.getStartRow(), page.getEndRow());
		}
		
		request.setAttribute("list", list);
		request.setAttribute("count", count);
		request.setAttribute("pagingHtml", page.getPage());
		
		return "/WEB-INF/views/inquiry/inquiryList.jsp";
	}
}
