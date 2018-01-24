package frameworks.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Extend this interface only when particular DAO methods have to be designed and implemented for a certain Entity<br>
 * Some generic methods receive Object type and not T type as parameter
 * since sometimes one particular DAO might provide operations for a whole strong related set of Entities
 * Created by Cornel on 26.08.2015.
 */
public interface GenericDao<T, ID extends Serializable> {

    void persist(Object o);

    void merge(Object o);

    void remove(Object o);

    /**
     * sometimes we want to perform operations on entities that must not affect their database state<br>
     * those entities must be detached first
     *
     * @param o
     */
    void detach(Object o);

    /**
     * sometimes we want to cancel all changes made on an entity
     *
     * @param o
     */
    void refresh(Object o);

    /**
     * sometimes we might use stored procedures that create/alter existing entities
     */
    void flush();

    void removeById(ID id);

    List<T> findAll();

    T findById(ID id);

    T get(ID id);

}
