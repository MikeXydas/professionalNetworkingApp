package service;

import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import javax.ws.rs.OPTIONS;

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

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import annotations.Secured;

import javax.ws.rs.core.UriBuilder;

import db.UserDB;
import entities.Application;
import db.AdvertismentDB;
import db.ApplicationDB;
import db.SkillDB;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import model.UserBean;
import model.AdvertismentPostBean;
import model.ApplicationBean;
import model.ApplyBean;
import model.ArticleBean;
import model.ChangeEmailBean;
import model.ChangePasswordBean;
import model.ExportXMLBean;
import model.LogInfoBean;
import model.SkillListBean;
import model.SearchBean;
import model.SkillBean;
import model.RegisterFormBean;
import model.LoginReturned;
import utilities.XmlCreator;
import utilities.FileManipulation;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;


@Path("/User")
public class UserEndpoint {
	
	private String FILE_SYSTEM = "/home/mike/Desktop/linkedInFileSystem";
			
	
	@GET
	@Path("query")
	public Response getUser(@Context UriInfo info) {
		int id = Integer.parseInt(info.getQueryParameters().getFirst("id"));
		UserDB userDao = new UserDB();
		entities.User userd = userDao.getById(id);
		return Response.status(200).entity("First name is: " + userd.getFirstName()).build();
	}
	
