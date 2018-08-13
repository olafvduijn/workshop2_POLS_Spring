package genericDAO;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import genericDAO.Account;
import genericDAO.GenericDAOImpl;
import genericDAO.Klant;

public class KlantDAOImpl extends GenericDAOImpl<Klant> {

	public KlantDAOImpl(EntityManager em, Class<Klant> entityClass) {
		super(em, entityClass);
	}

	@Override
	public Klant findByName(Class<Klant> entityClass, String name) {
		
		 CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery<Klant> query = cb.createQuery(entityClass);
	        Root<Klant> root = query.from(entityClass);
	        query = query.select(root).where(cb.equal(root.get("achternaam"), name));

	        try {
	            return em.createQuery(query).getSingleResult();
	        } catch (NoResultException nre) {
	            return null;
	        }

	}
	public Klant getAlleKlantenPerAccount(int accountId){
		
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Klant> query = cb.createQuery(Klant.class);
        Root<Klant> klant = query.from(Klant.class);
        query.select(klant);
        
        query = query.select(klant).where(cb.equal(klant.get("account"), accountId));

        try {
            return em.createQuery(query).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
		
	}
	


}
