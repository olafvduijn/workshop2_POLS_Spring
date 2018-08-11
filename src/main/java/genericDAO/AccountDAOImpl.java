package genericDAO;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author FeniksBV
 */
public class AccountDAOImpl extends GenericDAOImpl<Account> {

    public AccountDAOImpl(EntityManager em, Class<Account> entityClass) {
        super(em, entityClass);
    }

    @Override
    public Account findByName(Class<Account> entityClass, String name) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Account> query = cb.createQuery(entityClass);
        Root<Account> root = query.from(entityClass);
        //
        // LET OP: Hier doe je NIET de root.get() op annotated column_name
        // Dat is: @Column(name = "username", length = 45), zie class Account
        // maar je doet dat op variable name uit de class.
        // Dat is: private String userNaam
        //
        query = query.select(root).where(cb.equal(root.get("userNaam"), name));

        try {
            return em.createQuery(query).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

}
