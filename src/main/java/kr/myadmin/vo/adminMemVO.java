package kr.myadmin.vo;

import java.sql.Date;

public class adminMemVO {
	//MEMBER
	private int mem_num;	  //회원번호
	private String mem_id;	  //회원 아이디
	private int mem_auth;	  //회원 등급
	//MEMBER_DETAIL
	private String mem_name;  	  //회원 이름
	private Date mem_reg;	  	  //가입일
	private String mem_phone; 	  //전화번호
	private String mem_email; 	  //이메일
	private String mem_zipcode;   //우편번호
	private String mem_address1;  //주소
	private String mem_address2;  //상세주소
	
	private int rownum;		  //번호
	
	public int getRownum() {
		return rownum;
	}
	public void setRownum(int rownum) {
		this.rownum = rownum;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public int getMem_auth() {
		return mem_auth;
	}
	public void setMem_auth(int mem_auth) {
		this.mem_auth = mem_auth;
	}
	public String getMem_name() {
		return mem_name;
	}
	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
	}
	public Date getMem_reg() {
		return mem_reg;
	}
	public void setMem_reg(Date mem_reg) {
		this.mem_reg = mem_reg;
	}
	public String getMem_phone() {
		return mem_phone;
	}
	public void setMem_phone(String mem_phone) {
		this.mem_phone = mem_phone;
	}
	public String getMem_email() {
		return mem_email;
	}
	public void setMem_email(String mem_email) {
		this.mem_email = mem_email;
	}
	public String getMem_zipcode() {
		return mem_zipcode;
	}
	public void setMem_zipcode(String mem_zipcode) {
		this.mem_zipcode = mem_zipcode;
	}
	public String getMem_address1() {
		return mem_address1;
	}
	public void setMem_address1(String mem_address1) {
		this.mem_address1 = mem_address1;
	}
	public String getMem_address2() {
		return mem_address2;
	}
	public void setMem_address2(String mem_address2) {
		this.mem_address2 = mem_address2;
	}
	
	
}