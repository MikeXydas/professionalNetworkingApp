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
import db.MessageDB;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import model.UserBean;
import model.LogInfoBean;
import model.SkillListBean;
import model.BeginConversationBean;
import model.MessageBean;
import model.PendingRequestBean;
import model.ShowConvBean;

@Path("/Conversation")
public class ConversationEndpoint {
	
	@POST
	@Path("/begin")
	@Consumes({"application/json"})
	@Produces({"application/json"})
	public Response beginConversation(final BeginConversationBean convBean) {
		UserDB user1Dao = new UserDB();
		UserDB user2Dao = new UserDB();
		ConversationDB convDao = new ConversationDB();
		
		entities.User userSender = user1Dao.getById(convBean.getUserId1());
		entities.User userReceiver = user2Dao.getById(convBean.getUserId2());
		
		List<entities.Conversation> convs1 = userSender.getConversations();
		List<entities.Conversation> convs2 = userReceiver.getConversations();
		
		if((convs1 != null) && (convs2 != null)) {
			for(int whichConv1 = 0; whichConv1 < convs1.size(); whichConv1++) {
				for(int whichConv2 = 0; whichConv2 < convs2.size(); whichConv2++) {
					if(convs1.get(whichConv1).getIdConversation() == convs2.get(whichConv2).getIdConversation()) {
						return Response.status(200).entity("{\"convId\": " + convs1.get(whichConv1).getIdConversation() + "}").build();
					}
				}
			}
		}

		
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
		
		return Response.status(200).entity("{\"convId\": " + convid + "}").build();
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
	
	
	@GET
	@Produces({"application/json"})
	@Path("/showConvs/{id:[0-9]*}")
	public Response returnConvs(@PathParam("id") int id) {
		UserDB userDao = new UserDB();
		ConversationDB convDao = new ConversationDB();

		entities.User userd = userDao.getById(id);
		List<ShowConvBean> retList = new ArrayList<ShowConvBean>();

		List<entities.Conversation> convsd = userd.getConversations();
		
		if(convsd != null) {
			for(int whichConv = 0; whichConv < convsd.size(); whichConv++) {
				entities.User tempUserd = null;
				if(convsd.get(whichConv).getUsers().get(0).getIdUser() != id) {
					tempUserd = convsd.get(whichConv).getUsers().get(0);
				}
				else {
					tempUserd = convsd.get(whichConv).getUsers().get(1);
				}
				
				ShowConvBean tempBean = new ShowConvBean();
				tempBean.setIdConversation(convsd.get(whichConv).getIdConversation());
				tempBean.setFirstName(tempUserd.getFirstName());
				tempBean.setLastName(tempUserd.getLastName());
				
				retList.add(tempBean);
			}
		}
		return Response.status(200).entity(retList).build();
	}
	
	
	@GET
	@Produces({"application/json"})
	@Path("/showMessages/{id:[0-9]*}")
	public List<MessageBean> returnMessages (@PathParam("id") int id) {
		MessageDB messageDao = new MessageDB();
		
		List<entities.Message> messages = messageDao.getConvMessages(id);
		
		List<MessageBean> retList = new ArrayList<MessageBean>();
		
		for(int i = 0; i < messages.size(); i++) {
			MessageBean temp = new MessageBean();
			
			temp.setSenderId(messages.get(i).getSenderId());
			temp.setContentText(messages.get(i).getContentText());
			temp.setSendTime(messages.get(i).getSendTime());
			
			retList.add(temp);
		}
		
		return retList;
		
	}

}
