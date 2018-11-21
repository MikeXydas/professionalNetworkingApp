package model;

public class SendMessageBean {
	
	private int convId;
	private String contentText;
	private int senderId;
	
	public int getConvId() {
		return convId;
	}
	public void setConvId(int convId) {
		this.convId = convId;
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
	
}
