package vo;

public class ReviewPageInfo {
// ���� �� ���� ��Ͽ��� ����¡�� ���� �ʿ��� �����͵��� ������ Ŭ����
	private int cpage, pcnt, spage, epage, rcnt, psize, bsize;
	// ���� ������ ��ȣ, ��������, ���� ������, ���� ������, �Խñۼ�, ������ ũ��, ��� ũ��
	private String ord;		// ��������(�ֽż�/������)

	public int getCpage() {
		return cpage;
	}

	public void setCpage(int cpage) {
		this.cpage = cpage;
	}

	public int getPcnt() {
		return pcnt;
	}

	public void setPcnt(int pcnt) {
		this.pcnt = pcnt;
	}

	public int getSpage() {
		return spage;
	}

	public void setSpage(int spage) {
		this.spage = spage;
	}

	public int getEpage() {
		return epage;
	}

	public void setEpage(int epage) {
		this.epage = epage;
	}

	public int getRcnt() {
		return rcnt;
	}

	public void setRcnt(int rcnt) {
		this.rcnt = rcnt;
	}

	public int getPsize() {
		return psize;
	}

	public void setPsize(int psize) {
		this.psize = psize;
	}

	public int getBsize() {
		return bsize;
	}

	public void setBsize(int bsize) {
		this.bsize = bsize;
	}
	public String getOrd() {
		return ord;
	}
	public void setOrd(String ord) {
		this.ord = ord;
	}
}
