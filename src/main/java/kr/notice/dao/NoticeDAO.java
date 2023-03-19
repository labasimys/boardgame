package kr.notice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.notice.vo.NoticeVO;
import kr.util.DBUtil;
  
public class NoticeDAO {
	//�̱��� ����
	private static NoticeDAO instance = new NoticeDAO();
		
	public static NoticeDAO getInstance() {
		return instance;
	}
	
	private NoticeDAO() {}
	
	//���� �� ���
	public void insertNotice(NoticeVO notice) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "insert into notice (noti_num, noti_title, noti_content, noti_file)"
				+ "values (notice_seq.nextval, ?, ?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, notice.getNoti_title());
			pstmt.setString(2, notice.getNoti_content());
			pstmt.setString(3, notice.getNoti_file());
			
			pstmt.executeUpdate();
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//���� �� ����
	public int getCount() throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			
			sql = "select count(*) from notice";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}
	
	//���� �� ���
	public List<NoticeVO> getNoticeList(int startRow, int endRow) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<NoticeVO> list = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "select * from (select a.*, rownum rnum from (select * from notice order by noti_num desc) a) where rnum >= ? and rnum <= ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<NoticeVO>();
			while(rs.next()) {
				NoticeVO notice = new NoticeVO();
				
				notice.setRownum(rs.getInt("rnum"));
				notice.setNoti_num(rs.getInt("noti_num"));
				notice.setNoti_title(rs.getString("noti_title"));
				notice.setNoti_content(rs.getString("noti_content"));
				notice.setNoti_hit(rs.getInt("noti_hit"));
				notice.setNoti_reg_date(rs.getDate("noti_reg_date"));
				
				list.add(notice);
			}
		} catch(Exception e) {
			
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	
	//���� �� ��
	public NoticeVO getNotice(int noti_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		NoticeVO notice = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "select * from notice where noti_num = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, noti_num);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				notice = new NoticeVO();
				
				notice.setNoti_num(rs.getInt("noti_num"));
				notice.setNoti_title(rs.getString("noti_title"));
				notice.setNoti_content(rs.getString("noti_content"));
				notice.setNoti_file(rs.getString("noti_file"));
				notice.setNoti_hit(rs.getInt("noti_hit"));
				notice.setNoti_reg_date(rs.getDate("noti_reg_date"));
			}
		} catch(Exception e) {
			throw new Exception(e);
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return notice;
	}
	
	//��ȸ�� ����
	public void updateReadcount(int noti_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ��� �Ҵ�
			conn = DBUtil.getConnection();
			
			//SQL�� �ۼ�
			sql = "update notice set noti_hit = noti_hit + 1 where noti_num = ?";
			
			//PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			
			//?�� �����͸� ���ε�
			pstmt.setInt(1, noti_num);
			
			//SQL�� ����
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//���� ����
	public void deleteFile(int noti_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			
			//SQL�� �ۼ�
			sql = "update notice set noti_file = '' where noti_num = ?";
			
			//PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			
			//?�� ������ ���ε�
			pstmt.setInt(1, noti_num);
			
			//SQL�� ����
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//���� �� ����
	public void updateNotice(NoticeVO notice) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		
		try {
			//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			
			//���۵� ���� ���� üũ
			if(notice.getNoti_file() != null) {
				sub_sql += ", noti_file = ?";
			}

			sql = "update notice set noti_title = ?, noti_content = ?" + sub_sql + " where noti_num = ?"; 
			
			//PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			
			//?�� ������ ���ε�
			pstmt.setString(++cnt, notice.getNoti_title());
			pstmt.setString(++cnt, notice.getNoti_content());
			if(notice.getNoti_file() != null) {
				pstmt.setString(++cnt, notice.getNoti_file());
			}
			pstmt.setInt(++cnt, notice.getNoti_num());
			
			//SQL�� ����
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}	
	
	//���� �� ����
	public void deleteNotice(int noti_num) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ��� �Ҵ�
			conn = DBUtil.getConnection();
			
			sql = "delete from notice where noti_num = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, noti_num);
			
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
}
