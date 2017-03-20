package com.test.controller;

import com.test.models.MasterfriendsEntity;
import com.test.models.TasksEntity;
import com.test.models.UsernamesEntity;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpSession;
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


    @RequestMapping("welcome")
    public ModelAndView welcome(@RequestParam("code") String code) {

        FacebookConnection facebookConnection = new FacebookConnection(code);
        facebookConnection.invoke();
        info.add(code);
        info.add(facebookConnection.getId());
        info.add(facebookConnection.getOut());
        info.add(facebookConnection.getEmail());

        return new
                ModelAndView("welcome", "message", "");
    }

    @RequestMapping("habits")

    public ModelAndView welcome(Model model, HttpSession session) {

        if (session.getAttribute("counter") == null) {
            session.setAttribute("counter", info);
        }

        info = (ArrayList<String>) session.getAttribute("counter");


        session.setAttribute("Array", info);


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
            Session s = sessionFact.openSession();
            Transaction tx = s.beginTransaction();
            UsernamesEntity newuser = new UsernamesEntity();
            newuser.setUserId(info.get(1));
            newuser.setFullname(info.get(2));
            newuser.setEmail(info.get(3));
            newuser.setPoints(0);
            s.save(newuser);
            tx.commit();
            s.close();
        }
//   ******* Table of tasks *********
        //tasks is  a methods to connect to the database
        Criteria t = tasks();
        t.add(Restrictions.eq("userId", info.get(1)));
        t.add(Restrictions.eq("completed", 0));
        ArrayList<TasksEntity> taskList = (ArrayList<TasksEntity>) t.list();
        model.addAttribute("tasks", taskList);



        return new
                ModelAndView("habits", "message", "your id: " + info.get(1) + " your points: " + userList.get(0).getPoints());

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
        t.add(Restrictions.eq("completed", 0));
        ArrayList<TasksEntity> newtaskList = (ArrayList<TasksEntity>) t.list();
        model.addAttribute("tasks", newtaskList);
        return new
                ModelAndView("habits", "message", "Your id: " + userId);
    }


    @RequestMapping("deleteTask")
    public ModelAndView deleteCustomer(@RequestParam("taskId") String id, Model model)
    {
        String userId = info.get(1);
        String code = info.get(0);
        if (code == null || code.equals("")) {
            throw new RuntimeException(
                    "ERROR:Didn't get code parameter in callback.");
        }
        // temp will store info for the object that we want to delete
        TasksEntity temp = new TasksEntity();
        temp.setTaskId(id);
        temp.setUserId(userId);

        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");

        SessionFactory fact = cfg.buildSessionFactory();

        Session tasks = fact.openSession();
       tasks.beginTransaction();

        tasks.delete(temp);// delete the object from the list

        tasks.getTransaction().commit();// delete the row from the database


        Criteria t = tasks();
        t.add(Restrictions.eq("userId", info.get(1)));
        t.add(Restrictions.eq("completed", 0));
        ArrayList<TasksEntity> taskList = (ArrayList<TasksEntity>) t.list();
        model.addAttribute("tasks", taskList);


        return new
                ModelAndView("habits","message","Your id: " + userId);

    }

    @RequestMapping("complete")
    public ModelAndView completedTask(@RequestParam("taskId") String id, Model model) {

        //
        Criteria t = tasks();
        t.add(Restrictions.eq("userId", info.get(1)));
        t.add(Restrictions.eq("taskId", id));
        ArrayList<TasksEntity> completedTask = (ArrayList<TasksEntity>) t.list();

        Criteria c = userNamelist();
        c.add(Restrictions.eq("userId", info.get(1)));
        ArrayList<UsernamesEntity> userList = (ArrayList<UsernamesEntity>) c.list();


        if (completedTask.size() == 1) {
            completedTask.get(0).setCompleted(1);
            int points = userList.get(0).getPoints();
            userList.get(0).setPoints(points + 5);

            Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
            SessionFactory sessionFact = cfg.buildSessionFactory();
            Session session = sessionFact.openSession();
            Transaction tx = session.beginTransaction();
            session.update(userList.get(0));
            tx.commit();
            session.close();

            Configuration cf = new Configuration().configure("hibernate.cfg.xml");
            SessionFactory sessionFac = cf.buildSessionFactory();
            Session sessio = sessionFac.openSession();
            Transaction tc = sessio.beginTransaction();
            sessio.update(completedTask.get(0));
            tc.commit();
            sessio.close();
        }


        Criteria u = tasks();
        u.add(Restrictions.eq("userId", info.get(1)));
        u.add(Restrictions.eq("completed", 0));
        ArrayList<TasksEntity> unfinishedTasks = (ArrayList<TasksEntity>) u.list();
        model.addAttribute("tasks", unfinishedTasks);

        return new
                ModelAndView("habits", "message", "your id: " + info.get(1) + " your poinst: " + userList.get(0).getPoints());

    }

    private Criteria userNamelist() {
        Session selectUsers = getSession();
        return selectUsers.createCriteria(UsernamesEntity.class);
    }

    private Session getSession() {
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFact = cfg.buildSessionFactory();
        Session selectUsers = sessionFact.openSession();
        selectUsers.beginTransaction();
        return selectUsers;
    }

    private Criteria tasks() {
        Session selectTask = getSession();
        return selectTask.createCriteria(TasksEntity.class);
    }

    private Criteria friends() {
        Session selectTask = getSession();
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
        f.add(Restrictions.eq("userId", info.get(1)));
        ArrayList<MasterfriendsEntity> friendsList = (ArrayList<MasterfriendsEntity>) f.list();
        ArrayList<String> userFriends = new ArrayList<>();
        //search for friends for a given userId
        for (int i = 0; i < friendsList.size(); i++) {
            userFriends.add(friendsList.get(i).getFriendId());
        }
        userFriends.add(info.get(1));
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

    //addfriends mapping
    //do something with add bar to actually add friends

    @RequestMapping("addfriends")
    public ModelAndView addFriendsPage() {
        Criteria f = friends();
        f.add(Restrictions.eq("userId", info.get(1)));
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

        return new
                ModelAndView("addFriends", "friends", userList);
    }

    @RequestMapping("addFriendButton")
    public ModelAndView addFriendButton(@RequestParam("userid") String id, Model model) {
        Criteria c = userNamelist();
        c.add(Restrictions.like("userId", "%" + id + "%"));
        ArrayList<UsernamesEntity> userList = (ArrayList<UsernamesEntity>) c.list();

        Criteria f = friends();
        f.add(Restrictions.like("userId", "%" + id + "%"));
        ArrayList<MasterfriendsEntity> friendsList = (ArrayList<MasterfriendsEntity>) f.list();
        if (friendsList.size() == 0 && userList.size() == 1) {
            if (!id.equals(info.get(1))) {
                Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
                SessionFactory sessionFact = cfg.buildSessionFactory();
                Session session = sessionFact.openSession();
                Transaction tx = session.beginTransaction();

                MasterfriendsEntity newfriend = new MasterfriendsEntity();
                newfriend.setUserId(info.get(1));
                newfriend.setFriendId(id);

                //this saves the object into the database, writes to DB, C in Crud for CREATE
                session.save(newfriend);
                tx.commit();
                session.close();
            }
        }


        //redisplays friends table with added friend
        Criteria g = friends();
        g.add(Restrictions.eq("userId", info.get(1)));
        ArrayList<MasterfriendsEntity> friendsList2 = (ArrayList<MasterfriendsEntity>) g.list();
        ArrayList<String> userFriends = new ArrayList<>();
        //search for friends for a given userId
        for (int i = 0; i < friendsList2.size(); i++) {
            userFriends.add(friendsList2.get(i).getFriendId());
        }
        ArrayList<UsernamesEntity> userList2 = new ArrayList<>();
        //get all of the user info(points, name, id , email)
        for (String token : userFriends) {
            Criteria d = userNamelist();
            d.add(Restrictions.like("userId", "%" + token + "%"));
            userList2.add((UsernamesEntity) d.list().get(0));
        }



        return new
                ModelAndView("addFriends", "friends", userList2);

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
