package kr.inquiry.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.inquiry.dao.InquiryDAO;
import kr.inquiry.vo.InquiryVO;
import kr.util.FileUtil;

public class DeleteInquiryFileAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,String> mapAjax = new HashMap<String,String>();
		
		HttpSession session = request.getSession();
		
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//�α����� ���� ���� ���
			mapAjax.put("result", "logout");
		}else {//�α��� �� ���
			int inqu_num = Integer.parseInt(request.getParameter("inqu_num"));
			
			InquiryDAO dao = InquiryDAO.getInstance();
			InquiryVO inquiry = dao.getInquiry(inqu_num);
			
			if(user_num != inquiry.getMem_num()) {
				//�α����� ����� �ۼ��ڰ� ����ġ�� ���
				mapAjax.put("result", "wrongAccess");
			}else {
				//�α����� ����� �ۼ��� ��ġ
				dao.deleteFile(inqu_num);
				
				//���� ����
				FileUtil.removeFile(request, inquiry.getInqu_file());
				mapAjax.put("result", "success");
			}
		}
		
		//JSON ������ ����
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}
}
