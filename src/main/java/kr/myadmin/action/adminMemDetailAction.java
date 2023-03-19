package kr.myadmin.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.myadmin.dao.adminMemDAO;
import kr.myadmin.vo.adminMemVO;
import kr.util.StringUtil;

public class adminMemDetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		int mem_num = Integer.parseInt(request.getParameter("mem_num"));
				
		adminMemDAO dao = adminMemDAO.getInstance();
		adminMemVO detail = dao.getDetail(mem_num);
		
		request.setAttribute("detail", detail);
		
		return "/WEB-INF/views/myadmin/admin_memdetail.jsp";
	}
}
