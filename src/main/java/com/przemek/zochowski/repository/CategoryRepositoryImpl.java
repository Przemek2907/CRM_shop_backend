package com.przemek.zochowski.repository;

import com.przemek.zochowski.dto.CategoryDto;
import com.przemek.zochowski.dto.ModelMapper;
import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import com.przemek.zochowski.model.Category;
import com.przemek.zochowski.repository.generic.AbstractGenericRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class CategoryRepositoryImpl extends AbstractGenericRepository<Category> implements CategoryRepository {
    ErrorsRepository errorsRepository = new ErrorsRepositoryImpl();
    ModelMapper modelMapper = new ModelMapper();

    @Override
    public Optional<Category> findByName(String name) {
        Optional<Category> categoryOptional = Optional.empty();
        EntityManager entityManager = null;
        EntityTransaction tx = null;

        try {
            entityManager = getEntityManagerFactory().createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            List<Category> tempList = new ArrayList<>();
            tempList = entityManager.createQuery("select c from Category c where c.name= :name")
                    .setParameter("name", name)
                    .getResultList();
            if (!tempList.isEmpty()) {
                categoryOptional = Optional.of(tempList.get(0));
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ErrorCode.REPOSITORY, e.getMessage());
        }
        return categoryOptional;
    }
}
