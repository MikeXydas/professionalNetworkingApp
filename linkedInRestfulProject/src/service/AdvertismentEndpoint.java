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

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import model.UserBean;
import model.LogInfoBean;
import model.SkillListBean;
import model.AdvertismentBean;

@Path("/Advertisment")
public class AdvertismentEndpoint {

	//Testing the upload of an advertisment
	/*@POST
	@Path("/post")
	public Response postAd(
			@FormParam("id") int id,
			@FormParam("title") String title,
			@FormParam("content") String content) {
		AdvertismentDB advertismentDao = new AdvertismentDB();
		UserDB userDao = new UserDB();

		entities.User userd = userDao.getById(id);
		entities.Advertisment ad = new entities.Advertisment();
		
		ad.setTitle(title);
		ad.setDescriptionText(content);
		Date date = new Date();
		ad.setUploadTime(date);
		ad.setUser(userd);
		
		entities.AdvertismentPK pk = new entities.AdvertismentPK();
		pk.setUser_idUser(id);
		ad.setId(pk);
		advertismentDao.insertAdvertisment(ad);

		return Response.status(200).entity("Succesfully uploaded advertisment").build();
	}*/
	
	//CARE since the adBean must have the User field not null to get the user id
	@POST
	@Path("/post")
	@Consumes({"application/json"})
	public Response postAd(final AdvertismentBean adBean) {
		AdvertismentDB advertismentDao = new AdvertismentDB();
		UserDB userDao = new UserDB();

		entities.User userd = userDao.getById(adBean.getUser().getIdUser());
		entities.Advertisment ad = new entities.Advertisment();
		
		ad.setTitle(adBean.getTitle());
		ad.setDescriptionText(adBean.getDescriptionText());
		Date date = new Date();
		ad.setUploadTime(date);
		ad.setUser(userd);
		
		entities.AdvertismentPK pk = new entities.AdvertismentPK();
		pk.setUser_idUser(adBean.getUser().getIdUser());
		ad.setId(pk);
		advertismentDao.insertAdvertisment(ad);

		return Response.status(200).build();
	}
			
}
