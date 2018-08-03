package model;

import java.util.Date;

public class MessageBean {

	private MessagePKBean id;
	private String contentText;
	private int senderId;
	private Date sendTime;
	private ConversationBean conversation;
	
	public MessagePKBean getId() {
		return id;
	}
	public void setId(MessagePKBean id) {
		this.id = id;
	}
	public String getContentText() {
		return contentText;
	}
	public void setContentText(String contentText) {
		this.contentText = contentText;
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
	public ConversationBean getConversation() {
		return conversation;
	}
	public void setConversation(ConversationBean conversation) {
		this.conversation = conversation;
	}

}
