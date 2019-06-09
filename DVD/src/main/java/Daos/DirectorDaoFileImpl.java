/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Daos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import dtos.Director;
import dtos.Director;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;


public class DirectorDaoFileImpl implements DirectorDao {

    private Map<Integer, Director> directors;

    @Autowired
    
    
    public DirectorDaoFileImpl() {
        this.directors = new HashMap<>();
    }
    
    //public static final class RoomMapper implements RowMapper<Direct

    @Override
    public Director Create(Director entity) {
        int id = 0;
        if (directors.size() > 0) {
            id = this.directors.keySet().stream().max(Comparator.reverseOrder()).get();
        }
        id++;
        entity.setId(id);
        directors.put(id, entity);
        return entity;
    }

    @Override
    public List<Director> ReadAll() {
        return new ArrayList(directors.values());
    }

    @Override
    public Director ReadById(int id) {
        if (directors.containsKey(id)) {
            return directors.get(id);
        }
        return null;
    }

    @Override
    public void Update(int id, Director entity) {
        if (directors.containsKey(id)) {
            directors.put(id, entity);
        }
    }

    @Override
    public void Delete(int id) {
        if (directors.containsKey(id)) {
            directors.remove(id);
        }
    }


    
}