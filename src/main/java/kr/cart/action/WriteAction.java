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
		if(mem_num == null) {//로그인 되지 않은 경우
			mapAjax.put("result", "logout");
		}else {//로그인 된 경우
			//전송된 데이터 인코딩 처리
			request.setCharacterEncoding("utf-8");
			
			CartVO cart = new CartVO();
			cart.setPro_num(Integer.parseInt(
					   request.getParameter("pro_num")));
			cart.setCart_count(Integer.parseInt("1"));
			cart.setMem_num(mem_num);
			
			CartDAO dao = CartDAO.getInstance();
			CartVO db_cart = dao.getCart(cart);
			if(db_cart == null) {//같은 종류의 상품이 없음
				dao.insertCart(cart);
				mapAjax.put("result", "success");				
			}else {//같은 종류의 상품이 있음
				//재고 수를 구하기 위해서 ListDAO 호출
				ListDAO listDao = ListDAO.getInstance();
				ListVO list = listDao.getList(db_cart.getPro_num());
				
				//구매수량 합산(장바구니에 담겨있는 상품의 구매수량 + 새로 입력한 구매수량)
				int Cart_count = db_cart.getCart_count() + cart.getCart_count();
				
				if(list.getPro_count() < Cart_count) {
					//상품 재고보다 장바구니에 담은 수량이 많음
					mapAjax.put("result", "overquantity");
				}else {
					cart.setCart_count(Cart_count);
					dao.updateCartBypro_num(cart);
					mapAjax.put("result", "success");
				}
			}
		}
		
		//JSON 데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}


