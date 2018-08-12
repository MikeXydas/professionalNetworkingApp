package model;

import java.util.Date;
import java.util.List;

public class ArticleBean {
	
	private ArticlePKBean id;
	private String contentText;
	
	private String photoUrl;
	private byte[] photoBytes;
	private String finalPhotoUrl;
	
	private String soundUrl;
	private byte[] soundBytes;
	private String finalSoundUrl;
	
	private String title;
	private Date uploadTime;
	
	private String videoUrl;
	private byte[] videoBytes;
	private String finalVideoUrl;
	
	private UserBean user;
	private List<CommentBean> comments;
	private List<InterestBean> interests;
	
	public ArticlePKBean getId() {
		return id;
	}
	public void setId(ArticlePKBean id) {
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
	public byte[] getPhotoBytes() {
		return photoBytes;
	}
	public void setPhotoBytes(byte[] photoBytes) {
		this.photoBytes = photoBytes;
	}
	public String getFinalPhotoUrl() {
		return finalPhotoUrl;
	}
	public void setFinalPhotoUrl(String finalPhotoUrl) {
		this.finalPhotoUrl = finalPhotoUrl;
	}
	public byte[] getSoundBytes() {
		return soundBytes;
	}
	public void setSoundBytes(byte[] soundBytes) {
		this.soundBytes = soundBytes;
	}
	public String getFinalSoundUrl() {
		return finalSoundUrl;
	}
	public void setFinalSoundUrl(String finalSoundUrl) {
		this.finalSoundUrl = finalSoundUrl;
	}
	public byte[] getVideoBytes() {
		return videoBytes;
	}
	public void setVideoBytes(byte[] videoBytes) {
		this.videoBytes = videoBytes;
	}
	public String getFinalVideoUrl() {
		return finalVideoUrl;
	}
	public void setFinalVideoUrl(String finalVideoUrl) {
		this.finalVideoUrl = finalVideoUrl;
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
