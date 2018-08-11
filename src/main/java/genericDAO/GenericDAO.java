package genericDAO;

import java.util.ArrayList;

/**
 *
 * @author FeniksBV
 * @param <T>
 */
public interface GenericDAO<T> {

    public T findById(Class<T> type, Long id);

    public T findByName(Class<T> type, String name);
    
    public ArrayList<T> findAll();

    public T create(T entity);

    public void update(T entity);

    public void delete(T entity);

}
