package model;

import java.util.Date;

public class InterestBean {
	
	private int interestId;
	private int articleId;
	private Date interestTime;
	private int interesterId;
	private ArticleBean article;
	
	public int getInterestId() {
		return interestId;
	}
	public void setInterestId(int interestId) {
		this.interestId = interestId;
	}
	public int getArticleId() {
		return articleId;
	}
	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}
	public Date getInterestTime() {
		return interestTime;
	}
	public void setInterestTime(Date interestTime) {
		this.interestTime = interestTime;
	}
	public int getInteresterId() {
		return interesterId;
	}
	public void setInteresterId(int interesterId) {
		this.interesterId = interesterId;
	}
	public ArticleBean getArticle() {
		return article;
	}
	public void setArticle(ArticleBean article) {
		this.article = article;
	}
	

}
