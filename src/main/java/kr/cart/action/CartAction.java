package kr.cart.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.cart.dao.CartDAO;
import kr.cart.vo.CartVO;
import kr.controller.Action;

public class CartAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = 
				(Integer)session.getAttribute("user_num");
		if(user_num==null) {//�α����� ���� ���� ���
			return "redirect:/member/loginForm.do";
		}
		//�α����� �� ���
		CartDAO dao = CartDAO.getInstance();
		//ȸ����ȣ�� �� ���ž�
		int all_total = dao.getTotalByMem_num(user_num);
		
		List<CartVO> cart = null;
		if(all_total>0) {
			cart = dao.getListCart(user_num);
		}
		
		request.setAttribute("all_total", all_total);
		request.setAttribute("cart", cart);
		
		return "/WEB-INF/views/cart/cart.jsp";
	}
}
