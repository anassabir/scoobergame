package com.anas.scoobergame.controller;

import com.anas.scoobergame.domain.pojo.Player;
import com.anas.scoobergame.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/player")
public class PlayerController {

    @Autowired
    PlayerService playerService;

    /**
     * add new player by post request of api/v1/player
     * @param reqPlayer
     * @return player
     */
    @PostMapping
    public ResponseEntity<Player> addNewPlayer(@RequestBody Player reqPlayer) {
        return new ResponseEntity<>(playerService.addNewPlayer(reqPlayer), HttpStatus.OK);
    }

    /**
     * fetch all the players by api/v1/player/all
     * @return list of players
     */
    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<List<Player>> getAllPlayers() {
        return new ResponseEntity<>(playerService.getAllPlayers(), HttpStatus.OK);
    }

    /**
     * get the specific player by id, api/v1/player/{id}
     * @param id
     * @return player
     */
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Player> findById(@PathVariable(name = "id") Integer id) {
        return new ResponseEntity<>(playerService.getPlayerById(id), HttpStatus.OK);
    }

}
