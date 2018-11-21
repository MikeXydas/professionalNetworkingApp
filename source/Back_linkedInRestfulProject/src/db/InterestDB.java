package db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import entities.Article;
import entities.Comment;
import entities.Interest;
import entities.InterestPK;

public class InterestDB {
	
	@SuppressWarnings("unchecked")
	public List<Interest> getInterests()
    {
        List<Interest> interests = null;
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        Query q = em.createNamedQuery("Interest.findAll");
        interests =  q.getResultList();
		
        tx.commit();
        em.close();
        return interests;
    }
    
	@SuppressWarnings("unchecked")
	public List<Interest> interestsOfUser(int id) {
		
		List<Interest> interests = null;
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        Query q = em.createQuery("Select i from Interest i where i.interesterId = :id");
        q.setParameter("id", id);
        
        interests = q.getResultList();
        
        tx.commit();
        em.close();
        return interests;
    }
    
    public InterestPK insertInterest(Interest interest)
    {
    	InterestPK id = null;
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try 
        {
        	em.persist(interest);
            em.flush();
            id = interest.getId();
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
    
    public InterestPK mergeInterest(Interest interest)
    {
    	InterestPK id = null;
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try 
        {
        	em.merge(interest);
            em.flush();
            id = interest.getId();
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
    
    public Interest getById(InterestPK id)
    {
        Interest interest = null;
        
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        interest =em.find(Interest.class, id);
	
        tx.commit();
        em.close();
        
        
        return interest;
    }
    
}
