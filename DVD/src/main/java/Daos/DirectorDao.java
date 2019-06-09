/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daos;

import dtos.Director;
import java.util.List;


public interface DirectorDao {

    Director Create(Director entity);

    void Delete(int id);

    List<Director> ReadAll();

    Director ReadById(int id);

    void Update(int id, Director entity);
    
}
