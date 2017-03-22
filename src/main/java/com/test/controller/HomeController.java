package com.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class HomeController {

    @RequestMapping("/")
/// Connects to facebook just to get a authorization code.Displays landing page with login button. Nothing is displayed in message.
    public ModelAndView landingPage() {
        FBConnection fbConnection = new FBConnection();

        return new
                ModelAndView("landing", "message", fbConnection.getFBAuthUrl());

    }

    @RequestMapping("PrivacyPolicy")
// Displays PrivacyPolicy .jsp . Don't need to log in to see page
    public ModelAndView viewPrivacyPolicy() {

        return new
                ModelAndView("PrivacyPolicy", "message", "");

    }

    @RequestMapping("about")
// Displays about .jsp . Don't need to log in to see page
    public ModelAndView aboutPage() {

        return new
                ModelAndView("about", "message", "");

    }

    @RequestMapping("aboutPreLogin")
// Displays aboutPreLogin .jsp .
    public ModelAndView aboutPageBeforeLoggingIn() {

        return new
                ModelAndView("aboutPreLogin", "message", "");

    }

    @RequestMapping("challenges")
// Displays aboutPreLogin .jsp .
    public ModelAndView challenges() {

        return new
                ModelAndView("challenges", "message", "");

    }

    @RequestMapping("welcome")
   /*  Displays after user logs in.
       Completes facebook connnection and gets user(id, name, email)
       Also starts Session
    */
    public ModelAndView welcome(@RequestParam("code") String code, HttpSession session) {
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


        return new
                ModelAndView("welcome", "message", facebookConnection.getId());
    }




















}
