package utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import db.CommentDB;
import db.InterestDB;

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
		user.appendChild(getUserElements(doc, user, "photoUrl", userd.getPhotoUrl()));

		if(userd.getEducationText() != null)
			user.appendChild(getUserElements(doc, user, "educationText", userd.getEducationText()));
		else
			user.appendChild(getUserElements(doc, user, "educationText", "NULL"));

		if(userd.getJobExperienceText() != null)
			user.appendChild(getUserElements(doc, user, "jobExperienceText", userd.getJobExperienceText()));
		else
			user.appendChild(getUserElements(doc, user, "jobExperienceText", "NULL"));

		Element connectionsNode = doc.createElement("Connections");
		if(userd.getConnections().size() != 0)
		{
			List <entities.Connection> connections = userd.getConnections();
			for(int i = 0; i < connections.size(); i++) {
				connectionsNode.appendChild(getConnections(connections.get(i).getUser(), doc, connections.get(i).getId().getIdConnection()));
			}
		}
		user.appendChild(connectionsNode);
		
		Element articlesNode = doc.createElement("Articles");
		if(userd.getArticles().size() != 0)
		{
			List <entities.Article> articles = userd.getArticles();
			for(int i = 0; i < articles.size(); i++) {
				articlesNode.appendChild(getArticle(articles.get(i), doc));
			}
		}
		user.appendChild(articlesNode);
		
		Element advertismentsNode = doc.createElement("Advertisments");
		if(userd.getAdvertisments().size() != 0)
		{
			List <entities.Advertisment> advertisments = userd.getAdvertisments();
			for(int i = 0; i < advertisments.size(); i++) {
				advertismentsNode.appendChild(getAdvertisment(advertisments.get(i), doc));
			}
		}
		user.appendChild(advertismentsNode);
		
		Element commentsNode = doc.createElement("Comments");
		CommentDB commentDao = new CommentDB();
		List <entities.Comment> comments = commentDao.commentsOfUser(userd.getIdUser());
		if(comments.size() != 0) {
			for(int i = 0; i < comments.size(); i++) {
				commentsNode.appendChild(getComment(comments.get(i), doc));
			}
		}
		user.appendChild(commentsNode);
		
		Element interestsNode = doc.createElement("Interests");
		InterestDB interestDao = new InterestDB();
		List <entities.Interest> interests = interestDao.interestsOfUser(userd.getIdUser());
		if(interests.size() != 0) {
			for(int i = 0; i < interests.size(); i++) {
				interestsNode.appendChild(getInterest(interests.get(i), doc));
			}
		}
		user.appendChild(interestsNode);
		
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
	
	public Node getArticle(entities.Article articled, Document doc) {
		Element article = null;
		if(articled == null) {
			article = doc.createElement("Article");
			article.setAttribute("id", "-1");
		}
		else {
			article = doc.createElement("Article");
			article.setAttribute("id", Integer.toString(articled.getId().getIdArticle()));
			
			Element articleTitle = doc.createElement("articleTitle");
			articleTitle.appendChild(doc.createTextNode(articled.getTitle()));
			article.appendChild(articleTitle);
			
			Element uploadTime = doc.createElement("uploadTime");
			DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
			uploadTime.appendChild(doc.createTextNode(dateFormat.format(articled.getUploadTime())));
			article.appendChild(uploadTime);
		}
		return article;
	}
 
	public Node getAdvertisment(entities.Advertisment add, Document doc) {
		Element ad = null;
		if(add == null) {
			ad = doc.createElement("Advertisment");
			ad.setAttribute("id", "-1");
		}
		else {
			ad = doc.createElement("Advertisment");
			ad.setAttribute("id", Integer.toString(add.getId().getIdAdvertisment()));
			
			Element advertismentTitle = doc.createElement("advertismentTitle");
			advertismentTitle.appendChild(doc.createTextNode(add.getTitle()));
			ad.appendChild(advertismentTitle);
			
			Element uploadTime = doc.createElement("uploadTime");
			DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
			uploadTime.appendChild(doc.createTextNode(dateFormat.format(add.getUploadTime())));
			ad.appendChild(uploadTime);
		}
		return ad;
	}
	
	public Node getComment(entities.Comment commentd, Document doc) {
		Element comment = null;
		if(commentd == null) {
			comment = doc.createElement("Comment");
			comment.setAttribute("id", "-1");
		}
		else {
			comment = doc.createElement("Comment");
			comment.setAttribute("id", Integer.toString(commentd.getId().getIdComment()));
			
			Element commentedArticleId = doc.createElement("commentedArticleId");
			commentedArticleId.appendChild(doc.createTextNode(Integer.toString(commentd.getId().getArticle_idArticle())));
			comment.appendChild(commentedArticleId);
			
			Element uploadTime = doc.createElement("uploadTime");
			DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
			uploadTime.appendChild(doc.createTextNode(dateFormat.format(commentd.getUploadTime())));
			comment.appendChild(uploadTime);
		}
		return comment;
	}
	
	public Node getInterest(entities.Interest interestd, Document doc) {
		Element interest = null;
		if(interestd == null) {
			interest = doc.createElement("Interest");
			interest.setAttribute("id", "-1");
		}
		else {
			interest = doc.createElement("Interest");
			interest.setAttribute("id", Integer.toString(interestd.getId().getIdInterest()));
			
			Element interestedArticleId = doc.createElement("interestedArticleId");
			interestedArticleId.appendChild(doc.createTextNode(Integer.toString(interestd.getId().getArticle_idArticle())));
			interest.appendChild(interestedArticleId);
			
			Element uploadTime = doc.createElement("uploadTime");
			DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
			uploadTime.appendChild(doc.createTextNode(dateFormat.format(interestd.getInterestTime())));
			interest.appendChild(uploadTime);
		}
		return interest;
	}
    // utility method to create text node
	public Node getUserElements(Document doc, Element element, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
}
