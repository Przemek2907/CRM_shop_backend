package com.przemek.zochowski.repository;

import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import com.przemek.zochowski.model.Producer;
import com.przemek.zochowski.repository.generic.AbstractGenericRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

public class ProducerRepositoryImpl extends AbstractGenericRepository<Producer> implements ProducerRepository {

    ErrorsRepository errorsRepository = new ErrorsRepositoryImpl();
    @Override
    public Optional<Producer> findByName(String name) {
        Optional<Producer> optionalProducer = Optional.empty();
        EntityManager entityManager = null;
        EntityTransaction tx = null;

        try {
            entityManager = getEntityManagerFactory().createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            List<Producer> producers = entityManager
                    .createQuery("select p from Producer p where p.name= :name ", Producer.class)
                    .setParameter("name", name)
                    .getResultList();
            if (!producers.isEmpty()) {
                optionalProducer = Optional.of(producers.get(0));
            }
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
            throw new MyException(ErrorCode.REPOSITORY, e.getMessage());
        }
        return optionalProducer;
    }

    @Override
    public int numberOfProductsProducedBy(long producerId) {

        EntityManager entityManager = null;
        EntityTransaction tx = null;
        int number = 0;
        try {
            entityManager = getEntityManagerFactory().createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            number = entityManager.find(Producer.class, producerId)
                    .getProducts()
                    .size();
            tx.commit();
            System.out.println("------->" + number);
        }catch (Exception e){
            throw new MyException(ErrorCode.REPOSITORY, e.getMessage());
        }
        return number;
    }
}
