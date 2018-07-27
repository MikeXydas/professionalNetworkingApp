package model;

import java.util.List;

import entities.Advertisment_has_Skill;
import entities.User_has_Skill;

public class SkillBean {

	private int idSkill;
	private String skillName;
	private List<Advertisment_has_Skill> advertismentHasSkills;
	private List<User_has_Skill> userHasSkills;

	public SkillBean( ) {	
	}

	public int getIdSkill() {
		return idSkill;
	}

	public void setIdSkill(int idSkill) {
		this.idSkill = idSkill;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public List<Advertisment_has_Skill> getAdvertismentHasSkills() {
		return advertismentHasSkills;
	}

	public void setAdvertismentHasSkills(List<Advertisment_has_Skill> advertismentHasSkills) {
		this.advertismentHasSkills = advertismentHasSkills;
	}

	public List<User_has_Skill> getUserHasSkills() {
		return userHasSkills;
	}

	public void setUserHasSkills(List<User_has_Skill> userHasSkills) {
		this.userHasSkills = userHasSkills;
	}
	
}
