package model;

import java.util.Date;

public class CommentBean {

	private int commentId;
	private int articleId;
	private String contentText;
	private Date uploadTime;
	private int commenterId;
	private String firstName;
	private String lastName;
	private ArticleBean article;
	private PhotoBean photoBean;

	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public int getArticleId() {
		return articleId;
	}
	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}
	public String getContentText() {
		return contentText;
	}
	public void setContentText(String contentText) {
		this.contentText = contentText;
	}
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	public int getCommenterId() {
		return commenterId;
	}
	public void setCommenterId(int commenterId) {
		this.commenterId = commenterId;
	}
	public ArticleBean getArticle() {
		return article;
	}
	public void setArticle(ArticleBean article) {
		this.article = article;
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
	
	
}
