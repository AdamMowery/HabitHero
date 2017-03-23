package com.test.controller;

import com.test.models.MasterfriendsEntity;
import com.test.models.UsernamesEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
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
public class Addfriends extends FriendInfo {

    @RequestMapping("addfriends")

    public ModelAndView addFriendsPage(HttpSession session) {

        // Checks to see if the user is logged in
        if (!session.getAttribute("Array").equals(null)) {
            // gets the current session of the user information array
            ArrayList<String> info = (ArrayList<String>) session.getAttribute("Array");

            //database search for current friends
            Session selectFriend = getSession();
            Criteria f = selectFriend.createCriteria(MasterfriendsEntity.class);
            f.add(Restrictions.eq("userId", info.get(1)));
            ArrayList<MasterfriendsEntity> friendsList = (ArrayList<MasterfriendsEntity>) f.list();
            selectFriend.close();

            //gets all the friends user ids for a given userId
            ArrayList<String> userFriends = new ArrayList<>();
            for (int i = 0; i < friendsList.size(); i++) {
                userFriends.add(friendsList.get(i).getFriendId());
            }

            //gets all the information for each friend id to be able to display friend and points
            ArrayList<UsernamesEntity> userList = getInfo(userFriends);

            return new
                    ModelAndView("addFriends", "friends", userList);
        } else {
            return new
                    ModelAndView("/", "message", "please login");
        }
    }

    @RequestMapping("addFriendButton")
    public ModelAndView addFriendButton(@RequestParam("userid") String id, HttpSession session) {

        // Checks to see if the user is logged in
        if (!session.getAttribute("Array").equals(null)) {
            // gets the current session of the user information array
            ArrayList<String> info = (ArrayList<String>) session.getAttribute("Array");

            //database search to make sure the id entered by the user is a valid user
            Session selectUsers = getSession();
            Criteria c = selectUsers.createCriteria(UsernamesEntity.class);
            c.add(Restrictions.eq("userId", id));
            ArrayList<UsernamesEntity> userList = (ArrayList<UsernamesEntity>) c.list();
            selectUsers.close();

            //database search to make sure the user isn't already friends with the person they're try to add
            Session selectFriend = getSession();
            Criteria f = selectFriend.createCriteria(MasterfriendsEntity.class);
            f.add(Restrictions.eq("friendId", id));
            f.add(Restrictions.eq("userId", info.get(1)));
            ArrayList<MasterfriendsEntity> friendsList = (ArrayList<MasterfriendsEntity>) f.list();
            selectFriend.close();

            //adds the friend connection to the database and makes sure the user isn't adding their self
            if (friendsList.size() == 0 && userList.size() == 1) {
                if (!id.equals(info.get(1))) {
                    Session s = getSession();
                    MasterfriendsEntity newfriend = new MasterfriendsEntity();
                    newfriend.setUserId(info.get(1));
                    newfriend.setFriendId(id);
                    //this saves the object into the database, writes to DB, C in Crud for CREATE
                    s.save(newfriend);
                    s.getTransaction().commit();
                    s.close();
                }
            }

            //database search for current friends plus the new friend
            Session selectAllFriend = getSession();
            Criteria g = selectAllFriend.createCriteria(MasterfriendsEntity.class);
            g.add(Restrictions.eq("userId", info.get(1)));
            ArrayList<MasterfriendsEntity> friendsList2 = (ArrayList<MasterfriendsEntity>) g.list();
            selectAllFriend.close();

            //gets all the friends user ids for a given userId
            ArrayList<String> userFriends = new ArrayList<>();
            for (int i = 0; i < friendsList2.size(); i++) {
                userFriends.add(friendsList2.get(i).getFriendId());
            }

            //gets all the information for each friend id to be able to display friend and points
            ArrayList<UsernamesEntity> userList2 = getInfo(userFriends);

            return new
                    ModelAndView("addFriends", "friends", userList2);

        } else {
            return new
                    ModelAndView("/", "message", "please login");
        }

    }


}
