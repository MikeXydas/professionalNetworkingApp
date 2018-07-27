package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Advertisment_has_Skill database table.
 * 
 */
@Entity
@Table(name="`Advertisment_has_Skill`")
@NamedQuery(name="Advertisment_has_Skill.findAll", query="SELECT a FROM Advertisment_has_Skill a")
public class Advertisment_has_Skill implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private Advertisment_has_SkillPK id;

	//bi-directional one-to-one association to Advertisment
	@OneToOne
	@JoinColumns({
		@JoinColumn(name="Advertisment_idAdvertisment", referencedColumnName="idAdvertisment"),
		@JoinColumn(name="Advertisment_User_idUser", referencedColumnName="User_idUser")
		})
	private Advertisment advertisment;

	//bi-directional many-to-one association to Skill
	@ManyToOne
	@JoinColumn(name="Skill_idSkill")
	private Skill skill;

	public Advertisment_has_Skill() {
	}

	public Advertisment_has_SkillPK getId() {
		return this.id;
	}

	public void setId(Advertisment_has_SkillPK id) {
		this.id = id;
	}

	public Advertisment getAdvertisment() {
		return this.advertisment;
	}

	public void setAdvertisment(Advertisment advertisment) {
		this.advertisment = advertisment;
	}

	public Skill getSkill() {
		return this.skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

}