	@POST
	@Path("/add")
	@Consumes({ "application/json" })
	@Produces({"application/json"})
	public Response addUser(final RegisterFormBean user) {
		UserDB userDao = new UserDB();

		entities.User userd;
		//Checking if user already exists
		userd = userDao.findEmail(user.getEmail());
		String userExists = "0";
		if(userd != null)
			return Response.ok("{ \"id\": 0}", "application/json").build();
		
		userd = new entities.User();
		userd.setEmail(user.getEmail());
		userd.setPassword(user.getPassword());
		userd.setIsModerator(0);
		userd.setFirstName(user.getFirstName());
		userd.setLastName(user.getLastName());
		userd.setPhoneNumber(user.getPhoneNumber());
		
		userd.setIsPublicEducation(0);
		userd.setIsPublicJob(0);
		userd.setIsPublicSkill(0);
		
		
		int id = userDao.insertUser(userd);
		String retId = Integer.toString(id);
		
		userDao.mergeUser(userd);

		return Response.ok("{ \"id\": " + retId + "}", "application/json").build();

	}
	
	
	@POST
	@Path("/login")
	@Consumes({"application/json"})
	@Produces({"application/json"})
	public Response login(final LogInfoBean loginInfo) {
		System.out.println("login");
		UserDB userDao = new UserDB();
		entities.User userd = userDao.find(loginInfo.getEmail(), loginInfo.getPassword());
		if (userd != null) {
			String token = issueToken(Integer.toString(userd.getIdUser()));
			LoginReturned ret = new LoginReturned();
			ret.setToken(token);
			ret.setId(userd.getIdUser());
			ret.setIsModerator(userd.getIsModerator());
			return Response.ok(ret).build();
		}
		else {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}
	
	
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
	
	/*@OPTIONS
	@Path("/{id:[0-9][0-9]*}")
	public Response findById() {
		return Response.status(200).build();
	}*/
	
	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces({"application/json"})
	public Response findById(@PathParam("id") final Integer id) throws IOException {
		UserDB userDao = new UserDB();
		entities.User userd = userDao.getById(id);
		//FileManipulation photoManip = new FileManipulation();
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
	
	
	
	@GET
	@Produces({"application/json"})
	@Path("/skill/{id:[0-9][0-9]*}")
	public Response getUserSkills(@PathParam("id") final Integer id) {
		UserDB userDao = new UserDB();
		entities.User userd = userDao.getById(id);
		List <SkillBean> skillBeans = new ArrayList<SkillBean>();
		
		for(int i = 0; i < userd.getSkills().size(); i++) {
			SkillBean temp = new SkillBean();
			temp.setSkillName(userd.getSkills().get(i).getSkillName());
			temp.setIdSkill(userd.getSkills().get(i).getIdSkill());
			skillBeans.add(temp);

		}
		return Response.status(200).entity(skillBeans).build();

	}
	
	
	//TODO: I do not have to create new userd
	@POST
	@Path("/update")
	@Consumes({"application/json"})
	public Response updateUser(UserBean user) {
		UserDB userDao = new UserDB();
		entities.User temp;
		if((temp = userDao.findEmail(user.getEmail())) != null) {
			if(temp.getIdUser() != user.getIdUser()) {
				return Response.status(Response.Status.FORBIDDEN).build();
			}
		}
		entities.User userd = new entities.User();
		entities.User oldUserd = userDao.getById(user.getIdUser());
		FileManipulation photoManip = new FileManipulation();

		
		userd.setIdUser(user.getIdUser());
		userd.setLastName(user.getLastName());
		userd.setFirstName(user.getFirstName());
		userd.setPassword(user.getPassword());
		userd.setEmail(user.getEmail());
		userd.setPhoneNumber(user.getPhoneNumber());
		userd.setIsModerator(0);
		userd.setAdvertisments(oldUserd.getAdvertisments());
		userd.setArticles(oldUserd.getArticles());
		userd.setConnections(oldUserd.getConnections());
		userd.setConnectionRequests(oldUserd.getConnectionRequests());
		userd.setConversations(oldUserd.getConversations());
		userd.setSkills(oldUserd.getSkills());
		
		userd.setEducationText(user.getEducationText());
		userd.setJobExperienceText(user.getJobExperienceText());
		userd.setIsPublicEducation(user.getIsPublicEducation());
		userd.setIsPublicJob(user.getIsPublicJob());
		userd.setIsPublicSkill(user.getIsPublicSkill());
		//userDao.updateUser(userd);
		userDao.mergeUser(userd);
		
		return Response.ok().build();
		
	}
	
	
	//Will consume a SkillListBean of userId
	@POST
	@Path("/insertSkill")
	@Consumes({"application/json"})
	public Response insertSkill(final SkillListBean skillListBean) {
		UserDB userDao = new UserDB();
		SkillDB skillDao = new SkillDB();
		entities.User userd = userDao.getById(skillListBean.getUserId());
		
		List<String> skillList = skillListBean.getSkills();
		List<entities.Skill> skillsd = new ArrayList<entities.Skill>();
		for (int i = 0; i < skillList.size(); i++) {
			entities.Skill skilld = skillDao.find(skillList.get(i));
			if(skilld == null) {
				skilld = new entities.Skill();
				skilld.setSkillName(skillList.get(i));
				skillDao.insertSkill(skilld);
			}
			skillsd.add(skilld);

		}
		userd.setSkills(skillsd);
		userDao.mergeUser(userd);
		return Response.status(200).build();
	}
	
	
	@POST
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
	
	@GET
	@Path("/users")
    @Produces({ "application/json" })
	public Response returnUsers() {
		UserDB userDao = new UserDB();
		List<entities.User> users = null;
		users = userDao.getUsers();
		
		List <UserBean> userBeans = new ArrayList<UserBean>();
		
		for(int i = 0; i < users.size(); i++) {
			userBeans.add(createUserBeanFromEntity(users.get(i)));
		}
		
		return Response.ok(userBeans).build();
	}
	
	@POST
	@Consumes({"application/json"})
	@Produces({"application/json"})
	@Path("/search")
	public Response search(SearchBean searchBean) {
		
		UserDB userDao = new UserDB();
		List <entities.User> list_userd = userDao.findName(searchBean.getFirstName(), searchBean.getLastName());
		List <UserBean> userBeans = new ArrayList<UserBean>();
		if(list_userd != null) {
			for(int i=0;i<list_userd.size();i++) {
				userBeans.add(createUserBeanFromEntity(list_userd.get(i)));
			}
		}
		return Response.ok(userBeans).build();
	}
	
	public UserBean createUserBeanFromEntity(entities.User userd) {
		
		UserBean user = new UserBean();
		if (userd != null) {
			user.setIdUser(userd.getIdUser());
			user.setLastName(userd.getLastName());
			user.setFirstName(userd.getFirstName());
			user.setPassword(userd.getPassword());
			user.setEmail(userd.getEmail());
			user.setIsModerator(userd.getIsModerator());
			user.setPhoneNumber(userd.getPhoneNumber());
			user.setEducationText(userd.getEducationText());
			user.setJobExperienceText(userd.getJobExperienceText());
			user.setIsPublicEducation(userd.getIsPublicEducation());
			user.setIsPublicJob(userd.getIsPublicJob());
			user.setIsPublicSkill(userd.getIsPublicSkill());
		}
		else {
			user.setIdUser(-1);
		}
		
		return user;
	}
	

	@POST
	@Consumes({"application/json"})
	@Path("/changePassword")
	public Response changePassword(ChangePasswordBean changePasswordBean) {
		UserDB userDao = new UserDB();
		entities.User userd = userDao.getById(changePasswordBean.getUserId());
		userd.setPassword(changePasswordBean.getPassword());
		userDao.mergeUser(userd);

		return Response.status(200).build();
	}
	
	@POST
	@Consumes({"application/json"})
	@Path("/changeEmail")
	public Response changeEmail(ChangeEmailBean changeEmailBean) {
		UserDB userDao = new UserDB();
		entities.User userd = userDao.getById(changeEmailBean.getUserId());
		userd.setEmail(changeEmailBean.getEmail());
		userDao.mergeUser(userd);

		return Response.status(200).build();
	}
	
	@GET
	@Path("/ads/{id:[0-9]*}")
	@Produces({"application/json"})
	public Response getAdSkills(@PathParam("id") final Integer id) {
		UserDB userDao = new UserDB();
		AdvertismentDB advertismentDao = new AdvertismentDB();
		List <AdvertismentPostBean> retAds = new ArrayList<AdvertismentPostBean>();

		entities.User userd = userDao.getById(id);
		
		List <entities.Advertisment> advertisments = advertismentDao.getAdvertisments();
		
		if(advertisments != null) {			
			for(int whichAd = 0; whichAd < advertisments.size(); whichAd++) {
				if(advertisments.get(whichAd).getId().getUser_idUser() != id) {
					AdvertismentPostBean tempBean = new AdvertismentPostBean();
					tempBean.setAdId(advertisments.get(whichAd).getId().getIdAdvertisment());
					tempBean.setUserId(advertisments.get(whichAd).getId().getUser_idUser());
					tempBean.setTitle(advertisments.get(whichAd).getTitle());
					tempBean.setDescriptionText(advertisments.get(whichAd).getDescriptionText());
					tempBean.setUploadTime(advertisments.get(whichAd).getUploadTime());
					tempBean.setScore(0);
					tempBean.setSkills(new ArrayList<String>());
					
					for(int whichSkill = 0; whichSkill < advertisments.get(whichAd).getSkills().size(); whichSkill++) {
						tempBean.getSkills().add(advertisments.get(whichAd).getSkills().get(whichSkill).getSkillName());
					}
					
					retAds.add(tempBean);
				}
			}
			
			for(int whichAd = 0; whichAd < retAds.size(); whichAd++) {
				for(int whichAdSkill = 0; whichAdSkill < retAds.get(whichAd).getSkills().size(); whichAdSkill++) {
					for(int whichUserSkill = 0; whichUserSkill < userd.getSkills().size(); whichUserSkill++) {
						if(retAds.get(whichAd).getSkills().get(whichAdSkill).equals( userd.getSkills().get(whichUserSkill).getSkillName())) {
							retAds.get(whichAd).setScore(retAds.get(whichAd).getScore() + 1);
						}
					}
				}
				
			}

		}
		
		return Response.status(200).entity(retAds).build();

	}
	
	@POST
	@Path("/post")
	@Consumes({"application/json"})
	public Response postAd(final AdvertismentPostBean adBean) {
		AdvertismentDB advertismentDao = new AdvertismentDB();
		UserDB userDao = new UserDB();
		SkillDB skillDao = new SkillDB();

		entities.User userd = userDao.getById(adBean.getUserId());
		entities.Advertisment ad = new entities.Advertisment();
		
		//Initializing the new add entity
		ad.setTitle(adBean.getTitle());
		ad.setDescriptionText(adBean.getDescriptionText());
		Date date = new Date();
		ad.setUploadTime(date);
		ad.setUser(userd);
		
		
		//Primary key initialization
		entities.AdvertismentPK pk = new entities.AdvertismentPK();
		ad.setId(pk);
		advertismentDao.insertAdvertisment(ad);
		
		//Update correctly the AdHasSkill table
		List<String> skillList = adBean.getSkills();
		List<entities.Skill> skillsd = new ArrayList<entities.Skill>();

		for (int i = 0; i < skillList.size(); i++) {
			entities.Skill skilld = skillDao.find(skillList.get(i));
			if(skilld == null) {
				skilld = new entities.Skill();
				skilld.setSkillName(skillList.get(i));
				skilld.setAdvertisments(new ArrayList<entities.Advertisment>());
				skilld.getAdvertisments().add(ad);
				int skillId = skillDao.insertSkill(skilld);
			}
			else {
				skilld.getAdvertisments().add(ad);
				skillDao.mergeSkill(skilld);
			}
		}
		
		return Response.status(200).build();
	}
	
	
	@POST
	@Path("/apply")
	@Consumes({"application/json"})
	public Response applyAd(final ApplyBean applyBean) {
		AdvertismentDB advertismentDao = new AdvertismentDB();
		ApplicationDB applicationDao = new ApplicationDB();
		
		entities.Advertisment add = advertismentDao.getByAdId(applyBean.getAdId());

		List<Application> applications = add.getApplications();

		for (int i = 0; i < applications.size(); i++) {
			entities.Application currentApp = applications.get(i);
			if(currentApp.getApplicantId() == applyBean.getUserId()) {
				//CASE: The application already exists
				return Response.status(200).build();
			}
		}
		
		entities.Application appd = new entities.Application();
		appd.setAdvertisment(add);
		appd.setApplicantId(applyBean.getUserId());
		entities.ApplicationPK appPk = new entities.ApplicationPK();
		appd.setId(appPk);
		applicationDao.insertApplication(appd);
		
		return Response.status(200).build();
	}
	
	@GET
	@Path("/applicants/{id:[0-9]*}")
	@Produces({"application/json"})
	public Response getApplicants(@PathParam("id") int id) {
		UserDB userDao = new UserDB();
		ApplicationDB applicationDao = new ApplicationDB();
		
		List<Application> appsd = applicationDao.getApplicationsOfUser(id);
		List<ApplicationBean> retApps = new ArrayList<ApplicationBean>();
		
		if(appsd != null) {
			for(int whichApp = 0; whichApp < appsd.size(); whichApp++) {
				ApplicationBean temp = new ApplicationBean();
				entities.User userd = userDao.getById(appsd.get(whichApp).getApplicantId());
				
				temp.setApplicantId(userd.getIdUser());
				temp.setFirstName(userd.getFirstName());
				temp.setLastName(userd.getLastName());
				temp.setTitle(appsd.get(whichApp).getAdvertisment().getTitle());
				
				retApps.add(temp);
			}
		}
		
		return Response.status(200).entity(retApps).build();

	}
}
