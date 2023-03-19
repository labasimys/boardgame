package kr.list.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.list.dao.ListDAO;
import kr.list.vo.ListVO;
import kr.util.FileUtil;


public class GameDeleteFileAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,String> mapAjax = new HashMap<String,String>();
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			mapAjax.put("result", "logout");			//왜...?
		} 
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_auth<9) {//관리자로 로그인하지 않은 경우
			mapAjax.put("result", "wrongAccess");
		}else {	
		//관리자로 로그인 한 경우 파일 삭제
		int pro_num = Integer.parseInt(request.getParameter("pro_num"));
		
		ListDAO dao = ListDAO.getInstance();
		ListVO db_game = dao.getList(pro_num);
		
		//파일 삭제
		FileUtil.removeFile(request, db_game.getPro_picture());
		
		mapAjax.put("result", "success");
		}
		//JSON데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);

		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
