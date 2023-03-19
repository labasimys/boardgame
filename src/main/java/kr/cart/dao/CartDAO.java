package kr.cart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.cart.vo.CartVO;
import kr.list.vo.ListVO;
import kr.util.DBUtil;

public class CartDAO {
	private static CartDAO instance = new CartDAO();
	
	public static CartDAO getInstance() {
		return instance;
	}
	private CartDAO() {}
	
	//장바구니 등록
	public void insertCart(CartVO cart)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "INSERT INTO cart (cart_num,pro_num,"
				+ "cart_count,mem_num) VALUES ("
				+ "cart_seq.nextval,?,?,?)";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, cart.getPro_num());
			pstmt.setInt(2, cart.getCart_count());
			pstmt.setInt(3, cart.getMem_num());
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//회원번호(mem_num)별 총 구입액
	public int getTotalByMem_num(int mem_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int total = 0;
		
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT SUM(sub_total) FROM "
				+ "(SELECT c.mem_num, "
				+ "c.cart_count * i.pro_price as sub_total "
				+ "FROM cart c JOIN product i ON "
				+ "c.pro_num = i.pro_num) WHERE mem_num = ?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, mem_num);
			//SQL문을 실행해서 결과행을 ResultSet에 담음
			rs = pstmt.executeQuery();
			if(rs.next()) {
				total = rs.getInt(1);
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return total;
	}
	
	//장바구니 목록
	public List<CartVO> getListCart(int mem_num)
			                          throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CartVO> list = null;
		String sql = null;

		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM cart c JOIN product i ON "
				+ "c.pro_num = i.pro_num WHERE c.mem_num = ? "
				+ "ORDER BY i.pro_num ASC";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, mem_num);
			//SQL문을 실행해서 결과행을 ResultSet에 담음
			rs = pstmt.executeQuery();
			list = new ArrayList<CartVO>();
			
			while(rs.next()) {
				CartVO cart = new CartVO();
				cart.setCart_num(rs.getInt("cart_num"));
				cart.setPro_num(rs.getInt("pro_num"));
				cart.setCart_count(
						   rs.getInt("cart_count"));
				cart.setMem_num(rs.getInt("mem_num"));
				
				ListVO Pro = new ListVO();
				Pro.setPro_name(rs.getString("pro_name"));
				Pro.setPro_price(rs.getInt("pro_price"));
				Pro.setPro_picture(rs.getString("pro_picture"));
				Pro.setPro_count(rs.getInt("pro_count"));
				Pro.setPro_status(rs.getInt("pro_status"));
				
				cart.setListVo(Pro);
				
				//같은 상품의 총 구매 비용 연산
				cart.setSub_total(
					cart.getCart_count()*Pro.getPro_price());
				
				list.add(cart);			
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
			                          
	//장바구니 상세
	public CartVO getCart(CartVO cart)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CartVO cartSaved = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM cart WHERE pro_num=? AND mem_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, cart.getPro_num());
			pstmt.setInt(2, cart.getMem_num());
			//SQL문 실행
			pstmt.executeUpdate();
			//SQL문을 실행해서 결과행을 ResultSet에 담음
			rs = pstmt.executeQuery();
			if(rs.next()) {
				cartSaved = new CartVO();
				cartSaved.setCart_num(rs.getInt("cart_num"));
				cartSaved.setPro_num(rs.getInt("pro_num"));
				cartSaved.setCart_count(rs.getInt("cart_count"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return cartSaved;
	}
	
	//장바구니 수정 (개별 상품 수량 수정)
	public void updateCart(CartVO cart)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE cart SET cart_count=? "
				+ "WHERE cart_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, cart.getCart_count());
			pstmt.setInt(2, cart.getCart_num());
			//SQL문을 실행
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//장바구니 상품번호와 회원번호별 수정
	public void updateCartBypro_num(CartVO cart)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE cart SET cart_count=? "
				+ "WHERE pro_num=? AND mem_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, cart.getCart_count());
			pstmt.setInt(2, cart.getPro_num());
			pstmt.setInt(3, cart.getMem_num());
			//SQL문을 실행
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//장바구니 삭제
	public void deleteCart(int cart_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "DELETE FROM cart WHERE cart_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, cart_num);
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
}