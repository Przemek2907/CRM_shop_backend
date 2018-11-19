package com.przemek.zochowski.repository;

import com.przemek.zochowski.dto.ProductDto;
import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import com.przemek.zochowski.model.*;
import com.przemek.zochowski.repository.generic.AbstractGenericRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;


import javax.persistence.Query;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public class ProductRepositoryImpl extends AbstractGenericRepository<Product> implements ProductRepository{

    ErrorsRepository errorsRepository = new ErrorsRepositoryImpl();
    CategoryRepository categoryRepository = new CategoryRepositoryImpl();
    ProducerRepository producerRepository = new ProducerRepositoryImpl();
    CountryRepository countryRepository = new CountryRepositoryImpl();
/*    @Override
    public Optional<Product> findByName(String name) {
        Optional<Product> optionalProduct = Optional.empty();
        Session session = null;
        Transaction tx = null;

        try{
            session = getSessionFactory().openSession();
            tx = session.getTransaction();
            tx.begin();
            Query query = session.createQuery("select p from Product p where p.name = :name");
            query.setParameter("name", name);
            optionalProduct = Optional.of((Product) query.getSingleResult());
            tx.commit();
        } catch (Exception e){
            e.printStackTrace();
            throw new MyException(Product.class.getCanonicalName(), e.getMessage(), LocalDateTime.now());
        }
        finally {
            if (session != null) {
                session.close();
            }
        }
        return optionalProduct;
    }*/

    @Override
    public Optional<Product> findByName(String name) {
        Optional<Product> optionalProducer = Optional.empty();
        Session session = null;
        Transaction tx = null;

        try{
            session = getSessionFactory().openSession();
            tx = session.getTransaction();
            tx.begin();
            List<Product> products = session
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


    public Optional<Product> findByIdWithStocks(Long id) {
        Optional<Product> optionalProduct = Optional.empty();
        Session session = null;
        Transaction tx = null;

        try{
            session = getSessionFactory().openSession();
            tx = session.getTransaction();
            tx.begin();
            Query query = session.createQuery("select p from Product p FETCH ALL PROPERTIES where p.id = :id");
            query.setParameter("id", id);
            optionalProduct = Optional.of((Product) query.getSingleResult());
            tx.commit();
        } catch (Exception e){
            throw new MyException(ErrorCode.REPOSITORY, e.getMessage());
        }
      /*  finally {
            if (session != null) {
                session.close();
            }
        }*/
        return optionalProduct;
    }

    // do usuniecia po lekcji???

    public List<Product> findProductRequestedByCustomerSelectedByUser(){
        Session session = null;
        Transaction tx = null;
        List<ProductDto> productList = new ArrayList<>();
        List<Product> products = new ArrayList<>();

        try{
            session = getSessionFactory().openSession();
            tx = session.getTransaction();
            tx.begin();
            Query query = session.createQuery("select product.name, product.price, category.name, producer.name, country.name" +
                    " from Product product, Category category, Producer producer, Country country, CustomerOrder orders FETCH ALL PROPERTIES");

            List<Object> tempList = (List<Object>) query.getResultList();
            Iterator iterator = tempList.iterator();
            while (iterator.hasNext()){
                Object[] object = (Object[]) iterator.next();
                Category category = categoryRepository.findByName(String.valueOf(object[2])).get();
                Producer producer = producerRepository.findByName(String.valueOf(object[3])).get();
                Country country = countryRepository.findByName(String.valueOf(object[4])).get();
                String priceStr = String.valueOf(object[1]);
                BigDecimal price = new BigDecimal(priceStr);
                //now I have one array of Object for each row of the query;
                Product product = Product.builder().name(String.valueOf(object[0])).price(price)
                        .category(category).producer(producer).build();
                String countryNameResult = String.valueOf(object[4]);
                products.add(product);
            }
            tx.commit();
        }catch (Exception e){
            throw new MyException(ErrorCode.REPOSITORY, e.getMessage());
        }
        return products;
    }
}
