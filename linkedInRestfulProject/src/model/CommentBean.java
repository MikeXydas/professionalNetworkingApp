package model;

import java.util.Date;

public class CommentBean {

	private CommentPKBean id;
	private String contentText;
	private Date uploadTime;
	private int commenterId;
	private ArticleBean article;
	
	public CommentPKBean getId() {
		return id;
	}
	public void setId(CommentPKBean id) {
		this.id = id;
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
	
}
