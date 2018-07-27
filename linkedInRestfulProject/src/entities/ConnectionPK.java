package entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the Connection database table.
 * 
 */
@Embeddable
public class ConnectionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int idConnection;

	@Column(name="User_idUser", insertable=false, updatable=false)
	private int user_idUser;

	public ConnectionPK() {
	}
	public int getIdConnection() {
		return this.idConnection;
	}
	public void setIdConnection(int idConnection) {
		this.idConnection = idConnection;
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
		if (!(other instanceof ConnectionPK)) {
			return false;
		}
		ConnectionPK castOther = (ConnectionPK)other;
		return 
			(this.idConnection == castOther.idConnection)
			&& (this.user_idUser == castOther.user_idUser);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idConnection;
		hash = hash * prime + this.user_idUser;
		
		return hash;
	}
}