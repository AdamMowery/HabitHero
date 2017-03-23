package com.test.controller;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by adamm on 3/22/2017.
 */
public class getDBSession {

    public static SessionFactory sessionFact;

    //Sets up a connection to the database
    public Session getSession() {

        //if the user doesn't already have a session one is created
        if (sessionFact == null) {
            Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
            sessionFact = cfg.buildSessionFactory();
        }
        Session select = sessionFact.openSession();
        select.beginTransaction();

        return select;
    }
}
