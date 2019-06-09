/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg;

import com.sg.controller.Controller;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//dependency injection is eliminated, we use aop dependency beans instead
public class App {

    public static void main(String[] args) {
//        Dao dao = new DaoFileImpl();
//        Service service = new ServiceFileImpl(dao);
//        // user new Scanner(System.in)
//        UserIO io = new UserIOConsoleImpl();
//        View view = new View(io);
//        Controller controller = new Controller(service,view);
//        controller.run();
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Controller controller = context.getBean("controller", Controller.class);
        controller.run();
    }
}
