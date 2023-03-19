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
		if(mem_num == null) {//로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else {
			//전송된 데이터 인코딩 처리
			request.setCharacterEncoding("utf-8");
			//전송된 데이터 반환
			int pro_num = Integer.parseInt(request.getParameter("pro_num"));
			int pro_count = Integer.parseInt(request.getParameter("cart_count"));
			
			//현재 구매하고자 하는 상품의 재고수를 구함
			ListDAO listDao = ListDAO.getInstance();
			ListVO list = listDao.getList(pro_num);
			if(list.getPro_status() == 1) {//상품 미표시
				mapAjax.put("result", "noSale");
			}else if(list.getPro_count() < pro_count) {//재고부족
				mapAjax.put("result", "noQuantity");
			}else {//수량변경가능
				CartVO cart = new CartVO();
				cart.setCart_num(Integer.parseInt(
						request.getParameter("cart_num")));
				cart.setCart_count(pro_count);

				CartDAO cartDao = CartDAO.getInstance();
				cartDao.updateCart(cart);
				mapAjax.put("result", "success");
			}
		}
		
		//JSON 데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
			
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}