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

public class UpdateReviewAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		//리뷰 번호
		int rev_num = Integer.parseInt(request.getParameter("rev_num"));
		
		ListDAO dao = ListDAO.getInstance();
		//작성자의 회원번호 구하기
		GameReviewVO game_review = dao.getReviewGame(rev_num); //game_review가 리뷰상세 dao에 rev_num 
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		Map<String,String> mapAjax = new HashMap<String,String>();
		
		if(user_num==null) {//로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else if(user_num!=null && user_num == game_review.getMem_num()) {
			//로그인 ok, 회원번호와 작성자 회원번호 일치.
			GameReviewVO review = new GameReviewVO();
			review.setRev_num(rev_num);
			review.setRev_content(request.getParameter("rev_content"));
			review.setRe_ip(request.getRemoteAddr());
			
			//댓글수정
			dao.updateReviewGame(review);
			
			mapAjax.put("result", "success");
		}else {//로그인 ok, 로그인한 회원번호와 작성자 회원번호 불일치
			mapAjax.put("result", "wrongAccess");
		}
		
		//JSON 데이터로 변환
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

