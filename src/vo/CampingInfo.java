package vo;

public class CampingInfo {
// 하나의 캠핑후기 정보를 저장할 클래스
	private int cr_idx, cr_read, cr_good, cr_scrap, cr_reply;
	private String mi_email, cr_imgs, cr_pdtimgs, cr_content;
	private String cr_keyword, cr_last, cr_isview, cr_date, cr_history, cr_img;
	private String mi_nick, mi_introduce;	// 조인해서 가져올 정보
	
	
	public int getCr_idx() {
		return cr_idx;
	}
	public void setCr_idx(int cr_idx) {
		this.cr_idx = cr_idx;
	}
	public int getCr_read() {
		return cr_read;
	}
	public void setCr_read(int cr_read) {
		this.cr_read = cr_read;
	}
	public int getCr_good() {
		return cr_good;
	}
	public void setCr_good(int cr_good) {
		this.cr_good = cr_good;
	}
	public int getCr_scrap() {
		return cr_scrap;
	}
	public void setCr_scrap(int cr_scrap) {
		this.cr_scrap = cr_scrap;
	}
	public int getCr_reply() {
		return cr_reply;
	}
	public void setCr_reply(int cr_reply) {
		this.cr_reply = cr_reply;
	}
	public String getMi_email() {
		return mi_email;
	}
	public void setMi_email(String mi_email) {
		this.mi_email = mi_email;
	}
	public String getCr_imgs() {
		return cr_imgs;
	}
	public void setCr_imgs(String cr_imgs) {
		this.cr_imgs = cr_imgs;
	}
	public String getCr_pdtimgs() {
		return cr_pdtimgs;
	}
	public void setCr_pdtimgs(String cr_pdtimgs) {
		this.cr_pdtimgs = cr_pdtimgs;
	}
	public String getCr_content() {
		return cr_content;
	}
	public void setCr_content(String cr_content) {
		this.cr_content = cr_content;
	}
	public String getCr_keyword() {
		return cr_keyword;
	}
	public void setCr_keyword(String cr_keyword) {
		this.cr_keyword = cr_keyword;
	}
	public String getCr_last() {
		return cr_last;
	}
	public void setCr_last(String cr_last) {
		this.cr_last = cr_last;
	}
	public String getCr_isview() {
		return cr_isview;
	}
	public void setCr_isview(String cr_isview) {
		this.cr_isview = cr_isview;
	}
	public String getCr_date() {
		return cr_date;
	}
	public void setCr_date(String cr_date) {
		this.cr_date = cr_date;
	}
	public String getMi_nick() {
		return mi_nick;
	}
	public void setMi_nick(String mi_nick) {
		this.mi_nick = mi_nick;
	}
	public String getMi_introduce() {
		return mi_introduce;
	}
	public void setMi_introduce(String mi_introduce) {
		this.mi_introduce = mi_introduce;
	}
	public String getCr_history() {
		return cr_history;
	}
	public void setCr_history(String cr_history) {
		this.cr_history = cr_history;
	}
	public String getCr_img() {
		return cr_img;
	}
	public void setCr_img(String cr_img) {
		this.cr_img = cr_img;
	}
	
}
