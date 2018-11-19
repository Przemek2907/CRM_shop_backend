package com.przemek.zochowski.repository;

import com.przemek.zochowski.dto.CountryDto;
import com.przemek.zochowski.dto.ModelMapper;
import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import com.przemek.zochowski.model.Country;
import com.przemek.zochowski.model.Errors;
import com.przemek.zochowski.repository.generic.AbstractGenericRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.Optional;

public class CountryRepositoryImpl extends AbstractGenericRepository<Country> implements CountryRepository  {
    ErrorsRepository errorsRepository = new ErrorsRepositoryImpl();
    ModelMapper modelMapper = new ModelMapper();

    @Override
    public Optional<Country> findByName(String name) {
        Optional<Country> countryOptionalSubmitted = Optional.ofNullable(modelMapper.fromCountryDtoToCountry(CountryDto.builder().name(name).build()));
        Optional<Country> countryOptional = Optional.empty();
        Session session = null;
        Transaction tx = null;

        try {
            session = getSessionFactory().openSession();
            tx = session.getTransaction();
            tx.begin();
            Query query = session.createQuery("select c from Country c where c.name= :name");
            query.setParameter("name", name);
            countryOptional = Optional.of((Country) query.getSingleResult());
            tx.commit();
        } catch (Exception e) {
            if (!countryOptional.equals(countryOptionalSubmitted)){
                addOrUpdate(countryOptionalSubmitted.get());
                countryOptional = countryOptionalSubmitted;
                return countryOptional;
            }
            throw new MyException(ErrorCode.REPOSITORY, e.getMessage());
        }
        return countryOptional;
    }
}
