package com.test.controller;

import com.test.models.TasksEntity;
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
public class AddTask extends getDBSession {
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
            Session s = getSession();
            TasksEntity newTask = new TasksEntity();
            newTask.setUserId(userId);
            newTask.setTaskId(taskId);
            s.save(newTask);
            s.getTransaction().commit();
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
}
