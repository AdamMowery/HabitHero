package com.test.controller;

import com.test.models.TasksEntity;
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
public class AddTask extends getDBSession {

    @RequestMapping("addTask")

    public ModelAndView addTask(@RequestParam("task") String taskId, Model model, HttpSession session) {


        // Checks to see if the user is logged in
        if (!session.getAttribute("Array").equals(null)) {
            // gets the current session of the user information array
            ArrayList<String> info = (ArrayList<String>) session.getAttribute("Array");

            // searches the database to make sure the user doesn't duplicate a task
            Session selectTask = getSession();
            Criteria c = selectTask.createCriteria(TasksEntity.class);
            c.add(Restrictions.eq("userId", info.get(1)));
            c.add(Restrictions.eq("taskId", taskId));
            ArrayList<TasksEntity> taskList = (ArrayList<TasksEntity>) c.list();
            selectTask.close();

            // add task to database
            if (taskList.size() == 0 && !taskId.equals("")) {
                Session s = getSession();
                TasksEntity newTask = new TasksEntity();
                newTask.setUserId(info.get(1));
                newTask.setTaskId(taskId);
                s.save(newTask);
                s.getTransaction().commit();
                s.close();
            }


            // gets the updated task list
            Session selectAllTask = getSession();
            Criteria t = selectAllTask.createCriteria(TasksEntity.class);
            t.add(Restrictions.eq("userId", info.get(1)));
            t.add(Restrictions.eq("completed", 0));
            ArrayList<TasksEntity> newtaskList = (ArrayList<TasksEntity>) t.list();
            selectAllTask.close();

            // adding a second model view
            model.addAttribute("tasks", newtaskList);

            return new
                    ModelAndView("habits", "message", "Your id: " + info.get(1));

        } else {
            return new
                    ModelAndView("/", "message", "please login");
        }
    }
}
