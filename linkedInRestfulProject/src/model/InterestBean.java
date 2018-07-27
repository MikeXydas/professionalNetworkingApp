package model;

import java.util.Date;

import entities.Article;
import entities.InterestPK;

public class InterestBean {
	
	private InterestPK id;
	private Date interestTime;
	private Article article;
	
	public InterestBean() {
	}

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
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}

}
