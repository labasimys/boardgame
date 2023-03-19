package kr.myadmin.vo;

public class adminResVO {
	//RESERVE
	private int res_num;  	 //예약번호
	private String res_date; //예약날짜
	private String res_time; //예약시간
	private int res_count;   //인원수
	//ROOM
	private String room_detail;	 //방 설명
	private String room_name;	 //방 이름
		
	private String mem_name;  //회원이름

	public int getRes_num() {
		return res_num;
	}

	public void setRes_num(int res_num) {
		this.res_num = res_num;
	}

	public String getRes_date() {
		return res_date;
	}

	public void setRes_date(String res_date) {
		this.res_date = res_date;
	}

	public String getRes_time() {
		return res_time;
	}

	public void setRes_time(String res_time) {
		this.res_time = res_time;
	}

	public int getRes_count() {
		return res_count;
	}

	public void setRes_count(int res_count) {
		this.res_count = res_count;
	}

	public String getRoom_detail() {
		return room_detail;
	}

	public void setRoom_detail(String room_detail) {
		this.room_detail = room_detail;
	}

	public String getRoom_name() {
		return room_name;
	}

	public void setRoom_name(String room_name) {
		this.room_name = room_name;
	}

	public String getMem_name() {
		return mem_name;
	}

	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
	}
}
