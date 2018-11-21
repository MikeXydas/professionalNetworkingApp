package db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import entities.Application;
import entities.ApplicationPK;
import entities.Article;

public class ApplicationDB {

	@SuppressWarnings("unchecked")
	public List<Application> getApplications()
    {
        List<Application> applications = null;
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        Query q = em.createNamedQuery("Application.findAll");
        applications =  q.getResultList();
		
        tx.commit();
        em.close();
        return applications;
    }
    
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
    
    @SuppressWarnings("unchecked")
    public List<Application> getApplicationsOfUser(int userId) {
    	EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
    	List<Application> applications = null;
    	
    	Query q = em.createQuery("SELECT a FROM Application a WHERE a.id.advertisment_User_idUser = :userId");
        q.setParameter("userId", userId);

        applications = q.getResultList();
        tx.commit();
        em.close();
        
        return applications;
    }
}
