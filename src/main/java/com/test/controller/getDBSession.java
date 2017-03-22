package com.test.controller;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by adamm on 3/22/2017.
 */
public class getDBSession {
   public static   SessionFactory sessionFact;
    public Session getSession() {

        if (sessionFact == null) {

            Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
            sessionFact = cfg.buildSessionFactory();
        }
        Session select = sessionFact.openSession();
        select.beginTransaction();
        return select;
    }
}
