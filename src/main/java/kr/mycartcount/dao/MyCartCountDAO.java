package kr.mycartcount.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kr.util.DBUtil;

public class MyCartCountDAO {
	
	//�̱��� ����
	private static MyCartCountDAO instance = new MyCartCountDAO();
	
	public static MyCartCountDAO getInstance() {
		return instance;
	}
	private MyCartCountDAO() {}
	
	//�� ���ڵ� ��
	public int getMyCartCount(int mem_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			
			//SQL�� �ۼ�
			sql = "SELECT COUNT(*) FROM cart WHERE mem_num=?";
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
}
