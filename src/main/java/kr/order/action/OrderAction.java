package kr.order.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.cart.dao.CartDAO;
import kr.cart.vo.CartVO;
import kr.controller.Action;
import kr.list.dao.ListDAO;
import kr.list.vo.ListVO;
import kr.order.dao.OrderDAO;
import kr.order.vo.OrderDetailVO;
import kr.order.vo.OrderVO;

public class OrderAction implements Action{

   @Override
   public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      //濡쒓렇�씤 �솗�씤
      HttpSession session = request.getSession();
      Integer user_num = (Integer)session.getAttribute("user_num");
      if(user_num == null) {//濡쒓렇�씤 �릺吏� �븡�� �긽�깭
         return "redirect:/member/loginForm.do";
      }
      
      if(request.getMethod().toUpperCase().equals("GET")) {
         return "redirect:/item/itemList.do";
      }
      
      //�쟾�넚�맂 �뜲�씠�꽣 �씤肄붾뵫 泥섎━
      request.setCharacterEncoding("utf-8");
      
      CartDAO dao = CartDAO.getInstance();
      int all_total = dao.getTotalByMem_num(user_num);
      if(all_total<=0) {
         request.setAttribute("notice_msg", "�젙�긽�쟻�씤 二쇰Ц�씠 �븘�땲嫄곕굹 �긽�뭹�쓽 �닔�웾�씠 遺�議깊빀�땲�떎.");
         request.setAttribute("notice_url", request.getContextPath()+"/item/itemList.do");
         return "/WEB-INF/views/common/alert_singleView.jsp";
      }
      
      
      //�옣諛붽뎄�땲�뿉 �떞寃� �엳�뒗 �긽�뭹 �젙蹂� 諛섑솚
      List<CartVO> cartList = dao.getListCart(user_num);
      //二쇰Ц �긽�뭹�쓽 ���몴 �긽�뭹紐� �깮�꽦
      String item_name;
      
      if(cartList.size()==1) {
         item_name = cartList.get(0).getListVo().getPro_name();
      }else {
         item_name = cartList.get(0).getListVo().getPro_name() + "�쇅 " + (cartList.size()-1) + "嫄�";
      }
      
      
      //媛쒕퀎 �긽�뭹 �젙蹂� �떞湲�
      List<OrderDetailVO> orderDetailList = new ArrayList<OrderDetailVO>();
      for(CartVO cart : cartList) {
         ListDAO listDao = ListDAO.getInstance();
         ListVO list = listDao.getList(cart.getPro_num());
      
         if(list.getPro_status() == 1) {//�긽�뭹 誘명몴�떆
            request.setAttribute("notice_msg", "[" + list.getPro_name() + "]�긽�뭹�뙋留� 以묒�");
            request.setAttribute("notice_url", request.getContextPath() + "/cart/list.do");
            return "/WEB-INF/views/common/alert_singleView.jsp";
         }
         if(list.getPro_count() < cart.getCart_count()) {//�긽�뭹 �옱怨좎닔�웾 遺�議�
            request.setAttribute("notice_msg", "[" + list.getPro_name() + "]�옱怨좎닔�웾 遺�議�");
            request.setAttribute("notice_url", request.getContextPath() + "/cart/list.do");
            return "/WEB-INF/views/common/alert_singleView.jsp";
         }
         OrderDetailVO orderDetail = new OrderDetailVO();
         orderDetail.setPro_num(cart.getPro_num());
         orderDetail.setPro_name(cart.getListVo().getPro_name());
         orderDetail.setPro_price(cart.getListVo().getPro_price());
         orderDetail.setOrder_main_count(cart.getCart_count());
         orderDetail.setPro_total(cart.getSub_total());
         
         orderDetailList.add(orderDetail);
      }
      
      //援щℓ �젙蹂� �떞湲�
      OrderVO order = new OrderVO();
      order.setOrder_main_name(item_name);
      order.setOrder_main_total(all_total);
      order.setPayment(Integer.parseInt(request.getParameter("payment")));
      order.setReceive_name(request.getParameter("receive_name"));
      order.setReceive_zipcode(request.getParameter("receive_zipcode"));
      order.setReceive_address1(request.getParameter("receive_address1"));
      order.setReceive_address2(request.getParameter("receive_address2"));
      order.setReceive_phone(request.getParameter("receive_phone"));
      order.setNotice(request.getParameter("notice"));
      order.setMem_num(user_num);
      
      OrderDAO orderDao = OrderDAO.getInstance();
      orderDao.insertOrder(order, orderDetailList);
      
      request.setAttribute("notice_msg", "二쇰Ц�씠 �셿猷뚮릺�뿀�뒿�땲�떎.");
      request.setAttribute("notice_url", request.getContextPath()+"/mymember/myOrderList.do");

      return "/WEB-INF/views/common/alert_singleView.jsp";
      
   }

}