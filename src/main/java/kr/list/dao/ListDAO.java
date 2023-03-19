package kr.list.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.list.vo.ListVO;
import kr.review.vo.GameReviewVO;
import kr.util.DBUtil;
import kr.util.DurationFromNow;
import kr.util.StringUtil;

public class ListDAO {
   //싱글턴 패턴 (dao다른데서 호출할 때 얘 불러서 함)
   private static ListDAO instance = new ListDAO();
   
   public static ListDAO getInstance() {
      return instance;
   }
   private ListDAO() {}
   
   
   //총 레코드 수(검색 레코드 수) : 검색폼, 거기에 따른 게시글 숫자 보는 것.
   public int getGameListCount(String keyfield, 
                          String keyword)
                                       throws Exception{
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;		//페이지에 보여하는 값을 sql에서 받아와야해서 rs에 저장함. 
      String sql = null;
      String sub_sql = "";		//검색시 사용
      int count = 0;			//24:public int니까 나중에 64번줄 retune값이랑 자료형 맞춰야함. 그래서 int count.숫자값 계산해서 숫자값 보여주겠다. 
      
      try {
         //커넥셔풀로부터 커넥션 할당
         conn = DBUtil.getConnection();	//dao함수 밑에 try밑에 넣고 그냥그냥 사용!
         
         if(keyword != null && !"".equals(keyword)) {//키워드에 무슨 값이 들어오면
            //검색글 개수  (박스 순서)
            if(keyfield.equals("1")) sub_sql += "WHERE p.pro_name LIKE ?";
            else if(keyfield.equals("2")) sub_sql += "WHERE p.pro_level LIKE ?";
            else if(keyfield.equals("3")) sub_sql += "WHERE p.person LIKE ?";
         }
         
         
         //SQL문 작성     숫자                 숫자만 딸랑 하나만 나옴.  								
         sql = "SELECT COUNT(*) FROM product p " + sub_sql; //product p(알리아스) 
         //PreparedStatement 객체 생성
         pstmt = conn.prepareStatement(sql);			//sql다음에는 무조건 하는걸로 외우기^^!
         if(keyword !=null && !"".equals(keyword)) {
            pstmt.setString(1, "%" + keyword + "%");//첫번째 물음표에 데이터 바인딩, 36번째 ? 대신 값을 넣겠다. else if니까 ? 하나만 나옴. 그래서 하나밖에 없음. 
         }						//sql에서 양 끝에 %있으면 그거 포함된 값 검색. keyword -> 내가 검색 쓴글. "도둑" 독잡-> 도둑잡기

         //SQL문을 실행하고 결과행을 ResultSet 담음
         rs = pstmt.executeQuery();    //고정. rs가 있으면 query가 들어있는 거고, 없으면 executeUpdate가 들어감. 
         if(rs.next()) {				//하나만 나오면 IF, 여러줄 나오면 while
            count = rs.getInt(1);	//우리가 반환해야 하는 거 count. 반환할거 하나니까 그냥 숫자 1. 43번째 셀렉트 문을 여기 count안에 넣겠다. 
         }
      }catch(Exception e) {
         throw new Exception(e);
      }finally {
         DBUtil.executeClose(rs, pstmt, conn);
      }
      return count;
   }
   
  
 //글목록(검색글 목록) List<listVO>는 자료형이다. (배열이라는)
   public List<ListVO> getListGame(int start, int end,
                      String keyfield, String keyword) //페이지에 있는 글 목록은 이게 고정임. 강사님.
                                      throws Exception{
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      List<ListVO> list = null;	//return할 때 자료형
      String sql = null;
      String sub_sql = "";//검색시 사용
      int cnt = 0;		//?에 데이터 바인딩할 때 1,2,3 하는데, 그거 대신 ++하면 알아서 1씩 더해줌. 
      
      try {
         //커넥션풀로부터 커넥션을 할당
         conn = DBUtil.getConnection();
         
         if(keyword != null && !"".equals(keyword)) {//여기 결과값중 하나가 86,87,88에 있는 sub_sql중 하나로 들어감. 
             //검색글 보기
             if(keyfield.equals("1")) sub_sql += "WHERE p.pro_name LIKE ?";
             else if(keyfield.equals("2")) sub_sql += "WHERE p.pro_level LIKE ?";
             else if(keyfield.equals("3")) sub_sql += "WHERE p.person LIKE ?";
          }
          
          //SQL문 작성
          sql = "SELECT * FROM (SELECT a.*, rownum rnum "
             + "FROM (SELECT * FROM product p "+ sub_sql + " "
             + "ORDER BY p.pro_num DESC)a) "
             + "WHERE rnum >= ? AND rnum <= ?";
          //PreparedStatement 객체 생성
         pstmt = conn.prepareStatement(sql);
         //?에 데이터 바인딩
         if(keyword != null && !"".equals(keyword)) { //그냥 있는거. true면 100으로 감 
            pstmt.setString(++cnt, "%" + keyword + "%");	//cnt는 0으로 설정했는데 여기서 1이 들어감. 1번에 keyword넣겠다. 
         }
         pstmt.setInt(++cnt, start);	//2   100번째 실행 안 되고 들어오면-> 1
         pstmt.setInt(++cnt, end);		//3	  						->2
         //SQL문을 실행해서 결과행들을 ResultSet에 담음
         rs = pstmt.executeQuery();			//암기. 
         list = new ArrayList<ListVO>();
         while(rs.next()) {//게임상세페이지는 if : 하나만 보여줌 / 게임목록페이지는 while : 게시글 목록 많아서 이거 .
             ListVO game = new ListVO();  //ListVO쓰기 위해 만든 생성자. 
             game.setPro_num(rs.getInt("pro_num"));//rs에 저장되어 있는 곳에 get가져오다.int자료형.pro_num:sql에 있는 컬럼명.
             game.setPro_name(StringUtil.useNoHtml(rs.getString("pro_name")));//getString일 때는 앞에 이상한거 넣는다. 고정. 
             game.setPro_level(StringUtil.useNoHtml(rs.getString("pro_level")));
             game.setPerson(rs.getInt("person"));
             game.setPro_price(rs.getInt("pro_price"));
             game.setPro_picture(StringUtil.useNoHtml(rs.getString("pro_picture")));
             game.setExplanation(rs.getString("explanation"));
             game.setPro_count(rs.getInt("pro_count"));
             game.setPro_status(rs.getInt("pro_status"));
             //game.setPro_hit();   113
             //game.setPro_id();	114
             
             
             list.add(game);//리스트 배열 list = new ArrayList<ListVO>();와 같음. 
          }
         
      }catch(Exception e) {
         throw new Exception(e);
      }finally {
         DBUtil.executeClose(rs, pstmt, conn);
      }
      return list;							//list -> 75번째 list랑 69번째 줄List<ListVO>FKD 자료형 같다. 
   }

   
   //관리자 - 게임등록
   public void insertGame(ListVO game)throws Exception{
	   Connection conn = null;
	   PreparedStatement pstmt = null;
	   String sql = null;
	   
	   try {
		   //커넥션풀로부터 커넥션을 할당
		   conn = DBUtil.getConnection();
		   //SQL문 작성
		   sql = "INSERT INTO product (pro_num,pro_name,pro_price,pro_picture,"
				   + "pro_level,person,explanation,pro_count,pro_status) "
				   + "VALUES (product_seq.nextval,?,?,?,?,?,?,?,?)";
		   //PreparedStatement 객체 생성
		   pstmt = conn.prepareStatement(sql);
		   //?에 데이터 바인딩
		   pstmt.setString(1, game.getPro_name());
		   pstmt.setInt(2, game.getPro_price());
		   pstmt.setString(3, game.getPro_picture());
		   pstmt.setString(4, game.getPro_level());
		   pstmt.setInt(5, game.getPerson());
		   pstmt.setString(6, game.getExplanation());
		   pstmt.setInt(7, game.getPro_count());
		   pstmt.setInt(8,2);
		   //SQL문 실행
		   pstmt.executeUpdate();
	   }catch(Exception e) {
		   throw new Exception(e);
	   }finally {
		   DBUtil.executeClose(null, pstmt, conn);
	   }
   }
   
   
 //관리자/사용자 - 게임상세
   public ListVO getList(int pro_num)throws Exception{
	   Connection conn = null;
	   PreparedStatement pstmt = null;
	   ResultSet rs = null;
	   ListVO detail = null;
	   String sql = null;
	   
	   try {
		   //커넥션풀로부터 커넥션을 할당
		   conn = DBUtil.getConnection();
		   //SQL문 작성
		   sql = "SELECT * FROM product WHERE pro_num=?";
		   //PrepaerdStatement 객체 생성
		   pstmt = conn.prepareStatement(sql);
		   //?에 데이터 바인딩
		   pstmt.setInt(1, pro_num);
		   //SQL문을 실행해서 결과행을 ResultSet에 담음
		   rs = pstmt.executeQuery();
		   if(rs.next()) {
			   detail = new ListVO();
			   
			   detail.setPro_num(rs.getInt("pro_num"));
			   detail.setPro_name(rs.getString("pro_name"));
			   detail.setPro_price(rs.getInt("pro_price"));
			   detail.setPro_picture(rs.getString("pro_picture"));
			   detail.setPerson(rs.getInt("person"));
			   detail.setPro_level(rs.getString("pro_level"));
			   detail.setPro_status(rs.getInt("pro_status"));
			   detail.setMem_num(rs.getInt("mem_num"));
			   detail.setExplanation(rs.getString("explanation"));
			   detail.setPro_count(rs.getInt("pro_count"));
		   }
	   }catch(Exception e) {
		   throw new Exception(e);
	   }finally {
		   DBUtil.executeClose(rs, pstmt, conn);
	   }
	   return detail;
   }   
   //파일 삭제
   public void deleteFile(int pro_num)throws Exception{
	   Connection conn = null;
	   PreparedStatement pstmt = null;
	   String sql = null;
	   
	   try {
		   //커넥션풀로부터 커넥션 할당
		   conn = DBUtil.getConnection();
		   //SQL문 작성
		   sql = "UPDATE product SET pro_picture='' WHERE pro_num=?";
		   //PreparedStatement 객체 생성
		   pstmt = conn.prepareStatement(sql);
		   //?에 데이터 바인딩
		   pstmt.setInt(1, pro_num);
		   //SQL문 실행
		   pstmt.executeUpdate();
	   }catch(Exception e) {
		   throw new Exception(e);
	   }finally {
		   DBUtil.executeClose(null, pstmt, conn);
	   }
   }
   //게임 삭제
   public void deleteGame(int pro_num)throws Exception{
	   Connection conn = null;
	   PreparedStatement pstmt = null;
	   PreparedStatement pstmt2 = null;
	   PreparedStatement pstmt3 = null;
	   PreparedStatement pstmt4 = null;
	   String sql = null;
	   
	   try {
		   //커넥션풀로부터 커넥션을 할당
		   conn = DBUtil.getConnection();
		   //오토커밋 해제
		   conn.setAutoCommit(false);
		   
		   //리뷰 삭제
		   sql = "DELETE FROM review WHERE pro_num=?";
		   pstmt = conn.prepareStatement(sql);
		   pstmt.setInt(1, pro_num);
		   pstmt.executeUpdate();
		   
		   //cart 삭제
		   sql = "DELETE FROM cart WHERE pro_num=?";
		   pstmt2 = conn.prepareStatement(sql);
		   pstmt2.setInt(1, pro_num);
		   pstmt2.executeUpdate();
		   
		   //order_detail 삭제
		   sql = "DELETE FROM order_detail WHERE pro_num=?";
		   pstmt3 = conn.prepareStatement(sql);
		   pstmt3.setInt(1, pro_num);
		   pstmt3.executeUpdate();
		   
		   //게임 삭제
		   sql = "DELETE FROM product WHERE pro_num=?";
		   pstmt4 = conn.prepareStatement(sql);
		   pstmt4.setInt(1, pro_num);
		   pstmt4.executeUpdate();
		   
		   //예외 발생 없이 정상적으로 SQL문이 실행
		   conn.commit();
	   }catch(Exception e) {
		   //예외 발생
		   conn.rollback();
		   throw new Exception(e);
	   }finally {
		   DBUtil.executeClose(null, pstmt4, null);
		   DBUtil.executeClose(null, pstmt3, null);
		   DBUtil.executeClose(null, pstmt2, null);
		   DBUtil.executeClose(null, pstmt, conn);
	   }
   }
   
