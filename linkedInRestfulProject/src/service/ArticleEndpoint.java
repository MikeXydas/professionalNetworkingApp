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
import db.SkillDB;
import db.ArticleDB;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import model.UserBean;
import model.ChangeEmailBean;
import model.ChangePasswordBean;
import model.ExportXMLBean;
import model.LogInfoBean;
import model.SkillListBean;
import model.SearchBean;
import model.ArticleBean;
import utilities.XmlCreator;
import utilities.FileManipulation;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path("/Article")
public class ArticleEndpoint {
	private String FILE_SYSTEM = "/home/mike/Desktop/linkedInFileSystem";

	@POST
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

	}
	
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
	
	@GET
	@Path("/show")
	public Response uploadArticle(
			@FormParam("id") int id) {
		ArticleDB articleDao = new ArticleDB();
		entities.Article articled = articleDao.getByArticleId(id);
		
		return Response.status(200).entity("Title: " + articled.getTitle()).build();
	}
	
}
