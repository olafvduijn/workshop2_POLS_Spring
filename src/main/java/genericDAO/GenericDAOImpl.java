package genericDAO;

import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author FeniksBV
 * @param <T>
 */
public abstract class GenericDAOImpl<T> implements GenericDAO<T> {

    protected EntityManager em;
    protected final Class<T> entityClass;

    protected GenericDAOImpl(EntityManager em, Class<T> entityClass) {
        this.em = em;
        this.entityClass = entityClass;
    }

    @Override
    public T findById(Class<T> classType, Long id) {
        T entity = em.find(classType, id);
        return entity;
    }

    @Override
    public ArrayList<T> findAll() {
        CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(entityClass);
        criteriaQuery.select(criteriaQuery.from(entityClass));
        return (ArrayList<T>) em.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public T create(T entity) {
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
        return entity;
    }

    @Override
    public void update(T entity) {
        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();
    }

    @Override
    public void delete(T entity) {
        em.getTransaction().begin();
        em.remove(entity);
        em.getTransaction().commit();
    }
}
