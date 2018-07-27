package model;

import java.util.Date;
import java.util.List;

import entities.AdvertismentPK;
import entities.Advertisment_has_Skill;
import entities.Application;
import entities.User;

public class AdvertismentBean {
	
	private AdvertismentPK id;
	private String descriptionText;
	private String title;
	private Date uploadTime;
	private User user;
	private Advertisment_has_Skill advertismentHasSkill;
	private List<Application> applications;
	
	public AdvertismentBean() {
	}
	
	public AdvertismentPK getId() {
		return id;
	}
	public void setId(AdvertismentPK id) {
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Advertisment_has_Skill getAdvertismentHasSkill() {
		return advertismentHasSkill;
	}
	public void setAdvertismentHasSkill(Advertisment_has_Skill advertismentHasSkill) {
		this.advertismentHasSkill = advertismentHasSkill;
	}
	public List<Application> getApplications() {
		return applications;
	}
	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}

}
