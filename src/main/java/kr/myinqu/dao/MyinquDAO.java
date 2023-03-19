package kr.myinqu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.myinqu.vo.MyinquVO;
import kr.util.DBUtil;
import kr.util.StringUtil;

public class MyinquDAO {
	
	//�̱��� ����
	private static MyinquDAO instance = new MyinquDAO();
	
	public static MyinquDAO getInstance() {
		return instance;
	}
	private MyinquDAO() {}
		
	
	
	//�� ���ڵ� ��
	public int getInQuCount(int mem_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			
			//SQL�� �ۼ�
			sql = "SELECT COUNT(*) FROM inquiry WHERE mem_num=?";
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
	
	
	
	//���� �� ���� ���
	public List<MyinquVO> getInquListBoard(int start, int end, int mem_num)
	                                   throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MyinquVO> list = null;
		String sql = null;
		
		try {
			//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ��� �Ҵ�
			conn = DBUtil.getConnection();
			
			//SQL�� �ۼ�
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
					+ "(SELECT * FROM inquiry WHERE mem_num=? ORDER BY inqu_num DESC)a) "
					+ "WHERE rnum>=? AND rnum<=?";
					
			
			//PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			//?�� ������ ���ε�
			pstmt.setInt(1, mem_num);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			
			//SQL���� �����ؼ� �������� ResultSet�� ����
			rs = pstmt.executeQuery();
			list = new ArrayList<MyinquVO>();
			while(rs.next()) {
				MyinquVO Myinqu = new MyinquVO();
				Myinqu.setInqu_num(rs.getInt("inqu_num"));
				Myinqu.setInqu_title(StringUtil.useNoHtml(rs.getString("inqu_title")));
				Myinqu.setInqu_content(StringUtil.useNoHtml(rs.getString("inqu_content")));
				Myinqu.setInqu_reg_date(rs.getDate("inqu_reg_date"));				
				list.add(Myinqu);
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}	
	
		
		
		
}
