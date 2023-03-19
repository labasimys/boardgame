package kr.room.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.room.vo.RoomVO;
import kr.util.DBUtil;

public class RoomDAO {
	//�̱��� ����
	private static RoomDAO instance = new RoomDAO();
		
	public static RoomDAO getInstance() {
		return instance;
	}
	
	private RoomDAO() {}
	
	//������/�� ���
	public void insertRoom(RoomVO room)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "insert into room(room_num, room_size, room_detail, room_detail2, room_name, photo1, photo2, photo3) values(room_seq.nextval,?,?,?,?,?,?,?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, room.getRoom_size());
			pstmt.setString(2, room.getRoom_detail());
			pstmt.setString(3, room.getRoom_detail2());
			pstmt.setString(4, room.getRoom_name());
			pstmt.setString(5, room.getPhoto1());
			pstmt.setString(6, room.getPhoto2());
			pstmt.setString(7, room.getPhoto3());
			
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		
	}
	
	//������/����� - ��ǰ ��
	public RoomVO getRoom(int room_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		RoomVO room = null;
		String sql = null;
		
		try {
			
			conn = DBUtil.getConnection();
			
			sql = "SELECT * FROM room WHERE room_num=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, room_num);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				room = new RoomVO();
				
				room.setRoom_num(rs.getInt("room_num"));
				room.setRoom_name(rs.getString("room_name"));
				room.setRoom_size(rs.getInt("room_size"));
				room.setRoom_detail(rs.getString("room_detail"));
				room.setRoom_detail(rs.getString("room_detail2"));
				room.setPhoto1(rs.getString("photo1"));
				room.setPhoto2(rs.getString("photo2"));
				room.setPhoto3(rs.getString("photo3"));
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return room;
	}
	
	//������/�� ���
	public List<RoomVO> getListRoom() throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<RoomVO> list = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			//SQL�� �ۼ�
			sql = "SELECT room_num, room_name, room_detail, room_detail2, photo1 FROM room";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<RoomVO>();
			
			while(rs.next()) {
				RoomVO room = new RoomVO();
				room.setRoom_num(rs.getInt("room_num"));
				room.setRoom_name(rs.getString("room_name"));
				room.setRoom_detail(rs.getString("room_detail"));
				room.setRoom_detail2(rs.getString("room_detail2"));
				room.setPhoto1(rs.getString("photo1"));
				
				list.add(room);
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
		//�� ����
		public void deleteRoom(int room_num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			
			try {
				//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ� �Ҵ�
				conn = DBUtil.getConnection();
				//SQL�� �ۼ�
				sql = "DELETE FROM room WHERE room_num=?";
				//PreparedStatement ��ü ����
				pstmt = conn.prepareStatement(sql);
				//?�� ������ ���ε�
				pstmt.setInt(1, room_num);
				//SQL�� ����
				pstmt.executeUpdate();
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
	}
