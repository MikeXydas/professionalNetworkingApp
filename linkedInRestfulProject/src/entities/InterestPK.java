package entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the Interest database table.
 * 
 */
@Embeddable
public class InterestPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int idInterest;

	@Column(name="Article_idArticle", insertable=false, updatable=false)
	private int article_idArticle;


	public InterestPK() {
	}
	public int getIdInterest() {
		return this.idInterest;
	}
	public void setIdInterest(int idInterest) {
		this.idInterest = idInterest;
	}
	public int getArticle_idArticle() {
		return this.article_idArticle;
	}
	public void setArticle_idArticle(int article_idArticle) {
		this.article_idArticle = article_idArticle;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof InterestPK)) {
			return false;
		}
		InterestPK castOther = (InterestPK)other;
		return 
			(this.idInterest == castOther.idInterest)
			&& (this.article_idArticle == castOther.article_idArticle);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idInterest;
		hash = hash * prime + this.article_idArticle;		
		return hash;
	}
}