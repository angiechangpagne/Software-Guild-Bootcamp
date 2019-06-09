/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;



import java.util.List;
import dtos.Director;
import dtos.Dvd;

public interface DvdService {
    Director CreateDirector(String name);
    List<Director> ReadAllDirectors();
    Director ReadDirectorById(int directorId);
    void UpdateDirector(int directorId, Director director);
    void DeleteDirector(int directorId);
    
    Dvd CreateDvd(Dvd dvd) throws DirectorNotFoundException;
    List<Dvd> ReadAllDvds();
    Dvd ReadDvdById(int dvdId);
    List<Dvd> ReadDvdsByDirectorId(int directorId);
    void UpdateDvd(int directorId, Dvd dvd) throws DirectorNotFoundException;
    void DeleteDvd(int dvdId);
}