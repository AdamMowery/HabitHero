package com.test.controller;

import com.test.models.MasterfriendsEntity;
import com.test.models.TasksEntity;
import com.test.models.UsernamesEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import java.util.ArrayList;


import java.util.Collections;
import java.util.Map;

@Controller
public class HomeController {
    ArrayList<String> info = new ArrayList<String>();

    @RequestMapping("/")

    public ModelAndView landingPage() {
        FBConnection fbConnection = new FBConnection();

        return new
                ModelAndView("landing", "message", fbConnection.getFBAuthUrl());

    }
//        @RequestMapping("getPermission")
//
//        public ModelAndView permission(@RequestParam("code") String code,Model model){
//            FacebookConnection facebookConnection = new FacebookConnection(code);
//            facebookConnection.invoke();
//            String redirectURL = "habits";
//
//
//            return new
//                    ModelAndView(redirectURL,"message","");
//
//        }

    @RequestMapping("bullShitScreen")
    public ModelAndView welcome(@RequestParam("code") String code) {

        FacebookConnection facebookConnection = new FacebookConnection(code);
        facebookConnection.invoke();
        info.add(code);
        info.add(facebookConnection.getId());
        info.add(facebookConnection.getOut());
        info.add(facebookConnection.getEmail());
        return new
                ModelAndView("bullShitScreen", "message", "");
    }

    @RequestMapping("habits")

    public ModelAndView welcome(Model model) {

        String code = info.get(0);


        if (code == null || code.equals("")) {
            throw new RuntimeException(
                    "ERROR:Didn't get code parameter in callback.");
        }

//    ********* array list of users**************
        // userNamelist creates connection with the database
        Criteria c = userNamelist();
        c.add(Restrictions.like("userId", "%" + info.get(1) + "%"));
        ArrayList<UsernamesEntity> userList = (ArrayList<UsernamesEntity>) c.list();

//  *********** add user to database if not already there ******
        if (userList.size() == 0) {
            Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
            SessionFactory sessionFact = cfg.buildSessionFactory();
            Session session = sessionFact.openSession();
            Transaction tx = session.beginTransaction();
            UsernamesEntity newuser = new UsernamesEntity();
            newuser.setUserId(info.get(1));
            newuser.setFullname(info.get(2));
            newuser.setEmail(info.get(3));
            newuser.setPoints(0);
            session.save(newuser);
            tx.commit();
            session.close();
        }
//   ******* Table of tasks *********
        //tasks is  a methods to connect to the database
        Criteria t = tasks();
        t.add(Restrictions.like("userId", "%" + info.get(1) + "%"));
        ArrayList<TasksEntity> taskList = (ArrayList<TasksEntity>) t.list();
        model.addAttribute("tasks", taskList);
//   ******* Table of friends *******
        Criteria f = friends();
        f.add(Restrictions.like("userId", "%" + info.get(1) + "%"));
        //this adds the f.list table from database to a new arraylist
        ArrayList<MasterfriendsEntity> friendsList = (ArrayList<MasterfriendsEntity>) f.list();


        model.addAttribute("friends", friendsList);

        return new
                ModelAndView("habits", "message", "your id: " + info.get(1));

    }

    @RequestMapping("addTask")

    public ModelAndView addTask(@RequestParam("task") String taskId, Model model) {
        String userId = info.get(1);
        String code = info.get(0);
        if (code == null || code.equals("")) {
            throw new RuntimeException(
                    "ERROR:Didn't get code parameter in callback.");
        }

        Criteria c = tasks();
        // searches for where userId and taskId match the user input
        c.add(Restrictions.like("userId", "%" + userId + "%"));
        c.add(Restrictions.like("taskId", "%" + taskId + "%"));
        ArrayList<TasksEntity> taskList = (ArrayList<TasksEntity>) c.list();

//  *********** add task to database if not already there ******
        if (taskList.size() == 0) {
            Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
            SessionFactory sessionFact = cfg.buildSessionFactory();
            Session session = sessionFact.openSession();
            Transaction tx = session.beginTransaction();
            TasksEntity newTask = new TasksEntity();
            newTask.setUserId(userId);
            newTask.setTaskId(taskId);
            session.save(newTask);
            tx.commit();
            session.close();
        }
        //displays updated task list
        Criteria t = tasks();
        t.add(Restrictions.like("userId", "%" + info.get(1) + "%"));
        ArrayList<TasksEntity> newtaskList = (ArrayList<TasksEntity>) t.list();
        model.addAttribute("tasks", newtaskList);
        return new
                ModelAndView("habits", "message", "Your id: " + userId);
    }

