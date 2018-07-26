package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the Interest database table.
 * 
 */
@Entity
@Table(name="`Interest`")
@NamedQuery(name="Interest.findAll", query="SELECT i FROM Interest i")
public class Interest implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private InterestPK id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date interestTime;

	//bi-directional many-to-one association to Article
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="Article_idArticle", referencedColumnName="idArticle"),
		@JoinColumn(name="Article_User_idUser", referencedColumnName="User_idUser")
		})
	private Article article;

	public Interest() {
	}

	public InterestPK getId() {
		return this.id;
	}

	public void setId(InterestPK id) {
		this.id = id;
	}

	public Date getInterestTime() {
		return this.interestTime;
	}

	public void setInterestTime(Date interestTime) {
		this.interestTime = interestTime;
	}

	public Article getArticle() {
		return this.article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

}