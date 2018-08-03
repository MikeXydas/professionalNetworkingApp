package model;

import java.util.Date;

public class InterestBean {
	
	private InterestPKBean id;
	private Date interestTime;
	private int interesterId;
	private ArticleBean article;
	
	public InterestPKBean getId() {
		return id;
	}
	public void setId(InterestPKBean id) {
		this.id = id;
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
