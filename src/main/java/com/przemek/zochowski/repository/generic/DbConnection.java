package com.przemek.zochowski.repository.generic;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DbConnection {

    private static DbConnection myInstance = new DbConnection();

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("HBN");


    public static DbConnection getInstance() {
        return myInstance;
    }

    private DbConnection (){
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public void close(){
       if (entityManagerFactory != null){
           entityManagerFactory.close();
       }
   }


}
