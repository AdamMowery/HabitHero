package com.test.controller;

import com.test.models.UsernamesEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;

/**
 * Created by adamm on 3/22/2017.
 */
public class FriendInfo extends getDBSession {

    public  ArrayList<UsernamesEntity> getInfo(ArrayList<String> userFriends){
        ArrayList<UsernamesEntity> userList = new ArrayList<>();
        for (String token : userFriends) {
            Session findinfo = getSession();
            Criteria c = findinfo.createCriteria(UsernamesEntity.class);
            c.add(Restrictions.eq("userId", token));
            userList.add((UsernamesEntity) c.list().get(0));
            findinfo.close();
        }

    return userList ;
    }
}
