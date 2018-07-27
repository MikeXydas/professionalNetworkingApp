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

	//bi-directional many-to-one association to Advertisment_has_Skill
	@OneToMany(mappedBy="skill")
	private List<Advertisment_has_Skill> advertismentHasSkills;

	//bi-directional many-to-one association to User_has_Skill
	@OneToMany(mappedBy="skill")
	private List<User_has_Skill> userHasSkills;

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

	public List<Advertisment_has_Skill> getAdvertismentHasSkills() {
		return this.advertismentHasSkills;
	}

	public void setAdvertismentHasSkills(List<Advertisment_has_Skill> advertismentHasSkills) {
		this.advertismentHasSkills = advertismentHasSkills;
	}

	public Advertisment_has_Skill addAdvertismentHasSkill(Advertisment_has_Skill advertismentHasSkill) {
		getAdvertismentHasSkills().add(advertismentHasSkill);
		advertismentHasSkill.setSkill(this);

		return advertismentHasSkill;
	}

	public Advertisment_has_Skill removeAdvertismentHasSkill(Advertisment_has_Skill advertismentHasSkill) {
		getAdvertismentHasSkills().remove(advertismentHasSkill);
		advertismentHasSkill.setSkill(null);

		return advertismentHasSkill;
	}

	public List<User_has_Skill> getUserHasSkills() {
		return this.userHasSkills;
	}

	public void setUserHasSkills(List<User_has_Skill> userHasSkills) {
		this.userHasSkills = userHasSkills;
	}

	public User_has_Skill addUserHasSkill(User_has_Skill userHasSkill) {
		getUserHasSkills().add(userHasSkill);
		userHasSkill.setSkill(this);

		return userHasSkill;
	}

	public User_has_Skill removeUserHasSkill(User_has_Skill userHasSkill) {
		getUserHasSkills().remove(userHasSkill);
		userHasSkill.setSkill(null);

		return userHasSkill;
	}

}