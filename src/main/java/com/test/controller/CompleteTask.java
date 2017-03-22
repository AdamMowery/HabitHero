package com.test.controller;

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

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * Created by adamm on 3/22/2017.
 */
@Controller
public class CompleteTask extends getDBSession{
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

            Session s = getSession();
            s.update(userList.get(0));
            s.getTransaction().commit();
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

}
