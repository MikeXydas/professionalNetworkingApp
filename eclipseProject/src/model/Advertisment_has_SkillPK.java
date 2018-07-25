package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the Advertisment_has_Skill database table.
 * 
 */
@Embeddable
public class Advertisment_has_SkillPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="Advertisment_idAdvertisment", insertable=false, updatable=false)
	private int advertisment_idAdvertisment;

	@Column(name="Advertisment_User_idUser", insertable=false, updatable=false)
	private int advertisment_User_idUser;

	public Advertisment_has_SkillPK() {
	}
	public int getAdvertisment_idAdvertisment() {
		return this.advertisment_idAdvertisment;
	}
	public void setAdvertisment_idAdvertisment(int advertisment_idAdvertisment) {
		this.advertisment_idAdvertisment = advertisment_idAdvertisment;
	}
	public int getAdvertisment_User_idUser() {
		return this.advertisment_User_idUser;
	}
	public void setAdvertisment_User_idUser(int advertisment_User_idUser) {
		this.advertisment_User_idUser = advertisment_User_idUser;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Advertisment_has_SkillPK)) {
			return false;
		}
		Advertisment_has_SkillPK castOther = (Advertisment_has_SkillPK)other;
		return 
			(this.advertisment_idAdvertisment == castOther.advertisment_idAdvertisment)
			&& (this.advertisment_User_idUser == castOther.advertisment_User_idUser);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.advertisment_idAdvertisment;
		hash = hash * prime + this.advertisment_User_idUser;
		
		return hash;
	}
}