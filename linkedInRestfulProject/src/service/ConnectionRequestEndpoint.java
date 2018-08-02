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
import db.SkillDB;
import db.AdvertismentDB;
import db.ConnectionRequestDB;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import model.UserBean;
import model.LogInfoBean;
import model.SkillListBean;
import model.AdvertismentBean;
import model.AdvertismentPostBean;
import model.ConnectionRequestBean;

@Path("ConnectionRequest")
public class ConnectionRequestEndpoint {

	//Testing the send request (no checks)
	/*@POST
	@Path("/send")
	public Response sendRequest(
			@FormParam("idSend") int idSend,
			@FormParam("idReceive") int idReceive) {
		ConnectionRequestDB connectionRequestDao = new ConnectionRequestDB();
		entities.ConnectionRequest connd = null;// = connectionRequestDao.find(idReceive, idSend);
		
		UserDB userDao = new UserDB();
		entities.User userd = userDao.getById(idSend);
		List <entities.ConnectionRequest> reqList = userd.getConnectionRequests();
		
		int reqExists = 0;
		int i;
		for(i = 0; i < reqList.size(); i++) {
			if(reqList.get(i).getSenderId() == idReceive) {
				reqExists = 1;
				break;
			}
		}
		//return Response.status(200).entity("reqExists = " + reqExists).build();

		if(reqExists == 0) {
			connd = new entities.ConnectionRequest();
			connd.setSenderId(idReceive);
			Date date = new Date();
			connd.setSendTime(date);
			connd.setUser(userd);
			
			entities.ConnectionRequestPK pk = new entities.ConnectionRequestPK();
			//pk.setUser_idUser(idSend);
			connd.setId(pk);
			connectionRequestDao.insertSkill(connd);
			
			return Response.status(200).entity("Succesfully sent request").build();

		}
		else
			return Response.status(200).entity("Request already existed").build();
		
		
	}*/
	
	@POST
	@Path("/send")
	@Consumes({"application/json"})
	public Response sendRequest(final ConnectionRequestBean reqBean) {
		//Care senderId is actually receiverId (to be fixed)
		
		ConnectionRequestDB connectionRequestDao = new ConnectionRequestDB();
		entities.ConnectionRequest connd = null;
		UserDB userDao = new UserDB();
		entities.User userd = userDao.getById(reqBean.getUser().getIdUser());
		List <entities.ConnectionRequest> reqList = userd.getConnectionRequests();
		
		int reqExists = 0;
		int i;
		for(i = 0; i < reqList.size(); i++) {
			if(reqList.get(i).getSenderId() == reqBean.getSenderId()) {
				reqExists = 1;
				break;
			}
		}
		
		if(reqExists == 0) {
			connd = new entities.ConnectionRequest();
			connd.setSenderId(reqBean.getSenderId());
			Date date = new Date();
			connd.setSendTime(date);
			connd.setUser(userd);
			
			entities.ConnectionRequestPK pk = new entities.ConnectionRequestPK();
			//pk.setUser_idUser(idSend);
			connd.setId(pk);
			connectionRequestDao.insertSkill(connd);
			
			return Response.status(200).build();

		}
		else {
			//Case that the request already exists
			return Response.status(200).build();
		}
	}
}










