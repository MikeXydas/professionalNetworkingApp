package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the Advertisment database table.
 * 
 */
@Entity
@NamedQuery(name="Advertisment.findAll", query="SELECT a FROM Advertisment a")
public class Advertisment implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AdvertismentPK id;

	@Lob
	private String descriptionText;

	private String title;

	@Temporal(TemporalType.TIMESTAMP)
	private Date uploadTime;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="User_idUser")
	private User user;

	//bi-directional many-to-many association to Skill
	@ManyToMany(mappedBy="advertisments")
	private List<Skill> skills;

	//bi-directional many-to-one association to Application
	@OneToMany(mappedBy="advertisment")
	private List<Application> applications;

	public Advertisment() {
	}

	public AdvertismentPK getId() {
		return this.id;
	}

	public void setId(AdvertismentPK id) {
		this.id = id;
	}

	public String getDescriptionText() {
		return this.descriptionText;
	}

	public void setDescriptionText(String descriptionText) {
		this.descriptionText = descriptionText;
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

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Skill> getSkills() {
		return this.skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}

	public List<Application> getApplications() {
		return this.applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}

	public Application addApplication(Application application) {
		getApplications().add(application);
		application.setAdvertisment(this);

		return application;
	}

	public Application removeApplication(Application application) {
		getApplications().remove(application);
		application.setAdvertisment(null);

		return application;
	}

}