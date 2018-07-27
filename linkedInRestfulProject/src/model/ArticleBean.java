package model;

import java.util.Date;
import java.util.List;

import entities.ArticlePK;
import entities.Comment;
import entities.Interest;
import entities.User;

public class ArticleBean {
	
	private ArticlePK id;
	private String contentText;
	private String photoUrl;
	private String soundUrl;
	private String title;
	private Date uploadTime;
	private String videoUrl;
	private User user;
	private List<Comment> comments;
	private List<Interest> interests;
	
	public ArticleBean() {
	}
	
	public ArticlePK getId() {
		return id;
	}
	public void setId(ArticlePK id) {
		this.id = id;
	}
	public String getContentText() {
		return contentText;
	}
	public void setContentText(String contentText) {
		this.contentText = contentText;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public String getSoundUrl() {
		return soundUrl;
	}
	public void setSoundUrl(String soundUrl) {
		this.soundUrl = soundUrl;
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
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public List<Interest> getInterests() {
		return interests;
	}
	public void setInterests(List<Interest> interests) {
		this.interests = interests;
	}

}
