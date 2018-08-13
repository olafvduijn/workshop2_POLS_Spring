package genericDAO;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import genericDAO.Adres.AdresType;

public class AdresDAOImpl extends GenericDAOImpl<Adres> {

	public AdresDAOImpl(EntityManager em, Class<Adres> entityClass) {
		super(em, entityClass);
	}

	@Override
	public Adres findByName(Class<Adres> type, String pcode) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Adres> query = cb.createQuery(entityClass);
		Root<Adres> root = query.from(entityClass);
		query = query.select(root).where(cb.equal(root.get("postcode"), pcode));

		try {
			return em.createQuery(query).getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}

	}
	
	
	// "select * from adres where klant_idKlant= "+klantId+" && Adrestype=\"postadres\""
	public boolean factuurAdresAanwezig(int klantid) {
		
		 CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery<Adres> query = cb.createQuery(Adres.class);
	        Root<Adres> root = query.from(Adres.class);
	        query = query.select(root).where(cb.and((cb.equal(root.get("klant"), klantid)),cb.equal(root.get("adresType"),AdresType.FACTUURADRES)));
			
	        try {
				return (em.createQuery(query).getSingleResult()!=null);
			} catch (NoResultException nre) {
				return false;
			}

	}
	
	public boolean bezorgAdresAanwezig(int klantid) {
		
		 CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery<Adres> query = cb.createQuery(Adres.class);
	        Root<Adres> root = query.from(Adres.class);
	        query = query.select(root).where(cb.and((cb.equal(root.get("klant"), klantid)),cb.equal(root.get("adresType"),AdresType.BEZORGADRES)));
			
	        try {
				return (em.createQuery(query).getSingleResult()!=null);			
			} catch (NoResultException nre) {
				return false;
			}
	}
	
	public String toonFactuuradres(int klantid) {
		 CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery<Adres> query = cb.createQuery(Adres.class);
	        Root<Adres> root = query.from(Adres.class);
	        query = query.select(root).where(cb.and((cb.equal(root.get("klant"), klantid)),cb.equal(root.get("adresType"),AdresType.FACTUURADRES)));
			
	        try {
				Adres adresdb =em.createQuery(query).getSingleResult();	
				String adres="";
				adres="straatnaam: "+ adresdb.getStraatnaam() + System.lineSeparator();
				adres=adres+"huisnummer: "+adresdb.getHuisnummer()+ System.lineSeparator();
				adres=adres+"toevoeging: "+adresdb.getToevoeging()+ System.lineSeparator();
				adres=adres+"postcode: "+adresdb.getPostcode()+ System.lineSeparator();
				adres=adres+"plaats: "+adresdb.getWoonplaats();
				
				return adres;
				
			} catch (NoResultException nre) {
				return null;
			}
	}
	
	public String toonBezorgadres(int klantid) {
		 CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery<Adres> query = cb.createQuery(Adres.class);
	        Root<Adres> root = query.from(Adres.class);
	        query = query.select(root).where(cb.and((cb.equal(root.get("klant"), klantid)),cb.equal(root.get("adresType"),AdresType.BEZORGADRES)));
			
	        try {
				Adres adresdb =em.createQuery(query).getSingleResult();	
				String adres="";
				adres="straatnaam: "+ adresdb.getStraatnaam() + System.lineSeparator();
				adres=adres+"huisnummer: "+adresdb.getHuisnummer()+ System.lineSeparator();
				adres=adres+"toevoeging: "+adresdb.getToevoeging()+ System.lineSeparator();
				adres=adres+"postcode: "+adresdb.getPostcode()+ System.lineSeparator();
				adres=adres+"plaats: "+adresdb.getWoonplaats();
				
				return adres;
				
			} catch (NoResultException nre) {
				return null;
			}
		
	}
	
	public String toonPostadres(int klantid) {
		 CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery<Adres> query = cb.createQuery(Adres.class);
	        Root<Adres> root = query.from(Adres.class);
	        query = query.select(root).where(cb.and((cb.equal(root.get("klant"), klantid)),cb.equal(root.get("adresType"),AdresType.POSTADRES)));
			
	        try {
				Adres adresdb =em.createQuery(query).getSingleResult();	
				String adres="";
				adres="straatnaam: "+ adresdb.getStraatnaam() + System.lineSeparator();
				adres=adres+"huisnummer: "+adresdb.getHuisnummer()+ System.lineSeparator();
				adres=adres+"toevoeging: "+adresdb.getToevoeging()+ System.lineSeparator();
				adres=adres+"postcode: "+adresdb.getPostcode()+ System.lineSeparator();
				adres=adres+"plaats: "+adresdb.getWoonplaats();
				
				return adres;
				
			} catch (NoResultException nre) {
				return null;
			}
	}
}