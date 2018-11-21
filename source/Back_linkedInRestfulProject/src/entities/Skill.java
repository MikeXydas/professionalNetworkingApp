package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the Skill database table.
 * 
 */
@Entity
@Table(name="`Skill`")
@NamedQuery(name="Skill.findAll", query="SELECT s FROM Skill s")
public class Skill implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idSkill;

	private String skillName;

	//bi-directional many-to-many association to Advertisment
	@ManyToMany
	@JoinTable(
		name="Advertisment_has_Skill"
		, joinColumns={
			@JoinColumn(name="Skill_idSkill")
			}
		, inverseJoinColumns={
			@JoinColumn(name="Advertisment_idAdvertisment", referencedColumnName="idAdvertisment"),
			@JoinColumn(name="Advertisment_User_idUser", referencedColumnName="User_idUser")
			}
		)
	private List<Advertisment> advertisments;

	//bi-directional many-to-many association to User
	@ManyToMany(mappedBy="skills")
	private List<User> users;

	public Skill() {
	}

	public int getIdSkill() {
		return this.idSkill;
	}

	public void setIdSkill(int idSkill) {
		this.idSkill = idSkill;
	}

	public String getSkillName() {
		return this.skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public List<Advertisment> getAdvertisments() {
		return this.advertisments;
	}

	public void setAdvertisments(List<Advertisment> advertisments) {
		this.advertisments = advertisments;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}