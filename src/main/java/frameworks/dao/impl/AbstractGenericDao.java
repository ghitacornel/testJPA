package frameworks.dao.impl;

import frameworks.dao.GenericDao;
import frameworks.dao.exceptions.DAOException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Generic DAO implementation<br>
 * Can be used without Interface
 */
public abstract class AbstractGenericDao<T, ID extends Serializable> implements GenericDao<T, ID> {

    // need to have it for later use
    final private Class<T> type;

    @PersistenceContext
    protected EntityManager em;

    @SuppressWarnings("unchecked")
    public AbstractGenericDao() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class<T>) pt.getActualTypeArguments()[0];
    }

    @Override
    public void persist(Object o) {
        em.persist(o);
    }

    @Override
    public void merge(Object o) {
        em.merge(o);
    }

    @Override
    public void remove(Object o) {
        em.remove(o);
    }

    @Override
    public void detach(Object o) {
        em.detach(o);
    }

    @Override
    public void refresh(Object o) {
        em.refresh(o);
    }

    @Override
    public void flush() {
        em.flush();
    }

    @Override
    public void removeById(ID id) {
        T t = findById(id);
        if (t != null) {
            em.remove(t);
        }
    }

    @Override
    public List<T> findAll() {
        return em.createQuery("select t from " + type.getCanonicalName() + " t", type).getResultList();
    }

    @Override
    public T findById(ID id) {
        return em.find(type, id);
    }

    @Override
    public T get(ID id) {
        T t = em.find(type, id);
        if (t == null) {
            throw new DAOException("No instance of type " + type.getCanonicalName() + " found for id " + id);
        }
        return t;
    }

}
