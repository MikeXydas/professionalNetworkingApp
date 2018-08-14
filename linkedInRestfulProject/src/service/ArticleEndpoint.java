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
	public Response postArticle(final ArticleBean articleBean) {
		
		ArticleDB articleDao = new ArticleDB();
		UserDB userDao = new UserDB();
		FileManipulation fileManip = new FileManipulation();

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
		
		if(articleBean.getPhotoBytes() != null) {
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
		}
		
		return Response.status(200).build();
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
	public Response showInterest(final ShowInterestBean intBean) {
		ArticleDB articleDao = new ArticleDB();
		InterestDB interestDao = new InterestDB();
		entities.Article articled = articleDao.getByArticleId(intBean.getArticleId());
		entities.Interest interestd = new entities.Interest();
		entities.InterestPK interestPK = new entities.InterestPK();
		interestd.setId(interestPK);
		
		interestd.setArticle(articled);
		Date date = new Date();
		interestd.setInterestTime(date);
		interestd.setInteresterId(intBean.getInteresterId());
		
		interestDao.insertInterest(interestd);
		
		return Response.status(200).build();
	}
	
	@POST
	@Path("/postCommnet")
	@Consumes({"application/json"})
	public Response showInterest(final PostCommentBean commBean) {
		ArticleDB articleDao = new ArticleDB();
		CommentDB commentDao = new CommentDB();
		entities.Article articled = articleDao.getByArticleId(commBean.getArticleId());
		entities.Comment commentd = new entities.Comment();
		entities.CommentPK commentPK = new entities.CommentPK();
		commentd.setId(commentPK);
		
		commentd.setArticle(articled);
		commentd.setCommenterId(commBean.getCommenterId());
		Date date = new Date();
		commentd.setUploadTime(date);
		commentd.setContentText(commBean.getText());
		
		commentDao.insertComment(commentd);
		
		return Response.status(200).build();
	}
	
	@GET
	@Path("/showArticles/{id:[0-9]*}")
	@Produces({"application/json"})
	public List<ArticleBean> returnHomepageArticles(@PathParam("id") int id) throws IOException {
		ArticleDB articleDao = new ArticleDB();
		List<Article> articles = null;
		articles = articleDao.getConnectedArticles(id);
		
		List<ArticleBean> articleBeans = new ArrayList<ArticleBean>();
		
		for(int i = 0; i < articles.size(); i++) {
			articleBeans.add(createArticleBean(articles.get(i)));
		}
		
		return articleBeans;
	}
	
	@GET
	@Path("/showArticles")
	public Response showHomepageArticles(
			@FormParam("id") int id) {
		ArticleDB articleDao = new ArticleDB();
		//entities.Article articled = articleDao.getByArticleId(id);
		List<Article> articles = null;
		articles = articleDao.getConnectedArticles(id);
		
		
		return Response.status(200).entity("Numb of articles found: " + articles.size()).build();
	}
	
	//Creates an articleBean from an article entity
	private ArticleBean createArticleBean(entities.Article articled) throws IOException {
		ArticleBean artBean = new ArticleBean();
		UserBean user = new UserBean();
		FileManipulation fileManip= new FileManipulation();
		List<CommentBean> comments = new ArrayList<CommentBean>();
		List<InterestBean> interests = new ArrayList<InterestBean>();
		
		//Setting user bean
		//artBean.setIdArticle(articled.getId().getIdArticle());
		user.setFirstName(articled.getUser().getFirstName());
		user.setLastName(articled.getUser().getLastName());
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
		artBean.setUploadTime(articled.getUploadTime());
		if(articled.getContentText() != null) {
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
		}
		
		return artBean;
	}
	
}
