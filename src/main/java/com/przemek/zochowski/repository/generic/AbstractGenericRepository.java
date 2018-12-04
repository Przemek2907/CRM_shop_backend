package com.przemek.zochowski.repository.generic;

import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import com.przemek.zochowski.repository.ErrorsRepository;
import com.przemek.zochowski.repository.ErrorsRepositoryImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public abstract class AbstractGenericRepository<T> implements GenericRepository<T>{

    private SessionFactory sessionFactory = DbConnection.getInstance().getSessionFactory();

    private Class<T> entityClass = (Class<T>)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    public SessionFactory getSessionFactory (){
        return sessionFactory;
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }


    @Override
    public void addOrUpdate(T t) {
        Session session = null;
        Transaction tx = null;

        try{
            session = sessionFactory.openSession();
            tx = session.getTransaction();
            tx.begin();
            session.saveOrUpdate(t);
            tx.commit();
        }catch (Exception e){
            throw new MyException(ErrorCode.REPOSITORY, e.getMessage());
        }finally {
            session.close();
        }
    }


    @Override
    public void delete(Long id) {
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.getTransaction();
            tx.begin();
            T t = session.get(entityClass, id);
            session.delete(t);
            tx.commit();
        } catch (Exception e) {
            throw new MyException(ErrorCode.REPOSITORY, e.getMessage());
        } finally{
            session.close();
        }
    }

    @Override
    public void deleteAll() {
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.getTransaction();
            tx.begin();
            List<T> items = session.createQuery("select i from " + entityClass.getCanonicalName() + " i").getResultList();
            for (T item : items) {
                session.delete(item);
            }
            tx.commit();
        } catch (Exception e) {
            throw new MyException(ErrorCode.REPOSITORY, e.getMessage());
        }finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<T> findOneById(Long id) {
        Optional<T> optionalObject = Optional.empty();
        Session session = null;
        Transaction tx = null;

        try{
            session = sessionFactory.openSession();
            tx = session.getTransaction();
            tx.begin();
            optionalObject = Optional.of(session.get(entityClass, id));
            tx.commit();
        }catch (Exception e){
            throw new MyException(ErrorCode.REPOSITORY, e.getMessage());
        }
        finally {
            if (session != null) {
                session.close();
            }
        }
        return optionalObject;
    }


    @Override
    public List<T> findAll() {
        List<T> items = null;
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.getTransaction();
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
