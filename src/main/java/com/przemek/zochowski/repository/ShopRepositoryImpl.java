package com.przemek.zochowski.repository;

import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import com.przemek.zochowski.model.Shop;
import com.przemek.zochowski.repository.generic.AbstractGenericRepository;


import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

public class ShopRepositoryImpl extends AbstractGenericRepository<Shop> implements ShopRepository {

    ErrorsRepository errorsRepository = new ErrorsRepositoryImpl();
    @Override
    public Optional<Shop> findByName(String name) {
        Optional<Shop> optionalShop = Optional.empty();
        EntityManager entityManager = null;
        EntityTransaction tx = null;


        try{
            entityManager = getEntityManagerFactory().createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            Query query = entityManager.createQuery("select s from Shop s where s.name = :name");
            query.setParameter("name", name);
            optionalShop = Optional.of((Shop) query.getSingleResult());
            tx.commit();
        } catch (Exception e){
            e.printStackTrace();
            throw new MyException(ErrorCode.REPOSITORY, e.getMessage());
        }
        return optionalShop;
    }

    @Override
    public Optional<Shop> findByNameAndCountry(String name, String country) {
        Optional<Shop> optionalShop = Optional.empty();
        EntityManager entityManager = null;
        EntityTransaction tx = null;


        try{
            entityManager = getEntityManagerFactory().createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            List<Shop> shopList = entityManager.createQuery("select  s from Shop s" +
                    " where s.name = :name ", Shop.class)
                    .setParameter("name", name)
                    .getResultList();
                shopList.stream()
                    .filter(shop -> shop.getCountry().getName().equals(country));
            if (!shopList.isEmpty()){
                optionalShop = Optional.of(shopList.get(0));
            }
            tx.commit();
        }catch (Exception e){
            throw new MyException(ErrorCode.REPOSITORY, e.getMessage());
        }

        return optionalShop;
    }





}