    private Criteria userNamelist() {
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFact = cfg.buildSessionFactory();
        Session selectUsers = sessionFact.openSession();
        selectUsers.beginTransaction();
        return selectUsers.createCriteria(UsernamesEntity.class);
    }

    private Criteria tasks() {
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFact = cfg.buildSessionFactory();
        Session selectTask = sessionFact.openSession();
        selectTask.beginTransaction();
        return selectTask.createCriteria(TasksEntity.class);
    }

    private Criteria friends() {
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFact = cfg.buildSessionFactory();
        Session selectTask = sessionFact.openSession();
        selectTask.beginTransaction();
        return selectTask.createCriteria(MasterfriendsEntity.class);
    }

    @RequestMapping("leaderboard")

    public ModelAndView leading() {
        String code = info.get(0);
        if (code == null || code.equals("")) {
            throw new RuntimeException(
                    "ERROR:Didn't get code parameter in callback.");
        }

//        LeaderBoard leaders = new LeaderBoard();
//        ArrayList<UsernamesEntity> userFriends;
//        userFriends = leaders.leaderBoard(info);

        Criteria f = friends();
        f.add(Restrictions.like("userId", "%" + info.get(1) + "%"));
        ArrayList<MasterfriendsEntity> friendsList = (ArrayList<MasterfriendsEntity>) f.list();
        ArrayList<String> userFriends = new ArrayList<>();
        //search for friends for a given userId
        for (int i = 0; i < friendsList.size(); i++) {
            userFriends.add(friendsList.get(i).getFriendId());
        }
        ArrayList<UsernamesEntity> userList = new ArrayList<>();
        //get all of the user info(points, name, id , email)
        for (String token : userFriends) {
            Criteria c = userNamelist();
            c.add(Restrictions.like("userId", "%" + token + "%"));
            userList.add((UsernamesEntity) c.list().get(0));
        }
        //sorts user list by points. (method is located in the UsernamesEntity class)
        Collections.sort(userList);

        return new
                ModelAndView("leaderboard", "message", userList);

    }

    @RequestMapping("addFriends")

    public ModelAndView searchFriends(@RequestParam("code") String code,
                                      @RequestParam("find") String input) {

        if (code == null || code.equals("")) {
            throw new RuntimeException(
                    "ERROR:you are not logged in");
        }
        FacebookConnection facebookConnection = new FacebookConnection(code).invoke();
        String id = facebookConnection.getId();

        Criteria c = userNamelist();
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



        return new
                ModelAndView("addFriends", "message", "");

    }

    static class FacebookConnection {
        private String code;
        private String out;
        private String id;
        private String email;

        public FacebookConnection(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getOut() {
            return out;
        }

        public String getId() {
            return id;
        }

        public String getEmail() {
            return email;
        }

        public FacebookConnection invoke() {
            FBConnection fbConnection = new FBConnection();
            String accessToken = fbConnection.getAccessToken(code);
            FBGraph fbGraph = new FBGraph(accessToken);
            String graph = fbGraph.getFBGraph();
            Map<String, String> fbProfileData = fbGraph.getGraphData(graph);

            id = fbProfileData.get("id");
            out = fbProfileData.get("name");
            email = fbProfileData.get("email");

            return this;
        }
    }




}
