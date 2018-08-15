package service;

import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Iterator;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;

import annotations.Secured;

import javax.ws.rs.core.UriBuilder;

import db.UserDB;
import db.ConversationDB;
import db.SkillDB;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import model.UserBean;
import model.LogInfoBean;
import model.SkillListBean;
import model.ConversationBean;

import model.BeginConversationBean;

@Path("/Conversation")
public class ConversationEndpoint {
	
	/*@POST
	@Path("/begin")
	public Response beginConversation(
			@FormParam("userId1") int userId1,
			@FormParam("userId2") int userId2
			) {
		
		
		UserDB user1Dao = new UserDB();
		UserDB user2Dao = new UserDB();
		ConversationDB convDao = new ConversationDB();
		
		entities.User userSender = user1Dao.getById(userId1);
		entities.User userReceiver = user2Dao.getById(userId2);
		
		entities.Conversation conv = new entities.Conversation();
		
		Date date = new Date();
		conv.setLastModified(date);
		int convid = convDao.insertConversation(conv);
		
		List<entities.Conversation> conversationsS = userSender.getConversations();
		conversationsS.add(conv);
		userSender.setConversations(conversationsS);
	
		
		List<entities.Conversation> conversationsR = userReceiver.getConversations();
		conversationsR.add(conv);
		userReceiver.setConversations(conversationsR);
		
		user1Dao.mergeUser(userSender);
		user2Dao.mergeUser(userReceiver);
		
		
		return Response.status(200).entity("Succesfully began conversation with users:" + userSender.getIdUser() +" "+ userReceiver.getIdUser()+" with id "+ convid).build();
	}*/
	
	@POST
	@Path("/begin")
	@Consumes({"application/json"})
	public Response beginConversation(final BeginConversationBean convBean) {
		UserDB user1Dao = new UserDB();
		UserDB user2Dao = new UserDB();
		ConversationDB convDao = new ConversationDB();
		
		entities.User userSender = user1Dao.getById(convBean.getUserId1());
		entities.User userReceiver = user2Dao.getById(convBean.getUserId2());
		
		entities.Conversation conv = new entities.Conversation();
		
		Date date = new Date();
		conv.setLastModified(date);
		int convid = convDao.insertConversation(conv);
		
		List<entities.Conversation> conversationsS = userSender.getConversations();
		conversationsS.add(conv);
		userSender.setConversations(conversationsS);
	
		
		List<entities.Conversation> conversationsR = userReceiver.getConversations();
		conversationsR.add(conv);
		userReceiver.setConversations(conversationsR);
		
		user1Dao.mergeUser(userSender);
		user2Dao.mergeUser(userReceiver);
		
		return Response.status(200).build();
	}
	
	@POST
	@Path("/find")
	public Response findConversation(
			@FormParam("userId1") int userId1,
			@FormParam("userId2") int userId2)
	{
		
		ConversationDB convDao = new ConversationDB();

		
		int convid = -1;
		
		entities.Conversation conv = null;
		
		conv = convDao.find(userId1, userId2);
		
		if (conv !=null) {
			convid = conv.getIdConversation();
		}
		
		
		return Response.status(200).entity("Succesfully found conversation between users: " + userId1 + "  and " + userId2 + " with conversation id " + convid).build();
	}

}
