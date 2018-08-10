package service;

import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Iterator;
import java.io.*;
import java.nio.charset.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.UriInfo;
import javax.xml.parsers.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import annotations.Secured;

import javax.ws.rs.core.UriBuilder;

import db.UserDB;
import db.SkillDB;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import model.UserBean;
import model.ExportXMLBean;
import model.LogInfoBean;
import model.SkillListBean;
import utilities.XmlCreator;

@Path("/User")
public class UserEndpoint {
	
	@GET
	@Path("query")
	public Response getUser(@Context UriInfo info) {
		int id = Integer.parseInt(info.getQueryParameters().getFirst("id"));
		UserDB userDao = new UserDB();
		entities.User userd = userDao.getById(id);
		return Response.status(200).entity("First name is: " + userd.getFirstName()).build();
	}
	
	@Secured
	@POST
	@Path("/add")
	@Consumes({ "application/json" })
	public Response addUser(final UserBean user) {
		UserDB userDao = new UserDB();

		entities.User userd;
		//Checking if user already exists
		userd = userDao.find(user.getEmail(), user.getPassword());
		if(userd != null)
			return Response.status(Response.Status.UNAUTHORIZED).build();
		
		userd = new entities.User();
		userd.setEmail(user.getEmail());
		userd.setPassword(user.getPassword());
		userd.setIsModerator(0);
		userd.setFirstName(user.getFirstName());
		userd.setLastName(user.getLastName());
		userd.setPhoneNumber(user.getPhoneNumber());
		userd.setPhotoUrl(user.getPhotoUrl());
		userd.setIsPublicEducation(0);
		userd.setIsPublicJob(0);
		userd.setIsPublicSkill(0);
		
		int id = userDao.insertUser(userd);
		return Response.created(
				UriBuilder.fromResource(UserEndpoint.class)
				.path(String.valueOf(id)).build()).build();
	}
	
	
	@POST
	@Path("/login")
	@Consumes({"application/json"})
	@Produces({"text/plain"})
	public Response login(final LogInfoBean loginInfo) {
		UserDB userDao = new UserDB();
		entities.User userd = userDao.find(loginInfo.getEmai(), loginInfo.getPassword());
		if (userd != null) {
			String token = issueToken(loginInfo.getEmai());
			return Response.ok(token, "text/plain").build();
		}
		else {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
	
	
	//Testing login
	/*@POST
	@Path("/login")
	public Response login(
			@FormParam("email") String email,
			@FormParam("password") String password) {
		
		UserDB userDao = new UserDB();
		entities.User userd = userDao.find(email, password);
		
		if(userd == null)
			return Response.status(200).entity("User does not exist").build();
		else
			return Response.status(200).entity("Welcome back " + userd.getFirstName() + " " + userd.getLastName()).build();
		
	}*/
	
	private String issueToken(String username) {
		Key key = utilities.KeyHolder.key;
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		long expMillis = nowMillis + 300000L;
        Date exp = new Date(expMillis);
		String jws = Jwts.builder()
				  .setSubject(username)
				  .setIssuedAt(now)
				  .signWith(SignatureAlgorithm.HS512, key)
				  .setExpiration(exp)
				  .compact();
		return jws;
    }
	
	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces({"application/json"})
	public Response findById(@PathParam("id") final Integer id) {
		UserDB userDao = new UserDB();
		entities.User userd = userDao.getById(id);
		UserBean user = null;
		if (userd != null) {
			user = new UserBean();
			user.setIdUser(userd.getIdUser());
			user.setLastName(userd.getLastName());
			user.setFirstName(userd.getFirstName());
			user.setPassword(userd.getPassword());
			user.setEmail(userd.getEmail());
			user.setIsModerator(userd.getIsModerator());
			user.setPhoneNumber(userd.getPhoneNumber());
			user.setPhotoUrl(userd.getPhotoUrl());
			user.setEducationText(userd.getEducationText());
			user.setJobExperienceText(userd.getJobExperienceText());
			user.setIsPublicEducation(userd.getIsPublicEducation());
			user.setIsPublicJob(userd.getIsPublicJob());
			user.setIsPublicSkill(userd.getIsPublicSkill());
		}
		if (user == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(user).build();
	}
	
	/*@POST
	@Path("/update")
	@Consumes({"application/json"})
	public Response updateUser(UserBean user) {
		
		entities.User userd = new entities.User();
		
		userd.setIdUser(user.getIdUser());
		userd.setLastName(user.getLastName());
		userd.setFirstName(user.getFirstName());
		userd.setPassword(user.getPassword());
		userd.setEmail(user.getEmail());
		userd.setPhoneNumber(user.getPhoneNumber());
		userd.setPhotoUrl(user.getPhotoUrl());
		userd.setEducationText(user.getEducationText());
		userd.setJobExperienceText(user.getJobExperienceText());
		userd.setIsPublicEducation(user.getIsPublicEducation());
		userd.setIsPublicJob(user.getIsPublicJob());
		userd.setIsPublicSkill(user.getIsPublicSkill());
		UserDB userDao = new UserDB();
		userDao.updateUser(userd);
		
		return Response.ok().build();
		
	}*/
	
	@GET
	@Path("/skill")
	public Response getUserSkills(
			@QueryParam("id") int id) {
		UserDB userDao = new UserDB();
		entities.User userd = userDao.getById(id);
		
		int sz = userd.getSkills().size();
		return Response.status(200).entity("User: " + userd.getFirstName() + " has " + sz + " skills").build();

	}
	
	//Testing update
	@POST
	@Path("/update")
	public Response updateUser(
			@FormParam("id") int id,
			@FormParam("email") String email,
			@FormParam("password") String password,
			@FormParam("firstName") String firstName,
			@FormParam("lastName") String lastName,
			@FormParam("phoneNumber") String phoneNumber,
			@FormParam("educationText") String educationText,
			@FormParam("jobExperienceText") String jobExperienceText,
			@FormParam("photoUrl") String photoUrl,
			@FormParam("isPublicEducation") int isPublicEducation,
			@FormParam("isPublicJob") int isPublicJob,
			@FormParam("isPublicSkill") int isPublicSkill) {
		
		entities.User userd = new entities.User();
		
		userd.setIdUser(id);
		userd.setLastName(lastName);
		userd.setFirstName(firstName);
		userd.setPassword(password);
		userd.setEmail(email);
		userd.setPhoneNumber(phoneNumber);
		userd.setPhotoUrl(photoUrl);
		userd.setEducationText(educationText);
		userd.setJobExperienceText(jobExperienceText);
		userd.setIsPublicEducation(isPublicEducation);
		userd.setIsPublicJob(isPublicJob);
		userd.setIsPublicSkill(isPublicSkill);
		UserDB userDao = new UserDB();
		userDao.updateUser(userd);
		
		return Response.status(200).entity("Succesfully updated user: " + id).build();

	}
	
	//Testing insertion of one skill
	@POST
	@Path("/insertSkill")
	public Response insertSkill(
			@FormParam("userId") int userId,
			@FormParam("skillName") String skillName) {
		UserDB userDao = new UserDB();
		SkillDB skillDao = new SkillDB();
		entities.User userd = userDao.getById(userId);

		entities.Skill skilld = skillDao.find(skillName);

		if(skilld == null) {
			skilld = new entities.Skill();
			skilld.setSkillName(skillName);
			int skillId = skillDao.insertSkill(skilld);
		}
		

		userd.setSkills(Arrays.asList(skilld));
		userDao.mergeUser(userd);
		
		return Response.status(200).entity("Succesfully inserted skill: " + skillName + "on user: " + userd.getIdUser()).build();
		
	}
	
	//Will consume a SkillListBean of userId
	@POST
	@Path("/insertSkillUser")
	@Consumes({"application/json"})
	public Response insertSkillUser(final SkillListBean skillListBean) {
		UserDB userDao = new UserDB();
		SkillDB skillDao = new SkillDB();
		entities.User userd = userDao.getById(skillListBean.getUserId());
		
		List<String> skillList = skillListBean.getSkills();
		for (int i = 0; i < skillList.size(); i++) {
			entities.Skill skilld = skillDao.find(skillList.get(i));
			if(skilld == null) {
				skilld = new entities.Skill();
				skilld.setSkillName(skillList.get(i));
				int skillId = skillDao.insertSkill(skilld);
			}
			userd.setSkills(Arrays.asList(skilld));
			userDao.mergeUser(userd);
		}
		
		return Response.status(200).build();
	}
	
	// Testing XML export
	/*@GET
	@Path("/getXML")
    @Produces(MediaType.APPLICATION_XML)
	public Response getXML(
			@FormParam("id") int id) {
		UserDB userDao = new UserDB();
		XmlCreator creator = new XmlCreator();
		entities.User userd = userDao.getById(id);
		
		DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder icBuilder = null;
        try {
			icBuilder = icFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	Document doc = icBuilder.newDocument();
    	
    	Element rootElement = doc.createElement("Users");
    	doc.appendChild(rootElement);
    	
    	rootElement.appendChild(creator.getUser(userd, doc));
        
    	 StreamingOutput entity =  new StreamingOutput() {
    	    @Override
    	    public void write(OutputStream out)
    	            throws IOException, WebApplicationException {
    	        try {
    	            Transformer transformer = TransformerFactory.newInstance().newTransformer();
    	            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    	            StreamResult result = new StreamResult(out);
    	            DOMSource source = new DOMSource(doc);
    	            transformer.transform(source, result);
    	            out.flush();
    	        } catch (Exception e) {
    	            e.printStackTrace();
    	        }
    	    }
    	};
    	return Response.ok(entity)
                .header(HttpHeaders.CONTENT_DISPOSITION, 
                        "attachment;filename=Users.xml")
                .build();
	}*/
	
	@GET
	@Path("/getXML")
	@Consumes({"application/json"})
    @Produces(MediaType.APPLICATION_XML)
	public Response getXML(final ExportXMLBean xmlBean) {
		UserDB userDao = new UserDB();
		XmlCreator creator = new XmlCreator();
		DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder icBuilder = null;
        try {
			icBuilder = icFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	Document doc = icBuilder.newDocument();
    	
    	Element rootElement = doc.createElement("Users");
    	doc.appendChild(rootElement);
    	
    	List <Integer> users = xmlBean.getUserIds();
    	for(int i = 0; i < users.size(); i++) {
        	rootElement.appendChild(creator.getUser(userDao.getById(users.get(i)), doc));
    	}
    	
    	StreamingOutput entity =  new StreamingOutput() {
    	    @Override
    	    public void write(OutputStream out)
    	            throws IOException, WebApplicationException {
    	        try {
    	            Transformer transformer = TransformerFactory.newInstance().newTransformer();
    	            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    	            StreamResult result = new StreamResult(out);
    	            DOMSource source = new DOMSource(doc);
    	            transformer.transform(source, result);
    	            out.flush();
    	        } catch (Exception e) {
    	            e.printStackTrace();
    	        }
    	    }
    	};
    	return Response.ok(entity)
                .header(HttpHeaders.CONTENT_DISPOSITION, 
                        "attachment;filename=Users.xml")
                .build();
	}
	
}
