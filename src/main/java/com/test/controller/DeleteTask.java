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
public class DeleteTask extends getDBSession {


    @RequestMapping("deleteTask")

    public ModelAndView deleteTask(@RequestParam("taskId") String id, Model model, HttpSession session) {

        // Checks to see if the user is logged in
        if (!session.getAttribute("Array").equals(null)) {
        // gets the current session of the user information array
        ArrayList<String> info = (ArrayList<String>) session.getAttribute("Array");

        //deletes the task from the database
        Session s = getSession();
        TasksEntity temp = new TasksEntity();
        temp.setTaskId(id);
        temp.setUserId(info.get(1));
        s.delete(temp);
        s.getTransaction().commit();
        s.close();

        //gets the updated task list to display
        Session selectTask = getSession();
        Criteria t = selectTask.createCriteria(TasksEntity.class);
        t.add(Restrictions.eq("userId", info.get(1)));
        t.add(Restrictions.eq("completed", 0));
        ArrayList<TasksEntity> taskList = (ArrayList<TasksEntity>) t.list();
        selectTask.close();
        model.addAttribute("tasks", taskList);

        return new
                ModelAndView("habits", "message", "Your id: " + info.get(1));

        } else {
            return new
                    ModelAndView("/", "message", "please login");
        }

    }

}
