package com.test.controller;

import com.test.models.MasterfriendsEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;


public  class AddFriend {

//    public AddFriend() {
//    }

    public void searchFriends(String input, ArrayList<String> info) {



       /* Criteria c = friends();
        c.add(Restrictions.like("userid", "%" + input + "%"));
        ArrayList<MasterfriendsEntity> friendsList = (ArrayList<MasterfriendsEntity>) c.list();
        if (!(friendsList.size() == 0)) {
            Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
            SessionFactory sessionFact = cfg.buildSessionFactory();
            Session session = sessionFact.openSession();
            Transaction tx = session.beginTransaction();
            MasterfriendsEntity newfriend = new MasterfriendsEntity();
            newfriend.setUserId(id);
            newfriend.setFriendId(input);
            session.save(newfriend);
            tx.commit();
            session.close();
        }

*/


    }


    private Criteria friends() {
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFact = cfg.buildSessionFactory();
        Session selectTask = sessionFact.openSession();
        selectTask.beginTransaction();
        return selectTask.createCriteria(MasterfriendsEntity.class);
    }


}