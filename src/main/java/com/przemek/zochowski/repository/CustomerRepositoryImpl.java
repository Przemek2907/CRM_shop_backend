package com.przemek.zochowski.repository;

import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import com.przemek.zochowski.model.Customer;
import com.przemek.zochowski.repository.generic.AbstractGenericRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

public class CustomerRepositoryImpl extends AbstractGenericRepository<Customer> implements CustomerRepository {

    ErrorsRepository errorsRepository = new ErrorsRepositoryImpl();

    /*@Override
    public void addOrUpdate(Customer customer) {
        EntityManager entityManager = null;
        EntityTransaction tx = null;

        try {
            entityManager = getEntityManagerFactory().createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            Query query = entityManager.createQuery("select c from Customer c where c.name= :name");
            query.setParameter("name", name);
            customerOptional = Optional.of((Customer) query.getSingleResult());
            tx.commit();
        } catch (Exception e) {
            throw new MyException(ErrorCode.REPOSITORY, e.getMessage());
        }
    }*/

    @Override
    public Optional<Customer> findByName(String name) {
        Optional<Customer> customerOptional = Optional.empty();
        EntityManager entityManager = null;
        EntityTransaction tx = null;

        try {
            entityManager = getEntityManagerFactory().createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            Query query = entityManager.createQuery("select c from Customer c where c.name= :name");
            query.setParameter("name", name);
            customerOptional = Optional.of((Customer) query.getSingleResult());
            tx.commit();
        } catch (Exception e) {
            throw new MyException(ErrorCode.REPOSITORY, e.getMessage());
        }
        return customerOptional;
    }

    @Override
    public Optional<Customer> findByNameAndSurname(String name, String surname) {
        Optional<Customer> customerOptional = Optional.empty();
        EntityManager entityManager = null;
        EntityTransaction tx = null;

        try {
            entityManager = getEntityManagerFactory().createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            List<Customer> customers = entityManager
                    .createQuery("select c from Customer c where c.name= :name and c.surname = :surname", Customer.class)
                    .setParameter("surname", surname)
                    .setParameter("name", name)
                    .getResultList();
            if (!customers.isEmpty()) {
                customerOptional = Optional.of(customers.get(0));
            }
            tx.commit();
        } catch (Exception e) {
            throw new MyException(ErrorCode.REPOSITORY, e.getMessage());

        }
        return customerOptional;
    }


}
