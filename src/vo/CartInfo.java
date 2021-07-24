package vo;

public class CartInfo {
// 장바구니에 담긴 상품정보를 저장할 클래스로 장바구니 화면에서 보여줄 데이터들도 저장할 수 있게 함
	private int oc_idx, oc_cnt;
	private String oi_id, mi_email, pi_id, oc_option, oc_date;	// 장바구니 컬럼
	private String pi_name, pi_img1, br_name, pi_option;	// 추가로 보여줄 데이터
	private int pi_price, pi_stock;
	
	private String mi_name, mi_phone, oi_payment, oi_date, oi_status;
	// 회원이름, 회원전화번호, 결제방법, 결제일, 주문상태

	private String oi_name, oi_phone, oi_zip, oi_addr1, oi_addr2, oi_cmt;
	// 수취인/배송지 정보
	private int oi_pay, oi_usepoint, oi_delipay, oi_pdtprice, ai_idx, od_pdtprice, od_cnt;
	// 결제액(총 상품가격 + 배송비 - 사용포인트)
	private String oi_invoice, oi_rebank, oi_reaacount, oi_redepositer, oi_last, od_option;
	// 송장번호, 환불은행/계좌/예금주, 최종수정일
	
	private String od_id, oi_memo;	// 어드민용 관리자 메모
	
	public int getOc_idx() {
		return oc_idx;
	}

	public void setOc_idx(int oc_idx) {
		this.oc_idx = oc_idx;
	}

	public int getOc_cnt() {
		return oc_cnt;
	}

	public void setOc_cnt(int oc_cnt) {
		this.oc_cnt = oc_cnt;
	}

	public String getOi_id() {
		return oi_id;
	}

	public void setOi_id(String oi_id) {
		this.oi_id = oi_id;
	}

	public String getMi_email() {
		return mi_email;
	}

	public void setMi_email(String mi_email) {
		this.mi_email = mi_email;
	}

	public String getPi_id() {
		return pi_id;
	}

	public void setPi_id(String pi_id) {
		this.pi_id = pi_id;
	}

	public String getOc_option() {
		return oc_option;
	}

	public void setOc_option(String oc_option) {
		this.oc_option = oc_option;
	}

	public String getOc_date() {
		return oc_date;
	}

	public void setOc_date(String oc_date) {
		this.oc_date = oc_date;
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

	public String getBr_name() {
		return br_name;
	}

	public void setBr_name(String br_name) {
		this.br_name = br_name;
	}

	public String getPi_option() {
		return pi_option;
	}

	public void setPi_option(String pi_option) {
		this.pi_option = pi_option;
	}

	public int getPi_price() {
		return pi_price;
	}

	public void setPi_price(int pi_price) {
		this.pi_price = pi_price;
	}

	public int getPi_stock() {
		return pi_stock;
	}

	public void setPi_stock(int pi_stock) {
		this.pi_stock = pi_stock;
	}

	public String getMi_name() {
		return mi_name;
	}

	public void setMi_name(String mi_name) {
		this.mi_name = mi_name;
	}

	public String getMi_phone() {
		return mi_phone;
	}

	public void setMi_phone(String mi_phone) {
		this.mi_phone = mi_phone;
	}

	public String getOi_payment() {
		return oi_payment;
	}

	public void setOi_payment(String oi_payment) {
		this.oi_payment = oi_payment;
	}

	public String getOi_date() {
		return oi_date;
	}

	public void setOi_date(String oi_date) {
		this.oi_date = oi_date;
	}

	public String getOi_status() {
		return oi_status;
	}

	public void setOi_status(String oi_status) {
		this.oi_status = oi_status;
	}

	public String getOi_name() {
		return oi_name;
	}

	public void setOi_name(String oi_name) {
		this.oi_name = oi_name;
	}

	public String getOi_phone() {
		return oi_phone;
	}

	public void setOi_phone(String oi_phone) {
		this.oi_phone = oi_phone;
	}

	public String getOi_zip() {
		return oi_zip;
	}

	public void setOi_zip(String oi_zip) {
		this.oi_zip = oi_zip;
	}

	public String getOi_addr1() {
		return oi_addr1;
	}

	public void setOi_addr1(String oi_addr1) {
		this.oi_addr1 = oi_addr1;
	}

	public String getOi_addr2() {
		return oi_addr2;
	}

	public void setOi_addr2(String oi_addr2) {
		this.oi_addr2 = oi_addr2;
	}

	public String getOi_cmt() {
		return oi_cmt;
	}

	public void setOi_cmt(String oi_cmt) {
		this.oi_cmt = oi_cmt;
	}

	public int getOi_pay() {
		return oi_pay;
	}

	public void setOi_pay(int oi_pay) {
		this.oi_pay = oi_pay;
	}

	public int getOi_usepoint() {
		return oi_usepoint;
	}

	public void setOi_usepoint(int oi_usepoint) {
		this.oi_usepoint = oi_usepoint;
	}

	public int getOi_delipay() {
		return oi_delipay;
	}

	public void setOi_delipay(int oi_delipay) {
		this.oi_delipay = oi_delipay;
	}

	public int getOi_pdtprice() {
		return oi_pdtprice;
	}

	public void setOi_pdtprice(int oi_pdtprice) {
		this.oi_pdtprice = oi_pdtprice;
	}

	public int getAi_idx() {
		return ai_idx;
	}

	public void setAi_idx(int ai_idx) {
		this.ai_idx = ai_idx;
	}

	public String getOi_invoice() {
		return oi_invoice;
	}

	public void setOi_invoice(String oi_invoice) {
		this.oi_invoice = oi_invoice;
	}

	public String getOi_rebank() {
		return oi_rebank;
	}

	public void setOi_rebank(String oi_rebank) {
		this.oi_rebank = oi_rebank;
	}

	public String getOi_reaacount() {
		return oi_reaacount;
	}

	public void setOi_reaacount(String oi_reaacount) {
		this.oi_reaacount = oi_reaacount;
	}

	public String getOi_redepositer() {
		return oi_redepositer;
	}

	public void setOi_redepositer(String oi_redepositer) {
		this.oi_redepositer = oi_redepositer;
	}

	public String getOi_last() {
		return oi_last;
	}

	public void setOi_last(String oi_last) {
		this.oi_last = oi_last;
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

	public String getOd_option() {
		return od_option;
	}

	public void setOd_option(String od_option) {
		this.od_option = od_option;
	}

	public String getOd_id() {
		return od_id;
	}

	public void setOd_id(String od_id) {
		this.od_id = od_id;
	}

	public String getOi_memo() {
		return oi_memo;
	}

	public void setOi_memo(String oi_memo) {
		this.oi_memo = oi_memo;
	}
		
}