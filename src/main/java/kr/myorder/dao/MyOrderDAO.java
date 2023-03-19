package kr.myorder.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.myorder.vo.MyOrderVO;
import kr.util.DBUtil;
import kr.util.StringUtil;

public class MyOrderDAO {
	//�̱��� ����
	private static MyOrderDAO instance = new MyOrderDAO();
	
	public static MyOrderDAO getInstance() {
		return instance;
	}
	private MyOrderDAO() {}
		
	//�� ���ڵ� ��
	public int getOrderCount(int mem_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			
			//SQL�� �ۼ�
			sql = "SELECT COUNT(*) FROM order_main WHERE mem_num=?";
			//PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			//?�� ������ ���ε�
			pstmt.setInt(1, mem_num);

			//SQL���� �����ϰ� ������� ResultSet ����
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
	
	//���� �ֹ� ���(����)
	public List<MyOrderVO> getOrderListBoard(int start, int end, int mem_num)
	                                   throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MyOrderVO> list = null;
		String sql = null;
		
		try {
			//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ��� �Ҵ�
			conn = DBUtil.getConnection();
			
			//SQL�� �ۼ�
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
					+ "(SELECT * FROM order_main WHERE mem_num=? ORDER BY order_main_num DESC)a) "
					+ "WHERE rnum>=? AND rnum<=?";
					
			
			//PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			//?�� ������ ���ε�
			pstmt.setInt(1, mem_num);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			
			//SQL���� �����ؼ� �������� ResultSet�� ����
			rs = pstmt.executeQuery();
			list = new ArrayList<MyOrderVO>();
			while(rs.next()) {
				MyOrderVO Myorder = new MyOrderVO();
				
				Myorder.setStatus(rs.getInt("status"));
				Myorder.setOrder_main_num(rs.getInt("order_main_num"));
				Myorder.setOrder_main_total(rs.getInt("order_main_total"));
				Myorder.setOrder_main_name(StringUtil.useNoHtml(rs.getString("order_main_name")));
				Myorder.setOrder_main_date(rs.getDate("order_main_date"));				
				
				list.add(Myorder);
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}	
		
	
}


