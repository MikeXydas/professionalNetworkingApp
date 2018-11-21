package model;

import java.util.Date;

public class PendingRequestBean {

		private int userId;
		private int reqId;
		private String firstName;
		private String lastName;
		private PhotoBean photoBean;
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
		
		public PhotoBean getPhotoBean() {
			return photoBean;
		}
		public void setPhotoBean(PhotoBean photoBean) {
			this.photoBean = photoBean;
		}
		public Date getSendTime() {
			return sendTime;
		}
		public void setSendTime(Date sendTime) {
			this.sendTime = sendTime;
		}
		
		
}

