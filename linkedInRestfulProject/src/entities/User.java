package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the User database table.
 * 
 */
@Entity
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idUser;

	@Lob
	private String educationText;

	private String email;

	private String firstName;

	private int isModerator;

	@Lob
	private String jobExperienceText;

	private String lastName;

	private String password;

	private String phoneNumber;

	private String photoUrl;

	//bi-directional many-to-one association to Advertisment
	@OneToMany(mappedBy="user")
	private List<Advertisment> advertisments;

	//bi-directional many-to-one association to Article
	@OneToMany(mappedBy="user")
	private List<Article> articles;

	//bi-directional many-to-one association to Connection
	@OneToMany(mappedBy="user")
	private List<Connection> connections;

	//bi-directional many-to-one association to ConnectionRequest
	@OneToMany(mappedBy="user")
	private List<ConnectionRequest> connectionRequests;

	//bi-directional many-to-many association to Conversation
	@ManyToMany
	@JoinTable(
		name="User_has_Conversation"
		, joinColumns={
			@JoinColumn(name="User_idUser")
			}
		, inverseJoinColumns={
			@JoinColumn(name="Conversation_idConversation")
			}
		)
	private List<Conversation> conversations;

	//bi-directional many-to-many association to Skill
	@ManyToMany
	@JoinTable(
		name="User_has_Skill"
		, joinColumns={
			@JoinColumn(name="User_idUser")
			}
		, inverseJoinColumns={
			@JoinColumn(name="Skill_idSkill")
			}
		)
	private List<Skill> skills;

	public User() {
	}

	public int getIdUser() {
		return this.idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getEducationText() {
		return this.educationText;
	}

	public void setEducationText(String educationText) {
		this.educationText = educationText;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public int getIsModerator() {
		return this.isModerator;
	}

	public void setIsModerator(int isModerator) {
		this.isModerator = isModerator;
	}

	public String getJobExperienceText() {
		return this.jobExperienceText;
	}

	public void setJobExperienceText(String jobExperienceText) {
		this.jobExperienceText = jobExperienceText;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPhotoUrl() {
		return this.photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public List<Advertisment> getAdvertisments() {
		return this.advertisments;
	}

	public void setAdvertisments(List<Advertisment> advertisments) {
		this.advertisments = advertisments;
	}

	public Advertisment addAdvertisment(Advertisment advertisment) {
		getAdvertisments().add(advertisment);
		advertisment.setUser(this);

		return advertisment;
	}

	public Advertisment removeAdvertisment(Advertisment advertisment) {
		getAdvertisments().remove(advertisment);
		advertisment.setUser(null);

		return advertisment;
	}

	public List<Article> getArticles() {
		return this.articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public Article addArticle(Article article) {
		getArticles().add(article);
		article.setUser(this);

		return article;
	}

	public Article removeArticle(Article article) {
		getArticles().remove(article);
		article.setUser(null);

		return article;
	}

	public List<Connection> getConnections() {
		return this.connections;
	}

	public void setConnections(List<Connection> connections) {
		this.connections = connections;
	}

	public Connection addConnection(Connection connection) {
		getConnections().add(connection);
		connection.setUser(this);

		return connection;
	}

	public Connection removeConnection(Connection connection) {
		getConnections().remove(connection);
		connection.setUser(null);

		return connection;
	}

	public List<ConnectionRequest> getConnectionRequests() {
		return this.connectionRequests;
	}

	public void setConnectionRequests(List<ConnectionRequest> connectionRequests) {
		this.connectionRequests = connectionRequests;
	}

	public ConnectionRequest addConnectionRequest(ConnectionRequest connectionRequest) {
		getConnectionRequests().add(connectionRequest);
		connectionRequest.setUser(this);

		return connectionRequest;
	}

	public ConnectionRequest removeConnectionRequest(ConnectionRequest connectionRequest) {
		getConnectionRequests().remove(connectionRequest);
		connectionRequest.setUser(null);

		return connectionRequest;
	}

	public List<Conversation> getConversations() {
		return this.conversations;
	}

	public void setConversations(List<Conversation> conversations) {
		this.conversations = conversations;
	}

	public List<Skill> getSkills() {
		return this.skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}

}