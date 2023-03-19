package kr.myorder.vo;

import java.sql.Date;

public class MyOrderVO {
	
	private int mem_num;
	private String pro_name;
	private int order_main_total;
	private Date order_main_date;
	private int order_main_num;
	private int order_main_count;
	private int status;
	private int order_detail_num;
	private int pro_num;
	private Date modify_date;
	private int pro_total;
	private String order_main_name;
	
	public int getOrder_main_count() {
		return order_main_count;
	}
	public void setOrder_main_count(int order_main_count) {
		this.order_main_count = order_main_count;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getOrder_detail_num() {
		return order_detail_num;
	}
	public void setOrder_detail_num(int order_detail_num) {
		this.order_detail_num = order_detail_num;
	}
	public int getPro_num() {
		return pro_num;
	}
	public void setPro_num(int pro_num) {
		this.pro_num = pro_num;
	}
	public Date getModify_date() {
		return modify_date;
	}
	public void setModify_date(Date modify_date) {
		this.modify_date = modify_date;
	}
	public int getPro_total() {
		return pro_total;
	}
	public void setPro_total(int pro_total) {
		this.pro_total = pro_total;
	}
	public String getOrder_main_name() {
		return order_main_name;
	}
	public void setOrder_main_name(String order_main_name) {
		this.order_main_name = order_main_name;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public String getPro_name() {
		return pro_name;
	}
	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}
	public int getOrder_main_total() {
		return order_main_total;
	}
	public void setOrder_main_total(int order_main_total) {
		this.order_main_total = order_main_total;
	}
	public Date getOrder_main_date() {
		return order_main_date;
	}
	public void setOrder_main_date(Date order_main_date) {
		this.order_main_date = order_main_date;
	}
	public int getOrder_main_num() {
		return order_main_num;
	}
	public void setOrder_main_num(int order_main_num) {
		this.order_main_num = order_main_num;
	}
	
	
	
	
}
