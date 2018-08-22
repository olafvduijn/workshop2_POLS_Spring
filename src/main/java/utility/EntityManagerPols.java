package utility;

import javax.persistence.EntityManager;

public class EntityManagerPols {

	public static EntityManager em; 
	
	public EntityManagerPols() {
		this.em = HibernateEntityManagerFactory.getEntityManager();
	}

	public EntityManager getEm() {
		return this.em;
	}
}
