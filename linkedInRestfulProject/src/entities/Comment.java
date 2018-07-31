package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the Comment database table.
 * 
 */
@Entity
@Table(name="`Comment`")
@NamedQuery(name="Comment.findAll", query="SELECT c FROM Comment c")
public class Comment implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CommentPK id;

	@Lob
	private String contentText;

	@Temporal(TemporalType.TIMESTAMP)
	private Date uploadTime;

	//bi-directional many-to-one association to Article
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="Article_idArticle", referencedColumnName="idArticle"),
		@JoinColumn(name="Article_User_idUser", referencedColumnName="User_idUser")
		})
	private Article article;

	public Comment() {
	}

	public CommentPK getId() {
		return this.id;
	}

	public void setId(CommentPK id) {
		this.id = id;
	}

	public String getContentText() {
		return this.contentText;
	}

	public void setContentText(String contentText) {
		this.contentText = contentText;
	}

	public Date getUploadTime() {
		return this.uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public Article getArticle() {
		return this.article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

}