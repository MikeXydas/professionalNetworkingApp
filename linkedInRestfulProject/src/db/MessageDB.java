package db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import entities.User;
import entities.Advertisment;
import entities.ConnectionRequest;
import entities.Conversation;
import entities.Message;
import entities.MessagePK;

public class MessageDB {

    @SuppressWarnings("unchecked")
	public List<Message> getMessages()
    {
        List<Message> messages = null;
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        //Query q = em.createQuery("Select u from Message u");
        Query q = em.createNamedQuery("Message.findAll");
        messages =  q.getResultList();
		
        tx.commit();
        em.close();
        return messages;
    }
    
    public MessagePK insertMessage(Message message)
    {
    	MessagePK id = null;
    	EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try
        {
        	em.persist(message);
        	em.flush();
        	id = message.getId();
        	tx.commit();
        	return id;
        }
        catch(PersistenceException e)
        {
        	if(tx.isActive()) tx.rollback();
        	return id;
        }
        finally
        {
        	em.close();
        }
    }
    
    public MessagePK mergeMessage(Message message) 
    {
    	MessagePK id = null;
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try 
        {
        	em.merge(message);
            em.flush();
            id = message.getId();
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
    
    public Message getById(MessagePK id)
    {
    	Message message =null;
    	EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        message =em.find(Message.class, id);
	
        tx.commit();
        em.close();
        
        
        return message;
    }
    
    @SuppressWarnings("unchecked")
	public List<Message> getConvMessages(int convId) {
    	List<Message> messages = null;
    	EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        Query q = em.createQuery("Select m from Message m where m.id.conversation_idConversation = :convId");
        q.setParameter("convId", convId);
        
        messages = q.getResultList();
        tx.commit();
        em.close();
        
        return messages;
    }
}
