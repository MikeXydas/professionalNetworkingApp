package db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import entities.User;


//import entities.User;
import entities.Skill;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

public class SkillDB {
	
	
    @SuppressWarnings("unchecked")
	public List<Skill> getSkills()
    {
        List<Skill> skills = null;
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        Query q = em.createNamedQuery("Skill.findAll");
        skills =  q.getResultList();
		
        tx.commit();
        em.close();
        return skills;
    }
    
    public Skill find(String skillName)
    {
        Skill skill = null;
        
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        Query q = em.createQuery("Select s from Skill s where s.skillName = :skillName");
        q.setParameter("skillName", skillName);
        List skills =  q.getResultList();
        tx.commit();
        em.close();
        
        if (skills != null && skills.size() == 1)
        {
            skill = (Skill) skills.get(0);
        }

        return skill;
        
    }
    
    public int insertSkill(Skill skill)
    {
        int id = -1;
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try 
        {
            em.persist(skill);
            em.flush();
            id = skill.getIdSkill();
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
    
    public int mergeSkill(Skill skill)
    {
        int id = -1;
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try 
        {
            em.merge(skill);
            em.flush();
            id = skill.getIdSkill();
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
    
    public Skill getById(int id)
    {
        Skill skill = null;
        
        EntityManager em = JPAResource.factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        skill =em.find(Skill.class, id);
	
        tx.commit();
        em.close();
        
        
        return skill;
    }
}
