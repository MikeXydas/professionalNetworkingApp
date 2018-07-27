package model;

import java.util.List;

import entities.Advertisment;
import entities.Article;
import entities.Connection;
import entities.ConnectionRequest;
import entities.Conversation;
import entities.User_has_Skill;

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
	private List<Advertisment> advertisments;
	private List<Article> articles;
	private List<Connection> connections;
	private List<ConnectionRequest> connectionRequests;
	private List<Conversation> conversations;
	private User_has_Skill userHasSkill;
	
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
	public List<Advertisment> getAdvertisments() {
		return advertisments;
	}
	public void setAdvertisments(List<Advertisment> advertisments) {
		this.advertisments = advertisments;
	}
	public List<Article> getArticles() {
		return articles;
	}
	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}
	public List<Connection> getConnections() {
		return connections;
	}
	public void setConnections(List<Connection> connections) {
		this.connections = connections;
	}
	public List<ConnectionRequest> getConnectionRequests() {
		return connectionRequests;
	}
	public void setConnectionRequests(List<ConnectionRequest> connectionRequests) {
		this.connectionRequests = connectionRequests;
	}
	public List<Conversation> getConversations() {
		return conversations;
	}
	public void setConversations(List<Conversation> conversations) {
		this.conversations = conversations;
	}
	public User_has_Skill getUserHasSkill() {
		return userHasSkill;
	}
	public void setUserHasSkill(User_has_Skill userHasSkill) {
		this.userHasSkill = userHasSkill;
	}

	
}
