package kr.myadmin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.myadmin.vo.adminResVO;
import kr.util.DBUtil;

public class adminResDAO {
	//싱글턴 패턴
	private static adminResDAO instance = new adminResDAO();
			
	public static adminResDAO getInstance() {
		return instance;
	}
	private adminResDAO() {}
	
	//글의 총 개수
	public int getCount() throws Exception{
	   Connection conn = null;
	   PreparedStatement pstmt = null;
	   ResultSet rs = null;
	   int count = 0;
	   String sql = null;
	      
	   try {
	      conn = DBUtil.getConnection();
	       
	      sql = "SELECT COUNT(*) FROM reserve";
	       
	      pstmt = conn.prepareStatement(sql);
	      
	      rs = pstmt.executeQuery();
	      if(rs.next()) {
	         count = rs.getInt(1);
	      }
	   }catch (Exception e) {
	      throw new Exception(e);
	   }finally {
	      DBUtil.executeClose(rs, pstmt, conn);
	   }
		   return count;
	 }
	 
	//글 목록
	public List<adminResVO> getList(int startRow, int endRow) throws Exception{
	   Connection conn = null;
	   PreparedStatement pstmt = null;
	   ResultSet rs = null;
	   List<adminResVO> list = null;
	   String sql = null;
		      
	   try {
		  conn = DBUtil.getConnection();
		         
		  sql = "SELECT * FROM (SELECT a.*, rownum FROM (SELECT r.*, o.room_detail, o.room_name, m.mem_name FROM reserve r, room o, member_detail m "
			+ "WHERE r.room_num = o.room_num AND r.mem_num = m.mem_num ORDER BY r.res_num DESC)a) WHERE rownum>=? AND rownum<=?";
		  
	      pstmt = conn.prepareStatement(sql);

	      pstmt.setInt(1, startRow);
	      pstmt.setInt(2, endRow);
	      
	      rs = pstmt.executeQuery();
	      list = new ArrayList<adminResVO>();
	         
	      while(rs.next()) {
	         adminResVO adminResVO = new adminResVO();
	         adminResVO.setRes_num(rs.getInt("res_num"));
	         adminResVO.setRes_date(rs.getString("res_date")); 
	         adminResVO.setRes_time(rs.getString("res_time"));
	         adminResVO.setRoom_detail(rs.getString("room_detail"));
	         adminResVO.setRoom_name(rs.getString("room_name"));
	         adminResVO.setMem_name(rs.getString("mem_name"));
	            
	         list.add(adminResVO);
	      }
	         
	   } catch (Exception e) {
	      throw new Exception(e);
	   }finally {
	      DBUtil.executeClose(rs, pstmt, conn);
	   }
	   return list;
	}
}
