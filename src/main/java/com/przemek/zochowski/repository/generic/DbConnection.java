package com.przemek.zochowski.repository.generic;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DbConnection {

    private static DbConnection myInstance = new DbConnection();

    private SessionFactory sessionFactory
            = new Configuration().configure().buildSessionFactory();

    public SessionFactory getSessionFactory(){
        return sessionFactory;
    }

    public static DbConnection getInstance() {
        return myInstance;
    }

    private DbConnection (){
    }

    public void close(){
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }


}
