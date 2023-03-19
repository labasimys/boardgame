package kr.order.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.order.dao.OrderDAO;
import kr.order.vo.OrderVO;

public class OrderCancelAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//로그인 확인
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}//로그인이 된 경우
		
		int order_main_num = Integer.parseInt(request.getParameter("order_main_num"));
		
		OrderDAO dao = OrderDAO.getInstance();
		OrderVO db_order = dao.getOrder(order_main_num);
		if(db_order.getStatus()>1) {
			//배송준비중 이상으로 관리자가 변경한 상품을 주문자가 주문 취소할 수 없음
			request.setAttribute("notice_msg", "배송 상태가 변경되어 주문자가 주문을 취소할 수 없음");
			request.setAttribute("notice_url", request.getContextPath()+"/order/orderList.do");
			return "/WEB-INF/views/common/alert_singleView.jsp";
		}
		
		//주문 취소
		dao.updateOrderCancel(order_main_num);
		
		request.setAttribute("notice_msg", "주문취소가 완료되었습니다.");
		request.setAttribute("notice_url", request.getContextPath()+"/order/orderModifyForm.do?order_main_num="+order_main_num);
		
		return "/WEB-INF/views/common/alert_singleView.jsp";
	}

}
