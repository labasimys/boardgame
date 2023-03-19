package kr.myadmin.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import kr.controller.Action;
import kr.myadmin.dao.adminMemDAO;
import kr.myadmin.vo.adminMemVO;

public class adminMemAuthUpdateAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		Integer mem_num = Integer.parseInt(request.getParameter("mem_num"));
		Integer mem_auth = Integer.parseInt(request.getParameter("mem_auth"));
		
		adminMemVO upauth = new adminMemVO();
		upauth.setMem_num(Integer.parseInt(request.getParameter("mem_num")));
		upauth.setMem_auth(Integer.parseInt(request.getParameter("mem_auth")));
	
		adminMemDAO dao = adminMemDAO.getInstance();
		dao.updateAuth(mem_auth, mem_num);
		
		request.setAttribute("upauth", upauth);
		
		return "/WEB-INF/views/myadmin/admin_UpmemAuth.jsp";
	}
}
