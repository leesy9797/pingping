package vo;

public class ScrapInfo {
// �� ���� �Խñ� ��ũ�� ������ �����ϱ� ���� Ŭ����
	private int ms_idx;	// ��ũ�� ��ȣ
	private String mi_email, ms_kind, ms_linkidx, ms_date;
	// �� �̸���, ��ũ�� �� �Խù� ����(p:��ǰ/c:ķ���ı�/e:�̺�Ʈ/s:��ȹ��)
	
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
