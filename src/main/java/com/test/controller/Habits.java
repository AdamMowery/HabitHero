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
public class Habits extends getDBSession{
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
            Session s = getSession();
            UsernamesEntity newuser = new UsernamesEntity();
            newuser.setUserId(info.get(1));
            newuser.setFullname(info.get(2));
            newuser.setEmail(info.get(3));
            newuser.setPoints(0);
            s.save(newuser);
            s.getTransaction().commit();
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
}
