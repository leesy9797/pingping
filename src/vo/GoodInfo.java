package vo;

public class GoodInfo {
// 한 명의 게시글, 댓글, 답글 좋아요 정보를 저장하기 위한 클래스
	private int mg_idx;	// 좋아요 번호
	private String mi_email, mg_kind, mg_linkidx, mg_date;
	// 내 이메일, 좋아요 한 게시물 유형(c:캠핑후기/m:이달의추천/f:질문과답변(게시글/댓글/답글))
	
	public int getMg_idx() {
		return mg_idx;
	}
	public void setMg_idx(int mg_idx) {
		this.mg_idx = mg_idx;
	}
	public String getMi_email() {
		return mi_email;
	}
	public void setMi_email(String mi_email) {
		this.mi_email = mi_email;
	}
	public String getMg_kind() {
		return mg_kind;
	}
	public void setMg_kind(String mg_kind) {
		this.mg_kind = mg_kind;
	}
	public String getMg_linkidx() {
		return mg_linkidx;
	}
	public void setMg_linkidx(String mg_linkidx) {
		this.mg_linkidx = mg_linkidx;
	}
	public String getMg_date() {
		return mg_date;
	}
	public void setMg_date(String mg_date) {
		this.mg_date = mg_date;
	}
}
