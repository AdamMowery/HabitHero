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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

/**
 * Created by davidshinabarger on 3/15/17.
 */
public class AddFriend {
    ArrayList<String> info = new ArrayList<String>();

    public AddFriend(ArrayList<String> info) {
        this.info = info;
    }
    @RequestMapping("addFriend")

    public ModelAndView addTask(@RequestParam("addFriendship") String taskId, Model model) {
        String userId = info.get(1);
        String code = info.get(0);
        if (code == null || code.equals("")) {
            throw new RuntimeException(
                    "ERROR:Didn't get code parameter in callback.");
        }

        Criteria c = tasks();
        c.add(Restrictions.like("userID", "%" + userId + "%"));
        c.add(Restrictions.like("friendID", "%" + taskId + "%"));
        ArrayList<TasksEntity> taskList = (ArrayList<TasksEntity>) c.list();

// add task to database if not already there ******
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
        model.addAttribute("addFriend", taskList);

        //what is the model object?
        return new
                ModelAndView("addFriend", "message", "Your id: " + userId);
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
}
