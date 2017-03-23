package com.test.controller;

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

@Controller
public class HomeController extends getDBSession {

    @RequestMapping("/")

    public ModelAndView landingPage() {
        //starts the facebook connection to get auth code
        FBConnection fbConnection = new FBConnection();

        return new
                ModelAndView("landing", "message", fbConnection.getFBAuthUrl());
    }

    @RequestMapping("PrivacyPolicy")

    public ModelAndView viewPrivacyPolicy() {

        return new
                ModelAndView("PrivacyPolicy", "message", "");

    }

    @RequestMapping("about")

    public ModelAndView aboutPage() {

        return new
                ModelAndView("about", "message", "");

    }

    @RequestMapping("aboutPreLogin")

    public ModelAndView aboutPageBeforeLoggingIn() {

        return new
                ModelAndView("aboutPreLogin", "message", "");

    }

    @RequestMapping("challenges")

    public ModelAndView challenges() {

        return new
                ModelAndView("challenges", "message", "");

    }

    @RequestMapping("welcome")

    public ModelAndView welcome(@RequestParam("code") String code, HttpSession session) {

        //finishes the facebook connection to get all the user information
        ArrayList<String> info = new ArrayList<>();
        FacebookConnection facebookConnection = new FacebookConnection(code);
        facebookConnection.invoke();
        info.add(code);
        info.add(facebookConnection.getId());
        info.add(facebookConnection.getOut());
        info.add(facebookConnection.getEmail());

        // if session object that holds user info is empty, a new session is created.
        if (session.getAttribute("Array") == null) {
            session.setAttribute("Array", info);
        }

        // searches the database to make sure the user isn't already a member
        Session selectUsers = getSession();
        Criteria c = selectUsers.createCriteria(UsernamesEntity.class);
        c.add(Restrictions.eq("userId", info.get(1)));
        ArrayList<UsernamesEntity> userList = (ArrayList<UsernamesEntity>) c.list();
        selectUsers.close();

        // add user to database if not already there
        if (userList.size() == 0) {
            Session s = getSession();
            UsernamesEntity newuser = new UsernamesEntity();
            newuser.setUserId(info.get(1));
            newuser.setFullname(info.get(2));
            newuser.setEmail(info.get(3));
            newuser.setPoints(0);
            s.save(newuser);
            s.getTransaction().commit();
            s.close();
        }


        return new
                ModelAndView("welcome", "message", facebookConnection.getId());
    }




















}
