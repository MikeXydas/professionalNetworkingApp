package utilities;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XmlCreator {
	
	public Node getUser(entities.User userd, Document doc) {
		Element user = doc.createElement("User");
		user.setAttribute("id", Integer.toString(userd.getIdUser()));
		user.appendChild(getUserElements(doc, user, "firstName", userd.getFirstName()));
		user.appendChild(getUserElements(doc, user, "lastName", userd.getLastName()));
		user.appendChild(getUserElements(doc, user, "email", userd.getEmail()));
		user.appendChild(getUserElements(doc, user, "phoneNumber", userd.getPhoneNumber()));
		if(userd.getEducationText() != null)
			user.appendChild(getUserElements(doc, user, "educationText", userd.getEducationText()));
		else
			user.appendChild(getUserElements(doc, user, "educationText", "NULL"));

		if(userd.getJobExperienceText() != null)
			user.appendChild(getUserElements(doc, user, "jobExperienceText", userd.getJobExperienceText()));
		else
			user.appendChild(getUserElements(doc, user, "jobExperienceText", "NULL"));

		if(userd.getConnections().size() == 0)
			user.appendChild(getConnections(null, doc, -1));
		else {
			List <entities.Connection> connections = userd.getConnections();
			for(int i = 0; i < connections.size(); i++) {
				user.appendChild(getConnections(connections.get(i).getUser(), doc, connections.get(i).getId().getIdConnection()));
			}
		}
		return user;
	}
	
	public Node getConnections(entities.User userd, Document doc, int connectionId) {
		
		Element connect = null;
		if(userd == null) {
			connect = doc.createElement("Connection");
	        connect.setAttribute("id", "-1");
		}
		else {
			connect = doc.createElement("Connection");
	        connect.setAttribute("id", Integer.toString(connectionId));
	        
	        Element connectedUserId  = doc.createElement("connectedUserId");
	        connectedUserId.appendChild(doc.createTextNode(Integer.toString(userd.getIdUser())));
	        connect.appendChild(connectedUserId);

	        Element name = doc.createElement("Name");
	        name.appendChild(doc.createTextNode(userd.getFirstName() + " " + userd.getLastName()));
	        connect.appendChild(name);
	        
	        Element email  = doc.createElement("Email");
	        email.appendChild(doc.createTextNode(userd.getEmail()));
	        connect.appendChild(email);
		}
        return connect;
    }
 
    // utility method to create text node
	public Node getUserElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
}
