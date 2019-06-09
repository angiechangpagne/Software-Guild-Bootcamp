/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.controllers;

import com.mycompany.dtos.Game;
import com.mycompany.dtos.Round;
import com.mycompany.dtos.UserGuess;
import com.mycompany.services.GameNotFoundException;
import com.mycompany.services.GuessNumberService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author angela997
 */
@RestController
@RequestMapping("/api")
public class RESTGuessNumberController {

    private final GuessNumberService s1;

    public RESTGuessNumberController(GuessNumberService s1) {
        this.s1 = s1;
    }

    // GET, PUT, POST, DELETE - HTTP
    // READ, Update, Create, Delete - DAO
    // Select, Update, Insert, Delete -SQL
    //begin-post, guess-post, game-get, game-get 1 by id, rounds by 1 game Get
    //begin a game
    //@RequestMapping(value = "/games", method = POST)
    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED) //201
    //204 NOCONTENT, 201 created success
    public int create() throws GameNotFoundException {
        return (s1.beginGame()).getGameId();
    }

   // @RequestMapping(value = "/games", method = POST)
    @PostMapping("/guess")
    //@ResponseStatus(HttpStatus.CREATED)//201
    public String guess(@RequestBody UserGuess u1) throws GameNotFoundException {
        return s1.takeAGuess(u1.getGameId(), u1.getGuess());
    }

    @RequestMapping(value = "/game", method = GET)
    public List<Game> readAll() {
        return s1.getAllGames();
    }

    @RequestMapping(value = "/game/{gameId}", method = GET)
    public ResponseEntity<Game> getGameById(@PathVariable(value = "gameId") int id) {
        Game g1 = s1.getGameById(id);
        if (g1 == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(g1);
    }

    @RequestMapping(value = "/rounds/{gameId}", method = GET)
    public ResponseEntity<List<Round>> getRoundByGameId(@PathVariable(value = "gameId") int id) {
        List<Round> lr = s1.getGameById(id).getRounds();
        if (s1.getGameById(id) == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        } else if (lr.size() == 0 || lr == null) {
            return new ResponseEntity(null, HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(lr);
    }

    //@RequestMapping(value="guess",method=POST)
    //public 
}
