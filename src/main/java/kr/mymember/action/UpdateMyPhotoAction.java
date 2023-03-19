package kr.mymember.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.mymember.dao.MemberDAO;
import kr.mymember.vo.MemberVO;
import kr.util.FileUtil;

public class UpdateMyPhotoAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map<String,String> mapAjax = 
				new HashMap<String,String>();
		
		HttpSession session = request.getSession();
		Integer user_num = 
				(Integer)session.getAttribute("user_num");
		if(user_num==null) {//�α����� ���� ���� ���
			mapAjax.put("result", "logout");
		}else {//�α��� �� ���
			MemberDAO dao = MemberDAO.getInstance();
			//���� �̹��� ���� ���� ���
			MemberVO db_member = dao.getMember(user_num);
			
			//���۵� ���� ���ε� ó��
			MultipartRequest multi = FileUtil.createFile(request);
			//������ ����� ���ϸ� ��ȯ
			String photo = multi.getFilesystemName("photo");
			//������ ���� 
			//                ���ϸ�   mem_num
			dao.updateMyPhoto(photo, user_num);
			
			//���ǿ� ����� ������ ���� ���� ����
			session.setAttribute("user_photo", photo);
			
			//���� ������ �̹��� ����
			FileUtil.removeFile(request, db_member.getMem_photo());
			
			mapAjax.put("result", "success");
		}
		
		//JSON �����ͷ� ��ȯ
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}




