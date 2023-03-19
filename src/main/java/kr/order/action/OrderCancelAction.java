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
		//�α��� Ȯ��
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//�α����� ���� ���� ���
			return "redirect:/member/loginForm.do";
		}//�α����� �� ���
		
		int order_main_num = Integer.parseInt(request.getParameter("order_main_num"));
		
		OrderDAO dao = OrderDAO.getInstance();
		OrderVO db_order = dao.getOrder(order_main_num);
		if(db_order.getStatus()>1) {
			//����غ��� �̻����� �����ڰ� ������ ��ǰ�� �ֹ��ڰ� �ֹ� ����� �� ����
			request.setAttribute("notice_msg", "��� ���°� ����Ǿ� �ֹ��ڰ� �ֹ��� ����� �� ����");
			request.setAttribute("notice_url", request.getContextPath()+"/order/orderList.do");
			return "/WEB-INF/views/common/alert_singleView.jsp";
		}
		
		//�ֹ� ���
		dao.updateOrderCancel(order_main_num);
		
		request.setAttribute("notice_msg", "�ֹ���Ұ� �Ϸ�Ǿ����ϴ�.");
		request.setAttribute("notice_url", request.getContextPath()+"/order/orderModifyForm.do?order_main_num="+order_main_num);
		
		return "/WEB-INF/views/common/alert_singleView.jsp";
	}

}
