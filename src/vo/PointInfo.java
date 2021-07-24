package vo;

public class PointInfo {
// 한 회원의 포인트 내역을 저장하기 위한 클래스
	private int mp_idx, mp_point, ai_idx;
	private String mi_email, mp_kind, mp_linkidx, mp_content, mp_date;
	
	// 포인트 내역 상세정보
	private String mp_info;
	
	public int getMp_idx() {
		return mp_idx;
	}
	public void setMp_idx(int mp_idx) {
		this.mp_idx = mp_idx;
	}
	public int getMp_point() {
		return mp_point;
	}
	public void setMp_point(int mp_point) {
		this.mp_point = mp_point;
	}
	public int getAi_idx() {
		return ai_idx;
	}
	public void setAi_idx(int ai_idx) {
		this.ai_idx = ai_idx;
	}
	public String getMi_email() {
		return mi_email;
	}
	public void setMi_email(String mi_email) {
		this.mi_email = mi_email;
	}
	public String getMp_kind() {
		return mp_kind;
	}
	public void setMp_kind(String mp_kind) {
		this.mp_kind = mp_kind;
	}
	public String getMp_linkidx() {
		return mp_linkidx;
	}
	public void setMp_linkidx(String mp_linkidx) {
		this.mp_linkidx = mp_linkidx;
	}
	public String getMp_content() {
		return mp_content;
	}
	public void setMp_content(String mp_content) {
		this.mp_content = mp_content;
	}
	public String getMp_date() {
		return mp_date;
	}
	public void setMp_date(String mp_date) {
		this.mp_date = mp_date;
	}
	public String getMp_info() {
		return mp_info;
	}
	public void setMp_info(String mp_info) {
		this.mp_info = mp_info;
	}
}
