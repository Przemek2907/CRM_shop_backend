package com.przemek.zochowski.repository;

import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import com.przemek.zochowski.model.Customer;
import com.przemek.zochowski.model.Errors;
import com.przemek.zochowski.repository.generic.AbstractGenericRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class CustomerRepositoryImpl extends AbstractGenericRepository<Customer> implements CustomerRepository {

    ErrorsRepository errorsRepository = new ErrorsRepositoryImpl();
    @Override
    public Optional<Customer> findByName(String name) {
        Optional<Customer> customerOptional = Optional.empty();
        Session session = null;
        Transaction tx = null;

        try {
            session = getSessionFactory().openSession();
            tx = session.getTransaction();
            tx.begin();
            Query query = session.createQuery("select c from Customer c where c.name= :name");
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
        Session session = null;
        Transaction tx = null;

        try {
            session = getSessionFactory().openSession();
            tx = session.getTransaction();
            tx.begin();
            List<Customer> customers = session
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
