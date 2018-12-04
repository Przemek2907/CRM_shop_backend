package com.przemek.zochowski.repository;

import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import com.przemek.zochowski.model.Errors;
import com.przemek.zochowski.model.Stock;
import com.przemek.zochowski.repository.generic.AbstractGenericRepository;
import com.przemek.zochowski.service.errors.ErrorService;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class StockRepositoryImpl extends AbstractGenericRepository<Stock> implements StockRepository {


    ErrorsRepository errorsRepository = new ErrorsRepositoryImpl();

    @Override
    public Map<String, Integer> findAmountOfStockByTradeName(String industryName, int thresholdAmount) {

        Map<String, Integer> producerWithAmountOfProductsManufactured = new HashMap<>();

        Session session = null;
        Transaction tx = null;

        try {
            session = getSessionFactory().openSession();
            tx = session.getTransaction();
            tx.begin();
            Query query = session.createQuery("select Producer.name, SUM(Stock.quantity) from Stock, Product, Producer, Trade FETCH ALL PROPERTIES" +
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
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return producerWithAmountOfProductsManufactured;
    }

    public Long findStockIdByProductId(Long product_id) {
        Session session = null;
        Transaction tx = null;
        Long stockId = null;

        try {
            session = getSessionFactory().openSession();
            tx = session.getTransaction();
            tx.begin();
            Query query = session.createQuery("select id from Stock  where product = :product_id");
            query.setParameter("product_id", product_id);
            stockId = (Long) query.getSingleResult();
            tx.commit();
        } catch (MyException e) {
            e.printStackTrace();
            throw new MyException(ErrorCode.REPOSITORY, e.getMessage());
        }finally {
            if (session != null) {
                session.close();
            }
        }
        return stockId;
    }
}
