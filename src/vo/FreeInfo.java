package vo;

public class FreeInfo {
// 하나의 질문과답변 게시물 정보를 저장하기 위한 클래스
	private int bf_idx, bf_read, bf_good, bf_reply;
	private String mi_email, bf_nick, bf_title, bf_content;
	private String bf_img1, bf_img2, bf_img3, bf_isview, bf_date, last_date;
	
	public int getBf_idx() {
		return bf_idx;
	}
	public void setBf_idx(int bf_idx) {
		this.bf_idx = bf_idx;
	}
	public int getBf_read() {
		return bf_read;
	}
	public void setBf_read(int bf_read) {
		this.bf_read = bf_read;
	}
	public int getBf_good() {
		return bf_good;
	}
	public void setBf_good(int bf_good) {
		this.bf_good = bf_good;
	}
	public int getBf_reply() {
		return bf_reply;
	}
	public void setBf_reply(int bf_reply) {
		this.bf_reply = bf_reply;
	}
	public String getMi_email() {
		return mi_email;
	}
	public void setMi_email(String mi_email) {
		this.mi_email = mi_email;
	}
	public String getBf_nick() {
		return bf_nick;
	}
	public void setBf_nick(String bf_nick) {
		this.bf_nick = bf_nick;
	}
	public String getBf_title() {
		return bf_title;
	}
	public void setBf_title(String bf_title) {
		this.bf_title = bf_title;
	}
	public String getBf_content() {
		return bf_content;
	}
	public void setBf_content(String bf_content) {
		this.bf_content = bf_content;
	}
	public String getBf_img1() {
		return bf_img1;
	}
	public void setBf_img1(String bf_img1) {
		this.bf_img1 = bf_img1;
	}
	public String getBf_img2() {
		return bf_img2;
	}
	public void setBf_img2(String bf_img2) {
		this.bf_img2 = bf_img2;
	}
	public String getBf_img3() {
		return bf_img3;
	}
	public void setBf_img3(String bf_img3) {
		this.bf_img3 = bf_img3;
	}
	public String getBf_isview() {
		return bf_isview;
	}
	public void setBf_isview(String bf_isview) {
		this.bf_isview = bf_isview;
	}
	public String getBf_date() {
		return bf_date;
	}
	public void setBf_date(String bf_date) {
		this.bf_date = bf_date;
	}
	public String getLast_date() {
		return last_date;
	}
	public void setLast_date(String last_date) {
		this.last_date = last_date;
	}
}
