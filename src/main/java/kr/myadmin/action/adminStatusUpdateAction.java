package kr.myadmin.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.myadmin.dao.adminOrderDAO;
import kr.myadmin.vo.adminOrderVO;

public class adminStatusUpdateAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		Integer order_main_num = Integer.parseInt(request.getParameter("order_main_num"));
		Integer status = Integer.parseInt(request.getParameter("status"));
		
		adminOrderVO upstatus = new adminOrderVO();
		upstatus.setOrder_main_num(Integer.parseInt(request.getParameter("order_main_num")));
		upstatus.setStatus(Integer.parseInt(request.getParameter("status")));
	
		adminOrderDAO dao = adminOrderDAO.getInstance();
		dao.updateStatus(status, order_main_num);
		
		request.setAttribute("upstatus", upstatus);
		
		return "/WEB-INF/views/myadmin/admin_UpStatus.jsp";
	}
}
