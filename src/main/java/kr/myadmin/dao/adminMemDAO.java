package kr.myadmin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.myadmin.vo.adminMemVO;
import kr.util.DBUtil;

public class adminMemDAO {
	//싱글턴 패턴
	private static adminMemDAO instance = new adminMemDAO();
				
	public static adminMemDAO getInstance() {
		return instance;
	}
	private adminMemDAO() {}
	
	//모든 회원의 수
	public int getCount() throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
	    ResultSet rs = null;
		int count = 0;
		String sql = null;
		      
		try {
			conn = DBUtil.getConnection();
				     
			sql = "SELECT COUNT(*) FROM member";
				       
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
		 
	 //회원 목록
	 public List<adminMemVO> getList(int startRow, int endRow) throws Exception{
		 Connection conn = null;
		 PreparedStatement pstmt = null;
		 ResultSet rs = null;
		 List<adminMemVO> list = null;
		 String sql = null;
			      
		 try {
			conn = DBUtil.getConnection();
				         
			sql = "SELECT * FROM (SELECT a.*, rownum FROM (SELECT m.*, d.mem_name, d.mem_reg FROM member m, member_detail d "
				+ "WHERE m.mem_num = d.mem_num ORDER BY m.mem_num ASC)a) WHERE rownum>=? AND rownum<=?";
				  
			pstmt = conn.prepareStatement(sql);
			      
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			         
			rs = pstmt.executeQuery();
			list = new ArrayList<adminMemVO>();
		         
		    while(rs.next()) {
		       adminMemVO adminMemVO = new adminMemVO();
		       
		       adminMemVO.setRownum(rs.getInt("rownum"));
		       adminMemVO.setMem_num(rs.getInt("mem_num"));
		       adminMemVO.setMem_id(rs.getString("mem_id"));
		       adminMemVO.setMem_auth(rs.getInt("mem_auth")); 
		       adminMemVO.setMem_name(rs.getString("mem_name"));
		       adminMemVO.setMem_reg(rs.getDate("mem_reg"));
		       
		       list.add(adminMemVO);
		    }     
		   }catch (Exception e) {
		      throw new Exception(e);
		   }finally {
		      DBUtil.executeClose(rs, pstmt, conn);
		   }
		   return list;
	 }
	 
	//회원 상세정보
	public adminMemVO getDetail(int mem_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		adminMemVO detail = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();

			sql = "SELECT m.mem_id, m.mem_auth, d.* FROM member m, member_detail d WHERE m.mem_num = d.mem_num AND m.mem_num = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, mem_num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				detail = new adminMemVO();
				
				detail.setMem_num(rs.getInt("mem_num"));
				detail.setMem_id(rs.getString("mem_id"));
 		        detail.setMem_auth(rs.getInt("mem_auth")); 
		        detail.setMem_name(rs.getString("mem_name"));
		        detail.setMem_reg(rs.getDate("mem_reg"));
		        detail.setMem_phone(rs.getString("mem_phone"));
		        detail.setMem_email(rs.getString("mem_email"));
		        detail.setMem_zipcode(rs.getString("mem_zipcode"));
		        detail.setMem_address1(rs.getString("mem_address1"));
		        detail.setMem_address2(rs.getString("mem_address2"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return detail;
	}
	
	//회원등급 수정
	public void updateAuth(int mem_auth, int mem_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "UPDATE member SET mem_auth=? WHERE mem_num=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, mem_auth);  //수정할 회원등급
			pstmt.setInt(2, mem_num);  //회원번호
			
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
}
