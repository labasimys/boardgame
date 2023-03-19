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
		//���۵� ������ ���ڵ� ó��
		request.setCharacterEncoding("utf-8");
		//���۵� ������ ��ȯ    ??????????
		int rev_num = Integer.parseInt(request.getParameter("rev_num"));
		
		Map<String,String> mapAjax = new HashMap<String,String>();
		ListDAO dao = ListDAO.getInstance();
		
		//�ۼ��� ȸ����ȣ ���ϱ�.
		GameReviewVO review = dao.getReviewGame(rev_num);
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {//�α����� ���� ���� ���
			mapAjax.put("result", "logout");
		}else if(user_num!=null && user_num == review.getMem_num()) {
			//�α��� ok, �α��� �� ȸ����ȣ == �ۼ��� ȸ����ȣ
			dao.deleteReviewGame(rev_num);
			
			mapAjax.put("result", "success");
		}else {
			//�α��� ok, ȸ����ȣ != �ۼ��� ȸ����ȣ
			mapAjax.put("result", "wrongAccess");
		}
		
		//JSON �����ͷ� ��ȯ
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
