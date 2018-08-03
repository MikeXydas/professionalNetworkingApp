package model;

import java.util.Date;

public class ConnectionRequestBean {

	private ConnectionRequestPKBean id;
	private int senderId;
	private Date sendTime;
	private UserBean user;
	
	public ConnectionRequestPKBean getId() {
		return id;
	}
	public void setId(ConnectionRequestPKBean id) {
		this.id = id;
	}
	public int getSenderId() {
		return senderId;
	}
	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public UserBean getUser() {
		return user;
	}
	public void setUser(UserBean user) {
		this.user = user;
	}
	
	
}
