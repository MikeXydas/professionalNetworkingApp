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
import entities.Article;
import db.SkillDB;
import db.ArticleDB;
import db.InterestDB;
import db.CommentDB;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import model.UserBean;
import model.ChangeEmailBean;
import model.ChangePasswordBean;
import model.CommentBean;
import model.ExportXMLBean;
import model.InterestBean;
import model.LogInfoBean;
import model.NotificationBean;
import model.PendingRequestBean;
import model.SkillListBean;
import model.SearchBean;
import model.ArticleBean;
import model.ShowInterestBean;
import model.PostCommentBean;
import utilities.XmlCreator;
import utilities.FileManipulation;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path("/Article")
public class ArticleEndpoint {
	private String FILE_SYSTEM = "/home/mike/Desktop/linkedInFileSystem";

	/*@POST
	@Path("/post")
	public Response uploadArticle(
			@FormParam("id") int id,
			@FormParam("title") String title,
			@FormParam("context") String context) {
		
		ArticleDB articleDao = new ArticleDB();
		UserDB userDao = new UserDB();
		entities.User userd = userDao.getById(id);
		entities.Article articled = new entities.Article();
		entities.ArticlePK articledPK = new entities.ArticlePK();
		
		//articledPK.setUser_idUser(id);
		articled.setId(articledPK);
		
		articled.setUser(userd);
		articled.setTitle(title);
		articled.setContentText(context);
		Date date = new Date();
		articled.setUploadTime(date);
		articleDao.insertArticle(articled);
		
		return Response.status(200).entity("Succesfully posted article").build();

	}*/
	
	@POST
	@Path("/post")
	@Consumes({"application/json"})
	@Produces({"application/json"})
	public Response postArticle(final ArticleBean articleBean) throws IOException {
		
		ArticleDB articleDao = new ArticleDB();
		UserDB userDao = new UserDB();
		//FileManipulation fileManip = new FileManipulation();

		entities.User userd = userDao.getById(articleBean.getUserId());
		entities.Article articled = new entities.Article();
		entities.ArticlePK articledPK = new entities.ArticlePK();
		
		//articledPK.setUser_idUser(articleBean.getUserId());
		articled.setId(articledPK);
		articled.setUser(userd);
		
		articled.setTitle(articleBean.getTitle());
		articled.setContentText(articleBean.getContentText());
		Date date = new Date();
		articled.setUploadTime(date);
		entities.ArticlePK insertedPK = articleDao.insertArticle(articled);
		
		/*if(articleBean.getPhotoBytes() != null) {
			String fileName = "articleImage" + insertedPK.getIdArticle();
			String imagePath = FILE_SYSTEM + "/Article/image/" + fileName;
			
			articled.setPhotoUrl(fileManip.ReceiveFile(imagePath, articleBean.getPhotoBytes()));
		}
		
		if(articleBean.getSoundBytes() != null) {
			String fileName = "articleSound" + insertedPK.getIdArticle();
			String soundPath = FILE_SYSTEM + "/Article/sound/" + fileName;
			
			articled.setSoundUrl(fileManip.ReceiveFile(soundPath, articleBean.getSoundBytes()));
		}
		
		if(articleBean.getVideoBytes() != null) {
			String fileName = "articleVideo" + insertedPK.getIdArticle();
			String videoPath = FILE_SYSTEM + "/Article/video/" + fileName;
			
			articled.setPhotoUrl(fileManip.ReceiveFile(videoPath, articleBean.getVideoBytes()));
		}*/
		
		
		//entities.Article tempArticled = articleDao.getById(insertedPK);
		//articled.getId().setUser_idUser(articleBean.getUserId());
		return Response.status(200).entity(createArticleBean(articled)).build();
	}
	
	/*@POST
	@Path("/interest")
	public Response uploadArticle(
			@FormParam("articleId") int articleId,
			@FormParam("interesterId") int interesterId) {
		ArticleDB articleDao = new ArticleDB();
		//UserDB userDao = new UserDB();
		InterestDB interestDao = new InterestDB();
		
		
		entities.Article articled = articleDao.getByArticleId(articleId);
		//entities.User userd = userDao.getById(articled.getUser().getIdUser());
		entities.Interest interestd = new entities.Interest();
		entities.InterestPK interestPK = new entities.InterestPK();
		interestd.setId(interestPK);
		
		interestd.setArticle(articled);
		Date date = new Date();
		interestd.setInterestTime(date);
		interestd.setInteresterId(interesterId);
		
		interestDao.insertInterest(interestd);
		
		return Response.status(200).entity("Succesfully showed interest on article: " + articled.getTitle()).build();
	}*/
	
