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
import db.ConnectionDB;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import model.UserBean;
import model.LogInfoBean;
import model.SkillListBean;
import model.AdvertismentBean;
import model.AdvertismentPostBean;
import model.ConnectionRequestBean;
import model.ConnectionRequestPKBean;
import model.PendingRequestBean;

@Path("ConnectionRequest")
public class ConnectionRequestEndpoint {

	//Testing the send request (no checks)
	@POST
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
		
		
	}
	
	/*@POST
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
	}*/
	
	/*@POST
	@Path("/accept")
	public Response acceptRequest(
			@FormParam("reqId") int reqId,
			@FormParam("sendId") int sendId) {
		
		ConnectionRequestDB connectionRequestDao = new ConnectionRequestDB();
		UserDB userDao = new UserDB();
		ConnectionDB connectionDao = new ConnectionDB();
		
		entities.ConnectionRequestPK reqPk = new entities.ConnectionRequestPK();
		reqPk.setIdConnectionRequest(reqId);
		reqPk.setUser_idUser(sendId);
		
		entities.ConnectionRequest reqd = connectionRequestDao.getById(reqPk);
		
		entities.User userReceive = userDao.getById(reqd.getSenderId());
		
		entities.User userSend = reqd.getUser();
		
		connectionRequestDao.deleteConnectionRequest(reqd);

		entities.Connection sendConnect = new entities.Connection();
		entities.Connection receiveConnect = new entities.Connection();

		entities.ConnectionPK sendPk = new entities.ConnectionPK();
		entities.ConnectionPK receivePk = new entities.ConnectionPK();;
		
		sendConnect.setUser(userSend);
		receiveConnect.setUser(userReceive);
		
		sendConnect.setId(sendPk);
		receiveConnect.setId(receivePk);
		
		sendConnect.setConnectedUserId(userReceive.getIdUser());
		receiveConnect.setConnectedUserId(userSend.getIdUser());
		
		connectionDao.insertConnection(sendConnect);
		connectionDao.insertConnection(receiveConnect);
		
		return Response.status(200).entity("Succesfully accepted request").build();

	}*/
	
	@POST
	@Path("/accept")
	@Consumes({"application/json"})
	public Response acceptRequest(final ConnectionRequestBean reqBean) {
		
		ConnectionRequestDB connectionRequestDao = new ConnectionRequestDB();
		UserDB userDao = new UserDB();
		ConnectionDB connectionDao = new ConnectionDB();
		
		ConnectionRequestPKBean reqPkBean = reqBean.getId();
		entities.ConnectionRequestPK reqPk = new entities.ConnectionRequestPK();
		reqPk.setIdConnectionRequest(reqPkBean.getIdConnectionRequest());
		reqPk.setUser_idUser(reqPkBean.getUser_idUser());
		entities.ConnectionRequest reqd = connectionRequestDao.getById(reqPk);

		entities.User userReceive = userDao.getById(reqd.getSenderId());
		entities.User userSend = reqd.getUser();
		
		connectionRequestDao.deleteConnectionRequest(reqd);

		entities.Connection sendConnect = new entities.Connection();
		entities.Connection receiveConnect = new entities.Connection();

		entities.ConnectionPK sendPk = new entities.ConnectionPK();
		entities.ConnectionPK receivePk = new entities.ConnectionPK();;
		
		sendConnect.setUser(userSend);
		receiveConnect.setUser(userReceive);
		
		sendConnect.setId(sendPk);
		receiveConnect.setId(receivePk);
		
		sendConnect.setConnectedUserId(userReceive.getIdUser());
		receiveConnect.setConnectedUserId(userSend.getIdUser());
		
		connectionDao.insertConnection(sendConnect);
		connectionDao.insertConnection(receiveConnect);
		
		return Response.status(200).build();
		
	}
	
	//Example call: http://localhost:8080/linkedInRestfulProject/services/ConnectionRequest/pending/33
		
	//This test only returns the first names of the people that requested connection on the userId
	/*@GET
	@Path("/pending/{id:[0-9]*}")
	public Response returnPendingRequests(
			@PathParam("id") int id) {
		ConnectionRequestDB connectionRequestDao = new ConnectionRequestDB();

		List <entities.ConnectionRequest> requests = connectionRequestDao.getPendingRequests(id);
		if(requests.size() == 0) {
			return Response.status(200).entity("No pending request").build();
		}
		else {
			String retString = "";
			for(int i = 0; i < requests.size(); i++) {
				retString += " | " + requests.get(i).getUser().getFirstName();
			}
			return Response.status(200).entity(retString).build();
		}
	}*/
	
	@GET
	@Path("/pending/{id:[0-9]*}")
	@Produces({"application/json"})
	public List<PendingRequestBean> returnPendingRequests(@PathParam("id") int id) {
		ConnectionRequestDB connectionRequestDao = new ConnectionRequestDB();
		List <entities.ConnectionRequest> requests = connectionRequestDao.getPendingRequests(id);

		List<PendingRequestBean> retList = new ArrayList<PendingRequestBean>() ;
		
		for(int i = 0; i < requests.size(); i++) {
			PendingRequestBean temp = new PendingRequestBean();
			temp.setUserId(requests.get(i).getUser().getIdUser());
			temp.setFirstName(requests.get(i).getUser().getFirstName());
			temp.setLastName(requests.get(i).getUser().getLastName());
			temp.setReqId(requests.get(i).getId().getIdConnectionRequest());
			temp.setPhotoUrl(requests.get(i).getUser().getPhotoUrl());
			temp.setSendTime(requests.get(i).getSendTime());
			
			retList.add(temp);
		}
		
		return retList;
	}

}










