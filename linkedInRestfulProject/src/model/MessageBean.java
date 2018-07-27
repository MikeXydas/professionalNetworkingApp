package model;

import java.util.Date;

import entities.Conversation;
import entities.MessagePK;

public class MessageBean {

	private MessagePK id;
	private String contentText;
	private int senderId;
	private Date sendTime;
	private Conversation conversation;

	public MessageBean( ) {
	}

	public MessagePK getId() {
		return id;
	}

	public void setId(MessagePK id) {
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

	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}
	
}
