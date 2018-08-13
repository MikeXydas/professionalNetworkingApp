package model;

import java.util.Date;

public class PendingRequestBean {

		private int userId;
		private int reqId;
		private String firstName;
		private String lastName;
		private byte[] photoBytes;
		private String photoUrl;
		private String photoString64;
		private Date sendTime;
		
		public int getReqId() {
			return reqId;
		}
		public void setReqId(int reqId) {
			this.reqId = reqId;
		}
		public int getUserId() {
			return userId;
		}
		public void setUserId(int userId) {
			this.userId = userId;
		}
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		
		public byte[] getPhotoBytes() {
			return photoBytes;
		}
		public void setPhotoBytes(byte[] photoBytes) {
			this.photoBytes = photoBytes;
		}
		public String getPhotoUrl() {
			return photoUrl;
		}
		public void setPhotoUrl(String photoUrl) {
			this.photoUrl = photoUrl;
		}
		public String getPhotoString64() {
			return photoString64;
		}
		public void setPhotoString64(String photoString64) {
			this.photoString64 = photoString64;
		}
		public Date getSendTime() {
			return sendTime;
		}
		public void setSendTime(Date sendTime) {
			this.sendTime = sendTime;
		}
		
		
}

