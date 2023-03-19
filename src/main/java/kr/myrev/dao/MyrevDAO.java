package kr.myrev.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.myrev.vo.MyrevVO;
import kr.util.DBUtil;
import kr.util.StringUtil;

public class MyrevDAO {
	//�̱��� ����
	private static MyrevDAO instance = new MyrevDAO();
	
	public static MyrevDAO getInstance() {
		return instance;
	}
	private MyrevDAO() {}
			
	//�� ���ڵ� ��(�˻� ���ڵ� ��)-> ??
	public int getRevCount(int mem_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			
			//SQL�� �ۼ�
			sql = "SELECT COUNT(*) FROM review WHERE mem_num=?";
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
			
	//���� �� ��� ���
	public List<MyrevVO> getReviewListBoard(int start, int end, int mem_num)
		            								throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MyrevVO> list = null;
		String sql = null;
		
		try {
		//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ��� �Ҵ�
		conn = DBUtil.getConnection();
		
		//SQL�� �ۼ�	
		sql = "SELECT * FROM (SELECT a.*, rownum rnum, p.pro_name FROM "
			+ "(SELECT * FROM review WHERE mem_num=? ORDER BY rev_num DESC) a "
			+ "JOIN product p ON a.pro_num=p.pro_num) "
			+ "WHERE rnum>=? AND rnum<=?";
		
		
		
		//PreparedStatement ��ü ����
		pstmt = conn.prepareStatement(sql);
		//?�� ������ ���ε�
		pstmt.setInt(1, mem_num);
		pstmt.setInt(2, start);
		pstmt.setInt(3, end);
		
		//SQL���� �����ؼ� �������� ResultSet�� ����
		rs = pstmt.executeQuery();
		list = new ArrayList<MyrevVO>();
		
		while(rs.next()) {
		MyrevVO Myrev = new MyrevVO();
		Myrev.setRev_num(rs.getInt("rev_num"));
		Myrev.setPro_num(rs.getInt("pro_num"));
		Myrev.setRev_content(StringUtil.useNoHtml(rs.getString("rev_content")));
		Myrev.setRev_date(rs.getDate("rev_date"));
		Myrev.setPro_name(StringUtil.useNoHtml(rs.getString("pro_name")));
		list.add(Myrev);
		}
		
		}catch(Exception e) {
		throw new Exception(e);
		}finally {
		DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}	
}
