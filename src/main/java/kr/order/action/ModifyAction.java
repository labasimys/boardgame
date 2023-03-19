package kr.order.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.order.dao.OrderDAO;
import kr.order.vo.OrderVO;

public class ModifyAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//로그인 확인
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}//로그인이 된 경우
		//장바구니를 통해서 정보를 가지고 와야하므로 get이 아닌 post방식으로 연결되어야함
		//POST방식의 접급만 허용
		if(request.getMethod().toUpperCase().equals("GET")) {
			//아직 없는 영역
			return "redirect:/item/itemList.do";
		}
		
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		int order_main_num = Integer.parseInt(request.getParameter("order_main_num"));
		
		//주문 정보 수정전 배송 상태를 한번 더 체크함
		OrderDAO dao = OrderDAO.getInstance();
		OrderVO db_order = dao.getOrder(order_main_num);
		
		if(db_order.getStatus()>1) {
			//배송준비중 이상으로 관리자가 변경한 상품을 주문자가 변경할 수 없음
			request.setAttribute("notice_msg", "배송 상태가 변경되어 주문자가 주문 정보 변경 불가");
			request.setAttribute("notice_url", request.getContextPath()+"/order/orderList.do");
			return "/WEB-INF/views/common/alert_singleView.jsp";
		}
		
		OrderVO order = new OrderVO();
		order.setOrder_main_num(order_main_num);
		order.setStatus(Integer.parseInt(request.getParameter("status")));
		order.setReceive_name(request.getParameter("receive_name"));	
		order.setReceive_zipcode(request.getParameter("receive_zipcode"));	
		order.setReceive_address1(request.getParameter("receive_address1"));	
		order.setReceive_address2(request.getParameter("receive_address2"));	
		order.setReceive_phone(request.getParameter("receive_phone"));	
		order.setNotice(request.getParameter("notice"));	

		//주문 정보 수정
		dao.updateOrder(order);
		
		//return "redirect:/order/orderList.do";
		return "redirect:/mymember/myOrderList.do";
	}

}
