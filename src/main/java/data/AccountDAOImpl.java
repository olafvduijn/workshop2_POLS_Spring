package data;

import domein.*;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

/**
 *
 * @author FeniksBV
 */
public class AccountDAOImpl extends GenericDAOImpl<Account> {

    public AccountDAOImpl(EntityManager em, Class<Account> entityClass) {
        super(em, entityClass);
    }

    public Account findByName(String name) {

        // select * from account where name = ?
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

    public List<Account> getKlantAccountsZonderKlant() {

        // SELECT * FROM account a  LEFT join klant k on a.id=k.account_id 
        //          where k.id is Null and a.rol = "klant"
        // Dit kunnen we ook oplossen als:
        // 1. Bouw een lijst van alle accounts where rol = klant
        // 2. Bouw een lijst van alle klanten 
        // 3. Loop door de accountlist.
        // 4. Indien account.id niet voorkomt in klantList.account_id, voeg dan dat account
        // toe aan de resultList.
        //
        // Of een ander SQL (met hetzelfde resultaat):
        // select * from account where rol = "klant" and id not in (select account_id from klant);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Account> query = cb.createQuery(entityClass);
        Root<Account> root = query.from(entityClass);
        query.select(root);

        // Nu samenstellen de subquery: select account_id from klant
        Subquery<Integer> subquery = query.subquery(Integer.class);
        Root<Klant> subRoot = subquery.from(Klant.class);
        subquery.select(subRoot.get("account"));

        query.where(cb.equal(root.get("rol"), Account.Rol.klant),
                cb.not(cb.in(root.get("id")).value(subquery)));
        TypedQuery<Account> q = em.createQuery(query);
        return q.getResultList();

    }
}
