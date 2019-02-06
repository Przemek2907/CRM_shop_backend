package com.przemek.zochowski.repository;

import com.przemek.zochowski.dto.ModelMapper;
import com.przemek.zochowski.dto.TradeDto;
import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import com.przemek.zochowski.model.Trade;
import com.przemek.zochowski.repository.generic.AbstractGenericRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.Optional;

public class TradeRepositoryImpl extends AbstractGenericRepository<Trade> implements TradeRepository {

    ModelMapper modelMapper = new ModelMapper();
    ErrorsRepository errorsRepository = new ErrorsRepositoryImpl();



    @Override
    public Optional<Trade> findByName(String name) {
        Optional<Trade> optionalTrade = Optional.empty();
        EntityManager entityManager = null;
        EntityTransaction tx = null;


        try{
            entityManager = getEntityManagerFactory().createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            Query query = entityManager.createQuery("select t from Trade t where t.industry = :industry");
            query.setParameter("industry", name);
            optionalTrade = Optional.of((Trade) query.getSingleResult());
            tx.commit();
        } catch (Exception e){
            throw new MyException(ErrorCode.REPOSITORY, e.getMessage());
        }
        return optionalTrade;
    }

    //NEED TO CHECK IF THIS METHOD BELOW(WHICH ADDS A TRADE TO THE TABLE IF IT DOES NOT EXIST YET) WILL NOT MESS UP WITH THE FILE UPLOAD

    public Optional<Trade> findByNameAndAddIfNotFound(String name) {
        Optional<Trade> industryOptionalSubmitted = Optional.ofNullable(modelMapper.fromTradeDtoToTrade(TradeDto.builder().industry(name).build()));
        Optional<Trade> industryOptional = Optional.empty();
        EntityManager entityManager = null;
        EntityTransaction tx = null;


        try{
            entityManager = getEntityManagerFactory().createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            Query query = entityManager.createQuery("select t from Trade t where t.industry= :name");
            query.setParameter("name", name);
            industryOptional = Optional.of((Trade) query.getSingleResult());
            tx.commit();
        } catch (Exception e) {
            if (!industryOptional.equals(industryOptionalSubmitted)){
                addOrUpdate(industryOptionalSubmitted.get());
                industryOptional = industryOptionalSubmitted;
                return industryOptional;
            }
            throw new MyException(ErrorCode.REPOSITORY, e.getMessage());
        }
        return industryOptional;
    }
}
