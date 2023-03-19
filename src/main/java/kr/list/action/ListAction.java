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
      
      
      String pageNum = request.getParameter("pageNum"); //페이지 숫자 받는다. 
         if(pageNum==null)pageNum = "1"; //데이터 1도 없을 땐 pageNum을 1로 보여주겠다. 
         //검색할 때 필요한 거.
         String keyfield = request.getParameter("keyfield");
         String keyword = request.getParameter("keyword");
         
         ListDAO dao = ListDAO.getInstance();
         int count = dao.getGameListCount(keyfield, keyword); //count는 게시판에 페이지당 게임 나오는 갯수. 
        
         //페이지 처리
         //keyfield,keyword,currentPage,count,rowCount,
         //pageCount,url  (34~~36 강사님께서 만드신거 그대로 받아옴)
         PagingUtil page = 
               new PagingUtil(keyfield,keyword,
                           Integer.parseInt(pageNum),
                               count,12,10,"gameList.do"); //28 count->dao(게임갯수)에서 만든 총 갯수. 20->게시글 수
         List<ListVO> list = null;
         if(count > 0) {//데이터가 하나라도 있나? 게임 무조건 등록 돼 있으니까 무조건 되는 것. count:게임갯수니까 무조건 안으로 들어감.
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

