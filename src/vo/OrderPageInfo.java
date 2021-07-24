package vo;

public class OrderPageInfo {
	private int cpage, pcnt, spage, epage, rcnt, psize, bsize, cnt;	// 한 주문건에 포함된 상품 개수
	// 현재 페이지 번호, 페이지수, 시작 페이지, 종료 페이지, 게시글수, 페이지 크기, 블록 크기
	private String oiid, miemail, miname, miphone, sdate, edate, oistatus, schdate;
	private String ord;		// 정렬조건
	private String oistatuses[];

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
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public String getOiid() {
		return oiid;
	}
	public void setOiid(String oiid) {
		this.oiid = oiid;
	}
	public String getMiemail() {
		return miemail;
	}
	public void setMiemail(String miemail) {
		this.miemail = miemail;
	}
	public String getMiname() {
		return miname;
	}
	public void setMiname(String miname) {
		this.miname = miname;
	}	
	public String getMiphone() {
		return miphone;
	}
	public void setMiphone(String miphone) {
		this.miphone = miphone;
	}
	public String getSdate() {
		return sdate;
	}
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
	public String getEdate() {
		return edate;
	}
	public void setEdate(String edate) {
		this.edate = edate;
	}
	public String getOistatus() {
		return oistatus;
	}
	public void setOistatus(String oistatus) {
		this.oistatus = oistatus;
	}
	public String getSchdate() {
		return schdate;
	}
	public void setSchdate(String schdate) {
		this.schdate = schdate;
	}
	public String getOrd() {
		return ord;
	}
	public void setOrd(String ord) {
		this.ord = ord;
	}
	public String[] getOistatuses() {
		return oistatuses;
	}
	public void setOistatuses(String[] oistatuses) {
		this.oistatuses = oistatuses;
	}
	
}
