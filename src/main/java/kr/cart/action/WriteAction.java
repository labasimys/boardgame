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

public class WriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,String> mapAjax = 
				            new HashMap<String,String>();
		
		HttpSession session = request.getSession();
		Integer mem_num = 
				(Integer)session.getAttribute("user_num");
		if(mem_num == null) {//�α��� ���� ���� ���
			mapAjax.put("result", "logout");
		}else {//�α��� �� ���
			//���۵� ������ ���ڵ� ó��
			request.setCharacterEncoding("utf-8");
			
			CartVO cart = new CartVO();
			cart.setPro_num(Integer.parseInt(
					   request.getParameter("pro_num")));
			cart.setCart_count(Integer.parseInt("1"));
			cart.setMem_num(mem_num);
			
			CartDAO dao = CartDAO.getInstance();
			CartVO db_cart = dao.getCart(cart);
			if(db_cart == null) {//���� ������ ��ǰ�� ����
				dao.insertCart(cart);
				mapAjax.put("result", "success");				
			}else {//���� ������ ��ǰ�� ����
				//��� ���� ���ϱ� ���ؼ� ListDAO ȣ��
				ListDAO listDao = ListDAO.getInstance();
				ListVO list = listDao.getList(db_cart.getPro_num());
				
				//���ż��� �ջ�(��ٱ��Ͽ� ����ִ� ��ǰ�� ���ż��� + ���� �Է��� ���ż���)
				int Cart_count = db_cart.getCart_count() + cart.getCart_count();
				
				if(list.getPro_count() < Cart_count) {
					//��ǰ ����� ��ٱ��Ͽ� ���� ������ ����
					mapAjax.put("result", "overquantity");
				}else {
					cart.setCart_count(Cart_count);
					dao.updateCartBypro_num(cart);
					mapAjax.put("result", "success");
				}
			}
		}
		
		//JSON ������ ����
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}


