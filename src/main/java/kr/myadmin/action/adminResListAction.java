package kr.myadmin.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.myadmin.dao.adminResDAO;
import kr.myadmin.vo.adminResVO;
import kr.util.PagingUtil;

public class adminResListAction implements Action{

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
	    String pageNum = request.getParameter("pageNum");
	    if(pageNum == null) pageNum = "1";
	     
	    adminResDAO dao = adminResDAO.getInstance();
	    int count = dao.getCount();
	      
	    //페이지 처리
	    //currentPage(현재페이지), count(전체 게시물 개수), rowCount(한 페이지의 게시물 개수), pageCount(한 화면에 보여줄 페이지 개수), url(호출 페이지 url)
	    PagingUtil page = new PagingUtil(Integer.parseInt(pageNum), count, 10, 10, "resList.do");
	      
	    List<adminResVO> list = null;
	    if (count > 0) {
	       list = dao.getList(page.getStartRow(), page.getEndRow());
	    }
	      
	    request.setAttribute("list", list);
	    request.setAttribute("count", count);
	    request.setAttribute("pagingHtml", page.getPage());
	      
	      
	    //JSP 경로 반환
		return "/WEB-INF/views/myadmin/admin_reslist.jsp";
	}
}