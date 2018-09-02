package db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import entities.Article;
import entities.ArticlePK;
import entities.User;

public class ArticleDB {
	
	@SuppressWarnings("unchecked")
	public List<Article> getArticles()
    {
        List<Article> articles = null;
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        //Query q = em.createQuery("Select u from Article u");
        Query q = em.createNamedQuery("Article.findAll");
        articles =  q.getResultList();
		
        tx.commit();
        em.close();
        return articles;
    }
    
    //Returns the ad specified by the id only
    /*public Article find(int id)
    {
        Article article = null;
        
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        Query q = em.createQuery("Select a from Article a where a. = :email and u.password = :password");
        q.setParameter("email", email);
        q.setParameter("password", password);
        List articles =  q.getResultList();
        tx.commit();
        em.close();
        
        if (articles != null && articles.size() == 1)
        {
            article = (Article) articles.get(0);
        }

        return article;
        
    }*/
    
    public ArticlePK insertArticle(Article article)
    {
    	ArticlePK id = null;
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try 
        {
        	em.persist(article);
            em.flush();
            id = article.getId();
            tx.commit();
            return id;
        }
        catch (PersistenceException e)
        {
            if (tx.isActive()) tx.rollback();
            return id;
        }
        finally 
        {
            em.close();
        }
    }
    
    public ArticlePK mergeArticle(Article article)
    {
    	ArticlePK id = null;
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try 
        {
        	em.merge(article);
            em.flush();
            id = article.getId();
            tx.commit();
            return id;
        }
        catch (PersistenceException e)
        {
            if (tx.isActive()) tx.rollback();
            return id;
        }
        finally 
        {
            em.close();
        }
    }
    
    public Article getById(ArticlePK id)
    {
        Article article = null;
        
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        article =em.find(Article.class, id);
	
        tx.commit();
        em.close();
        
        
        return article;
    }
    
    public Article getByArticleId(int id) {
    	Article article = null;
    	
    	EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        Query q = em.createQuery("Select ad from Article ad where ad.id.idArticle = :idArticle");
        q.setParameter("idArticle", id);
        
        List articles = q.getResultList();
        tx.commit();
        em.close();
        
        if (articles != null && articles.size() == 1)
        {
        	article = (Article) articles.get(0);
        }

        return article;
    }
    
    @SuppressWarnings("unchecked")
	public List<Article> getConnectedArticles(int userId) {
    	
    	EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
    	List<Article> articles = null;
        
        //Will return userId's and connected with userId articles
    	String qString = "SELECT a FROM Article a "
    			+ "WHERE a.id.user_idUser in (SELECT c.connectedUserId FROM Connection c where c.id.user_idUser = :userId) "
    			+ "or a.id.user_idUser = :userId "
    			+ "or a.id.idArticle in (Select i.id.article_idArticle FROM Interest i" + 
    			"							WHERE i.interesterId in" + 
    			"                            (SELECT c.connectedUserId FROM Connection c where c.id.user_idUser = :userId))";
    	
    	
        //Query q = em.createQuery("SELECT a FROM Article a WHERE a.id.user_idUser in (SELECT c.connectedUserId FROM Connection c where c.id.user_idUser = :userId) or a.id.user_idUser = :userId");
    	
    	//String qString = "SELECT a FROM Article a Where a.id.idArticle in (Select i.id.article_idArticle FROM Interest i WHERE i.interesterId in (SELECT c.connectedUserId FROM Connection c where c.id.user_idUser = :userId))";
        Query q = em.createQuery(qString);


        q.setParameter("userId", userId);
        
        articles = q.getResultList();
        tx.commit();
        em.close();
        
        return articles;
    }
    
}
