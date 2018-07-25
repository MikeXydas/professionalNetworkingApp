package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the Advertisment database table.
 * 
 */
@Embeddable
public class AdvertismentPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int idAdvertisment;

	@Column(name="User_idUser", insertable=false, updatable=false)
	private int user_idUser;

	public AdvertismentPK() {
	}
	public int getIdAdvertisment() {
		return this.idAdvertisment;
	}
	public void setIdAdvertisment(int idAdvertisment) {
		this.idAdvertisment = idAdvertisment;
	}
	public int getUser_idUser() {
		return this.user_idUser;
	}
	public void setUser_idUser(int user_idUser) {
		this.user_idUser = user_idUser;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AdvertismentPK)) {
			return false;
		}
		AdvertismentPK castOther = (AdvertismentPK)other;
		return 
			(this.idAdvertisment == castOther.idAdvertisment)
			&& (this.user_idUser == castOther.user_idUser);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idAdvertisment;
		hash = hash * prime + this.user_idUser;
		
		return hash;
	}
}