package data;

import java.util.ArrayList;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import domein.BestelRegel;
import org.springframework.stereotype.Component;
import utility.EntityManagerPols;

@Component
public class BestelregelDAOImpl extends GenericDAOImpl <BestelRegel>{

    public BestelregelDAOImpl() {
        super(EntityManagerPols.em, BestelRegel.class);
    }
//	public BestelregelDAOImpl(EntityManager em, Class<BestelRegel> entityClass) {
//		super(em, entityClass);
//	}
	
	public ArrayList <BestelRegel> getAlleBestelregelsPerBestelling(int bestellingId){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<BestelRegel> query = cb.createQuery(BestelRegel.class);
		Root<BestelRegel> regel = query.from(BestelRegel.class);
		query.select(regel);

		query = query.select(regel).where(cb.equal(regel.get("bestellingId"), bestellingId));

		try {
			return (ArrayList<BestelRegel>) em.createQuery(query).getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

}
