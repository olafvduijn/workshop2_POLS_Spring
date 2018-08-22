package data;

import java.util.ArrayList;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import domein.Bestelling;
import domein.Klant;
import org.springframework.stereotype.Component;
import utility.EntityManagerPols;

@Component
public class BestellingDAOImpl extends GenericDAOImpl<Bestelling> {

    public BestellingDAOImpl() {
        super(EntityManagerPols.em, Bestelling.class);
    }
    
//	public BestellingDAOImpl(EntityManager em, Class<Bestelling> entityClass) {
//		super(em, entityClass);
//	}

	public ArrayList<Bestelling> getAlleBestellingenPerKlant(Klant klant) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Bestelling> query = cb.createQuery(Bestelling.class);
		Root<Bestelling> bestelling = query.from(Bestelling.class);
		query.select(bestelling);

		query = query.select(bestelling).where(cb.equal(bestelling.get("klantId"), klant.getId()));

		try {
			return (ArrayList<Bestelling>) em.createQuery(query).getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

}
