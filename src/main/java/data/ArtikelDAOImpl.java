package data;

import domein.Artikel;
import javax.persistence.EntityManager;

/**
 *
 * @author FeniksBV
 */

public class ArtikelDAOImpl extends GenericDAOImpl<Artikel>{
    
    public ArtikelDAOImpl(EntityManager em, Class<Artikel> entityClass) {
        super(em, entityClass);
    }
    
}
