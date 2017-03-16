package com.test.controller;

import com.test.models.MasterfriendsEntity;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * Created by davidshinabarger on 3/15/17.
 */
public class CreateFriendRelationship {

    Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
    SessionFactory sessionFact = cfg.buildSessionFactory();
    Session session = sessionFact.openSession();
    Transaction tx = session.beginTransaction();

    MasterfriendsEntity relationship = new MasterfriendsEntity();
    relationship.setuserID("namesomentinthoeusnht");

    session.save(relationship);

    session.getTransaction().commit();
    Hibernate.shutdown();


}
