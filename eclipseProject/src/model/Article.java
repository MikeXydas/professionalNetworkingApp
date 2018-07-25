package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the Article database table.
 * 
 */
@Entity
@NamedQuery(name="Article.findAll", query="SELECT a FROM Article a")
public class Article implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ArticlePK id;

	@Lob
	private String contentText;

	private String photoUrl;

	private String soundUrl;

	private String title;

	@Temporal(TemporalType.TIMESTAMP)
	private Date uploadTime;

	private String videoUrl;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="User_idUser")
	private User user;

	//bi-directional many-to-one association to Comment
	@OneToMany(mappedBy="article")
	private List<Comment> comments;

	//bi-directional many-to-one association to Interest
	@OneToMany(mappedBy="article")
	private List<Interest> interests;

	public Article() {
	}

	public ArticlePK getId() {
		return this.id;
	}

	public void setId(ArticlePK id) {
		this.id = id;
	}

	public String getContentText() {
		return this.contentText;
	}

	public void setContentText(String contentText) {
		this.contentText = contentText;
	}

	public String getPhotoUrl() {
		return this.photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getSoundUrl() {
		return this.soundUrl;
	}

	public void setSoundUrl(String soundUrl) {
		this.soundUrl = soundUrl;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getUploadTime() {
		return this.uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getVideoUrl() {
		return this.videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Comment> getComments() {
		return this.comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Comment addComment(Comment comment) {
		getComments().add(comment);
		comment.setArticle(this);

		return comment;
	}

	public Comment removeComment(Comment comment) {
		getComments().remove(comment);
		comment.setArticle(null);

		return comment;
	}

	public List<Interest> getInterests() {
		return this.interests;
	}

	public void setInterests(List<Interest> interests) {
		this.interests = interests;
	}

	public Interest addInterest(Interest interest) {
		getInterests().add(interest);
		interest.setArticle(this);

		return interest;
	}

	public Interest removeInterest(Interest interest) {
		getInterests().remove(interest);
		interest.setArticle(null);

		return interest;
	}

}