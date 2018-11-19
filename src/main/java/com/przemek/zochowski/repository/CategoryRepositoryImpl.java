package com.przemek.zochowski.repository;

import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import com.przemek.zochowski.model.Category;
import com.przemek.zochowski.model.Errors;
import com.przemek.zochowski.repository.generic.AbstractGenericRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public class CategoryRepositoryImpl extends AbstractGenericRepository<Category> implements CategoryRepository {
    ErrorsRepository errorsRepository = new ErrorsRepositoryImpl();
   /* @Override
    public Optional<Category> findByName(String name) {
        Optional<Category> optionalCategory = Optional.empty();
        Session session = null;
        Transaction tx = null;

        try{
            session = getSessionFactory().openSession();
            tx = session.getTransaction();
            tx.begin();
            Query query = session.createQuery("select c from Category  c where c.name = :name");
            query.setParameter("name", name);
            optionalCategory = Optional.of((Category) query.getSingleResult());
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
            errorsRepository.addOrUpdate(Errors.builder().tableName(Category.class.getCanonicalName()).dateTime(LocalDateTime.now())
                    .message("DID NOT FOUND SPECIFIED COUNTRY IN THE TABLE").build());
            throw new MyException(Category.class.getCanonicalName(), e.getMessage(), LocalDateTime.now());
        }
        return optionalCategory;
    }*/

    @Override
    public Optional<Category> findByName(String name) {
        Optional<Category> optionalCategory = Optional.empty();
        Session session = null;
        Transaction tx = null;

        try{
            session = getSessionFactory().openSession();
            tx = session.getTransaction();
            tx.begin();
            List<Category> categories = session
                    .createQuery("select p from Category p where p.name= :name ", Category.class)
                    .setParameter("name", name)
                    .getResultList();
            if (!categories.isEmpty()) {
                optionalCategory = Optional.of(categories.get(0));
            }
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
            throw new MyException(ErrorCode.REPOSITORY, e.getMessage());
        }
        return optionalCategory;
    }
}
