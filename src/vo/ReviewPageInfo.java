package vo;

public class ReviewPageInfo {
// 내가 쓴 리뷰 목록에서 페이징을 위해 필요한 데이터들을 저장할 클래스
	private int cpage, pcnt, spage, epage, rcnt, psize, bsize;
	// 현재 페이지 번호, 페이지수, 시작 페이지, 종료 페이지, 게시글수, 페이지 크기, 블록 크기
	private String ord;		// 정렬조건(최신순/별점순)

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
