package data;

import java.util.ArrayList;

/**
 *
 * @author FeniksBV
 * @param <T>
 */
public interface GenericDAO<T> {

    public T findById(int id);

    public ArrayList<T> findAll();

    public T create(T entity);

    public void update(T entity);

    public boolean delete(T entity);

}
