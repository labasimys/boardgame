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
		if(user_num == null) {//로그인 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		//로그인 된 경우
		
		//장바구니를 통해서 정보를 가지고 와야하므로 get이 아닌 post 방식으로 연결해야 하므로 post방식 접근만 허용
		if(request.getMethod().toUpperCase().equals("GET")) {
			//아직 없는 영역
			return "redirect:/game/gameList.do";
		}
		
		CartDAO dao = CartDAO.getInstance();
		int all_total = dao.getTotalByMem_num(user_num);
		if(all_total<=0) {
			request.setAttribute("notice_msg", "정상적인 주문이 아니거나 상품의 수량이 부족합니다.");
			request.setAttribute("notice_url", request.getContextPath()+"/game/gameList.do");
			return "/WEB-INF/views/common/alert_singleView.jsp";
		}
		
		//장바구니에 담겨있는 상품 정보 출력
		List<CartVO> cartList = dao.getListCart(user_num);
		for(CartVO cart : cartList) {
			ListDAO listDao = ListDAO.getInstance();
			ListVO list = listDao.getList(cart.getPro_num());
			
			if(list.getPro_status() == 1) {//상품 미표시
				request.setAttribute("notice_msg", "["+list.getPro_name()+"]상품판매 중지");
				request.setAttribute("notice_url", request.getContextPath()+"/cart/cart.do");
				return "/WEB-INF/views/common/alert_singleView.jsp";
			}
			if(list.getPro_count() < cart.getCart_count()) {//상품 재고 수량 부족
				request.setAttribute("notice_msg", "["+list.getPro_name()+"]재고수량 부족으로 주문 불가");
				request.setAttribute("notice_url", request.getContextPath()+"/cart/cart.do");
				return "/WEB-INF/views/common/alert_singleView.jsp";
			}
		}
		request.setAttribute("list", cartList);
		request.setAttribute("all_total", all_total);
		
		return "/WEB-INF/views/order/user_orderForm.jsp";
	}

}