	@POST
	@Path("/showInterest")
	@Consumes({"application/json"})
	@Produces({"application/json"})
	public Response showInterest(final ShowInterestBean intBean) {
		ArticleDB articleDao = new ArticleDB();
		InterestDB interestDao = new InterestDB();
		entities.Article articled = articleDao.getByArticleId(intBean.getArticleId());
		if(articled.getInterests() != null) {
			for(int i = 0; i < articled.getInterests().size(); i++) {
				if(articled.getInterests().get(i).getInteresterId() == intBean.getInteresterId()) {
					return Response.status(200).build();
				}
			}
		}
		entities.Interest interestd = new entities.Interest();
		entities.InterestPK interestPK = new entities.InterestPK();
		interestd.setId(interestPK);
		
		interestd.setArticle(articled);
		Date date = new Date();
		interestd.setInterestTime(date);
		interestd.setInteresterId(intBean.getInteresterId());
		
		interestDao.insertInterest(interestd);
		
		InterestBean temp = new InterestBean();
		temp.setArticleId(articled.getId().getIdArticle());
		temp.setInteresterId(interestd.getId().getIdInterest());
		temp.setInteresterId(interestd.getInteresterId());
		temp.setInterestTime(interestd.getInterestTime());
		
		return Response.status(200).entity(temp).build();
	}
	
	@POST
	@Path("/postComment")
	@Consumes({"application/json"})
	public Response showInterest(final PostCommentBean commBean) {
		ArticleDB articleDao = new ArticleDB();
		CommentDB commentDao = new CommentDB();
		UserDB userDao = new UserDB();
		entities.Article articled = articleDao.getByArticleId(commBean.getArticleId());
		
		/*for(int i = 0; i < articled.getComments().size(); i++) {
			if(articled.getInterests().get(i).getInteresterId() == commBean.getCommenterId()) {
				return Response.status(200).build();
			}
		}*/
		entities.Comment commentd = new entities.Comment();
		entities.CommentPK commentPK = new entities.CommentPK();
		commentd.setId(commentPK);
		
		commentd.setArticle(articled);
		commentd.setCommenterId(commBean.getCommenterId());
		Date date = new Date();
		commentd.setUploadTime(date);
		commentd.setContentText(commBean.getText());
		
		commentDao.insertComment(commentd);
		
		CommentBean temp = new CommentBean();
		temp.setArticleId(articled.getId().getIdArticle());
		temp.setCommentId(commentd.getId().getIdComment());
		temp.setCommenterId(commentd.getCommenterId());
		temp.setContentText(commentd.getContentText());
		temp.setUploadTime(commentd.getUploadTime());
		
		entities.User tempCommUser = userDao.getById(commentd.getCommenterId());
		temp.setFirstName(tempCommUser.getFirstName());
		temp.setLastName(tempCommUser.getLastName());
		
		return Response.status(200).entity(temp).build();
	}
	
	/*@GET
	@Path("/showArticles/{id:[0-9]*}")
	@Produces({"application/json"})
	public Response returnHomepageArticles(@PathParam("id") int id) throws IOException {
		ArticleDB articleDao = new ArticleDB();
		List<Article> articles = null;
		articles = articleDao.getConnectedArticles(id);
		
		List<ArticleBean> articleBeans = new ArrayList<ArticleBean>();
		
		for(int i = 0; i < articles.size(); i++) {
			articleBeans.add(createArticleBean(articles.get(i)));
		}
		
		return Response.status(200).entity(articleBeans).build();
	}*/
	
	@GET
	@Path("/showArticles/{id:[0-9]*}")
	@Produces({"application/json"})
	public Response returnHomepageArticles(@PathParam("id") int id) throws IOException {
		ArticleDB articleDao = new ArticleDB();
		List<Article> articles = null;
		articles = articleDao.getConnectedArticles(id);
		
		List<Article> knnArticles = KNNarticles(articles, id, 3);
		
		List<ArticleBean> articleBeans = new ArrayList<ArticleBean>();
		
		for(int i = 0; i < knnArticles.size(); i++) {
			articleBeans.add(createArticleBean(knnArticles.get(i)));
		}
		
		return Response.status(200).entity(articleBeans).build();
	}
	
