package com.test.controller;

import com.test.models.TasksEntity;
import com.test.models.UsernamesEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * Created by adamm on 3/22/2017.
 */
@Controller
public class Habits extends getDBSession {

    @RequestMapping("habits")

    public ModelAndView habits(Model model, HttpSession session) {

        // Checks to see if the user is logged in
        if (!session.getAttribute("Array").equals(null)) {

            // gets the current session of the user information array
            ArrayList<String> info = (ArrayList<String>) session.getAttribute("Array");

            //gets all the information on the user
            Session selectUsers = getSession();
            Criteria c = selectUsers.createCriteria(UsernamesEntity.class);
            c.add(Restrictions.eq("userId", info.get(1)));
            ArrayList<UsernamesEntity> userList = (ArrayList<UsernamesEntity>) c.list();
            selectUsers.close();

            // gets a list of the tasks to display
            Session selectTask = getSession();
            Criteria t = selectTask.createCriteria(TasksEntity.class);
            t.add(Restrictions.eq("userId", info.get(1)));
            t.add(Restrictions.eq("completed", 0));
            ArrayList<TasksEntity> taskList = (ArrayList<TasksEntity>) t.list();
            selectTask.close();

            // adds another model view
            model.addAttribute("tasks", taskList);

            return new
                    ModelAndView("habits", "message", "your id: " + info.get(1) + " your points: " + userList.get(0).getPoints());

        } else {
            return new
                    ModelAndView("/", "message", "please login");
        }
    }
}
