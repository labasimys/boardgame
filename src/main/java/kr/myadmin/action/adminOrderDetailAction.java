package kr.myadmin.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.myadmin.dao.adminOrderDAO;
import kr.myadmin.vo.adminOrderDetailVO;
import kr.myadmin.vo.adminOrderVO;

public class adminOrderDetailAction implements Action{
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		int order_main_num= Integer.parseInt(request.getParameter("order_main_num"));
				
		adminOrderDAO dao = adminOrderDAO.getInstance();
		adminOrderVO order = dao.getOrder(order_main_num);
		
		List<adminOrderDetailVO> list = dao.getDetail(order_main_num);
		
		request.setAttribute("list", list);
		request.setAttribute("order", order);
		
		return "/WEB-INF/views/myadmin/admin_orderdetail.jsp";
	}
}