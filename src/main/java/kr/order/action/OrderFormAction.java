package kr.order.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.cart.dao.CartDAO;
import kr.cart.vo.CartVO;
import kr.controller.Action;
import kr.list.dao.ListDAO;
import kr.list.vo.ListVO;

public class OrderFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//�α��� ���� ���� ���
			return "redirect:/member/loginForm.do";
		}
		//�α��� �� ���
		
		//��ٱ��ϸ� ���ؼ� ������ ������ �;��ϹǷ� get�� �ƴ� post ������� �����ؾ� �ϹǷ� post��� ���ٸ� ���
		if(request.getMethod().toUpperCase().equals("GET")) {
			//���� ���� ����
			return "redirect:/game/gameList.do";
		}
		
		CartDAO dao = CartDAO.getInstance();
		int all_total = dao.getTotalByMem_num(user_num);
		if(all_total<=0) {
			request.setAttribute("notice_msg", "�������� �ֹ��� �ƴϰų� ��ǰ�� ������ �����մϴ�.");
			request.setAttribute("notice_url", request.getContextPath()+"/game/gameList.do");
			return "/WEB-INF/views/common/alert_singleView.jsp";
		}
		
		//��ٱ��Ͽ� ����ִ� ��ǰ ���� ���
		List<CartVO> cartList = dao.getListCart(user_num);
		for(CartVO cart : cartList) {
			ListDAO listDao = ListDAO.getInstance();
			ListVO list = listDao.getList(cart.getPro_num());
			
			if(list.getPro_status() == 1) {//��ǰ ��ǥ��
				request.setAttribute("notice_msg", "["+list.getPro_name()+"]��ǰ�Ǹ� ����");
				request.setAttribute("notice_url", request.getContextPath()+"/cart/cart.do");
				return "/WEB-INF/views/common/alert_singleView.jsp";
			}
			if(list.getPro_count() < cart.getCart_count()) {//��ǰ ��� ���� ����
				request.setAttribute("notice_msg", "["+list.getPro_name()+"]������ �������� �ֹ� �Ұ�");
				request.setAttribute("notice_url", request.getContextPath()+"/cart/cart.do");
				return "/WEB-INF/views/common/alert_singleView.jsp";
			}
		}
		request.setAttribute("list", cartList);
		request.setAttribute("all_total", all_total);
		
		return "/WEB-INF/views/order/user_orderForm.jsp";
	}

}
