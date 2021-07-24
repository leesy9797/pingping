package vo;

public class MemberPageInfo {
// 회원 목록에서 페이징을 위해 필요한 데이터들을 저장할 클래스(어드민)
	private int cpage, pcnt, spage, epage, rcnt, psize, bsize;
	// 현재 페이지 번호, 페이지수, 시작 페이지, 종료 페이지, 게시글수, 페이지 크기, 블록 크기
	
	// 어드민에서 사용할 검색 관련 정보를 저장할 변수들
	private String email, nick, phone, gender, isactive, sage, eage;
	private String joinsdate, joinedate, lastsdate, lastedate;
	// 시작가입일, 종료가입일, 시작마지막로그인일자, 종료마지막로그인일자

	private String ord;		// 정렬조건
	
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getIsactive() {
		return isactive;
	}
	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}
	public String getSage() {
		return sage;
	}
	public void setSage(String sage) {
		this.sage = sage;
	}
	public String getEage() {
		return eage;
	}
	public void setEage(String eage) {
		this.eage = eage;
	}
	public String getJoinsdate() {
		return joinsdate;
	}
	public void setJoinsdate(String joinsdate) {
		this.joinsdate = joinsdate;
	}
	public String getJoinedate() {
		return joinedate;
	}
	public void setJoinedate(String joinedate) {
		this.joinedate = joinedate;
	}
	public String getLastsdate() {
		return lastsdate;
	}
	public void setLastsdate(String lastsdate) {
		this.lastsdate = lastsdate;
	}
	public String getLastedate() {
		return lastedate;
	}
	public void setLastedate(String lastedate) {
		this.lastedate = lastedate;
	}
	public String getOrd() {
		return ord;
	}
	public void setOrd(String ord) {
		this.ord = ord;
	}
	
}
