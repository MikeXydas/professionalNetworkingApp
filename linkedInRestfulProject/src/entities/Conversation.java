package entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the Conversation database table.
 * 
 */
@Entity
@Table(name="`Conversation`")
@NamedQuery(name="Conversation.findAll", query="SELECT c FROM Conversation c")
public class Conversation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idConversation;

	//bi-directional many-to-one association to Message
	@OneToMany(mappedBy="conversation")
	private List<Message> messages;

	//bi-directional many-to-many association to User
	@ManyToMany(mappedBy="conversations")
	private List<User> users;

	public Conversation() {
	}

	public int getIdConversation() {
		return this.idConversation;
	}

	public void setIdConversation(int idConversation) {
		this.idConversation = idConversation;
	}

	public List<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public Message addMessage(Message message) {
		getMessages().add(message);
		message.setConversation(this);

		return message;
	}

	public Message removeMessage(Message message) {
		getMessages().remove(message);
		message.setConversation(null);

		return message;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}