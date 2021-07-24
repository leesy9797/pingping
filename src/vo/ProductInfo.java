package vo;

public class ProductInfo {
// 하나의 상품 정보를 저장할 클래스
	private String pi_id, cb_id, cs_id, br_name, pi_name, pi_option;
	private String pi_img1, pi_img2, pi_img3, pi_img4, pi_img5, pi_desc;
	private String pi_isview, pi_date, last_date;
	private int pi_cost, pi_price, pi_dcrate, pi_stock, pi_salecnt, pi_reviewcnt, pi_qnacnt, ai_idx, last_admin;
	private double pi_staravg;
	private String cb_name, cs_name;	// 조인해서 받아올 데이터들
	
	public String getPi_id() {
		return pi_id;
	}
	public void setPi_id(String pi_id) {
		this.pi_id = pi_id;
	}
	public String getCb_id() {
		return cb_id;
	}
	public void setCb_id(String cb_id) {
		this.cb_id = cb_id;
	}
	public String getCs_id() {
		return cs_id;
	}
	public void setCs_id(String cs_id) {
		this.cs_id = cs_id;
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
	public String getPi_option() {
		return pi_option;
	}
	public void setPi_option(String pi_option) {
		this.pi_option = pi_option;
	}
	public String getPi_img1() {
		return pi_img1;
	}
	public void setPi_img1(String pi_img1) {
		this.pi_img1 = pi_img1;
	}
	public String getPi_img2() {
		return pi_img2;
	}
	public void setPi_img2(String pi_img2) {
		this.pi_img2 = pi_img2;
	}
	public String getPi_img3() {
		return pi_img3;
	}
	public void setPi_img3(String pi_img3) {
		this.pi_img3 = pi_img3;
	}
	public String getPi_img4() {
		return pi_img4;
	}
	public void setPi_img4(String pi_img4) {
		this.pi_img4 = pi_img4;
	}
	public String getPi_img5() {
		return pi_img5;
	}
	public void setPi_img5(String pi_img5) {
		this.pi_img5 = pi_img5;
	}
	public String getPi_desc() {
		return pi_desc;
	}
	public void setPi_desc(String pi_desc) {
		this.pi_desc = pi_desc;
	}
	public String getPi_isview() {
		return pi_isview;
	}
	public void setPi_isview(String pi_isview) {
		this.pi_isview = pi_isview;
	}
	public String getPi_date() {
		return pi_date;
	}
	public void setPi_date(String pi_date) {
		this.pi_date = pi_date;
	}
	public String getLast_date() {
		return last_date;
	}
	public void setLast_date(String last_date) {
		this.last_date = last_date;
	}
	public int getPi_cost() {
		return pi_cost;
	}
	public void setPi_cost(int pi_cost) {
		this.pi_cost = pi_cost;
	}
	public int getPi_price() {
		return pi_price;
	}
	public void setPi_price(int pi_price) {
		this.pi_price = pi_price;
	}
	public int getPi_dcrate() {
		return pi_dcrate;
	}
	public void setPi_dcrate(int pi_dcrate) {
		this.pi_dcrate = pi_dcrate;
	}
	public int getPi_stock() {
		return pi_stock;
	}
	public void setPi_stock(int pi_stock) {
		this.pi_stock = pi_stock;
	}
	public int getPi_salecnt() {
		return pi_salecnt;
	}
	public void setPi_salecnt(int pi_salecnt) {
		this.pi_salecnt = pi_salecnt;
	}
	public int getPi_reviewcnt() {
		return pi_reviewcnt;
	}
	public void setPi_reviewcnt(int pi_reviewcnt) {
		this.pi_reviewcnt = pi_reviewcnt;
	}
	public int getPi_qnacnt() {
		return pi_qnacnt;
	}
	public void setPi_qnacnt(int pi_qnacnt) {
		this.pi_qnacnt = pi_qnacnt;
	}
	public int getAi_idx() {
		return ai_idx;
	}
	public void setAi_idx(int ai_idx) {
		this.ai_idx = ai_idx;
	}
	public int getLast_admin() {
		return last_admin;
	}
	public void setLast_admin(int last_admin) {
		this.last_admin = last_admin;
	}
	public double getPi_staravg() {
		return pi_staravg;
	}
	public void setPi_staravg(double pi_staravg) {
		this.pi_staravg = pi_staravg;
	}
	public String getCb_name() {
		return cb_name;
	}
	public void setCb_name(String cb_name) {
		this.cb_name = cb_name;
	}
	public String getCs_name() {
		return cs_name;
	}
	public void setCs_name(String cs_name) {
		this.cs_name = cs_name;
	}
	
}
