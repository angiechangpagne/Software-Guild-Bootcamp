/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;


import dtos.Dvd;
public class DirectorNotFoundException extends Exception {

    private Dvd dvd;

    public DirectorNotFoundException(String message, Dvd dvd) {
        super(message);
        this.dvd = dvd;
    }

    public Dvd getDvd() {
        return dvd;
    }

    public void setDvd(Dvd dvd) {
        this.dvd = dvd;
    }

}