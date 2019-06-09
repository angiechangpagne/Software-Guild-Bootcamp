/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dao;

/**
 *
 * angiechangpagne
 */
public class PersistanceException extends Exception{

    public PersistanceException() {
    }

    public PersistanceException(String message) {
        super(message);
    }

    public PersistanceException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersistanceException(Throwable cause) {
        super(cause);
    }

    public PersistanceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
