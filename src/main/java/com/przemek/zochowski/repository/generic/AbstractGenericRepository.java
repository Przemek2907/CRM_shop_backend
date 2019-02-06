package com.przemek.zochowski.repository.generic;

import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

public abstract class AbstractGenericRepository<T> implements GenericRepository<T> {

    private EntityManagerFactory entityManagerFactory = DbConnection.getInstance().getEntityManagerFactory();
    private Class<T> entityClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }


    @Override
    public void addOrUpdate(T t) {
        EntityManager session = null;
        EntityTransaction tx = null;

        try {
            session = getEntityManagerFactory().createEntityManager();
            tx = session.getTransaction();
            tx.begin();
            session.merge(t);
            tx.commit();
        } catch (Exception e) {
            throw new MyException(ErrorCode.REPOSITORY, e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }


    @Override
    public void delete(Long id) {
        EntityManager session = null;
        EntityTransaction tx = null;
        try {
            session = getEntityManagerFactory().createEntityManager();
            tx = session.getTransaction();
            tx.begin();
            T t = session.find(entityClass, id);
            session.remove(t);
            tx.commit();
        } catch (Exception e) {
            throw new MyException(ErrorCode.REPOSITORY, e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void deleteAll() {
        EntityManager session = null;
        EntityTransaction tx = null;

        try {
            session = getEntityManagerFactory().createEntityManager();
            tx = session.getTransaction();
            tx.begin();
            List<T> items = session.createQuery("select i from " + entityClass.getCanonicalName() + " i").getResultList();
            for (T item : items) {
                session.remove(item);
            }
            tx.commit();
        } catch (Exception e) {
            throw new MyException(ErrorCode.REPOSITORY, e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<T> findOneById(Long id) {
        Optional<T> optionalObject = Optional.empty();
        EntityManager session = null;
        EntityTransaction tx = null;

        try {
            session = getEntityManagerFactory().createEntityManager();
            tx = session.getTransaction();
            tx.begin();
            optionalObject = Optional.of(session.find(entityClass, id));
            tx.commit();
        } catch (Exception e) {
            throw new MyException(ErrorCode.REPOSITORY, e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return optionalObject;
    }


    @Override
    public List<T> findAll() {
        List<T> items = null;

        EntityManager session = null;
        EntityTransaction tx = null;
        try {
            session = getEntityManagerFactory().createEntityManager();
            tx = session.getTransaction();
            boolean isConnectionActive = session.getTransaction().isActive();
            System.out.println(isConnectionActive);
            tx.begin();
            Query query = session.createQuery("select i from " + entityClass.getCanonicalName() + " i");
            items = query.getResultList();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ErrorCode.REPOSITORY, e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return items;
    }

}
