package model;

import java.util.List;

import entities.Message;
import entities.User;

public class ConversationBean {
	
	private int idConversation;
	private List<Message> messages;
	private List<User> users;
	
	public ConversationBean () {
	}
	
	public int getIdConversation() {
		return idConversation;
	}
	public void setIdConversation(int idConversation) {
		this.idConversation = idConversation;
	}
	public List<Message> getMessages() {
		return messages;
	}
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	
}
