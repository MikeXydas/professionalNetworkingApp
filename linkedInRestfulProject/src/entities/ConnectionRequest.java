package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ConnectionRequest database table.
 * 
 */
@Entity
@Table(name="`ConnectionRequest`")
@NamedQuery(name="ConnectionRequest.findAll", query="SELECT c FROM ConnectionRequest c")
public class ConnectionRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ConnectionRequestPK id;

	private int senderId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date sendTime;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="User_idUser")
	private User user;

	public ConnectionRequest() {
	}

	public ConnectionRequestPK getId() {
		return this.id;
	}

	public void setId(ConnectionRequestPK id) {
		this.id = id;
	}

	public int getSenderId() {
		return this.senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public Date getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}