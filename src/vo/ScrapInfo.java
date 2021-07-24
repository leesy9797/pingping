package vo;

public class ScrapInfo {
// 한 명의 게시글 스크랩 정보를 저장하기 위한 클래스
	private int ms_idx;	// 스크랩 번호
	private String mi_email, ms_kind, ms_linkidx, ms_date;
	// 내 이메일, 스크랩 한 게시물 유형(p:상품/c:캠핑후기/e:이벤트/s:기획전)
	
	public int getMs_idx() {
		return ms_idx;
	}
	public void setMs_idx(int ms_idx) {
		this.ms_idx = ms_idx;
	}
	public String getMi_email() {
		return mi_email;
	}
	public void setMi_email(String mi_email) {
		this.mi_email = mi_email;
	}
	public String getMs_kind() {
		return ms_kind;
	}
	public void setMs_kind(String ms_kind) {
		this.ms_kind = ms_kind;
	}
	public String getMs_linkidx() {
		return ms_linkidx;
	}
	public void setMs_linkidx(String ms_linkidx) {
		this.ms_linkidx = ms_linkidx;
	}
	public String getMs_date() {
		return ms_date;
	}
	public void setMs_date(String ms_date) {
		this.ms_date = ms_date;
	}
}
