package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ConnectionRequest database table.
 * 
 */
@Embeddable
public class ConnectionRequestPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int idConnectionRequest;

	@Column(name="User_idUser", insertable=false, updatable=false)
	private int user_idUser;

	public ConnectionRequestPK() {
	}
	public int getIdConnectionRequest() {
		return this.idConnectionRequest;
	}
	public void setIdConnectionRequest(int idConnectionRequest) {
		this.idConnectionRequest = idConnectionRequest;
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
		if (!(other instanceof ConnectionRequestPK)) {
			return false;
		}
		ConnectionRequestPK castOther = (ConnectionRequestPK)other;
		return 
			(this.idConnectionRequest == castOther.idConnectionRequest)
			&& (this.user_idUser == castOther.user_idUser);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idConnectionRequest;
		hash = hash * prime + this.user_idUser;
		
		return hash;
	}
}