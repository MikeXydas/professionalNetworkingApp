package entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the Article database table.
 * 
 */
@Embeddable
public class ArticlePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int idArticle;

	@Column(name="User_idUser", insertable=false, updatable=false)
	private int user_idUser;

	public ArticlePK() {
	}
	public int getIdArticle() {
		return this.idArticle;
	}
	public void setIdArticle(int idArticle) {
		this.idArticle = idArticle;
	}
	public int getUser_idUser() {
		return this.user_idUser;
	}
	public void setUser_idUser(int user_idUser) {
		this.user_idUser = user_idUser;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ArticlePK)) {
			return false;
		}
		ArticlePK castOther = (ArticlePK)other;
		return 
			(this.idArticle == castOther.idArticle)
			&& (this.user_idUser == castOther.user_idUser);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idArticle;
		hash = hash * prime + this.user_idUser;
		
		return hash;
	}
}