/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.advice;

import com.sg.model.VendableItem;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import org.aspectj.lang.JoinPoint;


public class LoggingAdvice {

    private static final String FILE_NAME = "errorLog.txt";
    
    public void createErrorEntry(JoinPoint joinPoint, Throwable error){
        Object[] args = joinPoint.getArgs();
        String auditEntry = "";
        for (Object currentArg : args) {
            if (currentArg instanceof String) {
                auditEntry += currentArg;
            } else if (currentArg instanceof VendableItem) {
                auditEntry += ((VendableItem) currentArg).getName();
            }
        }
        auditEntry += " : "+error.getClass().getName();
        writeEntry(auditEntry);
    }

    public void writeEntry(String entry) {
        // auto closeable
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME, true))) {
            LocalDateTime timestamp = LocalDateTime.now();
            out.println(timestamp.toString() + " : " + entry);
            out.flush();
        } catch (IOException e) {
            System.out.println("Could not persist error log information." + e.getMessage());
        }
    }
}
