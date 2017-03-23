package com.test.controller;

import com.test.models.MasterfriendsEntity;
import com.test.models.UsernamesEntity;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;


/**
 * Created by Owner on 3/14/2017.
 */
@Controller
public class LeaderBoard extends FriendInfo {


    @RequestMapping("leaderboard")

    public ModelAndView leading(HttpSession session) {

        // Checks to see if the user is logged in
        if (!session.getAttribute("Array").equals(null)) {
            // gets the current session of the user information array
            ArrayList<String> info = (ArrayList<String>) session.getAttribute("Array");

            //gets a list of all the users friends
            Session selectFriend = getSession();
            Criteria f = selectFriend.createCriteria(MasterfriendsEntity.class);
            f.add(Restrictions.eq("userId", info.get(1)));
            ArrayList<MasterfriendsEntity> friendsList = (ArrayList<MasterfriendsEntity>) f.list();
            selectFriend.close();

            //search for friends for a given userId
            ArrayList<String> userFriends = new ArrayList<>();
            for (int i = 0; i < friendsList.size(); i++) {
                userFriends.add(friendsList.get(i).getFriendId());
            }
            userFriends.add(info.get(1));

            //gets all the information on each friend
            ArrayList<UsernamesEntity> userList = getInfo(userFriends);

            //sorts user list by points
            Collections.sort(userList);

            return new
                    ModelAndView("leaderboard", "message", userList);
        } else {
            return new
                    ModelAndView("/", "message", "please login");
        }

    }


}



