package kr.review.vo;

public class GameReviewVO {
	private int rev_num;		//리뷰 고유 번호
	private String rev_content;	//리뷰 내용
	private String rev_date; 	//리뷰 단 날짜
	private String rev_modifydate; //리뷰 수정 날짜  <추가>해야함
    private String re_ip;			//왜 필요한지???
	private int pro_num;		//상품 고유 번호 (외래키 설정해야함)
	private int mem_num;		//회원 고유 번호 (외래키)
	private String mem_id;		//회원 아이디 (외래키) <추가안함>
	
	public int getRev_num() {
		return rev_num;
	}
	public void setRev_num(int rev_num) {
		this.rev_num = rev_num;
	}
	public String getRev_content() {
		return rev_content;
	}
	public void setRev_content(String rev_content) {
		this.rev_content = rev_content;
	}
	public String getRev_date() {
		return rev_date;
	}
	public void setRev_date(String rev_date) {
		this.rev_date = rev_date;
	}
	public String getRev_modifydate() {
		return rev_modifydate;
	}
	public void setRev_modifydate(String rev_modifydate) {
		this.rev_modifydate = rev_modifydate;
	}
	public String getRe_ip() {
		return re_ip;
	}
	public void setRe_ip(String re_ip) {
		this.re_ip = re_ip;
	}
	public int getPro_num() {
		return pro_num;
	}
	public void setPro_num(int pro_num) {
		this.pro_num = pro_num;
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
