/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daos;


import java.util.List;
import dtos.Dvd;

public interface DvdDao {

    Dvd Create(Dvd entity);

    void Delete(int id);

    List<Dvd> ReadAll();
    
    List<Dvd> ReadByDirectorId(int directorId);

    Dvd ReadById(int id);

    void Update(int id, Dvd entity);
    
}