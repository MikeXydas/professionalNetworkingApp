package db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import entities.Connection;
import entities.ConnectionPK;

public class ConnectionDB {
	
	@SuppressWarnings("unchecked")
	public List<Connection> getConnections()
    {
        List<Connection> connections = null;
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        Query q = em.createNamedQuery("Connection.findAll");
        connections =  q.getResultList();
		
        tx.commit();
        em.close();
        return connections;
    }
	
	public ConnectionPK insertConnection(Connection connection)
    {
    	ConnectionPK id = null;
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try 
        {
        	em.persist(connection);
            em.flush();
            id = connection.getId();
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
    
    public ConnectionPK mergeConnection(Connection connection)
    {
    	ConnectionPK id = null;
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try 
        {
        	em.merge(connection);
            em.flush();
            id = connection.getId();
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
    
    public Connection getById(ConnectionPK id)
    {
        Connection connection = null;
        
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        connection =em.find(Connection.class, id);
	
        tx.commit();
        em.close();
        
        
        return connection;
    }
}
