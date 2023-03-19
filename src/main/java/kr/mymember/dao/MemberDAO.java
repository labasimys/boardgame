package kr.mymember.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kr.mymember.vo.MemberVO;
import kr.util.DBUtil;

public class MemberDAO {
	//�̱��� ����
	private static MemberDAO instance = new MemberDAO();
	
	public static MemberDAO getInstance() {
		return instance;
	}
	private MemberDAO() {}
	
	//ȸ������
	public void insertMember(MemberVO member)
			                            throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		ResultSet rs = null;
		String sql = null;
		int num = 0; //������ ��ȣ ����
		
		try {
			//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			//���� Ŀ�� ����
			conn.setAutoCommit(false);
			
			//ȸ����ȣ(MEM_NUM) ����
			sql = "SELECT member_seq.nextval FROM dual";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				num = rs.getInt(1);
			}
			
			sql = "INSERT INTO memeber (mem_num,mem_id) VALUES (?,?)";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, num);//������(ȸ����ȣ)
			pstmt2.setString(2, member.getMem_id());
			pstmt2.executeUpdate();
			
			sql = "INSERT INTO memeber_detail (mem_num,mem_id,mem_pw,"
				+ "mem_name,mem_phone,mem_email,mem_zipcode,mem_address1,"
				+ "mem_address2) VALUES (?,?,?,?,?,?,?,?,?)";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setInt(1, num);//ȸ����ȣ
			pstmt3.setString(2, member.getMem_id());
			pstmt3.setString(3, member.getMem_pw());
			pstmt3.setString(4, member.getMem_name());
			pstmt3.setString(5, member.getMem_phone());
			pstmt3.setString(6, member.getMem_email());
			pstmt3.setString(7, member.getMem_zipcode());
			pstmt3.setString(8, member.getMem_address1());
			pstmt3.setString(9, member.getMem_address2());
			pstmt3.executeUpdate();
			
			//SQL ����� ��� �����ϸ� commit
			conn.commit();
		}catch(Exception e) {
			//SQL�� ����� �ϳ��� �����ϸ� RollBack
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
	}
	//ID �ߺ� üũ �� �α��� ó��
	public MemberVO checkMember(String id)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberVO member = null;
		String sql = null;
		
		try {
			//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			//sql
			sql = "SELECT * FROM member m LEFT OUTER JOIN "
				+ "member_detail d ON m.mem_num=d.mem_num "
				+ "WHERE m.mem_id=?";
			//PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			//?�� ������ ���ε�
			pstmt.setString(1, id);
			
			//SQL���� �����ؼ� ������� ResultSet�� ����
			rs = pstmt.executeQuery();
			if(rs.next()) {
				member = new MemberVO();
				member.setMem_num(rs.getInt("mem_num"));
				member.setMem_id(rs.getString("mem_id"));
				member.setMem_auth(rs.getInt("mem_auth"));
				member.setMem_pw(rs.getString("mem_pw"));
				member.setMem_photo(rs.getString("mem_photo"));
				member.setMem_phone(rs.getString("mem_phone"));
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return member;
	}
	//��й�ȣ �����ϱ����� �ʿ���(Ȯ��X)
	public MemberVO checkMember2(String id)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberVO member = null;
		String sql = null;
		
		try {
			//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			//sql
			sql = "SELECT * FROM member m LEFT OUTER JOIN "
				+ "member_detail d ON m.mem_num=d.mem_num "
				+ "WHERE m.mem_id=?";
			//PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			//?�� ������ ���ε�
			pstmt.setString(1, id);
			
			//SQL���� �����ؼ� ������� ResultSet�� ����
			rs = pstmt.executeQuery();
			if(rs.next()) {
				member = new MemberVO();
				member.setMem_auth(rs.getInt("mem_auth"));
				member.setMem_pw(rs.getString("mem_pw"));
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return member;
	}
	//ȸ���� ����
	public MemberVO getMember(int mem_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberVO member = null;
		String sql = null;
		
		try {
			//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ��� �Ҵ�
			conn = DBUtil.getConnection();
			//SQL�� �ۼ�
			sql = "SELECT * FROM member m JOIN member_detail d "
				+ "ON m.mem_num=d.mem_num WHERE m.mem_num=?";
			//PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			//?�� �����͸� ���ε�
			pstmt.setInt(1, mem_num);
			
			//SQL���� �����ؼ� ������� ResultSet�� ����
			rs = pstmt.executeQuery();
			if(rs.next()) {
				member = new MemberVO();
				member.setMem_num(rs.getInt("mem_num"));
				member.setMem_id(rs.getString("mem_id"));
				member.setMem_auth(rs.getInt("mem_auth"));
				member.setMem_pw(rs.getString("mem_pw"));
				member.setMem_name(rs.getString("mem_name"));
				member.setMem_phone(rs.getString("mem_phone"));
				member.setMem_email(rs.getString("mem_email"));
				member.setMem_zipcode(rs.getString("mem_zipcode"));
				member.setMem_address1(rs.getString("mem_address1"));
				member.setMem_address2(rs.getString("mem_address2"));
				member.setMem_photo(rs.getString("mem_photo"));
				member.setMem_reg(rs.getDate("mem_reg"));//������
				member.setMem_modify(rs.getDate("mem_modify"));//������
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return member;
	}
	//ȸ������ ����
	public void updateMember(MemberVO member)
			                             throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ��� �Ҵ�
			conn = DBUtil.getConnection();
			//SQL�� �ۼ�
			sql = "UPDATE member_detail SET mem_phone=?,"
				+ "mem_email=?,mem_zipcode=?,mem_address1=?,mem_address2=?,"
				+ "mem_modify=SYSDATE WHERE mem_num=?";
			//PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			//?�� �����͸� ���ε�
			pstmt.setString(1, member.getMem_phone());
			pstmt.setString(2, member.getMem_email());
			pstmt.setString(3, member.getMem_zipcode());
			pstmt.setString(4, member.getMem_address1());
			pstmt.setString(5, member.getMem_address2());
			pstmt.setInt(6, member.getMem_num());
			
			//SQL�� ����
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//��й�ȣ ����
	public void updatePassword(String passwd, int mem_num)
	                                       throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ��� �Ҵ�
			conn = DBUtil.getConnection();
			//SQL�� �ۼ�
			sql = "UPDATE member_detail SET mem_pw=? "
				+ "WHERE mem_num=?";
			//PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			//?�� �����͸� ���ε�
			pstmt.setString(1, passwd);//����й�ȣ
			pstmt.setInt(2, mem_num);//ȸ����ȣ
			//SQL�� ����
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//������ ���� ����
	public void updateMyPhoto(String photo, int mem_num)
	                                    throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ��� �Ҵ�
			conn = DBUtil.getConnection();
			//SQL�� �ۼ�
			sql = "UPDATE member_detail SET mem_photo=? WHERE mem_num=?";
			//PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			//?�� ������ ���ε�
			pstmt.setString(1, photo);
			pstmt.setInt(2, mem_num);
			//SQL�� ����
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//ȸ��Ż��(ȸ������ ����)
	public void deleteMember(int mem_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		String sql = null;
		
		try {
			//Ŀ�ؼ�Ǯ�κ��� Ŀ�ؼ� �Ҵ�
			conn = DBUtil.getConnection();
			//auto commit ����
			conn.setAutoCommit(false);
			
			//MEMBER�� auth �� ����
			sql = "UPDATE member SET mem_auth=0 WHERE mem_num=?";
			//PreparedStatement ��ü ����
			pstmt = conn.prepareStatement(sql);
			//?�� ������ ���ε�
			pstmt.setInt(1, mem_num);
			//SQL�� ����
			pstmt.executeUpdate();
			
			//MEMBER_DETAIL�� ���ڵ� ����
			sql = "DELETE FROM member_detail WHERE mem_num=?";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, mem_num);
			pstmt2.executeUpdate();
			
			//��� SQL���� ������ �����ϸ� commit
			conn.commit();
		}catch(Exception e) {
			//SQL�� ����� �ϳ��� �����ϸ� �ѹ�
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
}






