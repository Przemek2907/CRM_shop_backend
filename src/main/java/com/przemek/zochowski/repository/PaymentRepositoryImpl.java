package com.przemek.zochowski.repository;

import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import com.przemek.zochowski.model.Customer;
import com.przemek.zochowski.model.EPayment;
import com.przemek.zochowski.model.Errors;
import com.przemek.zochowski.model.Payment;
import com.przemek.zochowski.repository.generic.AbstractGenericRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.Optional;

public class PaymentRepositoryImpl extends AbstractGenericRepository<Payment> implements PaymentRepository {
    ErrorsRepository errorsRepository = new ErrorsRepositoryImpl();
    @Override
    public Optional<Payment> findByName(String payment) {
        Optional<Payment> paymentOptional = Optional.empty();
        Session session = null;
        Transaction tx = null;

        try {
            session = getSessionFactory().openSession();
            tx = session.getTransaction();
            tx.begin();
            Query query = session.createQuery("select p from Payment p where p.payment= :name");
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
