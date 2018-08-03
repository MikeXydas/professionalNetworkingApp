package model;

import java.util.Date;
import java.util.List;

public class AdvertismentBean {
	
	private AdvertismentPKBean id;
	private String descriptionText;
	private String title;
	private Date uploadTime;
	private UserBean user;
	private List<ApplicationBean> applications;
	private List<SkillBean> skills;
	
	public AdvertismentPKBean getId() {
		return id;
	}
	public void setId(AdvertismentPKBean id) {
		this.id = id;
	}
	public String getDescriptionText() {
		return descriptionText;
	}
	public void setDescriptionText(String descriptionText) {
		this.descriptionText = descriptionText;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	public UserBean getUser() {
		return user;
	}
	public void setUser(UserBean user) {
		this.user = user;
	}
	public List<ApplicationBean> getApplications() {
		return applications;
	}
	public void setApplications(List<ApplicationBean> applications) {
		this.applications = applications;
	}
	public List<SkillBean> getSkills() {
		return skills;
	}
	public void setSkills(List<SkillBean> skills) {
		this.skills = skills;
	}
	
	
	
}
