package model;

import entities.ConnectionPK;
import entities.User;

public class ConnectionBean {

	private ConnectionPK id;
	private int conncetedUserId;
	private User user;
	
	public ConnectionBean() {	
	}
	
	public ConnectionPK getId() {
		return id;
	}
	public void setId(ConnectionPK id) {
		this.id = id;
	}
	public int getConncetedUserId() {
		return conncetedUserId;
	}
	public void setConncetedUserId(int conncetedUserId) {
		this.conncetedUserId = conncetedUserId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

}
