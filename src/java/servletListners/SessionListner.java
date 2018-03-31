/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servletListners;

import entities.User;
import entities.UserType;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import utility.Utils;

/**
 *
 * @author sukhvir
 */
@WebListener
public class SessionListner implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

        HttpSession session = se.getSession();
        if ((boolean) session.getAttribute("accept")) {
            User loggedUser = (User) session.getAttribute(((UserType) session.getAttribute("type")).toString());
            System.out.println(loggedUser.getUserType().getCount());
            loggedUser.getUserType()
                    .decrementCount();
        }

    }

}
