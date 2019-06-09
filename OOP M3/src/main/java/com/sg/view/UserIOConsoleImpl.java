/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.view;

import java.util.Scanner;


public class UserIOConsoleImpl implements UserIO {

    private Scanner sc;

    /**
     * Uses System.in to get user input *
     */
    public UserIOConsoleImpl() {
        sc = new Scanner(System.in);
    }

    /**
     * wrapper for System.out.print(String) *
     */
    @Override
    public void print(String message) {
        System.out.print(message);
    }

    /**
     * wrapper for System.out.println(String) *
     */
    @Override
    public void println(String message) {
        System.out.println(message);
    }

    /**
     * @param prompt the value to print before getting user input
     * @return -1 if user did not enter a valid integer otherwise we return the
     * integer entered*
     */
    @Override
    public int readInt(String prompt) {
        print(prompt);
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * @param prompt the value to print before getting user input
     * @param min the minimum valid integer (inclusive)
     * @param max the maximum valid integer (inclusive)
     * @return -1 if user did not enter a valid integer or the user entered a
     * integer that was not between min and max otherwise we return the integer
     * entered*
     */
    @Override
    public int readInt(String prompt, int min, int max) {
        print(prompt);
        try {
            int val = Integer.parseInt(sc.nextLine());
            if (val >= min && val <= max) {
                return val;
            }
        } catch (NumberFormatException e) {
            // do nothing
        }
        return -1;
    }

    /**
     * @param prompt the value to print before getting user input
     * @return the user input from the console
     */
    @Override
    public String readString(String prompt) {
        print(prompt);
        return sc.nextLine();
    }

    @Override
    public double readDouble(String prompt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float readFloat(String prompt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long readLong(String prompt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
