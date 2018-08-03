package model;

public class ConnectionBean {

	private ConnectionPKBean id;
	private int conncetedUserId;
	private UserBean user;
	
	public ConnectionPKBean getId() {
		return id;
	}
	public void setId(ConnectionPKBean id) {
		this.id = id;
	}
	public int getConncetedUserId() {
		return conncetedUserId;
	}
	public void setConncetedUserId(int conncetedUserId) {
		this.conncetedUserId = conncetedUserId;
	}
	public UserBean getUser() {
		return user;
	}
	public void setUser(UserBean user) {
		this.user = user;
	}
	


}
