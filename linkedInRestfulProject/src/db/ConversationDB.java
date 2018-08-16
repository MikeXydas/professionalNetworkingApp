package db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import entities.User;

import entities.Conversation;
import entities.Skill;


public class ConversationDB {

	@SuppressWarnings("unchecked")
	public List<Conversation> getConverations()
	{
		List<Conversation> conversations = null;
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        Query q = em.createNamedQuery("Conversation.findAll");
        conversations = q.getResultList();
        
        tx.commit();
        em.close();
        
        return conversations;
		
	}
	
	public Conversation find(int userr1Id, int userr2Id)
	{
		Conversation conv = null;
        
		EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        Query q1 =em.createQuery(
        		"SELECT c FROM conversation c ,user_has_conversation u WHERE  u.User_idUser = :userr1Id  AND c.idConversation = u.Conversation_idConversation;");
        Query q2 =em.createQuery(
                "SELECT c FROM conversation c ,user_has_conversation u WHERE  u.User_idUser = :userr2Id  AND c.idConversation = u.Conversation_idConversation;");
        q1.setParameter("userr1Id", userr1Id);
        q2.setParameter("userr2Id", userr2Id);
        List convs1 =  q1.getResultList();
        List convs2 = q2.getResultList();
        tx.commit();
        em.close();
        
        convs1.retainAll(convs2);
        
        if(convs1 != null && convs1.size() == 1) {
        	
        	conv = (Conversation) convs1.get(0);
        }
		
		return conv;
	}
	
	public int insertConversation(Conversation conv) {
		 
		int id = -1;
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try 
        {
        	em.persist(conv);
        	em.flush();
        	id = conv.getIdConversation();
        	tx.commit();
        	return id;
        }
        catch(PersistenceException e)
        {
            if (tx.isActive()) tx.rollback();
            return id;
        }
        finally 
        {
            em.close();
        }
	}
	
	public int mergeConversation(Conversation conv) {
		 
		int id = -1;
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try 
        {
        	em.merge(conv);
        	em.flush();
        	id = conv.getIdConversation();
        	tx.commit();
        	return id;
        }
        catch(PersistenceException e)
        {
            if (tx.isActive()) tx.rollback();
            return id;
        }
        finally 
        {
            em.close();
        }
	}
	
	public Conversation getById(int id) 
	{
        Conversation conv= null;
        
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        conv =em.find(Conversation.class, id);
	
        tx.commit();
        em.close();
        
        
        return conv;
	}
	
}
