package model;

import java.util.List;

import entities.Advertisment;
import entities.User;

public class SkillBean {

	private int idSkill;
	private String skillName;
	private List<Advertisment> advertisments;
	private List<User> users;

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

	public List<Advertisment> getAdvertisments() {
		return advertisments;
	}

	public void setAdvertisments(List<Advertisment> advertisments) {
		this.advertisments = advertisments;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}
