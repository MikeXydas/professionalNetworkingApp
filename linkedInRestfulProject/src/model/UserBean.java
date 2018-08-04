package model;

import java.util.List;

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
	private String photoUrl;
	private int isPublicEducation;
	private int isPublicJob;
	private int isPublicSkill;
	private List<AdvertismentBean> advertisments;
	private List<ArticleBean> articles;
	private List<ConnectionBean> connections;
	private List<ConnectionRequestBean> connectionRequests;
	private List<ConversationBean> conversations;
	
	
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
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public List<AdvertismentBean> getAdvertisments() {
		return advertisments;
	}
	public void setAdvertisments(List<AdvertismentBean> advertisments) {
		this.advertisments = advertisments;
	}
	public List<ArticleBean> getArticles() {
		return articles;
	}
	public void setArticles(List<ArticleBean> articles) {
		this.articles = articles;
	}
	public List<ConnectionBean> getConnections() {
		return connections;
	}
	public void setConnections(List<ConnectionBean> connections) {
		this.connections = connections;
	}
	public List<ConnectionRequestBean> getConnectionRequests() {
		return connectionRequests;
	}
	public void setConnectionRequests(List<ConnectionRequestBean> connectionRequests) {
		this.connectionRequests = connectionRequests;
	}
	public List<ConversationBean> getConversations() {
		return conversations;
	}
	public void setConversations(List<ConversationBean> conversations) {
		this.conversations = conversations;
	}
	
	
}
