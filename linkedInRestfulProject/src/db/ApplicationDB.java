package db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import entities.Application;
import entities.ApplicationPK;

public class ApplicationDB {

	@SuppressWarnings("unchecked")
	public List<Application> getApplications()
    {
        List<Application> applications = null;
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        //Query q = em.createQuery("Select u from Application u");
        Query q = em.createNamedQuery("Application.findAll");
        applications =  q.getResultList();
		
        tx.commit();
        em.close();
        return applications;
    }
    
    //Returns the ad specified by the id only
    /*public Application find(int id)
    {
        Application application = null;
        
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        Query q = em.createQuery("Select a from Application a where a. = :email and u.password = :password");
        q.setParameter("email", email);
        q.setParameter("password", password);
        List applications =  q.getResultList();
        tx.commit();
        em.close();
        
        if (applications != null && applications.size() == 1)
        {
            application = (Application) applications.get(0);
        }

        return application;
        
    }*/
    
    public ApplicationPK insertApplication(Application application)
    {
    	ApplicationPK id = null;
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try 
        {
        	em.persist(application);
            em.flush();
            id = application.getId();
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
    
    public ApplicationPK mergeApplication(Application application)
    {
    	ApplicationPK id = null;
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try 
        {
        	em.merge(application);
            em.flush();
            id = application.getId();
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
    
    public Application getById(ApplicationPK id)
    {
        Application application = null;
        
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        application =em.find(Application.class, id);
	
        tx.commit();
        em.close();
        
        
        return application;
    }
}
