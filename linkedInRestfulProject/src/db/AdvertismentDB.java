package db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import entities.Advertisment;
import entities.AdvertismentPK;
import entities.ConnectionRequest;

public class AdvertismentDB {

    @SuppressWarnings("unchecked")
	public List<Advertisment> getAdvertisments()
    {
        List<Advertisment> advertisments = null;
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        //Query q = em.createQuery("Select u from Advertisment u");
        Query q = em.createNamedQuery("Advertisment.findAll");
        advertisments =  q.getResultList();
		
        tx.commit();
        em.close();
        return advertisments;
    }
    
    //Returns the ad specified by the id only
    /*public Advertisment find(int id)
    {
        Advertisment advertisment = null;
        
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        Query q = em.createQuery("Select a from Advertisment a where a. = :email and u.password = :password");
        q.setParameter("email", email);
        q.setParameter("password", password);
        List advertisments =  q.getResultList();
        tx.commit();
        em.close();
        
        if (advertisments != null && advertisments.size() == 1)
        {
            advertisment = (Advertisment) advertisments.get(0);
        }

        return advertisment;
        
    }*/
    
    public AdvertismentPK insertAdvertisment(Advertisment advertisment)
    {
    	AdvertismentPK id = null;
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try 
        {
        	em.persist(advertisment);
            em.flush();
            id = advertisment.getId();
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
    
    public AdvertismentPK mergeAdvertisment(Advertisment advertisment)
    {
    	AdvertismentPK id = null;
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try 
        {
        	em.merge(advertisment);
            em.flush();
            id = advertisment.getId();
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
    
    public Advertisment getById(AdvertismentPK id)
    {
        Advertisment advertisment = null;
        
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        advertisment =em.find(Advertisment.class, id);
	
        tx.commit();
        em.close();
        
        
        return advertisment;
    }
    
    @SuppressWarnings("unchecked")
	public Advertisment getByAdId(int id)
    {
        List<Advertisment> advertisments = null;
        Advertisment advertisment = null;
        
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        Query q = em.createQuery("Select ad from Advertisment ad where ad.id.idAdvertisment = :id");
        q.setParameter("id", id);
        
        advertisments = q.getResultList();
        tx.commit();
        em.close();
        
        if (advertisments != null && advertisments.size() == 1)
        {
            advertisment = (Advertisment) advertisments.get(0);
        }

        return advertisment;
        
    }
}
