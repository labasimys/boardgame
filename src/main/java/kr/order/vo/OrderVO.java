package kr.order.vo;

import java.sql.Date;

public class OrderVO {
	//order_main
	private int order_main_num; 	//주문번호
	private String order_main_name;	//주문상품이름
	private int order_main_total;	//총 가격
	private int payment;			//지불방식
	private int status;			//배송상태
	private String receive_name;	//이름
	private String receive_zipcode;		//우편번호
	private String receive_address1;	//주소
	private String receive_address2;	//상세주소
	private String receive_phone;		//번호
	private String notice;		//메모
	private Date order_main_date;	//구매 날짜
	private Date modify_date;	//수정 날짜
	private int mem_num;			//멤버번호
	
	//JOIN 사용
	private String mem_id;

	public int getOrder_main_num() {
		return order_main_num;
	}

	public void setOrder_main_num(int order_main_num) {
		this.order_main_num = order_main_num;
	}

	public String getOrder_main_name() {
		return order_main_name;
	}

	public void setOrder_main_name(String order_main_name) {
		this.order_main_name = order_main_name;
	}

	public int getOrder_main_total() {
		return order_main_total;
	}

	public void setOrder_main_total(int order_main_total) {
		this.order_main_total = order_main_total;
	}

	public int getPayment() {
		return payment;
	}

	public void setPayment(int payment) {
		this.payment = payment;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getReceive_name() {
		return receive_name;
	}

	public void setReceive_name(String receive_name) {
		this.receive_name = receive_name;
	}

	public String getReceive_zipcode() {
		return receive_zipcode;
	}

	public void setReceive_zipcode(String receive_zipcode) {
		this.receive_zipcode = receive_zipcode;
	}

	public String getReceive_address1() {
		return receive_address1;
	}

	public void setReceive_address1(String receive_address1) {
		this.receive_address1 = receive_address1;
	}

	public String getReceive_address2() {
		return receive_address2;
	}

	public void setReceive_address2(String receive_address2) {
		this.receive_address2 = receive_address2;
	}

	public String getReceive_phone() {
		return receive_phone;
	}

	public void setReceive_phone(String receive_phone) {
		this.receive_phone = receive_phone;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public Date getOrder_main_date() {
		return order_main_date;
	}

	public void setOrder_main_date(Date order_main_date) {
		this.order_main_date = order_main_date;
	}

	public Date getModify_date() {
		return modify_date;
	}

	public void setModify_date(Date modify_date) {
		this.modify_date = modify_date;
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
	
	

	

	
	
}
