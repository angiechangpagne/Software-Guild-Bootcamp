/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import Daos.DirectorDao;
import Daos.DirectorDaoFileImpl;
import Daos.DvdDao;
import Daos.DvdDaoFileImpl;
import dtos.Dvd;
import services.DirectorNotFoundException;
import services.DvdService;
import services.DvdServiceImpl;

@RestController
@RequestMapping("/api")
public class RESTDvdController {
    private final DvdService service;

    public RESTDvdController(DvdService service) {
        this.service = service;
    }

    // GET, PUT, POST, DELETE - HTTP
    // READ, Update, Create, Delete - DAO
    // Select, Update, Insert, Delete - SQL
    
    @RequestMapping(value="/dvds", method=GET)
    public List<Dvd> readAll() {
        
        return service.ReadAllDvds();
    }
    
    //localhost:8080/api/dvd?id=99
    @RequestMapping(value="/dvd", method=GET)
    public ResponseEntity<Dvd> readById(@RequestParam(value="id") int id) {
        Dvd d = service.ReadDvdById(id);
        if(d == null){
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(d);
    }
    
    //@RequestMapping(value="/dvds", method=POST)
    @PostMapping("/dvds")
    @ResponseStatus(HttpStatus.CREATED) // 201
    //204 - NOCONTENT
    public Dvd create(@RequestBody Dvd dvd) throws DirectorNotFoundException
    {
        return service.CreateDvd(dvd);
    }
    
    //localhost:8080/api/dvd/99
    @PutMapping("/dvd/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void update(@PathVariable int id,@RequestBody Dvd dvd) throws DirectorNotFoundException{
        service.UpdateDvd(id, dvd);
    }
    
    @DeleteMapping("/dvd/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    public void delete(@PathVariable int id){
        service.DeleteDvd(id);
    }
}