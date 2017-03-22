package com.test.controller;

import com.test.models.MasterfriendsEntity;
import com.test.models.UsernamesEntity;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Owner on 3/14/2017.
 */
@Controller
public class LeaderBoard extends getDBSession {


    @RequestMapping("leaderboard")

    public ModelAndView leading(HttpSession session) {

        ArrayList<String> info = (ArrayList<String>) session.getAttribute("Array");
        String code = info.get(0);
        if (code == null || code.equals("")) {
            throw new RuntimeException(
                    "ERROR:Didn't get code parameter in callback.");
        }

//        LeaderBoard leaders = new LeaderBoard();
//        ArrayList<UsernamesEntity> userFriends;
//        userFriends = leaders.leaderBoard(info);
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
        userFriends.add(info.get(1));
        ArrayList<UsernamesEntity> userList = new ArrayList<>();

        //get all of the user info(points, name, id , email)
        for (String token : userFriends) {
            Session findinfo = getSession();
            Criteria c = findinfo.createCriteria(UsernamesEntity.class);
            c.add(Restrictions.eq("userId", token));
            userList.add((UsernamesEntity) c.list().get(0));
        }
        //sorts user list by points. (method is located in the UsernamesEntity class)
        Collections.sort(userList);

        return new
                ModelAndView("leaderboard", "message", userList);

    }




}