	private List<Article> KNNarticles(List<Article> articles, int loggedInUser, int k) {
		
		System.out.println("  ---------------------------------------------------- ");
		UserDB userDao = new UserDB();
		if(articles == null) {
			return null;
		}
		
		List<DistanceUser> usersDists = createUserIdsList(articles, loggedInUser);
		
		
		List<List<Integer>> matrix = createMatrix(usersDists, articles);
		
		
		List<Integer> mainVector = new ArrayList<Integer>();
		for(int whichArticle = 0; whichArticle < articles.size(); whichArticle++) {
			mainVector.add(userLikesCommentsArticle(loggedInUser, articles.get(whichArticle)));
		}
		
		System.out.println(mainVector);

		System.out.println("UsersDists size = " + usersDists.size());
		for(int whichUser = 0; whichUser < usersDists.size(); whichUser++) {
			usersDists.get(whichUser).setDistance(calculateDistance(mainVector, matrix.get(whichUser)));
		}
		
		
		heapSortDistances(usersDists);
		
		for(int i = 0; i < usersDists.size(); i++) {
			System.out.println("User: " + usersDists.get(i).getUserId() + " |  Distance: " + usersDists.get(i).getDistance());
		}
		List<Article> topArticles = new ArrayList<Article>();
		if(usersDists.size() < k) {
			k = usersDists.size();
		}
		
		for(int whichUser = 0; whichUser < k; whichUser++) {
			entities.User topUserd = userDao.getById(usersDists.get(whichUser).getUserId());
			for(int whichArticle = 0; whichArticle < topUserd.getArticles().size(); whichArticle++) {
				topArticles.add(topUserd.getArticles().get(whichArticle));
			}
		}
		
		sortArticlesOnTime(topArticles);
		
		List<Article> restArticles = new ArrayList<Article>();
		for(int whichUser = k; whichUser < usersDists.size(); whichUser++) {
			System.out.println("ADDING");
			entities.User restUserd = userDao.getById(usersDists.get(whichUser).getUserId());
			for(int whichArticle = 0; whichArticle < restUserd.getArticles().size(); whichArticle++) {
				System.out.println("ADDING2");
				restArticles.add(restUserd.getArticles().get(whichArticle));
			}
		}
		
		entities.User loggedUserd = userDao.getById(loggedInUser);
		if(loggedUserd.getArticles() != null) {
			for(int i = 0; i < loggedUserd.getArticles().size(); i++) {
				restArticles.add(loggedUserd.getArticles().get(i));
			}
		}
		sortArticlesOnTime(restArticles);
		
		List<Article> finalArticles = new ArrayList<Article>();
		finalArticles.addAll(topArticles);
		finalArticles.addAll(restArticles);

		return finalArticles;
	}
	
	
	
	private List<List<Integer>> createMatrix(List<DistanceUser> users, List<Article> articles) {
		List<List<Integer>> retList = new ArrayList<List<Integer>>();
		
		for(int whichUser = 0; whichUser < users.size(); whichUser++) {
			List<Integer> vector = new ArrayList<Integer>();
			for(int whichArticle = 0; whichArticle < articles.size(); whichArticle++) {
				vector.add(userLikesCommentsArticle(users.get(whichUser).getUserId(), articles.get(whichArticle)));
			}
			System.out.println("Vector of " +users.get(whichUser).getUserId() + ": " + vector );
			retList.add(vector);
		}
		
		return retList;
	}
	private int userLikesCommentsArticle(int userId, Article articled) {
		
		if(articled.getId().getUser_idUser() == userId) {
			return 1;
		}
		if(articled.getComments() != null) {
			for(int i = 0; i < articled.getComments().size(); i++) {
				if(articled.getComments().get(i).getCommenterId() == userId) {
					return 1;
				}
			}
		}
		
		if(articled.getInterests() != null) {
			for(int i = 0; i < articled.getInterests().size(); i++) {
				if(articled.getInterests().get(i).getInteresterId() == userId) {
					return 1;
				}
			}
		}
		
		return 0;
	}
	
	private List<DistanceUser> createUserIdsList(List<Article> articles, int loggedUser) {
		
		List<DistanceUser> retList = new ArrayList<DistanceUser>();
		int userExists = 0;
		if(articles != null) {
			for(int whichArticle = 0; whichArticle < articles.size(); whichArticle++) {
				if(articles.get(whichArticle).getId().getUser_idUser() != loggedUser) {
					for(int whichUser = 0; whichUser < retList.size(); whichUser++) {
						if(articles.get(whichArticle).getId().getUser_idUser() == retList.get(whichUser).getUserId()) {
							userExists = 1;
							break;
						}
					}
					if(userExists == 0)
					{
						DistanceUser temp = new DistanceUser();
						temp.setUserId(articles.get(whichArticle).getId().getUser_idUser());
						temp.setDistance(0);
						retList.add(temp);
					}
					else {
						userExists = 0;
					}
				}
			}
		}
		
		return retList;
	}
	
