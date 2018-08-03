package model;

import java.util.List;

public class ConversationBean {
	
	private int idConversation;
	private List<MessageBean> messages;
	private List<UserBean> users;
	
	public int getIdConversation() {
		return idConversation;
	}
	public void setIdConversation(int idConversation) {
		this.idConversation = idConversation;
	}
	public List<MessageBean> getMessages() {
		return messages;
	}
	public void setMessages(List<MessageBean> messages) {
		this.messages = messages;
	}
	public List<UserBean> getUsers() {
		return users;
	}
	public void setUsers(List<UserBean> users) {
		this.users = users;
	}
	

	
}
