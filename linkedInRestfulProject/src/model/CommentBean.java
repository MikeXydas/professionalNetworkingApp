package model;

import java.util.Date;

import entities.Article;
import entities.CommentPK;

public class CommentBean {

	private CommentPK id;
	private String contentText;
	private Date uploadTime;
	private int commenterId;
	private Article article;
	
	public CommentPK getId() {
		return id;
	}
	public void setId(CommentPK id) {
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
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	

}
