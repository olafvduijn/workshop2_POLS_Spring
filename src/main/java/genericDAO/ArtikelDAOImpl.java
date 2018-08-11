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

    @Override
    public Artikel findByName(Class<Artikel> type, String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
