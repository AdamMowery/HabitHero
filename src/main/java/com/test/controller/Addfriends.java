package com.test.controller;

import com.test.models.MasterfriendsEntity;
import com.test.models.UsernamesEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * Created by adamm on 3/22/2017.
 */
@Controller
public class Addfriends extends getDBSession{


    //Addfriends mapping
    //do something with add bar to actually add friends
    @RequestMapping("addfriends")
    public ModelAndView addFriendsPage(HttpSession session) {

        ArrayList<String> info = (ArrayList<String>) session.getAttribute("Array");
        Session selectFriend = getSession();
        Criteria f = selectFriend.createCriteria(MasterfriendsEntity.class);
        f.add(Restrictions.eq("userId", info.get(1)));
        ArrayList<MasterfriendsEntity> friendsList = (ArrayList<MasterfriendsEntity>) f.list();
        selectFriend.close();
        ArrayList<String> userFriends = new ArrayList<>();
        //search for friends for a given userId
        for (int i = 0; i < friendsList.size(); i++) {
            userFriends.add(friendsList.get(i).getFriendId());
        }
        ArrayList<UsernamesEntity> userList = new ArrayList<>();
        //get all of the user info(points, name, id , email)
        for (String token : userFriends) {
            Session findinfo = getSession();
            Criteria c = findinfo.createCriteria(UsernamesEntity.class);
            c.add(Restrictions.eq("userId", token));
            userList.add((UsernamesEntity) c.list().get(0));
            findinfo.close();
        }

        return new
                ModelAndView("addFriends", "friends", userList);
    }

    @RequestMapping("addFriendButton")
    public ModelAndView addFriendButton(@RequestParam("userid") String id, HttpSession session) {

        ArrayList<String> info = (ArrayList<String>) session.getAttribute("Array");

        Session selectUsers = getSession();
        Criteria c = selectUsers.createCriteria(UsernamesEntity.class);
        c.add(Restrictions.eq("userId", id));
        ArrayList<UsernamesEntity> userList = (ArrayList<UsernamesEntity>) c.list();
        selectUsers.close();

        Session selectFriend = getSession();
        Criteria f = selectFriend.createCriteria(MasterfriendsEntity.class);
        f.add(Restrictions.eq("userId", id));
        ArrayList<MasterfriendsEntity> friendsList = (ArrayList<MasterfriendsEntity>) f.list();
        selectFriend.close();

        if (friendsList.size() == 0 && userList.size() == 1) {
            if (!id.equals(info.get(1))) {
                Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
                SessionFactory sessionFact = cfg.buildSessionFactory();
                Session s = sessionFact.openSession();
                Transaction tx = s.beginTransaction();

                MasterfriendsEntity newfriend = new MasterfriendsEntity();
                newfriend.setUserId(info.get(1));
                newfriend.setFriendId(id);

                //this saves the object into the database, writes to DB, C in Crud for CREATE
                s.save(newfriend);
                tx.commit();
                s.close();
            }
        }

        //redisplays friends table with added friend
        Session selectAllFriend = getSession();
        Criteria g = selectAllFriend.createCriteria(MasterfriendsEntity.class);
        g.add(Restrictions.eq("userId", info.get(1)));
        ArrayList<MasterfriendsEntity> friendsList2 = (ArrayList<MasterfriendsEntity>) g.list();
        selectAllFriend.close();
        ArrayList<String> userFriends = new ArrayList<>();
        //search for friends for a given userId
        for (int i = 0; i < friendsList2.size(); i++) {
            userFriends.add(friendsList2.get(i).getFriendId());
        }
        ArrayList<UsernamesEntity> userList2 = new ArrayList<>();
        //get all of the user info(points, name, id , email)
        for (String token : userFriends) {
            Session findinfo = getSession();
            Criteria t = findinfo.createCriteria(UsernamesEntity.class);
            t.add(Restrictions.eq("userId", token));
            userList2.add((UsernamesEntity) t.list().get(0));
            findinfo.close();
        }

        return new
                ModelAndView("addFriends", "friends", userList2);

    }


}
