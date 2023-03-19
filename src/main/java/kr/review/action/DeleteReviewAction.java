package kr.review.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.list.dao.ListDAO;
import kr.review.vo.GameReviewVO;

public class DeleteReviewAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		//전송된 데이터 반환    ??????????
		int rev_num = Integer.parseInt(request.getParameter("rev_num"));
		
		Map<String,String> mapAjax = new HashMap<String,String>();
		ListDAO dao = ListDAO.getInstance();
		
		//작성자 회원번호 구하기.
		GameReviewVO review = dao.getReviewGame(rev_num);
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else if(user_num!=null && user_num == review.getMem_num()) {
			//로그인 ok, 로그인 한 회원번호 == 작성자 회원번호
			dao.deleteReviewGame(rev_num);
			
			mapAjax.put("result", "success");
		}else {
			//로그인 ok, 회원번호 != 작성자 회원번호
			mapAjax.put("result", "wrongAccess");
		}
		
		//JSON 데이터로 변환
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
