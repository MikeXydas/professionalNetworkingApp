package service;

import java.io.IOException;
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
import utilities.FileManipulation;
import model.LogInfoBean;
import model.SkillListBean;
import model.AdvertismentPostBean;
import model.ConnectionRequestBean;
import model.PendingRequestBean;
import model.SendId;

@Path("ConnectionRequest")
public class ConnectionRequestEndpoint {

	
	@POST
	@Path("/send")
	@Consumes({"application/json"})
	public Response sendRequest(final ConnectionRequestBean reqBean) {
		//Care senderId IN THE ENTITY is actually receiverId (to be fixed)
		
		ConnectionRequestDB connectionRequestDao = new ConnectionRequestDB();		
		entities.ConnectionRequest connd = null;
		UserDB userDao = new UserDB();
		entities.User userd = userDao.getById(userDao.getById(reqBean.getUserId()).getIdUser());
		List <entities.ConnectionRequest> reqList = userd.getConnectionRequests();
		
		int reqExists = 0;
		int i;
		for(i = 0; i < reqList.size(); i++) {
			if(reqList.get(i).getSenderId() == reqBean.getReceiverId()) {
				reqExists = 1;
				break;
			}
		}
		
		if(reqExists == 0) {
			connd = new entities.ConnectionRequest();
			connd.setSenderId(reqBean.getReceiverId());
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
	
	@POST
	@Path("/accept/{id:[0-9]*}")
	public Response acceptRequest(@PathParam("id") final Integer id) {
		
		ConnectionRequestDB connectionRequestDao = new ConnectionRequestDB();
		UserDB userDao = new UserDB();
		ConnectionDB connectionDao = new ConnectionDB();
		
		//ConnectionRequestPKBean reqPkBean = reqBean.getId();
		//entities.ConnectionRequestPK reqPk = new entities.ConnectionRequestPK();
		//reqPk.setIdConnectionRequest(reqPkBean.getIdConnectionRequest());
		//reqPk.setUser_idUser(reqPkBean.getUser_idUser());
		entities.ConnectionRequest reqd = connectionRequestDao.getById(id);

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
	
	@POST
	@Path("/decline/{id:[0-9]*}")
	public Response declineRequest(@PathParam("id") final Integer id) {
		ConnectionRequestDB connectionRequestDao = new ConnectionRequestDB();
		UserDB userDao = new UserDB();
		ConnectionDB connectionDao = new ConnectionDB();
		
		//ConnectionRequestPKBean reqPkBean = reqBean.getId();
		//entities.ConnectionRequestPK reqPk = new entities.ConnectionRequestPK();
		//reqPk.setIdConnectionRequest(reqPkBean.getIdConnectionRequest());
		//reqPk.setUser_idUser(reqPkBean.getUser_idUser());
		entities.ConnectionRequest reqd = connectionRequestDao.getById(id);

		entities.User userReceive = userDao.getById(reqd.getSenderId());
		entities.User userSend = reqd.getUser();
		
		connectionRequestDao.deleteConnectionRequest(reqd);
		
		return Response.status(200).build();

	}
	
	@GET
	@Produces({"application/json"})
	@Path("/pending/{id:[0-9]*}")
	public Response returnPendingRequests(@PathParam("id") final Integer id) throws IOException {
		ConnectionRequestDB connectionRequestDao = new ConnectionRequestDB();

		List <entities.ConnectionRequest> requests = connectionRequestDao.getPendingRequests(id);

		List<PendingRequestBean> retList = new ArrayList<PendingRequestBean>();
		
		for(int i = 0; i < requests.size(); i++) {
			PendingRequestBean temp = new PendingRequestBean();
			temp.setUserId(requests.get(i).getUser().getIdUser());
			temp.setFirstName(requests.get(i).getUser().getFirstName());
			temp.setLastName(requests.get(i).getUser().getLastName());
			temp.setReqId(requests.get(i).getId().getIdConnectionRequest());
			temp.setSendTime(requests.get(i).getSendTime());
			
			
			retList.add(temp);
		}
		
		return Response.status(200).entity(retList).build();
	}
	
	@GET
	@Path("/connections/{id:[0-9]*}")
	@Produces({"application/json"})
	@Consumes({"application/json"})
	public Response returnConnections (@PathParam("id") final Integer id) {
		ConnectionDB connectionDao = new ConnectionDB();
		UserEndpoint userEnd = new UserEndpoint();
		
		List<entities.Connection> connectionsd = connectionDao.getConnectionsOfUser(id);
		
		List <UserBean> userBeans = new ArrayList<UserBean>();
		
		for(int i = 0; i < connectionsd.size(); i++) {
			userBeans.add(userEnd.createUserBeanFromEntity(connectionsd.get(i).getUser()));
		}
		
		return Response.ok(userBeans).build();
	}

}










