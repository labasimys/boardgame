package kr.review.vo;

public class GameReviewVO {
	private int rev_num;		//���� ���� ��ȣ
	private String rev_content;	//���� ����
	private String rev_date; 	//���� �� ��¥
	private String rev_modifydate; //���� ���� ��¥  <�߰�>�ؾ���
    private String re_ip;			//�� �ʿ�����???
	private int pro_num;		//��ǰ ���� ��ȣ (�ܷ�Ű �����ؾ���)
	private int mem_num;		//ȸ�� ���� ��ȣ (�ܷ�Ű)
	private String mem_id;		//ȸ�� ���̵� (�ܷ�Ű) <�߰�����>
	
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
