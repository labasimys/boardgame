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
		// ���۵� ������ ���ڵ� ó��
		request.setCharacterEncoding("utf-8");
		//���� ��ȣ
		int rev_num = Integer.parseInt(request.getParameter("rev_num"));
		
		ListDAO dao = ListDAO.getInstance();
		//�ۼ����� ȸ����ȣ ���ϱ�
		GameReviewVO game_review = dao.getReviewGame(rev_num); //game_review�� ����� dao�� rev_num 
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		Map<String,String> mapAjax = new HashMap<String,String>();
		
		if(user_num==null) {//�α����� ���� ���� ���
			mapAjax.put("result", "logout");
		}else if(user_num!=null && user_num == game_review.getMem_num()) {
			//�α��� ok, ȸ����ȣ�� �ۼ��� ȸ����ȣ ��ġ.
			GameReviewVO review = new GameReviewVO();
			review.setRev_num(rev_num);
			review.setRev_content(request.getParameter("rev_content"));
			review.setRe_ip(request.getRemoteAddr());
			
			//��ۼ���
			dao.updateReviewGame(review);
			
			mapAjax.put("result", "success");
		}else {//�α��� ok, �α����� ȸ����ȣ�� �ۼ��� ȸ����ȣ ����ġ
			mapAjax.put("result", "wrongAccess");
		}
		
		//JSON �����ͷ� ��ȯ
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

