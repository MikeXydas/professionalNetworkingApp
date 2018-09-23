package model;

import java.util.List;

import entities.Skill;

public class UserBean {
	
	private int idUser;
	private String educationText;
	private String email;
	private String firstName;
	private int isModerator;
	private String jobExperienceText;
	private String lastName;
	private String password;
	private String phoneNumber;
	private byte[] photoBytes;
	private String photoUrl;
	private String photoString64;
	private int isPublicEducation;
	private int isPublicJob;
	private int isPublicSkill;

	private List<Skill> skills;

	
	public int getIsPublicEducation() {
		return isPublicEducation;
	}
	public void setIsPublicEducation(int isPublicEducation) {
		this.isPublicEducation = isPublicEducation;
	}
	public int getIsPublicJob() {
		return isPublicJob;
	}
	public void setIsPublicJob(int isPublicJob) {
		this.isPublicJob = isPublicJob;
	}
	public int getIsPublicSkill() {
		return isPublicSkill;
	}
	public void setIsPublicSkill(int isPublicSkill) {
		this.isPublicSkill = isPublicSkill;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public String getEducationText() {
		return educationText;
	}
	public void setEducationText(String educationText) {
		this.educationText = educationText;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public int getIsModerator() {
		return isModerator;
	}
	public void setIsModerator(int isModerator) {
		this.isModerator = isModerator;
	}
	public String getJobExperienceText() {
		return jobExperienceText;
	}
	public void setJobExperienceText(String jobExperienceText) {
		this.jobExperienceText = jobExperienceText;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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
	
	public List<Skill> getSkills() {
		return skills;
	}
	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}
	
	
}
