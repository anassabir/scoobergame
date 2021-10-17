package com.anas.scoobergame.domain.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "MOVE")
public class Move extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "game_id", updatable = false)
    private Game game;

//    @Column(name = "original_number", nullable = false)
//    private Integer originalNumber;

    @Column(name = "description")
    private String description;

    public Move() {}

    public Move(Game game, String description) {
        this.game = game;
        this.description = description;
    }

}
