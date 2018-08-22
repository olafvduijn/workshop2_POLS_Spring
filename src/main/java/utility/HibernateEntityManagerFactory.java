package utility;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author FeniksBV
 */
public class HibernateEntityManagerFactory {

    protected static EntityManagerFactory emf = null;
    protected static EntityManager em = null;

    protected static void initialize() {
        emf = Persistence.createEntityManagerFactory("Workshop2PU");
        em = emf.createEntityManager();
    }

    public static EntityManager getEntityManager() {
        if (em == null) {
            initialize();
        }
        return em;
    }

    public static void closeEntityManagerFactory() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null) {
            emf.close();
        }
    }
}
