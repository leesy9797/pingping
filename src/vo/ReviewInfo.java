package vo;

public class ReviewInfo {
// 하나의 리뷰 정보를 저장할 클래스
	private int pr_idx, pr_star;
	private String mi_email, od_id, pi_id, br_opt;	// pk : 회원이메일, 주문상세번호, 상품ID, 선택한 옵션
	private String pr_content, pr_img1, pr_img2, pr_img3, pr_isview, pr_date;
	private String br_name, pi_name, pi_img1;		// 상품 정보 테이블에서 가져올 정보들
	private int od_pdtprice, od_cnt;	// 주문 상세 테이블에서 가져올 정보들
	private String oi_id, od_option, od_status;
	
	public String getMi_email() {
		return mi_email;
	}
	public void setMi_email(String mi_email) {
		this.mi_email = mi_email;
	}
	public String getOd_id() {
		return od_id;
	}
	public void setOd_id(String od_id) {
		this.od_id = od_id;
	}
	public String getPi_id() {
		return pi_id;
	}
	public void setPi_id(String pi_id) {
		this.pi_id = pi_id;
	}
	public String getBr_opt() {
		return br_opt;
	}
	public void setBr_opt(String br_opt) {
		this.br_opt = br_opt;
	}
	public String getPr_content() {
		return pr_content;
	}
	public void setPr_content(String pr_content) {
		this.pr_content = pr_content;
	}
	public String getPr_img1() {
		return pr_img1;
	}
	public void setPr_img1(String pr_img1) {
		this.pr_img1 = pr_img1;
	}
	public String getPr_img2() {
		return pr_img2;
	}
	public void setPr_img2(String pr_img2) {
		this.pr_img2 = pr_img2;
	}
	public String getPr_img3() {
		return pr_img3;
	}
	public void setPr_img3(String pr_img3) {
		this.pr_img3 = pr_img3;
	}
	public String getPr_isview() {
		return pr_isview;
	}
	public void setPr_isview(String pr_isview) {
		this.pr_isview = pr_isview;
	}
	public String getPr_date() {
		return pr_date;
	}
	public void setPr_date(String pr_date) {
		this.pr_date = pr_date;
	}
	public int getPr_idx() {
		return pr_idx;
	}
	public void setPr_idx(int pr_idx) {
		this.pr_idx = pr_idx;
	}
	public int getPr_star() {
		return pr_star;
	}
	public void setPr_star(int pr_star) {
		this.pr_star = pr_star;
	}
	public String getBr_name() {
		return br_name;
	}
	public void setBr_name(String br_name) {
		this.br_name = br_name;
	}
	public String getPi_name() {
		return pi_name;
	}
	public void setPi_name(String pi_name) {
		this.pi_name = pi_name;
	}
	public String getPi_img1() {
		return pi_img1;
	}
	public void setPi_img1(String pi_img1) {
		this.pi_img1 = pi_img1;
	}
	public int getOd_pdtprice() {
		return od_pdtprice;
	}
	public void setOd_pdtprice(int od_pdtprice) {
		this.od_pdtprice = od_pdtprice;
	}
	public int getOd_cnt() {
		return od_cnt;
	}
	public void setOd_cnt(int od_cnt) {
		this.od_cnt = od_cnt;
	}
	public String getOi_id() {
		return oi_id;
	}
	public void setOi_id(String oi_id) {
		this.oi_id = oi_id;
	}
	public String getOd_option() {
		return od_option;
	}
	public void setOd_option(String od_option) {
		this.od_option = od_option;
	}
	public String getOd_status() {
		return od_status;
	}
	public void setOd_status(String od_status) {
		this.od_status = od_status;
	}
}
