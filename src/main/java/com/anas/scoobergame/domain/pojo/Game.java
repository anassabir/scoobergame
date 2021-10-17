package com.anas.scoobergame.domain.pojo;

import com.anas.scoobergame.enums.GameStatus;
import com.anas.scoobergame.enums.GameType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "GAME")
public class Game extends AuditableEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "game_kind", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private GameType gameType;

    @ManyToOne
    @JoinColumn(name = "first_current_player_id", nullable = false)
    private Player firstCurrentPlayer;

    @ManyToOne
    @JoinColumn(name = "second_current_player_id", nullable = false)
    private Player secondCurrentPlayer;

    @ManyToOne
    @JoinColumn(name = "next_player_id", nullable = false)
    private Player nextPlayer;

    @Column(name = "current_number", nullable = false)
    private Integer currentNumber;

    @Column(name = "original_number", nullable = false)
    private Integer originalNumber;

    @Column(name = "game_status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private GameStatus gameStatus;

    public Game() {}

    public Game(GameType gameType, Player player1, Player player2, Player nextPlayer, Integer currentNumber, Integer originalNumber, GameStatus gameStatus) {
        this.gameType = gameType;
        this.firstCurrentPlayer = player1;
        this.secondCurrentPlayer = player2;
        this.nextPlayer = nextPlayer;
        this.currentNumber = currentNumber;
        this.originalNumber = originalNumber;
        this.gameStatus = gameStatus;
    }

}
