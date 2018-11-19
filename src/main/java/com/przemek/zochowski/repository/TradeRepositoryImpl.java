package com.przemek.zochowski.repository;

import com.przemek.zochowski.dto.CountryDto;
import com.przemek.zochowski.dto.ModelMapper;
import com.przemek.zochowski.dto.TradeDto;
import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import com.przemek.zochowski.model.Country;
import com.przemek.zochowski.model.Errors;
import com.przemek.zochowski.model.Trade;
import com.przemek.zochowski.repository.generic.AbstractGenericRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.Optional;

public class TradeRepositoryImpl extends AbstractGenericRepository<Trade> implements TradeRepository {

    ModelMapper modelMapper = new ModelMapper();
    ErrorsRepository errorsRepository = new ErrorsRepositoryImpl();



    @Override
    public Optional<Trade> findByName(String name) {
        Optional<Trade> optionalTrade = Optional.empty();
        Session session = null;
        Transaction tx = null;

        try{
            session = getSessionFactory().openSession();
            tx = session.getTransaction();
            tx.begin();
            Query query = session.createQuery("select t from Trade t where t.industry = :industry");
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
        Session session = null;
        Transaction tx = null;

        try {
            session = getSessionFactory().openSession();
            tx = session.getTransaction();
            tx.begin();
            Query query = session.createQuery("select t from Trade t where t.industry= :name");
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