	private class DistanceUser {
		
		private int userId;
		private double distance;
		
		public int getUserId() {
			return userId;
		}
		public void setUserId(int userId) {
			this.userId = userId;
		}
		public double getDistance() {
			return distance;
		}
		public void setDistance(double distance) {
			this.distance = distance;
		}
		
	}
	
	private void sortArticlesOnTime(List<Article> articles) {
		int n = articles.size();
		
		for(int i = n / 2 - 1; i >= 0; i--) {
			HeapifyOnTime(articles, n, i);
		}
		
		for(int i = n - 1; i >= 0; i--) {
			Collections.swap(articles, 0, i);
			HeapifyOnTime(articles, i, 0);
		}
	}
	
	private void HeapifyOnTime(List<Article> articles, int n, int i) {
		int largest = i; // Initialize largest as root
        int l = 2*i + 1; // left = 2*i + 1
        int r = 2*i + 2;
        
        if((l < n) && (articles.get(l).getUploadTime().compareTo(articles.get(largest).getUploadTime()) < 0)) {
        	largest = l;
        }
        
        if((r < n) && (articles.get(r).getUploadTime().compareTo(articles.get(largest).getUploadTime()) < 0)) {
        	largest = r;
        }
        
        if(largest != i) {
        	Collections.swap(articles, i, largest);
        	HeapifyOnTime(articles, n, largest);
        }
	}
	private void heapSortDistances(List<DistanceUser> usersDists) {
		int n = usersDists.size();
		
		for(int i = n / 2 - 1; i >= 0; i--) {
			heapify(usersDists, n, i);
		}
		
		for(int i = n - 1; i >= 0; i--) {
			Collections.swap(usersDists, 0, i);
			heapify(usersDists, i, 0);
		}
	}
	
	private void heapify(List<DistanceUser> usersDists, int n, int i) {
		int largest = i; // Initialize largest as root
        int l = 2*i + 1; // left = 2*i + 1
        int r = 2*i + 2;
        
        if((l < n) && (usersDists.get(l).getDistance() > usersDists.get(largest).getDistance())) {
        	largest = l;
        }
        
        if((r < n) && (usersDists.get(r).getDistance() > usersDists.get(largest).getDistance())) {
        	largest = r;
        }
        
        if(largest != i) {
        	Collections.swap(usersDists, i, largest);
        	heapify(usersDists, n, largest);
        }
	}
	
	private double calculateDistance(List<Integer> mainVector, List<Integer> vector) {
		double Sum = 0.0;
        for(int i=0;i<mainVector.size();i++) {
           Sum = Sum + Math.pow((mainVector.get(i)-vector.get(i)),2.0);
        }
        return Math.sqrt(Sum);
	}
	/*@POST
	@Path("/showArticles")
	public Response showHomepageArticles(
			@FormParam("id") int id) {
		ArticleDB articleDao = new ArticleDB();
		//entities.Article articled = articleDao.getByArticleId(id);
		List<Article> articles = null;
		articles = articleDao.getConnectedArticles(id);
		
		
		return Response.status(200).entity("Numb of articles found: " + articles.size()).build();
	}*/
	
