package com.przemek.zochowski.repository;

import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import com.przemek.zochowski.model.Stock;
import com.przemek.zochowski.repository.generic.AbstractGenericRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.*;

public class StockRepositoryImpl extends AbstractGenericRepository<Stock> implements StockRepository {


    ErrorsRepository errorsRepository = new ErrorsRepositoryImpl();

    @Override
    public Map<String, Integer> findAmountOfStockByTradeName(String industryName, int thresholdAmount) {

        Map<String, Integer> producerWithAmountOfProductsManufactured = new HashMap<>();

        EntityManager entityManager = null;
        EntityTransaction tx = null;


        try{
            entityManager = getEntityManagerFactory().createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            Query query = entityManager.createQuery("select Producer.name, SUM(Stock.quantity) from Stock, Product, Producer, Trade FETCH ALL PROPERTIES" +
                    "  where Trade.industry = :industryName " +
                    "group by Producer.name" +
                    " having SUM(Stock.quantity)> :thresholdAmount ");

            query.setParameter("industryName", industryName);
            query.setParameter("thresholdAmount", thresholdAmount);

            List<Object> queryResultList = query.getResultList();
            Iterator<Object> queryIterator = queryResultList.iterator();

            while (queryIterator.hasNext()){
                String producerName = queryIterator.next().toString();
                int amountOfProducts = (int) queryIterator.next();
                producerWithAmountOfProductsManufactured.put(producerName, amountOfProducts);
            }

            producerWithAmountOfProductsManufactured.forEach((k,v) -> System.out.println(k + "HAS PRODUCED " + v + "PRODUCTS IN TOTAL"));


            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ErrorCode.REPOSITORY, e.getMessage());
        }
        return producerWithAmountOfProductsManufactured;
    }

    public Long findStockIdByProductId(Long product_id) {
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        Long stockId = null;


        try{
            entityManager = getEntityManagerFactory().createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            Query query = entityManager.createQuery("select id from Stock fetch all properties  where product.id= :product_id");
            query.setParameter("product_id", product_id);
            stockId = (Long) query.getSingleResult();
            tx.commit();
        } catch (MyException e) {
            e.printStackTrace();
            throw new MyException(ErrorCode.REPOSITORY, e.getMessage());
        }
        return stockId;
    }

    public Long findStockIdByProductName(String productName) {
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        Long stockId = null;


        try{
            entityManager = getEntityManagerFactory().createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            Query query = entityManager.createQuery("select id from Stock fetch all properties where " +
                    "product.name = :product_name");
            query.setParameter("product_name", productName);
            stockId = (Long) query.getSingleResult();
            tx.commit();
        } catch (MyException e) {
            e.printStackTrace();
            throw new MyException(ErrorCode.REPOSITORY, e.getMessage());
        }
        return stockId;
    }
}
