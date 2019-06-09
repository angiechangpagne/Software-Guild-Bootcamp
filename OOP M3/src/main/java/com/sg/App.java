/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg;

import com.sg.controller.Controller;
import com.sg.dao.Dao;
import com.sg.dao.DaoFileImpl;
import com.sg.service.Service;
import com.sg.service.ServiceFileImpl;
import com.sg.view.UserIO;
import com.sg.view.UserIOConsoleImpl;
import com.sg.view.View;

/**
 *
 * This app class uses dependency injection, coding against the interface and primitive flexibility
 */
public class App {
    public static void main(String[] args) {
        Dao dao = new DaoFileImpl();
        Service service = new ServiceFileImpl(dao);
        // user new Scanner(System.in)
        UserIO io = new UserIOConsoleImpl();
        View view = new View(io);
        Controller controller = new Controller(service,view);
        controller.run();
    }
}
