package com.anas.scoobergame.controller;

import com.anas.scoobergame.domain.pojo.Game;
import com.anas.scoobergame.dto.GameRequestObjectDTO;
import com.anas.scoobergame.dto.GameResponseDTO;
import com.anas.scoobergame.dto.MoveRequestObjectDTO;
import com.anas.scoobergame.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/game")
public class GameController {
    private static final Logger log = (Logger) LoggerFactory.getLogger(GameController.class);

    @Autowired
    GameService gameService;

    /**
     * endpoint for starting a new game, post call on api/v1/game
     * @param requestObjectDTO
     * @return GameResponseDTO
     */
    @PostMapping
    public ResponseEntity<GameResponseDTO> startNewGame(@RequestBody GameRequestObjectDTO requestObjectDTO) {
        return new ResponseEntity<>(gameService.startNewGame(requestObjectDTO), HttpStatus.OK);
    }

    /**
     * handle the player move by put request of api/v1/game
     * @param requestObjectDTO
     * @return GameResponse DTO
     */
    @PutMapping
    public ResponseEntity<GameResponseDTO> playerMove(@RequestBody MoveRequestObjectDTO requestObjectDTO) {
        return new ResponseEntity<>(gameService.handlePlayerMoveInGame(requestObjectDTO), HttpStatus.OK);
    }

    /**
     * fetching the game with id by api/v1/game/{id}
     * @param id
     * @return game
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Game> getGame(@PathVariable(name = "id") Integer id) {
        return new ResponseEntity<>(gameService.getGameById(id), HttpStatus.OK);
    }

    /**
     * exit the player from active game by put request api/v1/game/exit
     * @param requestObjectDTO
     * @return GameResponseDTO
     */
    @PutMapping(value = "/exit")
    public ResponseEntity<GameResponseDTO> exitFromGame(@RequestBody MoveRequestObjectDTO requestObjectDTO) {
        return new ResponseEntity<>(gameService.exitPlayerFromGameById(requestObjectDTO.getGameId(), requestObjectDTO.getPlayerId()), HttpStatus.OK);
    }

    /**
     * fetch all active game by get api/v1/game/all
     * @return list of active games
     */
    @GetMapping(value = "/all/active")
    public ResponseEntity<List<Game>> getAllActiveGames() {
        return new ResponseEntity<>(gameService.findAllActiveGames(), HttpStatus.OK);
    }

    /**
     * end point to join the active game by put call at api/v1/game/join
     * @param moveRequestObjectDTO
     * @return GameResponseDTO
     */
    @PutMapping(value = "/join")
    public ResponseEntity<GameResponseDTO> joinActiveGame(@RequestBody MoveRequestObjectDTO moveRequestObjectDTO) {
        return new ResponseEntity<>(gameService.joinActiveGameById(moveRequestObjectDTO.getGameId(), moveRequestObjectDTO.getPlayerId()), HttpStatus.OK);
    }

    /**
     * simply checking the health of application
     * @return string
     */
    @GetMapping(value = "/health")
    public ResponseEntity<String> checkHealth() {
        return new ResponseEntity<>("Game is running.", HttpStatus.OK);
    }

}
