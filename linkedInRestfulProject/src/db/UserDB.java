package db;

import entities.User;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;


public class UserDB {

    
    @SuppressWarnings("unchecked")
	public List<User> getUsers()
    {
        List<User> users = null;
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        //Query q = em.createQuery("Select u from User u");
        Query q = em.createNamedQuery("User.findAll");
        users =  q.getResultList();
		
        tx.commit();
        em.close();
        return users;
    }
    
    public User find(String email, String password)
    {
        User user = null;
        
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        Query q = em.createQuery("Select u from User u where u.email = :email and u.password = :password");
        q.setParameter("email", email);
        q.setParameter("password", password);
        List users =  q.getResultList();
        tx.commit();
        em.close();
        
        if (users != null && users.size() == 1)
        {
            user = (User) users.get(0);
        }

        return user;
        
    }
    
    public int insertUser(User user)
    {
    	//TODO: Check if email exists and return suitable message
        int id = -1;
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try 
        {
        	em.merge(user);
            //em.persist(user);
            em.flush();
            id = user.getIdUser();
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
    
    public int mergeUser(User user)
    {
    	int id = -1;
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try 
        {
        	em.merge(user);
            //em.persist(user);
            em.flush();
            id = user.getIdUser();
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
    
    public User getById(int id)
    {
        User user = null;
        
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        user =em.find(User.class, id);
	
        tx.commit();
        em.close();
        
        
        return user;
    }
    
    public void updateUser(User user)
    {
    	int id = -1;
    	EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        id = user.getIdUser();
        String email = user.getEmail();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String photoUrl = user.getPhotoUrl();
        String phoneNumber = user.getPhoneNumber();
        String educationText = user.getEducationText();
        String jobExperienceText = user.getJobExperienceText();
        
        //I do not want to update null strings
        if(photoUrl == null)
        	photoUrl = "EMPTY";
        if(educationText == null)
        	educationText = "EMPTY";
        if(jobExperienceText == null)
        	jobExperienceText = "EMPTY";
        
        Query updUser = em.createQuery("UPDATE User user SET user.email = :email, user.password = :password, user.firstName = :firstName, user.lastName = :lastName, user.photoUrl = :photoUrl, user.phoneNumber = :phoneNumber, user.educationText = :educationText, user.jobExperienceText = :jobExperienceText WHERE user.idUser = :id");
        updUser.setParameter("firstName", firstName);
        updUser.setParameter("lastName", lastName);
        updUser.setParameter("email", email);
        updUser.setParameter("password", password);
        updUser.setParameter("photoUrl", photoUrl);
        updUser.setParameter("phoneNumber", phoneNumber);
        updUser.setParameter("educationText", educationText);
        updUser.setParameter("jobExperienceText", jobExperienceText);
        updUser.setParameter("id", id);
        
        updUser.executeUpdate();
        
        tx.commit();
        em.close();
        
    } 
    
}