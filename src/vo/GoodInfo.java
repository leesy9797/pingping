package vo;

public class GoodInfo {
// �� ���� �Խñ�, ���, ��� ���ƿ� ������ �����ϱ� ���� Ŭ����
	private int mg_idx;	// ���ƿ� ��ȣ
	private String mi_email, mg_kind, mg_linkidx, mg_date;
	// �� �̸���, ���ƿ� �� �Խù� ����(c:ķ���ı�/m:�̴�����õ/f:�������亯(�Խñ�/���/���))
	
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
