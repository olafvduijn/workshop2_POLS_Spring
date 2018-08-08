package genericDAO;

import javax.persistence.EntityManager;

/**
 *
 * @author FeniksBV
 */
public class ArtikelDAOImpl extends GenericDAOImpl<Artikel>{
    
    public ArtikelDAOImpl(EntityManager em, Class<Artikel> entityClass) {
        super(em, entityClass);
    }
    
    // Eventueel een @Override op bepaalde methods uit de interface
}
