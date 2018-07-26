package dao;

//import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import jpautils.EntityManagerHelper;
import model.User;

public class UserDAOImpl implements UserDAO 
{

	@Override
	public User find(int id) {
		EntityManager em = EntityManagerHelper.getEntityManager();
		User user = em.find(User.class, id); 
        return user;
	}

	@Override
	public List<User> list() {
		EntityManager em = EntityManagerHelper.getEntityManager();
		Query query = em.createNamedQuery("User.findAll");
		@SuppressWarnings("unchecked")
		List<User> users = query.getResultList();  
        return users;
	}

	@Override
	public void create(User user) 
	{
		EntityManager em = EntityManagerHelper.getEntityManager();
		em.persist(user);
	}
	
	@Override
	public int userExists(String email) {
		EntityManager em = EntityManagerHelper.getEntityManager();
		Query query = em.createQuery("SELECT u FROM User u WHERE u.email LIKE :inputEmail");
		query.setParameter("inputEMail", email);
		@SuppressWarnings("unchecked")
		List <User> emails = query.getResultList();
		
		if(emails == null)
			return 1;
		return 0;
	}
	
	
}
