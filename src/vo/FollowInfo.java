package vo;

public class FollowInfo {
// 한 명의 팔로잉/팔로워 정보를 저장하기 위한 클래스
	private int mfg_idx, mfr_idx;
	private String mi_email, mfg_email, mfg_date, mfr_email, mfr_date;
	// 내 이메일, 내가 팔로우한 회원이메일, 팔로잉 수행일, 나를 팔로우한 회원이메일, 팔로워 수행일
	private int mi_follower, mi_following;
	private String mi_nick, mi_img;
	// 회원정보 테이블에서 조인해서 가져올 정보
	
	public int getMfg_idx() {
		return mfg_idx;
	}
	public void setMfg_idx(int mfg_idx) {
		this.mfg_idx = mfg_idx;
	}
	public int getMfr_idx() {
		return mfr_idx;
	}
	public void setMfr_idx(int mfr_idx) {
		this.mfr_idx = mfr_idx;
	}
	public String getMi_email() {
		return mi_email;
	}
	public void setMi_email(String mi_email) {
		this.mi_email = mi_email;
	}
	public String getMfg_email() {
		return mfg_email;
	}
	public void setMfg_email(String mfg_email) {
		this.mfg_email = mfg_email;
	}
	public String getMfg_date() {
		return mfg_date;
	}
	public void setMfg_date(String mfg_date) {
		this.mfg_date = mfg_date;
	}
	public String getMfr_email() {
		return mfr_email;
	}
	public void setMfr_email(String mfr_email) {
		this.mfr_email = mfr_email;
	}
	public String getMfr_date() {
		return mfr_date;
	}
	public void setMfr_date(String mfr_date) {
		this.mfr_date = mfr_date;
	}
	public String getMi_nick() {
		return mi_nick;
	}
	public void setMi_nick(String mi_nick) {
		this.mi_nick = mi_nick;
	}
	public String getMi_img() {
		return mi_img;
	}
	public void setMi_img(String mi_img) {
		this.mi_img = mi_img;
	}
	public int getMi_follower() {
		return mi_follower;
	}
	public void setMi_follower(int mi_follower) {
		this.mi_follower = mi_follower;
	}
	public int getMi_following() {
		return mi_following;
	}
	public void setMi_following(int mi_following) {
		this.mi_following = mi_following;
	}
}
