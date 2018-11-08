package natapp.liujinliang.vo;

public class User {
	private String username;
	private String password;
	private Boolean remebermy;

	public Boolean getRemebermy() {
		return remebermy;
	}

	public void setRemebermy(Boolean remebermy) {
		this.remebermy = remebermy;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
