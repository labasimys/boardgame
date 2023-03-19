package kr.cart.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.cart.dao.CartDAO;
import kr.cart.vo.CartVO;
import kr.controller.Action;
import kr.list.dao.ListDAO;
import kr.list.vo.ListVO;

public class ModifyCartAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> mapAjax = new HashMap<String, String>();
		HttpSession session = request.getSession();
		Integer mem_num = (Integer)session.getAttribute("user_num");
		if(mem_num == null) {//�α����� ���� ���� ���
			mapAjax.put("result", "logout");
		}else {
			//���۵� ������ ���ڵ� ó��
			request.setCharacterEncoding("utf-8");
			//���۵� ������ ��ȯ
			int pro_num = Integer.parseInt(request.getParameter("pro_num"));
			int pro_count = Integer.parseInt(request.getParameter("cart_count"));
			
			//���� �����ϰ��� �ϴ� ��ǰ�� ������ ����
			ListDAO listDao = ListDAO.getInstance();
			ListVO list = listDao.getList(pro_num);
			if(list.getPro_status() == 1) {//��ǰ ��ǥ��
				mapAjax.put("result", "noSale");
			}else if(list.getPro_count() < pro_count) {//������
				mapAjax.put("result", "noQuantity");
			}else {//�������氡��
				CartVO cart = new CartVO();
				cart.setCart_num(Integer.parseInt(
						request.getParameter("cart_num")));
				cart.setCart_count(pro_count);

				CartDAO cartDao = CartDAO.getInstance();
				cartDao.updateCart(cart);
				mapAjax.put("result", "success");
			}
		}
		
		//JSON ������ ����
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
			
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}