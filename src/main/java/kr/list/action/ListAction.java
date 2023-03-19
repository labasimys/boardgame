package kr.list.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.list.dao.ListDAO;
import kr.list.vo.ListVO;
import kr.util.PagingUtil;

public class ListAction implements Action{

   @Override
   public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      
      
      String pageNum = request.getParameter("pageNum"); //������ ���� �޴´�. 
         if(pageNum==null)pageNum = "1"; //������ 1�� ���� �� pageNum�� 1�� �����ְڴ�. 
         //�˻��� �� �ʿ��� ��.
         String keyfield = request.getParameter("keyfield");
         String keyword = request.getParameter("keyword");
         
         ListDAO dao = ListDAO.getInstance();
         int count = dao.getGameListCount(keyfield, keyword); //count�� �Խ��ǿ� �������� ���� ������ ����. 
        
         //������ ó��
         //keyfield,keyword,currentPage,count,rowCount,
         //pageCount,url  (34~~36 ����Բ��� ����Ű� �״�� �޾ƿ�)
         PagingUtil page = 
               new PagingUtil(keyfield,keyword,
                           Integer.parseInt(pageNum),
                               count,12,10,"gameList.do"); //28 count->dao(���Ӱ���)���� ���� �� ����. 20->�Խñ� ��
         List<ListVO> list = null;
         if(count > 0) {//�����Ͱ� �ϳ��� �ֳ�? ���� ������ ��� �� �����ϱ� ������ �Ǵ� ��. count:���Ӱ����ϱ� ������ ������ ��.
            list = dao.getListGame(page.getStartRow(),
                                  page.getEndRow(),
                                  keyfield,keyword);
         }
         
         request.setAttribute("count", count);
         request.setAttribute("list", list);
         request.setAttribute("page", page.getPage());
         
         
         
         return "/WEB-INF/views/game/list.jsp";
      }
   }

