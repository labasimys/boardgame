package kr.order.vo;

public class OrderDetailVO {
	//order_detail
	private int order_detail_num;	//���ֹ���ȣ
	private int pro_num;			//��ǰ��ȣ
	private String pro_name;  		//���Ż�ǰ�̸�
	private int pro_price;			//����
	private int pro_total;			//�Ѱ���
	private int order_main_count;	//��ǰ����
	private int order_main_num;		//�ֹ���ȣ
	
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
	public String getPro_name() {
		return pro_name;
	}
	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}
	public int getPro_price() {
		return pro_price;
	}
	public void setPro_price(int pro_price) {
		this.pro_price = pro_price;
	}
	public int getPro_total() {
		return pro_total;
	}
	public void setPro_total(int pro_total) {
		this.pro_total = pro_total;
	}
	public int getOrder_main_count() {
		return order_main_count;
	}
	public void setOrder_main_count(int order_main_count) {
		this.order_main_count = order_main_count;
	}
	public int getOrder_main_num() {
		return order_main_num;
	}
	public void setOrder_main_num(int order_main_num) {
		this.order_main_num = order_main_num;
	}
	
	
	
}
