package com.test.controller;

import com.test.models.TasksEntity;
import com.test.models.UsernamesEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
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
public class CompleteTask extends getDBSession {


    @RequestMapping("complete")

    public ModelAndView completedTask(@RequestParam("taskId") String id, Model model, HttpSession session) {

        // Checks to see if the user is logged in
        if (!session.getAttribute("Array").equals(null)) {
            // gets the current session of the user information array
            ArrayList<String> info = (ArrayList<String>) session.getAttribute("Array");

            //database search for the task the user has finished
            Session selectTask = getSession();
            Criteria t = selectTask.createCriteria(TasksEntity.class);
            t.add(Restrictions.eq("userId", info.get(1)));
            t.add(Restrictions.eq("taskId", id));
            ArrayList<TasksEntity> completedTask = (ArrayList<TasksEntity>) t.list();
            selectTask.close();

            //pulls up the users information from the database
            Session selectUsers = getSession();
            Criteria c = selectUsers.createCriteria(UsernamesEntity.class);
            c.add(Restrictions.eq("userId", info.get(1)));
            ArrayList<UsernamesEntity> userList = (ArrayList<UsernamesEntity>) c.list();
            selectUsers.close();

            // adds points to the users profile and changes the task to completed
            if (completedTask.size() == 1) {
                completedTask.get(0).setCompleted(1);
                int points = userList.get(0).getPoints();
                userList.get(0).setPoints(points + 5);

                Session s = getSession();
                s.update(userList.get(0));
                s.getTransaction().commit();
                s.close();

                Session p = getSession();
                p.update(completedTask.get(0));
                p.getTransaction().commit();
                p.close();
            }

            // gets the new list of uncompleted tasks that the user has
            Session selectAllTask = getSession();
            Criteria u = selectAllTask.createCriteria(TasksEntity.class);
            u.add(Restrictions.eq("userId", info.get(1)));
            u.add(Restrictions.eq("completed", 0));
            ArrayList<TasksEntity> unfinishedTasks = (ArrayList<TasksEntity>) u.list();
            selectAllTask.close();
            model.addAttribute("tasks", unfinishedTasks);

            return new
                    ModelAndView("habits", "message", "your id: " + info.get(1) + " your points: " + userList.get(0).getPoints());

        } else {
            return new
                    ModelAndView("/", "message", "please login");
        }
    }

}