   //리뷰 등록
   public void insertReview(GameReviewVO gameReview)throws Exception{
	   Connection conn = null;
	   PreparedStatement pstmt = null;
	   String sql = null;
	   
	   try {
		   //커넥션풀로부터 커넥션 할당
		   conn = DBUtil.getConnection();
		   //SQL문 작성
		   sql = "INSERT INTO review (rev_num,rev_content,re_ip,mem_num,pro_num) "
				   + "VALUES (review_seq.nextval,?,?,?,?)";
		   //PreparedStatement 객체 생성
		   pstmt = conn.prepareStatement(sql);
		   //?에 데이터 바인딩
		   pstmt.setString(1, gameReview.getRev_content());
		   pstmt.setString(2, gameReview.getRe_ip());
		   pstmt.setInt(3, gameReview.getMem_num());
		   pstmt.setInt(4, gameReview.getPro_num());
		   //SQL문 실행
		   pstmt.executeUpdate();
	   }catch(Exception e) {
		   throw new Exception(e);
	   }finally {
		   DBUtil.executeClose(null, pstmt, conn);
	   }
   }
   //리뷰 개수
   public int getReviewCount(int pro_num)throws Exception{
	   Connection conn = null;
	   PreparedStatement pstmt = null;
	   ResultSet rs = null;
	   String sql = null;
	   int count = 0;
	   
	   try {
		   //커넥션풀로부터 커넥션 할당
		   conn = DBUtil.getConnection();
		   //SQL문 작성
		   sql = "SELECT COUNT(*) FROM review b "
				   + "JOIN member m ON b.mem_num=m.mem_num "
				   + "WHERE b.pro_num=?";
		   //PreparedStatement 객체 생성
		   pstmt = conn.prepareStatement(sql);
		   //?에 데이터 바인딩
		   pstmt.setInt(1, pro_num);
		   //SQL문을 실행해서 결과행을 ResultSet 담음
		   rs = pstmt.executeQuery();
		   if(rs.next()) {
			   count = rs.getInt(1);
		   }
	   }catch(Exception e) {
		   throw new Exception(e);
	   }finally {
		   DBUtil.executeClose(rs, pstmt, conn);
	   }
	   return count;
   }
   //리뷰 목록
   public List<GameReviewVO> getListReview(int start,int end, 
		   							int pro_num)throws Exception{
	   Connection conn = null;
	   PreparedStatement pstmt = null;
	   ResultSet rs = null;
	   List<GameReviewVO> list = null;
	   String sql = null;
	   
	   try {
		   //커넥션풀로부터 커넥션을 할당
		   conn = DBUtil.getConnection();
		   //SQL문 작성
		   sql = "SELECT * FROM (SELECT a.*,rownum rnum "
				   + "FROM (SELECT * FROM review b JOIN "
				   + "member m USING(mem_num) WHERE "
				   + "b.pro_num=? ORDER BY b.rev_num DESC)a) "
				   + "WHERE rnum>=? AND rnum<=?";
		   //PreparedStatement 객체 생성
		   pstmt = conn.prepareStatement(sql);
		   //?에 데이터 바인딩
		   pstmt.setInt(1, pro_num);
		   pstmt.setInt(2, start);
		   pstmt.setInt(3, end);
		   //SQL문을 실행해서 결과행들을 ResultSet에 담음
		   rs = pstmt.executeQuery();
		   list = new ArrayList<GameReviewVO>();
		   while(rs.next()) {
			   GameReviewVO review = new GameReviewVO();
			   review.setRev_num(rs.getInt("rev_num"));
			   review.setRev_date(DurationFromNow.getTimeDiffLabel(rs.getString("rev_date")));
			   if(rs.getString("rev_modifydate")!=null) {
				   review.setRev_modifydate(
						   DurationFromNow.getTimeDiffLabel(rs.getString("rev_modifydate")));
			   }
			   review.setRev_content(StringUtil.useBrNoHtml(rs.getString("rev_content")));
			   review.setPro_num(rs.getInt("pro_num"));
			   review.setMem_num(rs.getInt("mem_num"));
			   review.setMem_id(rs.getString("mem_id"));  //user_id...?
			   
			   list.add(review);
		   }
	   }catch(Exception e) {
		   throw new Exception(e);
	   }finally {
		   DBUtil.executeClose(rs, pstmt, conn);
	   }
	   return list;
   }
   
