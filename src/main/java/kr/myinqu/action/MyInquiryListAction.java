package kr.myinqu.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.myinqu.dao.MyinquDAO;
import kr.myinqu.vo.MyinquVO;
import kr.util.PagingUtil;

public class MyInquiryListAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = 
				(Integer)session.getAttribute("user_num");
		if(user_num==null) {//�α����� ���� ���� ���
			return "redirect:/member/loginForm.do";
		}
		//�α��� �� ���
		
		
		//������ ��ȣ ��ȯ
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum = "1";
		
		
		MyinquDAO dao = MyinquDAO.getInstance();
		int count = dao.getInQuCount(user_num);
		
		//������ ó��
		//currentPage,count,rowCount,pageCount,url
		PagingUtil page = new PagingUtil(
				Integer.parseInt(pageNum),count,10,10,"myInquiryList.do");
		List<MyinquVO> list = null;
		if(count > 0) {
			list = dao.getInquListBoard(page.getStartRow(),
					                page.getEndRow(),
					                user_num);
		}
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		
		
		return "/WEB-INF/views/myInquiry/myInquirylist.jsp";
	}

}
