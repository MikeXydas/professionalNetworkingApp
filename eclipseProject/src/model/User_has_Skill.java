package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the User_has_Skill database table.
 * 
 */
@Entity
@Table(name="`User_has_Skill`")
@NamedQuery(name="User_has_Skill.findAll", query="SELECT u FROM User_has_Skill u")
public class User_has_Skill implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="User_idUser",insertable=false, updatable=false)
	private int user_idUser;

	//bi-directional many-to-one association to Skill
	@ManyToOne
	@JoinColumn(name="Skill_idSkill")
	private Skill skill;

	//bi-directional one-to-one association to User
	@OneToOne
	@JoinColumn(name="User_idUser")
	private User user;

	public User_has_Skill() {
	}

	public int getUser_idUser() {
		return this.user_idUser;
	}

	public void setUser_idUser(int user_idUser) {
		this.user_idUser = user_idUser;
	}

	public Skill getSkill() {
		return this.skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}