   //리뷰 상세
   public GameReviewVO getReviewGame(int rev_num) throws Exception{
	   Connection conn = null;
	   PreparedStatement pstmt = null;
	   ResultSet rs = null;
	   GameReviewVO review = null;
	   String sql = null;
	   
	   try {
		   //커넥션풀로부터 커넥션을 할당
		   conn = DBUtil.getConnection();
		   //SQL문 작성
		   sql = "SELECT * FROM review WHERE rev_num=?";
		   //PreparedStatement 객체 생성
		   pstmt = conn.prepareStatement(sql);
		   //?에 데이터 바인딩
		   pstmt.setInt(1, rev_num);
		   //SQL문을 실행해서 결과행을 ResultSet에 담음
		   rs = pstmt.executeQuery();
		   if(rs.next()) {
			   review = new GameReviewVO();
			   review.setRev_num(rs.getInt("rev_num"));
			   review.setMem_num(rs.getInt("mem_num"));
		   }
	   }catch(Exception e) {
		   throw new Exception(e);
	   }finally {
		   DBUtil.executeClose(rs, pstmt, conn);
	   }
	   return review;
   }
   //리뷰 수정
   public void updateReviewGame(GameReviewVO review)throws Exception{
	   Connection conn = null;
	   PreparedStatement pstmt = null;
	   String sql = null;
	   
	   try {
		   //커넥션풀로부터 커넥션을 할당
		   conn = DBUtil.getConnection();
		   //SQL문 작성
		   sql = "UPDATE review SET rev_content=?,"
				   + "rev_modifydate=SYSDATE,re_ip=? WHERE rev_num=?";
		   //PreparedStatement 객체 생성
		   pstmt = conn.prepareStatement(sql);
		   //?에 데이터를 바인딩
		   pstmt.setString(1, review.getRev_content());
		   pstmt.setString(2, review.getRe_ip());
		   pstmt.setInt(3, review.getRev_num());
		   //SQL문 실행
		   pstmt.executeUpdate();
	   }catch(Exception e) {
		   throw new Exception(e);
	   }finally {
		   DBUtil.executeClose(null, pstmt, conn);
	   }
   }
   //리뷰 삭제
   public void deleteReviewGame(int rev_num) throws Exception{
	   Connection conn = null;
	   PreparedStatement pstmt = null;
	   String sql = null;
	   
	   try {
		   //커넥션풀로부터 커넥션을 할당
		   conn = DBUtil.getConnection();
		   //SQL문 작성
		   sql = "DELETE FROM review WHERE rev_num=?";
		   //PreparedStatement 객체 생성
		   pstmt = conn.prepareStatement(sql);
		   //?에 데이터 바인딩
		   pstmt.setInt(1, rev_num);
		   //SQL문 실행
		   pstmt.executeUpdate();
	   }catch(Exception e) {
		   throw new Exception(e);
	   }finally{
		   DBUtil.executeClose(null, pstmt, conn);
	   }
   }
}