package entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the Comment database table.
 * 
 */
@Embeddable
public class CommentPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idComment;

	@Column(name="Article_idArticle", insertable=false, updatable=false)
	private int article_idArticle;

	@Column(name="Article_User_idUser", insertable=false, updatable=false)
	private int article_User_idUser;

	public CommentPK() {
	}
	public int getIdComment() {
		return this.idComment;
	}
	public void setIdComment(int idComment) {
		this.idComment = idComment;
	}
	public int getArticle_idArticle() {
		return this.article_idArticle;
	}
	public void setArticle_idArticle(int article_idArticle) {
		this.article_idArticle = article_idArticle;
	}
	public int getArticle_User_idUser() {
		return this.article_User_idUser;
	}
	public void setArticle_User_idUser(int article_User_idUser) {
		this.article_User_idUser = article_User_idUser;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CommentPK)) {
			return false;
		}
		CommentPK castOther = (CommentPK)other;
		return 
			(this.idComment == castOther.idComment)
			&& (this.article_idArticle == castOther.article_idArticle)
			&& (this.article_User_idUser == castOther.article_User_idUser);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idComment;
		hash = hash * prime + this.article_idArticle;
		hash = hash * prime + this.article_User_idUser;
		
		return hash;
	}
}