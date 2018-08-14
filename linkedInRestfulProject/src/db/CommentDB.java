package db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import entities.Comment;
import entities.CommentPK;

public class CommentDB {
	
	@SuppressWarnings("unchecked")
	public List<Comment> getComments()
    {
        List<Comment> comments = null;
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        //Query q = em.createQuery("Select u from Comment u");
        Query q = em.createNamedQuery("Comment.findAll");
        comments =  q.getResultList();
		
        tx.commit();
        em.close();
        return comments;
    }
    
	@SuppressWarnings("unchecked")
	public List<Comment> commentsOfUser(int id) {
		
		List<Comment> comments = null;
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        Query q = em.createQuery("Select c from Comment c where c.commenterId = :id");
        q.setParameter("id", id);
        
        comments = q.getResultList();
        
        tx.commit();
        em.close();
        return comments;
    }
    //Returns the ad specified by the id only
    /*public Comment find(int id)
    {
        Comment comment = null;
        
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        Query q = em.createQuery("Select a from Comment a where a. = :email and u.password = :password");
        q.setParameter("email", email);
        q.setParameter("password", password);
        List comments =  q.getResultList();
        tx.commit();
        em.close();
        
        if (comments != null && comments.size() == 1)
        {
            comment = (Comment) comments.get(0);
        }

        return comment;
        
    }*/
    
    public CommentPK insertComment(Comment comment)
    {
    	CommentPK id = null;
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try 
        {
        	em.persist(comment);
            em.flush();
            id = comment.getId();
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
    
    public CommentPK mergeComment(Comment comment)
    {
    	CommentPK id = null;
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try 
        {
        	em.merge(comment);
            em.flush();
            id = comment.getId();
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
    
    public Comment getById(CommentPK id)
    {
        Comment comment = null;
        
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        comment =em.find(Comment.class, id);
	
        tx.commit();
        em.close();
        
        
        return comment;
    }
    
    

}
