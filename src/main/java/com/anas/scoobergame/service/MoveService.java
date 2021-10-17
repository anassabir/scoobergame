package com.anas.scoobergame.service;

import com.anas.scoobergame.domain.pojo.Game;
import com.anas.scoobergame.domain.pojo.Move;
import com.anas.scoobergame.domain.pojo.Player;
import com.anas.scoobergame.domain.repository.MoveRepository;
import com.anas.scoobergame.enums.GameStatus;
import com.anas.scoobergame.enums.PlayerKind;
import com.anas.scoobergame.exception.RestInternalException;
import com.anas.scoobergame.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MoveService {
    private static final Logger log = (Logger) LoggerFactory.getLogger(MoveService.class);

    @Autowired
    MoveRepository moveRepository;

    @Autowired
    GameService gameService;

    /**
     * handles player move, check for the move number, player type if npc or human
     * as well as check for the winner
     * @param game
     * @param player
     * @param moveNumber
     *
     */
    public void handlePlayerMove(Game game, Player player, Integer moveNumber) {
        if (game.getNextPlayer().getId() != player.getId()) {
            throw new RestInternalException("Invalid turn.", "-403", String.format("It is not Player %d turn", player.getId()));
        }
        Integer currentNumber = game.getCurrentNumber();
        Integer newNumber = 0;
        String moveDesc = "";
        if (currentNumber >= 1) {
            // in case of NPC
            if (moveNumber == null) {
                moveNumber = CommonUtil.getRandomMoveNumber();
                log.info(String.valueOf((currentNumber + moveNumber % 3)));
                while (true) {
                    if ((currentNumber + moveNumber) % 3 == 0)
                        break;
                    moveNumber = CommonUtil.getRandomMoveNumber();
                }
            }
            if ((currentNumber + moveNumber) % 3 == 0) {
                newNumber = (currentNumber + moveNumber) / 3;
                moveDesc = String.format("Move number: %d, Player %s, %s Move: %d + %d / 3 = %d",
                        moveNumber, player.getName(), player.getPlayerKind().name(), currentNumber, moveNumber, newNumber);
            } else {
                throw new RestInternalException("Invalid choice number", "-400", String.format("Move: %d + %d not divisible by 3", moveNumber, currentNumber));
            }
        }
        game.setCurrentNumber(newNumber);
        if (game.getFirstCurrentPlayer().getId() == player.getId()) {
            game.setNextPlayer(game.getSecondCurrentPlayer());
        } else {
            game.setNextPlayer(game.getFirstCurrentPlayer());
        }
        Game newGame = gameService.createOrUpdateNewGame(game);
        Move move = createNewMove(new Move(newGame, moveDesc));
        // announce the winner
        if (newGame.getCurrentNumber() == 1) {
            moveDesc = String.format("Player %s, %s is the winner.",
                    player.getName(), player.getPlayerKind().name());
            newGame.setNextPlayer(player);
            newGame.setGameStatus(GameStatus.ENDED);
            move.setGame(newGame);
            move.setDescription(moveDesc);
//            move.setDescription(moveDescBuilder.toString());
            Move newMove = createNewMove(move);
            return;
        }
//        Move move = new Move(game, moveDesc);
        // check if next player is NPC, make a move
        Player nextPlayer = newGame.getNextPlayer();
        if (nextPlayer.getPlayerKind().name().equals(PlayerKind.NPC.name())) {
            handlePlayerMove(newGame, nextPlayer, null);
        }
    }

    /**
     * create new move in database
     * @param reqMov
     * @return new created move
     */
    @Transactional
    public Move createNewMove(Move reqMov) {
        try {
            return moveRepository.saveAndFlush(reqMov);
        } catch (Exception e) {
            throw new RestInternalException("Internal Error", "-500", e.getMessage());
        }
    }

    /**
     * fetches all the move in descending order of current game
     * @param gameId
     * @return list of all the moves
     */
    public List<Move> getAllMovesByGameIdOderByDateDesc(Integer gameId) {
        try {
            return moveRepository.findByGameIdOrderByLastModifiedDateDesc(gameId);
        } catch (NoSuchElementException e) {
            throw new RestInternalException(String.format("Game %d not found.", gameId), "-400", e.getMessage());
        }
    }
}
