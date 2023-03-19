package kr.list.vo;

public class ListVO {
	   private int pro_num;         
	   private String pro_name;       
	   private int pro_price;           
	   private String pro_picture;            
	   private int pro_count;         
	   private String pro_level;      
	   private int pro_status;		//0이면 1이면
	   private int mem_num;			//아이디 고유 번호
	   private String explanation; 
	   private int person;
	   
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
	public String getPro_picture() {
		return pro_picture;
	}
	public void setPro_picture(String pro_picture) {
		this.pro_picture = pro_picture;
	}
	public int getPro_count() {
		return pro_count;
	}
	public void setPro_count(int pro_count) {
		this.pro_count = pro_count;
	}
	public String getPro_level() {
		return pro_level;
	}
	public void setPro_level(String pro_level) {
		this.pro_level = pro_level;
	}
	public int getPro_status() {
		return pro_status;
	}
	public void setPro_status(int pro_status) {
		this.pro_status = pro_status;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public String getExplanation() {
		return explanation;
	}
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	public int getPerson() {
		return person;
	}
	public void setPerson(int person) {
		this.person = person;
	}
	
   
}
