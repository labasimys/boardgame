package kr.myreserve.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.myreserve.vo.MyReserveVO;
import kr.util.DBUtil;
import kr.util.StringUtil;

public class MyReserveDAO {

	//�̱��� ����
	private static MyReserveDAO instance = new MyReserveDAO();
	
	public static MyReserveDAO getInstance() {
		return instance;
	}
	private MyReserveDAO() {}
	
	
	
	//�� ���ڵ� �� - ������
	public int getMyReserveCount(int mem_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			
			//SQL�� �ۼ�
			sql = "SELECT COUNT(*) FROM reserve WHERE mem_num=?";
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
	
	
	
	//�� ���� ��� - ��������(���೯¥�� ����)
	public List<MyReserveVO> getMyReserveListBefore(int start, int end, int mem_num)
	                                   throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MyReserveVO> list = null;
		String sql = null;
		
		try {
			//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ��� �Ҵ�
			conn = DBUtil.getConnection();
			
			//SQL�� �ۼ�
			sql = "SELECT * FROM (SELECT r.*,m.room_name,m.room_size,m.room_detail, rownum rnum FROM (SELECT * FROM reserve WHERE mem_num=? ORDER BY res_num)r JOIN room m ON m.room_num=r.room_num) WHERE rnum>=? AND rnum<=? ORDER BY res_date DESC";
			//PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			//?�� ������ ���ε�
			pstmt.setInt(1, mem_num);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			
			//SQL���� �����ؼ� �������� ResultSet�� ����
			rs = pstmt.executeQuery();
			list = new ArrayList<MyReserveVO>();
			while(rs.next()) {
				MyReserveVO MyRe = new MyReserveVO();
				MyRe.setRes_num(rs.getInt("res_num"));
				MyRe.setRes_date(StringUtil.useNoHtml(rs.getString("res_date")));
				MyRe.setRes_time(StringUtil.useNoHtml(rs.getString("res_time")));				
				MyRe.setRoom_name(StringUtil.useNoHtml(rs.getString("room_name")));
				MyRe.setRoom_size(rs.getInt("room_size"));
				MyRe.setRoom_detail(StringUtil.useNoHtml(rs.getString("room_detail")));
				
				list.add(MyRe);
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}	
	
	//�� ���ڵ� �� - ���ÿ���
	public int getMyReserveCountToday(int mem_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int todaycount = 0;
		
		try {
			//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			
			//SQL�� �ۼ�
			sql = "SELECT COUNT(*) FROM reserve WHERE mem_num=? AND res_date=TO_CHAR(SYSDATE, 'YY/MM/DD')";
			//PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			//?�� ������ ���ε�
			pstmt.setInt(1, mem_num);

			//SQL���� �����ϰ� ������� ResultSet ����
			rs = pstmt.executeQuery();
			if(rs.next()) {
				todaycount = rs.getInt(1);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return todaycount;
	}
	
	//���� ���� ����
	public MyReserveVO getMyReserveToday(int mem_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MyReserveVO MyRe = null;
		String sql = null;
		
		try {
			//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ��� �Ҵ�
			conn = DBUtil.getConnection();
			//SQL�� �ۼ�
			sql = "SELECT * FROM reserve r JOIN room m ON r.room_num=m.room_num WHERE r.mem_num=? AND r.res_date=TO_CHAR(SYSDATE, 'YY/MM/DD')";
			//PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			//?�� ������ ���ε�
			pstmt.setInt(1, mem_num);
			//SQL���� �����ؼ� ������� ResultSet�� ����
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				MyRe = new MyReserveVO();
				
				MyRe.setRes_date(StringUtil.useNoHtml(rs.getString("res_date")));
				MyRe.setRes_time(StringUtil.useNoHtml(rs.getString("res_time")));				
				MyRe.setRoom_name(StringUtil.useNoHtml(rs.getString("room_name")));
				MyRe.setRoom_size(rs.getInt("room_size"));
				MyRe.setRoom_detail(StringUtil.useNoHtml(rs.getString("room_detail")));
				
				MyRe.setPhoto1(rs.getString("photo1"));
				//MyRe.setPhoto2(StringUtil.useNoHtml(rs.getString("photo2")));
				//MyRe.setPhoto3(StringUtil.useNoHtml(rs.getString("photo3")));
				MyRe.setRes_num(rs.getInt("res_num"));//���߿� �� ���� ������ �ϴ� �߰�
				
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return MyRe;
	}		
}	
