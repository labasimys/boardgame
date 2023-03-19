package kr.myorder.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.myorder.dao.MyOrderDAO;
import kr.myorder.vo.MyOrderVO;
import kr.util.PagingUtil;

public class MyOrderAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = 
				(Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		//로그인 된 경우
		
		
		//페이지 번호 반환
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum = "1";
		
		
		MyOrderDAO dao = MyOrderDAO.getInstance();
		int count = dao.getOrderCount(user_num);
		
		//페이지 처리
		//currentPage,count,rowCount,pageCount,url
		PagingUtil page = new PagingUtil(
		Integer.parseInt(pageNum),count,10,10,"myOrderList.do");
		
		//주문 목록
		List<MyOrderVO> list = null;
		if(count > 0) {
			list = dao.getOrderListBoard(page.getStartRow(),
					                page.getEndRow(),
					                user_num);
		}
		

		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		
		
		return "/WEB-INF/views/myorder/myorderlist.jsp";
	}

}
