package com.anas.scoobergame.service;

import com.anas.scoobergame.domain.pojo.Player;
import com.anas.scoobergame.domain.repository.PlayerRepository;
import com.anas.scoobergame.enums.GameType;
import com.anas.scoobergame.enums.PlayerKind;
import com.anas.scoobergame.exception.RestInternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PlayerService {

    @Autowired
    PlayerRepository playerRepository;

    /**
     * add new player in db
     * @param requestPlayer
     * @return newly added player
     */
    @Transactional
    public Player addNewPlayer(Player requestPlayer) {
        try {
            return playerRepository.saveAndFlush(requestPlayer);
        } catch (Exception e) {
            throw new RestInternalException("Internal Error", "-500", e.getMessage());
        }
    }

    /**
     * fetch all players from db
     * @return list of players
     */
    public List<Player> getAllPlayers() {
        try {
            return playerRepository.findAll();
        } catch (Exception e) {
            throw new RestInternalException("Internal Error", "-500", e.getMessage());
        }
    }

    /**
     * fetch specific player by id
     * @param id
     * @return return the player
     */
    public Player getPlayerById(Integer id) {
        try {
            return playerRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new RestInternalException(String.format("Player %d does not exists.", id), "-400", e.getMessage());
        }
    }

    /**
     * creating new players on the basis of game type
     * @param gameType
     * @param player1Name
     * @param player2Name
     * @return returning the newly created players in list
     */
    public List<Player> createNewPlayerOnStartOfGame(GameType gameType, String player1Name, String player2Name) {
        Player player1 = null;
        Player player2 = null;
        switch (gameType) {
            case SINGLEPLAYER:
                player1 = new Player(player1Name, PlayerKind.HUMAN);
                player2 = new Player(player2Name, PlayerKind.NPC);
                break;
            case MULTIPLAYER:
                player1 = new Player(player1Name, PlayerKind.HUMAN);
                player2 = new Player(player2Name, PlayerKind.HUMAN);
                break;
            case AUTO:
                player1 = new Player(player1Name, PlayerKind.NPC);
                player2 = new Player(player2Name, PlayerKind.NPC);
                break;
            default:
        }
        return createPlayersOnBasisOfPlayerType(player1, player2);
    }

    private List<Player> createPlayersOnBasisOfPlayerType(Player player1, Player player2) {
        List<Player> playerList = new ArrayList<>();
        playerList.add(addNewPlayer(player1));
        playerList.add(addNewPlayer(player2));
        return playerList;
    }
}
