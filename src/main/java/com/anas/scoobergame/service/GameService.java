package com.anas.scoobergame.service;

import com.anas.scoobergame.domain.pojo.Game;
import com.anas.scoobergame.domain.pojo.Move;
import com.anas.scoobergame.domain.pojo.Player;
import com.anas.scoobergame.domain.repository.GameRepository;
import com.anas.scoobergame.dto.GameRequestObjectDTO;
import com.anas.scoobergame.dto.GameResponseDTO;
import com.anas.scoobergame.dto.MoveRequestObjectDTO;
import com.anas.scoobergame.dto.MoveRspSingleDTO;
import com.anas.scoobergame.enums.GameStatus;
import com.anas.scoobergame.enums.PlayerKind;
import com.anas.scoobergame.exception.RestInternalException;
import com.anas.scoobergame.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class GameService {
    private static final Logger log = (Logger) LoggerFactory.getLogger(GameService.class);

    @Autowired
    GameRepository gameRepository;

    @Autowired
    PlayerService playerService;

    @Autowired
    MoveService moveService;

    /**
     * start a new game
     * @param requestObjectDTO
     * @return GameResponseDTO (includes the current game status as well as moves list)
     */
    public GameResponseDTO startNewGame(GameRequestObjectDTO requestObjectDTO) {
        List<Player> playerList = playerService.createNewPlayerOnStartOfGame(requestObjectDTO.getGameType(), requestObjectDTO.getPlayer1Name(), requestObjectDTO.getPlayer2Name());
        Player player1 = playerList.get(0);
        Player player2 = playerList.get(1);
        Integer currentNumber = requestObjectDTO.getInitialNumber() != null ? requestObjectDTO.getInitialNumber() : CommonUtil.getRandomNumber();
        Game newGame = createOrUpdateNewGame(new Game(requestObjectDTO.getGameType(), player1, player2, player2, currentNumber, currentNumber, GameStatus.ACTIVE));
        if (player2.getPlayerKind().name().equals(PlayerKind.NPC.name())) {
            moveService.handlePlayerMove(newGame, newGame.getNextPlayer(), null);
        }
        return generateGameResponseDTO(newGame);
    }

    /**
     * handle player move in game, when human player launch a new move
     * @param requestObjectDTO
     * @return GameResponseDTO (includes the current game status as well as moves list)
     */
    public GameResponseDTO handlePlayerMoveInGame(MoveRequestObjectDTO requestObjectDTO) {
        Game currentGame =  getGameById(requestObjectDTO.getGameId());
        if (currentGame.getGameStatus().name().equals(GameStatus.ENDED.name())) {
            throw new RestInternalException("No move made", "-400", String.format("Game %d already ended with %s is the winner.", currentGame.getId(), currentGame.getNextPlayer().getName()));
        }
        Player currentPlayer = playerService.getPlayerById(requestObjectDTO.getPlayerId());
        moveService.handlePlayerMove(currentGame, currentPlayer, requestObjectDTO.getMoveNumber());
        return generateGameResponseDTO(currentGame);
    }

    /**
     * exit the player inside a game
     * @param gameId
     * @param playerId
     * @return GameResponseDTO (includes the current game status as well as moves list)
     */
    public GameResponseDTO exitPlayerFromGameById(Integer gameId, Integer playerId) {
        Game game = getGameById(gameId);
        if (game.getGameStatus().name().equals(GameStatus.ENDED.name())) {
            throw new RestInternalException("No move made", "-400", String.format("Game %d already ended with %s is the winner.", game.getId(), game.getNextPlayer().getName()));
        }
        Player newNpcPlayer = playerService.addNewPlayer(new Player("npc", PlayerKind.NPC));
        if (game.getFirstCurrentPlayer().getId() == playerId) {
            game.setFirstCurrentPlayer(newNpcPlayer);
        } else {
            game.setSecondCurrentPlayer(newNpcPlayer);
        }
        if (game.getNextPlayer().getId() == playerId) {
            game.setNextPlayer(newNpcPlayer);
        }
        Game newGame = createOrUpdateNewGame(game);
        if (newGame.getNextPlayer().getPlayerKind().name().equals(PlayerKind.NPC.name())) {
            moveService.handlePlayerMove(newGame, newGame.getNextPlayer(), null);
        }
        return generateGameResponseDTO(newGame);
    }

    /**
     * join the active game, one human playing with npc
     * @param gameId
     * @param playerId
     * @return GameResponseDTO (includes the current game status as well as moves list)
     */
    public GameResponseDTO joinActiveGameById(Integer gameId, Integer playerId) {
        Game game = getGameById(gameId);
        if (game.getGameStatus().name().equals(GameStatus.ENDED.name())) {
            throw new RestInternalException("No move made", "-400", String.format("Game %d already ended with %s is the winner.", game.getId(), game.getNextPlayer().getName()));
        }
        Player newPlayer = playerService.getPlayerById(playerId);
        if (game.getFirstCurrentPlayer().getPlayerKind().name().equals(PlayerKind.NPC.name())) {
            game.setFirstCurrentPlayer(newPlayer);
        } else {
            game.setSecondCurrentPlayer(newPlayer);
        }
        if (game.getNextPlayer().getPlayerKind().name().equals(PlayerKind.NPC.name())) {
            game.setNextPlayer(newPlayer);
        }
        Game newGame = createOrUpdateNewGame(game);
        return generateGameResponseDTO(newGame);
    }


    /**
     * fetch all active games
     * @return list of all active games
     */
    public List<Game> findAllActiveGames() {
        try {
            return gameRepository.findAllByGameStatusOrderByLastModifiedDateDesc(GameStatus.ACTIVE);
        } catch (Exception e) {
            throw new RestInternalException("Internal Error", "-500", e.getMessage());
        }
    }

    /**
     * createOrUpdate a game
     * @param reqGame
     * @return game
     */
    @Transactional
    public Game createOrUpdateNewGame(Game reqGame) {
        try {
            return gameRepository.saveAndFlush(reqGame);
        } catch (Exception e) {
            throw new RestInternalException("Internal Error", "-500", e.getMessage());
        }
    }

    /**
     * fectch game by id
     * @param id
     * @return
     */
    public Game getGameById(Integer id) {
        try {
            return gameRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new RestInternalException(String.format("Game %d does not exists.", id), "-400", e.getMessage());
        }
    }

    /**
     * method to generate a generic response for a move
     * @param game
     * @return GameResponseDTO (includes the current game status as well as moves list)
     */
    public GameResponseDTO generateGameResponseDTO(Game game) {
        GameResponseDTO responseDTO = new GameResponseDTO();
        Game newGame = getGameById(game.getId());
        responseDTO.setGameId(newGame.getId());
        responseDTO.setPlayer1(String.format("%d:%s", newGame.getFirstCurrentPlayer().getId(),newGame.getFirstCurrentPlayer().getName()));
        responseDTO.setPlayer2(String.format("%d:%s", newGame.getSecondCurrentPlayer().getId(),newGame.getSecondCurrentPlayer().getName()));
        responseDTO.setOriginalNumber(newGame.getOriginalNumber());
        responseDTO.setCurrentNumber(newGame.getCurrentNumber());
        List<MoveRspSingleDTO> moveRspSingleDTOList = new ArrayList<>();
        List<Move> moveList = moveService.getAllMovesByGameIdOderByDateDesc(game.getId());
        moveList.stream().forEach(x -> moveRspSingleDTOList.add(new MoveRspSingleDTO(x.getDescription())));
        responseDTO.setMoveRspSingleDTOList(moveRspSingleDTOList);
        return responseDTO;
    }

}
