package com.przemek.zochowski.repository;

import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import com.przemek.zochowski.model.EPayment;
import com.przemek.zochowski.model.Payment;
import com.przemek.zochowski.repository.generic.AbstractGenericRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.Optional;

public class PaymentRepositoryImpl extends AbstractGenericRepository<Payment> implements PaymentRepository {
    ErrorsRepository errorsRepository = new ErrorsRepositoryImpl();
    @Override
    public Optional<Payment> findByName(String payment) {
        Optional<Payment> paymentOptional = Optional.empty();
        EntityManager entityManager = null;
        EntityTransaction tx = null;

        try {
            entityManager = getEntityManagerFactory().createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            Query query = entityManager.createQuery("select p from Payment p where p.payment= :name");
            query.setParameter("name", EPayment.valueOf(payment));
            System.out.println(payment);
            paymentOptional = Optional.of((Payment) query.getSingleResult()); // TU SIE COS WYWALA, ALE CZEMU???
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ErrorCode.REPOSITORY, e.getMessage());
        }
        return paymentOptional;
    }
}
