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
		//�α��� Ȯ��
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//�α����� ���� ���� ���
			return "redirect:/member/loginForm.do";
		}//�α����� �� ���
		//��ٱ��ϸ� ���ؼ� ������ ������ �;��ϹǷ� get�� �ƴ� post������� ����Ǿ����
		//POST����� ���޸� ���
		if(request.getMethod().toUpperCase().equals("GET")) {
			//���� ���� ����
			return "redirect:/item/itemList.do";
		}
		
		//���۵� ������ ���ڵ� ó��
		request.setCharacterEncoding("utf-8");
		int order_main_num = Integer.parseInt(request.getParameter("order_main_num"));
		
		//�ֹ� ���� ������ ��� ���¸� �ѹ� �� üũ��
		OrderDAO dao = OrderDAO.getInstance();
		OrderVO db_order = dao.getOrder(order_main_num);
		
		if(db_order.getStatus()>1) {
			//����غ��� �̻����� �����ڰ� ������ ��ǰ�� �ֹ��ڰ� ������ �� ����
			request.setAttribute("notice_msg", "��� ���°� ����Ǿ� �ֹ��ڰ� �ֹ� ���� ���� �Ұ�");
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

		//�ֹ� ���� ����
		dao.updateOrder(order);
		
		//return "redirect:/order/orderList.do";
		return "redirect:/mymember/myOrderList.do";
	}

}
