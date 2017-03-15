package com.test.controller;

import com.test.models.MasterfriendsEntity;
import com.test.models.UsernamesEntity;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Owner on 3/14/2017.
 */
public class LeaderBoard {

    public ArrayList<UsernamesEntity> leaderBoard(ArrayList<String> info) {
        /*
        * 1. hql query1 that returns all friends for a given user
        * 2. store query1 in arraylist
        * 3. for each item in arraylist create new query that returns the  point and sorts highest to lowest
        *
        *
        *
        * */
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFact = cfg.buildSessionFactory();
        Session selectTask = sessionFact.openSession();
        selectTask.beginTransaction();
        Criteria f = selectTask.createCriteria(MasterfriendsEntity.class);
        f.add(Restrictions.like("userId", "%" + info.get(1) + "%"));
        ArrayList<MasterfriendsEntity> friendsList = (ArrayList<MasterfriendsEntity>) f.list();
        ArrayList<UsernamesEntity> userfriends = null;
        for (int i = 0; i < friendsList.size(); i++) {
            Configuration cf = new Configuration().configure("hibernate.cfg.xml");
            SessionFactory sessionFact1 = cf.buildSessionFactory();
            Session selectTask1 = sessionFact1.openSession();
            selectTask1.beginTransaction();
            Criteria u = selectTask.createCriteria(UsernamesEntity.class);
            u.add(Restrictions.like("userId", "%" + friendsList.get(i).getFriendId() + "%"));
            userfriends = (ArrayList<UsernamesEntity>) f.list();


        }
        return userfriends;

    }


}



