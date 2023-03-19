package kr.order.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.order.vo.OrderDetailVO;
import kr.order.vo.OrderVO;
import kr.util.DBUtil;

public class OrderDAO {
	private static OrderDAO instance = new OrderDAO();
	
	public static OrderDAO getInstance() {
		return instance;
	}
	private OrderDAO() {}
	
	//주문등록
	public void insertOrder(OrderVO order, List<OrderDetailVO> orderDetailList)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		PreparedStatement pstmt5 = null;
		ResultSet rs = null;
		String sql = null;
		//주문번호
		int order_main_num = 0;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//오토 커밋 해제
			conn.setAutoCommit(false);
			
			//주문 번호 구하기 - 테이블 두개이고 각 주문 번호 세팅을 위해
			sql = "SELECT order_main_seq.nextval FROM dual";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				order_main_num = rs.getInt(1);
			}
			
			//주문 정보 삽입 시퀀스 위에 미리 구해놓아서 그냥 ?를 씀
			sql = "INSERT INTO order_main(order_main_num,order_main_name,"
				+ "order_main_total,payment,receive_name,receive_zipcode,"
				+ "receive_address1,receive_address2,receive_phone,notice,"
				+ "mem_num) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
			//PreparedStatement 객체 생성
			pstmt2 = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt2.setInt(1, order_main_num);
			pstmt2.setString(2, order.getOrder_main_name());
			pstmt2.setInt(3, order.getOrder_main_total());
			pstmt2.setInt(4, order.getPayment());
			pstmt2.setString(5, order.getReceive_name());
			pstmt2.setString(6, order.getReceive_zipcode());
			pstmt2.setString(7, order.getReceive_address1());
			pstmt2.setString(8, order.getReceive_address2());
			pstmt2.setString(9, order.getReceive_phone());
			pstmt2.setString(10, order.getNotice());
			pstmt2.setInt(11, order.getMem_num());
			//SQL문 실행
			pstmt2.executeUpdate();
			
			//주문 상세 정보 삽입 - detail_num은 시퀀스로
			sql = "INSERT INTO order_detail (order_detail_num,"
				+ "pro_num,pro_name,pro_price,pro_total,"
				+ "order_main_count,order_main_num) VALUES (order_detail_seq.nextval,"
				+ "?,?,?,?,?,?)";
			pstmt3 = conn.prepareStatement(sql);
			
			for(int i=0;i<orderDetailList.size();i++) {
				OrderDetailVO orderDetail = orderDetailList.get(i);
				pstmt3.setInt(1, orderDetail.getPro_num());
				pstmt3.setString(2, orderDetail.getPro_name());
				pstmt3.setInt(3, orderDetail.getPro_price());
				pstmt3.setInt(4, orderDetail.getPro_total());
				pstmt3.setInt(5, orderDetail.getOrder_main_count());
				pstmt3.setInt(6, order_main_num);
				pstmt3.addBatch();//쿼리를 메모리에 올림 - 반복처리를 쿼리에 올려놓고 일괄처리
				
				//계속 추가하면 outOfMemory 발생, 
				//1000개 단위로 executeBatch()
				if(i % 1000 == 0) {
					pstmt3.executeBatch();
				}
			}
			pstmt3.executeBatch();//쿼리를 전송
			
			//상품의 재고수 차감
			sql = "UPDATE product SET pro_count=pro_count-? WHERE pro_num=?";
			pstmt4 = conn.prepareStatement(sql);
			for(int i=0;i<orderDetailList.size();i++) {
				OrderDetailVO orderDetail = orderDetailList.get(i);
				pstmt4.setInt(1, orderDetail.getOrder_main_count());
				pstmt4.setInt(2, orderDetail.getPro_num());
				pstmt4.addBatch();//쿼리를 메모리에 올림
				
				//계속 추가하면 outOfMemory 발생, 
				//1000개 단위로 executeBatch()
				if(i % 1000 == 0) {
					pstmt4.executeBatch();
				}
			}
			pstmt4.executeBatch();
			
			//장바구니에서 주문 상품 삭제
			sql = "DELETE FROM cart WHERE mem_num=?";
			pstmt5 = conn.prepareStatement(sql);
			pstmt5.setInt(1, order.getMem_num());
			pstmt5.executeUpdate();
			
			//모든 SQL문 정상 수행
			conn.commit();
			
		} catch (Exception e) {
			//하나의 SQL문이라도 예외가 발생하면 Rollback
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt5, null);
			DBUtil.executeClose(null, pstmt4, null);
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(rs, pstmt, conn);
		}
	}
	//관리자 - 전체 글 개수/검색글 개수
	//관리자 - 목록/검색글 목록
	//사용자 - 전체 글 개수/검색글 개수
	//사용자 - 목록/검색글 목록
	//개별 상품 목록
	public List<OrderDetailVO> getListOrderDetail(int order_main_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<OrderDetailVO> list = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM order_detail WHERE "
				+ "order_main_num = ? ORDER BY pro_num DESC";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, order_main_num);
			//SQL문 실행
			rs = pstmt.executeQuery();
			list = new ArrayList<OrderDetailVO>();
			while(rs.next()) {
				OrderDetailVO detail = new OrderDetailVO();
				detail.setPro_num(rs.getInt("pro_num"));
				detail.setPro_name(rs.getString("pro_name"));
				detail.setPro_price(rs.getInt("pro_price"));
				detail.setPro_total(rs.getInt("pro_total"));
				detail.setOrder_main_count(rs.getInt("order_main_count"));
				detail.setOrder_main_num(rs.getInt("order_main_num"));
			
				list.add(detail);
			}
			
		} catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	
	
	//주문 삭제(삭제 시 재고를 원상 복귀시키지 않음, 주문 취소일 때 원상 복귀)
	//관리자/주문자 주문 상세
	public OrderVO getOrder(int order_main_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OrderVO order = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM order_main WHERE order_main_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, order_main_num);
			//SQL문 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				order = new OrderVO();
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
			
			
		} catch (Exception e) {
			throw new Exception(e); 
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return order;
	}
	
	//주문 수정 
	public void updateOrder(OrderVO order)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE order_main SET status=?,receive_name=?,"
				+ "receive_zipcode=?,receive_address1=?,receive_address2=?,"
				+ "receive_phone=?,notice=?,modify_date=SYSDATE WHERE "
				+ "order_main_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, order.getStatus());
			pstmt.setString(2, order.getReceive_name());
			pstmt.setString(3, order.getReceive_zipcode());
			pstmt.setString(4, order.getReceive_address1());
			pstmt.setString(5, order.getReceive_address2());
			pstmt.setString(6, order.getReceive_phone());
			pstmt.setString(7, order.getNotice());
			pstmt.setInt(8, order.getOrder_main_num());
			//SQL문 실행
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//사용자 주문 취소
	public void updateOrderCancel(int order_main_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//오토 커밋 해제
			conn.setAutoCommit(false);
			
			sql = "UPDATE order_main SET status=5,modify_date=SYSDATE WHERE "
				+ "order_main_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, order_main_num);
			pstmt.executeUpdate();
			
			//주문번호에 해당하는 상품 정보 구하기
			List<OrderDetailVO> detailList = getListOrderDetail(order_main_num);
			//주문 취소로 주문 상품의 재고 수 환원
			sql = "UPDATE product SET pro_count=pro_count+? "
				+ "WHERE pro_num=?";
			pstmt2 = conn.prepareStatement(sql);
			for(int i=0;i<detailList.size();i++) {
				OrderDetailVO detail = detailList.get(i);
				pstmt2.setInt(1, detail.getOrder_main_count());
				pstmt2.setInt(2, detail.getPro_num());
				pstmt2.addBatch();
					
				if(i % 1000 == 0) {
					pstmt2.executeBatch();
				}
					
			}
			pstmt2.executeBatch();
				
			//모든 SQL문이 정상적으로 수행
			conn.commit();
				
		} catch (Exception e) {
			//SQL문이 하나라도 오류 발생
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, null);
		}
	}
	
}
