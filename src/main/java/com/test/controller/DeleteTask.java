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


        Session s = getSession();

        TasksEntity temp = new TasksEntity();
        temp.setTaskId(id);
        temp.setUserId(userId);
        s.delete(temp);
        s.getTransaction().commit();
        s.close();

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

}
