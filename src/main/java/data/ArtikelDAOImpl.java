package data;

import domein.Artikel;
import org.springframework.stereotype.Component;
import utility.EntityManagerPols;

/**
 *
 * @author FeniksBV
 */

@Component
public class ArtikelDAOImpl extends GenericDAOImpl<Artikel>{
    
    public ArtikelDAOImpl() {
        super(EntityManagerPols.em, Artikel.class);
    }
//    public ArtikelDAOImpl(EntityManager em, Class<Artikel> entityClass) {
//        super(em, entityClass);
//    }
    
}
