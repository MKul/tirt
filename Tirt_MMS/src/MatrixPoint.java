
public class MatrixPoint {

	Bts bts;
	User user;
	String located;
	
	MatrixPoint(User user, Bts bts, String located){
		this.user = user;
		this.bts = bts;
		this.located = located;
	}

	public Bts getBts() {
		return bts;
	}

	public void setBts(Bts bts) {
		this.bts = bts;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getLocated() {
		return located;
	}

	public void setLocated(String located) {
		this.located = located;
	}

	
}
