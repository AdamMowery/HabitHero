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

    @RequestMapping("habits")
/*Displays after the user clicks on the Habits button*/
    public ModelAndView habits(Model model, HttpSession session) {
        ArrayList<String> info = (ArrayList<String>) session.getAttribute("Array");

// Pulls the code for the user
        String code = info.get(0);

        if (code == null || code.equals("")) {
            throw new RuntimeException(
                    "ERROR:Didn't get code parameter in callback.");
        }

//    ********* array list of users**************
// userNamelist creates connection with the database
        Session selectUsers = getSession();
        Criteria c = selectUsers.createCriteria(UsernamesEntity.class);
        c.add(Restrictions.eq("userId", info.get(1)));
        ArrayList<UsernamesEntity> userList = (ArrayList<UsernamesEntity>) c.list();
        selectUsers.close();
//  *********** add user to database if not already there ******
//
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
//   ******* Displays Table of tasks *********
// the tasks method gets the tasks table and displays as an array
        Session selectTask = getSession();
        Criteria t = selectTask.createCriteria(TasksEntity.class);
        t.add(Restrictions.eq("userId", info.get(1)));
        t.add(Restrictions.eq("completed", 0));
        ArrayList<TasksEntity> taskList = (ArrayList<TasksEntity>) t.list();
        selectTask.close();

        // adds the taskList attribute to the model tasks
        model.addAttribute("tasks", taskList);


        if (userList.size() == 1) {

            return new
                    ModelAndView("habits", "message", "your id: " + info.get(1) + " your points: " + userList.get(0).getPoints());
        }
        return new
                ModelAndView("habits", "message", "your id: " + info.get(1) + " your points: 0");

    }

    @RequestMapping("addTask")

    public ModelAndView addTask(@RequestParam("task") String taskId, Model model, HttpSession session) {
        ArrayList<String> info = (ArrayList<String>) session.getAttribute("Array");
        String userId = info.get(1);
        String code = info.get(0);
        if (code == null || code.equals("")) {
            throw new RuntimeException(
                    "ERROR:Didn't get code parameter in callback.");
        }
        // searches the database where userId and taskId match the user input.
        //adds to c where user id and task id match user input
        //adds to arraylist all elements in the c list
        Session selectTask = getSession();
        Criteria c = selectTask.createCriteria(TasksEntity.class);
        c.add(Restrictions.eq("userId", userId));
        c.add(Restrictions.eq("taskId", taskId));
        ArrayList<TasksEntity> taskList = (ArrayList<TasksEntity>) c.list();
        selectTask.close();

//  *********** add task to database if not already there and not a empty string ******
        if (taskList.size() == 0 && !taskId.equals("")) {
            Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
            SessionFactory sessionFact = cfg.buildSessionFactory();
            Session s = sessionFact.openSession();
            Transaction tx = s.beginTransaction();
            TasksEntity newTask = new TasksEntity();
            newTask.setUserId(userId);
            newTask.setTaskId(taskId);
            s.save(newTask);
            tx.commit();
            s.close();
        }
        //displays updated task list
        Session selectAllTask = getSession();
        Criteria t = selectAllTask.createCriteria(TasksEntity.class);
        t.add(Restrictions.eq("userId", info.get(1)));
        t.add(Restrictions.eq("completed", 0));
        ArrayList<TasksEntity> newtaskList = (ArrayList<TasksEntity>) t.list();
        selectAllTask.close();
        model.addAttribute("tasks", newtaskList);
        return new
                ModelAndView("habits", "message", "Your id: " + userId);
    }

    @RequestMapping("deleteTask")
    /*
    Deletes a tasks. No points awarded to user
     */
    public ModelAndView deleteTask(@RequestParam("taskId") String id, Model model, HttpSession session) {
// gets Http session. What is the "Array"?
        ArrayList<String> info = (ArrayList<String>) session.getAttribute("Array");
// gets  the info at index 0 which is the userId
        String userId = info.get(1);
//returns the code which stored at index 0 of the info arrray
        String code = info.get(0);
        if (code == null || code.equals("")) {
            throw new RuntimeException(
                    "ERROR:Didn't get code parameter in callback.");
        }
        // temp will store info for the object that we want to delete

        //makes a TasksEntity object called temp
        TasksEntity temp = new TasksEntity();
        // sets task id and user id to variables passed in to method
        temp.setTaskId(id);
        temp.setUserId(userId);

        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");

        SessionFactory fact = cfg.buildSessionFactory();

        Session tasks = fact.openSession();

        tasks.beginTransaction();
//the tasks.delete deletes the task where the the object matches the temp object
        tasks.delete(temp);

        tasks.getTransaction().commit();

        tasks.close();

        Session selectTask = getSession();
        Criteria t = selectTask.createCriteria(TasksEntity.class);
        t.add(Restrictions.eq("userId", info.get(1)));
        t.add(Restrictions.eq("completed", 0));
        ArrayList<TasksEntity> taskList = (ArrayList<TasksEntity>) t.list();
        selectTask.close();
        model.addAttribute("tasks", taskList);

        return new
                ModelAndView("habits", "message", "Your id: " + userId);

    }

    @RequestMapping("complete")
    public ModelAndView completedTask(@RequestParam("taskId") String id, Model model, HttpSession session) {

        ArrayList<String> info = (ArrayList<String>) session.getAttribute("Array");

        Session selectTask = getSession();
        Criteria t = selectTask.createCriteria(TasksEntity.class);
        t.add(Restrictions.eq("userId", info.get(1)));
        t.add(Restrictions.eq("taskId", id));
        ArrayList<TasksEntity> completedTask = (ArrayList<TasksEntity>) t.list();
        selectTask.close();

        Session selectUsers = getSession();
        Criteria c = selectUsers.createCriteria(UsernamesEntity.class);
        c.add(Restrictions.eq("userId", info.get(1)));
        ArrayList<UsernamesEntity> userList = (ArrayList<UsernamesEntity>) c.list();
        selectUsers.close();

        if (completedTask.size() == 1) {
            completedTask.get(0).setCompleted(1);
            int points = userList.get(0).getPoints();
            userList.get(0).setPoints(points + 5);

            Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
            SessionFactory sessionFact = cfg.buildSessionFactory();
            Session s = sessionFact.openSession();
            Transaction tx = s.beginTransaction();
            s.update(userList.get(0));
            tx.commit();
            s.close();

            Configuration cf = new Configuration().configure("hibernate.cfg.xml");
            SessionFactory sessionFac = cf.buildSessionFactory();
            Session sessio = sessionFac.openSession();
            Transaction tc = sessio.beginTransaction();
            sessio.update(completedTask.get(0));
            tc.commit();
            sessio.close();
        }

        Session selectAllTask = getSession();
        Criteria u = selectAllTask.createCriteria(TasksEntity.class);
        u.add(Restrictions.eq("userId", info.get(1)));
        u.add(Restrictions.eq("completed", 0));
        ArrayList<TasksEntity> unfinishedTasks = (ArrayList<TasksEntity>) u.list();
        selectAllTask.close();
        model.addAttribute("tasks", unfinishedTasks);

        return new
                ModelAndView("habits", "message", "your id: " + info.get(1) + " your points: " + userList.get(0).getPoints());

    }



    private Session getSession() {
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFact = cfg.buildSessionFactory();
        Session selectUsers = sessionFact.openSession();
        selectUsers.beginTransaction();
        return selectUsers;
    }



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
