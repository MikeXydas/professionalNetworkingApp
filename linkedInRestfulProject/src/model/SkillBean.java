package model;

import java.util.List;

public class SkillBean {

	private int idSkill;
	private String skillName;
	private List<AdvertismentBean> advertisments;
	private List<UserBean> users;
	
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
	public List<AdvertismentBean> getAdvertisments() {
		return advertisments;
	}
	public void setAdvertisments(List<AdvertismentBean> advertisments) {
		this.advertisments = advertisments;
	}
	public List<UserBean> getUsers() {
		return users;
	}
	public void setUsers(List<UserBean> users) {
		this.users = users;
	}

}
