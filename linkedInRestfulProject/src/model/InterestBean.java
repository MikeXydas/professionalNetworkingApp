package model;

import java.util.Date;

import entities.Article;
import entities.InterestPK;

public class InterestBean {
	
	private InterestPK id;
	private Date interestTime;
	private int interesterId;
	private Article article;
	
	public InterestPK getId() {
		return id;
	}
	public void setId(InterestPK id) {
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
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	

}
