package model;

import java.util.Date;
import java.util.List;

public class ArticleBean {
	
	private int idArticle;
	private int user_idUser;
	private String contentText;
	
	private byte[] photoBytes;
	private String photoUrl;
	private String photoString64;
	
	private byte[] soundBytes;
	private String soundUrl;
	private String soundString64;
	
	private String title;
	private Date uploadTime;
	
	private byte[] videoBytes;
	private String videoUrl;
	private String videoString64;;
	
	private int userId;
	
	private UserBean user;
	private List<CommentBean> comments;
	private List<InterestBean> interests;
	
	public int getIdArticle() {
		return idArticle;
	}
	public void setIdArticle(int idArticle) {
		this.idArticle = idArticle;
	}
	public int getUser_idUser() {
		return user_idUser;
	}
	public void setUser_idUser(int user_idUser) {
		this.user_idUser = user_idUser;
	}
	public String getContentText() {
		return contentText;
	}
	public void setContentText(String contentText) {
		this.contentText = contentText;
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
	public byte[] getSoundBytes() {
		return soundBytes;
	}
	public void setSoundBytes(byte[] soundBytes) {
		this.soundBytes = soundBytes;
	}
	public String getSoundUrl() {
		return soundUrl;
	}
	public void setSoundUrl(String soundUrl) {
		this.soundUrl = soundUrl;
	}
	public String getSoundString64() {
		return soundString64;
	}
	public void setSoundString64(String soundString64) {
		this.soundString64 = soundString64;
	}
	public byte[] getVideoBytes() {
		return videoBytes;
	}
	public void setVideoBytes(byte[] videoBytes) {
		this.videoBytes = videoBytes;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public String getVideoString64() {
		return videoString64;
	}
	public void setVideoString64(String videoString64) {
		this.videoString64 = videoString64;
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

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public UserBean getUser() {
		return user;
	}
	public void setUser(UserBean user) {
		this.user = user;
	}
	public List<CommentBean> getComments() {
		return comments;
	}
	public void setComments(List<CommentBean> comments) {
		this.comments = comments;
	}
	public List<InterestBean> getInterests() {
		return interests;
	}
	public void setInterests(List<InterestBean> interests) {
		this.interests = interests;
	}

}
