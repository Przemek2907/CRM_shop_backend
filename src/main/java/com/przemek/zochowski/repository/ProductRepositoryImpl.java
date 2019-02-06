package com.przemek.zochowski.repository;

import com.przemek.zochowski.dto.ProductDto;
import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import com.przemek.zochowski.model.*;
import com.przemek.zochowski.repository.generic.AbstractGenericRepository;


import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.*;

public class ProductRepositoryImpl extends AbstractGenericRepository<Product> implements ProductRepository{


    @Override
    public Optional<Product> findByName(String name) {
        Optional<Product> optionalProducer = Optional.empty();
        EntityManager entityManager = null;
        EntityTransaction tx = null;

        try {
            entityManager = getEntityManagerFactory().createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            List<Product> products = entityManager
                    .createQuery("select p from Product p where p.name= :name ", Product.class)
                    .setParameter("name", name)
                    .getResultList();
            products.forEach(System.out::println);
            if (!products.isEmpty()) {
                optionalProducer = Optional.of(products.get(0));
            }
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
            throw new MyException(ErrorCode.REPOSITORY, e.getMessage());
        }
        return optionalProducer;
    }


    public Optional<Product> provideIdByProductName(String name) {
        Optional<Product> optionalProduct = Optional.empty();
        EntityManager entityManager = null;
        EntityTransaction tx = null;

        try {
            entityManager = getEntityManagerFactory().createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            Query query = entityManager.createQuery("select p from Product p  where p.name = :name");
            query.setParameter("name", name);
            optionalProduct = Optional.of((Product) query.getSingleResult());
            tx.commit();
        } catch (Exception e){
            throw new MyException(ErrorCode.REPOSITORY, e.getMessage());
        }
        return optionalProduct;
    }


    @Override
    public boolean hasGuaranteeSet(Long productId, EGuarantee g) {
        EntityManager entityManager = null;
        EntityTransaction tx = null;


        try{
            if (productId == null) {
                throw new NullPointerException("PRODUCT ID IS NULL");
            }

            if (g == null) {
                throw new NullPointerException("GUARANTEE IS NULL");
            }
            entityManager = getEntityManagerFactory().createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            Product product = entityManager.find(Product.class, productId);
            if (product != null) {
                return product.getGuarantees().contains(g);
            }
            tx.commit();
            return false;
        }catch (Exception e){
            throw new MyException(ErrorCode.REPOSITORY, e.getMessage());
        }
    }
}
