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

import java.util.Map;

@Controller
public class HomeController {


    @RequestMapping("/")

    public ModelAndView landingPage() {
        FBConnection fbConnection = new FBConnection();

        return new
                ModelAndView("landing", "message", fbConnection.getFBAuthUrl());

    }


    @RequestMapping("habits")

    public ModelAndView welcome(@RequestParam("code") String code, Model model) {

        if (code == null || code.equals("")) {
            throw new RuntimeException(
                    "ERROR:Didn't get code parameter in callback.");
        }
        FacebookConnection facebookConnection = new FacebookConnection(code).invoke();
        String id = facebookConnection.getId();
        String out = facebookConnection.getOut();
        String email = facebookConnection.getEmail();
//    ********* array list of users**************
        Criteria c = userNamelist();
        c.add(Restrictions.like("userid", "%" + id + "%"));
        ArrayList<UsernamesEntity> userList = (ArrayList<UsernamesEntity>) c.list();

//  *********** add user to database if not already there ******
        if (userList.size() == 0) {
            Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
            SessionFactory sessionFact = cfg.buildSessionFactory();
            Session session = sessionFact.openSession();
            Transaction tx = session.beginTransaction();
            UsernamesEntity newuser = new UsernamesEntity();
            newuser.setUserid(id);
            newuser.setFullname(out);
            newuser.setEmail(email);
            newuser.setPoints("0");
            session.save(newuser);
            tx.commit();
            session.close();
        }
//   ******* Table of tasks *********
        Criteria t = tasks();
        t.add(Restrictions.like("userId", "%" + id + "%"));
        ArrayList<TasksEntity> taskList = (ArrayList<TasksEntity>) t.list();
        model.addAttribute("task", taskList);
//   ******* Table of friends *******
        Criteria f = friends();
        f.add(Restrictions.like("userId", "%" + id + "%"));
        ArrayList<MasterfriendsEntity> friendsList = (ArrayList<MasterfriendsEntity>) f.list();
        model.addAttribute("friends", friendsList);

        return new
                ModelAndView("habits", "message", "your id: " + id);

    }

    @RequestMapping("addTask")

    public ModelAndView addTask(FacebookConnection connect,
                                @RequestParam("task") String taskId,
                                Model model) {
        String id = connect.id;
        String code = connect.code;
        if (code == null || code.equals("")) {
            throw new RuntimeException(
                    "ERROR:Didn't get code parameter in callback.");
        }

        Criteria c = tasks();
        c.add(Restrictions.like("userID", "%" + id + "%"));
        c.add(Restrictions.like("taskID", "%" + taskId + "%"));
        ArrayList<TasksEntity> taskList = (ArrayList<TasksEntity>) c.list();

//  *********** add task to database if not already there ******
        if (taskList.size() == 0) {
            Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
            SessionFactory sessionFact = cfg.buildSessionFactory();
            Session session = sessionFact.openSession();
            Transaction tx = session.beginTransaction();
            TasksEntity newTask = new TasksEntity();
            newTask.setUserId(taskId);
            session.save(newTask);
            tx.commit();
            session.close();
        }
        model.addAttribute("task", taskList);
        return new
                ModelAndView("habits", "message", "Your id: " + id);
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

    public ModelAndView leading(FacebookConnection connect) {
        String code = connect.code;
        String id = connect.id;
        String name = connect.out;
        if (code == null || code.equals("")) {
            throw new RuntimeException(
                    "ERROR:Didn't get code parameter in callback.");
        }


        // todo add points for all friends of this user

        return new
                ModelAndView("leaderboard", "message", name);

    }

    @RequestMapping("friendFinder")

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

    private class FacebookConnection {
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
            out = "";
            id = fbProfileData.get("id");
            out = fbProfileData.get("name");
            email = fbProfileData.get("email");
            return this;
        }
    }
}
