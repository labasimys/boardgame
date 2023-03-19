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
   //�̱��� ���� (dao�ٸ����� ȣ���� �� �� �ҷ��� ��)
   private static ListDAO instance = new ListDAO();
   
   public static ListDAO getInstance() {
      return instance;
   }
   private ListDAO() {}
   
   
   //�� ���ڵ� ��(�˻� ���ڵ� ��) : �˻���, �ű⿡ ���� �Խñ� ���� ���� ��.
   public int getGameListCount(String keyfield, 
                          String keyword)
                                       throws Exception{
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;		//�������� �����ϴ� ���� sql���� �޾ƿ;��ؼ� rs�� ������. 
      String sql = null;
      String sub_sql = "";		//�˻��� ���
      int count = 0;			//24:public int�ϱ� ���߿� 64���� retune���̶� �ڷ��� �������. �׷��� int count.���ڰ� ����ؼ� ���ڰ� �����ְڴ�. 
      
      try {
         //Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ� �Ҵ�
         conn = DBUtil.getConnection();	//dao�Լ� �ؿ� try�ؿ� �ְ� �׳ɱ׳� ���!
         
         if(keyword != null && !"".equals(keyword)) {//Ű���忡 ���� ���� ������
            //�˻��� ����  (�ڽ� ����)
            if(keyfield.equals("1")) sub_sql += "WHERE p.pro_name LIKE ?";
            else if(keyfield.equals("2")) sub_sql += "WHERE p.pro_level LIKE ?";
            else if(keyfield.equals("3")) sub_sql += "WHERE p.person LIKE ?";
         }
         
         
         //SQL�� �ۼ�     ����                 ���ڸ� ���� �ϳ��� ����.  								
         sql = "SELECT COUNT(*) FROM product p " + sub_sql; //product p(�˸��ƽ�) 
         //PreparedStatement ��ü ����
         pstmt = conn.prepareStatement(sql);			//sql�������� ������ �ϴ°ɷ� �ܿ��^^!
         if(keyword !=null && !"".equals(keyword)) {
            pstmt.setString(1, "%" + keyword + "%");//ù��° ����ǥ�� ������ ���ε�, 36��° ? ��� ���� �ְڴ�. else if�ϱ� ? �ϳ��� ����. �׷��� �ϳ��ۿ� ����. 
         }						//sql���� �� ���� %������ �װ� ���Ե� �� �˻�. keyword -> ���� �˻� ����. "����" ����-> �������

         //SQL���� �����ϰ� ������� ResultSet ����
         rs = pstmt.executeQuery();    //����. rs�� ������ query�� ����ִ� �Ű�, ������ executeUpdate�� ��. 
         if(rs.next()) {				//�ϳ��� ������ IF, ������ ������ while
            count = rs.getInt(1);	//�츮�� ��ȯ�ؾ� �ϴ� �� count. ��ȯ�Ұ� �ϳ��ϱ� �׳� ���� 1. 43��° ����Ʈ ���� ���� count�ȿ� �ְڴ�. 
         }
      }catch(Exception e) {
         throw new Exception(e);
      }finally {
         DBUtil.executeClose(rs, pstmt, conn);
      }
      return count;
   }
   
  
 //�۸��(�˻��� ���) List<listVO>�� �ڷ����̴�. (�迭�̶��)
   public List<ListVO> getListGame(int start, int end,
                      String keyfield, String keyword) //�������� �ִ� �� ����� �̰� ������. �����.
                                      throws Exception{
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      List<ListVO> list = null;	//return�� �� �ڷ���
      String sql = null;
      String sub_sql = "";//�˻��� ���
      int cnt = 0;		//?�� ������ ���ε��� �� 1,2,3 �ϴµ�, �װ� ��� ++�ϸ� �˾Ƽ� 1�� ������. 
      
      try {
         //Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ��� �Ҵ�
         conn = DBUtil.getConnection();
         
         if(keyword != null && !"".equals(keyword)) {//���� ������� �ϳ��� 86,87,88�� �ִ� sub_sql�� �ϳ��� ��. 
             //�˻��� ����
             if(keyfield.equals("1")) sub_sql += "WHERE p.pro_name LIKE ?";
             else if(keyfield.equals("2")) sub_sql += "WHERE p.pro_level LIKE ?";
             else if(keyfield.equals("3")) sub_sql += "WHERE p.person LIKE ?";
          }
          
          //SQL�� �ۼ�
          sql = "SELECT * FROM (SELECT a.*, rownum rnum "
             + "FROM (SELECT * FROM product p "+ sub_sql + " "
             + "ORDER BY p.pro_num DESC)a) "
             + "WHERE rnum >= ? AND rnum <= ?";
          //PreparedStatement ��ü ����
         pstmt = conn.prepareStatement(sql);
         //?�� ������ ���ε�
         if(keyword != null && !"".equals(keyword)) { //�׳� �ִ°�. true�� 100���� �� 
            pstmt.setString(++cnt, "%" + keyword + "%");	//cnt�� 0���� �����ߴµ� ���⼭ 1�� ��. 1���� keyword�ְڴ�. 
         }
         pstmt.setInt(++cnt, start);	//2   100��° ���� �� �ǰ� ������-> 1
         pstmt.setInt(++cnt, end);		//3	  						->2
         //SQL���� �����ؼ� �������� ResultSet�� ����
         rs = pstmt.executeQuery();			//�ϱ�. 
         list = new ArrayList<ListVO>();
         while(rs.next()) {//���ӻ��������� if : �ϳ��� ������ / ���Ӹ���������� while : �Խñ� ��� ���Ƽ� �̰� .
             ListVO game = new ListVO();  //ListVO���� ���� ���� ������. 
             game.setPro_num(rs.getInt("pro_num"));//rs�� ����Ǿ� �ִ� ���� get��������.int�ڷ���.pro_num:sql�� �ִ� �÷���.
             game.setPro_name(StringUtil.useNoHtml(rs.getString("pro_name")));//getString�� ���� �տ� �̻��Ѱ� �ִ´�. ����. 
             game.setPro_level(StringUtil.useNoHtml(rs.getString("pro_level")));
             game.setPerson(rs.getInt("person"));
             game.setPro_price(rs.getInt("pro_price"));
             game.setPro_picture(StringUtil.useNoHtml(rs.getString("pro_picture")));
             game.setExplanation(rs.getString("explanation"));
             game.setPro_count(rs.getInt("pro_count"));
             game.setPro_status(rs.getInt("pro_status"));
             //game.setPro_hit();   113
             //game.setPro_id();	114
             
             
             list.add(game);//����Ʈ �迭 list = new ArrayList<ListVO>();�� ����. 
          }
         
      }catch(Exception e) {
         throw new Exception(e);
      }finally {
         DBUtil.executeClose(rs, pstmt, conn);
      }
      return list;							//list -> 75��° list�� 69��° ��List<ListVO>FKD �ڷ��� ����. 
   }

   
   //������ - ���ӵ��
   public void insertGame(ListVO game)throws Exception{
	   Connection conn = null;
	   PreparedStatement pstmt = null;
	   String sql = null;
	   
	   try {
		   //Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ��� �Ҵ�
		   conn = DBUtil.getConnection();
		   //SQL�� �ۼ�
		   sql = "INSERT INTO product (pro_num,pro_name,pro_price,pro_picture,"
				   + "pro_level,person,explanation,pro_count,pro_status) "
				   + "VALUES (product_seq.nextval,?,?,?,?,?,?,?,?)";
		   //PreparedStatement ��ü ����
		   pstmt = conn.prepareStatement(sql);
		   //?�� ������ ���ε�
		   pstmt.setString(1, game.getPro_name());
		   pstmt.setInt(2, game.getPro_price());
		   pstmt.setString(3, game.getPro_picture());
		   pstmt.setString(4, game.getPro_level());
		   pstmt.setInt(5, game.getPerson());
		   pstmt.setString(6, game.getExplanation());
		   pstmt.setInt(7, game.getPro_count());
		   pstmt.setInt(8,2);
		   //SQL�� ����
		   pstmt.executeUpdate();
	   }catch(Exception e) {
		   throw new Exception(e);
	   }finally {
		   DBUtil.executeClose(null, pstmt, conn);
	   }
   }
   
   
 //������/����� - ���ӻ�
   public ListVO getList(int pro_num)throws Exception{
	   Connection conn = null;
	   PreparedStatement pstmt = null;
	   ResultSet rs = null;
	   ListVO detail = null;
	   String sql = null;
	   
	   try {
		   //Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ��� �Ҵ�
		   conn = DBUtil.getConnection();
		   //SQL�� �ۼ�
		   sql = "SELECT * FROM product WHERE pro_num=?";
		   //PrepaerdStatement ��ü ����
		   pstmt = conn.prepareStatement(sql);
		   //?�� ������ ���ε�
		   pstmt.setInt(1, pro_num);
		   //SQL���� �����ؼ� ������� ResultSet�� ����
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
   //���� ����
   public void deleteFile(int pro_num)throws Exception{
	   Connection conn = null;
	   PreparedStatement pstmt = null;
	   String sql = null;
	   
	   try {
		   //Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ� �Ҵ�
		   conn = DBUtil.getConnection();
		   //SQL�� �ۼ�
		   sql = "UPDATE product SET pro_picture='' WHERE pro_num=?";
		   //PreparedStatement ��ü ����
		   pstmt = conn.prepareStatement(sql);
		   //?�� ������ ���ε�
		   pstmt.setInt(1, pro_num);
		   //SQL�� ����
		   pstmt.executeUpdate();
	   }catch(Exception e) {
		   throw new Exception(e);
	   }finally {
		   DBUtil.executeClose(null, pstmt, conn);
	   }
   }
   //���� ����
   public void deleteGame(int pro_num)throws Exception{
	   Connection conn = null;
	   PreparedStatement pstmt = null;
	   PreparedStatement pstmt2 = null;
	   PreparedStatement pstmt3 = null;
	   PreparedStatement pstmt4 = null;
	   String sql = null;
	   
	   try {
		   //Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ��� �Ҵ�
		   conn = DBUtil.getConnection();
		   //����Ŀ�� ����
		   conn.setAutoCommit(false);
		   
		   //���� ����
		   sql = "DELETE FROM review WHERE pro_num=?";
		   pstmt = conn.prepareStatement(sql);
		   pstmt.setInt(1, pro_num);
		   pstmt.executeUpdate();
		   
		   //cart ����
		   sql = "DELETE FROM cart WHERE pro_num=?";
		   pstmt2 = conn.prepareStatement(sql);
		   pstmt2.setInt(1, pro_num);
		   pstmt2.executeUpdate();
		   
		   //order_detail ����
		   sql = "DELETE FROM order_detail WHERE pro_num=?";
		   pstmt3 = conn.prepareStatement(sql);
		   pstmt3.setInt(1, pro_num);
		   pstmt3.executeUpdate();
		   
		   //���� ����
		   sql = "DELETE FROM product WHERE pro_num=?";
		   pstmt4 = conn.prepareStatement(sql);
		   pstmt4.setInt(1, pro_num);
		   pstmt4.executeUpdate();
		   
		   //���� �߻� ���� ���������� SQL���� ����
		   conn.commit();
	   }catch(Exception e) {
		   //���� �߻�
		   conn.rollback();
		   throw new Exception(e);
	   }finally {
		   DBUtil.executeClose(null, pstmt4, null);
		   DBUtil.executeClose(null, pstmt3, null);
		   DBUtil.executeClose(null, pstmt2, null);
		   DBUtil.executeClose(null, pstmt, conn);
	   }
   }
   
   //���� ���
   public void insertReview(GameReviewVO gameReview)throws Exception{
	   Connection conn = null;
	   PreparedStatement pstmt = null;
	   String sql = null;
	   
	   try {
		   //Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ� �Ҵ�
		   conn = DBUtil.getConnection();
		   //SQL�� �ۼ�
		   sql = "INSERT INTO review (rev_num,rev_content,re_ip,mem_num,pro_num) "
				   + "VALUES (review_seq.nextval,?,?,?,?)";
		   //PreparedStatement ��ü ����
		   pstmt = conn.prepareStatement(sql);
		   //?�� ������ ���ε�
		   pstmt.setString(1, gameReview.getRev_content());
		   pstmt.setString(2, gameReview.getRe_ip());
		   pstmt.setInt(3, gameReview.getMem_num());
		   pstmt.setInt(4, gameReview.getPro_num());
		   //SQL�� ����
		   pstmt.executeUpdate();
	   }catch(Exception e) {
		   throw new Exception(e);
	   }finally {
		   DBUtil.executeClose(null, pstmt, conn);
	   }
   }
   //���� ����
   public int getReviewCount(int pro_num)throws Exception{
	   Connection conn = null;
	   PreparedStatement pstmt = null;
	   ResultSet rs = null;
	   String sql = null;
	   int count = 0;
	   
	   try {
		   //Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ� �Ҵ�
		   conn = DBUtil.getConnection();
		   //SQL�� �ۼ�
		   sql = "SELECT COUNT(*) FROM review b "
				   + "JOIN member m ON b.mem_num=m.mem_num "
				   + "WHERE b.pro_num=?";
		   //PreparedStatement ��ü ����
		   pstmt = conn.prepareStatement(sql);
		   //?�� ������ ���ε�
		   pstmt.setInt(1, pro_num);
		   //SQL���� �����ؼ� ������� ResultSet ����
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
   //���� ���
   public List<GameReviewVO> getListReview(int start,int end, 
		   							int pro_num)throws Exception{
	   Connection conn = null;
	   PreparedStatement pstmt = null;
	   ResultSet rs = null;
	   List<GameReviewVO> list = null;
	   String sql = null;
	   
	   try {
		   //Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ��� �Ҵ�
		   conn = DBUtil.getConnection();
		   //SQL�� �ۼ�
		   sql = "SELECT * FROM (SELECT a.*,rownum rnum "
				   + "FROM (SELECT * FROM review b JOIN "
				   + "member m USING(mem_num) WHERE "
				   + "b.pro_num=? ORDER BY b.rev_num DESC)a) "
				   + "WHERE rnum>=? AND rnum<=?";
		   //PreparedStatement ��ü ����
		   pstmt = conn.prepareStatement(sql);
		   //?�� ������ ���ε�
		   pstmt.setInt(1, pro_num);
		   pstmt.setInt(2, start);
		   pstmt.setInt(3, end);
		   //SQL���� �����ؼ� �������� ResultSet�� ����
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
   
   //���� ��
   public GameReviewVO getReviewGame(int rev_num) throws Exception{
	   Connection conn = null;
	   PreparedStatement pstmt = null;
	   ResultSet rs = null;
	   GameReviewVO review = null;
	   String sql = null;
	   
	   try {
		   //Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ��� �Ҵ�
		   conn = DBUtil.getConnection();
		   //SQL�� �ۼ�
		   sql = "SELECT * FROM review WHERE rev_num=?";
		   //PreparedStatement ��ü ����
		   pstmt = conn.prepareStatement(sql);
		   //?�� ������ ���ε�
		   pstmt.setInt(1, rev_num);
		   //SQL���� �����ؼ� ������� ResultSet�� ����
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
   //���� ����
   public void updateReviewGame(GameReviewVO review)throws Exception{
	   Connection conn = null;
	   PreparedStatement pstmt = null;
	   String sql = null;
	   
	   try {
		   //Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ��� �Ҵ�
		   conn = DBUtil.getConnection();
		   //SQL�� �ۼ�
		   sql = "UPDATE review SET rev_content=?,"
				   + "rev_modifydate=SYSDATE,re_ip=? WHERE rev_num=?";
		   //PreparedStatement ��ü ����
		   pstmt = conn.prepareStatement(sql);
		   //?�� �����͸� ���ε�
		   pstmt.setString(1, review.getRev_content());
		   pstmt.setString(2, review.getRe_ip());
		   pstmt.setInt(3, review.getRev_num());
		   //SQL�� ����
		   pstmt.executeUpdate();
	   }catch(Exception e) {
		   throw new Exception(e);
	   }finally {
		   DBUtil.executeClose(null, pstmt, conn);
	   }
   }
   //���� ����
   public void deleteReviewGame(int rev_num) throws Exception{
	   Connection conn = null;
	   PreparedStatement pstmt = null;
	   String sql = null;
	   
	   try {
		   //Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ��� �Ҵ�
		   conn = DBUtil.getConnection();
		   //SQL�� �ۼ�
		   sql = "DELETE FROM review WHERE rev_num=?";
		   //PreparedStatement ��ü ����
		   pstmt = conn.prepareStatement(sql);
		   //?�� ������ ���ε�
		   pstmt.setInt(1, rev_num);
		   //SQL�� ����
		   pstmt.executeUpdate();
	   }catch(Exception e) {
		   throw new Exception(e);
	   }finally{
		   DBUtil.executeClose(null, pstmt, conn);
	   }
   }
}