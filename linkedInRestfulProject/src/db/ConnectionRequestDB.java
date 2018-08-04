package db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import entities.ConnectionRequest;
import entities.ConnectionRequestPK;

public class ConnectionRequestDB {
	
	@SuppressWarnings("unchecked")
	public List<ConnectionRequest> getUsers()
    {
        List<ConnectionRequest> connectionRequests = null;
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        Query q = em.createNamedQuery("ConnectionRequest.findAll");
        connectionRequests =  q.getResultList();
		
        tx.commit();
        em.close();
        return connectionRequests;
    }
    
    public ConnectionRequest find(int receiverId)
    {
        ConnectionRequest connectionRequest = null;
        
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        Query q = em.createQuery("Select c from ConnectionRequest c where c.senderId = :senderId");
        q.setParameter("senderId", receiverId);

        List connectionRequests =  q.getResultList();
        tx.commit();
        em.close();
        
        if (connectionRequests != null && connectionRequests.size() == 1)
        {
            connectionRequest = (ConnectionRequest) connectionRequests.get(0);
        }

        return connectionRequest;
        
    }
    
    public ConnectionRequestPK insertSkill(ConnectionRequest connectionRequest)
    {
        ConnectionRequestPK id = null;
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try 
        {
            em.persist(connectionRequest);
            em.flush();
            id = connectionRequest.getId();
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
    
    public ConnectionRequestPK mergeSkill(ConnectionRequest connectionRequest)
    {
    	ConnectionRequestPK id = null;
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try 
        {
            em.merge(connectionRequest);
            em.flush();
            id = connectionRequest.getId();
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
    
    public ConnectionRequest getById(ConnectionRequestPK id)
    {
        ConnectionRequest connectionRequest = null;
        
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        connectionRequest =em.find(ConnectionRequest.class, id);
	
        tx.commit();
        em.close();
        
        
        return connectionRequest;
    }
    
    
    public void deleteConnectionRequest(ConnectionRequest connectionRequest) {
    	EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        if(!em.contains(connectionRequest)) {
        	connectionRequest = em.merge(connectionRequest);
        }
    	em.remove(connectionRequest);
    	
    	tx.commit();
    	em.close();
    }
    
    public List<ConnectionRequest> getPendingRequests(int receiverId) {
        List<ConnectionRequest> connectionRequests = null;
    	EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        Query q = em.createQuery("Select c from ConnectionRequest c where c.senderId = :senderId");
        q.setParameter("senderId", receiverId);
        
        connectionRequests = q.getResultList();
        tx.commit();
        em.close();
        
        return connectionRequests;
        
        
    }
}
