package kr.myreserve.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.myreserve.dao.MyReserveDAO;
import kr.myreserve.vo.MyReserveVO;
import kr.util.PagingUtil;

public class MyReserveAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = 
				(Integer)session.getAttribute("user_num");
		if(user_num==null) {//�α����� ���� ���� ���
			return "redirect:/member/loginForm.do";
		}
		//�α��� �� ���
		
		
		//������ ��ȣ ��ȯ
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum = "1";
		
		
		MyReserveDAO dao = MyReserveDAO.getInstance();
		//�� ���ڵ� �� - ������
		int count = dao.getMyReserveCount(user_num);
		//�� ���ڵ� �� - ���ÿ���
		int todaycount = dao.getMyReserveCountToday(user_num);
		//������ ó��
		//currentPage,count,rowCount,pageCount,url
		PagingUtil page = new PagingUtil(
				Integer.parseInt(pageNum),count,10,10,"myReserve.do");
		//������
		List<MyReserveVO> list = null;
		if(count > 0) {
			list = dao.getMyReserveListBefore(page.getStartRow(),
					                page.getEndRow(),
					                user_num);
		}
		//���ÿ���
		MyReserveVO MyRe = new MyReserveVO();
		if(todaycount > 0) {
			MyRe = dao.getMyReserveToday(user_num);
		}
		
		
		
		
		request.setAttribute("count", count);
		request.setAttribute("todaycount", todaycount);

		request.setAttribute("list", list);
		request.setAttribute("MyRe", MyRe);
		
		request.setAttribute("page", page.getPage());
		
		
		
		return "/WEB-INF/views/myreserve/myreservelist.jsp";
	}

}
