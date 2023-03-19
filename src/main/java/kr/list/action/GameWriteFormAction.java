package kr.list.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;

public class GameWriteFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();	
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//로그인 되지 않은 경우	(if문 작동 안됨)(다 여기로 들어옴)
			return "redirect:/member/loginForm.do"; //로그인하는 페이지 경로 적어주기.
		}
		
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_auth<9) {//관리자로 로그인하지 않은 경우
			return "/WEB-INF/views/common/notice.jsp";  //알려주는 페이지 경로 적어주기.
		}
		//관리자로 로그인한 경우
		return "/WEB-INF/views/game/admin_gameWrite.jsp";
	}

}
//관리자가 게임 관리 메뉴에 있는 게임 등록 누르면 관리자 체크 하고 게임 등록하는 폼으로 보내줌. 