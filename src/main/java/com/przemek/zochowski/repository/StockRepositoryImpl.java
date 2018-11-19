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
import java.util.Optional;

public class StockRepositoryImpl extends AbstractGenericRepository<Stock> implements StockRepository {


    ErrorsRepository errorsRepository = new ErrorsRepositoryImpl();

    //ProductRepository productRepository = new ProductRepositoryImpl();
    @Override
    public int findAmountOfStockByProductId(int product_id) {

        Session session = null;
        Transaction tx = null;
        int amountInStock = 0;

        try {
            session = getSessionFactory().openSession();
            tx = session.getTransaction();
            tx.begin();
            Query query = session.createQuery("select quantity from Stock  where product = :product_id");
            query.setParameter("product_id", product_id);

            amountInStock = (int) query.getSingleResult();
           /* if (optionalAmount.isPresent()) {
                amountInStock = optionalAmount.get();
            } else {
                amountInStock = 0;
            }*/
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ErrorCode.REPOSITORY, e.getMessage());


        } finally {
            if (session != null) {
                session.close();
            }
        }
        return amountInStock;
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
          /*  System.out.println(exception.getTableName());
            System.out.println(exception.getErrorDateTime());
            System.out.println(exception.getMessage());*/
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
