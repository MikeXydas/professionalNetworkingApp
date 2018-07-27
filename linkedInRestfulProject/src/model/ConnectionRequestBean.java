package model;

import java.util.Date;

import entities.ConnectionRequestPK;
import entities.User;

public class ConnectionRequestBean {

	private ConnectionRequestPK id;
	private int senderId;
	private Date sendTime;
	private User user;
	
	public ConnectionRequestBean() {
	}
	
	public ConnectionRequestPK getId() {
		return id;
	}
	public void setId(ConnectionRequestPK id) {
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	

}
