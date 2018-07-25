package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the Message database table.
 * 
 */
@Embeddable
public class MessagePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int idMessage;

	@Column(name="Conversation_idConversation", insertable=false, updatable=false)
	private int conversation_idConversation;

	public MessagePK() {
	}
	public int getIdMessage() {
		return this.idMessage;
	}
	public void setIdMessage(int idMessage) {
		this.idMessage = idMessage;
	}
	public int getConversation_idConversation() {
		return this.conversation_idConversation;
	}
	public void setConversation_idConversation(int conversation_idConversation) {
		this.conversation_idConversation = conversation_idConversation;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MessagePK)) {
			return false;
		}
		MessagePK castOther = (MessagePK)other;
		return 
			(this.idMessage == castOther.idMessage)
			&& (this.conversation_idConversation == castOther.conversation_idConversation);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idMessage;
		hash = hash * prime + this.conversation_idConversation;
		
		return hash;
	}
}