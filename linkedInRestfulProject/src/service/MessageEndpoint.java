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
import model.MessageBean;
import model.SkillListBean;
import model.ConversationBean;

import model.SendMessageBean;


@Path("/Message")
public class MessageEndpoint {
	
	/*@POST
	@Path("/send")
	public Response sendMessage(
			@FormParam("convId") int convId,
			@FormParam("contentText") String contentText,
			@FormParam("senderId") int senderId)
	{
		ConversationDB convDao = new ConversationDB();
		MessageDB messageDao = new MessageDB();
		
		entities.Message message = new entities.Message();
		entities.Conversation conv = convDao.getById(convId);
		
		Date date = new Date();
		message.setSendTime(date);
		message.setSenderId(senderId);
		message.setContentText(contentText);
		message.setConversation(conv);
		
		entities.MessagePK pk = new entities.MessagePK();
		pk.setConversation_idConversation(convId);
		message.setId(pk);
		
		conv.setLastModified(date);
		
		convDao.mergeConversation(conv);
		messageDao.insertMessage(message);
		
		
		return Response.status(200).entity("Succesfully send message").build();
	}*/

	@POST
	@Path("/send")
	@Consumes({"application/json"})
	@Produces({"application/json"})
	public Response sendMessage(final SendMessageBean messBean) {
		ConversationDB convDao = new ConversationDB();
		MessageDB messageDao = new MessageDB();
		
		entities.Message message = new entities.Message();
		entities.Conversation conv = convDao.getById(messBean.getConvId());
		
		Date date = new Date();
		message.setSendTime(date);
		message.setSenderId(messBean.getSenderId());
		message.setContentText(messBean.getContentText());
		message.setConversation(conv);
		
		entities.MessagePK pk = new entities.MessagePK();
		//pk.setConversation_idConversation(convId);
		message.setId(pk);
		
		conv.setLastModified(date);
		
		convDao.mergeConversation(conv);
		messageDao.insertMessage(message);
		
		MessageBean retMsg = new MessageBean();
		retMsg.setContentText(message.getContentText());
		retMsg.setSenderId(message.getSenderId());
		retMsg.setSendTime(message.getSendTime());
		
		return Response.status(200).entity(retMsg).build();
	}
}
