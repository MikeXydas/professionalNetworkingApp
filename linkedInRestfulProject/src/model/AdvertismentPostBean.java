package model;

import java.util.Date;

public class AdvertismentPostBean {
	
	//Skills are a continuous text comma separated. For example C, Python
	private String skills;
	
	private String text;
	
	private Date uploadTime;

	private int userId;

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
