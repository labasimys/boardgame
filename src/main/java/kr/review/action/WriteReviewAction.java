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

public class WriteReviewAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,String> mapAjax = new HashMap<String,String>();
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			mapAjax.put("result", "logout");		//?????
		}else {//로그인 된 경우
			//전송된 데이터 인코딩 처리
			request.setCharacterEncoding("utf-8");
			
			GameReviewVO review = new GameReviewVO();
			
			review.setMem_num(user_num);//회원번호(댓글 작성자)
			review.setRev_content(request.getParameter("rev_content"));
			review.setRe_ip(request.getRemoteAddr());
			review.setPro_num(Integer.parseInt(request.getParameter("pro_num")));
			
			ListDAO dao = ListDAO.getInstance();
			dao.insertReview(review);
			
			mapAjax.put("result", "success"); //??????
		}
		
		//JSON 데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
