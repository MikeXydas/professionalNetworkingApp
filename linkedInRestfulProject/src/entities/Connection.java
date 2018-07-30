package entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Connection database table.
 * 
 */
@Entity
@NamedQuery(name="Connection.findAll", query="SELECT c FROM Connection c")
public class Connection implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ConnectionPK id;

	private int connectedUserId;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="User_idUser")
	private User user;

	public Connection() {
	}

	public ConnectionPK getId() {
		return this.id;
	}

	public void setId(ConnectionPK id) {
		this.id = id;
	}

	public int getConnectedUserId() {
		return this.connectedUserId;
	}

	public void setConnectedUserId(int connectedUserId) {
		this.connectedUserId = connectedUserId;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}