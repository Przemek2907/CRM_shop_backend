package com.przemek.zochowski.service.businessLogic;

import com.przemek.zochowski.model.Shop;
import com.przemek.zochowski.repository.generic.AbstractGenericRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShopServiceLayer extends AbstractGenericRepository<Shop> {

    private List<String> listofShopsFromCountriesDifferentThanProduct;


    /*THIS METHOD LOADS A DATA FROM A DATABASE INTO A LIST AND RETURNS A LIST OF SHOPS WHICH ARE LOCATED IN A DIFFERNET COUNTRY THAN THE PRODUCT WAS PRODUCED IN*/
    public List<String> getListofProductsWithAdifferentCountryThanTheShop() {
        Session session = null;
        Transaction transaction = null;

        try {
            session = getSessionFactory().openSession();
            transaction = session.getTransaction();
            transaction.begin();
            Query query = session.createQuery(" select Shop.name from Shop");

            /*FIGURE OUT HOW TO RECREATE SQL QUERY OF THIS TYPE
             select shop.name from shop
             inner join country on shop.country_id = country.id
                inner join stock on shop.id = stock.shop_id
                inner join product on stock.product_id = product.id
                inner join producer on producer.id = product.producer_id
                where producer.country_id <> shop.country_id

                WITH THE HQL ABOVE
             */

            List<Object> tempList = (List<Object>) query.getResultList();

           Iterator iterator = tempList.iterator();
            while (iterator.hasNext()) {
                Object[] object = (Object[]) iterator.next();
                //now I have one array of Object for each row of the query;
                String shopName = String.valueOf(object[0]);
                listofShopsFromCountriesDifferentThanProduct.add(shopName);
            }
            System.out.println(listofShopsFromCountriesDifferentThanProduct);

        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.println("FAILED RUNNING THE QUERY TO GET THE LIST OF SHOPS LOCATED IN A DIFFERENT COUNTRY THAN THE PRODUCT HAS BEEN CREATED IN");
        }
            return listofShopsFromCountriesDifferentThanProduct;
        }
    }