	//Creates an articleBean from an article entity
	private ArticleBean createArticleBean(entities.Article articled) throws IOException {
		ArticleBean artBean = new ArticleBean();
		UserBean user = new UserBean();
		UserDB userDao = new UserDB();
		FileManipulation fileManip= new FileManipulation();
		List<CommentBean> comments = new ArrayList<CommentBean>();
		List<InterestBean> interests = new ArrayList<InterestBean>();
		
		//Setting user bean
		//artBean.setIdArticle(articled.getId().getIdArticle());
		user.setFirstName(articled.getUser().getFirstName());
		user.setLastName(articled.getUser().getLastName());
		user.setIdUser(articled.getUser().getIdUser());
		
		artBean.setUser(user);
		
		//Setting comments list
		List<entities.Comment> commentsd = articled.getComments();
		for(int i = 0; i < commentsd.size(); i++) {
			CommentBean temp = new CommentBean();
			temp.setArticleId(articled.getId().getIdArticle());
			temp.setCommentId(articled.getComments().get(i).getId().getIdComment());
			temp.setCommenterId(articled.getComments().get(i).getCommenterId());
			temp.setContentText(articled.getComments().get(i).getContentText());
			temp.setUploadTime(articled.getComments().get(i).getUploadTime());
			
			entities.User tempCommUser = userDao.getById(articled.getComments().get(i).getCommenterId());
			temp.setFirstName(tempCommUser.getFirstName());
			temp.setLastName(tempCommUser.getLastName());
			
			comments.add(temp);
		}
		artBean.setComments(comments);
		
		//Setting interests list
		List<entities.Interest> interestsd = articled.getInterests();
		for(int i = 0; i < interestsd.size(); i++) {
			InterestBean temp = new InterestBean();
			temp.setArticleId(articled.getId().getIdArticle());
			temp.setInteresterId(articled.getInterests().get(i).getId().getIdInterest());
			temp.setInteresterId(articled.getInterests().get(i).getInteresterId());
			temp.setInterestTime(articled.getInterests().get(i).getInterestTime());
			
			interests.add(temp);
		}
		artBean.setInterests(interests);
		
		//Setting article contents
		artBean.setIdArticle(articled.getId().getIdArticle());
		artBean.setTitle(articled.getTitle());
		artBean.setContentText(articled.getContentText());
		artBean.setUploadTime(articled.getUploadTime());
		/*if(articled.getContentText() != null) {
			artBean.setContentText(articled.getContentText());
		}
		
		if(articled.getPhotoUrl() != null) {
			artBean.setPhotoString64(fileManip.SendFile(articled.getPhotoUrl()));
		}
		
		if(articled.getSoundUrl() != null) {
			artBean.setSoundString64(fileManip.SendFile(articled.getSoundUrl()));
		}
		
		if(articled.getVideoUrl() != null) {
			artBean.setVideoString64(fileManip.SendFile(articled.getVideoUrl()));
		}*/
		
		return artBean;
	}
	
	@GET
	@Path("/notifications/{id:[0-9]*}")
	@Produces({"application/json"})
	public Response returnNotifications(@PathParam("id") final Integer id) {
		UserDB userDao = new UserDB();
		
		entities.User mainUserd = userDao.getById(id);
		
		List <entities.Interest> interestsd = new ArrayList<entities.Interest>();
		List <entities.Comment> commentsd = new ArrayList<entities.Comment>();
				
		if(mainUserd.getArticles() != null) {
			for(int whichArticle = 0; whichArticle < mainUserd.getArticles().size(); whichArticle++) {
				if(mainUserd.getArticles().get(whichArticle).getInterests() != null) {
					for(int whichInt = 0; whichInt < mainUserd.getArticles().get(whichArticle).getInterests().size(); whichInt++) {
						interestsd.add(mainUserd.getArticles().get(whichArticle).getInterests().get(whichInt));
					}
				}
				
				if(mainUserd.getArticles().get(whichArticle).getComments() != null) {
					for(int whichInt = 0; whichInt < mainUserd.getArticles().get(whichArticle).getComments().size(); whichInt++) {
						commentsd.add(mainUserd.getArticles().get(whichArticle).getComments().get(whichInt));
					}
				}
			}
		}
		
		
		
		List<NotificationBean> notificationBeans = new ArrayList<NotificationBean>();
		
		if(interestsd != null) {
			for(int whichInt = 0; whichInt < interestsd.size(); whichInt++) {
				NotificationBean temp = new NotificationBean();
				entities.User userd = userDao.getById(interestsd.get(whichInt).getInteresterId());
				//entities.Article articled = articleDao.getByArticleId(interestsd.get(whichInt).getArticle().get))
				temp.setArticleTitle(interestsd.get(whichInt).getArticle().getTitle());
				temp.setFirstName(userd.getFirstName());
				temp.setLastName(userd.getLastName());
				temp.setUserId(userd.getIdUser());
				temp.setIsComment(0);
				temp.setUploadTime(interestsd.get(whichInt).getInterestTime());
				
				notificationBeans.add(temp);
			}
		}
		
		if(commentsd != null) {
			for(int whichInt = 0; whichInt < commentsd.size(); whichInt++) {
				NotificationBean temp = new NotificationBean();
				entities.User userd = userDao.getById(commentsd.get(whichInt).getCommenterId());
				temp.setArticleTitle(commentsd.get(whichInt).getArticle().getTitle());
				temp.setFirstName(userd.getFirstName());
				temp.setLastName(userd.getLastName());
				temp.setUserId(userd.getIdUser());
				temp.setIsComment(1);
				temp.setUploadTime(commentsd.get(whichInt).getUploadTime());
				temp.setContentText(commentsd.get(whichInt).getContentText());
				
				notificationBeans.add(temp);
			}
		}

		return Response.status(200).entity(notificationBeans).build();
	}
	
}
