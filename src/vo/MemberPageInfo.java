package vo;

public class MemberPageInfo {
// ȸ�� ��Ͽ��� ����¡�� ���� �ʿ��� �����͵��� ������ Ŭ����(����)
	private int cpage, pcnt, spage, epage, rcnt, psize, bsize;
	// ���� ������ ��ȣ, ��������, ���� ������, ���� ������, �Խñۼ�, ������ ũ��, ��� ũ��
	
	// ���ο��� ����� �˻� ���� ������ ������ ������
	private String email, nick, phone, gender, isactive, sage, eage;
	private String joinsdate, joinedate, lastsdate, lastedate;
	// ���۰�����, ���ᰡ����, ���۸������α�������, ���Ḷ�����α�������

	private String ord;		// ��������
	
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
