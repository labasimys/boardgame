package kr.myadmin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.myadmin.vo.adminOrderDetailVO;
import kr.myadmin.vo.adminOrderVO;
import kr.order.vo.OrderVO;
import kr.util.DBUtil;

public class adminOrderDAO {
	//싱글턴 패턴
	private static adminOrderDAO instance = new adminOrderDAO();
		
	public static adminOrderDAO getInstance() {
		return instance;
	}
	private adminOrderDAO() {}
	
	//주문 총 개수
	public int getCount() throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
	    ResultSet rs = null;
		int count = 0;
		String sql = null;
		      
		try {
			conn = DBUtil.getConnection();
				       
			sql = "SELECT COUNT(*) FROM order_main";
				       
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
			 
	 //주문 목록
	 public List<adminOrderVO> getList(int startRow, int endRow) throws Exception{
		 Connection conn = null;
		 PreparedStatement pstmt = null;
		 ResultSet rs = null;
		 List<adminOrderVO> list = null;
		 String sql = null;
			      
		 try {
			conn = DBUtil.getConnection();
				       
			sql = "SELECT * FROM (SELECT a.*, rownum FROM (SELECT o.*, m.mem_name FROM order_main o, member_detail m WHERE o.mem_num = m.mem_num "
					+ "ORDER BY o.order_main_num DESC)a) WHERE rownum>=? AND rownum<=?";
				  
			pstmt = conn.prepareStatement(sql);
			      
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			         
			rs = pstmt.executeQuery();
			list = new ArrayList<adminOrderVO>();
		         
		    while(rs.next()) {
		       adminOrderVO order = new adminOrderVO();
		       
		       order.setRownum(rs.getInt("rownum"));
		       order.setOrder_main_num(rs.getInt("order_main_num"));
		       order.setOrder_main_name(rs.getString("order_main_name"));
		       order.setMem_name(rs.getString("mem_name"));
		       order.setOrder_main_date(rs.getDate("order_main_date")); 
		       order.setOrder_main_total(rs.getInt("order_main_total"));
		       order.setStatus(rs.getInt("status"));
		       
		       list.add(order);
		    }     
		   }catch (Exception e) {
		      throw new Exception(e);
		   }finally {
		      DBUtil.executeClose(rs, pstmt, conn);
		   }
		   return list;
	 }
	 
	//주문 상세정보(개별)
	public List<adminOrderDetailVO> getDetail(int order_main_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<adminOrderDetailVO> list = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT * FROM order_detail WHERE order_main_num=? ORDER BY pro_num DESC";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, order_main_num);
			
			rs = pstmt.executeQuery();
			
			list = new ArrayList<adminOrderDetailVO>();
					
			while(rs.next()) {
				adminOrderDetailVO detail = new adminOrderDetailVO();
				
				detail.setPro_num(rs.getInt("pro_num"));
		        detail.setPro_name(rs.getString("pro_name"));
		        detail.setPro_price(rs.getInt("pro_price"));
		        detail.setPro_total(rs.getInt("pro_total"));
		        detail.setOrder_main_count(rs.getInt("order_main_count"));
		        detail.setOrder_main_num(rs.getInt("order_main_num"));
		        
		        list.add(detail);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	
	//주문 상세정보(배송지 정보)
	public adminOrderVO getOrder(int order_main_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		adminOrderVO order = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();

			sql = "SELECT * FROM order_main WHERE order_main_num=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, order_main_num);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				order = new adminOrderVO();
				
				order.setOrder_main_num(rs.getInt("order_main_num"));
				order.setOrder_main_name(rs.getString("order_main_name"));
				order.setOrder_main_total(rs.getInt("order_main_total"));
				order.setPayment(rs.getInt("payment"));
				order.setStatus(rs.getInt("status"));
				order.setReceive_name(rs.getString("receive_name"));
				order.setReceive_zipcode(rs.getString("receive_zipcode"));
				order.setReceive_address1(rs.getString("receive_address1"));
				order.setReceive_address2(rs.getString("receive_address2"));
				order.setReceive_phone(rs.getString("receive_phone"));
				order.setNotice(rs.getString("notice"));
				order.setOrder_main_date(rs.getDate("order_main_date"));
				order.setMem_num(rs.getInt("mem_num"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return order;
	}
	//배송상태 수정
	public void updateStatus(int status, int order_main_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "UPDATE order_main SET status=? WHERE order_main_num=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, status);  //수정할 배송상태
			pstmt.setInt(2, order_main_num);  //회원번호
			
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
}