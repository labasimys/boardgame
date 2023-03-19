package kr.reserve.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kr.reserve.vo.ReserveVO;
import kr.util.DBUtil;

public class ReserveDAO {
	   //�̱��� ����
	   private static ReserveDAO instance = new ReserveDAO();
	   
	   public static ReserveDAO getInstance() {
	      return instance;
	   }
	   private ReserveDAO() {}
	   
	   //���� ��
	   public ReserveVO getReserve(int res_num)throws Exception{
		   Connection conn = null;
		   PreparedStatement pstmt = null;
		   ResultSet rs = null;
		   ReserveVO res = null;
		   String sql = null;
		   
		   try {
			   conn = DBUtil.getConnection();
			   
			   sql = "SELECT * FROM RESERVE WHERE res_num=?";
			   
			   pstmt = conn.prepareStatement(sql);
			   
			   pstmt.setInt(1, res_num);
			   
			   rs = pstmt.executeQuery();
			   if(rs.next()) {
				   res = new ReserveVO();
				   
				   res.setRes_num(rs.getInt("res_num"));
				   res.setMem_num(rs.getInt("mem_num"));
				   res.setRes_date(rs.getString("res_date"));
				   res.setRes_time(rs.getString("res_time"));
				   res.setRes_count(rs.getInt("res_count"));
			   }
			   
		   }catch(Exception e) {
			   throw new Exception(e);
		   }finally {
			   DBUtil.executeClose(rs, pstmt, conn);
		   }
		   return res;
	   }
	   //ȸ�� �̸� �ҷ�����
		public ReserveVO getMemDetail(int mem_num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			ReserveVO detail = null;
			String sql = null;
			
			try {
				//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ��� �Ҵ�
				conn = DBUtil.getConnection();
				//SQL�� �ۼ�
				sql = "SELECT mem_name, mem_num FROM member_detail WHERE mem_num = ?";
				
				//PreparedStatement ��ü ����
				pstmt = conn.prepareStatement(sql);
				
				//?�� ������ ���ε�
				pstmt.setInt(1, mem_num);
				
				//SQL���� �����ؼ� ������� ResultSet�� ����
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					detail = new ReserveVO();
					
					detail.setMem_num(rs.getInt("mem_num"));
					detail.setMem_name(rs.getString("mem_name"));
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return detail;
		}
		
		//�� ��ȣ �ҷ�����
				public ReserveVO getRoom(int room_num)throws Exception{
					Connection conn = null;
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					ReserveVO room = null;
					String sql = null;
					
					try {
						//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ��� �Ҵ�
						conn = DBUtil.getConnection();
						//SQL�� �ۼ�
						sql = "SELECT room_num FROM room WHERE room_num = ?";
						
						//PreparedStatement ��ü ����
						pstmt = conn.prepareStatement(sql);
						
						//?�� ������ ���ε�
						pstmt.setInt(1, room_num);
						
						//SQL���� �����ؼ� ������� ResultSet�� ����
						rs = pstmt.executeQuery();
						
						if(rs.next()) {
							room = new ReserveVO();
							
							room.setRoom_num(rs.getInt("room_num"));
						}
					}catch(Exception e) {
						throw new Exception(e);
					}finally {
						DBUtil.executeClose(rs, pstmt, conn);
					}
					return room;
				}
				
				
		
	   //���� �Է��ϱ�
	   public void insertReservation(ReserveVO res, int room_num) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			
			try {
				//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ� �Ҵ�
				conn = DBUtil.getConnection();
				
				//SQL�� �ۼ�
				sql = "insert into reserve(res_num, mem_num, res_date, res_time, res_count, room_num) "
						+ "values(reserve_seq.nextval,?,?,?,?,?)";
				
				//PreparedStatement ��ü ����
				pstmt = conn.prepareStatement(sql);
				
				//?�� ������ ���ε�
				pstmt.setInt(1, res.getMem_num());
				pstmt.setString(2, res.getRes_date());
				pstmt.setString(3, res.getRes_time());
				pstmt.setInt(4, res.getRes_count());
				pstmt.setInt(5, res.getRoom_num());
				
				
				//SQL�� ����
				pstmt.executeUpdate();
				}catch(Exception e) {
					throw new Exception(e);
				}finally {
					DBUtil.executeClose(null, pstmt, conn);
				}
			}
	  
	   
	   //�ش� ��¥�� �´� �ð� �ҷ�����
	   public String getRsvTimes(String res_date, String room_num) throws Exception {
		   Connection conn = null;
		   PreparedStatement pstmt = null;
		   String sql = null;
		   ResultSet rs = null;
		   String res_times="";
		   
		   try {
			   conn = DBUtil.getConnection();
			   
			   sql = "SELECT RES_TIME FROM RESERVE WHERE RES_DATE = ?";
			   
			   pstmt=conn.prepareStatement(sql);
			   pstmt.setString(1, res_date);
			   rs = pstmt.executeQuery();
			   
			   while(rs.next()) {
				   res_times += rs.getString(1)+"/";
			   }
			   
		   }catch(Exception e) {
			   throw new Exception(e);
		   }finally {
			   DBUtil.executeClose(null, pstmt, conn);
		   }
		   return res_times;
	   }
}
